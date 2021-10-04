-- MySQL Script generated by MySQL Workbench
-- 10/03/21 09:47:51
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema infra_supply
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `infra_supply` ;

-- -----------------------------------------------------
-- Schema infra_supply
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `infra_supply` DEFAULT CHARACTER SET utf8 ;
USE `infra_supply` ;

-- -----------------------------------------------------
-- Table `infra_supply`.`PROJECT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `infra_supply`.`PROJECT` ;

CREATE TABLE IF NOT EXISTS `infra_supply`.`PROJECT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(45) NULL,
  `OWNER` VARCHAR(45) NULL,
  `CREATE_TIME` DATETIME NULL,
  `STATUS` INT NULL,
  `NOTE` VARCHAR(100) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `infra_supply`.`CONTRACT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `infra_supply`.`CONTRACT` ;

CREATE TABLE IF NOT EXISTS `infra_supply`.`CONTRACT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(45) NULL,
  `NAME` VARCHAR(45) NULL,
  `PROGRESS_PERCENT` INT NULL,
  `PROGRESS_DESC` VARCHAR(45) NULL,
  `PARTNER` VARCHAR(45) NULL,
  `OWNER` VARCHAR(45) NULL,
  `NOTE` VARCHAR(100) NULL,
  `STATUS` INT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `infra_supply`.`BBBG`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `infra_supply`.`BBBG` ;

CREATE TABLE IF NOT EXISTS `infra_supply`.`BBBG` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(45) NULL,
  `NAME` VARCHAR(45) NULL,
  `SUBMITTER` VARCHAR(45) NULL,
  `APPROVED_TIME` DATETIME NULL,
  `ATTACH_FILE` VARCHAR(200) NULL,
  `NOTE` VARCHAR(100) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `infra_supply`.`UNIT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `infra_supply`.`UNIT` ;

CREATE TABLE IF NOT EXISTS `infra_supply`.`UNIT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(45) NULL,
  `NAME` VARCHAR(45) NULL,
  `PARENT_ID` INT NULL,
  `OWNER` VARCHAR(45) NULL,
  `NOTE` VARCHAR(100) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `infra_supply`.`POOL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `infra_supply`.`POOL` ;

CREATE TABLE IF NOT EXISTS `infra_supply`.`POOL` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(45) NULL,
  `NAME` VARCHAR(45) NULL,
  `NOTE` VARCHAR(45) NULL,
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC),
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `infra_supply`.`HA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `infra_supply`.`HA` ;

CREATE TABLE IF NOT EXISTS `infra_supply`.`HA` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(45) NULL,
  `NAME` VARCHAR(45) NULL,
  `INFRA_TYPE` INT NULL,
  `SUPPLY_TYPE` INT NULL,
  `NOTE` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
