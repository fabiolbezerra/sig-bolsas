-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sig_bolsas_bd
-- -----------------------------------------------------
-- Banco de dados do Sistema de Gestão de Bolsistas
DROP SCHEMA IF EXISTS `sig_bolsas_bd` ;

-- -----------------------------------------------------
-- Schema sig_bolsas_bd
--
-- Banco de dados do Sistema de Gestão de Bolsistas
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sig_bolsas_bd` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `sig_bolsas_bd` ;

-- -----------------------------------------------------
-- Table `sig_bolsas_bd`.`pessoa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sig_bolsas_bd`.`pessoa` ;

CREATE TABLE IF NOT EXISTS `sig_bolsas_bd`.`pessoa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome_completo` VARCHAR(50) NOT NULL,
  `nascimento` DATE NULL,
  `telefone` VARCHAR(11) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `senha` VARCHAR(100) NOT NULL DEFAULT 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sig_bolsas_bd`.`papel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sig_bolsas_bd`.`papel` ;

CREATE TABLE IF NOT EXISTS `sig_bolsas_bd`.`papel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `codigo` CHAR NOT NULL COMMENT 'C - Coordenador\nP - Pesquisador\nB - Bolsista\nG - Gerente\nA - Administrador',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sig_bolsas_bd`.`pessoa_papel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sig_bolsas_bd`.`pessoa_papel` ;

CREATE TABLE IF NOT EXISTS `sig_bolsas_bd`.`pessoa_papel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pessoa` INT NOT NULL,
  `papel` INT NOT NULL,
  INDEX `fk_pessoa_has_papel_papel1_idx` (`papel` ASC),
  INDEX `fk_pessoa_has_papel_pessoa_idx` (`pessoa` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_pessoa_has_papel_pessoa`
    FOREIGN KEY (`pessoa`)
    REFERENCES `sig_bolsas_bd`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pessoa_has_papel_papel1`
    FOREIGN KEY (`papel`)
    REFERENCES `sig_bolsas_bd`.`papel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sig_bolsas_bd`.`comite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sig_bolsas_bd`.`comite` ;

CREATE TABLE IF NOT EXISTS `sig_bolsas_bd`.`comite` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `portaria` VARCHAR(45) NOT NULL,
  `inicio` DATE NOT NULL,
  `fim` DATE NOT NULL,
  `coordenador` INT NOT NULL COMMENT 'Pessoa no papel de coordenador, que cadastrou o comitê',
  PRIMARY KEY (`id`),
  INDEX `fk_comite_pessoa1_idx` (`coordenador` ASC),
  CONSTRAINT `fk_comite_pessoa1`
    FOREIGN KEY (`coordenador`)
    REFERENCES `sig_bolsas_bd`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sig_bolsas_bd`.`edital`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sig_bolsas_bd`.`edital` ;

CREATE TABLE IF NOT EXISTS `sig_bolsas_bd`.`edital` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(45) NOT NULL,
  `ano` INT NOT NULL,
  `numero` VARCHAR(5) NOT NULL COMMENT 'Código do edital. \nEx: \nEdital 2025/001 PROPED\nNúmero = 2025/001',
  `descricao` VARCHAR(100) NULL,
  `data_cadastro` DATE NULL,
  `data_inicio` DATE NOT NULL,
  `data_final` DATE NOT NULL,
  `situacao` CHAR NOT NULL DEFAULT 'C' COMMENT 'Status do edital:\nC - Cadastrado\nP - Publicado\nN - Não publicado\nE - Encerrado',
  `tipo` CHAR NOT NULL DEFAULT 'P' COMMENT 'P - Pesquisa / E - Extensão / D - Desenvolvimento Tecnológico',
  `coordenador` INT NOT NULL COMMENT 'Pessoa no papel de coordenador, que cadastrou o edital',
  `comite` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_edital_pessoa1_idx` (`coordenador` ASC),
  INDEX `fk_edital_comite1_idx` (`comite` ASC),
  CONSTRAINT `fk_edital_pessoa1`
    FOREIGN KEY (`coordenador`)
    REFERENCES `sig_bolsas_bd`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_edital_comite1`
    FOREIGN KEY (`comite`)
    REFERENCES `sig_bolsas_bd`.`comite` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sig_bolsas_bd`.`comite_pessoa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sig_bolsas_bd`.`comite_pessoa` ;

CREATE TABLE IF NOT EXISTS `sig_bolsas_bd`.`comite_pessoa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comite` INT NOT NULL,
  `pessoa` INT NOT NULL COMMENT 'Pessoa deve ser um pesquisador\n',
  INDEX `fk_comite_has_pessoa_pessoa1_idx` (`pessoa` ASC),
  INDEX `fk_comite_has_pessoa_comite1_idx` (`comite` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_comite_has_pessoa_comite1`
    FOREIGN KEY (`comite`)
    REFERENCES `sig_bolsas_bd`.`comite` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comite_has_pessoa_pessoa1`
    FOREIGN KEY (`pessoa`)
    REFERENCES `sig_bolsas_bd`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
