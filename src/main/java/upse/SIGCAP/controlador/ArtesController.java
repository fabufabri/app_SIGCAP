// src/main/java/upse/SIGCAP/controlador/ArtesController.java

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
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class ArtesController implements Initializable {

    @FXML
    private TextField txt_buscar;

    @FXML
    private TableView<ItemOrdenTrabajo> tb_items;

    @FXML
    private TableColumn<ItemOrdenTrabajo, Integer> col_item;

    @FXML
    private TableColumn<ItemOrdenTrabajo, String> col_ot;

    @FXML
    private TableColumn<ItemOrdenTrabajo, String> col_local;

    @FXML
    private TableColumn<ItemOrdenTrabajo, String> col_producto;

    @FXML
    private TableColumn<ItemOrdenTrabajo, String> col_descripcion;

    @FXML
    private TableColumn<ItemOrdenTrabajo, String> col_medida;

    @FXML
    private TableColumn<ItemOrdenTrabajo, String> col_material;

    @FXML
    private TableColumn<ItemOrdenTrabajo, Integer> col_cantidad;

    @FXML
    private TableColumn<ItemOrdenTrabajo, String> col_estado;

    @FXML
    private TableColumn<ItemOrdenTrabajo, Integer> col_progreso;

    @FXML
    private Label lbl_total;

    @FXML
    private Label lbl_pendientes;

    @FXML
    private Label lbl_aprobados;

    @FXML
    private Label lbl_observados;

    @FXML
    private Label lbl_ot;

    @FXML
    private Label lbl_cliente;

    @FXML
    private Label lbl_campania;

    @FXML
    private Label lbl_local;

    @FXML
    private Label lbl_producto;

    @FXML
    private Label lbl_descripcion;

    @FXML
    private Label lbl_medida;

    @FXML
    private Label lbl_material;

    @FXML
    private Label lbl_cantidad;

    @FXML
    private Label lbl_estado;

    @FXML
    private Label lbl_progreso;

    private final Mad_OrdenTrabajo madOrdenTrabajo =
            new Mad_OrdenTrabajo();

    private final ObservableList<ItemOrdenTrabajo> listaCompleta =
            FXCollections.observableArrayList();

    private final ObservableList<ItemOrdenTrabajo> listaFiltrada =
            FXCollections.observableArrayList();

    private final ObservableList<OrdenTrabajo> ordenes =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarTabla();

        txt_buscar.textProperty().addListener(
                (obs, anterior, actual) -> aplicarFiltro()
        );

        tb_items.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, anterior, actual) -> {

                            if (actual != null) {
                                mostrarDetalle(actual);
                            }
                        }
                );

        cargarDatos();
    }

    private void configurarTabla() {

        col_item.setCellValueFactory(
                new PropertyValueFactory<>("itm_id")
        );

        col_ot.setCellValueFactory(
                new PropertyValueFactory<>("itm_ot_codigo")
        );

        col_local.setCellValueFactory(
                new PropertyValueFactory<>("itm_local")
        );

        col_producto.setCellValueFactory(
                new PropertyValueFactory<>("itm_producto")
        );

        col_descripcion.setCellValueFactory(
                new PropertyValueFactory<>("itm_descripcion")
        );

        col_medida.setCellValueFactory(
                new PropertyValueFactory<>("itm_medida")
        );

        col_material.setCellValueFactory(
                new PropertyValueFactory<>("itm_material")
        );

        col_cantidad.setCellValueFactory(
                new PropertyValueFactory<>("itm_cantidad")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("itm_estado")
        );

        col_progreso.setCellValueFactory(
                new PropertyValueFactory<>("itm_progreso")
        );
    }

    private void cargarDatos() {

        try {

            listaCompleta.clear();
            ordenes.clear();

            ObservableList<OrdenTrabajo> datos =
                    madOrdenTrabajo.getOrdenesTrabajo();

            if (datos != null) {
                ordenes.addAll(datos);
            }

            for (OrdenTrabajo ot : ordenes) {

                ObservableList<ItemOrdenTrabajo> items =
                        madOrdenTrabajo.getItems(
                                ot.getOt_id()
                        );

                if (items == null) {
                    continue;
                }

                for (ItemOrdenTrabajo item : items) {

                    item.setItm_ot_codigo(
                            ot.getOt_codigo()
                    );

                    if (esTrabajoDeArtes(item)) {
                        listaCompleta.add(item);
                    }
                }
            }

            aplicarFiltro();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron cargar los elementos de Artes.\n\n"
                    + e.getClass().getSimpleName()
                    + ":\n"
                    + e.getMessage()
            );
        }
    }

    private boolean esTrabajoDeArtes(
            ItemOrdenTrabajo item) {

        String estado =
                item.getItm_estado() == null
                        ? ""
                        : item.getItm_estado()
                                .trim()
                                .toLowerCase();

        return estado.isEmpty()
                || estado.contains("arte")
                || estado.contains("pendiente")
                || estado.contains("observado")
                || estado.contains("aprobado");
    }

    private void aplicarFiltro() {

        listaFiltrada.clear();

        String texto =
                txt_buscar.getText() == null
                        ? ""
                        : txt_buscar
                                .getText()
                                .trim()
                                .toLowerCase();

        for (ItemOrdenTrabajo item :
                listaCompleta) {

            if (texto.isEmpty()
                    || contiene(
                            item.getItm_ot_codigo(),
                            texto
                    )
                    || contiene(
                            item.getItm_local(),
                            texto
                    )
                    || contiene(
                            item.getItm_producto(),
                            texto
                    )
                    || contiene(
                            item.getItm_descripcion(),
                            texto
                    )
                    || contiene(
                            item.getItm_material(),
                            texto
                    )
                    || contiene(
                            item.getItm_estado(),
                            texto
                    )) {

                listaFiltrada.add(item);
            }
        }

        tb_items.setItems(
                listaFiltrada
        );

        calcularResumen();

        if (!listaFiltrada.isEmpty()) {

            tb_items
                    .getSelectionModel()
                    .selectFirst();

        } else {

            limpiarDetalle();
        }
    }

    private boolean contiene(
            String valor,
            String texto) {

        return valor != null
                && valor
                        .toLowerCase()
                        .contains(texto);
    }

    private void calcularResumen() {

        int total =
                listaCompleta.size();

        int pendientes = 0;
        int aprobados = 0;
        int observados = 0;

        for (ItemOrdenTrabajo item :
                listaCompleta) {

            String estado =
                    item.getItm_estado() == null
                            ? ""
                            : item.getItm_estado()
                                    .trim()
                                    .toLowerCase();

            if (estado.isEmpty()
                    || estado.contains("pendiente")
                    || estado.contains("en arte")) {

                pendientes++;

            } else if (estado.contains("aprobado")) {

                aprobados++;

            } else if (estado.contains("observado")) {

                observados++;
            }
        }

        lbl_total.setText(
                String.valueOf(total)
        );

        lbl_pendientes.setText(
                String.valueOf(pendientes)
        );

        lbl_aprobados.setText(
                String.valueOf(aprobados)
        );

        lbl_observados.setText(
                String.valueOf(observados)
        );
    }

    private void mostrarDetalle(
            ItemOrdenTrabajo item) {

        lbl_ot.setText(
                valor(item.getItm_ot_codigo())
        );

        lbl_local.setText(
                valor(item.getItm_local())
        );

        lbl_producto.setText(
                valor(item.getItm_producto())
        );

        lbl_descripcion.setText(
                valor(item.getItm_descripcion())
        );

        lbl_medida.setText(
                valor(item.getItm_medida())
        );

        lbl_material.setText(
                valor(item.getItm_material())
        );

        lbl_cantidad.setText(
                String.valueOf(
                        item.getItm_cantidad()
                )
        );

        lbl_estado.setText(
                valor(item.getItm_estado())
        );

        lbl_progreso.setText(
                item.getItm_progreso()
                + "%"
        );
    }

    private void limpiarDetalle() {

        lbl_ot.setText("-");
        lbl_cliente.setText("-");
        lbl_campania.setText("-");
        lbl_local.setText("-");
        lbl_producto.setText("-");
        lbl_descripcion.setText("-");
        lbl_medida.setText("-");
        lbl_material.setText("-");
        lbl_cantidad.setText("0");
        lbl_estado.setText("-");
        lbl_progreso.setText("0%");
    }

    private String valor(
            String texto) {

        return texto == null
                || texto.trim().isEmpty()
                ? "-"
                : texto;
    }

    @FXML
    private void acc_actualizar() {

        cargarDatos();
    }

    @FXML
    private void acc_aprobar() {

        ItemOrdenTrabajo item =
                tb_items
                        .getSelectionModel()
                        .getSelectedItem();

        if (item == null) {

            fun_mensajeError(
                    "Seleccione un elemento de producción."
            );

            return;
        }

        fun_mensajeInformacion(
                "El elemento seleccionado está listo para aprobación."
        );
    }

    @FXML
    private void acc_observar() {

        ItemOrdenTrabajo item =
                tb_items
                        .getSelectionModel()
                        .getSelectedItem();

        if (item == null) {

            fun_mensajeError(
                    "Seleccione un elemento de producción."
            );

            return;
        }

        fun_mensajeInformacion(
                "El elemento seleccionado ha sido marcado para revisión."
        );
    }

    @FXML
    private void acc_abrirOT() {

        ItemOrdenTrabajo item =
                tb_items
                        .getSelectionModel()
                        .getSelectedItem();

        if (item == null) {

            fun_mensajeError(
                    "Seleccione un elemento."
            );

            return;
        }

        String codigo =
                item.getItm_ot_codigo();

        for (OrdenTrabajo ot :
                ordenes) {

            if (codigo != null
                    && codigo.equalsIgnoreCase(
                            ot.getOt_codigo()
                    )) {

                try {

                    FXMLLoaderHelper
                            .abrirFormularioOT(
                                    ot.getOt_id()
                            );

                    cargarDatos();

                } catch (Exception e) {

                    e.printStackTrace();

                    fun_mensajeError(
                            "No se pudo abrir la OT.\n\n"
                            + e.getMessage()
                    );
                }

                return;
            }
        }

        fun_mensajeError(
                "No se encontró la OT asociada."
        );
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