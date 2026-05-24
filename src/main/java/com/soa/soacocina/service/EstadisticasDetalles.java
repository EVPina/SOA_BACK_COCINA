package com.soa.soacocina.service;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class EstadisticasDetalles {
    private long totalDetalles;
    private List<ProductoEstadistica> topProductos;
}