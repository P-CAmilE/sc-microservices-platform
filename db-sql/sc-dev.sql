/*
 Navicat Premium Data Transfer

 Source Server         : MySQL-5.7-168.3
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 192.168.168.3:3306
 Source Schema         : sc-dev

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 14/08/2020 17:28:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for DEPARTMENT
-- ----------------------------
DROP TABLE IF EXISTS `DEPARTMENT`;
CREATE TABLE `DEPARTMENT`  (
  `DEPARTMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPARTMENT_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DEPARTMENT_INTRO` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`DEPARTMENT_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of DEPARTMENT
-- ----------------------------

-- ----------------------------
-- Table structure for USER
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER`  (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ACCOUNT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `USER_PASSWORD` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `USER_TYPE` int(11) NOT NULL DEFAULT 0,
  `USER_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'user',
  `USER_DEPARTMENT_ID` int(11) NULL DEFAULT NULL,
  `USER_EMAIL` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `USER_START_TIME` time(0) NULL DEFAULT NULL,
  `USER_END_TIME` time(0) NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`) USING BTREE,
  UNIQUE INDEX `USER_ACCOUNT`(`USER_ACCOUNT`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of USER
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
