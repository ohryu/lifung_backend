
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assignee_id` int(11) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `reporter_id` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsrodfgrekcvv8ksyslehr53j8` (`assignee_id`),
  KEY `FKkm56sdvq22y1ikcgao47pirs0` (`reporter_id`),
  CONSTRAINT `FKkm56sdvq22y1ikcgao47pirs0` FOREIGN KEY (`reporter_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKsrodfgrekcvv8ksyslehr53j8` FOREIGN KEY (`assignee_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

LOCK TABLES `task` WRITE;

INSERT INTO `task` VALUES (2,1,'2023-03-25 18:01:00','2023-02-25 17:43:52',4,1,'Task 1','2023-03-25 19:09:06'),(3,2,'2023-03-25 18:17:01','2023-04-25 17:43:52',4,0,'Task 5','2023-03-25 18:17:01'),(4,1,'2023-03-25 18:17:01','2023-04-25 17:43:52',4,0,'Task 2','2023-03-25 18:17:01'),(5,2,'2023-03-25 18:17:01','2023-04-25 17:43:52',4,0,'Task 3','2023-03-25 18:17:01'),(6,1,'2023-03-25 18:17:01','2023-04-25 17:43:52',4,1,'Task 4','2023-03-25 18:17:01'),(7,2,'2023-03-25 18:23:29','2023-04-25 17:43:52',4,0,'Task 6','2023-03-25 18:23:29'),(8,1,'2023-03-25 18:23:29','2023-04-25 17:43:52',4,0,'Task 7','2023-03-25 18:23:29');
UNLOCK TABLES;



DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'Tony'),(2,'May'),(4,'Admin');
UNLOCK TABLES;
