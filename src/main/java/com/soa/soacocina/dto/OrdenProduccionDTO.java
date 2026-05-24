package com.soa.soacocina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProduccionDTO {
    private UUID id;
    private UUID pedidoId;
    private Integer mesaNumero;
    private String estado;
    private UUID usuarioJefeId;
    private Integer tiempoPreparacionSegundos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer totalItems;
    private Integer itemsListos;
    private List<DetalleProduccionDTO> detalles;
}