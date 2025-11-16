/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Proveedor {
    private int idProveedor;
    private String nif;
    private Direccion direccion;
    
     public Proveedor(String nif, String nombreEmpresa, Direccion direccion) {
        this.nif = nif;
        this.direccion = direccion;
    }
     
     public int getIdProveedor() {
        return idProveedor;
    }
     
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
    
}
