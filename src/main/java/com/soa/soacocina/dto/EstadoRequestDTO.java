package com.soa.soacocina.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoRequestDTO {
    
    @JsonProperty("nuevoEstado")
    @NotNull(message = "El nuevo estado es requerido")
    private String nuevoEstado;
    
    @JsonProperty("usuarioId")
    @NotNull(message = "El ID del usuario es requerido")
    private UUID usuarioId;
}