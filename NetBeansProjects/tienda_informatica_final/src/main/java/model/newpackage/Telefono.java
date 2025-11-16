/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.newpackage;

/**
 *
 * @author ASUS
 */
public class Telefono {
    private int idTelefono; 
    private String numero_principal;
    private String numero_secundario;
    
    public Telefono(String numero_principal, String numero_secundario) {
        this.numero_principal = numero_principal;
        this.numero_secundario = numero_secundario;
    }
    
   
    public int getIdTelefono() {
        return idTelefono;
    }


    public String getNumeroPrincipal() {
        return numero_principal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numero_principal = numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numero_secundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numero_secundario = numeroSecundario;
    }
    

    
}
