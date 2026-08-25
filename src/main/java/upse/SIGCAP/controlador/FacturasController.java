// src/main/java/upse/SIGCAP/controlador/FacturasController.java

package upse.SIGCAP.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import upse.SIGCAP.mad.Mad_Factura;
import upse.SIGCAP.modelo.Factura;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class FacturasController implements Initializable {

    @FXML
    private TableView<Factura> tb_facturas;

    @FXML
    private TableColumn<Factura, Integer> col_id;

    @FXML
    private TableColumn<Factura, String> col_numero;

    @FXML
    private TableColumn<Factura, String> col_cliente;

    @FXML
    private TableColumn<Factura, String> col_proforma;

    @FXML
    private TableColumn<Factura, String> col_ot;

    @FXML
    private TableColumn<Factura, String> col_fecha;

    @FXML
    private TableColumn<Factura, Double> col_total;

    @FXML
    private TableColumn<Factura, String> col_estado;

    @FXML
    private TextField txt_buscar;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_cerrar;

    private final Mad_Factura madFactura =
            new Mad_Factura();

    private ObservableList<Factura> listaOriginal =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        col_id.setCellValueFactory(
                new PropertyValueFactory<>("fac_id")
        );

        col_numero.setCellValueFactory(
                new PropertyValueFactory<>("fac_numero")
        );

        col_cliente.setCellValueFactory(
                new PropertyValueFactory<>("cli_nombre")
        );

        col_proforma.setCellValueFactory(
                new PropertyValueFactory<>("pfr_codigo")
        );

        col_ot.setCellValueFactory(
                new PropertyValueFactory<>("ot_codigo")
        );

        col_fecha.setCellValueFactory(
                new PropertyValueFactory<>("fac_fecha")
        );

        col_total.setCellValueFactory(
                new PropertyValueFactory<>("fac_total")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("fac_estado")
        );

        cargarDatos();

        txt_buscar.textProperty().addListener(
                (obs, anterior, nuevo) ->
                        filtrar(nuevo)
        );
    }

    private void cargarDatos() {

        try {

            listaOriginal =
                    madFactura.getFacturas();

            if (listaOriginal == null) {

                listaOriginal =
                        FXCollections.observableArrayList();
            }

            tb_facturas.setItems(
                    listaOriginal
            );

        } catch (Exception e) {

            fun_mensajeError(
                    "No se pudieron cargar las Facturas.\n"
                    + e.getMessage()
            );
        }
    }

    private void filtrar(String texto) {

        if (
                texto == null
                || texto.trim().isEmpty()
        ) {

            tb_facturas.setItems(
                    listaOriginal
            );

            return;
        }

        String filtro =
                texto.toLowerCase().trim();

        ObservableList<Factura> filtrada =
                FXCollections.observableArrayList();

        for (Factura f : listaOriginal) {

            if (
                    contiene(f.getFac_numero(), filtro)
                    || contiene(f.getCli_nombre(), filtro)
                    || contiene(f.getPfr_codigo(), filtro)
                    || contiene(f.getOt_codigo(), filtro)
                    || contiene(f.getFac_estado(), filtro)
            ) {

                filtrada.add(f);
            }
        }

        tb_facturas.setItems(
                filtrada
        );
    }

    private boolean contiene(
            String valor,
            String filtro) {

        return valor != null
                && valor.toLowerCase().contains(filtro);
    }

    @FXML
    private void acc_nuevo() {

        abrirModal("");
    }

    @FXML
    private void acc_dobleClick(
            MouseEvent event) {

        if (
                event.getButton() == MouseButton.PRIMARY
                && event.getClickCount() == 2
        ) {

            Factura seleccionada =
                    tb_facturas
                            .getSelectionModel()
                            .getSelectedItem();

            if (seleccionada != null) {

                abrirModal(
                        String.valueOf(
                                seleccionada.getFac_id()
                        )
                );
            }
        }
    }

    private void abrirModal(String id) {

        try {

            System.out.println(
                    "Cargando pantalla: "
                    + "/upse/SIGCAP/vistas/Factura.fxml"
            );

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/upse/SIGCAP/vistas/Facturas.fxml"
                            )
                    );

            if (loader.getLocation() == null) {

                throw new IOException(
                        "No se encontró Facturas.fxml"
                );
            }

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "Facturación"
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir Facturación.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_cerrar() {

        Stage stage =
                (Stage) btn_cerrar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase