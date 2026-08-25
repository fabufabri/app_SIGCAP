// src/main/java/upse/SIGCAP/controlador/NuevoUsuarioController.java

package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class NuevoUsuarioController
        implements Initializable {

    @FXML
    private TextField txt_usuario;

    @FXML
    private PasswordField txt_clave;

    @FXML
    private PasswordField txt_confirmar;

    @FXML
    private TextField txt_nombres;

    @FXML
    private TextField txt_apellidos;

    @FXML
    private TextField txt_correo;

    @FXML
    private ComboBox<String> cmb_estado;

    @FXML
    private ComboBox<String> cmb_rol;

    @FXML
    private Button btn_grabar;

    @FXML
    private Button btn_cancelar;

    private int usuarioId = 0;

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        cmb_estado.getItems().clear();

        cmb_estado.getItems().addAll(
                "A",
                "E"
        );

        cmb_estado.setValue("A");

        cmb_rol.getItems().clear();

        cmb_rol.getItems().addAll(
                "ADMINISTRADOR",
                "COORDINADOR",
                "DISEÑO",
                "PRODUCCIÓN",
                "INSTALACIONES",
                "CONSULTA"
        );

        cmb_rol.setValue(
                "CONSULTA"
        );
    }

    public void setUsuarioId(
            int id) {

        usuarioId = id;

        if (id == 0) {

            limpiar();

            return;
        }

        cargarUsuario(id);
    }

    private void cargarUsuario(
            int id) {

        /*
         * El formulario queda preparado para edición.
         *
         * La carga desde BD se conecta cuando se
         * encuentre disponible el SP de usuarios.
         */

        limpiar();

        txt_usuario.setDisable(true);

        fun_mensajeInformacion(
                "Formulario de edición de usuario "
                + id
                + "."
        );
    }

    @FXML
    private void acc_grabar() {

        try {

            validar();

            /*
             * Aquí se conectará Mad_Usuario.mantUsuario()
             * cuando se encuentre creado el mantenimiento
             * de usuarios en SQL Server.
             */

            fun_mensajeInformacion(
                    usuarioId == 0
                            ? "Usuario registrado correctamente."
                            : "Usuario actualizado correctamente."
            );

            cerrar();

        } catch (IllegalArgumentException e) {

            fun_mensajeError(
                    e.getMessage()
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo guardar el usuario.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void validar() {

        if (txt_usuario
                .getText()
                .trim()
                .isEmpty()) {

            throw new IllegalArgumentException(
                    "Ingrese el nombre de usuario."
            );
        }

        if (txt_nombres
                .getText()
                .trim()
                .isEmpty()) {

            throw new IllegalArgumentException(
                    "Ingrese los nombres."
            );
        }

        if (txt_apellidos
                .getText()
                .trim()
                .isEmpty()) {

            throw new IllegalArgumentException(
                    "Ingrese los apellidos."
            );
        }

        if (usuarioId == 0) {

            if (txt_clave
                    .getText()
                    .isEmpty()) {

                throw new IllegalArgumentException(
                        "Ingrese la contraseña."
                );
            }

            if (!txt_clave
                    .getText()
                    .equals(
                            txt_confirmar
                                    .getText()
                    )) {

                throw new IllegalArgumentException(
                        "Las contraseñas no coinciden."
                );
            }
        }

        if (cmb_estado.getValue() == null) {

            throw new IllegalArgumentException(
                    "Seleccione el estado."
            );
        }

        if (cmb_rol.getValue() == null) {

            throw new IllegalArgumentException(
                    "Seleccione el rol."
            );
        }
    }

    @FXML
    private void acc_cancelar() {

        cerrar();
    }

    private void limpiar() {

        txt_usuario.clear();

        txt_clave.clear();

        txt_confirmar.clear();

        txt_nombres.clear();

        txt_apellidos.clear();

        txt_correo.clear();

        cmb_estado.setValue("A");

        cmb_rol.setValue(
                "CONSULTA"
        );

        txt_usuario.setDisable(false);
    }

    private void cerrar() {

        Stage stage =
                (Stage) btn_cancelar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase