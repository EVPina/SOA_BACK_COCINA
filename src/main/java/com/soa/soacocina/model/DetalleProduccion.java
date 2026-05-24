package com.soa.soacocina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema="public",name = "detalles_produccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProduccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenProduccion orden;
    
    @Column(name = "producto_nombre", nullable = false, length = 100)
    private String productoNombre;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoDetalle estado = EstadoDetalle.PENDIENTE;
    
    @Column(columnDefinition = "TEXT")
    private String notas;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
