package com.soa.soacocina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soa.soacocina.model.EstadoProduccion;
import com.soa.soacocina.model.OrdenProduccion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdenProduccionRepository extends JpaRepository<OrdenProduccion, UUID> {
    
    Optional<OrdenProduccion> findByPedidoId(UUID pedidoId);
    
    List<OrdenProduccion> findByEstado(EstadoProduccion estado);
    
    List<OrdenProduccion> findByEstadoIn(List<EstadoProduccion> estados);
    
    List<OrdenProduccion> findByMesaNumero(Integer mesaNumero);
    
    @Query("SELECT o FROM OrdenProduccion o WHERE o.estado IN :estados ORDER BY o.createdAt ASC")
    List<OrdenProduccion> findActiveOrders(@Param("estados") List<EstadoProduccion> estados);
    
    @Query("SELECT o FROM OrdenProduccion o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<OrdenProduccion> findOrdersBetweenDates(@Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
    
    long countByEstado(EstadoProduccion estado);
    
    @Query("SELECT AVG(o.tiempoPreparacionSegundos) FROM OrdenProduccion o WHERE o.estado = 'ENTREGADO'")
    Double getAveragePreparationTime();
}