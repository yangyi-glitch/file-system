CREATE TABLE `file`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `file_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `file_url`  varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `file_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `user`
(
    `user_id`       int(11) NOT NULL AUTO_INCREMENT,
    `account`       varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `username`      varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `password`      varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `email`         varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `created`       varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `last_modified` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `role`          int(255) DEFAULT NULL,
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `user` VALUES (1, '8000843017', '杨溢', '123456', '1965997927@qq.com', '2024-07-18', '2024-07-18', 0);
INSERT INTO `user` VALUES (2, '1067307503', '压索', '123456', '1965997927@qq.com', '2024-07-18', '2024-07-18', 1);
INSERT INTO `user` VALUES (4, '12322534', '小智', '123456', '42143512', '2024-09-19', '2024-09-19', 1);
INSERT INTO `user` VALUES (5, 'admin', 'admin', 'admin', '1965997927@qq.com', '2024-09-19', '2024-09-19', 0);

CREATE TABLE `article`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `book_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '书名',
    `price`     varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '价格',
    `author`    varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '作者',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
