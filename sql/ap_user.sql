create database leadnews_user;
use leadnews_user;

CREATE TABLE ap_user (
                         id int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                         salt varchar(32) DEFAULT NULL COMMENT '密码加密盐',
                         name varchar(20) DEFAULT NULL COMMENT '用户名',
                         password varchar(32) DEFAULT NULL COMMENT '密码',
                         phone varchar(11) DEFAULT NULL COMMENT '手机号',
                         image varchar(255) DEFAULT NULL COMMENT '头像',
                         sex tinyint(1) unsigned DEFAULT NULL COMMENT '性别 0->男；1->女；2->未知',
                         is_certification tinyint(1) unsigned DEFAULT NULL COMMENT '0->未认证；1->已认证',
                         is_identity_authentication tinyint(1) DEFAULT NULL COMMENT '是否身份认证',
                         status tinyint(1) unsigned DEFAULT NULL COMMENT '0->正常；1->锁定',
                         flag tinyint(1) unsigned DEFAULT NULL COMMENT '0->普通用户；1->自媒体人；2->大V',
                         created_time datetime DEFAULT NULL COMMENT '注册时间',
                         PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='App用户信息表';

SHOW COLLATION LIKE 'utf8mb4_unicode_ci';