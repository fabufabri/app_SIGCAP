// src/main/java/upse/SIGCAP/controlador/ConfiguracionController.java

package upse.SIGCAP.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class ConfiguracionController implements Initializable {

    @FXML
    private Button btn_usuarios;

    @FXML
    private Button btn_roles;

    @FXML
    private Button btn_cancelar;

    @FXML
    private Button btn_guardar;

    @FXML
    private TextField txt_empresa;

    @FXML
    private TextField txt_ruc;

    @FXML
    private TextField txt_telefono;

    @FXML
    private TextField txt_direccion;

    @FXML
    private TextField txt_correo;

    @FXML
    private ComboBox<String> cmb_moneda;

    @FXML
    private TextField txt_dias_alerta;

    @FXML
    private TextField txt_porcentaje;

    @FXML
    private TextField txt_prefijo_ot;

    @FXML
    private TextField txt_anio_ot;

    @FXML
    private TextField txt_secuencia_ot;

    @FXML
    private Label lbl_preview_ot;

    @FXML
    private Label lbl_secuencia_actual;

    @FXML
    private CheckBox chk_ot_vencida;

    @FXML
    private CheckBox chk_ot_proxima;

    @FXML
    private CheckBox chk_arte;

    @FXML
    private CheckBox chk_produccion;

    @FXML
    private CheckBox chk_instalacion;

    @FXML
    private CheckBox chk_completada;

    @FXML
    private Label lbl_usuario;

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        cmb_moneda.getItems().clear();

        cmb_moneda.getItems().addAll(
                "USD - Dólar estadounidense",
                "EUR - Euro"
        );

        cmb_moneda.setValue(
                "USD - Dólar estadounidense"
        );

        txt_dias_alerta.setText("2");
        txt_porcentaje.setText("100");
        txt_prefijo_ot.setText("OT");

        txt_anio_ot.setText(
                String.valueOf(
                        LocalDate.now().getYear()
                )
        );

        txt_secuencia_ot.setText("1");

        actualizarPreview();

        txt_prefijo_ot.textProperty()
                .addListener(
                        (obs, anterior, actual) ->
                                actualizarPreview()
                );

        txt_anio_ot.textProperty()
                .addListener(
                        (obs, anterior, actual) ->
                                actualizarPreview()
                );

        txt_secuencia_ot.textProperty()
                .addListener(
                        (obs, anterior, actual) ->
                                actualizarPreview()
                );
    }

    private void actualizarPreview() {

        String prefijo =
                txt_prefijo_ot.getText()
                        .trim();

        String anio =
                txt_anio_ot.getText()
                        .trim();

        String secuencia =
                txt_secuencia_ot.getText()
                        .trim();

        if (prefijo.isEmpty()) {
            prefijo = "OT";
        }

        if (anio.isEmpty()) {
            anio = String.valueOf(
                    LocalDate.now().getYear()
            );
        }

        int numero = 1;

        try {
            numero = Integer.parseInt(secuencia);
        } catch (NumberFormatException e) {
            numero = 1;
        }

        if (numero < 1) {
            numero = 1;
        }

        lbl_preview_ot.setText(
                prefijo
                + "-"
                + anio
                + "-"
                + String.format(
                        "%06d",
                        numero
                )
        );

        lbl_secuencia_actual.setText(
                String.valueOf(numero)
        );
    }

    @FXML
    private void acc_usuarios() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            App.class.getResource(
                                    "/upse/SIGCAP/vistas/Usuarios.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "SIGCAP - Usuarios"
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initOwner(
                    btn_usuarios
                            .getScene()
                            .getWindow()
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.setResizable(true);

            stage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir Usuarios.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_roles() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            App.class.getResource(
                                    "/upse/SIGCAP/vistas/Roles.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "SIGCAP - Roles y Permisos"
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initOwner(
                    btn_roles
                            .getScene()
                            .getWindow()
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.setResizable(false);

            stage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir Roles y Permisos.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_nuevaNumeracion() {

        txt_secuencia_ot.setText("1");

        actualizarPreview();

        fun_mensajeInformacion(
                "La numeración de Órdenes de Trabajo "
                + "fue reiniciada."
        );
    }

    @FXML
    private void acc_guardar() {

        try {

            validarDatos();

            actualizarPreview();

            fun_mensajeInformacion(
                    "La configuración fue guardada correctamente."
            );

        } catch (NumberFormatException e) {

            fun_mensajeError(
                    "Revise los campos numéricos."
            );

        } catch (IllegalArgumentException e) {

            fun_mensajeError(
                    e.getMessage()
            );
        }
    }

    private void validarDatos() {

        if (txt_empresa.getText()
                .trim()
                .isEmpty()) {

            throw new IllegalArgumentException(
                    "Ingrese el nombre de la empresa."
            );
        }

        if (cmb_moneda.getValue() == null) {

            throw new IllegalArgumentException(
                    "Seleccione una moneda."
            );
        }

        int dias =
                Integer.parseInt(
                        txt_dias_alerta
                                .getText()
                                .trim()
                );

        if (dias < 0) {

            throw new IllegalArgumentException(
                    "Los días de alerta no pueden ser negativos."
            );
        }

        int porcentaje =
                Integer.parseInt(
                        txt_porcentaje
                                .getText()
                                .trim()
                );

        if (porcentaje < 1
                || porcentaje > 100) {

            throw new IllegalArgumentException(
                    "El porcentaje debe estar entre 1 y 100."
            );
        }

        if (txt_prefijo_ot.getText()
                .trim()
                .isEmpty()) {

            throw new IllegalArgumentException(
                    "Ingrese el prefijo de la OT."
            );
        }

        int anio =
                Integer.parseInt(
                        txt_anio_ot
                                .getText()
                                .trim()
                );

        if (anio < 2000
                || anio > 2100) {

            throw new IllegalArgumentException(
                    "El año de la OT no es válido."
            );
        }

        int secuencia =
                Integer.parseInt(
                        txt_secuencia_ot
                                .getText()
                                .trim()
                );

        if (secuencia < 1) {

            throw new IllegalArgumentException(
                    "La secuencia debe ser mayor que cero."
            );
        }
    }

    @FXML
    private void acc_cancelar() {

        Stage stage =
                (Stage) btn_cancelar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase