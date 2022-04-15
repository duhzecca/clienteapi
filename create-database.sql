-- -----------------------------------------------------
-- Schema cliente_adm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cliente_adm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `cliente_adm` ;

GRANT SELECT, UPDATE, INSERT, DELETE ON cliente_adm_user.* TO 'cliente_adm_user';

-- -----------------------------------------------------
-- Table `cliente_adm`.`lista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cliente_adm`.`lista` (
  `idlista` INT NOT NULL AUTO_INCREMENT,
  `nomeLista` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`idlista`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cliente_adm`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cliente_adm`.`cliente` (
  `idcliente` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(200) NOT NULL,
  `nome` VARCHAR(200) NOT NULL,
  `idLista` INT NULL DEFAULT NULL,
  PRIMARY KEY (`idcliente`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_1_idx` (`idLista` ASC) VISIBLE,
  CONSTRAINT `fk_1`
    FOREIGN KEY (`idLista`)
    REFERENCES `cliente_adm`.`lista` (`idlista`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cliente_adm`.`lista_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cliente_adm`.`lista_product` (
  `idlista` INT NOT NULL,
  `idproduct` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idlista`, `idproduct`),
  CONSTRAINT `fk_2`
    FOREIGN KEY (`idlista`)
    REFERENCES `cliente_adm`.`lista` (`idlista`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cliente_adm`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cliente_adm`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `user` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `cliente_adm`.`user` (`user`, `password`) VALUES ('user123', '123456');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
