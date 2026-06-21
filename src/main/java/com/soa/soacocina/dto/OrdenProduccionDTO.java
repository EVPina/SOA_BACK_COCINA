package com.soa.soacocina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProduccionDTO {
    
    
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("pedidoId")
    private UUID pedidoId;
   
    @JsonProperty("mesaNumero")
    private Integer mesaNumero;
   
    @JsonProperty("estado")
    private String estado;

    @JsonProperty("usuarioJefeId")
    private UUID usuarioJefeId;

    @JsonProperty("tiempoPreparacionSegundos")
    private Integer tiempoPreparacionSegundos;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("totalItems")
    private Integer totalItems;

    @JsonProperty("itemsListos")
    private Integer itemsListos;

    @JsonProperty("detalles")
    private List<DetalleProduccionDTO> detalles;
}