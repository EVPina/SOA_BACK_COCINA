package com.soa.soacocina.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProduccionDTO {
    
    @JsonProperty("id")
    private UUID id;
    
    @JsonProperty("ordenId")
    @NotNull(message = "El ID de la orden es requerido")
    private UUID ordenId;
    
    @JsonProperty("productoNombre")
    @NotBlank(message = "El nombre del producto es requerido")
    private String productoNombre;
    
    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
    
    @JsonProperty("estado")
    private String estado;
    
    @JsonProperty("notas")
    private String notas;
    
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}