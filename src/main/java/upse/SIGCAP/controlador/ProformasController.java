// src/main/java/upse/SIGCAP/controlador/ProformasController.java

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

import upse.SIGCAP.mad.Mad_Proforma;
import upse.SIGCAP.modelo.Proforma;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class ProformasController implements Initializable {

    @FXML
    private TableView<Proforma> tb_proformas;

    @FXML
    private TableColumn<Proforma, Integer> col_id;

    @FXML
    private TableColumn<Proforma, String> col_codigo;

    @FXML
    private TableColumn<Proforma, String> col_cliente;

    @FXML
    private TableColumn<Proforma, String> col_campania;

    @FXML
    private TableColumn<Proforma, String> col_fecha;

    @FXML
    private TableColumn<Proforma, Double> col_total;

    @FXML
    private TableColumn<Proforma, String> col_estado;

    @FXML
    private TextField txt_buscar;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_cerrar;

    private final Mad_Proforma madProforma =
            new Mad_Proforma();

    private ObservableList<Proforma> listaOriginal =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        col_id.setCellValueFactory(
                new PropertyValueFactory<>("pfr_id")
        );

        col_codigo.setCellValueFactory(
                new PropertyValueFactory<>("pfr_codigo")
        );

        col_cliente.setCellValueFactory(
                new PropertyValueFactory<>("cli_nombre")
        );

        col_campania.setCellValueFactory(
                new PropertyValueFactory<>("cam_nombre")
        );

        col_fecha.setCellValueFactory(
                new PropertyValueFactory<>("pfr_fecha")
        );

        col_total.setCellValueFactory(
                new PropertyValueFactory<>("pfr_total")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("pfr_estado")
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
                    madProforma.getProformas();

            if (listaOriginal == null) {

                listaOriginal =
                        FXCollections.observableArrayList();
            }

            tb_proformas.setItems(
                    listaOriginal
            );

        } catch (Exception e) {

            fun_mensajeError(
                    "No se pudieron cargar las Proformas.\n"
                    + e.getMessage()
            );
        }
    }

    private void filtrar(String texto) {

        if (
                texto == null
                || texto.trim().isEmpty()
        ) {

            tb_proformas.setItems(
                    listaOriginal
            );

            return;
        }

        String filtro =
                texto.toLowerCase().trim();

        ObservableList<Proforma> filtrada =
                FXCollections.observableArrayList();

        for (Proforma p : listaOriginal) {

            if (
                    contiene(p.getPfr_codigo(), filtro)
                    || contiene(p.getCli_nombre(), filtro)
                    || contiene(p.getCam_nombre(), filtro)
                    || contiene(p.getPfr_estado(), filtro)
            ) {

                filtrada.add(p);
            }
        }

        tb_proformas.setItems(
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

            Proforma seleccionada =
                    tb_proformas
                            .getSelectionModel()
                            .getSelectedItem();

            if (seleccionada != null) {

                abrirModal(
                        String.valueOf(
                                seleccionada.getPfr_id()
                        )
                );
            }
        }
    }

    private void abrirModal(String id) {

        try {

            System.out.println(
                    "Cargando pantalla: "
                    + "/upse/SIGCAP/vistas/Proforma.fxml"
            );

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/upse/SIGCAP/vistas/Proforma.fxml"
                            )
                    );

            if (loader.getLocation() == null) {

                throw new IOException(
                        "No se encontró Proforma.fxml"
                );
            }

            Parent root =
                    loader.load();

            ProformaController controller =
                    loader.getController();

            controller.recuperarProforma(id);

            Stage stage =
                    new Stage();

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initStyle(
                    StageStyle.UNDECORATED
            );

            stage.setTitle(
                    "Proforma"
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.showAndWait();

            cargarDatos();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir Proforma.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
private void acc_cerrar() {

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