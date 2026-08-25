// src/main/java/upse/SIGCAP/controlador/BuscarOTController.java

package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import upse.SIGCAP.general.Mod_VariablesGlobales;
import upse.SIGCAP.mad.Mad_OrdenTrabajo;
import upse.SIGCAP.modelo.OrdenTrabajo;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class BuscarOTController implements Initializable {

    @FXML
    private TextField txt_buscar;

    @FXML
    private RadioButton rad_codigo;

    @FXML
    private RadioButton rad_cliente;

    @FXML
    private RadioButton rad_campania;

    @FXML
    private RadioButton rad_estado;

    @FXML
    private RadioButton rad_ciudad;

    @FXML
    private RadioButton rad_responsable;

    @FXML
    private ComboBox<String> cmb_estado;

    @FXML
    private ComboBox<String> cmb_ciudad;

    @FXML
    private TableView<OrdenTrabajo> tb_ordenes;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_codigo;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_cliente;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_campania;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_ciudad;

    @FXML
    private TableColumn<OrdenTrabajo, Object> col_fecha;

    @FXML
    private TableColumn<OrdenTrabajo, Object> col_fecha_requerida;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_prioridad;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_responsable;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_estado;

    @FXML
    private Label lbl_total;

    private final Mad_OrdenTrabajo madOrdenTrabajo =
            new Mad_OrdenTrabajo();

    private ObservableList<OrdenTrabajo> listaCompleta =
            FXCollections.observableArrayList();

    private final ObservableList<OrdenTrabajo> listaFiltrada =
            FXCollections.observableArrayList();

    private final ToggleGroup grupoBusqueda =
            new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            validarFXML();

            configurarTabla();
            configurarBusqueda();
            cargarFiltros();
            cargarOrdenes();

            tb_ordenes.setOnMouseClicked(
                    this::eventoDobleClick
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Error inicializando BuscarOT: "
                    + e.getMessage(),
                    e
            );
        }
    }

    private void validarFXML() {

        if (txt_buscar == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"txt_buscar\" en BuscarOT.fxml"
            );
        }

        if (rad_codigo == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"rad_codigo\" en BuscarOT.fxml"
            );
        }

        if (rad_cliente == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"rad_cliente\" en BuscarOT.fxml"
            );
        }

        if (rad_campania == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"rad_campania\" en BuscarOT.fxml"
            );
        }

        if (rad_estado == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"rad_estado\" en BuscarOT.fxml"
            );
        }

        if (rad_ciudad == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"rad_ciudad\" en BuscarOT.fxml"
            );
        }

        if (rad_responsable == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"rad_responsable\" en BuscarOT.fxml"
            );
        }

        if (cmb_estado == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"cmb_estado\" en BuscarOT.fxml"
            );
        }

        if (cmb_ciudad == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"cmb_ciudad\" en BuscarOT.fxml"
            );
        }

        if (tb_ordenes == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"tb_ordenes\" en BuscarOT.fxml"
            );
        }

        if (lbl_total == null) {
            throw new RuntimeException(
                    "No existe fx:id=\"lbl_total\" en BuscarOT.fxml"
            );
        }

        if (col_codigo == null
                || col_cliente == null
                || col_campania == null
                || col_ciudad == null
                || col_fecha == null
                || col_fecha_requerida == null
                || col_prioridad == null
                || col_responsable == null
                || col_estado == null) {

            throw new RuntimeException(
                    "Falta una TableColumn con fx:id en BuscarOT.fxml"
            );
        }
    }

    private void configurarTabla() {

        col_codigo.setCellValueFactory(
                new PropertyValueFactory<>("ot_codigo")
        );

        col_cliente.setCellValueFactory(
                new PropertyValueFactory<>("ot_cliente")
        );

        col_campania.setCellValueFactory(
                new PropertyValueFactory<>("ot_campania")
        );

        col_ciudad.setCellValueFactory(
                new PropertyValueFactory<>("ot_ciudad")
        );

        col_fecha.setCellValueFactory(
                new PropertyValueFactory<>("ot_fecha")
        );

        col_fecha_requerida.setCellValueFactory(
                new PropertyValueFactory<>("ot_fecha_requerida")
        );

        col_prioridad.setCellValueFactory(
                new PropertyValueFactory<>("ot_prioridad")
        );

        col_responsable.setCellValueFactory(
                new PropertyValueFactory<>("ot_responsable")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("ot_estado")
        );
    }

    private void configurarBusqueda() {

        rad_codigo.setToggleGroup(grupoBusqueda);
        rad_cliente.setToggleGroup(grupoBusqueda);
        rad_campania.setToggleGroup(grupoBusqueda);
        rad_estado.setToggleGroup(grupoBusqueda);
        rad_ciudad.setToggleGroup(grupoBusqueda);
        rad_responsable.setToggleGroup(grupoBusqueda);

        rad_codigo.setSelected(true);

        txt_buscar.textProperty().addListener(
                (obs, anterior, actual) ->
                        aplicarFiltro()
        );

        grupoBusqueda.selectedToggleProperty().addListener(
                (obs, anterior, actual) ->
                        aplicarFiltro()
        );
    }

    private void cargarFiltros() {

        cmb_estado.setItems(
                FXCollections.observableArrayList(
                        "Todos",
                        "PENDIENTE",
                        "EN ARTES",
                        "EN PRODUCCIÓN",
                        "EN INSTALACIÓN",
                        "COMPLETADO",
                        "CANCELADO"
                )
        );

        cmb_ciudad.setItems(
                FXCollections.observableArrayList(
                        "Todas",
                        "QUITO",
                        "GUAYAQUIL",
                        "CUENCA",
                        "MANTA",
                        "AMBATO"
                )
        );

        cmb_estado.setValue("Todos");
        cmb_ciudad.setValue("Todas");

        cmb_estado.valueProperty().addListener(
                (obs, anterior, actual) ->
                        aplicarFiltro()
        );

        cmb_ciudad.valueProperty().addListener(
                (obs, anterior, actual) ->
                        aplicarFiltro()
        );
    }

    private void cargarOrdenes() {

        try {

            ObservableList<OrdenTrabajo> datos =
                    madOrdenTrabajo.getOrdenTrabajos();

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
                txt_buscar.getText() == null
                        ? ""
                        : txt_buscar.getText()
                                .trim()
                                .toLowerCase();

        for (OrdenTrabajo ot : listaCompleta) {

            if (ot == null) {
                continue;
            }

            if (!cumpleTexto(ot, texto)) {
                continue;
            }

            if (!cumpleEstado(ot)) {
                continue;
            }

            if (!cumpleCiudad(ot)) {
                continue;
            }

            listaFiltrada.add(ot);
        }

        tb_ordenes.setItems(listaFiltrada);

        lbl_total.setText(
                String.valueOf(listaFiltrada.size())
        );
    }

    private boolean cumpleTexto(
            OrdenTrabajo ot,
            String texto) {

        if (texto.isEmpty()) {
            return true;
        }

        RadioButton seleccionado =
                (RadioButton) grupoBusqueda.getSelectedToggle();

        if (seleccionado == null) {
            return true;
        }

        switch (seleccionado.getText().toLowerCase()) {

            case "ot":
                return contiene(
                        ot.getOt_codigo(),
                        texto
                );

            case "cliente":
                return contiene(
                        ot.getOt_cliente(),
                        texto
                );

            case "campaña":
                return contiene(
                        ot.getOt_campania(),
                        texto
                );

            case "estado":
                return contiene(
                        ot.getOt_estado(),
                        texto
                );

            case "ciudad":
                return contiene(
                        ot.getOt_ciudad(),
                        texto
                );

            case "responsable":
                return contiene(
                        ot.getOt_responsable(),
                        texto
                );

            default:
                return true;
        }
    }

    private boolean cumpleEstado(
            OrdenTrabajo ot) {

        String estado =
                cmb_estado.getValue();

        if (estado == null
                || estado.equalsIgnoreCase("Todos")) {

            return true;
        }

        return estado.equalsIgnoreCase(
                valor(ot.getOt_estado())
        );
    }

    private boolean cumpleCiudad(
            OrdenTrabajo ot) {

        String ciudad =
                cmb_ciudad.getValue();

        if (ciudad == null
                || ciudad.equalsIgnoreCase("Todas")) {

            return true;
        }

        return ciudad.equalsIgnoreCase(
                valor(ot.getOt_ciudad())
        );
    }

    private boolean contiene(
            String valor,
            String texto) {

        if (valor == null) {
            return false;
        }

        return valor
                .toLowerCase()
                .contains(texto);
    }

    private String valor(String texto) {

        return texto == null
                ? ""
                : texto;
    }

    @FXML
    private void acc_buscar() {

        aplicarFiltro();
    }

    @FXML
    private void acc_actualizar() {

        txt_buscar.clear();

        rad_codigo.setSelected(true);

        cmb_estado.setValue("Todos");
        cmb_ciudad.setValue("Todas");

        cargarOrdenes();
    }

    @FXML
    private void acc_limpiar() {

        txt_buscar.clear();

        rad_codigo.setSelected(true);

        cmb_estado.setValue("Todos");
        cmb_ciudad.setValue("Todas");

        aplicarFiltro();
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

    private void eventoDobleClick(
            MouseEvent evento) {

        if (evento.getButton() == MouseButton.PRIMARY
                && evento.getClickCount() == 2) {

            OrdenTrabajo ot =
                    tb_ordenes
                            .getSelectionModel()
                            .getSelectedItem();

            if (ot != null) {

                abrirOT(
                        ot.getOt_id()
                );
            }
        }
    }

    private void abrirOT(int id) {

        try {

            Mod_VariablesGlobales.g_otSeleccionada = id;

            App.setRoot("Principal");

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir la Orden de Trabajo.\n\n"
                    + e.getMessage()
            );
        }
    }

}//fin clase