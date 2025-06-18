CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `file_url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `file_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
