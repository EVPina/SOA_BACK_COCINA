package com.soa.soacocina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProduccionDTO {
    
    @Schema(description = "ID de la orden de producción")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "ID del pedido asociado")
    @JsonProperty("pedidoId")
    private UUID pedidoId;
   
    @Schema(description = "Número de la mesa")
    @JsonProperty("mesaNumero")
    private Integer mesaNumero;
   
    @Schema(description = "Estado de la orden de producción")
    @JsonProperty("estado")
    private String estado;

    @Schema(description = "ID del usuario jefe")
    @JsonProperty("usuarioJefeId")
    private UUID usuarioJefeId;

    @Schema(description = "Tiempo de preparación en segundos")
    @JsonProperty("tiempoPreparacionSegundos")
    private Integer tiempoPreparacionSegundos;

    @Schema(description = "Fecha de creación")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de actualización")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @Schema(description = "Total de items")
    @JsonProperty("totalItems")
    private Integer totalItems;

    @Schema(description = "Items listos")
    @JsonProperty("itemsListos")
    private Integer itemsListos;

    @Schema(description = "Detalles de la orden")
    @JsonProperty("detalles")
    private List<DetalleProduccionDTO> detalles;
}