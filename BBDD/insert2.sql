-- Insertar tarifas asociadas al empleado 5
INSERT INTO Tarifa (nombre, precio, descripcion, periodo, idEmpleado) VALUES
('Tarifa Premium', 59.99, 'Acceso completo a entrenamientos y dietas personalizadas', 'MENSUAL', 5),
('Tarifa Nutrición', 39.99, 'Asesoramiento nutricional completo', 'MENSUAL', 5);

-- Insertar rutina del empleado 5
INSERT INTO Rutina (nombre, descripcion, idCliente, idEmpleado) VALUES
('Rutina Fuerza Avanzada', 'Entrenamiento de fuerza con énfasis en hipertrofia', 11, 5),
('Rutina Quema Grasa', 'Circuito de alta intensidad para pérdida de grasa', 11, 5);

-- Insertar dieta del empleado 5
INSERT INTO Dieta (nombre, descripcion, idEmpleado) VALUES
('Dieta Hipocalórica', 'Reducción calórica para definir masa muscular', 5),
('Dieta Alto Rendimiento', 'Dieta enfocada en deportistas de alto rendimiento', 5);

-- Asignar tarifas a clientes
INSERT INTO ClienteTarifa (idCliente, idTarifa, estado, fechaContratacion) VALUES
(11, 6, 'ACTIVA', '2025-04-01'),
(11, 7, 'ACTIVA', '2025-04-01');

-- Asignar rutina a cliente mediante UsuarioRutina
INSERT INTO UsuarioRutina (idUsuario, idRutina, fechaAsignacion) VALUES
(11, 13, '2025-04-02'),
(11, 14, '2025-04-02');

-- Asignar dieta a cliente
INSERT INTO ClienteDieta (idCliente, idDieta, fechaAsignacion) VALUES
(11, 6, '2025-04-03'),
(11, 7, '2025-04-03');

-- Insertar revisiones realizadas por el empleado
INSERT INTO Revision (fecha, peso, grasa, musculo, mPecho, mCintura, mCadera, observaciones, idCliente, idEmpleado) VALUES
('2025-04-10', 78.5, 18.2, 38.1, 100.0, 85.0, 95.0, 'Progreso positivo', 11, 5),
('2025-04-10', 66.3, 22.5, 31.2, 90.0, 75.0, 92.0, 'Mejorar alimentación', 11, 5);