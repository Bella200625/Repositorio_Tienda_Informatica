 

import model.DetalleAltaTecnologia;
import model.EmpresaFabricante;
import model.Producto;
import model.*;
import ports.ProductoRepositoryPort;

import java.time.LocalDate;

import adapters.MySQLProductoRepositoryAdapter;

public class testProducto {

    public static void main(String[] args) {

        EmpresaFabricante fabricante = new EmpresaFabricante(
            0,
            "Intel Corp",
            150000 
        );


        DetalleAltaTecnologia detalle = new DetalleAltaTecnologia(
            0,
            "Estados Unidos",
            LocalDate.of(2025, 10, 20),
            0
        );

        detalle.setFabricante(fabricante); 

        Producto cpu = new Producto(
            0,
            "CPU Core Ultra 9", 
            "i9-15900K",   
            "Procesador de alto rendimiento para gaming y estación de trabajo.",

            null, 
            1 // ID de categoría 1, que asumimos es 'CPU'
        );

        cpu.setDetalleAltaTecnologia(detalle);


        MySQLProductoRepositoryAdapter adapter = new MySQLProductoRepositoryAdapter();


        int idProductoGenerado = adapter.guardarProducto(cpu);

        if (idProductoGenerado > 0) {
            System.out.println("✅ CPU (Producto de Alta Tecnología) guardado exitosamente con ID: " + idProductoGenerado);
        } else {
            System.out.println("❌ Error al guardar el producto. Revisa la consola para la excepción SQL detallada.");
        }
    }
}