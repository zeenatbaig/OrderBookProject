-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema orderbook
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `orderbook` ;

-- -----------------------------------------------------
-- Schema orderbook
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `orderbook` DEFAULT CHARACTER SET utf8 ;
USE `orderbook` ;

-- -----------------------------------------------------
-- Table `orderbook`.`party`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`party` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`party` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `symbol` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `symbol_UNIQUE` (`symbol` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orderbook`.`stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`stock` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`stock` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(10,2) NOT NULL,
  `tickSize` DECIMAL(3,2) NOT NULL,
  `party_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_stock_party1_idx` (`party_id` ASC) VISIBLE,
  CONSTRAINT `fk_stock_party1`
    FOREIGN KEY (`party_id`)
    REFERENCES `orderbook`.`party` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orderbook`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`order` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `side` ENUM('SELL', 'BUY') NOT NULL,
  `status` ENUM('COMPLETED', 'IN-PROGRESS', 'CANCELLED') NOT NULL,
  `stock_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `dateTime` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`, `stock_id`),
  INDEX `fk_order_stock1_idx` (`stock_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_stock1`
    FOREIGN KEY (`stock_id`)
    REFERENCES `orderbook`.`stock` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orderbook`.`trade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`trade` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`trade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `buy_order_id` INT NOT NULL,
  `sell_order_id` INT NOT NULL,
  `dateTime` TIMESTAMP NOT NULL,
  `quantity` INT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `stock_id` INT NOT NULL,
  PRIMARY KEY (`id`, `buy_order_id`, `sell_order_id`),
  INDEX `fk_trade_order_idx` (`buy_order_id` ASC) VISIBLE,
  INDEX `fk_trade_order1_idx` (`sell_order_id` ASC) VISIBLE,
  INDEX `fk_trade_stock1_idx` (`stock_id` ASC) VISIBLE,
  CONSTRAINT `fk_trade_order`
    FOREIGN KEY (`buy_order_id`)
    REFERENCES `orderbook`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trade_order1`
    FOREIGN KEY (`sell_order_id`)
    REFERENCES `orderbook`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trade_stock1`
    FOREIGN KEY (`stock_id`)
    REFERENCES `orderbook`.`stock` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orderbook`.`history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`history` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dateTime` TIMESTAMP NOT NULL,
  `trade_id` INT NOT NULL,
  `trade_buy_order_id` INT NOT NULL,
  `trade_sell_order_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_history_trade1_idx` (`trade_id` ASC, `trade_buy_order_id` ASC, `trade_sell_order_id` ASC) VISIBLE,
  CONSTRAINT `fk_history_trade1`
    FOREIGN KEY (`trade_id` , `trade_buy_order_id` , `trade_sell_order_id`)
    REFERENCES `orderbook`.`trade` (`id` , `buy_order_id` , `sell_order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orderbook`.`orderTransaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`orderTransaction` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`orderTransaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `order_stock_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `dateTime` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`, `order_id`, `order_stock_id`),
  INDEX `fk_orderTransaction_order1_idx` (`order_id` ASC, `order_stock_id` ASC) VISIBLE,
  CONSTRAINT `fk_orderTransaction_order1`
    FOREIGN KEY (`order_id` , `order_stock_id`)
    REFERENCES `orderbook`.`order` (`id` , `stock_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
