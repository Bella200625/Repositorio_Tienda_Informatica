package adapters;

import ports.ProveedorRepositoryPort;
import model.Proveedor;
import model.Direccion;
import java.sql.*;
import java.util.List;


public class MySQLProveedorRepositoryAdapter implements ProveedorRepositoryPort {


    private static final String JDBC_URL = "jdbc:mysql://25.50.109.111:3306/tienda_informatica_final?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root2";
    private static final String PASSWORD = "";

    @Override
    public int guardarProveedor(Proveedor proveedor) {
        int idGenerado = 0;
        Direccion direccion = proveedor.getDireccion();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            conn.setAutoCommit(false);


            //INSERTAR DIRECCION Y OBTENER ID

            String sqlInsertDireccion = "INSERT INTO direccion (pais, ciudad, calle, barrio) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement psDir = conn.prepareStatement(sqlInsertDireccion, Statement.RETURN_GENERATED_KEYS)) {
                psDir.setString(1, direccion.getPais());
                psDir.setString(2, direccion.getCiudad());
                psDir.setString(3, direccion.getCalle());
                psDir.setString(4, direccion.getBarrio());

                psDir.executeUpdate();
                
                try (ResultSet rsDir = psDir.getGeneratedKeys()) {
                    if (rsDir.next()) {
                        int idDireccionGenerado = rsDir.getInt(1);
                        direccion.setIdDireccion(idDireccionGenerado); // Actualizar el objeto Direccion
                    } else {
                        throw new SQLException("Fallo al obtener ID de Dirección.");
                    }
                }
            }
            

            //INSERTAR PROVEEDOR USANDO EL ID DE DIRECCION

            String sqlInsertProveedor = "INSERT INTO proveedor (nif, nombre, Direccion_idDireccion) VALUES (?, ?, ?)";

            try (PreparedStatement psProv = conn.prepareStatement(sqlInsertProveedor, Statement.RETURN_GENERATED_KEYS)) {
                psProv.setString(1, proveedor.getNif());
                psProv.setString(2, proveedor.getNombre());
                psProv.setInt(3, direccion.getIdDireccion());

                if (psProv.executeUpdate() > 0) {
                    try (ResultSet rsProv = psProv.getGeneratedKeys()) {
                        if (rsProv.next()) {
                            idGenerado = rsProv.getInt(1);
                            proveedor.setIdProveedor(idGenerado);
                        }
                    }
                } else {
                    throw new SQLException("Fallo al insertar Proveedor.");
                }
            }

            conn.commit();
            System.out.println("LOG: Proveedor " + proveedor.getNombre() + " registrado con ID: " + idGenerado);

        } catch (SQLException e) {
            System.err.println("ERROR FATAL AL GUARDAR PROVEEDOR (ROLLBACK): " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error en rollback: " + ex.getMessage());
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return idGenerado;
    }

    @Override
    public Proveedor buscarProveedorPorId(int id) {
    Proveedor proveedor = null;

    // Consulta usando JOIN para traer Proveedor y su Direccion
    String sql = "SELECT p.idProveedor, p.nif, p.nombre, p.Direccion_idDireccion, " +
                 "d.idDireccion, d.pais, d.ciudad, d.calle, d.barrio " +
                 "FROM proveedor p " +
                 "JOIN direccion d ON p.Direccion_idDireccion = d.idDireccion " +
                 "WHERE p.idProveedor = ?";

    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                //Crear el objeto Direccion
                Direccion direccion = new Direccion(
                    rs.getString("pais"), 
                    rs.getString("ciudad"), 
                    rs.getString("calle"), 
                    rs.getString("barrio")
                );

                direccion.setIdDireccion(rs.getInt("idDireccion")); 

                //Crear el objeto Proveedor, agregando la Direccion
                proveedor = new Proveedor(
                    rs.getInt("idProveedor"), 
                    rs.getString("nif"),
                    rs.getString("nombre"), 
                    direccion
                );
            }
        }
    } catch (SQLException e) {
        System.err.println("ERROR al buscar proveedor por ID: " + e.getMessage());
        e.printStackTrace();
    }
    return proveedor;
}

    @Override
    public List<Proveedor> obtenerTodosLosProveedores() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTodosLosProveedores'");
    }

    @Override
    public boolean actualizarProveedor(Proveedor proveedor) {
    Connection conn = null;
    boolean exito = false;

    try {
        conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        conn.setAutoCommit(false);


        //ACTUALIZAR DIRECCION

        Direccion direccion = proveedor.getDireccion();
        String sqlUpdateDireccion = "UPDATE direccion SET pais = ?, ciudad = ?, calle = ?, barrio = ? WHERE idDireccion = ?";
        
        try (PreparedStatement psDir = conn.prepareStatement(sqlUpdateDireccion)) {
            psDir.setString(1, direccion.getPais());
            psDir.setString(2, direccion.getCiudad());
            psDir.setString(3, direccion.getCalle());
            psDir.setString(4, direccion.getBarrio());
            psDir.setInt(5, direccion.getIdDireccion());
            psDir.executeUpdate();
        }
        

        //ACTUALIZAR PROVEEDOR

        String sqlUpdateProveedor = "UPDATE proveedor SET nif = ?, nombre = ? WHERE idProveedor = ?";

        try (PreparedStatement psProv = conn.prepareStatement(sqlUpdateProveedor)) {
            psProv.setString(1, proveedor.getNif());
            psProv.setString(2, proveedor.getNombre());
            psProv.setInt(3, proveedor.getIdProveedor()); 

            if (psProv.executeUpdate() > 0) {
                exito = true;
            }
        }

        if (exito) {
            conn.commit();
        } else {
            conn.rollback();
        }

    } catch (SQLException e) {
        System.err.println("ERROR FATAL AL ACTUALIZAR PROVEEDOR (ROLLBACK): " + e.getMessage());
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException ex) {}
        }
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
        }
    }
    return exito;
}

    @Override
    public boolean eliminarProveedor(int id) {
    //Buscar el proveedor para obtener el ID de la dirección
    Proveedor proveedorAEliminar = buscarProveedorPorId(id); 
    if (proveedorAEliminar == null) {
        return true;
    }
    final int idDireccion = proveedorAEliminar.getDireccion().getIdDireccion();
    
    Connection conn = null;
    boolean exito = false;

    try {
        conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        conn.setAutoCommit(false);


        //ELIMINAR PROVEEDOR
        String sqlDeleteProveedor = "DELETE FROM proveedor WHERE idProveedor = ?";
        try (PreparedStatement psProv = conn.prepareStatement(sqlDeleteProveedor)) {
            psProv.setInt(1, id);
            psProv.executeUpdate();
        }
        

        //ELIMINAR DIRECCION
        String sqlDeleteDireccion = "DELETE FROM direccion WHERE idDireccion = ?";
        try (PreparedStatement psDir = conn.prepareStatement(sqlDeleteDireccion)) {
            psDir.setInt(1, idDireccion);
            if (psDir.executeUpdate() > 0) {
                exito = true;
            }
        }
        
        if (exito) {
            conn.commit();
        } else {
            conn.rollback();
        }

    } catch (SQLException e) {
        System.err.println("ERROR FATAL AL ELIMINAR PROVEEDOR (ROLLBACK): " + e.getMessage());
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException ex) {}
        }
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
        }
    }
    return exito;
}


}