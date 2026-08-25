// src/main/java/upse/SIGCAP/controlador/FacturaController.java

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

import upse.SIGCAP.mad.Mad_DetalleFactura;
import upse.SIGCAP.mad.Mad_Factura;
import upse.SIGCAP.modelo.DetalleFactura;
import upse.SIGCAP.modelo.Factura;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class FacturaController implements Initializable {

    @FXML
    private TextField txt_numero;

    @FXML
    private TextField txt_cliente;

    @FXML
    private TextField txt_proforma;

    @FXML
    private TextField txt_ot;

    @FXML
    private DatePicker dtp_fecha;

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
    private TableView<DetalleFactura> tb_detalles;

    @FXML
    private TableColumn<DetalleFactura, String> col_descripcion;

    @FXML
    private TableColumn<DetalleFactura, Integer> col_cantidad;

    @FXML
    private TableColumn<DetalleFactura, Double> col_precio;

    @FXML
    private TableColumn<DetalleFactura, Double> col_descuento;

    @FXML
    private TableColumn<DetalleFactura, Double> col_total;

    @FXML
    private Label lbl_subtotal;

    @FXML
    private Label lbl_iva;

    @FXML
    private Label lbl_total;

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_quitar;

    @FXML
    private Button btn_grabar;

    @FXML
    private Button btn_cancelar;

    private int bandera = 0;

    private int cliId = 0;
    private Integer pfrId = null;
    private Integer otId = null;

    private final Mad_Factura madFactura =
            new Mad_Factura();

    private final Mad_DetalleFactura madDetalle =
            new Mad_DetalleFactura();

    private final ObservableList<DetalleFactura> detalles =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cmb_estado.setItems(
                FXCollections.observableArrayList(
                        "PENDIENTE",
                        "EMITIDA",
                        "PAGADA",
                        "ANULADA"
                )
        );

        cmb_estado.setValue("PENDIENTE");

        dtp_fecha.setValue(
                LocalDate.now()
        );

        col_descripcion.setCellValueFactory(
                new PropertyValueFactory<>("dfa_descripcion")
        );

        col_cantidad.setCellValueFactory(
                new PropertyValueFactory<>("dfa_cantidad")
        );

        col_precio.setCellValueFactory(
                new PropertyValueFactory<>("dfa_precio_unitario")
        );

        col_descuento.setCellValueFactory(
                new PropertyValueFactory<>("dfa_descuento")
        );

        col_total.setCellValueFactory(
                new PropertyValueFactory<>("dfa_total")
        );

        tb_detalles.setItems(detalles);
    }

    public void recuperarFactura(String id) {

        if (
                id == null
                || id.trim().isEmpty()
        ) {

            limpiar();

            return;
        }

        try {

            Factura obj =
                    madFactura.buscaFacturaxId(
                            Integer.parseInt(id)
                    );

            if (obj == null) {
                limpiar();
                return;
            }

            bandera =
                    obj.getFac_id();

            txt_numero.setText(
                    obj.getFac_numero()
            );

            txt_cliente.setText(
                    obj.getCli_nombre()
            );

            txt_proforma.setText(
                    obj.getPfr_codigo()
            );

            txt_ot.setText(
                    obj.getOt_codigo()
            );

            cliId =
                    obj.getCli_id();

            pfrId =
                    obj.getPfr_id();

            otId =
                    obj.getOt_id();

            dtp_fecha.setValue(
                    obj.getFac_fecha()
            );

            cmb_estado.setValue(
                    obj.getFac_estado()
            );

            txt_observaciones.setText(
                    obj.getFac_observaciones()
            );

            detalles.clear();

            ObservableList<DetalleFactura> lista =
                    madDetalle.getDetalles(
                            obj.getFac_id()
                    );

            if (lista != null) {
                detalles.addAll(lista);
            }

            calcularTotales();

        } catch (Exception e) {

            fun_mensajeError(
                    "Error recuperando Factura.\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_agregar() {

        try {

            String descripcion =
                    txt_descripcion.getText();

            if (
                    descripcion == null
                    || descripcion.trim().isEmpty()
            ) {

                fun_mensajeInformacion(
                        "Ingrese la descripción."
                );

                return;
            }

            int cantidad =
                    Integer.parseInt(
                            txt_cantidad.getText()
                    );

            double precio =
                    Double.parseDouble(
                            txt_precio.getText()
                    );

            double descuento =
                    Double.parseDouble(
                            txt_descuento.getText()
                    );

            if (cantidad <= 0) {

                fun_mensajeInformacion(
                        "La cantidad debe ser mayor a cero."
                );

                return;
            }

            double total =
                    cantidad * precio - descuento;

            if (total < 0) {
                total = 0;
            }

            DetalleFactura detalle =
                    new DetalleFactura();

            detalle.setDfa_id(0);
            detalle.setDfa_descripcion(
                    descripcion.trim()
            );
            detalle.setDfa_cantidad(cantidad);
            detalle.setDfa_precio_unitario(precio);
            detalle.setDfa_descuento(descuento);
            detalle.setDfa_total(total);

            detalles.add(detalle);

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

        DetalleFactura seleccionado =
                tb_detalles
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            fun_mensajeInformacion(
                    "Seleccione un detalle."
            );

            return;
        }

        detalles.remove(seleccionado);

        if (seleccionado.getDfa_id() > 0) {

            madDetalle.eliminarDetalle(
                    seleccionado.getDfa_id()
            );
        }

        calcularTotales();
    }

    @FXML
    private void acc_grabar() {

        if (
                txt_numero.getText() == null
                || txt_numero.getText().trim().isEmpty()
        ) {

            fun_mensajeInformacion(
                    "Ingrese el número de factura."
            );

            return;
        }

        if (cliId <= 0) {

            fun_mensajeInformacion(
                    "Debe seleccionar un cliente."
            );

            return;
        }

        if (dtp_fecha.getValue() == null) {

            fun_mensajeInformacion(
                    "Ingrese la fecha."
            );

            return;
        }

        double subtotal =
                obtenerSubtotal();

        double iva =
                subtotal * 0.15;

        double total =
                subtotal + iva;

        Factura obj =
                new Factura();

        obj.setFac_id(bandera);
        obj.setCli_id(cliId);
        obj.setPfr_id(pfrId);
        obj.setOt_id(otId);
        obj.setFac_numero(
                txt_numero.getText().trim()
        );
        obj.setFac_fecha(
                dtp_fecha.getValue()
        );
        obj.setFac_subtotal(subtotal);
        obj.setFac_iva(iva);
        obj.setFac_total(total);
        obj.setFac_estado(
                cmb_estado.getValue()
        );
        obj.setFac_observaciones(
                txt_observaciones.getText()
        );

        if (!madFactura.mantFactura(obj)) {

            fun_mensajeError(
                    "No se pudo guardar la Factura."
            );

            return;
        }

        fun_mensajeInformacion(
                "Factura guardada correctamente."
        );

        cerrar();
    }

    private double obtenerSubtotal() {

        double subtotal = 0;

        for (DetalleFactura d : detalles) {
            subtotal += d.getDfa_total();
        }

        return subtotal;
    }

    private void calcularTotales() {

        double subtotal =
                obtenerSubtotal();

        double iva =
                subtotal * 0.15;

        double total =
                subtotal + iva;

        lbl_subtotal.setText(
                String.format("%.2f", subtotal)
        );

        lbl_iva.setText(
                String.format("%.2f", iva)
        );

        lbl_total.setText(
                String.format("%.2f", total)
        );
    }

    private void limpiar() {

        bandera = 0;
        cliId = 0;
        pfrId = null;
        otId = null;

        txt_numero.clear();
        txt_cliente.clear();
        txt_proforma.clear();
        txt_ot.clear();

        dtp_fecha.setValue(
                LocalDate.now()
        );

        cmb_estado.setValue(
                "PENDIENTE"
        );

        txt_observaciones.clear();
        detalles.clear();

        txt_descripcion.clear();
        txt_cantidad.setText("1");
        txt_precio.setText("0");
        txt_descuento.setText("0");

        calcularTotales();
    }

    @FXML
    private void acc_cancelar() {

        cerrar();
    }

    private void cerrar() {

        Stage stage =
                (Stage) btn_cancelar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase