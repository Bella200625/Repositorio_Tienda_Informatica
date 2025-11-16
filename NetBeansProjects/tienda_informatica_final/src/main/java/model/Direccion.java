/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Direccion {
   private int idDireccion;
   private String pais;
   private String ciudad; 
   private String calle;
   private String barrio; 
    
    
    public Direccion(String pais, String ciudad, String calle, String barrio) {
        this.idDireccion = idDireccion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.calle = calle;
        this.barrio = barrio;
    }
    
    public int getIdDireccion() {
        return idDireccion;
    }


    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
}
    
    
    

