-- --------------------------------------------------------
-- Máy chủ:                      127.0.0.1
-- Phiên bản máy chủ:            8.0.43 - MySQL Community Server - GPL
-- HĐH máy chủ:                  Linux
-- HeidiSQL Phiên bản:           12.13.0.7147
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Đang kết xuất đổ cấu trúc cơ sở dữ liệu cho cinema_service1
CREATE DATABASE IF NOT EXISTS `cinema_service1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cinema_service1`;

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.account
CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `google_id` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `role` enum('CUSTOMER','STAFF','ADMIN') NOT NULL DEFAULT 'CUSTOMER',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_email_unique` (`email`),
  UNIQUE KEY `account_google_id_unique` (`google_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.account: ~8 rows (xấp xỉ)
INSERT INTO `account` (`id`, `email`, `google_id`, `phone`, `password_hash`, `full_name`, `role`, `is_active`) VALUES
	(1, 'admin@cinema.com', NULL, '0901234567', '$2y$12$pABznrwRdf7ASzuL3x.V0Or4SBskbWoINNjTvEd119hzQ/HuzPhdO', 'Administrator', 'ADMIN', 1),
	(2, 'staff1@cinema.com', NULL, '0902345678', '$2y$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Nguyễn Văn A', 'STAFF', 1),
	(3, 'staff2@cinema.com', NULL, '0903456789', '$2y$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Trần Thị B', 'STAFF', 1),
	(4, 'customer1@gmail.com', NULL, '0904567890', '$2y$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Lê Văn C', 'CUSTOMER', 1),
	(5, 'customer2@gmail.com', NULL, '0905678901', '$2y$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Phạm Thị D', 'CUSTOMER', 1),
	(6, 'customer3@gmail.com', 'google_123456', '0906789012', NULL, 'Hoàng Văn E', 'CUSTOMER', 1),
	(7, 'customer4@gmail.com', 'google_789012', '0907890123', NULL, 'Vũ Thị F', 'CUSTOMER', 1),
	(8, 'customer5@gmail.com', NULL, '0908901234', '$2y$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Đặng Văn G', 'CUSTOMER', 1);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.cache
