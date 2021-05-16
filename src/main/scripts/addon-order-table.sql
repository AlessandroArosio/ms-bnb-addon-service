CREATE TABLE `addon_order` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `booking_uid` binary(255) NOT NULL,
                               `last_modified_date` datetime(6) DEFAULT NULL,
                               `notes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                               `order_date` datetime(6) DEFAULT NULL,
                               `paid` bit(1) NOT NULL,
                               `qty` smallint(6) NOT NULL,
                               `total_price` decimal(19,2) NOT NULL,
                               `uuid` binary(255) NOT NULL,
                               `version` bigint(20) DEFAULT NULL,
                               `addon_id` bigint(20) DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKlhjqijrwha0ocxkrys4i7ihqo` (`addon_id`),
                               CONSTRAINT `FKlhjqijrwha0ocxkrys4i7ihqo` FOREIGN KEY (`addon_id`) REFERENCES `addon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
