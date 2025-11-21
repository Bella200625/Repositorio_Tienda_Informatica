package adapters;

import ports.ProductoProveedorRepositoryPort;
import model.ProductoProveedor;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MySQLProductoProveedorRepositoryAdapter implements ProductoProveedorRepositoryPort {

    private static final String JDBC_URL = "jdbc:mysql://25.50.109.111:3306/tienda_informatica_final?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root2";
    private static final String PASSWORD = "";

    // 1. REGISTRAR ADQUISICIÓN (C)
    @Override
    public int registrarAdquisicion(ProductoProveedor adquisicion) {
        int idGenerado = 0;
        String sql = "INSERT INTO producto_proveedor (producto_idProducto, proveedor_idProveedor, fecha_adquisicion) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            ps.setInt(1, adquisicion.getProductoIdProducto());
            ps.setInt(2, adquisicion.getProveedorIdProveedor());
            ps.setDate(3, Date.valueOf(adquisicion.getFechaAdquisicion())); 

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        adquisicion.setIdProductoProveedor(idGenerado); 
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR al registrar adquisición: " + e.getMessage());
            e.printStackTrace();
        }
        return idGenerado;
    }

    //BUSCAR ADQUISICIONES POR PROVEEDOR
    @Override
    public List<ProductoProveedor> buscarAdquisicionesPorProveedor(int idProveedor) {
        List<ProductoProveedor> adquisiciones = new ArrayList<>();
        String sql = "SELECT * FROM producto_proveedor WHERE proveedor_idProveedor = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    adquisiciones.add(mapResultSetToProductoProveedor(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR al buscar adquisiciones por proveedor: " + e.getMessage());
            e.printStackTrace();
        }
        return adquisiciones;
    }

    //BUSCAR ADQUISICIONES POR FECHA
    @Override
    public List<ProductoProveedor> buscarAdquisicionesPorFecha(LocalDate fecha) {
        List<ProductoProveedor> adquisiciones = new ArrayList<>();
        String sql = "SELECT * FROM producto_proveedor WHERE fecha_adquisicion = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    adquisiciones.add(mapResultSetToProductoProveedor(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR al buscar adquisiciones por fecha: " + e.getMessage());
            e.printStackTrace();
        }
        return adquisiciones;
    }

    // CONTAR PRODUCTOS POR PROVEEDOR (R - Conteo)
    @Override
    public int contarProductosPorProveedor(int idProveedor) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM producto_proveedor WHERE proveedor_idProveedor = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR al contar productos por proveedor: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }
    

    //MÉTODO AUXILIAR DE MAPEO

    private ProductoProveedor mapResultSetToProductoProveedor(ResultSet rs) throws SQLException {
        return new ProductoProveedor(
            rs.getInt("idProducto_proveedor"),
            rs.getInt("producto_idProducto"),
            rs.getInt("proveedor_idProveedor"),
            rs.getDate("fecha_adquisicion").toLocalDate()
        );
    }
}