CREATE TABLE IF NOT EXISTS `cache` (
  `key` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  `expiration` int NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.cache: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.cache_locks
CREATE TABLE IF NOT EXISTS `cache_locks` (
  `key` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `expiration` int NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.cache_locks: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.failed_jobs
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

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.failed_jobs: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.job_batches
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

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.job_batches: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.jobs
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

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.jobs: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.migrations
CREATE TABLE IF NOT EXISTS `migrations` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) NOT NULL,
  `batch` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.migrations: ~21 rows (xấp xỉ)
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
	(18, '2025_11_06_144023_create_personal_access_tokens_table', 1),
	(19, '2025_11_18_061957_add_colums_poster_genre_into_table_movie', 1),
	(20, '2025_11_27_153742_add_is_active_to_movie_table', 1),
	(57, '2025_12_09_024545_add_google_id_to_account_table', 2);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.movie
CREATE TABLE IF NOT EXISTS `movie` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `duration_min` smallint NOT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `poster` varchar(255) DEFAULT NULL,
  `rating_code` varchar(10) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `movie_title_index` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.movie: ~15 rows (xấp xỉ)
INSERT INTO `movie` (`id`, `title`, `duration_min`, `genre`, `poster`, `rating_code`, `is_active`) VALUES
	(1, 'Avatar: The Way of Water', 192, 'Sci-Fi, Adventure', 'https://image.tmdb.org/t/p/w500/94xxm5701CzOdJdUEdIuwqZaowx.jpg', 'T13', 1),
	(2, 'Top Gun: Maverick', 131, 'Action, Drama', 'https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg', 'T13', 1),
	(3, 'Avengers: Endgame', 181, 'Action, Sci-Fi', 'https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg', 'T13', 1),
	(4, 'The Batman', 176, 'Action, Crime', 'https://image.tmdb.org/t/p/w500/74xTEgt7R36Fpooo50r9T25onhq.jpg', 'T16', 1),
	(5, 'Spider-Man: No Way Home', 148, 'Action, Adventure', 'https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg', 'T13', 1),
	(6, 'Frozen II', 103, 'Animation, Family', 'https://image.tmdb.org/t/p/w500/pjeMs3yqRmFL3giJy4PMXWZTTPa.jpg', 'P', 1),
	(7, 'Dune: Part Two', 166, 'Sci-Fi, Adventure', 'https://image.tmdb.org/t/p/w500/8b8R8l88Qje9dn9OE8PY05Nxl1X.jpg', 'T13', 1),
	(8, 'Oppenheimer', 180, 'Biography, Drama', 'https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg', 'T16', 1),
	(9, 'Barbie', 114, 'Comedy, Adventure', 'https://image.tmdb.org/t/p/w500/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg', 'T13', 1),
	(10, 'The Super Mario Bros. Movie', 92, 'Animation, Family', 'https://image.tmdb.org/t/p/w500/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg', 'P', 1),
	(11, 'Guardians of the Galaxy Vol. 3', 150, 'Action, Sci-Fi', 'https://image.tmdb.org/t/p/w500/r2J02Z2OpNTctfOSN1Ydgii51I3.jpg', 'T13', 1),
	(12, 'Fast X', 141, 'Action, Crime', 'https://image.tmdb.org/t/p/w500/fiVW06jE7z9YnO4trhaMEdclSiC.jpg', 'T16', 1),
	(13, 'The Little Mermaid', 135, 'Fantasy, Musical', 'https://image.tmdb.org/t/p/w500/ym1dxyOk4jFcSl4Q2zmRrA5BEEN.jpg', 'P', 1),
	(14, 'Mission: Impossible 7', 163, 'Action, Thriller', 'https://image.tmdb.org/t/p/w500/NNxYkU70HPurnNCSiCjYAmacwm.jpg', 'T13', 1),
	(15, 'Elemental', 109, 'Animation, Family', 'https://image.tmdb.org/t/p/w500/4Y1WNkd88JXmGfhtWR7dmDAo1T2.jpg', 'P', 1);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.order_line
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
  CONSTRAINT `order_line_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `order_line_seat_id_foreign` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.order_line: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.orders
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
  CONSTRAINT `orders_showtime_id_foreign` FOREIGN KEY (`showtime_id`) REFERENCES `showtime` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.orders: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.password_reset_tokens
CREATE TABLE IF NOT EXISTS `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.password_reset_tokens: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.personal_access_tokens
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
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.personal_access_tokens: ~2 rows (xấp xỉ)
INSERT INTO `personal_access_tokens` (`id`, `tokenable_type`, `tokenable_id`, `name`, `token`, `abilities`, `last_used_at`, `expires_at`, `created_at`, `updated_at`) VALUES
	(60, 'App\\Models\\Account', 14, 'admin', '504c85b217621d1426193134949522e95ea23a5c27afb190f81ce56019fb9f6d', '["*"]', '2025-12-09 10:08:03', NULL, '2025-12-09 10:00:54', '2025-12-09 10:08:03'),
	(61, 'App\\Models\\Account', 1, 'admin', '889d876b3b1b1dc88e7e0a426bd566aaf7e44d21c597214b95e7c1d2d80f1147', '["*"]', '2025-12-16 14:11:48', NULL, '2025-12-16 14:08:23', '2025-12-16 14:11:48');

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` int NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.product: ~10 rows (xấp xỉ)
INSERT INTO `product` (`id`, `name`, `price`, `is_active`) VALUES
	(1, 'Combo 1 (Bắp + Nước)', 80000, 1),
	(2, 'Combo 2 (Bắp lớn + 2 Nước)', 120000, 1),
	(3, 'Bắp rang bơ (M)', 50000, 1),
	(4, 'Bắp rang bơ (L)', 65000, 1),
	(5, 'Nước ngọt (M)', 35000, 1),
	(6, 'Nước ngọt (L)', 45000, 1),
	(7, 'Combo Couple (2 Bắp + 2 Nước)', 150000, 1),
	(8, 'Combo Gia đình (3 Bắp + 4 Nước)', 250000, 1),
	(9, 'Snack', 25000, 1),
	(10, 'Kẹo', 20000, 1);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.screen
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.screen: ~5 rows (xấp xỉ)
INSERT INTO `screen` (`id`, `code`, `name`, `format`, `row_count`, `col_count`, `is_active`) VALUES
	(1, 'S01', 'Screen 1', '2D', 8, 8, 1),
	(2, 'S02', 'Screen 2', '3D', 8, 8, 1),
	(3, 'S03', 'Screen 3', 'IMAX', 8, 8, 1),
	(4, 'S04', 'Screen 4', '2D', 8, 8, 1),
	(5, 'S05', 'Screen 5', '4DX', 8, 8, 1);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.seat
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
) ENGINE=InnoDB AUTO_INCREMENT=561 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.seat: ~320 rows (xấp xỉ)
INSERT INTO `seat` (`id`, `screen_id`, `row_label`, `seat_number`, `seat_type`, `is_aisle`, `is_blocked`) VALUES
	(241, 1, 'A', 1, 'STANDARD', 0, 0),
	(242, 1, 'A', 2, 'STANDARD', 0, 0),
	(243, 1, 'A', 3, 'STANDARD', 0, 0),
	(244, 1, 'A', 4, 'STANDARD', 0, 0),
	(245, 1, 'A', 5, 'STANDARD', 0, 0),
	(246, 1, 'A', 6, 'STANDARD', 0, 0),
	(247, 1, 'A', 7, 'STANDARD', 0, 0),
	(248, 1, 'A', 8, 'STANDARD', 0, 0),
	(249, 1, 'B', 1, 'STANDARD', 0, 0),
	(250, 1, 'B', 2, 'STANDARD', 0, 0),
	(251, 1, 'B', 3, 'STANDARD', 0, 0),
	(252, 1, 'B', 4, 'STANDARD', 0, 0),
	(253, 1, 'B', 5, 'STANDARD', 0, 0),
	(254, 1, 'B', 6, 'STANDARD', 0, 0),
	(255, 1, 'B', 7, 'STANDARD', 0, 0),
	(256, 1, 'B', 8, 'STANDARD', 0, 0),
	(257, 1, 'C', 1, 'STANDARD', 0, 0),
	(258, 1, 'C', 2, 'STANDARD', 0, 0),
	(259, 1, 'C', 3, 'STANDARD', 0, 0),
	(260, 1, 'C', 4, 'STANDARD', 0, 0),
	(261, 1, 'C', 5, 'STANDARD', 0, 0),
	(262, 1, 'C', 6, 'STANDARD', 0, 0),
	(263, 1, 'C', 7, 'STANDARD', 0, 0),
	(264, 1, 'C', 8, 'STANDARD', 0, 0),
	(265, 1, 'D', 1, 'STANDARD', 0, 0),
	(266, 1, 'D', 2, 'STANDARD', 0, 0),
	(267, 1, 'D', 3, 'STANDARD', 0, 0),
	(268, 1, 'D', 4, 'STANDARD', 0, 0),
	(269, 1, 'D', 5, 'STANDARD', 0, 0),
	(270, 1, 'D', 6, 'STANDARD', 0, 0),
	(271, 1, 'D', 7, 'STANDARD', 0, 0),
	(272, 1, 'D', 8, 'STANDARD', 0, 0),
	(273, 1, 'E', 1, 'STANDARD', 0, 0),
	(274, 1, 'E', 2, 'STANDARD', 0, 0),
	(275, 1, 'E', 3, 'STANDARD', 0, 0),
	(276, 1, 'E', 4, 'STANDARD', 0, 0),
	(277, 1, 'E', 5, 'STANDARD', 0, 0),
	(278, 1, 'E', 6, 'STANDARD', 0, 0),
	(279, 1, 'E', 7, 'STANDARD', 0, 0),
	(280, 1, 'E', 8, 'STANDARD', 0, 0),
	(281, 1, 'F', 1, 'STANDARD', 0, 0),
	(282, 1, 'F', 2, 'STANDARD', 0, 0),
	(283, 1, 'F', 3, 'STANDARD', 0, 0),
	(284, 1, 'F', 4, 'STANDARD', 0, 0),
	(285, 1, 'F', 5, 'STANDARD', 0, 0),
	(286, 1, 'F', 6, 'STANDARD', 0, 0),
	(287, 1, 'F', 7, 'STANDARD', 0, 0),
	(288, 1, 'F', 8, 'STANDARD', 0, 0),
	(289, 1, 'G', 1, 'STANDARD', 0, 0),
	(290, 1, 'G', 2, 'STANDARD', 0, 0),
	(291, 1, 'G', 3, 'STANDARD', 0, 0),
	(292, 1, 'G', 4, 'STANDARD', 0, 0),
	(293, 1, 'G', 5, 'STANDARD', 0, 0),
	(294, 1, 'G', 6, 'STANDARD', 0, 0),
	(295, 1, 'G', 7, 'STANDARD', 0, 0),
	(296, 1, 'G', 8, 'STANDARD', 0, 0),
	(297, 1, 'H', 1, 'STANDARD', 0, 0),
	(298, 1, 'H', 2, 'STANDARD', 0, 0),
	(299, 1, 'H', 3, 'STANDARD', 0, 0),
	(300, 1, 'H', 4, 'STANDARD', 0, 0),
	(301, 1, 'H', 5, 'STANDARD', 0, 0),
	(302, 1, 'H', 6, 'STANDARD', 0, 0),
	(303, 1, 'H', 7, 'STANDARD', 0, 0),
	(304, 1, 'H', 8, 'STANDARD', 0, 0),
	(305, 2, 'A', 1, 'STANDARD', 0, 0),
	(306, 2, 'A', 2, 'STANDARD', 0, 0),
	(307, 2, 'A', 3, 'STANDARD', 0, 0),
	(308, 2, 'A', 4, 'STANDARD', 0, 0),
	(309, 2, 'A', 5, 'STANDARD', 0, 0),
	(310, 2, 'A', 6, 'STANDARD', 0, 0),
	(311, 2, 'A', 7, 'STANDARD', 0, 0),
	(312, 2, 'A', 8, 'STANDARD', 0, 0),
	(313, 2, 'B', 1, 'STANDARD', 0, 0),
	(314, 2, 'B', 2, 'STANDARD', 0, 0),
	(315, 2, 'B', 3, 'STANDARD', 0, 0),
	(316, 2, 'B', 4, 'STANDARD', 0, 0),
	(317, 2, 'B', 5, 'STANDARD', 0, 0),
	(318, 2, 'B', 6, 'STANDARD', 0, 0),
	(319, 2, 'B', 7, 'STANDARD', 0, 0),
	(320, 2, 'B', 8, 'STANDARD', 0, 0),
	(321, 2, 'C', 1, 'STANDARD', 0, 0),
	(322, 2, 'C', 2, 'STANDARD', 0, 0),
	(323, 2, 'C', 3, 'STANDARD', 0, 0),
	(324, 2, 'C', 4, 'STANDARD', 0, 0),
	(325, 2, 'C', 5, 'STANDARD', 0, 0),
	(326, 2, 'C', 6, 'STANDARD', 0, 0),
	(327, 2, 'C', 7, 'STANDARD', 0, 0),
	(328, 2, 'C', 8, 'STANDARD', 0, 0),
	(329, 2, 'D', 1, 'STANDARD', 0, 0),
	(330, 2, 'D', 2, 'STANDARD', 0, 0),
	(331, 2, 'D', 3, 'STANDARD', 0, 0),
	(332, 2, 'D', 4, 'STANDARD', 0, 0),
	(333, 2, 'D', 5, 'STANDARD', 0, 0),
	(334, 2, 'D', 6, 'STANDARD', 0, 0),
	(335, 2, 'D', 7, 'STANDARD', 0, 0),
	(336, 2, 'D', 8, 'STANDARD', 0, 0),
	(337, 2, 'E', 1, 'STANDARD', 0, 0),
	(338, 2, 'E', 2, 'STANDARD', 0, 0),
	(339, 2, 'E', 3, 'STANDARD', 0, 0),
	(340, 2, 'E', 4, 'STANDARD', 0, 0),
	(341, 2, 'E', 5, 'STANDARD', 0, 0),
	(342, 2, 'E', 6, 'STANDARD', 0, 0),
	(343, 2, 'E', 7, 'STANDARD', 0, 0),
	(344, 2, 'E', 8, 'STANDARD', 0, 0),
	(345, 2, 'F', 1, 'STANDARD', 0, 0),
	(346, 2, 'F', 2, 'STANDARD', 0, 0),
	(347, 2, 'F', 3, 'STANDARD', 0, 0),
	(348, 2, 'F', 4, 'STANDARD', 0, 0),
	(349, 2, 'F', 5, 'STANDARD', 0, 0),
	(350, 2, 'F', 6, 'STANDARD', 0, 0),
	(351, 2, 'F', 7, 'STANDARD', 0, 0),
	(352, 2, 'F', 8, 'STANDARD', 0, 0),
	(353, 2, 'G', 1, 'STANDARD', 0, 0),
	(354, 2, 'G', 2, 'STANDARD', 0, 0),
	(355, 2, 'G', 3, 'STANDARD', 0, 0),
	(356, 2, 'G', 4, 'STANDARD', 0, 0),
	(357, 2, 'G', 5, 'STANDARD', 0, 0),
	(358, 2, 'G', 6, 'STANDARD', 0, 0),
	(359, 2, 'G', 7, 'STANDARD', 0, 0),
	(360, 2, 'G', 8, 'STANDARD', 0, 0),
	(361, 2, 'H', 1, 'STANDARD', 0, 0),
	(362, 2, 'H', 2, 'STANDARD', 0, 0),
	(363, 2, 'H', 3, 'STANDARD', 0, 0),
	(364, 2, 'H', 4, 'STANDARD', 0, 0),
	(365, 2, 'H', 5, 'STANDARD', 0, 0),
	(366, 2, 'H', 6, 'STANDARD', 0, 0),
	(367, 2, 'H', 7, 'STANDARD', 0, 0),
	(368, 2, 'H', 8, 'STANDARD', 0, 0),
	(369, 3, 'A', 1, 'STANDARD', 0, 0),
	(370, 3, 'A', 2, 'STANDARD', 0, 0),
	(371, 3, 'A', 3, 'STANDARD', 0, 0),
	(372, 3, 'A', 4, 'STANDARD', 0, 0),
	(373, 3, 'A', 5, 'STANDARD', 0, 0),
	(374, 3, 'A', 6, 'STANDARD', 0, 0),
	(375, 3, 'A', 7, 'STANDARD', 0, 0),
	(376, 3, 'A', 8, 'STANDARD', 0, 0),
	(377, 3, 'B', 1, 'STANDARD', 0, 0),
	(378, 3, 'B', 2, 'STANDARD', 0, 0),
	(379, 3, 'B', 3, 'STANDARD', 0, 0),
	(380, 3, 'B', 4, 'STANDARD', 0, 0),
	(381, 3, 'B', 5, 'STANDARD', 0, 0),
	(382, 3, 'B', 6, 'STANDARD', 0, 0),
	(383, 3, 'B', 7, 'STANDARD', 0, 0),
	(384, 3, 'B', 8, 'STANDARD', 0, 0),
	(385, 3, 'C', 1, 'STANDARD', 0, 0),
	(386, 3, 'C', 2, 'STANDARD', 0, 0),
	(387, 3, 'C', 3, 'STANDARD', 0, 0),
	(388, 3, 'C', 4, 'STANDARD', 0, 0),
	(389, 3, 'C', 5, 'STANDARD', 0, 0),
	(390, 3, 'C', 6, 'STANDARD', 0, 0),
	(391, 3, 'C', 7, 'STANDARD', 0, 0),
	(392, 3, 'C', 8, 'STANDARD', 0, 0),
	(393, 3, 'D', 1, 'STANDARD', 0, 0),
	(394, 3, 'D', 2, 'STANDARD', 0, 0),
	(395, 3, 'D', 3, 'STANDARD', 0, 0),
	(396, 3, 'D', 4, 'STANDARD', 0, 0),
	(397, 3, 'D', 5, 'STANDARD', 0, 0),
	(398, 3, 'D', 6, 'STANDARD', 0, 0),
	(399, 3, 'D', 7, 'STANDARD', 0, 0),
	(400, 3, 'D', 8, 'STANDARD', 0, 0),
	(401, 3, 'E', 1, 'STANDARD', 0, 0),
	(402, 3, 'E', 2, 'STANDARD', 0, 0),
	(403, 3, 'E', 3, 'STANDARD', 0, 0),
	(404, 3, 'E', 4, 'STANDARD', 0, 0),
	(405, 3, 'E', 5, 'STANDARD', 0, 0),
	(406, 3, 'E', 6, 'STANDARD', 0, 0),
	(407, 3, 'E', 7, 'STANDARD', 0, 0),
	(408, 3, 'E', 8, 'STANDARD', 0, 0),
	(409, 3, 'F', 1, 'STANDARD', 0, 0),
	(410, 3, 'F', 2, 'STANDARD', 0, 0),
	(411, 3, 'F', 3, 'STANDARD', 0, 0),
	(412, 3, 'F', 4, 'STANDARD', 0, 0),
	(413, 3, 'F', 5, 'STANDARD', 0, 0),
	(414, 3, 'F', 6, 'STANDARD', 0, 0),
	(415, 3, 'F', 7, 'STANDARD', 0, 0),
	(416, 3, 'F', 8, 'STANDARD', 0, 0),
	(417, 3, 'G', 1, 'STANDARD', 0, 0),
	(418, 3, 'G', 2, 'STANDARD', 0, 0),
	(419, 3, 'G', 3, 'STANDARD', 0, 0),
	(420, 3, 'G', 4, 'STANDARD', 0, 0),
	(421, 3, 'G', 5, 'STANDARD', 0, 0),
	(422, 3, 'G', 6, 'STANDARD', 0, 0),
	(423, 3, 'G', 7, 'STANDARD', 0, 0),
	(424, 3, 'G', 8, 'STANDARD', 0, 0),
	(425, 3, 'H', 1, 'STANDARD', 0, 0),
	(426, 3, 'H', 2, 'STANDARD', 0, 0),
	(427, 3, 'H', 3, 'STANDARD', 0, 0),
	(428, 3, 'H', 4, 'STANDARD', 0, 0),
	(429, 3, 'H', 5, 'STANDARD', 0, 0),
	(430, 3, 'H', 6, 'STANDARD', 0, 0),
	(431, 3, 'H', 7, 'STANDARD', 0, 0),
	(432, 3, 'H', 8, 'STANDARD', 0, 0),
	(433, 4, 'A', 1, 'STANDARD', 0, 0),
	(434, 4, 'A', 2, 'STANDARD', 0, 0),
	(435, 4, 'A', 3, 'STANDARD', 0, 0),
	(436, 4, 'A', 4, 'STANDARD', 0, 0),
	(437, 4, 'A', 5, 'STANDARD', 0, 0),
	(438, 4, 'A', 6, 'STANDARD', 0, 0),
	(439, 4, 'A', 7, 'STANDARD', 0, 0),
	(440, 4, 'A', 8, 'STANDARD', 0, 0),
	(441, 4, 'B', 1, 'STANDARD', 0, 0),
	(442, 4, 'B', 2, 'STANDARD', 0, 0),
	(443, 4, 'B', 3, 'STANDARD', 0, 0),
	(444, 4, 'B', 4, 'STANDARD', 0, 0),
	(445, 4, 'B', 5, 'STANDARD', 0, 0),
	(446, 4, 'B', 6, 'STANDARD', 0, 0),
	(447, 4, 'B', 7, 'STANDARD', 0, 0),
	(448, 4, 'B', 8, 'STANDARD', 0, 0),
	(449, 4, 'C', 1, 'STANDARD', 0, 0),
	(450, 4, 'C', 2, 'STANDARD', 0, 0),
	(451, 4, 'C', 3, 'STANDARD', 0, 0),
	(452, 4, 'C', 4, 'STANDARD', 0, 0),
	(453, 4, 'C', 5, 'STANDARD', 0, 0),
	(454, 4, 'C', 6, 'STANDARD', 0, 0),
	(455, 4, 'C', 7, 'STANDARD', 0, 0),
	(456, 4, 'C', 8, 'STANDARD', 0, 0),
	(457, 4, 'D', 1, 'STANDARD', 0, 0),
	(458, 4, 'D', 2, 'STANDARD', 0, 0),
	(459, 4, 'D', 3, 'STANDARD', 0, 0),
	(460, 4, 'D', 4, 'STANDARD', 0, 0),
	(461, 4, 'D', 5, 'STANDARD', 0, 0),
	(462, 4, 'D', 6, 'STANDARD', 0, 0),
	(463, 4, 'D', 7, 'STANDARD', 0, 0),
	(464, 4, 'D', 8, 'STANDARD', 0, 0),
	(465, 4, 'E', 1, 'STANDARD', 0, 0),
	(466, 4, 'E', 2, 'STANDARD', 0, 0),
	(467, 4, 'E', 3, 'STANDARD', 0, 0),
	(468, 4, 'E', 4, 'STANDARD', 0, 0),
	(469, 4, 'E', 5, 'STANDARD', 0, 0),
	(470, 4, 'E', 6, 'STANDARD', 0, 0),
	(471, 4, 'E', 7, 'STANDARD', 0, 0),
	(472, 4, 'E', 8, 'STANDARD', 0, 0),
	(473, 4, 'F', 1, 'STANDARD', 0, 0),
	(474, 4, 'F', 2, 'STANDARD', 0, 0),
	(475, 4, 'F', 3, 'STANDARD', 0, 0),
	(476, 4, 'F', 4, 'STANDARD', 0, 0),
	(477, 4, 'F', 5, 'STANDARD', 0, 0),
	(478, 4, 'F', 6, 'STANDARD', 0, 0),
	(479, 4, 'F', 7, 'STANDARD', 0, 0),
	(480, 4, 'F', 8, 'STANDARD', 0, 0),
	(481, 4, 'G', 1, 'STANDARD', 0, 0),
	(482, 4, 'G', 2, 'STANDARD', 0, 0),
	(483, 4, 'G', 3, 'STANDARD', 0, 0),
	(484, 4, 'G', 4, 'STANDARD', 0, 0),
	(485, 4, 'G', 5, 'STANDARD', 0, 0),
	(486, 4, 'G', 6, 'STANDARD', 0, 0),
	(487, 4, 'G', 7, 'STANDARD', 0, 0),
	(488, 4, 'G', 8, 'STANDARD', 0, 0),
	(489, 4, 'H', 1, 'STANDARD', 0, 0),
	(490, 4, 'H', 2, 'STANDARD', 0, 0),
	(491, 4, 'H', 3, 'STANDARD', 0, 0),
	(492, 4, 'H', 4, 'STANDARD', 0, 0),
	(493, 4, 'H', 5, 'STANDARD', 0, 0),
	(494, 4, 'H', 6, 'STANDARD', 0, 0),
	(495, 4, 'H', 7, 'STANDARD', 0, 0),
	(496, 4, 'H', 8, 'STANDARD', 0, 0),
	(497, 5, 'A', 1, 'STANDARD', 0, 0),
	(498, 5, 'A', 2, 'STANDARD', 0, 0),
	(499, 5, 'A', 3, 'STANDARD', 0, 0),
	(500, 5, 'A', 4, 'STANDARD', 0, 0),
	(501, 5, 'A', 5, 'STANDARD', 0, 0),
	(502, 5, 'A', 6, 'STANDARD', 0, 0),
	(503, 5, 'A', 7, 'STANDARD', 0, 0),
	(504, 5, 'A', 8, 'STANDARD', 0, 0),
	(505, 5, 'B', 1, 'STANDARD', 0, 0),
	(506, 5, 'B', 2, 'STANDARD', 0, 0),
	(507, 5, 'B', 3, 'STANDARD', 0, 0),
	(508, 5, 'B', 4, 'STANDARD', 0, 0),
	(509, 5, 'B', 5, 'STANDARD', 0, 0),
	(510, 5, 'B', 6, 'STANDARD', 0, 0),
	(511, 5, 'B', 7, 'STANDARD', 0, 0),
	(512, 5, 'B', 8, 'STANDARD', 0, 0),
	(513, 5, 'C', 1, 'STANDARD', 0, 0),
	(514, 5, 'C', 2, 'STANDARD', 0, 0),
	(515, 5, 'C', 3, 'STANDARD', 0, 0),
	(516, 5, 'C', 4, 'STANDARD', 0, 0),
	(517, 5, 'C', 5, 'STANDARD', 0, 0),
	(518, 5, 'C', 6, 'STANDARD', 0, 0),
	(519, 5, 'C', 7, 'STANDARD', 0, 0),
	(520, 5, 'C', 8, 'STANDARD', 0, 0),
	(521, 5, 'D', 1, 'STANDARD', 0, 0),
	(522, 5, 'D', 2, 'STANDARD', 0, 0),
	(523, 5, 'D', 3, 'STANDARD', 0, 0),
	(524, 5, 'D', 4, 'STANDARD', 0, 0),
	(525, 5, 'D', 5, 'STANDARD', 0, 0),
	(526, 5, 'D', 6, 'STANDARD', 0, 0),
	(527, 5, 'D', 7, 'STANDARD', 0, 0),
	(528, 5, 'D', 8, 'STANDARD', 0, 0),
	(529, 5, 'E', 1, 'STANDARD', 0, 0),
	(530, 5, 'E', 2, 'STANDARD', 0, 0),
	(531, 5, 'E', 3, 'STANDARD', 0, 0),
	(532, 5, 'E', 4, 'STANDARD', 0, 0),
	(533, 5, 'E', 5, 'STANDARD', 0, 0),
	(534, 5, 'E', 6, 'STANDARD', 0, 0),
	(535, 5, 'E', 7, 'STANDARD', 0, 0),
	(536, 5, 'E', 8, 'STANDARD', 0, 0),
	(537, 5, 'F', 1, 'STANDARD', 0, 0),
	(538, 5, 'F', 2, 'STANDARD', 0, 0),
	(539, 5, 'F', 3, 'STANDARD', 0, 0),
	(540, 5, 'F', 4, 'STANDARD', 0, 0),
	(541, 5, 'F', 5, 'STANDARD', 0, 0),
	(542, 5, 'F', 6, 'STANDARD', 0, 0),
	(543, 5, 'F', 7, 'STANDARD', 0, 0),
	(544, 5, 'F', 8, 'STANDARD', 0, 0),
	(545, 5, 'G', 1, 'STANDARD', 0, 0),
	(546, 5, 'G', 2, 'STANDARD', 0, 0),
	(547, 5, 'G', 3, 'STANDARD', 0, 0),
	(548, 5, 'G', 4, 'STANDARD', 0, 0),
	(549, 5, 'G', 5, 'STANDARD', 0, 0),
	(550, 5, 'G', 6, 'STANDARD', 0, 0),
	(551, 5, 'G', 7, 'STANDARD', 0, 0),
	(552, 5, 'G', 8, 'STANDARD', 0, 0),
	(553, 5, 'H', 1, 'STANDARD', 0, 0),
	(554, 5, 'H', 2, 'STANDARD', 0, 0),
	(555, 5, 'H', 3, 'STANDARD', 0, 0),
	(556, 5, 'H', 4, 'STANDARD', 0, 0),
	(557, 5, 'H', 5, 'STANDARD', 0, 0),
	(558, 5, 'H', 6, 'STANDARD', 0, 0),
	(559, 5, 'H', 7, 'STANDARD', 0, 0),
	(560, 5, 'H', 8, 'STANDARD', 0, 0);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.seat_lock
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

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.seat_lock: ~0 rows (xấp xỉ)

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.sessions
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

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.sessions: ~3 rows (xấp xỉ)
INSERT INTO `sessions` (`id`, `user_id`, `ip_address`, `user_agent`, `payload`, `last_activity`) VALUES
	('7mNQqNCcyF7eiDmNOOZOOGoHCuA7905BiIOTVCAa', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoicnlzSUJzRk84U2Q2dWRWaTJrQlR4bTh4cGNLcmVBYTVqOUJGUnZnSCI7czo2OiJfZmxhc2giO2E6Mjp7czozOiJuZXciO2E6MDp7fXM6Mzoib2xkIjthOjA6e319czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzM6Imh0dHA6Ly8xMjcuMC4wLjE6ODAwMC9hdXRoL2dvb2dsZSI7fXM6NToic3RhdGUiO3M6NDA6ImdTYlB2eTFvYVJtZzEwVU11VXBKekNvWDRTSkRjSEFEaTB3bkJRV1YiO30=', 1765250223),
	('OikmHs6d8CilI3yOFxvuqUdh8gdxwub0xDUitkJh', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiMDFaRkJodjRpSUVqdHlNOUh0dTBJOXdkeVdFQ3BHT09Kd3pFS0F6bCI7czo2OiJfZmxhc2giO2E6Mjp7czozOiJuZXciO2E6MDp7fXM6Mzoib2xkIjthOjA6e319czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6Mjc6Imh0dHA6Ly9sb2NhbGhvc3Q6ODAwMC9sb2dpbiI7fX0=', 1765251257),
	('XcBktbvEfR0BvtvGoM2j3sBXn56rX4mmvvOLb9ZO', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoiSTBsOThsc1ZnY1Fsd0p5b09aVTlhVGVCcjRiSXhSOEdzU1JUQnhUZiI7czo2OiJfZmxhc2giO2E6Mjp7czozOiJuZXciO2E6MDp7fXM6Mzoib2xkIjthOjA6e319czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MzM6Imh0dHA6Ly8xMjcuMC4wLjE6ODAwMC9hdXRoL2dvb2dsZSI7fXM6NToic3RhdGUiO3M6NDA6IlhOOXBQUWlQZ2pxa2RWQUg0c2tjR3dMaG12SFUyZXp2SFZtMG9IeXEiO30=', 1765251252);

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.showtime
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
  CONSTRAINT `showtime_movie_id_foreign` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `showtime_screen_id_foreign` FOREIGN KEY (`screen_id`) REFERENCES `screen` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.showtime: ~112 rows (xấp xỉ)
INSERT INTO `showtime` (`id`, `movie_id`, `screen_id`, `start_at`, `end_at`, `base_price`, `status`) VALUES
	(276, 1, 3, '2025-12-16 09:00:00', '2025-12-16 12:12:00', 150000, 'OPEN'),
	(277, 1, 3, '2025-12-16 14:00:00', '2025-12-16 17:12:00', 150000, 'OPEN'),
	(278, 1, 3, '2025-12-16 19:30:00', '2025-12-16 22:42:00', 180000, 'OPEN'),
	(279, 1, 3, '2025-12-17 09:00:00', '2025-12-17 12:12:00', 150000, 'OPEN'),
	(280, 1, 3, '2025-12-17 14:00:00', '2025-12-17 17:12:00', 150000, 'OPEN'),
	(281, 1, 3, '2025-12-17 19:30:00', '2025-12-17 22:42:00', 180000, 'OPEN'),
	(282, 1, 3, '2025-12-18 14:00:00', '2025-12-18 17:12:00', 150000, 'OPEN'),
	(283, 1, 3, '2025-12-19 09:00:00', '2025-12-19 12:12:00', 150000, 'OPEN'),
	(284, 1, 3, '2025-12-20 19:30:00', '2025-12-20 22:42:00', 180000, 'OPEN'),
	(285, 1, 3, '2025-12-21 14:00:00', '2025-12-21 17:12:00', 150000, 'OPEN'),
	(286, 2, 2, '2025-12-16 10:00:00', '2025-12-16 12:11:00', 120000, 'OPEN'),
	(287, 2, 2, '2025-12-16 13:00:00', '2025-12-16 15:11:00', 120000, 'OPEN'),
	(288, 2, 2, '2025-12-16 20:00:00', '2025-12-16 22:11:00', 140000, 'OPEN'),
	(289, 2, 2, '2025-12-18 10:00:00', '2025-12-18 12:11:00', 120000, 'OPEN'),
	(290, 2, 2, '2025-12-19 13:00:00', '2025-12-19 15:11:00', 120000, 'OPEN'),
	(291, 2, 2, '2025-12-20 10:00:00', '2025-12-20 12:11:00', 120000, 'OPEN'),
	(292, 2, 2, '2025-12-21 20:00:00', '2025-12-21 22:11:00', 140000, 'OPEN'),
	(293, 2, 2, '2025-12-22 13:00:00', '2025-12-22 15:11:00', 120000, 'OPEN'),
	(294, 3, 1, '2025-12-16 09:30:00', '2025-12-16 12:31:00', 100000, 'OPEN'),
	(295, 3, 1, '2025-12-16 14:00:00', '2025-12-16 17:01:00', 100000, 'OPEN'),
	(296, 3, 1, '2025-12-16 18:30:00', '2025-12-16 21:31:00', 120000, 'OPEN'),
	(297, 3, 1, '2025-12-19 09:30:00', '2025-12-19 12:31:00', 100000, 'OPEN'),
	(298, 3, 1, '2025-12-20 14:00:00', '2025-12-20 17:01:00', 100000, 'OPEN'),
	(299, 3, 1, '2025-12-21 09:30:00', '2025-12-21 12:31:00', 100000, 'OPEN'),
	(300, 3, 1, '2025-12-22 18:30:00', '2025-12-22 21:31:00', 120000, 'OPEN'),
	(301, 3, 1, '2025-12-23 14:00:00', '2025-12-23 17:01:00', 100000, 'OPEN'),
	(302, 4, 4, '2025-12-16 11:00:00', '2025-12-16 13:56:00', 100000, 'OPEN'),
	(303, 4, 4, '2025-12-16 15:00:00', '2025-12-16 17:56:00', 100000, 'OPEN'),
	(304, 4, 4, '2025-12-16 19:30:00', '2025-12-16 22:26:00', 120000, 'OPEN'),
	(305, 4, 4, '2025-12-17 11:00:00', '2025-12-17 13:56:00', 100000, 'OPEN'),
	(306, 4, 4, '2025-12-18 19:30:00', '2025-12-18 22:26:00', 120000, 'OPEN'),
	(307, 4, 4, '2025-12-21 15:00:00', '2025-12-21 17:56:00', 100000, 'OPEN'),
	(308, 4, 4, '2025-12-23 11:00:00', '2025-12-23 13:56:00', 100000, 'OPEN'),
	(309, 4, 4, '2025-12-24 19:30:00', '2025-12-24 22:26:00', 120000, 'OPEN'),
	(310, 5, 5, '2025-12-16 10:30:00', '2025-12-16 12:58:00', 200000, 'OPEN'),
	(311, 5, 5, '2025-12-16 14:30:00', '2025-12-16 16:58:00', 200000, 'OPEN'),
	(312, 5, 5, '2025-12-16 19:00:00', '2025-12-16 21:28:00', 250000, 'OPEN'),
	(313, 5, 5, '2025-12-17 10:30:00', '2025-12-17 12:58:00', 200000, 'OPEN'),
	(314, 5, 5, '2025-12-18 14:30:00', '2025-12-18 16:58:00', 200000, 'OPEN'),
	(315, 5, 5, '2025-12-19 19:00:00', '2025-12-19 21:28:00', 250000, 'OPEN'),
	(316, 5, 5, '2025-12-22 10:30:00', '2025-12-22 12:58:00', 200000, 'OPEN'),
	(317, 5, 5, '2025-12-24 14:30:00', '2025-12-24 16:58:00', 200000, 'OPEN'),
	(318, 6, 1, '2025-12-17 09:00:00', '2025-12-17 10:43:00', 80000, 'OPEN'),
	(319, 6, 1, '2025-12-17 11:00:00', '2025-12-17 12:43:00', 80000, 'OPEN'),
	(320, 6, 1, '2025-12-18 09:00:00', '2025-12-18 10:43:00', 80000, 'OPEN'),
	(321, 6, 1, '2025-12-22 09:30:00', '2025-12-22 11:13:00', 80000, 'OPEN'),
	(322, 6, 1, '2025-12-24 09:00:00', '2025-12-24 10:43:00', 80000, 'OPEN'),
	(323, 6, 1, '2025-12-25 11:00:00', '2025-12-25 12:43:00', 80000, 'OPEN'),
	(324, 6, 1, '2025-12-26 09:00:00', '2025-12-26 10:43:00', 80000, 'OPEN'),
	(325, 7, 3, '2025-12-18 09:00:00', '2025-12-18 11:46:00', 150000, 'OPEN'),
	(326, 7, 3, '2025-12-18 19:30:00', '2025-12-18 22:16:00', 180000, 'OPEN'),
	(327, 7, 3, '2025-12-22 09:00:00', '2025-12-22 11:46:00', 150000, 'OPEN'),
	(328, 7, 3, '2025-12-22 14:00:00', '2025-12-22 16:46:00', 150000, 'OPEN'),
	(329, 7, 3, '2025-12-23 19:30:00', '2025-12-23 22:16:00', 180000, 'OPEN'),
	(330, 7, 3, '2025-12-25 14:00:00', '2025-12-25 16:46:00', 150000, 'OPEN'),
	(331, 7, 3, '2025-12-27 09:00:00', '2025-12-27 11:46:00', 150000, 'OPEN'),
	(332, 8, 2, '2025-12-17 10:00:00', '2025-12-17 13:00:00', 130000, 'OPEN'),
	(333, 8, 2, '2025-12-17 14:30:00', '2025-12-17 17:30:00', 130000, 'OPEN'),
	(334, 8, 2, '2025-12-17 19:00:00', '2025-12-17 22:00:00', 150000, 'OPEN'),
	(335, 8, 2, '2025-12-20 14:30:00', '2025-12-20 17:30:00', 130000, 'OPEN'),
	(336, 8, 2, '2025-12-23 10:00:00', '2025-12-23 13:00:00', 130000, 'OPEN'),
	(337, 8, 2, '2025-12-25 19:00:00', '2025-12-25 22:00:00', 150000, 'OPEN'),
	(338, 8, 2, '2025-12-28 14:30:00', '2025-12-28 17:30:00', 130000, 'OPEN'),
	(339, 9, 4, '2025-12-17 09:00:00', '2025-12-17 10:54:00', 90000, 'OPEN'),
	(340, 9, 4, '2025-12-18 09:00:00', '2025-12-18 10:54:00', 90000, 'OPEN'),
	(341, 9, 4, '2025-12-19 11:00:00', '2025-12-19 12:54:00', 90000, 'OPEN'),
	(342, 9, 4, '2025-12-22 09:00:00', '2025-12-22 10:54:00', 90000, 'OPEN'),
	(343, 9, 4, '2025-12-25 09:00:00', '2025-12-25 10:54:00', 90000, 'OPEN'),
	(344, 9, 4, '2025-12-27 11:00:00', '2025-12-27 12:54:00', 90000, 'OPEN'),
	(345, 9, 4, '2025-12-29 09:00:00', '2025-12-29 10:54:00', 90000, 'OPEN'),
	(346, 10, 1, '2025-12-21 11:00:00', '2025-12-21 12:32:00', 80000, 'OPEN'),
	(347, 10, 1, '2025-12-25 09:00:00', '2025-12-25 10:32:00', 80000, 'OPEN'),
	(348, 10, 1, '2025-12-26 11:00:00', '2025-12-26 12:32:00', 80000, 'OPEN'),
	(349, 10, 1, '2025-12-28 09:00:00', '2025-12-28 10:32:00', 80000, 'OPEN'),
	(350, 10, 1, '2025-12-29 11:00:00', '2025-12-29 12:32:00', 80000, 'OPEN'),
	(351, 10, 1, '2025-12-31 09:00:00', '2025-12-31 10:32:00', 80000, 'OPEN'),
	(352, 10, 1, '2025-01-01 11:00:00', '2025-01-01 12:32:00', 80000, 'OPEN'),
	(353, 11, 2, '2025-12-19 10:00:00', '2025-12-19 12:30:00', 120000, 'OPEN'),
	(354, 11, 2, '2025-12-19 14:00:00', '2025-12-19 16:30:00', 120000, 'OPEN'),
	(355, 11, 2, '2025-12-19 19:00:00', '2025-12-19 21:30:00', 140000, 'OPEN'),
	(356, 11, 2, '2025-12-24 10:00:00', '2025-12-24 12:30:00', 120000, 'OPEN'),
	(357, 11, 2, '2025-12-26 14:00:00', '2025-12-26 16:30:00', 120000, 'OPEN'),
	(358, 11, 2, '2025-12-29 19:00:00', '2025-12-29 21:30:00', 140000, 'OPEN'),
	(359, 11, 2, '2025-12-31 14:00:00', '2025-12-31 16:30:00', 120000, 'OPEN'),
	(360, 12, 5, '2025-12-20 10:30:00', '2025-12-20 12:51:00', 200000, 'OPEN'),
	(361, 12, 5, '2025-12-20 14:30:00', '2025-12-20 16:51:00', 200000, 'OPEN'),
	(362, 12, 5, '2025-12-20 19:00:00', '2025-12-20 21:21:00', 250000, 'OPEN'),
	(363, 12, 5, '2025-12-23 10:30:00', '2025-12-23 12:51:00', 200000, 'OPEN'),
	(364, 12, 5, '2025-12-25 14:30:00', '2025-12-25 16:51:00', 200000, 'OPEN'),
	(365, 12, 5, '2025-12-27 19:00:00', '2025-12-27 21:21:00', 250000, 'OPEN'),
	(366, 12, 5, '2025-12-30 14:30:00', '2025-12-30 16:51:00', 200000, 'OPEN'),
	(367, 13, 4, '2025-12-20 09:00:00', '2025-12-20 11:15:00', 85000, 'OPEN'),
	(368, 13, 4, '2025-12-22 11:00:00', '2025-12-22 13:15:00', 85000, 'OPEN'),
	(369, 13, 4, '2025-12-26 09:00:00', '2025-12-26 11:15:00', 85000, 'OPEN'),
	(370, 13, 4, '2025-12-28 11:00:00', '2025-12-28 13:15:00', 85000, 'OPEN'),
	(371, 13, 4, '2025-12-30 09:00:00', '2025-12-30 11:15:00', 85000, 'OPEN'),
	(372, 13, 4, '2025-01-01 09:00:00', '2025-01-01 11:15:00', 85000, 'OPEN'),
	(373, 13, 4, '2025-01-02 11:00:00', '2025-01-02 13:15:00', 85000, 'OPEN'),
	(374, 14, 3, '2025-12-24 09:00:00', '2025-12-24 11:43:00', 150000, 'OPEN'),
	(375, 14, 3, '2025-12-24 14:00:00', '2025-12-24 16:43:00', 150000, 'OPEN'),
	(376, 14, 3, '2025-12-26 09:00:00', '2025-12-26 11:43:00', 150000, 'OPEN'),
	(377, 14, 3, '2025-12-26 19:30:00', '2025-12-26 22:13:00', 180000, 'OPEN'),
	(378, 14, 3, '2025-12-28 09:00:00', '2025-12-28 11:43:00', 150000, 'OPEN'),
	(379, 14, 3, '2025-12-30 14:00:00', '2025-12-30 16:43:00', 150000, 'OPEN'),
	(380, 14, 3, '2025-01-01 19:30:00', '2025-01-01 22:13:00', 180000, 'OPEN'),
	(381, 15, 1, '2025-12-27 09:00:00', '2025-12-27 10:49:00', 80000, 'OPEN'),
	(382, 15, 1, '2025-12-27 11:00:00', '2025-12-27 12:49:00', 80000, 'OPEN'),
	(383, 15, 1, '2025-12-29 09:00:00', '2025-12-29 10:49:00', 80000, 'OPEN'),
	(384, 15, 1, '2025-12-30 11:00:00', '2025-12-30 12:49:00', 80000, 'OPEN'),
	(385, 15, 1, '2025-01-02 09:00:00', '2025-01-02 10:49:00', 80000, 'OPEN'),
	(386, 15, 1, '2025-01-03 11:00:00', '2025-01-03 12:49:00', 80000, 'OPEN'),
	(387, 15, 1, '2025-01-04 09:00:00', '2025-01-04 10:49:00', 80000, 'OPEN');

-- Đang kết xuất đổ cấu trúc cho bảng cinema_service1.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_email_unique` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Đang kết xuất đổ dữ liệu cho bảng cinema_service1.users: ~0 rows (xấp xỉ)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
