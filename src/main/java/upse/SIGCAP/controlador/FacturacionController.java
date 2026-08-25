package upse.SIGCAP.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
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

public class FacturacionController implements Initializable {

    @FXML
    private TextField txt_numFactura;

    @FXML
    private DatePicker dtp_fecha;

    @FXML
    private TextField txt_otCodigo;

    @FXML
    private Button btn_buscarOT;

    @FXML
    private TextField txt_cliente;

    @FXML
    private TextField txt_campania;

    @FXML
    private TextField txt_responsable;

    @FXML
    private TextArea txt_observacion;

    @FXML
    private TableView<DetalleFactura> tb_detalle;

    @FXML
    private TableColumn<DetalleFactura, String> col_codigo;

    @FXML
    private TableColumn<DetalleFactura, String> col_producto;

    @FXML
    private TableColumn<DetalleFactura, String> col_descripcion;

    @FXML
    private TableColumn<DetalleFactura, Integer> col_cantidad;

    @FXML
    private TableColumn<DetalleFactura, Double> col_precio;

    @FXML
    private TableColumn<DetalleFactura, Double> col_subtotal;

    @FXML
    private TableColumn<DetalleFactura, Double> col_total;

    @FXML
    private TableColumn<DetalleFactura, String> col_aplicaIva;

    @FXML
    private TextField txt_subtotal12;

    @FXML
    private TextField txt_subtotal0;

    @FXML
    private TextField txt_iva;

    @FXML
    private TextField txt_total;

    @FXML
    private CheckBox chk_validar;

    @FXML
    private Button btn_agregar;

    @FXML
    private Button btn_quitar;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_grabar;

    @FXML
    private Button btn_consultar;

    @FXML
    private Button btn_cerrar;

    @FXML
    private Button btn_sri;

    private final Mad_OrdenTrabajo madOrdenTrabajo;

    private final ObservableList<DetalleFactura> listaDetalle;

    private OrdenTrabajo ordenSeleccionada;

    public FacturacionController() {

        madOrdenTrabajo = new Mad_OrdenTrabajo();

        listaDetalle =
                FXCollections.observableArrayList();
    }

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        col_codigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        col_producto.setCellValueFactory(
                new PropertyValueFactory<>("producto")
        );

        col_descripcion.setCellValueFactory(
                new PropertyValueFactory<>("descripcion")
        );

        col_cantidad.setCellValueFactory(
                new PropertyValueFactory<>("cantidad")
        );

        col_precio.setCellValueFactory(
                new PropertyValueFactory<>("precio")
        );

        col_subtotal.setCellValueFactory(
                new PropertyValueFactory<>("subtotal")
        );

        col_total.setCellValueFactory(
                new PropertyValueFactory<>("total")
        );

        col_aplicaIva.setCellValueFactory(
                new PropertyValueFactory<>("aplicaIva")
        );

        tb_detalle.setItems(
                listaDetalle
        );

        dtp_fecha.setValue(
                LocalDate.now()
        );

        txt_numFactura.setText(
                "001-001-000000001"
        );

