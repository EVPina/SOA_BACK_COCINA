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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProduccionDTO {
    
    private UUID id;
    
    @NotNull(message = "El ID de la orden es requerido")
    private UUID ordenId;
    
    @NotBlank(message = "El nombre del producto es requerido")
    private String productoNombre;
    
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;
    
    private String estado;
    
    private String notas;
    
    private LocalDateTime createdAt;
}