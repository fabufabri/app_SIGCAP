// src/main/java/upse/SIGCAP/controlador/LoginController.java
package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import upse.SIGCAP.general.Mod_VariablesGlobales;
import upse.SIGCAP.mad.Mad_seguridad;
import upse.SIGCAP.modelo.Usuario;

import static upse.SIGCAP.general.Mod_general.detectarTecla;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class LoginController implements Initializable {

    @FXML
    private TextField txt_usuario;

    @FXML
    private PasswordField txt_clave;

    private final Mad_seguridad madSeguridad =
            new Mad_seguridad();

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        detectarTecla(
                txt_usuario,
                javafx.scene.input.KeyCode.ENTER,
                txt_clave
        );

        detectarTecla(
                txt_clave,
                javafx.scene.input.KeyCode.ENTER,
                txt_clave
        );
    }

    @FXML
    private void acc_login() {

        String usuario =
                txt_usuario.getText().trim();

        String clave =
                txt_clave.getText();

        if (usuario.isEmpty()
                || clave.isEmpty()) {

            fun_mensajeInformacion(
                    "Ingrese Usuario y Clave."
            );

            return;
        }

        Usuario obj =
                madSeguridad.login(
                        usuario,
                        clave
                );

        if (obj != null) {

            Mod_VariablesGlobales.g_nombreUsuario =
                    obj.getUsu_usuario();

            App.cargarPrincipal();

        } else {

            fun_mensajeInformacion(
                    "Usuario o Clave Incorrecta"
            );
        }
    }

    @FXML
    private void acc_cancelar() {

        Platform.exit();
    }

}//fin clase