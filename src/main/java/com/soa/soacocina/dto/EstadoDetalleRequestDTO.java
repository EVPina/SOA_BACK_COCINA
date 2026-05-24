package com.soa.soacocina.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDetalleRequestDTO {
    
    @NotNull(message = "El nuevo estado es requerido")
    private String nuevoEstado;
    
    @NotNull(message = "El ID del usuario es requerido")
    private UUID usuarioId;
}