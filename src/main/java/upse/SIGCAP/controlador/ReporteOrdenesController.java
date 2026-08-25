// src/main/java/upse/SIGCAP/controlador/ReporteOrdenesController.java

package upse.SIGCAP.controlador;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import upse.SIGCAP.mad.Mad_OrdenTrabajo;
import upse.SIGCAP.modelo.OrdenTrabajo;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class ReporteOrdenesController
        implements Initializable {

    @FXML
    private TableView<OrdenTrabajo> tb_ordenes;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_codigo;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_cliente;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_campania;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_solicitante;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_ciudad;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_fecha;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_fecha_requerida;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_prioridad;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_responsable;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_estado;

    @FXML
    private TextField txt_buscar;

    @FXML
    private Label lbl_total;

    @FXML
    private Button btn_buscar;

    @FXML
    private Button btn_limpiar;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_imprimir;

    @FXML
    private Button btn_exportar;

    @FXML
    private Button btn_cerrar;

    private final Mad_OrdenTrabajo madOrdenTrabajo =
            new Mad_OrdenTrabajo();

    private ObservableList<OrdenTrabajo> listaOriginal =
            FXCollections.observableArrayList();

    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        configurarColumnas();

        cargarDatos();

        if (txt_buscar != null) {

            txt_buscar.textProperty()
                    .addListener(
                            (obs, anterior, actual) ->
                                    filtrar(actual)
                    );
        }
    }

    private void configurarColumnas() {

        if (col_codigo != null) {

            col_codigo.setCellValueFactory(
                    new PropertyValueFactory<>("ot_codigo")
            );
        }

        if (col_cliente != null) {

            col_cliente.setCellValueFactory(
                    new PropertyValueFactory<>("ot_cliente")
            );
        }

        if (col_campania != null) {

            col_campania.setCellValueFactory(
                    new PropertyValueFactory<>("ot_campania")
            );
        }

        if (col_solicitante != null) {

            col_solicitante.setCellValueFactory(
                    new PropertyValueFactory<>("ot_solicitante")
            );
        }

        if (col_ciudad != null) {

            col_ciudad.setCellValueFactory(
                    new PropertyValueFactory<>("ot_ciudad")
            );
        }

        if (col_fecha != null) {

            col_fecha.setCellValueFactory(
                    celda -> {

                        if (celda.getValue() == null
                                || celda.getValue().getOt_fecha() == null) {

                            return new javafx.beans.property.SimpleStringProperty("");
                        }

                        return new javafx.beans.property.SimpleStringProperty(
                                celda.getValue()
                                        .getOt_fecha()
                                        .format(formatoFecha)
                        );
                    }
            );
        }

        if (col_fecha_requerida != null) {

            col_fecha_requerida.setCellValueFactory(
                    celda -> {

                        if (celda.getValue() == null
                                || celda.getValue().getOt_fecha_requerida() == null) {

                            return new javafx.beans.property.SimpleStringProperty("");
                        }

                        return new javafx.beans.property.SimpleStringProperty(
                                celda.getValue()
                                        .getOt_fecha_requerida()
                                        .format(formatoFecha)
                        );
                    }
            );
        }

        if (col_prioridad != null) {

            col_prioridad.setCellValueFactory(
                    new PropertyValueFactory<>("ot_prioridad")
            );
        }

        if (col_responsable != null) {

            col_responsable.setCellValueFactory(
                    new PropertyValueFactory<>("ot_responsable")
            );
        }

        if (col_estado != null) {

            col_estado.setCellValueFactory(
                    new PropertyValueFactory<>("ot_estado")
            );
        }
    }

    private void cargarDatos() {

        try {

            ObservableList<OrdenTrabajo> datos =
                    madOrdenTrabajo.getOrdenesTrabajo();

            if (datos == null) {

                listaOriginal =
                        FXCollections.observableArrayList();

            } else {

                listaOriginal =
                        FXCollections.observableArrayList(datos);
            }

            if (tb_ordenes != null) {

                tb_ordenes.setItems(
                        FXCollections.observableArrayList(
                                listaOriginal
                        )
                );
            }

            actualizarTotal();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron cargar las Órdenes de Trabajo.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void filtrar(
            String texto) {

        if (tb_ordenes == null) {
            return;
        }

        if (texto == null
                || texto.trim().isEmpty()) {

            tb_ordenes.setItems(
                    FXCollections.observableArrayList(
                            listaOriginal
                    )
            );

            actualizarTotal();

            return;
        }

        String filtro =
                texto.trim().toLowerCase();

        ObservableList<OrdenTrabajo> filtradas =
                FXCollections.observableArrayList();

        for (OrdenTrabajo ot : listaOriginal) {

            if (ot == null) {
                continue;
            }

            boolean coincide = false;

            if (ot.getOt_codigo() != null
                    && ot.getOt_codigo()
                            .toLowerCase()
                            .contains(filtro)) {

                coincide = true;
            }

            if (ot.getOt_cliente() != null
                    && ot.getOt_cliente()
                            .toLowerCase()
                            .contains(filtro)) {

                coincide = true;
            }

            if (ot.getOt_campania() != null
                    && ot.getOt_campania()
                            .toLowerCase()
                            .contains(filtro)) {

                coincide = true;
            }

            if (ot.getOt_solicitante() != null
                    && ot.getOt_solicitante()
                            .toLowerCase()
                            .contains(filtro)) {

                coincide = true;
            }

            if (ot.getOt_ciudad() != null
                    && ot.getOt_ciudad()
                            .toLowerCase()
                            .contains(filtro)) {

                coincide = true;
            }

            if (ot.getOt_prioridad() != null
                    && ot.getOt_prioridad()
                            .toLowerCase()
                            .contains(filtro)) {

                coincide = true;
            }

            if (ot.getOt_estado() != null
                    && ot.getOt_estado()
                            .toLowerCase()
                            .contains(filtro)) {

                coincide = true;
            }

            if (coincide) {

                filtradas.add(ot);
            }
        }

        tb_ordenes.setItems(filtradas);

        actualizarTotal();
    }

    private void actualizarTotal() {

        if (lbl_total == null) {
            return;
        }

        if (tb_ordenes == null
                || tb_ordenes.getItems() == null) {

            lbl_total.setText("0");

            return;
        }

        lbl_total.setText(
                String.valueOf(
                        tb_ordenes.getItems().size()
                )
        );
    }

    @FXML
    private void acc_buscar() {

        if (txt_buscar != null) {

            filtrar(
                    txt_buscar.getText()
            );
        }
    }

    @FXML
    private void acc_limpiar() {

        if (txt_buscar != null) {

            txt_buscar.clear();
        }

        if (tb_ordenes != null) {

            tb_ordenes.setItems(
                    FXCollections.observableArrayList(
                            listaOriginal
                    )
            );
        }

        actualizarTotal();
    }

    @FXML
    private void acc_actualizar() {

        cargarDatos();

        if (txt_buscar != null
                && !txt_buscar.getText()
                        .trim()
                        .isEmpty()) {

            filtrar(
                    txt_buscar.getText()
            );
        }

        fun_mensajeInformacion(
                "Reporte actualizado correctamente."
        );
    }

    @FXML
    private void acc_imprimir() {

        if (tb_ordenes == null
                || tb_ordenes.getItems().isEmpty()) {

            fun_mensajeInformacion(
                    "No existen Órdenes de Trabajo para imprimir."
            );

            return;
        }

        fun_mensajeInformacion(
                "El reporte contiene "
                + tb_ordenes.getItems().size()
                + " Orden(es) de Trabajo."
        );
    }

    @FXML
    private void acc_exportar() {

        if (tb_ordenes == null
                || tb_ordenes.getItems().isEmpty()) {

            fun_mensajeInformacion(
                    "No existen datos para exportar."
            );

            return;
        }

        fun_mensajeInformacion(
                "Se exportarán "
                + tb_ordenes.getItems().size()
                + " Orden(es) de Trabajo."
        );
    }

    @FXML
    private void acc_cerrar() {

        if (btn_cerrar == null
                || btn_cerrar.getScene() == null) {

            return;
        }

        Stage stage =
                (Stage) btn_cerrar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase