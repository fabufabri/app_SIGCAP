package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import upse.SIGCAP.mad.Mad_Factura;
import upse.SIGCAP.modelo.Factura;

public class ConsultaFacturaController implements Initializable {

    @FXML
    private TextField txt_numFactura;

    @FXML
    private TableView<Factura> tb_facturas;

    @FXML
    private TableColumn<Factura, String> col_numero;

    @FXML
    private TableColumn<Factura, String> col_fecha;

    @FXML
    private TableColumn<Factura, String> col_cliente;

    @FXML
    private TableColumn<Factura, Double> col_total;

    @FXML
    private TableColumn<Factura, String> col_estado;

    @FXML
    private TableColumn<Factura, String> col_estadoSri;

    @FXML
    private Button btn_buscar;

    @FXML
    private Button btn_abrir;

    @FXML
    private Button btn_enviarSri;

    @FXML
    private Button btn_cerrar;

    private Mad_Factura madFactura;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        madFactura = new Mad_Factura();

        if (tb_facturas == null) {
            throw new RuntimeException(
                    "El TableView tb_facturas no está conectado en ConsultaFactura.fxml"
            );
        }

        if (col_numero == null) {
            throw new RuntimeException(
                    "La columna col_numero no está conectada en ConsultaFactura.fxml"
            );
        }

        if (col_fecha == null) {
            throw new RuntimeException(
                    "La columna col_fecha no está conectada en ConsultaFactura.fxml"
            );
        }

        if (col_cliente == null) {
            throw new RuntimeException(
                    "La columna col_cliente no está conectada en ConsultaFactura.fxml"
            );
        }

        if (col_total == null) {
            throw new RuntimeException(
                    "La columna col_total no está conectada en ConsultaFactura.fxml"
            );
        }

        if (col_estado == null) {
            throw new RuntimeException(
                    "La columna col_estado no está conectada en ConsultaFactura.fxml"
            );
        }

        if (col_estadoSri == null) {
            throw new RuntimeException(
                    "La columna col_estadoSri no está conectada en ConsultaFactura.fxml"
            );
        }

        col_numero.setCellValueFactory(
                new PropertyValueFactory<>("fac_numero")
        );

        col_fecha.setCellValueFactory(
                new PropertyValueFactory<>("fac_fecha")
        );

        col_cliente.setCellValueFactory(
                new PropertyValueFactory<>("cli_nombre")
        );

        col_total.setCellValueFactory(
                new PropertyValueFactory<>("fac_total")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("fac_estado")
        );

        col_estadoSri.setCellValueFactory(
                new PropertyValueFactory<>("fac_estadoSri")
        );

        cargarFacturas();
    }

    private void cargarFacturas() {

        try {

            ObservableList<Factura> lista =
                    madFactura.getFacturas();

            if (lista == null) {

                mostrarError(
                        "La consulta de facturas no devolvió datos."
                );

                return;
            }

            tb_facturas.setItems(lista);

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    "No se pudieron cargar las facturas.\n\n"
                    + obtenerCausa(e)
            );
        }
    }

    @FXML
    private void acc_buscar(ActionEvent event) {

        String texto =
                txt_numFactura.getText()
                        .trim()
                        .toLowerCase();

        if (texto.isEmpty()) {

            cargarFacturas();

            return;
        }

        try {

            ObservableList<Factura> lista =
                    madFactura.getFacturas();

            if (lista == null) {

                mostrarError(
                        "No existen facturas para consultar."
                );

                return;
            }

            javafx.collections.ObservableList<Factura>
                    resultado =
                    javafx.collections.FXCollections
                            .observableArrayList();

            for (Factura factura : lista) {

                if (factura.getFac_numero() != null
                        && factura.getFac_numero()
                                .toLowerCase()
                                .contains(texto)) {

                    resultado.add(factura);
                }
            }

            tb_facturas.setItems(resultado);

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    "No se pudo realizar la búsqueda.\n\n"
                    + obtenerCausa(e)
            );
        }
    }

    @FXML
    private void acc_abrir(ActionEvent event) {

        Factura factura =
                tb_facturas
                        .getSelectionModel()
                        .getSelectedItem();

        if (factura == null) {

            mostrarInformacion(
                    "Seleccione una factura."
            );

            return;
        }

        try {

            URL url =
                    getClass().getResource(
                            "/upse/SIGCAP/vistas/VerFactura.fxml"
                    );

            if (url == null) {

                mostrarError(
                        "No se encontró VerFactura.fxml."
                );

                return;
            }

            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(url);

            javafx.scene.Parent root =
                    loader.load();

            VerFacturaController controlador =
                    loader.getController();

            controlador.cargarFactura(
                    factura.getFac_id()
            );

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "SIGCAP - Ver Factura"
            );

            stage.initModality(
                    javafx.stage.Modality.APPLICATION_MODAL
            );

            stage.setScene(
                    new javafx.scene.Scene(root)
            );

            stage.showAndWait();

            cargarFacturas();

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    "No se pudo abrir la factura.\n\n"
                    + obtenerCausa(e)
            );
        }
    }

    @FXML
    private void acc_enviarSri(ActionEvent event) {

        Factura factura =
                tb_facturas
                        .getSelectionModel()
                        .getSelectedItem();

        if (factura == null) {

            mostrarInformacion(
                    "Seleccione una factura."
            );

            return;
        }

        mostrarInformacion(
                "El proceso de envío al SRI todavía "
                + "no está implementado."
        );
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {

        Stage stage =
                (Stage) btn_cerrar
                        .getScene()
                        .getWindow();

        stage.close();
    }

    private String obtenerCausa(Exception e) {

        Throwable causa = e;

        while (causa.getCause() != null) {
            causa = causa.getCause();
        }

        if (causa.getMessage() == null
                || causa.getMessage().trim().isEmpty()) {

            return causa.getClass()
                    .getSimpleName();
        }

        return causa.getClass()
                .getSimpleName()
                + ":\n"
                + causa.getMessage();
    }

    private void mostrarError(String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alerta.setTitle(
                "Mensaje del Sistema"
        );

        alerta.setHeaderText(null);

        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alerta.setTitle(
                "Mensaje del Sistema"
        );

        alerta.setHeaderText(null);

        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}