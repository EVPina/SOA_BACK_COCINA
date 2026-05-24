package com.soa.soacocina.controller;

import com.soa.soacocina.dto.DetalleProduccionDTO;
import com.soa.soacocina.dto.EstadoDetalleRequestDTO;
import com.soa.soacocina.service.DetalleProduccionService;
import com.soa.soacocina.service.EstadisticasDetalles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/detalles-produccion")
@RequiredArgsConstructor
@Tag(name = "Detalles de Producción", description = "API para gestionar los detalles de producción de la cocina")
@CrossOrigin(origins = "*")
public class DetalleProduccionController {
    
    private final DetalleProduccionService detalleService;
    
    @PostMapping
    @Operation(summary = "Crear un nuevo detalle de producción")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public ResponseEntity<DetalleProduccionDTO> crearDetalle(@Valid @RequestBody DetalleProduccionDTO detalleDTO) {
        DetalleProduccionDTO nuevoDetalle = detalleService.crearDetalle(detalleDTO);
        return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
    }
    
    @PatchMapping("/{detalleId}/estado")
    @Operation(summary = "Actualizar estado de un detalle")
    public ResponseEntity<DetalleProduccionDTO> actualizarEstadoDetalle(
            @PathVariable UUID detalleId,
            @Valid @RequestBody EstadoDetalleRequestDTO estadoRequest) {
        DetalleProduccionDTO detalleActualizado = detalleService.actualizarEstadoDetalle(
            detalleId, 
            estadoRequest.getNuevoEstado(), 
            estadoRequest.getUsuarioId()
        );
        return ResponseEntity.ok(detalleActualizado);
    }
    
    @GetMapping("/orden/{ordenId}")
    @Operation(summary = "Obtener todos los detalles de una orden específica")
    public ResponseEntity<List<DetalleProduccionDTO>> getDetallesByOrdenId(@PathVariable UUID ordenId) {
        List<DetalleProduccionDTO> detalles = detalleService.getDetallesByOrdenId(ordenId);
        return ResponseEntity.ok(detalles);
    }
    
    @GetMapping("/{detalleId}")
    @Operation(summary = "Obtener detalle por ID")
    public ResponseEntity<DetalleProduccionDTO> getDetalleById(@PathVariable UUID detalleId) {
        DetalleProduccionDTO detalle = detalleService.getDetalleById(detalleId);
        return ResponseEntity.ok(detalle);
    }
    
    @GetMapping("/por-estado/{estado}")
    @Operation(summary = "Obtener detalles por estado")
    public ResponseEntity<List<DetalleProduccionDTO>> getDetallesByEstado(
            @Parameter(description = "Estado: PENDIENTE, PREPARANDO, LISTO")
            @PathVariable String estado) {
        List<DetalleProduccionDTO> detalles = detalleService.getDetallesByEstado(estado);
        return ResponseEntity.ok(detalles);
    }
    
    @PutMapping("/{detalleId}")
    @Operation(summary = "Actualizar detalle completo")
    public ResponseEntity<DetalleProduccionDTO> actualizarDetalle(
            @PathVariable UUID detalleId,
            @Valid @RequestBody DetalleProduccionDTO detalleDTO) {
        DetalleProduccionDTO detalleActualizado = detalleService.actualizarDetalle(detalleId, detalleDTO);
        return ResponseEntity.ok(detalleActualizado);
    }
    
    @DeleteMapping("/{detalleId}")
    @Operation(summary = "Eliminar detalle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<Void> eliminarDetalle(@PathVariable UUID detalleId) {
        detalleService.eliminarDetalle(detalleId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/estadisticas")
    @Operation(summary = "Obtener estadísticas de detalles")
    public ResponseEntity<EstadisticasDetalles> getEstadisticas() {
        EstadisticasDetalles estadisticas = detalleService.getEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }
}