CREATE TABLE `springmvc5`.`students` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NULL,
  `name` VARCHAR(30) NULL,
  `address` VARCHAR(255) NULL,
  `password` VARCHAR(20) NULL,
  `newsletter` INT NULL,
  `framework` VARCHAR(500) NULL,
  `sex` VARCHAR(1) NULL,
  `number` VARCHAR(45) NULL,
  `country` VARCHAR(10) NULL,
  `skill` VARCHAR(500) NULL,
  PRIMARY KEY (`id`));
