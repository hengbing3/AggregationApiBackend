package com.christer.project;

import com.christer.myapiclientsdk.uitls.SignUtils;
import com.christer.myapicommon.common.ResultCode;
import com.christer.myapicommon.exception.BusinessException;
import com.christer.myapicommon.model.entity.InterfaceInfo;
import com.christer.myapicommon.model.entity.UserEntity;
import com.christer.myapicommon.service.InnerInterfaceInfoService;
import com.christer.myapicommon.service.InnerUserInterfaceInfoService;
import com.christer.myapicommon.service.InnerUserService;
import com.christer.project.util.RedissonLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-13 14:34
 * Description:
 * API 业务网关，自定义全局过滤器
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    protected static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "localhost");

    public static final Integer ONE_MINUTES = 60;

    public static final String NO_AUTHORIZED = "无权限";

    private final RedissonLockUtil redissonLockUtil;

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    public static final String HOST_URL = "http://localhost:8213";

    public CustomGlobalFilter(RedissonLockUtil redissonLockUtil) {
        this.redissonLockUtil = redissonLockUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 用户发送请求到API 网关
        log.info("custom global filter");
        // 2. 请求日志
        final ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识:{}", request.getId());
        final String url = HOST_URL + request.getPath().value();
        log.info("请求路径:{}", url);
        final String method = Objects.requireNonNull(request.getMethod()).toString();
        log.info("请求方法:{}", method);
        log.info("请求参数:{}", request.getQueryParams());
        final InetSocketAddress remoteAddress = request.getRemoteAddress();
        // 获取响应对象
        final ServerHttpResponse response = exchange.getResponse();
        if (Objects.isNull(remoteAddress)) {
            log.error("请求地址不存在：{}", HttpStatus.BAD_REQUEST);
            return handleResponseError(response, HttpStatus.BAD_REQUEST, "请求地址不存在");
        }
        log.info("请求来源地址:{}", remoteAddress);
        // 3. 黑白名单
        if (!IP_WHITE_LIST.contains(remoteAddress.getHostString())) {
            log.error("非白名单请求:{}", HttpStatus.FORBIDDEN);
            return handleResponseError(response, HttpStatus.FORBIDDEN, "非白名单请求");
        }
        final HttpHeaders headers = request.getHeaders();
        // 4. 用户鉴权（判断 ak, sk 是否合法）
        // 1.拿到这五个我们可以一步一步去做校验,比如 accessKey 我们先去数据库中查一下
        // 从请求头中获取参数
        final String accessKey = headers.getFirst("accessKey");
        final String nonce = headers.getFirst("nonce");
        final String timestamp = headers.getFirst("timestamp");
        final String sign = headers.getFirst("sign");
        final String unDecodeBody = headers.getFirst("body");
        // 获取用户的ak 和 sk
        UserEntity invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUserByCondition(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUserByCondition error", e);
        }
        if (null == invokeUser) {
            return handleResponseError(response, HttpStatus.UNAUTHORIZED, NO_AUTHORIZED);
        }
        if (!StringUtils.hasText(accessKey) || !accessKey.equals(invokeUser.getAccessKey())) {
            return handleResponseError(response, HttpStatus.UNAUTHORIZED, NO_AUTHORIZED);
        }
        // 随机数可以存储到Redis或者HashMap 校验是否被使用过，可以过5min清空一次数据,用于防止重放
        if (!StringUtils.hasText(nonce) || nonce.length() != 6) {
            return handleResponseError(response, HttpStatus.UNAUTHORIZED, NO_AUTHORIZED);
        }
        // 只对一分钟以内的请求有效 随机数和时间戳可以配合，防止重放
        if (!StringUtils.hasText(timestamp) || (System.currentTimeMillis() / 1000) - Long.parseLong(timestamp) > ONE_MINUTES) {
            return handleResponseError(response, HttpStatus.UNAUTHORIZED, NO_AUTHORIZED);
        }
        if (!StringUtils.hasText(unDecodeBody)) {
            return handleResponseError(response, HttpStatus.UNAUTHORIZED, NO_AUTHORIZED);
        }
        String body;
        try {
            body = URLDecoder.decode(unDecodeBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return handleResponseError(response, HttpStatus.BAD_REQUEST, NO_AUTHORIZED);
        }
        final String genSign = SignUtils.genSign(body, invokeUser.getSecretKey());
        if (!StringUtils.hasText(sign) || !sign.equals(genSign)) {
            return handleResponseError(response, HttpStatus.UNAUTHORIZED, NO_AUTHORIZED);
        }
        // 5. 请求模拟接口是否存在
        // 从数据库中查询模拟接口是否存在，以及请求方法是否匹配（还可以校验请求参数）
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfoByCondition(url, method);
        } catch (Exception e) {
            log.error("getInterfaceInfoByCondition error:", e);
        }
        if (null == interfaceInfo) {
            return handleResponseError(response, HttpStatus.UNAUTHORIZED, "无法请求到匹配路径！");
        }
        // 6. 请求转发，调用模拟接口
//        final Mono<Void> filter = chain.filter(exchange);
        // 7. 响应日志
        log.info("响应code:{}", response.getStatusCode());
        // 9. 调用失败，返回一个规范的错误码
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, Long interfaceInfoId, Long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();

            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 调用成功，接口调用次数 + 1 invokeCount
                                redissonLockUtil.redissonDistributedLocks(("gateway_" + userId + "_" + interfaceInfoId).intern(), () -> {
                                    boolean invoke = innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                    if (!invoke) {
                                        throw new BusinessException(ResultCode.FAILED, "系统繁忙，接口调用失败");
                                    }
                                },"操作失败！");

                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                sb2.append("<--- {} {} \n");
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                // 打印日志
                                log.info("响应结果：{}", data);
                                log.info(sb2.toString(), rspArgs.toArray());
                                //log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            //降级处理返回数据
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("网关处理响应异常:", e);
            return chain.filter(exchange);
        }
    }

    /**
     * 响应错误处理
     *
     * @param response     响应体
     * @param status       响应状态码
     * @param errorMessage 响应错误信息
     * @return
     */
    private Mono<Void> handleResponseError(ServerHttpResponse response, HttpStatus status, String errorMessage) {
        log.error("错误请求，请求状态码：{}", status);
        response.setStatusCode(status);
        // 构造错误信息的JSON字符串
        final String errorJson = String.format("{\"errorMessage\": \"%s\", \"statusCode\": \"%s\"}", errorMessage, status.value());
        // 将错误信息转换为DataBuffer
        final byte[] bytes = errorJson.getBytes(StandardCharsets.UTF_8);
        // 设置响应头，指明返回内容类型为JSON
        response.getHeaders().add("Content-Type", "application/json");
        final DataBuffer buffer = response.bufferFactory().wrap(bytes);
        // 异步写入错误信息到响应体
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }
}
