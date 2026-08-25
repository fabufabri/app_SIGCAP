// src/main/java/upse/SIGCAP/controlador/CiudadController.java
package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import upse.SIGCAP.mad.Mad_Ciudad;
import upse.SIGCAP.modelo.Ciudad;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class CiudadController implements Initializable {

    @FXML
    private TextField txt_nombre;

    @FXML
    private ComboBox<String> cmb_estado;

    @FXML
    private Button btn_grabar;

    @FXML
    private Button btn_cancelar;

    private int bandera = 0;

    private final Mad_Ciudad madCiudad =
            new Mad_Ciudad();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cmb_estado.getItems().clear();

        cmb_estado.getItems().add("Activo");
        cmb_estado.getItems().add("Inactivo");

        cmb_estado.setValue("Activo");
    }

    @FXML
    private void acc_grabar() {

        String nombre =
                txt_nombre.getText() == null
                        ? ""
                        : txt_nombre.getText().trim();

        if (nombre.isEmpty()) {

            fun_mensajeInformacion(
                    "Ingrese el nombre de la ciudad."
            );

            txt_nombre.requestFocus();

            return;
        }

        String estado =
                "Activo".equals(
                        cmb_estado.getValue()
                )
                        ? "A"
                        : "E";

        Ciudad obj =
                new Ciudad();

        obj.setCiu_id(bandera);
        obj.setCiu_nombre(nombre);
        obj.setCiu_estado(estado);

        try {

            boolean resultado =
                    madCiudad.mantCiudad(obj);

            if (resultado) {

                fun_mensajeInformacion(
                        bandera == 0
                                ? "Ciudad registrada correctamente."
                                : "Ciudad actualizada correctamente."
                );

                cerrar();

            } else {

                fun_mensajeError(
                        "No se pudo guardar la ciudad."
                );
            }

        } catch (Exception e) {

            fun_mensajeError(
                    "Error al guardar la ciudad.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_cancelar() {

        cerrar();
    }

    public void recuperarCiudad(String id) {

        limpiar();

        if (id == null
                || id.trim().isEmpty()) {

            bandera = 0;

            txt_nombre.requestFocus();

            return;
        }

        try {

            int ciudadId =
                    Integer.parseInt(id);

            Ciudad obj =
                    madCiudad.buscaCiudadxId(
                            ciudadId
                    );

            if (obj == null) {

                fun_mensajeError(
                        "No se encontró la ciudad."
                );

                return;
            }

            bandera =
                    obj.getCiu_id();

            txt_nombre.setText(
                    valor(obj.getCiu_nombre())
            );

            cmb_estado.setValue(
                    "A".equalsIgnoreCase(
                            obj.getCiu_estado()
                    )
                            ? "Activo"
                            : "Inactivo"
            );

        } catch (NumberFormatException e) {

            fun_mensajeError(
                    "El identificador de la ciudad no es válido."
            );

        } catch (Exception e) {

            fun_mensajeError(
                    "No se pudo recuperar la ciudad.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void limpiar() {

        bandera = 0;

        txt_nombre.clear();

        cmb_estado.setValue("Activo");
    }

    private String valor(String texto) {

        return texto == null
                ? ""
                : texto;
    }

    private void cerrar() {

        Stage stage =
                (Stage) btn_cancelar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase