-- Crear la base de dades
CREATE DATABASE IF NOT EXISTS gestio_productes;
USE gestio_productes;

-- Crear la taula de productes
CREATE TABLE IF NOT EXISTS productes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    preu DECIMAL(10, 2) NOT NULL
);

-- Inserir productes de la categoria de targetes gràfiques
INSERT INTO productes (nom, preu) VALUES
('NVIDIA GeForce RTX 4080', 1199.99),
('AMD Radeon RX 7900 XT', 899.99),
('NVIDIA GeForce RTX 4070 Ti', 799.99),
('AMD Radeon RX 6800 XT', 699.99),
('NVIDIA GeForce RTX 3060 Ti', 399.99);

