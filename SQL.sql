/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.4.24-MariaDB : Database - medicine_delivery_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`medicine_delivery_system` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `medicine_delivery_system`;

/*Table structure for table `appoinments` */

DROP TABLE IF EXISTS `appoinments`;

CREATE TABLE `appoinments` (
  `appoinment_id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`appoinment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `appoinments` */

insert  into `appoinments`(`appoinment_id`,`doctor_id`,`user_id`,`date`,`time`,`status`) values (1,1,1,'12/2/23','4.00 AM','pending'),(2,2,1,'12-2-23','4.00','pending');

/*Table structure for table `delivery` */

DROP TABLE IF EXISTS `delivery`;

CREATE TABLE `delivery` (
  `delivery_id` int(11) NOT NULL AUTO_INCREMENT,
  `prescription_id` int(11) DEFAULT NULL,
  `boy_id` int(11) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`delivery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `delivery` */

insert  into `delivery`(`delivery_id`,`prescription_id`,`boy_id`,`date`) values (1,1,1,'2022-02-18 20:17:47'),(3,2,2,'2022-03-17 08:56:18'),(4,4,2,'2023-02-08 08:59:10');

/*Table structure for table `deliveryboy` */

DROP TABLE IF EXISTS `deliveryboy`;

CREATE TABLE `deliveryboy` (
  `boy_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`boy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `deliveryboy` */

insert  into `deliveryboy`(`boy_id`,`login_id`,`firstname`,`lastname`,`phone`,`email`) values (1,1,'dboy','dboy','234567','j@gmail.com'),(2,8,'Ddd','dddd','80976433222','dfgg@gmail.com');

/*Table structure for table `doctors` */

DROP TABLE IF EXISTS `doctors`;

CREATE TABLE `doctors` (
  `doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `doctors` */

insert  into `doctors`(`doctor_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`) values (1,10,'sds','dfd','fdf','8136840879','gfdg@f'),(2,11,'sara','mary','kochi','9870987890','sara@gmail.com');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `feedback` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`feedback_id`,`user_id`,`feedback`,`date`) values (1,1,'hlo','2-2-2022'),(2,2,'gggg','2022-03-16'),(3,2,'kkk','2022-03-16'),(4,1,'good','2023-02-08');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values (1,'dboy','dboy','delivery'),(3,'shopshop','shopshop','mshop'),(4,'admin','admin','admin'),(5,'shop2shop2','shop2shop2','mshop'),(6,'rtyuuiirrtyuu','sdfghjkdfg','mshop'),(7,'user','user','user'),(8,'ddd','ddd','delivery'),(9,'anu','anu','user'),(10,'d1','d1','doctor'),(11,'sara','sara123','pending'),(12,'s1','ss1','shop'),(13,'s2','ss2','shop'),(14,'shopshop12','shopshop12','mshop');

/*Table structure for table `medicalshop` */

DROP TABLE IF EXISTS `medicalshop`;

CREATE TABLE `medicalshop` (
  `medicalshop_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `shopname` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`medicalshop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `medicalshop` */

insert  into `medicalshop`(`medicalshop_id`,`login_id`,`shopname`,`place`,`city`,`district`,`email`,`phone`) values (1,3,'shop','ertyui','3456789','fhfh','s@gamil.com','s@gamil.com'),(2,12,'s1','edrfr','fgfyu','gyhguyg','s@TFGT','5765876'),(3,13,'s3','ugfyu','frtyrt','tyutu','rtyrf@fythf','564565465'),(4,14,'s3','kochi','calicut','ERANAKULAM','marysara@gmail.com','9812345678');

/*Table structure for table `medicinedetails` */

DROP TABLE IF EXISTS `medicinedetails`;

CREATE TABLE `medicinedetails` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `prescription_id` int(11) DEFAULT NULL,
  `medicine_id` int(11) DEFAULT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  `total` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `medicinedetails` */

insert  into `medicinedetails`(`detail_id`,`prescription_id`,`medicine_id`,`quantity`,`rate`,`total`) values (2,1,3,'4','100','400'),(3,2,3,'2','100','200'),(4,4,3,'5','100','500');

/*Table structure for table `medicines` */

DROP TABLE IF EXISTS `medicines`;

CREATE TABLE `medicines` (
  `medicine_id` int(11) NOT NULL AUTO_INCREMENT,
  `medicalshop_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `medicine` varchar(50) DEFAULT NULL,
  `details` varchar(50) DEFAULT NULL,
  `rate` varchar(50) DEFAULT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  `mdate` varchar(50) DEFAULT NULL,
  `edate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `medicines` */

insert  into `medicines`(`medicine_id`,`medicalshop_id`,`type_id`,`medicine`,`details`,`rate`,`quantity`,`mdate`,`edate`) values (2,1,1,'med1','asdfghj','10','500',NULL,NULL),(3,1,3,'med1','asdfghj0','100','4993',NULL,NULL),(5,1,3,'mm','eeeeeeee','55','8','2022-05-05','2022-05-31');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `prescription_id` int(11) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`prescription_id`,`amount`,`date`) values (1,2,'200','2022-03-16'),(2,4,'500','2023-02-08');

/*Table structure for table `prescription` */

DROP TABLE IF EXISTS `prescription`;

CREATE TABLE `prescription` (
  `pres_id` int(11) NOT NULL AUTO_INCREMENT,
  `appoinment_id` int(11) DEFAULT NULL,
  `file` varchar(1000) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pres_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `prescription` */

insert  into `prescription`(`pres_id`,`appoinment_id`,`file`,`date`,`status`) values (1,1,'static/c0e41b6b-a424-4e74-927e-57d490eef2d8abc.jpg','2023-02-08','pending'),(2,1,'static/b8ccc1d3-7b87-4de2-a67d-62bc7c4e18c6abc.jpg','2023-02-08','pending');

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `boy_id` int(11) DEFAULT NULL,
  `rated` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `rating` */

insert  into `rating`(`rating_id`,`user_id`,`boy_id`,`rated`,`date`) values (1,1,1,'4','2-2-2021'),(2,2,2,'4.5','2022-03-16');

/*Table structure for table `type` */

DROP TABLE IF EXISTS `type`;

CREATE TABLE `type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `type` */

insert  into `type`(`type_id`,`type`) values (1,'type1'),(3,'type2');

/*Table structure for table `uploadprescription` */

DROP TABLE IF EXISTS `uploadprescription`;

CREATE TABLE `uploadprescription` (
  `prescription_id` int(11) NOT NULL AUTO_INCREMENT,
  `medicalshop_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `uploadfile` varchar(50) DEFAULT NULL,
  `totalamount` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`prescription_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `uploadprescription` */

insert  into `uploadprescription`(`prescription_id`,`medicalshop_id`,`user_id`,`uploadfile`,`totalamount`,`date`,`status`) values (1,1,1,NULL,'400',NULL,'pending'),(2,1,2,'static/f364ebe5-3a24-407a-9e7a-e027972a345cabc.jpg','200','2022-03-16','delivered'),(3,1,1,'static/aa5e26d3-8f5e-4abc-bb29-e98b59ab4a98abc.jpg','','2022-03-16','pending'),(4,1,1,'static/e836add8-5ab0-46b7-b257-51a222b329adabc.jpg','500','2023-02-08','delivered'),(6,1,1,'static/c0e41b6b-a424-4e74-927e-57d490eef2d8abc.jpg','0','2023-02-10','pending');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `dob` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `district` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`firstname`,`lastname`,`dob`,`phone`,`email`,`place`,`district`) values (1,7,'jif','rt','rtyu','8136840879','ertyu','sdfgh','h'),(2,9,'Anu','anu','16-03-2000','8136840879','fhh@gmail.com','78965432190','alza');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
