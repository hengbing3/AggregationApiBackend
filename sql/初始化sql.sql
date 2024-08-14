/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : my_api

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 11/08/2024 21:38:32
*/


-- ----------------------------
-- Table structure for interface_info
-- ----------------------------
DROP TABLE IF EXISTS `interface_info`;
CREATE TABLE `interface_info`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
                                   `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
                                   `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '接口地址',
                                   `request_params` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '请求参数',
                                   `request_header` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '请求头',
                                   `response_header` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '响应头',
                                   `status` int(11) NOT NULL DEFAULT 0 COMMENT '接口状态（0-关闭，1-开启）',
                                   `method` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '请求类型',
                                   `create_user_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '创建人',
                                   `update_user_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '更新人',
                                   `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                   `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                   `deleted_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '接口信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interface_info
-- ----------------------------
INSERT INTO `interface_info` VALUES (1, '田伟祺11', 'Eu', 'www.quinn-cummings.co', 'OpJV', 'WuXSp', 'dsfds', 0, 'Ar', 1, 1, '2024-01-28 17:20:27', '2024-02-05 17:23:18', 1);
INSERT INTO `interface_info` VALUES (2, '郝烨霖', '3JX用户', 'www.philip-abshire.name', 'hlM', 'xx7eB', 'sdfsd', 0, '90tmj', 1, 1, '2024-01-28 17:20:27', '2024-02-19 22:13:03', 0);
INSERT INTO `interface_info` VALUES (3, '姜修洁333', 'ilt', 'www.jared-nicolas.net', 'kCNeF', 'uF', 'vbsdfg', 1, 'q35', 1, 1, '2024-01-28 17:20:27', '2024-02-07 17:16:46', 0);
INSERT INTO `interface_info` VALUES (4, '邵风华', 'ZHq', 'www.morris-kunde.io', 'EWehc', '79i', 'dsfasf', 0, 'fNsq', 1, 1, '2024-01-28 17:20:27', '2024-02-07 23:41:14', 0);
INSERT INTO `interface_info` VALUES (5, '秦子骞', 'ia', 'www.wes-jaskolski.com', 'qY', 'jpjwr', 'sdfsdf', 1, 'YNOh', 1, 1, '2024-01-28 17:20:27', '2024-02-08 15:40:07', 0);
INSERT INTO `interface_info` VALUES (6, '龚建辉', '1LDd', 'www.rachael-flatley.name', 'Tu8rY', 'twLfI', NULL, 0, 'cbT', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (7, '邱越彬', '0Qp', 'www.ashli-glover.org', 'ck', 'YC6I', NULL, 0, 'vbVYO', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (8, '曹潇然', 'ceRgL', 'www.leatha-stracke.info', 'g0', 'ztUn', NULL, 0, 'jVbPC', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (9, '王懿轩', '9w', 'www.leon-gislason.com', 'JbK', 'HI', NULL, 0, 'SpKf', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (10, '徐正豪', 'cVTIm', 'www.jorge-miller.biz', 'n4zf', '6t', NULL, 0, 'Vgk5l', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (11, '林煜城', 'KLd9', 'www.kermit-batz.co', 's2I', 'IX', NULL, 0, 'FWy', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (12, '孔笑愚', '1wZ0', 'www.noe-goyette.biz', 'yMrmK', 'w8AL', NULL, 0, 'shG', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (13, '宋哲瀚', 'sL', 'www.gregg-connelly.org', 'QLMn9', 'IL', NULL, 0, '81W', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (14, '史博涛', 'Tv0G', 'www.kaleigh-blick.net', 'k1a', 'JL', NULL, 0, '1a', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (15, '彭健柏', '7Hf', 'www.corrinne-kshlerin.net', 'rXkMp', 'Ro', NULL, 0, 'wsz', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (16, '吴明轩', 'wSsuc', 'www.elene-goodwin.biz', '6UL', 'KXP', NULL, 0, '2jtBV', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (17, '范晓啸', 'R58', 'www.ernie-hayes.com', 'G5', 'lI1', NULL, 0, 'wUv5U', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (18, '卢擎宇', '7upo', 'www.lane-volkman.org', 'PrHtY', 'sTQuX', NULL, 0, 'vZY', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (19, '丁文博', 'opm', 'www.florrie-rutherford.co', 'me52m', '2o', NULL, 0, 'o0', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (20, '魏文博', 'Gh', 'www.jarvis-king.com', '2L', 'IlPdK', NULL, 0, 'k2U4', 1, 1, '2024-01-28 17:20:27', '2024-01-28 17:20:27', 0);
INSERT INTO `interface_info` VALUES (21, '大师傅但是', '撒旦发射点', 'http://localhost:8080', 'SDFSAD', 'DGFSDG', 'SDFDSF', 0, 'GET', 1, 1, '2024-02-01 22:01:45', '2024-02-01 22:01:45', 0);
INSERT INTO `interface_info` VALUES (22, 'getUsernameByPost', '获取用户名', '/name/user', '[\n  {\n    \"name\": \"username\",\n    \"type\": \"string\"\n  }\n]', '{\n  \"Content-Type\":\"application/json\"\n}', '{\n  \"Content-Type\":\"application/json\"\n}', 1, 'POST', 1, 1, '2024-02-08 13:02:20', '2024-04-29 22:16:16', 0);
INSERT INTO `interface_info` VALUES (23, 'getNameByGet', '获取姓名', 'http://localhost:8213/api/name', '[\n  {\n    \"name\": \"name\",\n    \"type\": \"string\"\n  }\n]', '{\n  \"Content-Type\": \"application/json\"\n}', '{\n  \"Content-Type\": \"application/json\"\n}', 0, 'GET', 1, 1, '2024-02-08 13:24:17', '2024-02-08 13:30:03', 0);
INSERT INTO `interface_info` VALUES (24, 'sdf', 'sdfsd', 'sdfsd', 'dsfsdf', 'sdfdsf', 'sdfdf', 0, 'POST', 1, 1, '2024-02-08 13:33:02', '2024-02-08 13:33:02', 0);
INSERT INTO `interface_info` VALUES (25, 'sdf2', 'sdfsd2', 'sdfsd2', 'dsfsdf2', 'sdfdsf2', 'sdfdf2', 0, 'POST', 1, 1, '2024-02-08 13:39:06', '2024-02-08 13:39:06', 0);
INSERT INTO `interface_info` VALUES (26, 'sdfs', 'sadfsgf', 'fghdf', 'fghdfgh', 'fdghdfgh', 'fghdfgh', 0, 'DELETE', 1, 1, '2024-02-08 13:39:48', '2024-02-08 13:39:48', 0);
INSERT INTO `interface_info` VALUES (27, 'gdfsg', 'dfgdfg', 'dfgdfg', 'dfgdfg', 'dfgdfg', 'dfgdfg', 0, 'PUT', 1, 1, '2024-02-08 14:05:09', '2024-02-08 14:05:09', 0);
INSERT INTO `interface_info` VALUES (28, 'sdfs', '圣诞夫', '圣诞夫', '圣诞夫', '圣诞夫', '圣诞夫', 0, 'POST', 1, 1, '2024-02-08 14:29:15', '2024-02-08 14:29:15', 0);
INSERT INTO `interface_info` VALUES (29, 'sdfdsf', 'sdfsdf', 'sdfdsf', 'sdfsd', 'sdfsdf', 'sdff', 0, 'PUT', 1, 1, '2024-02-08 15:20:34', '2024-02-08 15:20:34', 0);
INSERT INTO `interface_info` VALUES (30, 'dfhugfh', 'fghdfgh', 'ghjfghj', 'ghjghj', 'sdgdfg', 'sdfgsdfg', 0, 'DELETE', 1, 1, '2024-02-08 15:21:06', '2024-02-08 15:21:06', 0);
INSERT INTO `interface_info` VALUES (31, '圣诞夫', '手动阀十分', 'dsfsd', 'sdf', 'asdfsdf', 'asdfsdf', 0, 'PUT', 1, 1, '2024-02-08 15:39:38', '2024-02-08 15:39:38', 0);
INSERT INTO `interface_info` VALUES (32, '摸鱼日历', '在线生成工作时的摸鱼日历', '/moyu', '无', '无', '无', 1, 'GET', 1, 1, '2024-04-25 22:36:49', '2024-04-29 22:16:09', 0);

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `parent_id` bigint(20) NOT NULL COMMENT 'parentId',
                                   `dept_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门名称',
                                   `create_user_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '创建用户 id',
                                   `update_user_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '更新用户 id',
                                   `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                   `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                   `deleted_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES (1, 0, 'API开放平台', 1, 1, '2024-03-03 22:20:53', '2024-03-03 22:20:53', 0);
INSERT INTO `sys_department` VALUES (2, 1, 'API审核部门', 1, 1, '2024-03-03 22:21:21', '2024-03-03 22:21:21', 0);
INSERT INTO `sys_department` VALUES (3, 1, '普通用户组', 1, 1, '2024-03-10 16:37:08', '2024-03-10 16:37:08', 0);



DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `user_account` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
                         `user_password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                         `union_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
                         `mp_open_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公众号openId',
                         `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
                         `user_avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
                         `user_profile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
                         `access_key` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'accessKey',
                         `secret_key` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'secretKey',
                         `user_role` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
                         `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                         `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                         `deleted_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
                         `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
                         `balance` bigint(20) NULL DEFAULT 30 COMMENT '钱包积分余额,注册送30积分',
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `idx_unionId`(`union_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

