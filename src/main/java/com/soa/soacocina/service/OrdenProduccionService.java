package com.soa.soacocina.service;

import com.soa.soacocina.dto.DetalleProduccionDTO;
import com.soa.soacocina.dto.OrdenProduccionDTO;
import com.soa.soacocina.dto.EstadoRequestDTO;
import com.soa.soacocina.exception.ResourceNotFoundException;
import com.soa.soacocina.model.*;
import com.soa.soacocina.repository.DetalleProduccionRepository;
import com.soa.soacocina.repository.OrdenProduccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdenProduccionService {
    
    private final OrdenProduccionRepository ordenRepository;
    private final DetalleProduccionRepository detalleRepository;
    
   @Transactional
    public OrdenProduccionDTO crearOrden(OrdenProduccionDTO ordenDTO) {
        log.info("Creando orden de producción para pedido: {}", ordenDTO.getPedidoId());
        
        OrdenProduccion orden = new OrdenProduccion();
        orden.setPedidoId(ordenDTO.getPedidoId());
        orden.setMesaNumero(ordenDTO.getMesaNumero());
        orden.setUsuarioJefeId(ordenDTO.getUsuarioJefeId());
        orden.setEstado(EstadoProduccion.PENDIENTE);
        
        OrdenProduccion saved = ordenRepository.save(orden);
        return convertToDTO(saved);
    }
    
    @Transactional
    public OrdenProduccionDTO actualizarEstado(UUID ordenId, EstadoRequestDTO estadoRequest) {
        log.info("Actualizando estado de orden {} a {}", ordenId, estadoRequest.getNuevoEstado());
        
        // CORRECCIÓN: Lambda simplificada
        OrdenProduccion orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + ordenId));
        
        EstadoProduccion nuevoEstado = EstadoProduccion.valueOf(estadoRequest.getNuevoEstado());
        orden.setEstado(nuevoEstado);
        
        OrdenProduccion updatedOrden = ordenRepository.save(orden);
        log.info("Estado de orden {} actualizado exitosamente", ordenId);
        
        return convertToDTO(updatedOrden);
    }
    
    @Transactional(readOnly = true)
    public OrdenProduccionDTO getOrdenById(UUID id) {
        // CORRECCIÓN: Lambda simplificada
        OrdenProduccion orden = ordenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + id));
        return convertToDTO(orden);
    }
    
    @Transactional(readOnly = true)
    public List<OrdenProduccionDTO> getOrdenesActivas() {
        List<EstadoProduccion> estadosActivos = List.of(
            EstadoProduccion.PENDIENTE, 
            EstadoProduccion.PREPARANDO, 
            EstadoProduccion.LISTO
        );
        
        List<OrdenProduccion> ordenes = ordenRepository.findActiveOrders(estadosActivos);
        return ordenes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<OrdenProduccionDTO> getOrdenesPorEstado(String estado) {
        EstadoProduccion estadoEnum = EstadoProduccion.valueOf(estado);
        List<OrdenProduccion> ordenes = ordenRepository.findByEstado(estadoEnum);
        return ordenes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public DashboardStats getDashboardStats() {
        long pendientes = ordenRepository.countByEstado(EstadoProduccion.PENDIENTE);
        long preparando = ordenRepository.countByEstado(EstadoProduccion.PREPARANDO);
        long listo = ordenRepository.countByEstado(EstadoProduccion.LISTO);
        long entregado = ordenRepository.countByEstado(EstadoProduccion.ENTREGADO);
        
        Double tiempoPromedio = ordenRepository.getAveragePreparationTime();
        
        return DashboardStats.builder()
                .ordenesPendientes(pendientes)
                .ordenesPreparando(preparando)
                .ordenesListas(listo)
                .ordenesEntregadas(entregado)
                .tiempoPreparacionPromedio(tiempoPromedio != null ? tiempoPromedio : 0.0)
                .build();
    }
    
    private OrdenProduccionDTO convertToDTO(OrdenProduccion orden) {
        long totalItems = 0;
        long itemsListos = 0;
        
        if (orden.getId() != null) {
            totalItems = detalleRepository.countByOrdenIdAndEstado(orden.getId(), null);
            itemsListos = detalleRepository.countByOrdenIdAndEstado(orden.getId(), EstadoDetalle.LISTO);
        }
        
        List<DetalleProduccionDTO> detallesDTO = null;
        if (orden.getDetalles() != null) {
            detallesDTO = orden.getDetalles().stream()
                .map(this::convertDetalleToDTO)
                .collect(Collectors.toList());
        }
        
        return OrdenProduccionDTO.builder()
                .id(orden.getId())
                .pedidoId(orden.getPedidoId())
                .mesaNumero(orden.getMesaNumero())
                .estado(orden.getEstado().toString())
                .usuarioJefeId(orden.getUsuarioJefeId())
                .tiempoPreparacionSegundos(orden.getTiempoPreparacionSegundos())
                .createdAt(orden.getCreatedAt())
                .updatedAt(orden.getUpdatedAt())
                .totalItems((int) totalItems)
                .itemsListos((int) itemsListos)
                .detalles(detallesDTO)
                .build();
    }
    
    private DetalleProduccionDTO convertDetalleToDTO(DetalleProduccion detalle) {
        return DetalleProduccionDTO.builder()
                .id(detalle.getId())
                .ordenId(detalle.getOrden().getId())
                .productoNombre(detalle.getProductoNombre())
                .cantidad(detalle.getCantidad())
                .estado(detalle.getEstado().toString())
                .notas(detalle.getNotas())
                .createdAt(detalle.getCreatedAt())
                .build();
    }
}