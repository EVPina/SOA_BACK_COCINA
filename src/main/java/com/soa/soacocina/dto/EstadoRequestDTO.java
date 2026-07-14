package com.soa.soacocina.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoRequestDTO {
    
    @Schema(description = "nuevo estado del detalle de producción")
    @JsonProperty("nuevoEstado")
    @NotNull(message = "El nuevo estado es requerido")
    private String nuevoEstado;
    
    @Schema(description = "ID del usuario")
    @JsonProperty("usuarioId")
    @NotNull(message = "El ID del usuario es requerido")
    private UUID usuarioId;
}