package com.soa.soacocina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema="public",name = "ordenes_produccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProduccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "pedido_id", nullable = false, unique = true)
    private UUID pedidoId;
    
    @Column(name = "mesa_numero", nullable = false)
    private Integer mesaNumero;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoProduccion estado = EstadoProduccion.PENDIENTE;
    
    @Column(name = "usuario_jefe_id", nullable = false)
    private UUID usuarioJefeId;
    
    @Column(name = "tiempo_preparacion_segundos")
    private Integer tiempoPreparacionSegundos;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleProduccion> detalles;
    
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialEstado> historialEstados;
}

