package com.soa.soacocina.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soa.soacocina.dto.EstadoRequestDTO;
import com.soa.soacocina.dto.OrdenProduccionDTO;
import com.soa.soacocina.service.OrdenProduccionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/detalles-produccion")
@RequiredArgsConstructor  // ← Agrega esta línea
public class OrdenProduccionAliasController {
        private final OrdenProduccionService ordenService;

       // ✅ Alias para el documento: /cocina/ordenes/activas
    @GetMapping("/ordenes/activas")
    public ResponseEntity<List<OrdenProduccionDTO>> getOrdenesActivasAlias() {
        // Redirigir al endpoint existente
        return ResponseEntity.ok(ordenService.getOrdenesActivas());
    }
       // ✅ Alias para /cocina/ordenes/{id}/estado
    @PutMapping("/ordenes/{id}/estado")
    public ResponseEntity<OrdenProduccionDTO> actualizarEstadoAlias(
            @PathVariable UUID id,
            @RequestParam String estado,
            @RequestParam UUID usuarioId) {
        EstadoRequestDTO request = new EstadoRequestDTO(estado, usuarioId);
        return ResponseEntity.ok(ordenService.actualizarEstado(id, request));
    }
    
    // ✅ Alias para /cocina/ordenes/pendientes
    @GetMapping("/ordenes/pendientes")
    public ResponseEntity<List<OrdenProduccionDTO>> getPendientesAlias() {
        return ResponseEntity.ok(ordenService.getOrdenesPorEstado("PENDIENTE"));
    }
    
    // ✅ Alias para /cocina/ordenes/en-preparacion
    @GetMapping("/ordenes/en-preparacion")
    public ResponseEntity<List<OrdenProduccionDTO>> getEnPreparacionAlias() {
        return ResponseEntity.ok(ordenService.getOrdenesPorEstado("PREPARANDO"));
    }
    
    // ✅ Alias para /cocina/ordenes/listos
    @GetMapping("/ordenes/listos")
    public ResponseEntity<List<OrdenProduccionDTO>> getListosAlias() {
        return ResponseEntity.ok(ordenService.getOrdenesPorEstado("LISTO"));
    }
}
