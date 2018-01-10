/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.245
Source Server Version : 50709
Source Host           : 192.168.0.245:3306
Source Database       : ias-test

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2018-01-09 19:46:29
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_system_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_user`;
CREATE TABLE `t_system_user` (
  `id` bigint(18) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_system_user
-- ----------------------------
INSERT INTO `t_system_user` VALUES ('950686082289106944', 'name11000', '0');
INSERT INTO `t_system_user` VALUES ('950686087292911616', 'name11001', '0');
INSERT INTO `t_system_user` VALUES ('950686087527792640', 'name11002', '0');
INSERT INTO `t_system_user` VALUES ('950686087720730624', 'name11003', '0');
INSERT INTO `t_system_user` VALUES ('950686087980777472', 'name11004', '0');
INSERT INTO `t_system_user` VALUES ('950686088177909760', 'name11005', '0');
INSERT INTO `t_system_user` VALUES ('950686088446345216', 'name11006', '0');
INSERT INTO `t_system_user` VALUES ('950686088677031936', 'name11007', '0');
INSERT INTO `t_system_user` VALUES ('950686088987410432', 'name11008', '0');
INSERT INTO `t_system_user` VALUES ('950686089205514240', 'name11009', '0');
INSERT INTO `t_system_user` VALUES ('950686089406840832', 'name11010', '0');
INSERT INTO `t_system_user` VALUES ('950686089574612992', 'name11011', '0');
INSERT INTO `t_system_user` VALUES ('950686089754968064', 'name11012', '0');
INSERT INTO `t_system_user` VALUES ('950686090010820608', 'name11013', '0');
INSERT INTO `t_system_user` VALUES ('950686090207952896', 'name11014', '0');
INSERT INTO `t_system_user` VALUES ('950686090480582656', 'name11015', '0');
INSERT INTO `t_system_user` VALUES ('950686090753212416', 'name11016', '0');
INSERT INTO `t_system_user` VALUES ('950686090950344704', 'name11017', '0');
INSERT INTO `t_system_user` VALUES ('950686091164254208', 'name11018', '0');
INSERT INTO `t_system_user` VALUES ('950686091441078272', 'name11019', '0');
INSERT INTO `t_system_user` VALUES ('950686091688542208', 'name11020', '0');
INSERT INTO `t_system_user` VALUES ('950686091940200448', 'name11021', '0');
INSERT INTO `t_system_user` VALUES ('950686092179275776', 'name11022', '0');
INSERT INTO `t_system_user` VALUES ('950686092443516928', 'name11023', '0');
INSERT INTO `t_system_user` VALUES ('950686092716146688', 'name11024', '0');
INSERT INTO `t_system_user` VALUES ('950686092984582144', 'name11025', '0');
INSERT INTO `t_system_user` VALUES ('950686093265600512', 'name11026', '0');
INSERT INTO `t_system_user` VALUES ('950686093525647360', 'name11027', '0');
INSERT INTO `t_system_user` VALUES ('950686093819248640', 'name11028', '0');
INSERT INTO `t_system_user` VALUES ('950686094100267008', 'name11029', '0');
INSERT INTO `t_system_user` VALUES ('950686094561640448', 'name11030', '0');
INSERT INTO `t_system_user` VALUES ('950686094876213248', 'name11031', '0');
INSERT INTO `t_system_user` VALUES ('950686095144648704', 'name11032', '0');
INSERT INTO `t_system_user` VALUES ('950686095375335424', 'name11033', '0');
INSERT INTO `t_system_user` VALUES ('950686095752822784', 'name11034', '0');
INSERT INTO `t_system_user` VALUES ('950686096004481024', 'name11035', '0');
INSERT INTO `t_system_user` VALUES ('950686096629432320', 'name11036', '0');
INSERT INTO `t_system_user` VALUES ('950686097006919680', 'name11037', '0');
INSERT INTO `t_system_user` VALUES ('950686097329881088', 'name11038', '0');
INSERT INTO `t_system_user` VALUES ('950686097707368448', 'name11039', '0');
INSERT INTO `t_system_user` VALUES ('950686098038718464', 'name11040', '0');
INSERT INTO `t_system_user` VALUES ('950686098374262784', 'name11041', '0');
INSERT INTO `t_system_user` VALUES ('950686098818859008', 'name11042', '0');
INSERT INTO `t_system_user` VALUES ('950686099158597632', 'name11043', '0');
INSERT INTO `t_system_user` VALUES ('950686099540279296', 'name11044', '0');
INSERT INTO `t_system_user` VALUES ('950686099775160320', 'name11045', '0');
INSERT INTO `t_system_user` VALUES ('950686100077150208', 'name11046', '0');
INSERT INTO `t_system_user` VALUES ('950686100391723008', 'name11047', '0');
INSERT INTO `t_system_user` VALUES ('950686100643381248', 'name11048', '0');
INSERT INTO `t_system_user` VALUES ('950686100953759744', 'name11049', '0');
INSERT INTO `t_system_user` VALUES ('950686101197029376', 'name11050', '0');
INSERT INTO `t_system_user` VALUES ('950686101641625600', 'name11051', '0');
INSERT INTO `t_system_user` VALUES ('950686101926838272', 'name11052', '0');
INSERT INTO `t_system_user` VALUES ('950686102262382592', 'name11053', '0');
INSERT INTO `t_system_user` VALUES ('950686102547595264', 'name11054', '0');
INSERT INTO `t_system_user` VALUES ('950686102979608576', 'name11055', '0');
INSERT INTO `t_system_user` VALUES ('950686103168352256', 'name11056', '0');
INSERT INTO `t_system_user` VALUES ('950686103520673792', 'name11057', '0');
INSERT INTO `t_system_user` VALUES ('950686103751360512', 'name11058', '0');
INSERT INTO `t_system_user` VALUES ('950686104023990272', 'name11059', '0');
INSERT INTO `t_system_user` VALUES ('950686104250482688', 'name11060', '0');
INSERT INTO `t_system_user` VALUES ('950686104518918144', 'name11061', '0');
INSERT INTO `t_system_user` VALUES ('950686104908988416', 'name11062', '0');
INSERT INTO `t_system_user` VALUES ('950686105311641600', 'name11063', '0');
INSERT INTO `t_system_user` VALUES ('950686105512968192', 'name11064', '0');
INSERT INTO `t_system_user` VALUES ('950686105731072000', 'name11065', '0');
INSERT INTO `t_system_user` VALUES ('950686105944981504', 'name11066', '0');
INSERT INTO `t_system_user` VALUES ('950686106171473920', 'name11067', '0');
INSERT INTO `t_system_user` VALUES ('950686106385383424', 'name11068', '0');
INSERT INTO `t_system_user` VALUES ('950686106607681536', 'name11069', '0');
INSERT INTO `t_system_user` VALUES ('950686106876116992', 'name11070', '0');
INSERT INTO `t_system_user` VALUES ('950686107090026496', 'name11071', '0');
INSERT INTO `t_system_user` VALUES ('950686107308130304', 'name11072', '0');
INSERT INTO `t_system_user` VALUES ('950686107756920832', 'name11073', '0');
INSERT INTO `t_system_user` VALUES ('950686107962441728', 'name11074', '0');
INSERT INTO `t_system_user` VALUES ('950686108180545536', 'name11075', '0');
INSERT INTO `t_system_user` VALUES ('950686108386066432', 'name11076', '0');
INSERT INTO `t_system_user` VALUES ('950686108604170240', 'name11077', '0');
INSERT INTO `t_system_user` VALUES ('950686108801302528', 'name11078', '0');
INSERT INTO `t_system_user` VALUES ('950686108985851904', 'name11079', '0');
INSERT INTO `t_system_user` VALUES ('950686109216538624', 'name11080', '0');
INSERT INTO `t_system_user` VALUES ('950686109405282304', 'name11081', '0');
INSERT INTO `t_system_user` VALUES ('950686109610803200', 'name11082', '0');
INSERT INTO `t_system_user` VALUES ('950686109858267136', 'name11083', '0');
INSERT INTO `t_system_user` VALUES ('950686110143479808', 'name11084', '0');
INSERT INTO `t_system_user` VALUES ('950686110340612096', 'name11085', '0');
INSERT INTO `t_system_user` VALUES ('950686110525161472', 'name11086', '0');
INSERT INTO `t_system_user` VALUES ('950686110801985536', 'name11087', '0');
INSERT INTO `t_system_user` VALUES ('950686111502434304', 'name11088', '0');
INSERT INTO `t_system_user` VALUES ('950686111724732416', 'name11089', '0');
INSERT INTO `t_system_user` VALUES ('950686111921864704', 'name11090', '0');
INSERT INTO `t_system_user` VALUES ('950686112139968512', 'name11091', '0');
INSERT INTO `t_system_user` VALUES ('950686112337100800', 'name11092', '0');
INSERT INTO `t_system_user` VALUES ('950686112559398912', 'name11093', '0');
INSERT INTO `t_system_user` VALUES ('950686112756531200', 'name11094', '0');
INSERT INTO `t_system_user` VALUES ('950686112978829312', 'name11095', '0');
INSERT INTO `t_system_user` VALUES ('950686113175961600', 'name11096', '0');
INSERT INTO `t_system_user` VALUES ('950686113402454016', 'name11097', '0');
INSERT INTO `t_system_user` VALUES ('950686113603780608', 'name11098', '0');
INSERT INTO `t_system_user` VALUES ('950686113813495808', 'name11099', '0');
