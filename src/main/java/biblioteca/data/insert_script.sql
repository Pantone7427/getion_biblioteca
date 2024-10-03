-- Script para insertar datos iniciales en la base de datos de la biblioteca

-- Insertar usuarios
INSERT INTO usuario (nombre, apellido) VALUES ('Juan', 'Pérez');
INSERT INTO usuario (nombre, apellido) VALUES ('Ana', 'García');
INSERT INTO usuario (nombre, apellido) VALUES ('Luis', 'Martínez');
INSERT INTO usuario (nombre, apellido) VALUES ('María', 'López');
INSERT INTO usuario (nombre, apellido) VALUES ('Carlos', 'Sánchez');

-- Insertar bibliotecarios
INSERT INTO bibliotecario (nombre, apellido) VALUES ('Luis', 'Martínez');
INSERT INTO bibliotecario (nombre, apellido) VALUES ('Carmen', 'Fernández');
INSERT INTO bibliotecario (nombre, apellido) VALUES ('Diego', 'Rodríguez');
INSERT INTO bibliotecario (nombre, apellido) VALUES ('Laura', 'Gómez');
INSERT INTO bibliotecario (nombre, apellido) VALUES ('Elena', 'Ruiz');

-- Insertar libros
INSERT INTO libro (titulo, autor, isbn, disponible) VALUES ('El Quijote', 'Miguel de Cervantes', '1234567890123', TRUE);
INSERT INTO libro (titulo, autor, isbn, disponible) VALUES ('Cien Años de Soledad', 'Gabriel García Márquez', '1234567890124', TRUE);
INSERT INTO libro (titulo, autor, isbn, disponible) VALUES ('1984', 'George Orwell', '1234567890125', TRUE);
INSERT INTO libro (titulo, autor, isbn, disponible) VALUES ('Don Juan Tenorio', 'José Zorrilla', '1234567890126', TRUE);
INSERT INTO libro (titulo, autor, isbn, disponible) VALUES ('La Casa de los Espíritus', 'Isabel Allende', '1234567890127', TRUE);