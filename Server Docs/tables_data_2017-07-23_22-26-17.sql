-- MySQL dump 10.14  Distrib 5.5.52-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: bnet_data
-- ------------------------------------------------------
-- Server version	5.5.52-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `bnet_data`
--


--
-- Table structure for table `account_table`
--

DROP TABLE IF EXISTS `account_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_table` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=hebrew;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_table`
--

LOCK TABLES `account_table` WRITE;
/*!40000 ALTER TABLE `account_table` DISABLE KEYS */;
INSERT INTO `account_table` VALUES ('Admin','d033e22ae348aeb5660fc2140aec35850c4da997'),('Aviyay','92ea89ddcc5f936899b635e6353a6c27b479180c'),('shy71','7c4a8d09ca3762af61e59520943dc26494f8941b');
/*!40000 ALTER TABLE `account_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_table`
--

DROP TABLE IF EXISTS `activity_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_table` (
  `_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `price` varchar(45) NOT NULL,
  `start` varchar(45) NOT NULL,
  `end` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `business_id` bigint(11) NOT NULL,
  `changed` tinyint(1) NOT NULL,
  PRIMARY KEY (`_id`),
  KEY `activity_table_ibfk_1` (`business_id`),
  CONSTRAINT `activity_table_ibfk_1` FOREIGN KEY (`business_id`) REFERENCES `business_table` (`_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=hebrew;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_table`
--

LOCK TABLES `activity_table` WRITE;
/*!40000 ALTER TABLE `activity_table` DISABLE KEYS */;
INSERT INTO `activity_table` VALUES (1,'Travel in comfort on our NY escorted tours','USA','150','08:00 15/02/2016','08:00 29/02/2016','TRAVEL',3,0),(2,'Enjoy your time in NY, with Tours USA.','USA','150','12:00 19/03/2016','08:00 29/03/2016','TRAVEL',3,0),(6,'come to visit in Machon Lev, the great school','Israel','80.0','06:45 08/01/2017','08:20 21/02/2017','TRAVEL',13,0),(9,'Great Pepole, great views','Turkey','130.0','08:00 02/02/2017','06:25 23/03/2017','TRAVEL',1,0),(10,'The holy land, before your hands','Israel','70.0','08:00 15/02/2017','08:00 31/03/2017','TRAVEL',2,0),(11,'USA like you have never seen.','USA','900.0','08:00 16/02/2017','08:00 22/03/2017','TRAVEL',4,0);
/*!40000 ALTER TABLE `activity_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_table`
--

DROP TABLE IF EXISTS `business_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `business_table` (
  `_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `link` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `street` varchar(45) NOT NULL,
  `changed` tinyint(1) NOT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=hebrew;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_table`
--

LOCK TABLES `business_table` WRITE;
/*!40000 ALTER TABLE `business_table` DISABLE KEYS */;
INSERT INTO `business_table` VALUES (1,'Turkish Trips','turkeytrips@gmail.com','0506502644','www.turkishairlines.com','Turkey','Antalia','Main Street',0),(2,'Israel Trips','israeltrips@gmail.com','0503762644','https://www.israel.com/','Israel','Jerusalem','Sdarot Herzl',0),(3,'Tours Usa','tours.usa@gmail.com','0585205020','http://www.tours-usa.com/','Usa','New York','Fifth Avenue 5',0),(4,'Contiki','contact@Contiki.com','0585205020','www.contiki.com','Usa','Washington','1600 Pennsylvania Ave',0),(13,'Machon Lev','help@g.jct.ac.il','052-555-3322','www.jct.com','Israel','Jerusalem','Havad Haleumi',0);
/*!40000 ALTER TABLE `business_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-23 22:26:34

