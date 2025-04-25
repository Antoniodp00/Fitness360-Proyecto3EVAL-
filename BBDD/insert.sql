-- =======================================
-- Insertar datos en la base de datos Fitness360
-- =======================================

-- 1) Insertar Clientes
INSERT INTO Cliente (nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, altura, estado)
VALUES
('ana123', 'Ana', 'Pérez', 'ana.perez@email.com', 'hashedPassword1', '123456789', '1990-05-15', 'F', 1.65, 'ACTIVO'),
('luis456', 'Luis', 'González', 'luis.gonzalez@email.com', 'hashedPassword2', '987654321', '1985-09-23', 'M', 1.80, 'ACTIVO'),
('maria789', 'María', 'López', 'maria.lopez@email.com', 'hashedPassword3', '456123789', '1992-02-11', 'F', 1.60, 'INACTIVO');

-- 2) Insertar Empleados
INSERT INTO Empleado (nombreUsuario, nombre, apellidos, correo, password, telefono, fechaNacimiento, sexo, descripcion, rol, estado)
VALUES
('jorge_trainer', 'Jorge', 'Martínez', 'jorge.martinez@email.com', 'hashedPassword4', '555123456', '1988-04-12', 'M', 'Entrenador personal', 'ENTRENADOR', 'ACTIVO'),
('carla_dietista', 'Carla', 'Fernández', 'carla.fernandez@email.com', 'hashedPassword5', '555987654', '1982-07-20', 'F', 'Dietista y nutricionista', 'DIETISTA', 'ACTIVO'),
('pedro_ambos', 'Pedro', 'Sánchez', 'pedro.sanchez@email.com', 'hashedPassword6', '555654321', '1990-12-01', 'M', 'Entrenador y dietista', 'AMBOS', 'ACTIVO');

-- 3) Insertar Tarifas
INSERT INTO Tarifa (nombre, precio, descripcion, periodo, idEmpleado)
VALUES
('Tarifa Básica', 30.00, 'Acceso a rutinas de ejercicio básicas', 'MENSUAL', 1),
('Tarifa Premium', 60.00, 'Acceso a rutinas personalizadas y seguimiento completo', 'MENSUAL', 2),
('Tarifa Anual', 300.00, 'Acceso anual a todos los servicios', 'ANUAL', 3);

-- 4) Insertar Dietas
INSERT INTO Dieta (nombre, descripcion, archivo, idEmpleado)
VALUES
('Dieta Básica', 'Dieta para mantener peso', 'dieta_basica.pdf', 2),
('Dieta Deportiva', 'Dieta para aumentar masa muscular', 'dieta_deportiva.pdf', 3),
('Dieta Vegana', 'Dieta basada en alimentos de origen vegetal', 'dieta_vegana.pdf', 2);

-- 5) Insertar Rutinas
INSERT INTO Rutina (nombre, descripcion, idCliente, idEmpleado)
VALUES
('Rutina para principiantes', 'Rutina básica para nuevos en el ejercicio', 1, 1),
('Rutina avanzada', 'Rutina para ganar masa muscular', 4, 1),
('Rutina de cardio', 'Rutina centrada en ejercicios cardiovasculares', 3, 1);

-- 6) Insertar Relación ClienteDieta
INSERT INTO ClienteDieta (idCliente, idDieta, fechaAsignacion, fechaFin)
VALUES
(1, 1, '2025-01-01', '2025-02-01'),
(4, 2, '2025-01-15', '2025-02-15'),
(3, 3, '2025-01-20', '2025-02-20');

-- 7) Insertar Relación UsuarioRutina
INSERT INTO UsuarioRutina (idUsuario, idRutina, fechaAsignacion, fechaFin)
VALUES
(1, 7, '2025-01-01', '2025-01-31'),
(4, 8, '2025-01-15', '2025-02-15'),
(3, 9, '2025-01-20', '2025-02-20');

-- 8) Insertar Relación ClienteTarifa
INSERT INTO ClienteTarifa (idCliente, idTarifa, estado, fechaContratacion, fechaRenovacion, fechaFin)
VALUES
(1, 1, 'ACTIVA', '2025-01-01', '2025-02-01', '2025-02-01'),
(4, 2, 'ACTIVA', '2025-01-15', '2025-02-15', '2025-02-15'),
(3, 3, 'SUSPENDIDA', '2025-01-20', '2025-02-20', '2025-02-20');

-- 9) Insertar Revisiones
INSERT INTO Revision (fecha, peso, grasa, musculo, mPecho, mCintura, mCadera, observaciones, imagen, idCliente, idEmpleado)
VALUES
('2025-01-01', 70.5, 18.0, 32.5, 95.0, 80.0, 90.0, 'Buena forma física', 'revision1.jpg', 1, 1),
('2025-01-15', 85.0, 20.0, 35.0, 100.0, 85.0, 95.0, 'Necesita más trabajo en cardio', 'revision2.jpg', 4, 1),
('2025-01-20', 65.0, 22.0, 30.0, 90.0, 78.0, 88.0, 'Progreso moderado', 'revision3.jpg', 3, 2);

