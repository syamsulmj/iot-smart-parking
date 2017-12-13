-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 14, 2017 at 01:00 AM
-- Server version: 10.0.30-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ipscapst_ipscapstone`
--

-- --------------------------------------------------------

--
-- Table structure for table `parking_status`
--

CREATE TABLE `parking_status` (
  `id` int(11) NOT NULL,
  `grid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `available` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `booked` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `not_paid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `paid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `booked_for` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `led_color` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `parking_status`
--

INSERT INTO `parking_status` (`id`, `grid`, `available`, `booked`, `not_paid`, `paid`, `booked_for`, `led_color`) VALUES
(1, '1-1', 'false', 'false', 'false', 'true', '', 'blue'),
(2, '1-2', 'true', 'false', 'false', 'false', '', 'green'),
(3, '2-1', 'true', 'false', 'false', 'false', '', 'green'),
(4, '2-2', 'true', 'false', 'false', 'false', '', 'green');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `parking_status`
--
ALTER TABLE `parking_status`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `parking_status`
--
ALTER TABLE `parking_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
