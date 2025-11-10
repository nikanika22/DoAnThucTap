-- --------------------------------------------------------
-- Máy chủ:                      127.0.0.1
-- Phiên bản máy chủ:            8.0.43 - MySQL Community Server - GPL
-- HĐH máy chủ:                  Linux
-- HeidiSQL Phiên bản:           12.12.0.7122
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Đang kết xuất đổ cấu trúc cơ sở dữ liệu cho cinema_service
CREATE DATABASE IF NOT EXISTS `cinema_service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cinema_service`;

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.account
CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `role` enum('CUSTOMER','STAFF','ADMIN') NOT NULL DEFAULT 'CUSTOMER',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.account: ~11 rows (xấp xỉ)
INSERT INTO `account` (`id`, `email`, `phone`, `password_hash`, `full_name`, `role`, `is_active`) VALUES
	(1, 'admin@cinema.com', '0123456789', '$2y$12$YXd/SzkPt59xTxd2cKvcx.4XksrLlbVFB.UoWyK.UJ.kJRw7X.mD6', 'Admin User', 'ADMIN', 1),
	(2, 'vnitzsche@example.com', '1-980-817-5230', '$2y$12$IUMIWTxv2AS.SHJL2uef7uAjHkCXbApEKNmCcf4B0DTW4ZtJd4JW6', 'Dr. Destin Daniel', 'CUSTOMER', 1),
	(3, 'kelly61@example.com', '870-559-2538', '$2y$12$zSOSn2nHVvSjGZ5N6vFVV.PXy2ss6oLmxeyhvAlE5h4pQviq9wQWC', 'Prof. Seamus Kiehn III', 'STAFF', 1),
	(4, 'stephania.stanton@example.net', '+1 (915) 708-8231', '$2y$12$u6czm1DG29J4AoIknTQhXeKROIqJpkJQI46Rx6iSDlABH6AfBKJ5i', 'Mrs. Bridgette Keeling MD', 'STAFF', 1),
	(5, 'serenity92@example.com', '+1 (430) 850-5993', '$2y$12$W5jDl8rHLHd7bk1RCvj9a.hFq6RoIJolTnqa9NAn2B.0lnKmsZxg2', 'Prof. Eldred Pollich Sr.', 'STAFF', 1),
	(6, 'roy.harber@example.net', '+1 (562) 331-5763', '$2y$12$ROGgGiZsIFbtaWzF6n8G..LwhPCRhBjvsuIELvmyW8KDLH5mk8AVW', 'Alf Rowe', 'CUSTOMER', 1),
	(7, 'vpowlowski@example.com', '+1.667.261.9308', '$2y$12$rYlmpYtr1TvN.GjlMQbfqOsh.jcQirGntgoPJhwNCQtB1HDIN7EaW', 'Camron Kertzmann', 'STAFF', 1),
	(8, 'kmaggio@example.com', '+1 (651) 664-9367', '$2y$12$5bQBqL5R5uSoFTYkXFo18uDGt/ue6YCJuExpGwn.Ogb1be3v2WzYS', 'Noelia Stoltenberg', 'CUSTOMER', 1),
	(9, 'eugenia.jacobi@example.org', '(681) 293-1676', '$2y$12$pxIT62vV.oorTaGJKUjqq.QxNWmaMJ7JMvWDmovsbMqtfLEVV7gTW', 'Monserrat Hartmann', 'STAFF', 0),
	(10, 'jayson45@example.net', '+1-775-763-2174', '$2y$12$aMX23eiSFapwW7V0k/hu.eUHkrWj19Ibj/iH4RUP/BoLZ3q447equ', 'Wendell Wisozk IV', 'STAFF', 1),
	(11, 'schulist.albertha@example.net', '(401) 549-3201', '$2y$12$vRwkZWrgF9nU7J26Uj72aeNFwqiCIR65zyD.VopO8/tulP5u/gTKC', 'Michel Jerde', 'STAFF', 0),
	(12, 'user1@example.com', '0123456788', '$2y$12$e2ZwRjEF8mZk95mv8TTkseMqnRUBwsePxOQtCslf1VnwpYIpeR.Za', 'Nguyen Van A', 'CUSTOMER', 1);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.cache
