package model.newpackage;

public class EmpresaFabricante {

    private int idEmpresaFabricante;
    private String nombre;
    private int numeroEmpleados;

    public EmpresaFabricante() {
    }

    public EmpresaFabricante(int idEmpresaFabricante, String nombre, int numeroEmpleados) {
        this.idEmpresaFabricante = idEmpresaFabricante;
        this.nombre = nombre;
        this.numeroEmpleados = numeroEmpleados;
    }

    // Getters y Setters
    public int getIdEmpresaFabricante() {
        return idEmpresaFabricante;
    }

    public void setIdEmpresaFabricante(int idEmpresaFabricante) {
        this.idEmpresaFabricante = idEmpresaFabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroEmpleados() {
        return numeroEmpleados;
    }

    public void setNumeroEmpleados(int numeroEmpleados) {
        this.numeroEmpleados = numeroEmpleados;
    }
}