/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.general;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Fabufabri
 */
public class BD {

    private Connection conexion;
    private Statement sentenciaSQL;
    private ResultSet resulSet;

    // Configuración SQL Server
    private final String servidor = "DESKTOP-LQHIOK1";
    private final int puerto = 1433;
    private final String basedatos = "SIGCAP";
    private final String usuario = "usu_appVisual";
    private final String clave = "usu_appVisual";

    /**
     * Establece la conexión con SQL Server.
     */
    public void conectarBD() throws SQLException {

        try {

            // Cargar driver de SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Conexión directa al puerto TCP 1433.
            // Ya no usamos DESKTOP-LQHIOK1\SQLEXPRESS
            // para evitar depender de SQL Server Browser.
            String url = "jdbc:sqlserver://"
                    + servidor + ":" + puerto
                    + ";databaseName=" + basedatos
                    + ";user=" + usuario
                    + ";password=" + clave
                    + ";encrypt=false"
                    + ";trustServerCertificate=true"
                    + ";loginTimeout=30;";

            System.out.println("========================================");
            System.out.println("Conectando a SQL Server...");
            System.out.println("Servidor: " + servidor);
            System.out.println("Puerto: " + puerto);
            System.out.println("Base: " + basedatos);
            System.out.println("Usuario: " + usuario);
            System.out.println("========================================");

            conexion = DriverManager.getConnection(url);

            System.out.println("Conexión a SIGCAP correcta.");

        } catch (ClassNotFoundException e) {

            throw new SQLException(
                    "No se encontró el driver JDBC de SQL Server.",
                    e
            );
        }
    }

    /**
     * Cierra todos los recursos utilizados por la conexión.
     */
    public void desconectarBD() {

        try {

            if (resulSet != null) {
                resulSet.close();
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al cerrar ResultSet: " + e.getMessage()
            );
        }

        try {

            if (sentenciaSQL != null) {
                sentenciaSQL.close();
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al cerrar Statement: " + e.getMessage()
            );
        }

        try {

            if (conexion != null) {
                conexion.close();
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al cerrar conexión: " + e.getMessage()
            );
        }

        resulSet = null;
        sentenciaSQL = null;
        conexion = null;
    }

    /**
     * Inicia una transacción.
     */
    public void iniciarTransaccion() throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            conectarBD();
        }

        conexion.setAutoCommit(false);
    }

    /**
     * Confirma una transacción.
     */
    public void commit() throws SQLException {

        if (conexion != null) {

            conexion.commit();
            conexion.setAutoCommit(true);
        }
    }

    /**
     * Revierte una transacción.
     */
    public void rollback() {

        try {

            if (conexion != null) {

                conexion.rollback();
                conexion.setAutoCommit(true);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al realizar rollback: " + e.getMessage()
            );
        }
    }

    /**
     * Ejecuta una consulta SELECT.
     */
    public ResultSet ejecutarConsultaSql(String sql) throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            conectarBD();
        }

        sentenciaSQL = conexion.createStatement();

        resulSet = sentenciaSQL.executeQuery(sql);

        return resulSet;
    }

    /**
     * Ejecuta INSERT, UPDATE o DELETE.
     */
    public int ejecutarSQL(String sql) throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            conectarBD();
        }

        sentenciaSQL = conexion.createStatement();

        return sentenciaSQL.executeUpdate(sql);
    }

    /**
     * Ejecuta una consulta y devuelve una ObservableList.
     */
    public <T> ObservableList<T> getListaConsulta(
            String sql,
            Function<ResultSet, T> mapper) {

        ObservableList<T> lista =
                FXCollections.observableArrayList();

        try {

            conectarBD();

            resulSet = ejecutarConsultaSql(sql);

            while (resulSet.next()) {

                lista.add(mapper.apply(resulSet));
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error BD: " + e.getMessage()
            );

        } finally {

            desconectarBD();
        }

        return lista;
    }

    /**
     * Ejecuta INSERT, UPDATE o DELETE.
     */
    public boolean fun_ejecutar(String sql) {

        try {

            conectarBD();

            int filas = ejecutarSQL(sql);

            return filas > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error BD: " + e.getMessage()
            );

            return false;

        } finally {

            desconectarBD();
        }
    }

    /**
     * Devuelve la conexión actual.
     */
    public Connection getConexion() {

        return conexion;
    }

    /**
     * Prepara una sentencia SQL parametrizada.
     */
    public PreparedStatement prepararStatement(
            String sql) throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            conectarBD();
        }

        return conexion.prepareStatement(sql);
    }

}//fin clase