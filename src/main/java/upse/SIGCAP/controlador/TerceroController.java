// src/main/java/upse/SIGCAP/controlador/TerceroController.java

package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import upse.SIGCAP.mad.Mad_Tercero;
import upse.SIGCAP.modelo.Tercero;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class TerceroController
        implements Initializable {

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_telefono;

    @FXML
    private TextField txt_correo;

    @FXML
    private ComboBox<String> cmb_estado;

    @FXML
    private Button btn_grabar;

    @FXML
    private Button btn_cancelar;

    private int bandera = 0;

    private final Mad_Tercero madTercero =
            new Mad_Tercero();

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        cmb_estado.getItems().addAll(
                "A",
                "E"
        );

        cmb_estado.setValue("A");
    }

    @FXML
    private void acc_grabar() {

        try {

            if (txt_nombre.getText() == null
                    || txt_nombre.getText()
                            .trim()
                            .isEmpty()) {

                fun_mensajeError(
                        "Ingrese el nombre del tercero."
                );

                txt_nombre.requestFocus();

                return;
            }

            Tercero obj =
                    new Tercero();

            obj.setTer_id(
                    bandera
            );

            obj.setTer_nombre(
                    txt_nombre
                            .getText()
                            .trim()
            );

            obj.setTer_telefono(
                    txt_telefono.getText()
                            == null
                            ? ""
                            : txt_telefono
                                    .getText()
                                    .trim()
            );

            obj.setTer_correo(
                    txt_correo.getText()
                            == null
                            ? ""
                            : txt_correo
                                    .getText()
                                    .trim()
            );

            obj.setTer_estado(
                    cmb_estado.getValue()
                            == null
                            ? "A"
                            : cmb_estado
                                    .getValue()
            );

            boolean resultado =
                    madTercero
                            .mantTercero(
                                    obj
                            );

            if (resultado) {

                fun_mensajeInformacion(
                        bandera == 0
                                ? "Tercero registrado correctamente."
                                : "Tercero actualizado correctamente."
                );

                cerrar();

            } else {

                fun_mensajeError(
                        "No se pudo guardar el tercero."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "Error al guardar el tercero.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_cancelar() {

        cerrar();
    }

    public void recuperarTercero(
            String id) {

        limpiar();

        if (id == null
                || id.trim().isEmpty()) {

            bandera = 0;

            return;
        }

        try {

            int terceroId =
                    Integer.parseInt(
                            id
                    );

            Tercero obj =
                    madTercero
                            .buscaTerceroxId(
                                    terceroId
                            );

            if (obj == null) {

                fun_mensajeError(
                        "No se encontró el tercero."
                );

                return;
            }

            bandera =
                    obj.getTer_id();

            txt_nombre.setText(
                    valor(
                            obj.getTer_nombre()
                    )
            );

            txt_telefono.setText(
                    valor(
                            obj.getTer_telefono()
                    )
            );

            txt_correo.setText(
                    valor(
                            obj.getTer_correo()
                    )
            );

            cmb_estado.setValue(
                    obj.getTer_estado()
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo recuperar el tercero.\n\n"
                    + e.getMessage()
            );
        }
    }

    private String valor(
            String texto) {

        return texto == null
                ? ""
                : texto;
    }

    private void limpiar() {

        bandera = 0;

        txt_nombre.clear();
        txt_telefono.clear();
        txt_correo.clear();

        cmb_estado.setValue(
                "A"
        );
    }

    private void cerrar() {

        Stage stage =
                (Stage) btn_cancelar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase