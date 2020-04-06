/*
SQLyog Professional v12.08 (64 bit)
MySQL - 5.7.28 : Database - sy_blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sy_blog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sy_blog`;

/*Table structure for table `blog_classify` */

CREATE TABLE `blog_classify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classify_name` varchar(30) NOT NULL COMMENT '分类名',
  `status` int(3) NOT NULL COMMENT '状态',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `blog_classify` */

insert  into `blog_classify`(`id`,`classify_name`,`status`,`remark`) values (1,'随笔',1,NULL);
insert  into `blog_classify`(`id`,`classify_name`,`status`,`remark`) values (2,'Java',1,NULL);

/*Table structure for table `blog_liuyan` */

CREATE TABLE `blog_liuyan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(100) NOT NULL COMMENT '内容',
  `reply_type` int(11) DEFAULT NULL COMMENT '回复类型',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父类id',
  `reply_id` int(11) NOT NULL COMMENT '文章id',
  `status` int(3) NOT NULL DEFAULT '1' COMMENT '状态',
  `liuyan_date` datetime DEFAULT NULL COMMENT '留言时间',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `blog_liuyan` */

/*Table structure for table `blog_power` */

CREATE TABLE `blog_power` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `power_name` varchar(30) NOT NULL COMMENT '权限名',
  `open` int(3) NOT NULL COMMENT '开关',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `blog_power` */

/*Table structure for table `blog_title` */

CREATE TABLE `blog_title` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) NOT NULL COMMENT '标题',
  `classify_id` int(11) NOT NULL DEFAULT '0' COMMENT '标题内容id',
  `img` varchar(30) DEFAULT NULL COMMENT '预览图',
  `outline` varchar(3000) DEFAULT NULL COMMENT '文章内容',
  `author` int(11) NOT NULL COMMENT '作者id',
  `status` int(3) NOT NULL COMMENT '状态',
  `publish_date` datetime DEFAULT NULL COMMENT '发表时间',
  `update_date` datetime DEFAULT NULL COMMENT '最近修改时间',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `blog_title` */

/*Table structure for table `blog_title_detail` */

CREATE TABLE `blog_title_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) NOT NULL COMMENT '内容',
  `status` int(3) NOT NULL COMMENT '状态',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `blog_title_detail` */

/*Table structure for table `blog_user` */

CREATE TABLE `blog_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) DEFAULT NULL,
  `nick_name` varchar(30) NOT NULL COMMENT '昵称',
  `user_name` varchar(30) NOT NULL COMMENT '邮箱',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `user_info_id` int(11) DEFAULT '0' COMMENT '资料表id',
  `head_Image` varchar(50) DEFAULT NULL COMMENT '头像',
  `power` int(3) NOT NULL COMMENT '权限',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `up_time` datetime DEFAULT NULL COMMENT '最近上线时间',
  `status` int(3) NOT NULL COMMENT '状态',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `blog_user` */

/*Table structure for table `blog_user_info` */

CREATE TABLE `blog_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `status` int(3) DEFAULT NULL COMMENT '状态',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `blog_user_info` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
