// src/main/java/upse/SIGCAP/controlador/CiudadesController.java
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import upse.SIGCAP.mad.Mad_Ciudad;
import upse.SIGCAP.modelo.Ciudad;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class CiudadesController implements Initializable {

    @FXML
    private TextField txt_buscar;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_cerrar;

    @FXML
    private TableView<Ciudad> tb_ciudades;

    @FXML
    private TableColumn<Ciudad, Integer> col_id;

    @FXML
    private TableColumn<Ciudad, String> col_nombre;

    @FXML
    private TableColumn<Ciudad, String> col_estado;

    @FXML
    private Label lbl_total;

    private final Mad_Ciudad madCiudad =
            new Mad_Ciudad();

    private ObservableList<Ciudad> listaCiudades;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarTabla();
        cargarCiudades();

        txt_buscar.textProperty().addListener(
                (obs, anterior, actual) ->
                        filtrar(actual)
        );
    }

    private void configurarTabla() {

        col_id.setCellValueFactory(
                new PropertyValueFactory<>("ciu_id"));

        col_nombre.setCellValueFactory(
                new PropertyValueFactory<>("ciu_nombre"));

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("ciu_estado"));

        tb_ciudades.setOnMouseClicked(
                this::eventoTabla
        );
    }

    private void cargarCiudades() {

        try {

            listaCiudades =
                    madCiudad.getCiudades();

            if (listaCiudades == null) {

                listaCiudades =
                        FXCollections.observableArrayList();
            }

            tb_ciudades.setItems(
                    FXCollections.observableArrayList(
                            listaCiudades
                    )
            );

            actualizarTotal(
                    tb_ciudades.getItems().size()
            );

        } catch (Exception e) {

            fun_mensajeError(
                    "No se pudieron cargar las ciudades.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void filtrar(String texto) {

        if (listaCiudades == null) {
            return;
        }

        String buscar =
                texto == null
                        ? ""
                        : texto.trim().toLowerCase();

        ObservableList<Ciudad> filtradas =
                FXCollections.observableArrayList();

        for (Ciudad ciudad : listaCiudades) {

            String nombre =
                    ciudad.getCiu_nombre() == null
                            ? ""
                            : ciudad.getCiu_nombre()
                                    .toLowerCase();

            if (buscar.isEmpty()
                    || nombre.contains(buscar)) {

                filtradas.add(ciudad);
            }
        }

        tb_ciudades.setItems(filtradas);

        actualizarTotal(
                filtradas.size()
        );
    }

    private void actualizarTotal(int total) {

        lbl_total.setText(
                "Registros: " + total
        );
    }

    private void eventoTabla(MouseEvent evento) {

        if (evento.getButton() == MouseButton.PRIMARY
                && evento.getClickCount() == 2) {

            Ciudad ciudad =
                    tb_ciudades
                            .getSelectionModel()
                            .getSelectedItem();

            if (ciudad != null) {

                abrirModal(
                        String.valueOf(
                                ciudad.getCiu_id()
                        )
                );
            }
        }
    }

    @FXML
    private void acc_nuevo() {

        abrirModal("");
    }

    @FXML
    private void acc_actualizar() {

        txt_buscar.clear();

        cargarCiudades();
    }

    @FXML
    private void acc_cerrar() {

        navegar("Catalogos");
    }

    private void abrirModal(String id) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            App.class.getResource(
                                    "/upse/SIGCAP/vistas/Ciudad.fxml"
                            )
                    );

            Parent root = loader.load();

            CiudadController controller =
                    loader.getController();

            controller.recuperarCiudad(id);

            Stage stage = new Stage();

            stage.setTitle(
                    id == null || id.isEmpty()
                            ? "Nueva Ciudad"
                            : "Editar Ciudad"
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initOwner(
                    btn_nuevo
                            .getScene()
                            .getWindow()
            );

            stage.setResizable(false);

            stage.setScene(
                    new Scene(root)
            );

            stage.showAndWait();

            cargarCiudades();

        } catch (IOException e) {

            fun_mensajeError(
                    "No se pudo abrir el formulario.\n\n"
                    + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    private void navegar(String pantalla) {

        try {

            App.setRoot(pantalla);

        } catch (IOException e) {

            fun_mensajeError(
                    "No se pudo abrir "
                    + pantalla
            );

            e.printStackTrace();
        }
    }

}//fin clase