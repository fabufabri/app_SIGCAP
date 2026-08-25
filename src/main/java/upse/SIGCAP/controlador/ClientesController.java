// src/main/java/upse/SIGCAP/controlador/ClientesController.java
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
import upse.SIGCAP.mad.Mad_Cliente;
import upse.SIGCAP.modelo.Cliente;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class ClientesController implements Initializable {

    @FXML
    private TextField txt_buscar;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_cerrar;

    @FXML
    private TableView<Cliente> tb_clientes;

    @FXML
    private TableColumn<Cliente, Integer> col_id;

    @FXML
    private TableColumn<Cliente, String> col_nombre;

    @FXML
    private TableColumn<Cliente, String> col_ruc;

    @FXML
    private TableColumn<Cliente, String> col_contacto;

    @FXML
    private TableColumn<Cliente, String> col_telefono;

    @FXML
    private TableColumn<Cliente, String> col_correo;

    @FXML
    private TableColumn<Cliente, String> col_estado;

    @FXML
    private Label lbl_total;

    private final Mad_Cliente madCliente =
            new Mad_Cliente();

    private ObservableList<Cliente> listaClientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configurarTabla();
        cargarClientes();

        txt_buscar.textProperty().addListener(
                (obs, anterior, actual) ->
                        filtrar(actual)
        );
    }

    private void configurarTabla() {

        col_id.setCellValueFactory(
                new PropertyValueFactory<>("cli_id"));

        col_nombre.setCellValueFactory(
                new PropertyValueFactory<>("cli_nombre"));

        col_ruc.setCellValueFactory(
                new PropertyValueFactory<>("cli_ruc"));

        col_contacto.setCellValueFactory(
                new PropertyValueFactory<>("cli_contacto"));

        col_telefono.setCellValueFactory(
                new PropertyValueFactory<>("cli_telefono"));

        col_correo.setCellValueFactory(
                new PropertyValueFactory<>("cli_correo"));

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("cli_estado"));

        tb_clientes.setOnMouseClicked(
                this::eventoTabla
        );
    }

    private void cargarClientes() {

        try {

            listaClientes =
                    madCliente.getClientes();

            if (listaClientes == null) {

                listaClientes =
                        FXCollections.observableArrayList();
            }

            tb_clientes.setItems(
                    FXCollections.observableArrayList(
                            listaClientes
                    )
            );

            actualizarTotal(
                    tb_clientes.getItems().size()
            );

        } catch (Exception e) {

            fun_mensajeError(
                    "No se pudieron cargar los clientes.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void filtrar(String texto) {

        if (listaClientes == null) {
            return;
        }

        String buscar =
                texto == null
                        ? ""
                        : texto.trim().toLowerCase();

        ObservableList<Cliente> filtrados =
                FXCollections.observableArrayList();

        for (Cliente cliente : listaClientes) {

            String nombre =
                    cliente.getCli_nombre() == null
                            ? ""
                            : cliente.getCli_nombre()
                                    .toLowerCase();

            String ruc =
                    cliente.getCli_ruc() == null
                            ? ""
                            : cliente.getCli_ruc()
                                    .toLowerCase();

            String contacto =
                    cliente.getCli_contacto() == null
                            ? ""
                            : cliente.getCli_contacto()
                                    .toLowerCase();

            if (buscar.isEmpty()
                    || nombre.contains(buscar)
                    || ruc.contains(buscar)
                    || contacto.contains(buscar)) {

                filtrados.add(cliente);
            }
        }

        tb_clientes.setItems(filtrados);

        actualizarTotal(
                filtrados.size()
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

            Cliente cliente =
                    tb_clientes
                            .getSelectionModel()
                            .getSelectedItem();

            if (cliente != null) {

                abrirModal(
                        String.valueOf(
                                cliente.getCli_id()
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

        cargarClientes();
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
                                    "/upse/SIGCAP/vistas/Cliente.fxml"
                            )
                    );

            Parent root = loader.load();

            ClienteController controller =
                    loader.getController();

            controller.recuperarCliente(id);

            Stage stage = new Stage();

            stage.setTitle(
                    id == null || id.isEmpty()
                            ? "Nuevo Cliente"
                            : "Editar Cliente"
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

            cargarClientes();

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