/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.general;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Fabufabri
 */
public class Mod_general {

    public static final String DIRVISTAS = "/upse/sigcap/vistas/";

    public static void fun_mensajeInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void fun_mensajeError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void detectarTecla(Node origen, KeyCode tecla, Node destino) {
        origen.setOnKeyPressed(event -> {
            if (event.getCode() == tecla) {
                destino.requestFocus();
            }
        });
    }
}//fin clase
