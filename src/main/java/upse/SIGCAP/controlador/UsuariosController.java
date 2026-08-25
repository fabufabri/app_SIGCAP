// src/main/java/upse/SIGCAP/controlador/UsuariosController.java

package upse.SIGCAP.controlador;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

import upse.SIGCAP.mad.Mad_seguridad;
import upse.SIGCAP.modelo.Usuario;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class UsuariosController implements Initializable {

    @FXML
    private TextField txt_buscar;

    @FXML
    private TableView<Usuario> tb_usuarios;

    @FXML
    private TableColumn<Usuario, Integer> col_id;

    @FXML
    private TableColumn<Usuario, String> col_usuario;

    @FXML
    private TableColumn<Usuario, String> col_nombres;

    @FXML
    private TableColumn<Usuario, String> col_apellidos;

    @FXML
    private TableColumn<Usuario, String> col_estado;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_cerrar;

    private final Mad_seguridad madSeguridad =
            new Mad_seguridad();

    private ObservableList<Usuario> listaUsuarios =
            FXCollections.observableArrayList();

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        configurarTabla();

        cargarUsuarios();

        txt_buscar.textProperty()
                .addListener(
                        (obs, anterior, actual) ->
                                filtrarUsuarios(actual)
                );

        tb_usuarios.setOnMouseClicked(
                event -> {

                    if (event.getClickCount() == 2
                            && event.getButton()
                            == javafx.scene.input.MouseButton.PRIMARY) {

                        acc_editar();
                    }
                }
        );
    }

    private void configurarTabla() {

        col_id.setCellValueFactory(
                new PropertyValueFactory<>("usu_id")
        );

        col_usuario.setCellValueFactory(
                new PropertyValueFactory<>("usu_usuario")
        );

        col_nombres.setCellValueFactory(
                new PropertyValueFactory<>("usu_nombres")
        );

        col_apellidos.setCellValueFactory(
                new PropertyValueFactory<>("usu_apellidos")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("usu_estado")
        );
    }

    private void cargarUsuarios() {

        try {

            /*
             * Si posteriormente se implementa
             * getUsuarios() en Mad_seguridad,
             * se reemplaza únicamente esta llamada.
             */

            listaUsuarios =
                    FXCollections.observableArrayList();

            tb_usuarios.setItems(
                    listaUsuarios
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron cargar los usuarios.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void filtrarUsuarios(
            String texto) {

        if (listaUsuarios == null) {
            return;
        }

        String buscar =
                texto == null
                        ? ""
                        : texto
                                .trim()
                                .toLowerCase();

        if (buscar.isEmpty()) {

            tb_usuarios.setItems(
                    listaUsuarios
            );

            return;
        }

        ObservableList<Usuario> filtrados =
                FXCollections.observableArrayList();

        for (Usuario usuario :
                listaUsuarios) {

            boolean coincide =

                    contiene(
                            String.valueOf(
                                    usuario.getUsu_id()
                            ),
                            buscar
                    )

                    || contiene(
                            usuario.getUsu_usuario(),
                            buscar
                    )

                    || contiene(
                            usuario.getUsu_nombres(),
                            buscar
                    )

                    || contiene(
                            usuario.getUsu_apellidos(),
                            buscar
                    )

                    || contiene(
                            usuario.getUsu_estado(),
                            buscar
                    );

            if (coincide) {

                filtrados.add(usuario);
            }
        }

        tb_usuarios.setItems(
                filtrados
        );
    }

    private boolean contiene(
            String valor,
            String buscar) {

        return valor != null
                && valor
                        .toLowerCase()
                        .contains(buscar);
    }

    @FXML
    private void acc_nuevo() {

        abrirModalUsuario(0);
    }

    @FXML
    private void acc_editar() {

        Usuario seleccionado =
                tb_usuarios
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            fun_mensajeInformacion(
                    "Seleccione un usuario."
            );

            return;
        }

        abrirModalUsuario(
                seleccionado.getUsu_id()
        );
    }

    private void abrirModalUsuario(
            int id) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            App.class.getResource(
                                    "/upse/SIGCAP/vistas/"
                                    + "NuevoUsuario.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            NuevoUsuarioController controller =
                    loader.getController();

            controller.setUsuarioId(id);

            Stage stage =
                    new Stage();

            stage.setTitle(
                    id == 0
                            ? "Nuevo Usuario"
                            : "Editar Usuario"
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initOwner(
                    btn_cerrar
                            .getScene()
                            .getWindow()
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.setResizable(false);

            stage.showAndWait();

            cargarUsuarios();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir el formulario de Usuario.\n\n"
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