CREATE TABLE IF NOT EXISTS `cache` (
  `key` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  `expiration` int NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.cache: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.cache_locks
CREATE TABLE IF NOT EXISTS `cache_locks` (
  `key` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `expiration` int NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.cache_locks: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.failed_jobs
CREATE TABLE IF NOT EXISTS `failed_jobs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) NOT NULL,
  `connection` text NOT NULL,
  `queue` text NOT NULL,
  `payload` longtext NOT NULL,
  `exception` longtext NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.failed_jobs: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.jobs
CREATE TABLE IF NOT EXISTS `jobs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `queue` varchar(255) NOT NULL,
  `payload` longtext NOT NULL,
  `attempts` tinyint unsigned NOT NULL,
  `reserved_at` int unsigned DEFAULT NULL,
  `available_at` int unsigned NOT NULL,
  `created_at` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `jobs_queue_index` (`queue`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.jobs: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.job_batches
CREATE TABLE IF NOT EXISTS `job_batches` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `total_jobs` int NOT NULL,
  `pending_jobs` int NOT NULL,
  `failed_jobs` int NOT NULL,
  `failed_job_ids` longtext NOT NULL,
  `options` mediumtext,
  `cancelled_at` int DEFAULT NULL,
  `created_at` int NOT NULL,
  `finished_at` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.job_batches: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.migrations
CREATE TABLE IF NOT EXISTS `migrations` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) NOT NULL,
  `batch` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.migrations: ~18 rows (xấp xỉ)
INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
	(1, '0001_01_01_000000_create_users_table', 1),
	(2, '0001_01_01_000001_create_cache_table', 1),
	(3, '0001_01_01_000002_create_jobs_table', 1),
	(4, '2025_11_06_143152_create_account_table', 1),
	(5, '2025_11_06_143152_create_movie_table', 1),
	(6, '2025_11_06_143152_create_order_line_table', 1),
	(7, '2025_11_06_143152_create_orders_table', 1),
	(8, '2025_11_06_143152_create_product_table', 1),
	(9, '2025_11_06_143152_create_screen_table', 1),
	(10, '2025_11_06_143152_create_seat_lock_table', 1),
	(11, '2025_11_06_143152_create_seat_table', 1),
	(12, '2025_11_06_143152_create_showtime_table', 1),
	(13, '2025_11_06_143155_add_foreign_keys_to_order_line_table', 1),
	(14, '2025_11_06_143155_add_foreign_keys_to_orders_table', 1),
	(15, '2025_11_06_143155_add_foreign_keys_to_seat_lock_table', 1),
	(16, '2025_11_06_143155_add_foreign_keys_to_seat_table', 1),
	(17, '2025_11_06_143155_add_foreign_keys_to_showtime_table', 1),
	(18, '2025_11_06_144023_create_personal_access_tokens_table', 1);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.movie
CREATE TABLE IF NOT EXISTS `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `duration_min` smallint NOT NULL,
  `rating_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `movie_title_index` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.movie: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `channel` enum('WEB','POS') NOT NULL,
  `account_id` bigint DEFAULT NULL,
  `cashier_id` bigint DEFAULT NULL,
  `showtime_id` bigint NOT NULL,
  `status` enum('INIT','PAID','CANCELLED') NOT NULL DEFAULT 'INIT',
  `payment_method` enum('CASH','CARD','EWALLET') DEFAULT NULL,
  `total_amount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `orders_showtime_id_status_index` (`showtime_id`,`status`),
  KEY `orders_channel_index` (`channel`),
  KEY `orders_account_id_index` (`account_id`),
  KEY `orders_cashier_id_index` (`cashier_id`),
  CONSTRAINT `orders_account_id_foreign` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `orders_cashier_id_foreign` FOREIGN KEY (`cashier_id`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `orders_showtime_id_foreign` FOREIGN KEY (`showtime_id`) REFERENCES `showtime` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.orders: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.order_line
CREATE TABLE IF NOT EXISTS `order_line` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `item_type` enum('TICKET','PRODUCT') NOT NULL,
  `seat_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `qty` int NOT NULL DEFAULT '1',
  `unit_price` int NOT NULL,
  `line_total` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_line_order_id_item_type_index` (`order_id`,`item_type`),
  KEY `order_line_seat_id_index` (`seat_id`),
  KEY `order_line_product_id_index` (`product_id`),
  CONSTRAINT `order_line_order_id_foreign` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_line_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `order_line_seat_id_foreign` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.order_line: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.password_reset_tokens
CREATE TABLE IF NOT EXISTS `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.password_reset_tokens: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.personal_access_tokens
CREATE TABLE IF NOT EXISTS `personal_access_tokens` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tokenable_type` varchar(255) NOT NULL,
  `tokenable_id` bigint unsigned NOT NULL,
  `name` text NOT NULL,
  `token` varchar(64) NOT NULL,
  `abilities` text,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`),
  KEY `personal_access_tokens_expires_at_index` (`expires_at`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.personal_access_tokens: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` int NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.product: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.screen
CREATE TABLE IF NOT EXISTS `screen` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `format` varchar(20) DEFAULT '2D',
  `row_count` int NOT NULL,
  `col_count` int NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `screen_code_unique` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.screen: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.seat
CREATE TABLE IF NOT EXISTS `seat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `screen_id` bigint NOT NULL,
  `row_label` varchar(5) NOT NULL,
  `seat_number` int NOT NULL,
  `seat_type` enum('STANDARD','VIP','COUPLE','ACCESSIBLE') NOT NULL DEFAULT 'STANDARD',
  `is_aisle` tinyint(1) NOT NULL DEFAULT '0',
  `is_blocked` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `seat_screen_id_row_label_seat_number_unique` (`screen_id`,`row_label`,`seat_number`),
  KEY `seat_screen_id_index` (`screen_id`),
  CONSTRAINT `seat_screen_id_foreign` FOREIGN KEY (`screen_id`) REFERENCES `screen` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.seat: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.seat_lock
CREATE TABLE IF NOT EXISTS `seat_lock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `showtime_id` bigint NOT NULL,
  `seat_id` bigint NOT NULL,
  `account_id` bigint DEFAULT NULL,
  `expires_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `seat_lock_showtime_id_seat_id_unique` (`showtime_id`,`seat_id`),
  KEY `seat_lock_seat_id_index` (`seat_id`),
  KEY `seat_lock_account_id_index` (`account_id`),
  KEY `seat_lock_expires_at_index` (`expires_at`),
  CONSTRAINT `seat_lock_account_id_foreign` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `seat_lock_seat_id_foreign` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `seat_lock_showtime_id_foreign` FOREIGN KEY (`showtime_id`) REFERENCES `showtime` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.seat_lock: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.sessions
CREATE TABLE IF NOT EXISTS `sessions` (
  `id` varchar(255) NOT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text,
  `payload` longtext NOT NULL,
  `last_activity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sessions_user_id_index` (`user_id`),
  KEY `sessions_last_activity_index` (`last_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.sessions: ~1 rows (xấp xỉ)
INSERT INTO `sessions` (`id`, `user_id`, `ip_address`, `user_agent`, `payload`, `last_activity`) VALUES
	('zNWRigEYztC75HUP6N9FrRT3cOT3j0Kw1OXldCa7', NULL, '127.0.0.1', 'PostmanRuntime/7.50.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoid1lWcjBxWHNtbXpacll6MGxUQWpzZDM3WVBnSUVydEdDSVpjMFJ1ZCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MjE6Imh0dHA6Ly8xMjcuMC4wLjE6ODAwMCI7fXM6NjoiX2ZsYXNoIjthOjI6e3M6Mzoib2xkIjthOjA6e31zOjM6Im5ldyI7YTowOnt9fX0=', 1762510481);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service.showtime
CREATE TABLE IF NOT EXISTS `showtime` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `movie_id` bigint NOT NULL,
  `screen_id` bigint NOT NULL,
  `start_at` datetime NOT NULL,
  `end_at` datetime NOT NULL,
  `base_price` int NOT NULL,
  `status` enum('SCHEDULED','OPEN','CLOSED','CANCELLED') NOT NULL DEFAULT 'OPEN',
  PRIMARY KEY (`id`),
  UNIQUE KEY `showtime_screen_id_start_at_unique` (`screen_id`,`start_at`),
  KEY `showtime_movie_id_start_at_index` (`movie_id`,`start_at`),
  CONSTRAINT `showtime_movie_id_foreign` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `showtime_screen_id_foreign` FOREIGN KEY (`screen_id`) REFERENCES `screen` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service.showtime: ~0 rows (xấp xỉ)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
