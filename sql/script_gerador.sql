-- MySQL Workbench Forward Engineering

        SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
        SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
        SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

        -- -----------------------------------------------------
        -- Schema mydb
        -- -----------------------------------------------------
        -- -----------------------------------------------------
        -- Schema hotel
        -- -----------------------------------------------------

        -- -----------------------------------------------------
        -- Schema hotel
        -- -----------------------------------------------------
        CREATE SCHEMA IF NOT EXISTS `hotel` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
        USE `hotel` ;

        -- -----------------------------------------------------
        -- Table `hotel`.`hospede`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `hotel`.`hospede` (
        `id_hospede` INT(11) NOT NULL AUTO_INCREMENT,
        `hospede_nome` VARCHAR(150) NOT NULL,
        `hospede_endereco` VARCHAR(150) NOT NULL,
        `hospede_cpf` INT(11) NOT NULL,
        `telefone` INT(11) NOT NULL,
        `checkin` VARCHAR(12) NOT NULL,
        `checkout` VARCHAR(12) NOT NULL,
        PRIMARY KEY (`id_hospede`),
        UNIQUE INDEX `id_hospede_UNIQUE` (`id_hospede` ASC) VISIBLE,
        UNIQUE INDEX `hospede_cpf_UNIQUE` (`hospede_cpf` ASC) VISIBLE)
        ENGINE = InnoDB
        AUTO_INCREMENT = 13
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_0900_ai_ci;


        -- -----------------------------------------------------
        -- Table `hotel`.`quarto`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `hotel`.`quarto` (
        `id_quarto` INT(11) NOT NULL AUTO_INCREMENT,
        `no_quarto` INT(11) NOT NULL,
        `preco_quarto` FLOAT NOT NULL,
        `tipo_quarto` VARCHAR(45) NOT NULL,
        `hospede_quarto` INT(11) NOT NULL,
        PRIMARY KEY (`id_quarto`),
        UNIQUE INDEX `idquarto_UNIQUE` (`id_quarto` ASC) VISIBLE,
        UNIQUE INDEX `no_quarto_UNIQUE` (`no_quarto` ASC) VISIBLE)
        ENGINE = InnoDB
        AUTO_INCREMENT = 5
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_0900_ai_ci;


        -- -----------------------------------------------------
        -- Table `hotel`.`servico`
        -- -----------------------------------------------------
        CREATE TABLE IF NOT EXISTS `hotel`.`servico` (
        `id_servico` INT(11) NOT NULL AUTO_INCREMENT,
        `tipo_servico` VARCHAR(45) NOT NULL,
        `preco_servico` FLOAT NOT NULL,
        `hospede_servico` INT(11) NOT NULL,
        PRIMARY KEY (`id_servico`),
        UNIQUE INDEX `id_servico_UNIQUE` (`id_servico` ASC) VISIBLE)
        ENGINE = InnoDB
        AUTO_INCREMENT = 9
        DEFAULT CHARACTER SET = utf8mb4
        COLLATE = utf8mb4_0900_ai_ci;


        SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
