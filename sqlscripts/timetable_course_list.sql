-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: timetable
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course_list`
--

DROP TABLE IF EXISTS `course_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_list` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `day` tinyint NOT NULL,
  `begin` tinyint NOT NULL,
  `row` tinyint NOT NULL DEFAULT '2',
  `room_id` int DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  `total_num` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_list`
--

LOCK TABLES `course_list` WRITE;
/*!40000 ALTER TABLE `course_list` DISABLE KEYS */;
INSERT INTO `course_list` VALUES (1,'计算机网络',1,1,2,1,1001,0),(2,'数据库概论',1,2,2,1,1001,2),(3,'互联网金融概论',1,3,2,5,1002,0),(4,'软件需求与分析',1,4,2,3,1001,0),(5,'数值分析',2,1,2,3,1004,1),(6,'形势与政策',2,2,2,2,1005,1),(7,'就业指导I',2,3,2,2,1001,1),(8,'思修',2,4,2,2,1010,1),(9,'体育',3,5,2,4,1004,0),(10,'心理健康',3,1,2,1,1009,1),(11,'JAVA应用',3,2,2,3,1002,0),(12,'英语',3,3,2,8,1002,0),(13,'操作系统',4,4,2,1,1001,0),(14,'软件工程',4,5,2,1,1010,0),(15,'人工智能',4,1,2,3,1004,1),(16,'算法设计',4,2,2,3,1005,0),(17,'编译原理',5,1,2,1,1003,0),(18,'操作系统',5,2,2,1,1009,0),(19,'界面设计',5,3,2,7,1010,2),(20,'图像处理',5,2,2,1,1005,0);
/*!40000 ALTER TABLE `course_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-06 12:38:32
