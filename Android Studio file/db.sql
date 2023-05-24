/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - charity app
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`charity app` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `charity app`;

/*Table structure for table `charity organisation` */

DROP TABLE IF EXISTS `charity organisation`;

CREATE TABLE `charity organisation` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Lid` int(11) DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `Place` varchar(100) DEFAULT NULL,
  `post` varchar(100) DEFAULT NULL,
  `pin` bigint(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `Phone` bigint(20) DEFAULT NULL,
  `Latitude` varchar(100) DEFAULT NULL,
  `Longitude` varchar(100) DEFAULT NULL,
  `proof` varchar(100) DEFAULT NULL,
  `Reg_date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `charity organisation` */

insert  into `charity organisation`(`Id`,`Lid`,`Name`,`Place`,`post`,`pin`,`email`,`Phone`,`Latitude`,`Longitude`,`proof`,`Reg_date`) values 
(1,2,'ssss','wwwewe',NULL,NULL,NULL,NULL,NULL,NULL,'IMG_5413.png',NULL),
(3,4,'ansadmk','parambilpeedika','kozikode',676317,'ansadmk2@gmail.com',12356789,'2312423434','2321332','pending','2023-01-05'),
(4,29,'ansadmk','kozikode','kozikode',676317,'ansadmk2@gmail.com',12356789,'2312423434','2321332','pending','2023-03-09'),
(5,31,'charity','kkk','kozikode',676317,'ans@gmail.com',12356789,'11.257667','75.784523','pending','2023-03-20'),
(6,33,'musthafa','chammad','kozikode',676317,'ansadmk2@gmail.com',12356789,'11.069086305891274','75.98769363885575','pending','2023-04-13');

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from id` int(11) DEFAULT NULL,
  `lo id` int(11) DEFAULT NULL,
  `chat` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

insert  into `chat`(`id`,`from id`,`lo id`,`chat`,`date`) values 
(1,27,2,'hi','2023-03-07'),
(2,27,3,'hello','2023-03-07'),
(3,27,4,'hiii','2023-03-07'),
(4,2,27,'hello','2023-03-07'),
(5,27,2,'hey','2023-03-07'),
(6,2,27,'hi ansad','2023-03-07'),
(7,27,3,'hi','2023-03-07'),
(8,2,28,'hi','2023-03-09'),
(9,2,28,'hilo','2023-03-09'),
(10,28,4,'hi','2023-03-20'),
(11,28,29,'hi','2023-04-08'),
(12,28,31,'hello','2023-04-08'),
(13,28,2,'hey','2023-04-08');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`cid`,`lid`,`complaint`,`date`,`reply`) values 
(1,1,'gyftds','2022-12-06','ok'),
(2,2,'gyftds','2022-12-06','ddd'),
(3,1,'jnjijji','2022-12-06','pending'),
(4,0,NULL,NULL,NULL),
(5,2,'charity','2022-12-20','pending'),
(6,2,'helo','2022-12-20','hi'),
(7,2,'fraud','2022-12-21','pending'),
(8,2,'hi','2023-01-05','pending'),
(9,0,'djhfgwefyg','2023-02-11','pending'),
(10,0,'qdefd','2023-02-11','pending'),
(11,0,'hi','2023-03-07','pending'),
(12,27,'hello','2023-03-07','pending'),
(13,27,'sdd','2023-03-07','pending'),
(14,27,'rtntmgkgmd','2023-03-07','pending'),
(15,28,'','2023-03-09','pending'),
(16,2,'trouble','2023-04-07','pending'),
(17,28,'bla bla','2023-04-08','pending'),
(18,33,'charity','2023-04-13','pending'),
(19,28,'no safety','2023-04-13','pending');

/*Table structure for table `donation` */

DROP TABLE IF EXISTS `donation`;

CREATE TABLE `donation` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Lid` int(11) DEFAULT NULL,
  `Req id` int(11) DEFAULT NULL,
  `Date` varchar(100) DEFAULT NULL,
  `Donation` varchar(100) DEFAULT NULL,
  `Status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `donation` */

insert  into `donation`(`Id`,`Lid`,`Req id`,`Date`,`Donation`,`Status`) values 
(1,1,1,NULL,'belt','pending');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(100) DEFAULT NULL,
  `Password` varchar(100) DEFAULT NULL,
  `Type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`lid`,`Username`,`Password`,`Type`) values 
(0,'','',''),
(1,'admin','12345','admin'),
(2,'charity','123456','charity'),
(28,'ansad','123','user'),
(31,'cha','cha','pending'),
(32,'testentry','1','user'),
(33,'musth','1234','charity');

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Lid` int(11) DEFAULT NULL,
  `Details` varchar(100) DEFAULT NULL,
  `Date` varchar(100) DEFAULT NULL,
  `Status` varchar(100) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `post` */

insert  into `post`(`Id`,`Lid`,`Details`,`Date`,`Status`,`image`) values 
(1,28,'belt','2023-03-20','pending','storage_emulated_0_Android_media_com.whatsapp_WhatsApp_Media_WhatsApp_Images_IMG-20230319-WA0070.jpg'),
(2,28,'shoe','2023-04-13','pending','storage_emulated_0_Android_media_com.whatsapp_WhatsApp_Media_WhatsApp_Images_IMG-20230413-WA0053.jpg');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oid` int(11) DEFAULT NULL,
  `request` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`id`,`oid`,`request`,`date`,`status`) values 
(1,2,'lkjhgvfc','2022-12-20','Accepted'),
(2,2,'food','2022-12-20','Accepted'),
(3,2,'hi','2022-12-20','Accepted'),
(4,2,'bag','2022-12-21','pending'),
(5,2,'box','2023-01-05','hi'),
(6,2,'shoes','2023-04-08','pending');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `Fname` varchar(100) DEFAULT NULL,
  `Lname` varchar(100) DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `post` varchar(100) DEFAULT NULL,
  `pin` bigint(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`id`,`lid`,`Fname`,`Lname`,`gender`,`place`,`post`,`pin`,`email`,`phone`) values 
(1,1,'muthu','ashraf','male','kozhikode','kozhikode',654321,'ANSADMK@GMAIL.COM',12345678),
(2,27,'GDEYGEWFGE','wkjhfwfgre','Male','fbwfhuirf','dnfbrfhriuf',2345,'fvreg',234567889),
(3,28,'ansad','mk','Male','pp','kozhikode',123456,'ansadmk@gmail.com',1234567890),
(4,32,'mohemed ansad','mk','Male','parambil peedika ','parambil peedika ',676317,'test@last.com',9207513163);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
