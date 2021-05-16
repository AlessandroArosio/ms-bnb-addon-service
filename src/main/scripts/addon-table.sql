CREATE TABLE `addon` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `category` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                         `price_per_unit` decimal(19,2) NOT NULL,
                         `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
