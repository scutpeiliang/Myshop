/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.5.49 : Database - myshop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`myshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `myshop`;

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `gid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) NOT NULL COMMENT '商品名',
  `description` varchar(100) NOT NULL COMMENT '商品描述',
  `price` double NOT NULL COMMENT '商品原价',
  `stock` int(11) NOT NULL COMMENT '商品库存',
  `img` varchar(50) NOT NULL COMMENT '商品图片',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `discount_price` double NOT NULL COMMENT '秒杀价',
  `begin_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀开始时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `goods` */

insert  into `goods`(`gid`,`name`,`description`,`price`,`stock`,`img`,`create_time`,`update_time`,`discount_price`,`begin_time`,`end_time`) values (1,'门票','演唱会的门票。(限购一张)',1000,9,'img/ticket.jpg','2020-01-28 16:10:18','2020-01-30 23:11:45',200,'2020-01-30 16:26:38','2020-02-01 19:10:18');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `oid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) NOT NULL COMMENT '下单用户id',
  `gid` int(11) NOT NULL COMMENT '订单商品id',
  `money` double NOT NULL COMMENT '订单总价',
  `status` int(11) NOT NULL COMMENT '订单状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `expire_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单过期时间',
  PRIMARY KEY (`oid`),
  KEY `order_user` (`uid`),
  KEY `order_good` (`gid`),
  CONSTRAINT `order_good` FOREIGN KEY (`gid`) REFERENCES `goods` (`gid`),
  CONSTRAINT `order_user` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `orders` */

insert  into `orders`(`oid`,`uid`,`gid`,`money`,`status`,`create_time`,`expire_time`) values (7,1,1,200,0,'2020-01-30 23:11:45','2020-01-31 23:11:45');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id，主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '加密后密码',
  `salt` varchar(20) NOT NULL COMMENT '用于密码加密的盐值',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `phone` varchar(20) NOT NULL COMMENT '手机号码',
  `address` varchar(200) DEFAULT NULL COMMENT '联系地址',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */

insert  into `users`(`uid`,`username`,`password`,`salt`,`create_time`,`phone`,`address`,`update_time`) values (1,'username','c2b24b95e0eaca65bf903452212c780e','username','2020-01-28 16:10:18','123456','默认地址','2020-01-28 16:10:18');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
