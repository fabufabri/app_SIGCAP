package upse.SIGCAP.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import upse.SIGCAP.mad.Mad_Instalacion;
import upse.SIGCAP.modelo.Instalacion;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class InstalacionController implements Initializable {

    @FXML
    private TextField txt_item;

    @FXML
    private TextField txt_tercero;

    @FXML
    private TextField txt_tipo;

    @FXML
    private DatePicker dtp_fecha_programada;

    @FXML
    private DatePicker dtp_fecha_real;

    @FXML
    private ComboBox<String> cmb_estado;

    @FXML
    private TextField txt_evidencia;

    @FXML
    private TextArea txt_observaciones;

    @FXML
    private Button btn_grabar;

    @FXML
    private Button btn_cancelar;

    private int bandera = 0;

    private final Mad_Instalacion madInstalacion =
            new Mad_Instalacion();

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        cmb_estado.getItems().clear();

        cmb_estado.getItems().addAll(
                "PENDIENTE",
                "PROGRAMADA",
                "EN INSTALACIÓN",
                "COMPLETADA",
                "CANCELADA"
        );

        cmb_estado.setValue(
                "PENDIENTE"
        );

        dtp_fecha_programada.setValue(
                LocalDate.now()
        );
    }

    @FXML
    private void acc_grabar() {

        try {

            if (txt_item.getText() == null
                    || txt_item.getText()
                            .trim()
                            .isEmpty()) {

                fun_mensajeError(
                        "Ingrese el ID del ítem."
                );

                txt_item.requestFocus();

                return;
            }

            if (txt_tercero.getText() == null
                    || txt_tercero.getText()
                            .trim()
                            .isEmpty()) {

                fun_mensajeError(
                        "Ingrese el ID del tercero."
                );

                txt_tercero.requestFocus();

                return;
            }

            if (txt_tipo.getText() == null
                    || txt_tipo.getText()
                            .trim()
                            .isEmpty()) {

                fun_mensajeError(
                        "Ingrese el ID del tipo de instalación."
                );

                txt_tipo.requestFocus();

                return;
            }

            int itemId =
                    Integer.parseInt(
                            txt_item.getText()
                                    .trim()
                    );

            int terceroId =
                    Integer.parseInt(
                            txt_tercero.getText()
                                    .trim()
                    );

            int tipoId =
                    Integer.parseInt(
                            txt_tipo.getText()
                                    .trim()
                    );

            if (itemId <= 0
                    || terceroId <= 0
                    || tipoId <= 0) {

                fun_mensajeError(
                        "Los IDs deben ser mayores que cero."
                );

                return;
            }

            Instalacion obj =
                    new Instalacion();

            obj.setIns_id(
                    bandera
            );

            obj.setItm_id(
                    itemId
            );

            obj.setTer_id(
                    terceroId
            );

            obj.setTin_id(
                    tipoId
            );

            obj.setIns_fecha_programada(
                    dtp_fecha_programada
                            .getValue()
            );

            obj.setIns_fecha_real(
                    dtp_fecha_real
                            .getValue()
            );

            obj.setIns_estado(
                    cmb_estado.getValue() == null
                    ? "PENDIENTE"
                    : cmb_estado.getValue()
            );

            obj.setIns_observaciones(
                    txt_observaciones.getText()
            );

            obj.setIns_evidencia(
                    txt_evidencia.getText()
            );

            boolean resultado =
                    madInstalacion.mantInstalacion(
                            obj
                    );

            if (!resultado) {

                fun_mensajeError(
                        "No se pudo guardar la instalación.\n\n"
                        + "Verifique que el Ítem, Tercero y Tipo de Instalación existan."
                );

                return;
            }

            fun_mensajeInformacion(
                    bandera == 0
                    ? "Instalación registrada correctamente."
                    : "Instalación actualizada correctamente."
            );

            cerrar();

        } catch (NumberFormatException e) {

            fun_mensajeError(
                    "Ítem, Tercero y Tipo de Instalación deben ser números."
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "Error al guardar la instalación.\n\n"
                    + e.getClass().getSimpleName()
                    + ":\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_cancelar() {

        cerrar();
    }

    public void recuperarInstalacion(
            String id) {

        limpiar();

        if (id == null
                || id.trim().isEmpty()) {

            bandera = 0;

            return;
        }

        try {

            int instalacionId =
                    Integer.parseInt(
                            id.trim()
                    );

            Instalacion obj =
                    madInstalacion
                            .buscaInstalacionxId(
                                    instalacionId
                            );

            if (obj == null) {

                fun_mensajeError(
                        "No se encontró la instalación."
                );

                return;
            }

            bandera =
                    obj.getIns_id();

            txt_item.setText(
                    String.valueOf(
                            obj.getItm_id()
                    )
            );

            txt_tercero.setText(
                    String.valueOf(
                            obj.getTer_id()
                    )
            );

            txt_tipo.setText(
                    String.valueOf(
                            obj.getTin_id()
                    )
            );

            dtp_fecha_programada.setValue(
                    obj.getIns_fecha_programada()
            );

            dtp_fecha_real.setValue(
                    obj.getIns_fecha_real()
            );

            cmb_estado.setValue(
                    obj.getIns_estado()
            );

            txt_observaciones.setText(
                    obj.getIns_observaciones() == null
                    ? ""
                    : obj.getIns_observaciones()
            );

            txt_evidencia.setText(
                    obj.getIns_evidencia() == null
                    ? ""
                    : obj.getIns_evidencia()
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo recuperar la instalación.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void limpiar() {

        bandera = 0;

        txt_item.clear();

        txt_tercero.clear();

        txt_tipo.clear();

        dtp_fecha_programada.setValue(
                LocalDate.now()
        );

        dtp_fecha_real.setValue(
                null
        );

        cmb_estado.setValue(
                "PENDIENTE"
        );

        txt_observaciones.clear();

        txt_evidencia.clear();
    }

    private void cerrar() {

        if (btn_cancelar == null
                || btn_cancelar.getScene() == null) {

            return;
        }

        Stage stage =
                (Stage) btn_cancelar
                        .getScene()
                        .getWindow();

        stage.close();
    }
}