INSERT INTO `user` VALUES (1, 'admin', 'bad81df845583523e4236b5b0ce7721b', '', '', '阿斌', 'http://g.hiphotos.baidu.com/image/pic/item/6d81800a19d8bc3e770bd00d868ba61ea9d345f2.jpg', '', '04b39bb3e178c8ebd20dbff5c09dde84', '4ef454b6af98c4c4c37b74a5807dd1fc', 'admin', '2024-01-25 22:11:31', '2024-04-17 21:13:46', 0, NULL, 30);
INSERT INTO `user` VALUES (2, 'user', 'bad81df845583523e4236b5b0ce7721b', '', '', '普通用户', 'http://e.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7e41f5cfe760e0cf3d6cad6ee.jpg', '', '8a9533d270522f1aa8eeb69adc1eac1f', 'e404d67a6995a9cdd033e5782453ecb7', 'user', '2024-02-08 12:46:02', '2024-02-08 12:47:48', 0, NULL, 30);
INSERT INTO `user` VALUES (3, 'user2', 'bad81df845583523e4236b5b0ce7721b', '', '', '看看你的', '', '', '6e3aedb7b0058993f392da9d42480b63', '394ebb57cbe8bc6b090764d6cabbc70c', 'admin', '2024-03-12 20:03:33', '2024-03-19 21:57:10', 0, NULL, 30);
INSERT INTO `user` VALUES (4, 'myuser', '60df163f313e2320b7d81b05c788be66', '', '', '测试111', 'store\\623bf5fb-9b50-4ad8-9d10-52eb96cb375f.png', '的首发式地方·1', '94a3b8fc6f36cbe60f347d24134c87d3', 'cbe3d6e6b690fe2abf59f70db0067048', 'user', '2024-03-12 20:27:50', '2024-04-06 18:29:41', 0, NULL, 30);
INSERT INTO `user` VALUES (5, 'approve', 'bad81df845583523e4236b5b0ce7721b', '', '', '开放审核人', 'https://upload-images.jianshu.io/upload_images/5809200-caf66b935fd00e18.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240', '', 'bc755f8dc45dfd2de01398e61ef8cd74', '533da8b931605ed2e7fb61f66887a3fa', 'admin', '2024-03-19 22:16:32', '2024-03-19 22:16:49', 0, NULL, 30);
INSERT INTO `user` VALUES (6, 'user3', 'bad81df845583523e4236b5b0ce7721b', NULL, NULL, '的说法是', 'store\\14d3f3e1-a8be-40f2-80d6-903d2b8d4e64.jpg', 'asdfasdfsadf', 'd87be9fee2e7ff733530de1bdaf98964', '67e59e8784d0b226b6b1c1f4fde69586', 'user', '2024-03-31 22:19:30', '2024-03-31 22:19:30', 0, NULL, 30);
INSERT INTO `user` VALUES (7, 'user4', 'bad81df845583523e4236b5b0ce7721b', NULL, NULL, '冲！！！', 'store\\b76982cf-e782-4066-ad8f-17669fe5bd30.png', '式打法是', '76a0bc758ceb1ce775c641369dd93112', '7db27ac554a77d1c66868f75db2e6b76', 'user', '2024-04-01 23:47:44', '2024-04-02 00:07:49', 0, NULL, 30);
INSERT INTO `user` VALUES (8, 'user5', 'bad81df845583523e4236b5b0ce7721b', NULL, NULL, '不信邪', 'store\\4414d612-3549-4a53-89ac-a7595f4c4918.png', 'dsaf', 'acb9a2ceee78e6bed08b7ad9477ca551', 'b5643077bd8a06a40fc888e24d2567e6', 'user', '2024-04-01 23:52:00', '2024-04-05 15:59:40', 0, NULL, 30);


