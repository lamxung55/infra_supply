-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for infra_supply
CREATE DATABASE IF NOT EXISTS `infra_supply` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `infra_supply`;

-- Dumping structure for table infra_supply.bbbg
CREATE TABLE IF NOT EXISTS `bbbg` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `SUBMITTER` varchar(45) DEFAULT NULL,
  `APPROVED_TIME` date DEFAULT NULL,
  `ATTACH_FILE` varchar(200) DEFAULT NULL,
  `NOTE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.bbbg: ~2 rows (approximately)
/*!40000 ALTER TABLE `bbbg` DISABLE KEYS */;
INSERT INTO `bbbg` (`ID`, `CODE`, `NAME`, `SUBMITTER`, `APPROVED_TIME`, `ATTACH_FILE`, `NOTE`) VALUES
	(2, '22', '222', '22', '2021-10-07', 'C:\\running_projects\\infra_supply\\source\\infra_supply\\out\\artifacts\\infrasupply\\resources\\datas\\file_upload\\1633614481477_CMC.docx', 'ok');
/*!40000 ALTER TABLE `bbbg` ENABLE KEYS */;

-- Dumping structure for table infra_supply.contract
CREATE TABLE IF NOT EXISTS `contract` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `PROGRESS_PERCENT` int(11) DEFAULT NULL,
  `PROGRESS_DESC` varchar(45) DEFAULT NULL,
  `PARTNER` varchar(45) DEFAULT NULL,
  `OWNER` varchar(45) DEFAULT NULL,
  `NOTE` varchar(100) DEFAULT NULL,
  `STATUS` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.contract: ~2 rows (approximately)
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` (`ID`, `CODE`, `NAME`, `PROGRESS_PERCENT`, `PROGRESS_DESC`, `PARTNER`, `OWNER`, `NOTE`, `STATUS`) VALUES
	(1, '111', '11', 111, 'Mới', 'aa', '11', '111', NULL),
	(2, '333', '33', 33, 'Đang thực hiện', 'bbb', '33', '33', NULL);
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;

-- Dumping structure for table infra_supply.device
CREATE TABLE IF NOT EXISTS `device` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `CONFIGURATION` varchar(500) DEFAULT NULL,
  `PRICE` decimal(10,0) DEFAULT NULL,
  `UNIT` varchar(45) DEFAULT NULL,
  `DEVICE_TYPE` varchar(45) DEFAULT NULL,
  `INFRA_TYPE` varchar(45) DEFAULT NULL,
  `V_CPU` int(11) DEFAULT NULL,
  `V_RAM` int(11) DEFAULT NULL,
  `TOTAL_AVAIL` int(11) DEFAULT NULL,
  `SSD` decimal(10,0) DEFAULT NULL,
  `HDD` decimal(10,0) DEFAULT NULL,
  `TIERING` decimal(10,0) DEFAULT NULL,
  `NOTE` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.device: ~0 rows (approximately)
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` (`ID`, `CODE`, `NAME`, `CONFIGURATION`, `PRICE`, `UNIT`, `DEVICE_TYPE`, `INFRA_TYPE`, `V_CPU`, `V_RAM`, `TOTAL_AVAIL`, `SSD`, `HDD`, `TIERING`, `NOTE`) VALUES
	(1, 'abcdef', '1', '1', 33, '1', '1', '1', 1, NULL, 1, NULL, 1, NULL, '');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;

-- Dumping structure for table infra_supply.device_infra
CREATE TABLE IF NOT EXISTS `device_infra` (
  `ID` bigint(20) NOT NULL DEFAULT 0,
  `DEVICE_ID` int(11) DEFAULT NULL,
  `DEVICE_CODE` varchar(100) DEFAULT NULL,
  `DEVICE_NAME` varchar(100) DEFAULT NULL,
  `INFRA_ID` int(11) DEFAULT NULL,
  `INFRA_CODE` varchar(50) DEFAULT NULL,
  `INFRA_NAME` varchar(100) DEFAULT NULL,
  `INFRA_TYPE` varchar(50) DEFAULT NULL,
  `COUNT` int(11) DEFAULT NULL,
  `NOTE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_device_infra_device` (`DEVICE_ID`),
  CONSTRAINT `FK_device_infra_device` FOREIGN KEY (`DEVICE_ID`) REFERENCES `device` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.device_infra: ~5 rows (approximately)
/*!40000 ALTER TABLE `device_infra` DISABLE KEYS */;
INSERT INTO `device_infra` (`ID`, `DEVICE_ID`, `DEVICE_CODE`, `DEVICE_NAME`, `INFRA_ID`, `INFRA_CODE`, `INFRA_NAME`, `INFRA_TYPE`, `COUNT`, `NOTE`) VALUES
	(3, 1, 'abcdef', '1', 10, '22', '22222', 'PROJECT', 12, '9999');
/*!40000 ALTER TABLE `device_infra` ENABLE KEYS */;

-- Dumping structure for table infra_supply.ha
CREATE TABLE IF NOT EXISTS `ha` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `INFRA_TYPE` varchar(50) DEFAULT NULL,
  `SUPPLY_TYPE` varchar(50) DEFAULT NULL,
  `NOTE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.ha: ~0 rows (approximately)
