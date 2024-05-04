
-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    union_id      varchar(256)                           null comment '微信开放平台id',
    mp_open_id     varchar(256)                           null comment '公众号openId',
    email          varchar(256)                           null comment '邮箱',
    user_name     varchar(256)                           null comment '用户昵称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    user_profile  varchar(512)                           null comment '用户简介',
    access_key  varchar(512)                           null comment 'accessKey',
    secret_key  varchar(512)                           null comment 'secretKey',
    balance        bigint       default 30                not null comment '钱包积分余额,注册送30币',
    status         tinyint      default 0                 not null comment '账号状态（0- 正常 1- 封号）',
    user_role     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted_flag     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (union_id)
    ) comment '用户' collate = utf8mb4_unicode_ci;

--- 接口信息表
CREATE TABLE `interface_info`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' primary key,
    `name`            varchar(256) NOT NULL COMMENT '名称',
    `description`     varchar(256) NULL COMMENT '描述',
    `url`             varchar(512) NOT NULL COMMENT '接口地址',
    `request_params`  text         NOT NULL COMMENT '请求参数',
    `request_header`  text NULL COMMENT '请求头',
    `response_header` text NULL COMMENT '响应头',
    `status`          int(11) NOT NULL DEFAULT 0 COMMENT '接口状态（0-关闭，1-开启）',
    `method`          varchar(256) NOT NULL COMMENT '请求类型',
    `consume_points`  bigint default 0 not null comment '扣除积分数',
    `create_user_id`  bigint(20) NOT NULL DEFAULT 1 COMMENT '创建人',
    `update_user_id`  bigint(20) NOT NULL DEFAULT 1 COMMENT '更新人',
    `create_time`     datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) COMMENT '创建时间',
    `update_time`     datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0) ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '更新时间',
    `deleted_flag`    tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)'
) comment '接口信息表' collate = utf8mb4_unicode_ci;

-- 用户调用接口信息关系
CREATE TABLE `user_interface_info` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `user_id` bigint(20) NOT NULL COMMENT '调用用户 id',
                                       `interface_info_id` bigint(20) NOT NULL COMMENT '接口 id',
                                       `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '总调用次数',
                                       `status` int(11) NOT NULL DEFAULT '0' COMMENT '0-正常，1-禁用',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删, 1-已删)',
                                       PRIMARY KEY (`id`)
) comment '用户调用接口关系表' collate = utf8mb4_unicode_ci;



-- 产品信息
create table if not exists t_product_info
(
    id             bigint auto_increment comment 'id' primary key,
    name           varchar(256)                           not null comment '产品名称',
    description    varchar(256)                           null comment '产品描述',
    total          bigint                                 null comment '金额(分)',
    add_points      bigint       default 0                 not null comment '增加积分个数',
    product_type    varchar(256) default 'RECHARGE'        not null comment '产品类型（VIP-会员 RECHARGE-充值,RECHARGEACTIVITY-充值活动）',
    status         tinyint      default 0                 not null comment '商品状态（0- 默认下线 1- 上线）',
    expiration_time datetime                               null comment '过期时间',
    create_user_id         bigint                                 null comment '创建人',
    update_user_id         bigint                                 null comment '更新人',
    createTime     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime     default CURRENT_TIMESTAMP not null comment '更新时间',
    deleted_flag       tinyint      default 0                 not null comment '是否删除'
    )
    comment '产品信息';

-- 产品订单
create table if not exists t_product_order
(
    id             bigint auto_increment comment 'id' primary key,
    order_no        varchar(256)                           not null comment '订单号',
    code_url        varchar(256)                           null comment '二维码地址',
    product_id      bigint                                 not null comment '商品id',
    product_name      varchar(256)                           not null comment '商品名称',
    total          bigint                                 not null comment '金额(分)',
    status         varchar(256) default 'NOTPAY'          not null comment '交易状态(SUCCESS：支付成功 REFUND：转入退款 NOTPAY：未支付 CLOSED：已关闭 REVOKED：已撤销（仅付款码支付会返回）
                                                                              USERPAYING：用户支付中（仅付款码支付会返回）PAYERROR：支付失败（仅付款码支付会返回）)',
    pay_type        varchar(256) default 'WX'              not null comment '支付方式（默认 WX- 微信 ZFB- 支付宝）',
    form_data       text                                   null comment '支付宝formData',
    addPoints      bigint       default 0                 not null comment '增加积分个数',
    expiration_time datetime                               null comment '过期时间',
    create_user_id         bigint                                 not null comment '创建人',
    createTime     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
    )
    comment '商品订单';

-- 付款信息
create table if not exists t_payment_info
(
    id             bigint auto_increment comment 'id' primary key,
    order_no        varchar(256)                           null comment '商户订单号',
    transaction_id  varchar(256)                           null comment '支付订单号',
    trade_type      varchar(256)                           null comment '交易类型',
    trade_state     varchar(256)                           null comment '交易状态(SUCCESS：支付成功 REFUND：转入退款 NOTPAY：未支付 CLOSED：已关闭 REVOKED：已撤销（仅付款码支付会返回）
                                                                              USERPAYING：用户支付中（仅付款码支付会返回）PAYERROR：支付失败（仅付款码支付会返回）)',
    trade_state_desc varchar(256)                           null comment '交易状态描述',
    success_time    varchar(256)                           null comment '支付完成时间',
    openid         varchar(256)                           null comment '用户标识',
    payer_total     bigint                                 null comment '用户支付金额',
    currency       varchar(256) default 'CNY'             null comment '货币类型',
    payer_currency  varchar(256) default 'CNY'             null comment '用户支付币种',
    content        text                                   null comment '接口返回内容',
    total          bigint                                 null comment '总金额(分)',
    create_time     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
    ) comment '付款信息';


-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumb_num   int      default 0                 not null comment '点赞数',
    favour_num  int      default 0                 not null comment '收藏数',
    user_id     bigint                             not null comment '创建用户 id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted_flag   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (user_id)
    ) comment '帖子' collate = utf8mb4_unicode_ci;

