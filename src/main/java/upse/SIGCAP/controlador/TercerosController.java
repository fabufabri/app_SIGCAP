// src/main/java/upse/SIGCAP/controlador/TercerosController.java
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

import upse.SIGCAP.mad.Mad_Tercero;
import upse.SIGCAP.modelo.Tercero;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class TercerosController
        implements Initializable {

    @FXML
    private TextField txt_buscar;

    @FXML
    private TableView<Tercero> tb_terceros;

    @FXML
    private TableColumn<Tercero, Integer> col_id;

    @FXML
    private TableColumn<Tercero, String> col_nombre;

    @FXML
    private TableColumn<Tercero, String> col_telefono;

    @FXML
    private TableColumn<Tercero, String> col_correo;

    @FXML
    private TableColumn<Tercero, String> col_estado;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_cerrar;

    private final Mad_Tercero madTercero
            = new Mad_Tercero();

    private ObservableList<Tercero> lista;

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        configurarTabla();

        cargarTerceros();

        txt_buscar.textProperty()
                .addListener(
                        (obs, anterior, actual)
                        -> filtrar(actual)
                );

        tb_terceros.setOnMouseClicked(
                this::dobleClic
        );
    }

    private void configurarTabla() {

        col_id.setCellValueFactory(
                new PropertyValueFactory<>("ter_id")
        );

        col_nombre.setCellValueFactory(
                new PropertyValueFactory<>("ter_nombre")
        );

        col_telefono.setCellValueFactory(
                new PropertyValueFactory<>("ter_telefono")
        );

        col_correo.setCellValueFactory(
                new PropertyValueFactory<>("ter_correo")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("ter_estado")
        );
    }

    private void cargarTerceros() {

        try {

            lista
                    = madTercero.getTerceros();

            if (lista == null) {

                lista
                        = FXCollections
                                .observableArrayList();
            }

            tb_terceros.setItems(lista);

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron cargar los terceros.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void filtrar(
            String texto) {

        if (lista == null) {
            return;
        }

        String buscar
                = texto == null
                        ? ""
                        : texto
                                .trim()
                                .toLowerCase();

        if (buscar.isEmpty()) {

            tb_terceros.setItems(lista);

            return;
        }

        ObservableList<Tercero> filtrados
                = FXCollections
                        .observableArrayList();

        for (Tercero tercero
                : lista) {

            if (contiene(
                    tercero.getTer_nombre(),
                    buscar
            )
                    || contiene(
                            tercero.getTer_telefono(),
                            buscar
                    )
                    || contiene(
                            tercero.getTer_correo(),
                            buscar
                    )
                    || contiene(
                            tercero.getTer_estado(),
                            buscar
                    )) {

                filtrados.add(
                        tercero
                );
            }
        }

        tb_terceros.setItems(
                filtrados
        );
    }

    private boolean contiene(
            String valor,
            String texto) {

        return valor != null
                && valor
                        .toLowerCase()
                        .contains(texto);
    }

    private void dobleClic(
            MouseEvent event) {

        if (event.getButton()
                == MouseButton.PRIMARY
                && event.getClickCount() == 2) {

            Tercero tercero
                    = tb_terceros
                            .getSelectionModel()
                            .getSelectedItem();

            if (tercero != null) {

                abrirModal(
                        String.valueOf(
                                tercero.getTer_id()
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
    private void acc_editar() {

        Tercero tercero
                = tb_terceros
                        .getSelectionModel()
                        .getSelectedItem();

        if (tercero == null) {

            fun_mensajeInformacion(
                    "Seleccione un tercero para editar."
            );

            return;
        }

        abrirModal(
                String.valueOf(
                        tercero.getTer_id()
                )
        );
    }

    @FXML
    private void acc_actualizar() {

        cargarTerceros();
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

    private void abrirModal(
            String id) {

        try {

            FXMLLoader loader
                    = new FXMLLoader(
                            App.class.getResource(
                                    "/upse/SIGCAP/vistas/Tercero.fxml"
                            )
                    );

            Parent root
                    = loader.load();

            TerceroController controller
                    = loader.getController();

            controller.recuperarTercero(id);

            Stage stage
                    = new Stage();

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initOwner(
                    tb_terceros
                            .getScene()
                            .getWindow()
            );

            stage.initStyle(
                    StageStyle.UNDECORATED
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.setResizable(false);

            stage.showAndWait();

            cargarTerceros();

        } catch (IOException e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir el formulario de Tercero.\n\n"
                    + e.getMessage()
            );
        }
    }

}//fin clase