        limpiarTotales();
    }

    @FXML
    private void acc_buscarOrden(
            ActionEvent event) {

        try {

            String codigo =
                    txt_otCodigo
                            .getText()
                            .trim();

            if (codigo.isEmpty()) {

                fun_mensajeInformacion(
                        "Ingrese el código de la Orden de Trabajo."
                );

                return;
            }

            ObservableList<OrdenTrabajo> ordenes =
                    madOrdenTrabajo
                            .getOrdenesTrabajo();

            OrdenTrabajo encontrada = null;

            for (OrdenTrabajo ot : ordenes) {

                if (ot.getOt_codigo() != null
                        && ot.getOt_codigo()
                                .equalsIgnoreCase(codigo)) {

                    encontrada = ot;

                    break;
                }
            }

            if (encontrada == null) {

                fun_mensajeInformacion(
                        "No se encontró la Orden de Trabajo."
                );

                return;
            }

            cargarOrden(encontrada);

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo buscar la Orden de Trabajo.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void cargarOrden(
            OrdenTrabajo ot) {

        ordenSeleccionada = ot;

        txt_otCodigo.setText(
                ot.getOt_codigo()
        );

        txt_cliente.setText(
                ot.getOt_cliente() == null
                ? ""
                : ot.getOt_cliente()
        );

        txt_campania.setText(
                ot.getOt_campania() == null
                ? ""
                : ot.getOt_campania()
        );

        txt_responsable.setText(
                ot.getOt_responsable() == null
                ? ""
                : ot.getOt_responsable()
        );

        txt_observacion.setText(
                ot.getOt_observaciones() == null
                ? ""
                : ot.getOt_observaciones()
        );

        cargarDetalle(
                ot.getOt_id()
        );
    }

    private void cargarDetalle(
            int otId) {

        listaDetalle.clear();

        ObservableList<ItemOrdenTrabajo> items =
                madOrdenTrabajo.getItems(otId);

        if (items == null
                || items.isEmpty()) {

            calcularTotales();

            fun_mensajeInformacion(
                    "La Orden de Trabajo no tiene ítems."
            );

            return;
        }

        for (ItemOrdenTrabajo item : items) {

            DetalleFactura detalle =
                    new DetalleFactura();

            detalle.setCodigo(
                    String.valueOf(
                            item.getItm_id()
                    )
            );

            detalle.setProducto(
                    item.getItm_producto()
            );

            detalle.setDescripcion(
                    item.getItm_descripcion()
            );

            detalle.setCantidad(
                    item.getItm_cantidad()
            );

            detalle.setPrecio(0.00);

            detalle.setAplicaIva("S");

            listaDetalle.add(
                    detalle
            );
        }

        calcularTotales();

        tb_detalle.refresh();
    }

    @FXML
    private void acc_agregar(
            ActionEvent event) {

        DetalleFactura detalle =
                new DetalleFactura();

        detalle.setCantidad(1);

        detalle.setPrecio(0.00);

        detalle.setAplicaIva("S");

        listaDetalle.add(
                detalle
        );

        tb_detalle
                .getSelectionModel()
                .select(detalle);

        calcularTotales();
    }

    @FXML
    private void acc_quitar(
            ActionEvent event) {

        DetalleFactura detalle =
                tb_detalle
                        .getSelectionModel()
                        .getSelectedItem();

        if (detalle == null) {

            fun_mensajeInformacion(
                    "Seleccione una línea."
            );

            return;
        }

        listaDetalle.remove(
                detalle
        );

        calcularTotales();
    }

    @FXML
    private void acc_nuevo(
            ActionEvent event) {

        ordenSeleccionada = null;

        txt_otCodigo.clear();
        txt_cliente.clear();
        txt_campania.clear();
        txt_responsable.clear();
        txt_observacion.clear();

        listaDetalle.clear();

        dtp_fecha.setValue(
                LocalDate.now()
        );

        txt_numFactura.setText(
                "001-001-000000001"
        );

        limpiarTotales();
    }

    @FXML
    private void acc_grabar(
            ActionEvent event) {

        if (ordenSeleccionada == null) {

            fun_mensajeInformacion(
                    "Debe seleccionar una Orden de Trabajo."
            );

            return;
        }

        if (listaDetalle.isEmpty()) {

            fun_mensajeInformacion(
                    "Debe ingresar al menos un detalle."
            );

            return;
        }

        calcularTotales();

        fun_mensajeInformacion(
                "Factura preparada correctamente."
        );
    }

    @FXML
    private void acc_consultar(
            ActionEvent event) {

        try {

            URL url =
                    getClass().getResource(
                            "/upse/SIGCAP/vistas/ConsultaFactura.fxml"
                    );

            if (url == null) {

                fun_mensajeError(
                        "No se encontró ConsultaFactura.fxml."
                );

                return;
            }

            FXMLLoader loader =
                    new FXMLLoader(url);

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "SIGCAP - Consulta de Facturas"
            );

            stage.initModality(
                    javafx.stage.Modality.APPLICATION_MODAL
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.setResizable(false);

            stage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir la consulta de facturas.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_enviarSRI(
            ActionEvent event) {

        fun_mensajeInformacion(
                "Seleccione una factura desde la consulta."
        );
    }

    @FXML
    private void acc_cerrar(
            ActionEvent event) {

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

    private void calcularTotales() {

        double subtotal12 = 0.00;
        double subtotal0 = 0.00;
        double iva = 0.00;
        double total = 0.00;

        for (DetalleFactura detalle :
                listaDetalle) {

            double subtotal =
                    detalle.getCantidad()
                    * detalle.getPrecio();

            detalle.setSubtotal(
                    subtotal
            );

            if ("S".equalsIgnoreCase(
                    detalle.getAplicaIva()
            )) {

                subtotal12 += subtotal;

                double valorIva =
                        subtotal * 0.15;

                iva += valorIva;

                detalle.setTotal(
                        subtotal + valorIva
                );

            } else {

                subtotal0 += subtotal;

                detalle.setTotal(
                        subtotal
                );
            }

            total +=
                    detalle.getTotal();
        }

        txt_subtotal12.setText(
                String.format(
                        "%.2f",
                        subtotal12
                )
        );

        txt_subtotal0.setText(
                String.format(
                        "%.2f",
                        subtotal0
                )
        );

        txt_iva.setText(
                String.format(
                        "%.2f",
                        iva
                )
        );

        txt_total.setText(
                String.format(
                        "%.2f",
                        total
                )
        );

        tb_detalle.refresh();
    }

    private void limpiarTotales() {

        txt_subtotal12.setText("0.00");
        txt_subtotal0.setText("0.00");
        txt_iva.setText("0.00");
        txt_total.setText("0.00");
    }

    public static class DetalleFactura {

        private String codigo;
        private String producto;
        private String descripcion;
        private int cantidad;
        private double precio;
        private double subtotal;
        private double total;
        private String aplicaIva;

        public DetalleFactura() {
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getProducto() {
            return producto;
        }

        public void setProducto(String producto) {
            this.producto = producto;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public String getAplicaIva() {
            return aplicaIva;
        }

        public void setAplicaIva(String aplicaIva) {
            this.aplicaIva = aplicaIva;
        }
    }
}