/*!40000 ALTER TABLE `ha` DISABLE KEYS */;
INSERT INTO `ha` (`ID`, `CODE`, `NAME`, `INFRA_TYPE`, `SUPPLY_TYPE`, `NOTE`) VALUES
	(1, '2323', '232', 'Datalake', 'Ảo hóa', 'Ghi chú nhé');
/*!40000 ALTER TABLE `ha` ENABLE KEYS */;

-- Dumping structure for table infra_supply.oam_user
CREATE TABLE IF NOT EXISTS `oam_user` (
  `USER_ID` bigint(20) NOT NULL,
  `ACCOUNT_STATUS` smallint(6) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
  `MOBILE_NUMBER` varchar(255) DEFAULT NULL,
  `NUM_LOGIN_FAIL_ATTEMPT` smallint(6) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `RANDOM_STRING` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.oam_user: ~4 rows (approximately)
/*!40000 ALTER TABLE `oam_user` DISABLE KEYS */;
INSERT INTO `oam_user` (`USER_ID`, `ACCOUNT_STATUS`, `CREATE_DATE`, `EMAIL`, `LAST_MODIFIED_DATE`, `MOBILE_NUMBER`, `NUM_LOGIN_FAIL_ATTEMPT`, `PASSWORD`, `RANDOM_STRING`, `USERNAME`) VALUES
	(1, 1, NULL, '123@gmail.com', NULL, '84977222333', NULL, '9a311ff341272bb2a122b1e4325d2ff2b1e1152940c7b81478978ddda48db96e', NULL, 'admin'),
	(2, 1, NULL, '123@gmail.com', NULL, '84977222333', NULL, 'QxrWpuibZno=', NULL, 'admin2'),
	(330, 1, NULL, '123@gmail.com', NULL, '84977222333', NULL, 'QxrWpuibZno=', NULL, 'operator1'),
	(350, 1, NULL, '123@yahoo.com', NULL, '84977222333', NULL, 'QxrWpuibZno=', NULL, 'callcenter1');
/*!40000 ALTER TABLE `oam_user` ENABLE KEYS */;

-- Dumping structure for table infra_supply.oam_user_role
CREATE TABLE IF NOT EXISTS `oam_user_role` (
  `ROLE_ID` int(11) NOT NULL,
  `ACTION_ID` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `PAGE_URL` varchar(255) DEFAULT NULL,
  `ROLE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.oam_user_role: ~3 rows (approximately)
/*!40000 ALTER TABLE `oam_user_role` DISABLE KEYS */;
INSERT INTO `oam_user_role` (`ROLE_ID`, `ACTION_ID`, `DESCRIPTION`, `PAGE_URL`, `ROLE_NAME`) VALUES
	(1, NULL, 'This role can\'t be deleted!', '/permission', 'guest'),
	(2, 'ALL', 'admin role', 'ALL', 'admin'),
	(3, 'sdfsd', 'sdfsdf', 'sdfsdf', 'dsdsf');
/*!40000 ALTER TABLE `oam_user_role` ENABLE KEYS */;

-- Dumping structure for table infra_supply.oam_user_role_mapping
CREATE TABLE IF NOT EXISTS `oam_user_role_mapping` (
  `ID` bigint(20) NOT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  `STATUS` smallint(6) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `VALID_FROM` datetime DEFAULT NULL,
  `VALID_TO` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.oam_user_role_mapping: ~0 rows (approximately)
/*!40000 ALTER TABLE `oam_user_role_mapping` DISABLE KEYS */;
INSERT INTO `oam_user_role_mapping` (`ID`, `ROLE_ID`, `STATUS`, `USER_ID`, `VALID_FROM`, `VALID_TO`) VALUES
	(1, 2, 1, 1, '2017-05-01 19:59:41', '2027-05-01 19:59:43');
/*!40000 ALTER TABLE `oam_user_role_mapping` ENABLE KEYS */;

-- Dumping structure for table infra_supply.pool
CREATE TABLE IF NOT EXISTS `pool` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `NOTE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.pool: ~0 rows (approximately)
/*!40000 ALTER TABLE `pool` DISABLE KEYS */;
INSERT INTO `pool` (`ID`, `CODE`, `NAME`, `NOTE`) VALUES
	(1, 'pool1', 'pool1', 'pool1pool1');
/*!40000 ALTER TABLE `pool` ENABLE KEYS */;

-- Dumping structure for table infra_supply.project
CREATE TABLE IF NOT EXISTS `project` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` int(11) DEFAULT NULL,
  `OWNER` varchar(45) DEFAULT NULL,
  `CREATE_TIME` date DEFAULT NULL,
  `STATUS` varchar(50) DEFAULT NULL,
  `NOTE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.project: ~3 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`ID`, `CODE`, `NAME`, `OWNER`, `CREATE_TIME`, `STATUS`, `NOTE`) VALUES
	(10, '22', 22222, '222', '2021-10-04', 'Đang thực hiện', NULL),
	(15, '88', 88, '888', '2021-08-02', 'Hoàn thành', 'ok'),
	(17, '333', 333, '333', '2021-10-05', 'Mới', NULL);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

-- Dumping structure for table infra_supply.reset_password_mail
CREATE TABLE IF NOT EXISTS `reset_password_mail` (
  `ID` int(11) NOT NULL,
  `CONTENT` varchar(500) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.reset_password_mail: ~4 rows (approximately)
/*!40000 ALTER TABLE `reset_password_mail` DISABLE KEYS */;
INSERT INTO `reset_password_mail` (`ID`, `CONTENT`, `EMAIL`, `CREATE_TIME`) VALUES
	(1, 'Mật khẩu mới của Quý khách là: 796038', '123@xxx', '2017-01-20 20:42:31'),
	(2, 'Mật khẩu mới của Quý khách là: 439048', '123@xxx', '2017-01-20 20:44:21'),
	(3, 'Mật khẩu mới của Quý khách là: 995095', '123@xxx', '2017-01-20 20:56:13'),
	(4, 'Mật khẩu mới của Quý khách là: 445292', '123@xxx111', '2017-01-20 20:56:48');
/*!40000 ALTER TABLE `reset_password_mail` ENABLE KEYS */;

-- Dumping structure for table infra_supply.unit
CREATE TABLE IF NOT EXISTS `unit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `OWNER` varchar(45) DEFAULT NULL,
  `NOTE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.unit: ~0 rows (approximately)
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` (`ID`, `CODE`, `NAME`, `PARENT_ID`, `OWNER`, `NOTE`) VALUES
	(1, 'A', 'aa', NULL, 'aa', 'aaaa');
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;

-- Dumping structure for table infra_supply.user
CREATE TABLE IF NOT EXISTS `user` (
  `USER_ID` int(11) NOT NULL,
  `ACCOUNT` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `MONEY` double(50,1) DEFAULT NULL,
  `LOCK_TIME` time DEFAULT NULL,
  `FAIL_NUMBER` int(11) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table infra_supply.user: ~6 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`USER_ID`, `ACCOUNT`, `EMAIL`, `PASSWORD`, `PHONE`, `MONEY`, `LOCK_TIME`, `FAIL_NUMBER`) VALUES
	(1, 'abc', '123@xxx111', '445292', 'xxxx', NULL, NULL, NULL),
	(2, 'tuan anh', NULL, '123', '123xxx', NULL, NULL, NULL),
	(3, 'tuan anh2222', NULL, '123', NULL, NULL, NULL, NULL),
	(4, 'tuan an3333', '123@xxx', '995095', NULL, NULL, NULL, NULL),
	(5, 'tuanxxx2', NULL, '123', NULL, NULL, NULL, NULL),
	(6, 'tuanxxx3', NULL, '123', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
