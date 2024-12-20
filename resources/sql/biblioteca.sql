-- Creació de la base de dades
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- Taula dels llibres
CREATE TABLE IF NOT EXISTS llibres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titol VARCHAR(200) NOT NULL,
    any_publicacio INT NOT NULL
);

-- Taula dels autors
CREATE TABLE IF NOT EXISTS autors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    nacionalitat VARCHAR(100) NOT NULL
);

-- Taula intermitja per la relació molts-a-molts entre llibres i autors
CREATE TABLE IF NOT EXISTS llibres_autors (
    llibre_id INT NOT NULL,
    autor_id INT NOT NULL,
    PRIMARY KEY (llibre_id, autor_id),
    FOREIGN KEY (llibre_id) REFERENCES llibres(id) ON DELETE CASCADE,
    FOREIGN KEY (autor_id) REFERENCES autors(id) ON DELETE CASCADE
);

-- Taula dels préstecs
CREATE TABLE IF NOT EXISTS prestecs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    llibre_id INT NOT NULL,
    usuari VARCHAR(100) NOT NULL,
    data_prestec DATE NOT NULL,
    data_devolucio DATE,
    FOREIGN KEY (llibre_id) REFERENCES llibres(id) ON DELETE CASCADE
);

-- Inserció de llibres
INSERT INTO llibres (titol, any_publicacio) VALUES
('1984', 1949),
('Brave New World', 1932),
('Fahrenheit 451', 1953),
('To Kill a Mockingbird', 1960),
('The Great Gatsby', 1925);

-- Inserció d'autors
INSERT INTO autors (nom, nacionalitat) VALUES
('George Orwell', 'Regne Unit'),
('Aldous Huxley', 'Regne Unit'),
('Ray Bradbury', 'Estats Units'),
('Harper Lee', 'Estats Units'),
('F. Scott Fitzgerald', 'Estats Units');

-- Relació molts-a-molts entre llibres i autors
INSERT INTO llibres_autors (llibre_id, autor_id) VALUES
(1, 1), -- 1984 - George Orwell
(2, 2), -- Brave New World - Aldous Huxley
(3, 3), -- Fahrenheit 451 - Ray Bradbury
(4, 4), -- To Kill a Mockingbird - Harper Lee
(5, 5); -- The Great Gatsby - F. Scott Fitzgerald

-- Inserció de préstecs
INSERT INTO prestecs (llibre_id, usuari, data_prestec, data_devolucio) VALUES
(1, 'Joan Garcia', '2024-01-10', '2024-01-20'),
(2, 'Maria López', '2024-02-05', '2024-02-15'),
(3, 'Pere Martí', '2024-03-01', NULL), -- Encara no retornat
(4, 'Anna Puig', '2024-04-12', '2024-04-20'),
(5, 'Carlos Ferrer', '2024-05-18', NULL); -- Encara no retornat
