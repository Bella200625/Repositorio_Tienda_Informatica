package model.newpackage;

import java.time.LocalDate;

public class DetalleAltaTecnologia {

    private int idDetalleAltaTecnologia;
    private String paisOrigen;
    private LocalDate fechaFabricacion;
    private int empresaFabricanteId;
    
    private EmpresaFabricante fabricante;

    public DetalleAltaTecnologia() {
    }

    public DetalleAltaTecnologia(int idDetalleAltaTecnologia, String paisOrigen, LocalDate fechaFabricacion, int empresaFabricanteId) {
        this.idDetalleAltaTecnologia = idDetalleAltaTecnologia;
        this.paisOrigen = paisOrigen;
        this.fechaFabricacion = fechaFabricacion;
        this.empresaFabricanteId = empresaFabricanteId;
    }

    // Getters y Setters...
    public int getIdDetalleAltaTecnologia() {
        return idDetalleAltaTecnologia;
    }

    public void setIdDetalleAltaTecnologia(int idDetalleAltaTecnologia) {
        this.idDetalleAltaTecnologia = idDetalleAltaTecnologia;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public LocalDate getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }

    public int getEmpresaFabricanteId() {
        return empresaFabricanteId;
    }

    public void setEmpresaFabricanteId(int empresaFabricanteId) {
        this.empresaFabricanteId = empresaFabricanteId;
    }
    
    public EmpresaFabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(EmpresaFabricante fabricante) {
        this.fabricante = fabricante;
    }
}