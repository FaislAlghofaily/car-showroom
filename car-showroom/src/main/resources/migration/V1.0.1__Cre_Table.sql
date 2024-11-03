CREATE TABLE IF NOT EXISTS `showrooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `commercial_registration_number` bigint NOT NULL,
  `manager_name` varchar(100) DEFAULT NULL,
  `contact_number` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` enum('Active','Inactive') NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_crn` (`commercial_registration_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;



CREATE TABLE IF NOT EXISTS `cars` (
  `id` int NOT NULL AUTO_INCREMENT,
  `showroom_id` int NOT NULL,
  `vin` varchar(25) NOT NULL,
  `status` enum('Available','Sold','Unavailable') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'Available',
  `maker` varchar(25) NOT NULL,
  `model` varchar(25) NOT NULL,
  `model_year` int NOT NULL,
  `price` decimal(18,2) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `cars_fk_showrooms` (`showroom_id`),
  CONSTRAINT `cars_fk_showrooms` FOREIGN KEY (`showroom_id`) REFERENCES `showrooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;


CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

INSERT INTO users ( `full_name`, `email`, `password`, `created_date`, `updated_date`)
VALUES
( 'admin', 'admin@gmail.com', '$2a$10$FwqfreP0VN5gVi668ei34ODIqeTerR6QPe9sGYwFaxFjRCuN5S23m', SYSDATE(), SYSDATE());


INSERT INTO showrooms ( `name`, `commercial_registration_number`, `manager_name`, `contact_number`, `address`, `created_date`, `updated_date`)
VALUES
( 'Riyadh showroom', 1100423874, 'Mohammed Alqahtani', 5555144398, 'Riyadh - king abdulaziz street', SYSDATE(), SYSDATE()),
( 'Jeddah showroom', 4356382760, NULL, 5055134380, 'Jeddah - king Faisal street', SYSDATE(), SYSDATE()),
( 'Dammam showroom', 2924441898, 'Ahmad Alturki', 5525146368, NULL, SYSDATE(), SYSDATE());


INSERT INTO cars (`showroom_id`, `vin`, `status`, `maker`, `model`, `model_year`, `price`, `created_date`,`updated_date`)
VALUES
((SELECT id FROM showrooms WHERE commercial_registration_number = 1100423874), '124221411212211', 'Available', 'Toyota', 'Hilux', 2023, 99999.99, SYSDATE(), SYSDATE() ),
((SELECT id FROM showrooms WHERE commercial_registration_number = 1100423874), '414221459111223', 'Available', 'Toyota', 'Camry', 2020, 135000.99, SYSDATE(), SYSDATE() ),
((SELECT id FROM showrooms WHERE commercial_registration_number = 1100423874), '421555511224111', 'Available', 'Toyota', 'Corrola', 2022, 99999.99, SYSDATE(), SYSDATE() ),
((SELECT id FROM showrooms WHERE commercial_registration_number = 4356382760), '553222421244411', 'Available', 'Toyota', 'Hilux', 2023, 99999.99, SYSDATE(), SYSDATE() ),
((SELECT id FROM showrooms WHERE commercial_registration_number = 4356382760), '611224441112444', 'Available', 'Toyota', 'Camry', 2020, 135000.99, SYSDATE(), SYSDATE() ),
((SELECT id FROM showrooms WHERE commercial_registration_number = 4356382760), '241241112211146', 'Available', 'Toyota', 'Corrola', 2022, 99999.99, SYSDATE(), SYSDATE() ),
((SELECT id FROM showrooms WHERE commercial_registration_number = 2924441898), '441111245411231', 'Available', 'Toyota', 'Hilux', 2023, 99999.99, SYSDATE(), SYSDATE() ),
((SELECT id FROM showrooms WHERE commercial_registration_number = 2924441898), '414577864541321', 'Available', 'Toyota', 'Camry', 2020, 135000.99, SYSDATE(), SYSDATE() ),
((SELECT id FROM showrooms WHERE commercial_registration_number = 2924441898), '663257764324114', 'Available', 'Toyota', 'Corrola', 2022, 99999.99, SYSDATE(), SYSDATE() );