-- ----------------------------
-- Table structure for sys_user_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department`  (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `user_id` bigint(20) NOT NULL COMMENT '用户id',
                                        `department_id` bigint(20) NOT NULL COMMENT '部门id',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        INDEX `user_id`(`user_id`) USING BTREE,
                                        INDEX `department_id`(`department_id`) USING BTREE,
                                        CONSTRAINT `sys_user_department_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                        CONSTRAINT `sys_user_department_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_department
-- ----------------------------
INSERT INTO `sys_user_department` VALUES (1, 1, 1);
INSERT INTO `sys_user_department` VALUES (2, 3, 3);
INSERT INTO `sys_user_department` VALUES (3, 4, 3);
INSERT INTO `sys_user_department` VALUES (4, 5, 3);
INSERT INTO `sys_user_department` VALUES (5, 6, 3);
INSERT INTO `sys_user_department` VALUES (12, 7, 3);
INSERT INTO `sys_user_department` VALUES (21, 8, 3);

-- ----------------------------
-- Table structure for t_interface_info_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_interface_info_apply`;
CREATE TABLE `t_interface_info_apply`  (
                                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                           `process_instance_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '流程实例id',
                                           `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                                           `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
                                           `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口地址',
                                           `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
                                           `request_header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '请求头',
                                           `response_header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '响应头',
                                           `method` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求类型',
                                           `code_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代码块',
                                           `audit_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核状态',
                                           `create_user_id` bigint(20) NOT NULL COMMENT '申请人',
                                           `update_user_id` bigint(20) NOT NULL COMMENT '更新人',
                                           `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                           `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                           `deleted_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '接口申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_interface_info_apply
-- ----------------------------
INSERT INTO `t_interface_info_apply` VALUES (1, '63668513-e5f7-11ee-b59e-d0abd5b04905', '代码名称new', '测试描述', '我的url', '请求参数', '请求头11', '响应头new', 'GET', '这是一段代码new', '4', 4, 4, '2024-03-19 21:48:38', '2024-03-23 22:41:03', 0);
INSERT INTO `t_interface_info_apply` VALUES (2, 'd5293a90-e923-11ee-8df2-d0abd5b04905', '测试', '测试描述11', 'localhost:8080', '{\n  \"test\": \"123\"\n}', '11', '111', 'GET', 'public void notify(DelegateTask delegateTask) {\n        // 获取所有的流程变量\n        Map<String, Object> variables = delegateTask.getVariables();\n        Set<String> keys = variables.keySet();\n        for (String key : keys) {\n            Object obj = variables.get(key);\n            System.out.println(key + \" = \" + obj);\n            if(obj instanceof  String){\n              // 修改 流程变量的信息\n              // variables.put(key,obj + \":boge3306\"); 直接修改Map中的数据 达不到修改流程变量的效果\n              delegateTask.setVariable(key + \":boge3306\");\n            }\n        }\n    }', '4', 4, 4, '2024-03-23 22:44:20', '2024-03-24 20:10:17', 0);
INSERT INTO `t_interface_info_apply` VALUES (3, '21f56a70-e924-11ee-8df2-d0abd5b04905', '册数2', '刹石狩', '123', '士大夫撒旦', '撒旦发顺丰', '撒旦发射点发', 'POST', '球球你了', '0', 4, 4, '2024-03-23 22:46:29', '2024-03-24 22:43:46', 0);

-- ----------------------------
-- Table structure for t_interface_info_apply_record
-- ----------------------------
DROP TABLE IF EXISTS `t_interface_info_apply_record`;
CREATE TABLE `t_interface_info_apply_record`  (
                                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                                  `interface_info_apply_id` bigint(20) NOT NULL COMMENT '接口申请id',
                                                  `process_node` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '流程节点名称',
                                                  `process_node_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '流程节点id（任务id）',
                                                  `audit_result` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核结果 1： 通过 0：不通过',
                                                  `audit_opinion` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核意见',
                                                  `audit_user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核用户id',
                                                  `create_user_id` bigint(20) NOT NULL COMMENT '申请人',
                                                  `update_user_id` bigint(20) NOT NULL COMMENT '更新人',
                                                  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                                  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                                  `deleted_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '接口审核记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_interface_info_apply_record
-- ----------------------------
INSERT INTO `t_interface_info_apply_record` VALUES (1, 1, '用户申请', '6366d33b-e5f7-11ee-b59e-d0abd5b04905', '1', NULL, NULL, 4, 4, '2024-03-19 21:48:38', '2024-03-19 21:48:38', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (2, 1, 'API审核', '63729311-e5f7-11ee-b59e-d0abd5b04905', '1', '审核通过', 3, 4, 4, '2024-03-19 22:10:02', '2024-03-19 22:10:02', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (3, 1, 'API开放', '60a4779c-e5fa-11ee-b59e-d0abd5b04905', '0', '不通过', 5, 4, 4, '2024-03-19 22:18:37', '2024-03-19 22:18:37', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (4, 1, '用户申请', '93921366-e5fb-11ee-b59e-d0abd5b04905', '1', NULL, NULL, 4, 4, '2024-03-19 22:27:08', '2024-03-19 22:27:08', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (5, 1, 'API审核', 'c44f61ab-e5fc-11ee-b59e-d0abd5b04905', '1', '通过', 3, 4, 4, '2024-03-19 22:32:31', '2024-03-19 22:32:31', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (6, 1, 'API开放', '81bdcc04-e5fd-11ee-b59e-d0abd5b04905', '1', '通过', 5, 4, 4, '2024-03-19 22:33:05', '2024-03-19 22:33:05', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (7, 2, '用户申请', 'd5319f08-e923-11ee-8df2-d0abd5b04905', '1', NULL, NULL, 4, 4, '2024-03-23 22:44:20', '2024-03-23 22:44:20', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (8, 3, '用户申请', '21f59188-e924-11ee-8df2-d0abd5b04905', '1', NULL, NULL, 4, 4, '2024-03-23 22:46:29', '2024-03-23 22:46:29', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (9, 2, 'API审核', 'd548344e-e923-11ee-8df2-d0abd5b04905', '0', '不想让你通过', 3, 4, 4, '2024-03-24 20:03:23', '2024-03-24 20:03:23', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (10, 2, '用户申请', '8193bc15-e9d6-11ee-9443-d0abd5b04905', '1', NULL, NULL, 4, 4, '2024-03-24 20:10:17', '2024-03-24 20:10:17', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (11, 2, 'API审核', '7a9e770a-e9d7-11ee-9443-d0abd5b04905', '1', '可以吧', 3, 4, 4, '2024-03-24 22:35:13', '2024-03-24 22:35:13', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (12, 2, 'API开放', 'b93d6943-e9eb-11ee-9443-d0abd5b04905', '1', '牛逼！！！', 5, 4, 4, '2024-03-24 22:36:23', '2024-03-24 22:36:23', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (13, 3, 'API审核', '21fb84fe-e924-11ee-8df2-d0abd5b04905', '0', '去屎吧', 3, 4, 4, '2024-03-24 22:42:30', '2024-03-24 22:42:30', 0);
INSERT INTO `t_interface_info_apply_record` VALUES (14, 3, '用户申请', 'bdefff76-e9ec-11ee-9443-d0abd5b04905', '1', NULL, NULL, 4, 4, '2024-03-24 22:43:46', '2024-03-24 22:43:46', 0);

-- ----------------------------
-- Table structure for t_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `t_payment_info`;
CREATE TABLE `t_payment_info`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `order_no` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商户订单号',
                                   `transaction_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '支付订单号',
                                   `trade_type` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '交易类型',
                                   `trade_state` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '交易状态(SUCCESS：支付成功 REFUND：转入退款 NOTPAY：未支付 CLOSED：已关闭 REVOKED：已撤销（仅付款码支付会返回）\r\n                                                                              USERPAYING：用户支付中（仅付款码支付会返回）PAYERROR：支付失败（仅付款码支付会返回）)',
                                   `trade_state_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '交易状态描述',
                                   `success_time` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '支付完成时间',
                                   `openid` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户标识',
                                   `payer_total` bigint(20) NULL DEFAULT NULL COMMENT '用户支付金额',
                                   `currency` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'CNY' COMMENT '货币类型',
                                   `payer_currency` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'CNY' COMMENT '用户支付币种',
                                   `content` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '接口返回内容',
                                   `total` bigint(20) NULL DEFAULT NULL COMMENT '总金额(分)',
                                   `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                   `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '付款信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_payment_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_product_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_info`;
CREATE TABLE `t_product_info`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '产品名称',
                                   `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '产品描述',
                                   `total` bigint(20) NULL DEFAULT NULL COMMENT '金额(分)',
                                   `add_points` bigint(20) NOT NULL DEFAULT 0 COMMENT '增加积分个数',
                                   `product_type` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'RECHARGE' COMMENT '产品类型（VIP-会员 RECHARGE-充值,RECHARGEACTIVITY-充值活动）',
                                   `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '商品状态（0- 默认下线 1- 上线）',
                                   `expiration_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
                                   `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
                                   `update_user_id` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
                                   `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                   `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                   `deleted_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '产品信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_info
-- ----------------------------
INSERT INTO `t_product_info` VALUES (1, '1金币', '1金币等于1分钱', 1, 1, '1', 0, '2024-10-01 23:59:59', NULL, NULL, '2024-07-08 21:46:15', '2024-07-08 21:46:15', 0);
INSERT INTO `t_product_info` VALUES (2, '10金币', '10金币等于1毛钱', 10, 10, '1', 0, '2024-10-01 23:59:59', NULL, NULL, '2024-07-08 21:48:23', '2024-07-08 21:48:23', 0);
INSERT INTO `t_product_info` VALUES (3, '100金币', '100金币等于1元', 100, 100, '1', 0, '2024-10-01 23:59:59', NULL, NULL, '2024-07-08 21:49:16', '2024-07-08 21:49:16', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_interface_info
-- ----------------------------
DROP TABLE IF EXISTS `user_interface_info`;
CREATE TABLE `user_interface_info`  (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                        `user_id` bigint(20) NOT NULL COMMENT '调用用户 id',
                                        `interface_info_id` bigint(20) NOT NULL COMMENT '接口 id',
                                        `total_num` int(11) NOT NULL DEFAULT 0 COMMENT '总调用次数',
                                        `left_num` int(11) NOT NULL DEFAULT 0 COMMENT '剩余调用次数',
                                        `status` int(11) NOT NULL DEFAULT 0 COMMENT '0-正常，1-禁用',
                                        `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                        `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                        `deleted_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户调用接口关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_interface_info
-- ----------------------------
INSERT INTO `user_interface_info` VALUES (1, 1, 22, 45, 7, 0, '2024-02-09 17:39:38', '2024-04-24 23:38:18', 0);
INSERT INTO `user_interface_info` VALUES (2, 2, 2, 22, 12, 0, '2024-02-16 21:17:43', '2024-02-16 21:17:43', 0);
INSERT INTO `user_interface_info` VALUES (3, 1, 3, 12, 10, 0, '2024-02-16 21:17:43', '2024-02-16 21:17:43', 0);
INSERT INTO `user_interface_info` VALUES (4, 2, 4, 23, 10, 0, '2024-02-16 21:17:43', '2024-02-16 21:17:43', 0);
INSERT INTO `user_interface_info` VALUES (5, 1, 5, 11, 10, 0, '2024-02-16 21:17:43', '2024-02-16 21:17:43', 0);
INSERT INTO `user_interface_info` VALUES (6, 1, 32, 1, 9, 0, '2024-04-25 23:04:55', '2024-04-25 23:05:29', 0);


