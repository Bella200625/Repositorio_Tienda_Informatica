/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enum.newpackage;

/**
 *
 * @author ASUS
 */
public enum TipoPago {
    TARJETA_CREDITO(1),
    TRANSFERENCIA_BANCARIA(2),
    EFECTIVO(3)
    ;
    
    //guardara el id delos enums
    private final int id;
    
    // constructor
    TipoPago(int id) {
        // Asigna el número a la variable  id
        this.id = id; // Asigna el número a la variable 
    }
    
    //metodo get
     public int getid() {
        return id;
    }
    
    
}
