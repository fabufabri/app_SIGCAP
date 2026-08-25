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

import upse.SIGCAP.mad.Mad_Instalacion;
import upse.SIGCAP.modelo.Instalacion;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class InstalacionesController implements Initializable {

    @FXML
    private TableView<Instalacion> tb_instalaciones;

    @FXML
    private TableColumn<Instalacion, Integer> col_id;

    @FXML
    private TableColumn<Instalacion, Integer> col_item;

    @FXML
    private TableColumn<Instalacion, Integer> col_tercero;

    @FXML
    private TableColumn<Instalacion, Integer> col_tipo;

    @FXML
    private TableColumn<Instalacion, String> col_fecha_programada;

    @FXML
    private TableColumn<Instalacion, String> col_fecha_real;

    @FXML
    private TableColumn<Instalacion, String> col_estado;

    @FXML
    private TextField txt_buscar;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_cerrar;

    private final Mad_Instalacion madInstalacion =
            new Mad_Instalacion();

    private ObservableList<Instalacion> listaOriginal =
            FXCollections.observableArrayList();

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        col_id.setCellValueFactory(
                new PropertyValueFactory<>("ins_id")
        );

        col_item.setCellValueFactory(
                new PropertyValueFactory<>("itm_id")
        );

        col_tercero.setCellValueFactory(
                new PropertyValueFactory<>("ter_id")
        );

        col_tipo.setCellValueFactory(
                new PropertyValueFactory<>("tin_id")
        );

        col_fecha_programada.setCellValueFactory(
                new PropertyValueFactory<>("ins_fecha_programada")
        );

        col_fecha_real.setCellValueFactory(
                new PropertyValueFactory<>("ins_fecha_real")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("ins_estado")
        );

        cargarDatos();

        if (txt_buscar != null) {

            txt_buscar.textProperty().addListener(
                    (obs, anterior, nuevo) ->
                            filtrar(nuevo)
            );
        }

        if (tb_instalaciones != null) {

            tb_instalaciones.setOnMouseClicked(
                    this::acc_dobleClick
            );
        }
    }

    private void cargarDatos() {

        try {

            listaOriginal =
                    madInstalacion.getInstalaciones();

            if (listaOriginal == null) {

                listaOriginal =
                        FXCollections.observableArrayList();
            }

            tb_instalaciones.setItems(
                    listaOriginal
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron cargar las Instalaciones.\n\n"
                    + obtenerCausa(e)
            );
        }
    }

    private void filtrar(
            String texto) {

        if (texto == null
                || texto.trim().isEmpty()) {

            tb_instalaciones.setItems(
                    listaOriginal
            );

            return;
        }

        String filtro =
                texto.toLowerCase().trim();

        ObservableList<Instalacion> filtrada =
                FXCollections.observableArrayList();

        for (Instalacion obj :
                listaOriginal) {

            if (
                    String.valueOf(
                            obj.getIns_id()
                    ).contains(filtro)

                    || String.valueOf(
                            obj.getItm_id()
                    ).contains(filtro)

                    || String.valueOf(
                            obj.getTer_id()
                    ).contains(filtro)

                    || String.valueOf(
                            obj.getTin_id()
                    ).contains(filtro)

                    || contiene(
                            obj.getIns_estado(),
                            filtro
                    )
            ) {

                filtrada.add(obj);
            }
        }

        tb_instalaciones.setItems(
                filtrada
        );
    }

    private boolean contiene(
            String valor,
            String filtro) {

        return valor != null
                && valor.toLowerCase()
                        .contains(filtro);
    }

    @FXML
    private void acc_nuevo() {

        abrirModal("");
    }

    @FXML
    private void acc_editar() {

        Instalacion seleccionada =
                tb_instalaciones
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionada == null) {

            fun_mensajeError(
                    "Seleccione una instalación para editar."
            );

            return;
        }

        abrirModal(
                String.valueOf(
                        seleccionada.getIns_id()
                )
        );
    }

    @FXML
    private void acc_dobleClick(
            MouseEvent event) {

        if (
                event.getButton()
                        == MouseButton.PRIMARY
                && event.getClickCount()
                        == 2
        ) {

            Instalacion seleccionada =
                    tb_instalaciones
                            .getSelectionModel()
                            .getSelectedItem();

            if (seleccionada != null) {

                abrirModal(
                        String.valueOf(
                                seleccionada.getIns_id()
                        )
                );
            }
        }
    }

    private void abrirModal(
            String id) {

        try {

            String ruta =
                    "/upse/SIGCAP/vistas/Instalacion.fxml";

            URL url =
                    getClass().getResource(ruta);

            if (url == null) {

                throw new IOException(
                        "No se encontró el archivo "
                        + ruta
                );
            }

            FXMLLoader loader =
                    new FXMLLoader(url);

            Parent root =
                    loader.load();

            InstalacionController controller =
                    loader.getController();

            if (controller == null) {

                throw new IOException(
                        "No se pudo obtener InstalacionController."
                );
            }

            controller.recuperarInstalacion(id);

            Stage stage =
                    new Stage();

            stage.setTitle(
                    id == null
                    || id.trim().isEmpty()
                    ? "Nueva Instalación"
                    : "Editar Instalación"
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initStyle(
                    StageStyle.UNDECORATED
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.setResizable(false);

            stage.showAndWait();

            cargarDatos();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir Instalación.\n\n"
                    + obtenerCausa(e)
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
                    + obtenerCausa(e)
            );
        }
    }

    private String obtenerCausa(
            Exception e) {

        Throwable causa = e;

        while (
                causa.getCause() != null
        ) {

            causa =
                    causa.getCause();
        }

        String mensaje =
                causa.getMessage();

        if (
                mensaje == null
                || mensaje.trim().isEmpty()
        ) {

            mensaje =
                    causa.getClass()
                            .getSimpleName();
        }

        return causa.getClass()
                .getSimpleName()
                + ":\n"
                + mensaje;
    }
}