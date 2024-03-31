package com.christer.project.controller;

import com.christer.project.common.CommonResult;
import com.christer.project.common.ResultBody;
import com.christer.project.exception.BusinessException;
import com.christer.project.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.christer.project.common.ResultCode.FILE_UPLOAD_ERROR;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-31 16:21
 * Description:
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/files")
public class FileController {


    @Value("${file.max-upload-size}")
    private int maxUploadSize;

    private final FileService fileService;

    @PostMapping("/upload")
    public CommonResult<List<String>> uploadFiles(@RequestParam("file") MultipartFile[] files) {
        log.info("批量文件上传，校验上传文件数量");
        if (Objects.isNull(files) || files.length > maxUploadSize) {
            throw new BusinessException("单次文件最大上传数量为" + maxUploadSize + "个，请重新上传");
        }
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return ResultBody.failed(FILE_UPLOAD_ERROR.getCode(),"请不要上传空文件");
            }
            try (final InputStream inputStream = file.getInputStream()){
                // 将文件保存为临时路径并返回
                fileUrls.add(fileService.transferToTempPath(inputStream, file.getOriginalFilename()));
            } catch (IOException e) {
                log.error("文件上传异常:{0}", e);
                return ResultBody.failed(FILE_UPLOAD_ERROR.getCode(),"无法保存文件:" + file.getOriginalFilename());
            }
        }
        return ResultBody.success(fileUrls);
    }
}
