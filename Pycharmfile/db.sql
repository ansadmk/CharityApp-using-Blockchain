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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `charity organisation` */

insert  into `charity organisation`(`Id`,`Lid`,`Name`,`Place`,`post`,`pin`,`email`,`Phone`,`Latitude`,`Longitude`,`proof`,`Reg_date`) values 
(1,2,'ssss','wwwewe',NULL,NULL,NULL,NULL,NULL,NULL,'sdgejdgehj',NULL),
(2,3,'ansadmk','kozikod','koziko',676317,'ansadmk2@gmail.com',12356789,'2312423434','2321332','pending','2022-12-21'),
(3,4,'ansadmk','parambilpeedika','kozikode',676317,'ansadmk2@gmail.com',12356789,'2312423434','2321332','pending','2023-01-05');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
