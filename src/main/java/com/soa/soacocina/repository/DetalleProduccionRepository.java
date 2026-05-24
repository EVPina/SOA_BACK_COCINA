package com.soa.soacocina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.soa.soacocina.model.DetalleProduccion;
import com.soa.soacocina.model.EstadoDetalle;

import java.util.List;
import java.util.UUID;

@Repository
public interface DetalleProduccionRepository extends JpaRepository<DetalleProduccion, UUID> {
    
    List<DetalleProduccion> findByOrdenId(UUID ordenId);
    
    List<DetalleProduccion> findByOrdenIdAndEstado(UUID ordenId, EstadoDetalle estado);
    
    @Modifying
    @Transactional
    @Query("UPDATE DetalleProduccion d SET d.estado = :nuevoEstado WHERE d.orden.id = :ordenId AND d.estado = :estadoActual")
    int updateEstadoByOrdenAndEstado(@Param("ordenId") UUID ordenId, 
                                      @Param("estadoActual") EstadoDetalle estadoActual,
                                      @Param("nuevoEstado") EstadoDetalle nuevoEstado);
    
    long countByOrdenIdAndEstado(UUID ordenId, EstadoDetalle estado);
    
    @Query("SELECT d.productoNombre, COUNT(d) FROM DetalleProduccion d GROUP BY d.productoNombre ORDER BY COUNT(d) DESC")
    List<Object[]> getMostOrderedProducts();
}