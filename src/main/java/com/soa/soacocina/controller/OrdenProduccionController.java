package com.soa.soacocina.controller;

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

import com.soa.soacocina.dto.EstadoRequestDTO;
import com.soa.soacocina.dto.OrdenProduccionDTO;
import com.soa.soacocina.service.OrdenProduccionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ordenes-produccion")
@RequiredArgsConstructor
@Tag(name = "Órdenes de Producción", description = "API para gestionar órdenes de producción de cocina")
@CrossOrigin(origins = "*")
public class OrdenProduccionController {
    
    private final OrdenProduccionService ordenService;
    
    @PostMapping
    @Operation(summary = "Crear nueva orden de producción")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<OrdenProduccionDTO> crearOrden(@Valid @RequestBody OrdenProduccionDTO ordenDTO) {
        OrdenProduccionDTO nuevaOrden = ordenService.crearOrden(ordenDTO);
        return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
    }
    
    @PatchMapping("/{ordenId}/estado")
    @Operation(summary = "Actualizar estado de una orden")
    public ResponseEntity<OrdenProduccionDTO> actualizarEstado(
            @PathVariable UUID ordenId,
            @Valid @RequestBody EstadoRequestDTO estadoRequest) {
        OrdenProduccionDTO ordenActualizada = ordenService.actualizarEstado(ordenId, estadoRequest);
        return ResponseEntity.ok(ordenActualizada);
    }
    
    @GetMapping("/{ordenId}")
    @Operation(summary = "Obtener orden por ID")
    public ResponseEntity<OrdenProduccionDTO> getOrdenById(@PathVariable UUID ordenId) {
        OrdenProduccionDTO orden = ordenService.getOrdenById(ordenId);
        return ResponseEntity.ok(orden);
    }
    
    @GetMapping("/activas")
    @Operation(summary = "Obtener órdenes activas (PENDIENTE, PREPARANDO, LISTO)")
    public ResponseEntity<List<OrdenProduccionDTO>> getOrdenesActivas() {
        List<OrdenProduccionDTO> ordenes = ordenService.getOrdenesActivas();
        return ResponseEntity.ok(ordenes);
    }
    
    @GetMapping("/por-estado/{estado}")
    @Operation(summary = "Obtener órdenes por estado")
    public ResponseEntity<List<OrdenProduccionDTO>> getOrdenesPorEstado(
            @Parameter(description = "Estado: PENDIENTE, PREPARANDO, LISTO, ENTREGADO")
            @PathVariable String estado) {
        List<OrdenProduccionDTO> ordenes = ordenService.getOrdenesPorEstado(estado);
        return ResponseEntity.ok(ordenes);
    }
    
    @GetMapping("/dashboard/stats")
    @Operation(summary = "Obtener estadísticas del dashboard")
    public ResponseEntity<?> getDashboardStats() {
        return ResponseEntity.ok(ordenService.getDashboardStats());
    }
}