package com.soa.soacocina.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;   // <--- ¡AGREGA ESTO!


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProduccionDTO {
    

    @Schema(description = "ID detalle de producción")
    @JsonProperty("id")
    private UUID id;
    
    @Schema(description = "ID de la orden")
    @JsonProperty("ordenId")
    @NotNull(message = "El ID de la orden es requerido")
    private UUID ordenId;
    
    @Schema(description = "Nombre del producto")
    @JsonProperty("productoNombre")
    @NotBlank(message = "El nombre del producto es requerido")
    private String productoNombre;
    
    @Schema(description = "Cantidad del producto")
    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
    
    @Schema(description = "Estado del detalle")
    @JsonProperty("estado")
    private String estado;
    
    @Schema(description = "Notas del detalle")
    @JsonProperty("notas")
    private String notas;
    
    @Schema(description = "Fecha de creación")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}