package com.soa.soacocina.service;

import com.soa.soacocina.dto.DetalleProduccionDTO;
import com.soa.soacocina.exception.ResourceNotFoundException;
import com.soa.soacocina.model.DetalleProduccion;
import com.soa.soacocina.model.EstadoDetalle;
import com.soa.soacocina.model.OrdenProduccion;
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
public class DetalleProduccionService {
    
    private final DetalleProduccionRepository detalleRepository;
    private final OrdenProduccionRepository ordenRepository;
    
    @Transactional
    public DetalleProduccionDTO crearDetalle(DetalleProduccionDTO detalleDTO) {
        log.info("Creando nuevo detalle de producción para orden: {}", detalleDTO.getOrdenId());
        
         // ✅ Verificar que ordenId no sea null
        if (detalleDTO.getOrdenId() == null) {
            log.error("ordenId es null");
            throw new IllegalArgumentException("ordenId no puede ser null");
        }
        
        OrdenProduccion orden = ordenRepository.findById(detalleDTO.getOrdenId())
                .orElseThrow(() -> {
                    log.error("Orden no encontrada con ID: {}", detalleDTO.getOrdenId());
                    return new ResourceNotFoundException("Orden no encontrada con ID: " + detalleDTO.getOrdenId());
                });
        
        DetalleProduccion detalle = new DetalleProduccion();
        detalle.setOrden(orden);
        detalle.setProductoNombre(detalleDTO.getProductoNombre());
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.setNotas(detalleDTO.getNotas());
        detalle.setEstado(EstadoDetalle.PENDIENTE);
        
        DetalleProduccion savedDetalle = detalleRepository.save(detalle);
        log.info("Detalle creado exitosamente con ID: {}", savedDetalle.getId());
        
        return convertToDTO(savedDetalle);
    }
    
    @Transactional
    public DetalleProduccionDTO actualizarEstadoDetalle(UUID detalleId, String nuevoEstado, UUID usuarioId) {
        log.info("Actualizando estado del detalle {} a {}", detalleId, nuevoEstado);
        
        DetalleProduccion detalle = detalleRepository.findById(detalleId)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + detalleId));
        
        EstadoDetalle estadoEnum = EstadoDetalle.valueOf(nuevoEstado);
        detalle.setEstado(estadoEnum);
        
        DetalleProduccion updatedDetalle = detalleRepository.save(detalle);
        log.info("Estado del detalle {} actualizado exitosamente", detalleId);
        
        return convertToDTO(updatedDetalle);
    }
    
    @Transactional
    public void actualizarEstadoPorOrden(UUID ordenId, String nuevoEstado) {
        log.info("Actualizando todos los detalles de la orden {} a {}", ordenId, nuevoEstado);
        
        EstadoDetalle nuevoEstadoEnum = EstadoDetalle.valueOf(nuevoEstado);
        
        if (nuevoEstadoEnum == EstadoDetalle.PREPARANDO) {
            detalleRepository.updateEstadoByOrdenAndEstado(ordenId, EstadoDetalle.PENDIENTE, EstadoDetalle.PREPARANDO);
        } else if (nuevoEstadoEnum == EstadoDetalle.LISTO) {
            detalleRepository.updateEstadoByOrdenAndEstado(ordenId, EstadoDetalle.PREPARANDO, EstadoDetalle.LISTO);
        }
    }
    
    @Transactional(readOnly = true)
    public List<DetalleProduccionDTO> getDetallesByOrdenId(UUID ordenId) {
        log.info("Obteniendo detalles de la orden: {}", ordenId);
        
        // Verificar que la orden existe
        ordenRepository.findById(ordenId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + ordenId));
        
        List<DetalleProduccion> detalles = detalleRepository.findByOrdenId(ordenId);
        return detalles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public DetalleProduccionDTO getDetalleById(UUID id) {
        log.info("Obteniendo detalle por ID: {}", id);
        
        DetalleProduccion detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + id));
        
        return convertToDTO(detalle);
    }
    
    @Transactional(readOnly = true)
    public List<DetalleProduccionDTO> getDetallesByEstado(String estado) {
        log.info("Obteniendo detalles por estado: {}", estado);
        
        EstadoDetalle estadoEnum = EstadoDetalle.valueOf(estado);
        List<DetalleProduccion> detalles = detalleRepository.findByOrdenIdAndEstado(null, estadoEnum);
        return detalles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void eliminarDetalle(UUID id) {
        log.info("Eliminando detalle con ID: {}", id);
        
        if (!detalleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Detalle no encontrado con ID: " + id);
        }
        
        detalleRepository.deleteById(id);
        log.info("Detalle eliminado exitosamente");
    }
    
    @Transactional
    public DetalleProduccionDTO actualizarDetalle(UUID id, DetalleProduccionDTO detalleDTO) {
        log.info("Actualizando detalle con ID: {}", id);
        
        DetalleProduccion detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + id));
        
        if (detalleDTO.getProductoNombre() != null) {
            detalle.setProductoNombre(detalleDTO.getProductoNombre());
        }
        if (detalleDTO.getCantidad() != null) {
            detalle.setCantidad(detalleDTO.getCantidad());
        }
        if (detalleDTO.getNotas() != null) {
            detalle.setNotas(detalleDTO.getNotas());
        }
        
        DetalleProduccion updatedDetalle = detalleRepository.save(detalle);
        log.info("Detalle actualizado exitosamente");
        
        return convertToDTO(updatedDetalle);
    }
    
    @Transactional(readOnly = true)
    public EstadisticasDetalles  getEstadisticas() {
        log.info("Obteniendo estadísticas de detalles");
        
        List<Object[]> productosMasPedidos = detalleRepository.getMostOrderedProducts();
        
        List<ProductoEstadistica> topProductos = productosMasPedidos.stream()
                .limit(5)
                .map(obj -> new ProductoEstadistica((String) obj[0], ((Long) obj[1]).intValue()))
                .collect(Collectors.toList());
        
        return EstadisticasDetalles.builder()
                .totalDetalles(detalleRepository.count())
                .topProductos(topProductos)
                .build();
    }
    
    private DetalleProduccionDTO convertToDTO(DetalleProduccion detalle) {
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