package upse.SIGCAP.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import upse.SIGCAP.mad.Mad_DetalleProforma;
import upse.SIGCAP.mad.Mad_Proforma;
import upse.SIGCAP.modelo.DetalleProforma;
import upse.SIGCAP.modelo.Proforma;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class ProformaController implements Initializable {

    @FXML
    private TextField txt_codigo;

    @FXML
    private TextField txt_cliente;

    @FXML
    private TextField txt_campania;

    @FXML
    private DatePicker dtp_fecha;

    @FXML
    private DatePicker dtp_validez;

    @FXML
    private ComboBox<String> cmb_estado;

    @FXML
    private TextArea txt_observaciones;

    @FXML
    private TextField txt_descripcion;

    @FXML
    private TextField txt_cantidad;

    @FXML
    private TextField txt_precio;

    @FXML
    private TextField txt_descuento;

    @FXML
    private TableView<DetalleProforma> tb_detalles;

    @FXML
    private TableColumn<DetalleProforma, String> col_descripcion;

    @FXML
    private TableColumn<DetalleProforma, Integer> col_cantidad;

    @FXML
    private TableColumn<DetalleProforma, Double> col_precio;

    @FXML
    private TableColumn<DetalleProforma, Double> col_descuento;

    @FXML
    private TableColumn<DetalleProforma, Double> col_total;

    @FXML
    private Label lbl_subtotal;

    @FXML
    private Label lbl_iva;

    @FXML
    private Label lbl_total;

    @FXML
    private Button btn_cancelar;

    private int bandera = 0;

    private int cliId = 0;

    private Integer camId = null;

    private final Mad_Proforma madProforma
            = new Mad_Proforma();

    private final Mad_DetalleProforma madDetalle
            = new Mad_DetalleProforma();

    private final ObservableList<DetalleProforma> detalles
            = FXCollections.observableArrayList();

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        cmb_estado.setItems(
                FXCollections.observableArrayList(
                        "PENDIENTE",
                        "APROBADA",
                        "RECHAZADA",
                        "VENCIDA"
                )
        );

        cmb_estado.setValue(
                "PENDIENTE"
        );

        dtp_fecha.setValue(
                LocalDate.now()
        );

        txt_cantidad.setText("1");
        txt_precio.setText("0");
        txt_descuento.setText("0");

        col_descripcion.setCellValueFactory(
                new PropertyValueFactory<>("dpf_descripcion")
        );

        col_cantidad.setCellValueFactory(
                new PropertyValueFactory<>("dpf_cantidad")
        );

        col_precio.setCellValueFactory(
                new PropertyValueFactory<>("dpf_precio_unitario")
        );

        col_descuento.setCellValueFactory(
                new PropertyValueFactory<>("dpf_descuento")
        );

        col_total.setCellValueFactory(
                new PropertyValueFactory<>("dpf_total")
        );

        tb_detalles.setItems(
                detalles
        );

        calcularTotales();
    }

    public void recuperarProforma(
            String id) {

        if (id == null
                || id.trim().isEmpty()) {

            limpiar();

            return;
        }

        try {

            Proforma obj
                    = madProforma.buscaProformaxId(
                            Integer.parseInt(id)
                    );

            if (obj == null) {

                limpiar();

                return;
            }

            bandera
                    = obj.getPfr_id();

            txt_codigo.setText(
                    obj.getPfr_codigo()
            );

            txt_cliente.setText(
                    obj.getCli_nombre()
            );

            txt_campania.setText(
                    obj.getCam_nombre()
            );

            cliId
                    = obj.getCli_id();

            camId
                    = obj.getCam_id();

            dtp_fecha.setValue(
                    obj.getPfr_fecha()
            );

            dtp_validez.setValue(
                    obj.getPfr_validez()
            );

            cmb_estado.setValue(
                    obj.getPfr_estado()
            );

            txt_observaciones.setText(
                    obj.getPfr_observaciones()
            );

            detalles.clear();

            ObservableList<DetalleProforma> lista
                    = madDetalle.getDetalles(
                            obj.getPfr_id()
                    );

            if (lista != null) {

                detalles.addAll(
                        lista
                );
            }

            calcularTotales();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "Error recuperando Proforma.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_agregar() {

        try {

            String descripcion
                    = txt_descripcion
                            .getText()
                            .trim();

            if (descripcion.isEmpty()) {

                fun_mensajeInformacion(
                        "Ingrese la descripción."
                );

                return;
            }

            int cantidad
                    = Integer.parseInt(
                            txt_cantidad
                                    .getText()
                                    .trim()
                    );

            double precio
                    = Double.parseDouble(
                            txt_precio
                                    .getText()
                                    .trim()
                    );

            double descuento
                    = Double.parseDouble(
                            txt_descuento
                                    .getText()
                                    .trim()
                    );

            if (cantidad <= 0) {

                fun_mensajeInformacion(
                        "La cantidad debe ser mayor a cero."
                );

                return;
            }

            if (precio < 0) {

                fun_mensajeInformacion(
                        "El precio no puede ser negativo."
                );

                return;
            }

            if (descuento < 0) {

                fun_mensajeInformacion(
                        "El descuento no puede ser negativo."
                );

                return;
            }

            double total
                    = cantidad * precio
                    - descuento;

            if (total < 0) {

                total = 0;
            }

            DetalleProforma detalle
                    = new DetalleProforma();

            detalle.setDpf_id(0);

            detalle.setDpf_descripcion(
                    descripcion
            );

            detalle.setDpf_cantidad(
                    cantidad
            );

            detalle.setDpf_precio_unitario(
                    precio
            );

            detalle.setDpf_descuento(
                    descuento
            );

            detalle.setDpf_total(
                    total
            );

            detalles.add(
                    detalle
            );

            txt_descripcion.clear();

            txt_cantidad.setText("1");

            txt_precio.setText("0");

            txt_descuento.setText("0");

            calcularTotales();

        } catch (NumberFormatException e) {

            fun_mensajeInformacion(
                    "Cantidad, precio y descuento deben ser numéricos."
            );
        }
    }

    @FXML
    private void acc_quitar() {

        DetalleProforma seleccionado
                = tb_detalles
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            fun_mensajeInformacion(
                    "Seleccione un detalle."
            );

            return;
        }

        detalles.remove(
                seleccionado
        );

        if (seleccionado.getDpf_id() > 0) {

            try {

                madDetalle.eliminarDetalle(
                        seleccionado.getDpf_id()
                );

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        calcularTotales();
    }

    @FXML
    private void acc_grabar() {

        try {

            if (txt_cliente.getText() == null
                    || txt_cliente.getText()
                            .trim()
                            .isEmpty()) {

                fun_mensajeInformacion(
                        "Debe ingresar el cliente."
                );

                return;
            }

            if (dtp_fecha.getValue() == null) {

                fun_mensajeInformacion(
                        "Debe ingresar la fecha."
                );

                return;
            }

            if (dtp_validez.getValue() == null) {

                fun_mensajeInformacion(
                        "Debe ingresar la fecha de validez."
                );

                return;
            }

            if (dtp_validez.getValue()
                    .isBefore(
                            dtp_fecha.getValue()
                    )) {

                fun_mensajeInformacion(
                        "La fecha de validez no puede ser anterior a la fecha de la proforma."
                );

                return;
            }

            if (detalles.isEmpty()) {

                fun_mensajeInformacion(
                        "Debe agregar al menos un detalle."
                );

                return;
            }

            calcularTotales();

            fun_mensajeInformacion(
                    "La proforma fue validada correctamente.\n\n"
                    + "Total: $"
                    + lbl_total.getText()
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo procesar la proforma.\n\n"
                    + e.getMessage()
            );
        }
    }

    private double obtenerSubtotal() {

        double subtotal = 0;

        for (DetalleProforma d
                : detalles) {

            subtotal
                    += d.getDpf_total();
        }

        return subtotal;
    }

    private void calcularTotales() {

        double subtotal
                = obtenerSubtotal();

        double iva
                = subtotal * 0.15;

        double total
                = subtotal + iva;

        lbl_subtotal.setText(
                String.format(
                        "%.2f",
                        subtotal
                )
        );

        lbl_iva.setText(
                String.format(
                        "%.2f",
                        iva
                )
        );

        lbl_total.setText(
                String.format(
                        "%.2f",
                        total
                )
        );
    }

    private void limpiar() {

        bandera = 0;

        cliId = 0;

        camId = null;

        txt_codigo.clear();

        txt_cliente.clear();

        txt_campania.clear();

        dtp_fecha.setValue(
                LocalDate.now()
        );

        dtp_validez.setValue(
                null
        );

        cmb_estado.setValue(
                "PENDIENTE"
        );

        txt_observaciones.clear();

        txt_descripcion.clear();

        txt_cantidad.setText("1");

        txt_precio.setText("0");

        txt_descuento.setText("0");

        detalles.clear();

        calcularTotales();
    }

    @FXML
private void acc_cancelar(javafx.event.ActionEvent event) {

    try {

        Stage stage =
                (Stage) btn_cancelar
                        .getScene()
                        .getWindow();

        stage.close();

    } catch (Exception e) {

        e.printStackTrace();

        fun_mensajeError(
                "No se pudo cerrar Nueva Proforma.\n\n"
                + e.getMessage()
        );
    }
}

}
