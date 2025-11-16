package model;

import java.time.LocalDate;

public class ServicioTecnico {

    private int idServicioTecnico;
    private String detallesServicio;
    private LocalDate fechaServicioTecnico;
    private int clienteId;
    private int productoId;

    public ServicioTecnico() {
    }

    public ServicioTecnico(int idServicioTecnico, String detallesServicio, LocalDate fechaServicioTecnico, int clienteId, int productoId) {
        this.idServicioTecnico = idServicioTecnico;
        this.detallesServicio = detallesServicio;
        this.fechaServicioTecnico = fechaServicioTecnico;
        this.clienteId = clienteId;
        this.productoId = productoId;
    }

    // Getters y Setters
    public int getIdServicioTecnico() {
        return idServicioTecnico;
    }

    public void setIdServicioTecnico(int idServicioTecnico) {
        this.idServicioTecnico = idServicioTecnico;
    }

    public String getDetallesServicio() {
        return detallesServicio;
    }

    public void setDetallesServicio(String detallesServicio) {
        this.detallesServicio = detallesServicio;
    }

    public LocalDate getFechaServicioTecnico() {
        return fechaServicioTecnico;
    }

    public void setFechaServicioTecnico(LocalDate fechaServicioTecnico) {
        this.fechaServicioTecnico = fechaServicioTecnico;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}