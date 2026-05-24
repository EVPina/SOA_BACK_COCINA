package com.soa.soacocina.service;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStats {
    private long ordenesPendientes;
    private long ordenesPreparando;
    private long ordenesListas;
    private long ordenesEntregadas;
    private double tiempoPreparacionPromedio;
}