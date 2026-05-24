package com.soa.soacocina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema="public",name = "historial_estados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEstado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenProduccion orden;
    
    @Column(name = "estado_anterior", length = 15)
    private String estadoAnterior;
    
    @Column(name = "estado_nuevo", nullable = false, length = 15)
    private String estadoNuevo;
    
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;
    
    @CreationTimestamp
    @Column(name = "changed_at", nullable = false, updatable = false)
    private LocalDateTime changedAt;
}