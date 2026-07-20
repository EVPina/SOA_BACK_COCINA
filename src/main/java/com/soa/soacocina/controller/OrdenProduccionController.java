package com.soa.soacocina.controller;

import com.soa.soacocina.dto.EstadoRequestDTO;
import com.soa.soacocina.dto.OrdenProduccionDTO;
import com.soa.soacocina.service.OrdenProduccionService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/ordenes-produccion")
@RequiredArgsConstructor
@Tag(name = "Órdenes de Producción", description = "API para gestionar órdenes de producción")

public class OrdenProduccionController {

    private final OrdenProduccionService ordenService;

    @PostMapping
    @Operation(summary = "Crear una nueva orden de producción")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<OrdenProduccionDTO> crearOrden(@Valid @RequestBody OrdenProduccionDTO ordenDTO) {
        OrdenProduccionDTO nuevaOrden = ordenService.crearOrden(ordenDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public ResponseEntity<OrdenProduccionDTO> obtenerOrden(@PathVariable UUID id) {
        return ResponseEntity.ok(ordenService.getOrdenById(id));
    }

    @GetMapping("/activas")
    @Operation(summary = "Obtener órdenes activas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Órdenes obtenidas exitosamente")
    })
    public ResponseEntity<List<OrdenProduccionDTO>> getOrdenesActivas() {
        return ResponseEntity.ok(ordenService.getOrdenesActivas());
    }

   @PatchMapping("/{id}/estado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    public ResponseEntity<OrdenProduccionDTO> actualizarEstado(
            @PathVariable UUID id,
            @RequestBody EstadoRequestDTO estadoRequest) {
        return ResponseEntity.ok(ordenService.actualizarEstado(id, estadoRequest));
    }

    @GetMapping("/por-estado/{estado}")
    @Operation(summary = "Obtener órdenes por estado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Órdenes obtenidas exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<List<OrdenProduccionDTO>> getOrdenesPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(ordenService.getOrdenesPorEstado(estado));
    }
}
