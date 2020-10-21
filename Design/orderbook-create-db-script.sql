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
-- Table `orderbook`.`stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`stock` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`stock` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `symbol` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orderbook`.`stock_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`stock_order` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`stock_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `side` ENUM('SELL', 'BUY') NOT NULL,
  `status` ENUM('COMPLETED', 'IN-PROGRESS', 'CANCELLED') NOT NULL,
  `stock_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `datetime` TIMESTAMP NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
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
  `datetime` TIMESTAMP NOT NULL,
  `quantity` INT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `buy_order_id` INT NOT NULL,
  `sell_order_id` INT NOT NULL,
  `stock_id` INT NOT NULL,
  PRIMARY KEY (`id`, `buy_order_id`, `sell_order_id`),
  INDEX `fk_trade_stock_order1_idx` (`buy_order_id` ASC) VISIBLE,
  INDEX `fk_trade_stock_order2_idx` (`sell_order_id` ASC) VISIBLE,
  INDEX `fk_trade_stock1_idx` (`stock_id` ASC) VISIBLE,
  CONSTRAINT `fk_trade_stock_order1`
    FOREIGN KEY (`buy_order_id`)
    REFERENCES `orderbook`.`stock_order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trade_stock_order2`
    FOREIGN KEY (`sell_order_id`)
    REFERENCES `orderbook`.`stock_order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trade_stock1`
    FOREIGN KEY (`stock_id`)
    REFERENCES `orderbook`.`stock` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orderbook`.`order_transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orderbook`.`order_transaction` ;

CREATE TABLE IF NOT EXISTS `orderbook`.`order_transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `datetime` TIMESTAMP NOT NULL,
  `transactiontype` ENUM('CREATED', 'FULFILLED', 'PARTIALLY-FULFILLED', 'CANCELLED') NOT NULL,
  `stock_order_id` INT NOT NULL,
  PRIMARY KEY (`id`, `stock_order_id`),
  INDEX `fk_order_transaction_stock_order1_idx` (`stock_order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_transaction_stock_order1`
    FOREIGN KEY (`stock_order_id`)
    REFERENCES `orderbook`.`stock_order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
