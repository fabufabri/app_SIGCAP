// src/main/java/upse/SIGCAP/controlador/ProduccionGraficaController.java

package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import upse.SIGCAP.mad.Mad_OrdenTrabajo;
import upse.SIGCAP.modelo.ItemOrdenTrabajo;
import upse.SIGCAP.modelo.OrdenTrabajo;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class ProduccionGraficaController implements Initializable {

    @FXML private TextField txt_buscar;

    @FXML private TableView<OrdenTrabajo> tb_ordenes;
    @FXML private TableColumn<OrdenTrabajo, String> col_codigo;
    @FXML private TableColumn<OrdenTrabajo, String> col_cliente;
    @FXML private TableColumn<OrdenTrabajo, String> col_campania;
    @FXML private TableColumn<OrdenTrabajo, String> col_ciudad;
    @FXML private TableColumn<OrdenTrabajo, Object> col_fecha_requerida;
    @FXML private TableColumn<OrdenTrabajo, String> col_prioridad;
    @FXML private TableColumn<OrdenTrabajo, String> col_responsable;
    @FXML private TableColumn<OrdenTrabajo, String> col_estado;

    @FXML private TableView<ItemOrdenTrabajo> tb_items;
    @FXML private TableColumn<ItemOrdenTrabajo, Integer> col_item;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_local;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_producto;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_descripcion;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_medida;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_material;
    @FXML private TableColumn<ItemOrdenTrabajo, Integer> col_cantidad;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_instalacion;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_item_estado;
    @FXML private TableColumn<ItemOrdenTrabajo, Integer> col_progreso;

    @FXML private Label lbl_ot;
    @FXML private Label lbl_cliente;
    @FXML private Label lbl_campania;
    @FXML private Label lbl_fecha_requerida;
    @FXML private Label lbl_estado;
    @FXML private Label lbl_total;
    @FXML private Label lbl_artes;
    @FXML private Label lbl_produccion;
    @FXML private Label lbl_instalacion;
    @FXML private Label lbl_completados;
    @FXML private Label lbl_items;

    private final Mad_OrdenTrabajo madOrdenTrabajo =
            new Mad_OrdenTrabajo();

    private final ObservableList<OrdenTrabajo> listaCompleta =
            FXCollections.observableArrayList();

    private final ObservableList<OrdenTrabajo> listaFiltrada =
            FXCollections.observableArrayList();

    private final ObservableList<ItemOrdenTrabajo> listaItems =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarTabla();

        limpiarDetalle();

        txt_buscar.textProperty().addListener(
                (obs, anterior, actual) -> aplicarFiltro()
        );

        tb_ordenes.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, anterior, actual) -> {

                            if (actual != null) {
                                cargarDetalle(actual);
                            }
                        }
                );

        cargarOrdenes();
    }

    private void configurarTabla() {

        col_codigo.setCellValueFactory(
                new PropertyValueFactory<>("ot_codigo"));

        col_cliente.setCellValueFactory(
                new PropertyValueFactory<>("ot_cliente"));

        col_campania.setCellValueFactory(
                new PropertyValueFactory<>("ot_campania"));

        col_ciudad.setCellValueFactory(
                new PropertyValueFactory<>("ot_ciudad"));

        col_fecha_requerida.setCellValueFactory(
                new PropertyValueFactory<>("ot_fecha_requerida"));

        col_prioridad.setCellValueFactory(
                new PropertyValueFactory<>("ot_prioridad"));

        col_responsable.setCellValueFactory(
                new PropertyValueFactory<>("ot_responsable"));

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("ot_estado"));

        col_item.setCellValueFactory(
                new PropertyValueFactory<>("itm_id"));

        col_local.setCellValueFactory(
                new PropertyValueFactory<>("itm_local"));

        col_producto.setCellValueFactory(
                new PropertyValueFactory<>("itm_producto"));

        col_descripcion.setCellValueFactory(
                new PropertyValueFactory<>("itm_descripcion"));

        col_medida.setCellValueFactory(
                new PropertyValueFactory<>("itm_medida"));

        col_material.setCellValueFactory(
                new PropertyValueFactory<>("itm_material"));

        col_cantidad.setCellValueFactory(
                new PropertyValueFactory<>("itm_cantidad"));

        col_instalacion.setCellValueFactory(
                new PropertyValueFactory<>("itm_instalacion"));

        col_item_estado.setCellValueFactory(
                new PropertyValueFactory<>("itm_estado"));

        col_progreso.setCellValueFactory(
                new PropertyValueFactory<>("itm_progreso"));
    }

    private void cargarOrdenes() {

        try {

            ObservableList<OrdenTrabajo> datos =
                    madOrdenTrabajo.getOrdenesTrabajo();

            listaCompleta.clear();

            if (datos != null) {
                listaCompleta.addAll(datos);
            }

            aplicarFiltro();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron cargar las Órdenes de Trabajo.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void aplicarFiltro() {

        listaFiltrada.clear();

        String texto =
                txt_buscar.getText()
                        .trim()
                        .toLowerCase();

        for (OrdenTrabajo ot : listaCompleta) {

            if (texto.isEmpty()
                    || contiene(ot.getOt_codigo(), texto)
                    || contiene(ot.getOt_cliente(), texto)
                    || contiene(ot.getOt_campania(), texto)
                    || contiene(ot.getOt_ciudad(), texto)
                    || contiene(ot.getOt_responsable(), texto)
                    || contiene(ot.getOt_estado(), texto)) {

                listaFiltrada.add(ot);
            }
        }

        tb_ordenes.setItems(listaFiltrada);

        calcularResumen();

        if (!listaFiltrada.isEmpty()) {
            tb_ordenes.getSelectionModel().selectFirst();
        }
    }

    private boolean contiene(
            String valor,
            String texto) {

        return valor != null
                && valor.toLowerCase().contains(texto);
    }

    private void cargarDetalle(
            OrdenTrabajo ot) {

        try {

            lbl_ot.setText(valor(ot.getOt_codigo()));
            lbl_cliente.setText(valor(ot.getOt_cliente()));
            lbl_campania.setText(valor(ot.getOt_campania()));

            lbl_fecha_requerida.setText(
                    ot.getOt_fecha_requerida() == null
                            ? "-"
                            : ot.getOt_fecha_requerida().toString()
            );

            lbl_estado.setText(valor(ot.getOt_estado()));

            ObservableList<ItemOrdenTrabajo> datos =
                    madOrdenTrabajo.getItems(ot.getOt_id());

            listaItems.clear();

            if (datos != null) {
                listaItems.addAll(datos);
            }

            tb_items.setItems(listaItems);

            lbl_items.setText(
                    String.valueOf(listaItems.size())
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron cargar los elementos de la OT.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void limpiarDetalle() {

        lbl_ot.setText("-");
        lbl_cliente.setText("-");
        lbl_campania.setText("-");
        lbl_fecha_requerida.setText("-");
        lbl_estado.setText("-");
        lbl_items.setText("0");

        listaItems.clear();

        tb_items.setItems(listaItems);
    }

    private void calcularResumen() {

        int total = listaCompleta.size();
        int artes = 0;
        int produccion = 0;
        int instalacion = 0;
        int completados = 0;

        for (OrdenTrabajo ot : listaCompleta) {

            String estado =
                    ot.getOt_estado() == null
                            ? ""
                            : ot.getOt_estado()
                                    .trim()
                                    .toLowerCase();

            if (estado.contains("artes")) {
                artes++;
            }

            if (estado.contains("produccion")
                    || estado.contains("producción")) {
                produccion++;
            }

            if (estado.contains("instalacion")
                    || estado.contains("instalación")) {
                instalacion++;
            }

            if (estado.contains("completado")) {
                completados++;
            }
        }

        lbl_total.setText(String.valueOf(total));
        lbl_artes.setText(String.valueOf(artes));
        lbl_produccion.setText(String.valueOf(produccion));
        lbl_instalacion.setText(String.valueOf(instalacion));
        lbl_completados.setText(String.valueOf(completados));
    }

    private String valor(String texto) {

        return texto == null ? "-" : texto;
    }

    @FXML
    private void acc_actualizar() {

        cargarOrdenes();
    }

    @FXML
    private void acc_abrirOT() {

        OrdenTrabajo ot =
                tb_ordenes.getSelectionModel()
                        .getSelectedItem();

        if (ot == null) {

            fun_mensajeError(
                    "Seleccione una Orden de Trabajo."
            );

            return;
        }

        try {

            FXMLLoaderHelper.abrirFormularioOT(
                    ot.getOt_id()
            );

            cargarOrdenes();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir la Orden de Trabajo.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_volver() {

        try {

            App.setRoot("Dashboard");

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo regresar al Dashboard.\n\n"
                    + e.getMessage()
            );
        }
    }

}//fin clase