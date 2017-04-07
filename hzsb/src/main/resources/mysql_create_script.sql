CREATE TABLE `paises` (
  `id_pais` int(11) NOT NULL,
  `nombre` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `prefijo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_pais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

CREATE TABLE `hazelcast_tareasdiferidas` (
  `fecha_operativa` date NOT NULL,
  `numero_transaccion` bigint(20) NOT NULL,
  `serialized_object` longblob NOT NULL,
  PRIMARY KEY (`fecha_operativa`,`numero_transaccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
