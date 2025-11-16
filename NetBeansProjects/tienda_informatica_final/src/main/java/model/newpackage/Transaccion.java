/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.newpackage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import Enum.newpackage.TipoPago; 

/**
 *
 * @author ASUS
 */
public class Transaccion {
    private int idTransaccion;
    // BigDecimal maneja tipos de datos decimales lo usamos porque es mas exacto mejora la precisión y la exactitud.
    private BigDecimal precio;
    private TipoPago tipoDePago;
    
    // crearemos el constructorr
    public Transaccion(BigDecimal precio, TipoPago tipoDePago) {
        this.precio = precio;
        this.tipoDePago = tipoDePago;
    }
    
    
    public int getIdTransaccion() {
        return idTransaccion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public TipoPago getTipoDePago() {
        return tipoDePago;
    }

    public void setTipoDePago(TipoPago tipoDePago) {
        this.tipoDePago = tipoDePago;
    }
    
    
}
