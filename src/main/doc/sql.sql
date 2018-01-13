/*
 Navicat Premium Data Transfer

 Source Server         : jcool
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost:3306
 Source Schema         : crawler

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : 65001

 Date: 07/01/2018 23:12:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `solicitationNumber` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `agency` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `offerLocation` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `synopsis` longtext COLLATE utf8mb4_bin,
  `noticeType` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `postedDate` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `responseDate` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `archivingPolicy` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `archiveDate` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `originalSetAside` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `setAside` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `classificationCode` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `naicsCode` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `contractingOfficeAddress` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `placeOfPerformance` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `primaryPointOfContractName` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `primaryPointOfContractEmail` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `primaryPointOfContractPhone` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `filePath` varchar(4096) COLLATE utf8mb4_bin DEFAULT NULL,
  `url` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL,
  `taskId` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=694 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seed_url` varchar(1024) COLLATE utf8mb4_bin NOT NULL,
  `day_num` int(10) NOT NULL,
  `is_finish` bit(1) NOT NULL DEFAULT b'0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
