DROP TABLE IF EXISTS `t_system_user`;
CREATE TABLE `t_system_user` (
  `id` bigint(18) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);