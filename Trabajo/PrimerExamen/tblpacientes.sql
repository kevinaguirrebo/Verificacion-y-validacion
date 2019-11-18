-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-10-2019 a las 16:59:18
-- Versión del servidor: 10.4.6-MariaDB
-- Versión de PHP: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: primerexamen
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla tblpacientes
--

CREATE TABLE tblpacientes (
  PKDocumento varchar(15) NOT NULL,
  Nombre varchar(50) NOT NULL,
  Apellido varchar(50) NOT NULL,
  FechaNacimiento date NOT NULL,
  Correo varchar(50) NOT NULL,
  Telefono varchar(12) NOT NULL,
  FKCodigo_TblEps int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla tblpacientes
--

INSERT INTO tblpacientes (PKDocumento, Nombre, Apellido, FechaNacimiento, Correo, Telefono, FKCodigo_TblEps) VALUES
('1', 'asdfg', 'asdfgh', '2000-07-05', 'asd', '123', 1),
('2', 'acfg', 'fre', '2003-09-07', 'avrer', '134', 1),
('3', 'bhjgh', 'rthyt', '2000-10-08', 'thjyuj', '564765', 1),
('4', 'sgfd', 'dfgd', '2000-07-06', 'dfgdgf', '234', 1),
('5', 'gfhf', 'hfhgfh', '2000-09-08', 'sdgtgd', '234543', 2),
('6', 'sdfgfd', 'fgdfg', '2003-05-31', 'dgfdfg', '124234', 1),
('8', 'sgfd', 'fgedfdg', '2005-07-09', 'hjkhkh', '7874532', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla tblpacientes
--
ALTER TABLE tblpacientes
  ADD PRIMARY KEY (PKDocumento),
  ADD KEY FKCodigo_TblEps (FKCodigo_TblEps);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla tblpacientes
--
ALTER TABLE tblpacientes
  ADD CONSTRAINT tblpacientes_ibfk_1 FOREIGN KEY (FKCodigo_TblEps) REFERENCES tbleps (PKCodigo);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
