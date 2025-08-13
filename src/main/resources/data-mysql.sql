CREATE DATABASE  IF NOT EXISTS `db_bar_backend`;
USE `db_bar_backend`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `invoice_detail`;
DROP TABLE IF EXISTS `stock`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `invoice`;
DROP TABLE IF EXISTS `inventory_location`;
DROP TABLE IF EXISTS `category`;

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  `category_status` enum('BUSY','DISABLED','ENABLED') DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `category` WRITE;
INSERT INTO `category` VALUES (1,'BEER','ENABLED'),(2,'LIQUOR','ENABLED');
UNLOCK TABLES;


DROP TABLE IF EXISTS `inventory_location`;

CREATE TABLE `inventory_location` (
  `location_id` int NOT NULL AUTO_INCREMENT,
  `location_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `inventory_location` WRITE;
INSERT INTO `inventory_location` VALUES (1,'WAREHOUSE'),(2,'RECEPTION');
UNLOCK TABLES;


DROP TABLE IF EXISTS `invoice`;

CREATE TABLE `invoice` (
  `invoice_id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `total` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`invoice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `invoice` WRITE;
INSERT INTO `invoice` VALUES (1,'2025-07-22 16:35:19.020311',24000.00),(2,'2025-07-22 17:34:53.013390',48000.00),(3,'2025-07-22 17:35:37.196702',48000.00),(4,'2025-07-23 10:15:48.483470',48000.00),(5,'2025-08-13 12:34:53.644422',24000.00);
UNLOCK TABLES;





DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `product_price` decimal(38,2) DEFAULT NULL,
  `product_status` enum('BUSY','DISABLED','ENABLED') DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `product` WRITE;
INSERT INTO `product` VALUES (1,'POKER',6000.00,'ENABLED',1),(2,'CLUB',6000.00,'ENABLED',1),(3,'AGUILA',6000.00,'ENABLED',1),(4,'CORONA',10000.00,'ENABLED',1);
UNLOCK TABLES;





DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  `min_quantity` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5oh31rty3peepqympuol89t51` (`location_id`),
  KEY `FKjghkvw2snnsr5gpct0of7xfcf` (`product_id`),
  CONSTRAINT `FK5oh31rty3peepqympuol89t51` FOREIGN KEY (`location_id`) REFERENCES `inventory_location` (`location_id`),
  CONSTRAINT `FKjghkvw2snnsr5gpct0of7xfcf` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


LOCK TABLES `stock` WRITE;
INSERT INTO `stock` VALUES (1,'2025-07-22 16:26:48','2025-07-22 16:37:14',10,48,1,1),(2,'2025-07-22 16:27:06','2025-08-13 12:34:54',10,7,2,1),(3,'2025-07-22 16:27:54','2025-07-22 16:37:19',10,48,1,2),(4,'2025-07-22 16:28:10','2025-08-13 12:34:54',10,17,2,2);
UNLOCK TABLES;

DROP TABLE IF EXISTS `invoice_detail`;

CREATE TABLE `invoice_detail` (
  `invoice_detail_id` bigint NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity_sold` int NOT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `unit_price` decimal(38,2) DEFAULT NULL,
  `invoice_id` bigint DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`invoice_detail_id`),
  KEY `FKit1rbx4thcr6gx6bm3gxub3y4` (`invoice_id`),
  KEY `FKbe6c21nke5fy4m3vw00f23qsf` (`product_id`),
  CONSTRAINT `FKbe6c21nke5fy4m3vw00f23qsf` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKit1rbx4thcr6gx6bm3gxub3y4` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `invoice_detail` WRITE;
INSERT INTO `invoice_detail` VALUES (1,'POKER',2,12000.00,6000.00,1,1),(2,'CLUB',2,12000.00,6000.00,1,2),(3,'POKER',3,18000.00,6000.00,2,1),(4,'CLUB',5,30000.00,6000.00,2,2),(5,'POKER',3,18000.00,6000.00,3,1),(6,'POKER',5,30000.00,6000.00,3,1),(7,'POKER',7,42000.00,6000.00,4,1),(8,'POKER',1,6000.00,6000.00,4,1),(9,'POKER',2,12000.00,6000.00,5,1),(10,'CLUB',2,12000.00,6000.00,5,2);
UNLOCK TABLES;


DROP PROCEDURE IF EXISTS get_product_sales_report; 

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_product_sales_report`(
    IN in_product_name VARCHAR(100),
    IN in_start_date   DATE,
    IN in_end_date     DATE
)
BEGIN
    SELECT  p.product_name AS productName,
            SUM(d.quantity_sold) AS totalQuantitySold,
            SUM(d.total_price)   AS totalRevenue
    FROM    invoice_detail d
    JOIN    invoice i ON i.invoice_id = d.invoice_id
    JOIN    product p ON p.product_id = d.product_id
    WHERE   p.product_name = in_product_name
      AND   DATE(i.date) BETWEEN in_start_date AND in_end_date   -- <‑‑ convertir i.date a DATE
    GROUP BY p.product_name;
END ;;
DELIMITER ;
