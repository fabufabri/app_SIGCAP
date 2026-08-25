// src/main/java/upse/SIGCAP/controlador/NuevaOrdenTrabajoController.java
package upse.SIGCAP.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import upse.SIGCAP.mad.Mad_OrdenTrabajo;
import upse.SIGCAP.modelo.ItemOrdenTrabajo;
import upse.SIGCAP.modelo.OrdenTrabajo;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class NuevaOrdenTrabajoController
        implements Initializable {

    @FXML private Label lbl_titulo;
    @FXML private Label lbl_usuario;

    @FXML private TextField txt_codigo;
    @FXML private ComboBox<String> cmb_cliente;
    @FXML private ComboBox<String> cmb_campania;
    @FXML private TextField txt_solicitante;
    @FXML private ComboBox<String> cmb_ciudad;
    @FXML private DatePicker dtp_fecha;
    @FXML private DatePicker dtp_fecha_requerida;
    @FXML private ComboBox<String> cmb_prioridad;
    @FXML private ComboBox<String> cmb_responsable;
    @FXML private TextArea txt_observaciones;

    @FXML private TableView<ItemOrdenTrabajo> tb_detalles;

    @FXML private TableColumn<ItemOrdenTrabajo, Integer> col_numero;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_local;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_producto;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_descripcion;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_medida;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_material;
    @FXML private TableColumn<ItemOrdenTrabajo, Integer> col_cantidad;
    @FXML private TableColumn<ItemOrdenTrabajo, String> col_instalacion;

    @FXML private Label lbl_total;

    private int otId = 0;

    private final Mad_OrdenTrabajo madOrdenTrabajo =
            new Mad_OrdenTrabajo();

    private final ObservableList<ItemOrdenTrabajo> detalles =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarTabla();
        cargarCombos();

        dtp_fecha.setValue(
                LocalDate.now()
        );

        dtp_fecha_requerida.setValue(
                LocalDate.now()
        );

        cmb_prioridad.setValue(
                "NORMAL"
        );

        tb_detalles.setItems(
                detalles
        );

        lbl_usuario.setText(
                "-"
        );
    }

    public void setOtId(int id) {

        this.otId = id;

        if (id > 0) {

            cargarOT(id);

        } else {

            prepararNuevaOT();
        }
    }

    private void prepararNuevaOT() {

        lbl_titulo.setText(
                "NUEVA ORDEN DE TRABAJO"
        );

        generarCodigo();

        dtp_fecha.setValue(
                LocalDate.now()
        );

        dtp_fecha_requerida.setValue(
                LocalDate.now()
        );

        detalles.clear();

        actualizarTotal();
    }

    private void cargarOT(int id) {

        try {

            OrdenTrabajo ot =
                    madOrdenTrabajo.buscaOrdenTrabajoxId(id);

            if (ot == null) {

                fun_mensajeError(
                        "No se encontró la Orden de Trabajo."
                );

                return;
            }

            lbl_titulo.setText(
                    "EDITAR ORDEN DE TRABAJO"
            );

            txt_codigo.setText(
                    valor(ot.getOt_codigo())
            );

            seleccionarCombo(
                    cmb_cliente,
                    ot.getOt_cliente()
            );

            seleccionarCombo(
                    cmb_campania,
                    ot.getOt_campania()
            );

            txt_solicitante.setText(
                    valorEditable(
                            ot.getOt_solicitante()
                    )
            );

            seleccionarCombo(
                    cmb_ciudad,
                    ot.getOt_ciudad()
            );

            dtp_fecha.setValue(
                    ot.getOt_fecha()
            );

            dtp_fecha_requerida.setValue(
                    ot.getOt_fecha_requerida()
            );

            seleccionarCombo(
                    cmb_prioridad,
                    ot.getOt_prioridad()
            );

            seleccionarCombo(
                    cmb_responsable,
                    ot.getOt_responsable()
            );

            txt_observaciones.setText(
                    valorEditable(
                            ot.getOt_observaciones()
                    )
            );

            ObservableList<ItemOrdenTrabajo> datos =
                    madOrdenTrabajo.getItems(id);

            detalles.clear();

            if (datos != null) {
                detalles.addAll(datos);
            }

            actualizarTotal();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo cargar la OT.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void configurarTabla() {

        col_numero.setCellValueFactory(
                new PropertyValueFactory<>("itm_id")
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

        col_instalacion.setCellValueFactory(
                new PropertyValueFactory<>("itm_instalacion")
        );
    }

    private void cargarCombos() {

        cmb_cliente.setItems(
                FXCollections.observableArrayList(
                        "Seleccione cliente",
                        "Cliente A",
                        "Cliente B",
                        "Cliente C"
                )
        );

        cmb_campania.setItems(
                FXCollections.observableArrayList(
                        "Seleccione campaña",
                        "Campaña 2026",
                        "Campaña Institucional",
                        "Campaña Promocional"
                )
        );

        cmb_ciudad.setItems(
                FXCollections.observableArrayList(
                        "Seleccione ciudad",
                        "QUITO",
                        "GUAYAQUIL",
                        "CUENCA",
                        "MANTA",
                        "AMBATO"
                )
        );

        cmb_prioridad.setItems(
                FXCollections.observableArrayList(
                        "BAJA",
                        "NORMAL",
                        "ALTA",
                        "URGENTE"
                )
        );

        cmb_responsable.setItems(
                FXCollections.observableArrayList(
                        "Seleccione responsable",
                        "Diseño",
                        "Producción",
                        "Administración"
                )
        );

        cmb_cliente.getSelectionModel().selectFirst();
        cmb_campania.getSelectionModel().selectFirst();
        cmb_ciudad.getSelectionModel().selectFirst();
        cmb_responsable.getSelectionModel().selectFirst();
        cmb_prioridad.setValue("NORMAL");
    }

    private void generarCodigo() {

        txt_codigo.setText(
                "OT-" + System.currentTimeMillis()
        );
    }

    @FXML
    private void acc_agregar() {

        abrirDetalle(null);
    }

    @FXML
    private void acc_editarDetalle() {

        ItemOrdenTrabajo seleccionado =
                tb_detalles
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            fun_mensajeInformacion(
                    "Seleccione un detalle."
            );

            return;
        }

        abrirDetalle(seleccionado);
    }

    @FXML
    private void acc_eliminarDetalle() {

        ItemOrdenTrabajo seleccionado =
                tb_detalles
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            fun_mensajeInformacion(
                    "Seleccione un detalle."
            );

            return;
        }

        Alert alerta =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        alerta.setTitle(
                "SIGCAP"
        );

        alerta.setHeaderText(
                "Eliminar detalle"
        );

        alerta.setContentText(
                "¿Desea eliminar el detalle seleccionado?"
        );

        if (alerta.showAndWait()
                .get()
                .getButtonData()
                .isDefaultButton()) {

            detalles.remove(
                    seleccionado
            );

            renumerarDetalles();

            actualizarTotal();
        }
    }

    private void abrirDetalle(
            ItemOrdenTrabajo item) {

        try {

            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(
                            App.class.getResource(
                                    "/upse/SIGCAP/vistas/"
                                    + "NuevoItemOT.fxml"
                            )
                    );

            javafx.scene.Parent root =
                    loader.load();

            NuevoItemOTController controller =
                    loader.getController();

            if (item != null) {
                controller.setItem(item);
            }

            Stage stage =
                    new Stage();

            stage.initModality(
                    javafx.stage.Modality.APPLICATION_MODAL
            );

            stage.initOwner(
                    obtenerStage()
            );

            stage.setTitle(
                    item == null
                            ? "Agregar detalle"
                            : "Editar detalle"
            );

            stage.setScene(
                    new javafx.scene.Scene(
                            root
                    )
            );

            stage.setResizable(false);

            stage.showAndWait();

            ItemOrdenTrabajo resultado =
                    controller.getItem();

            if (resultado != null) {

                if (item == null) {

                    resultado.setItm_id(
                            detalles.size() + 1
                    );

                    detalles.add(
                            resultado
                    );

                } else {

                    int indice =
                            detalles.indexOf(item);

                    if (indice >= 0) {

                        detalles.set(
                                indice,
                                resultado
                        );
                    }
                }

                actualizarTotal();
            }

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir el detalle.\n\n"
                    + e.getMessage()
            );
        }
    }

    private Stage obtenerStage() {

        return (Stage) txt_codigo
                .getScene()
                .getWindow();
    }

    private void renumerarDetalles() {

        int numero = 1;

        for (ItemOrdenTrabajo item : detalles) {

            item.setItm_id(
                    numero++
            );
        }

        tb_detalles.refresh();
    }

    private void actualizarTotal() {

        int total = 0;

        for (ItemOrdenTrabajo item : detalles) {

            if (item != null) {

                total += item.getItm_cantidad();
            }
        }

        lbl_total.setText(
                String.valueOf(total)
        );
    }

    @FXML
    private void acc_grabar() {

        if (!validar()) {
            return;
        }

        /*
         * Aquí se conectará el SP maestro-detalle definitivo.
         *
         * La estructura ya está preparada para:
         *
         * NUEVA OT:
         * INSERT cabecera + INSERT detalles
         *
         * EDITAR OT:
         * UPDATE cabecera + INSERT/UPDATE/DELETE detalles
         *
         * Todo dentro de una transacción.
         */

        fun_mensajeInformacion(
                otId == 0
                        ? "La nueva OT está lista para guardarse."
                        : "Los cambios de la OT están listos para guardarse."
        );

        cerrar();
    }

    private boolean validar() {

        if (cmb_cliente.getValue() == null
                || cmb_cliente.getValue()
                        .equals("Seleccione cliente")) {

            fun_mensajeInformacion(
                    "Seleccione el cliente."
            );

            return false;
        }

        if (cmb_campania.getValue() == null
                || cmb_campania.getValue()
                        .equals("Seleccione campaña")) {

            fun_mensajeInformacion(
                    "Seleccione la campaña."
            );

            return false;
        }

        if (txt_solicitante.getText()
                .trim()
                .isEmpty()) {

            fun_mensajeInformacion(
                    "Ingrese el solicitante."
            );

            return false;
        }

        if (cmb_ciudad.getValue() == null
                || cmb_ciudad.getValue()
                        .equals("Seleccione ciudad")) {

            fun_mensajeInformacion(
                    "Seleccione la ciudad."
            );

            return false;
        }

        if (dtp_fecha_requerida.getValue() == null) {

            fun_mensajeInformacion(
                    "Seleccione la fecha requerida."
            );

            return false;
        }

        if (dtp_fecha.getValue() != null
                && dtp_fecha_requerida.getValue()
                        .isBefore(dtp_fecha.getValue())) {

            fun_mensajeInformacion(
                    "La fecha requerida no puede ser anterior "
                    + "a la fecha de la OT."
            );

            return false;
        }

        if (detalles.isEmpty()) {

            fun_mensajeInformacion(
                    "La OT debe tener al menos un detalle."
            );

            return false;
        }

        return true;
    }

    @FXML
    private void acc_limpiar() {

        if (otId > 0) {

            cargarOT(otId);

        } else {

            prepararNuevaOT();
        }
    }

    @FXML
    private void acc_cancelar() {

        cerrar();
    }

    private void cerrar() {

        Stage stage =
                obtenerStage();

        stage.close();
    }

    private void seleccionarCombo(
            ComboBox<String> combo,
            String valor) {

        if (valor == null) {
            return;
        }

        if (!combo.getItems().contains(valor)) {

            combo.getItems().add(valor);
        }

        combo.setValue(valor);
    }

    private String valor(String texto) {

        return texto == null
                ? "-"
                : texto;
    }

    private String valorEditable(String texto) {

        return texto == null
                ? ""
                : texto;
    }

}//fin clase