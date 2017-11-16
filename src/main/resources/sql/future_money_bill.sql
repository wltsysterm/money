/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50022
Source Host           : 127.0.0.1:3306
Source Database       : future

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2017-11-16 22:16:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for future_money_bill
-- ----------------------------
DROP TABLE IF EXISTS `future_money_bill`;
CREATE TABLE `future_money_bill` (
  `id` varchar(255) NOT NULL,
  `project_name` varchar(255) NOT NULL,
  `project_price` varchar(255) NOT NULL,
  `project_count` varchar(255) NOT NULL,
  `member_id` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `create_time` varchar(255) NOT NULL,
  `settle_time` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
