// src/main/java/upse/SIGCAP/controlador/MisOTController.java
package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import upse.SIGCAP.general.Mod_VariablesGlobales;
import upse.SIGCAP.mad.Mad_OrdenTrabajo;
import upse.SIGCAP.modelo.OrdenTrabajo;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class MisOTController implements Initializable {

    @FXML
    private TextField txt_buscar;

    @FXML
    private TableView<OrdenTrabajo> tb_ordenes;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_codigo;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_cliente;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_campania;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_ciudad;

    @FXML
    private TableColumn<OrdenTrabajo, Object> col_fecha;

    @FXML
    private TableColumn<OrdenTrabajo, Object> col_fecha_requerida;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_prioridad;

    @FXML
    private TableColumn<OrdenTrabajo, String> col_estado;

    @FXML
    private Label lbl_usuario;

    @FXML
    private Label lbl_total;

    @FXML
    private Label lbl_pendientes;

    @FXML
    private Label lbl_artes;

    @FXML
    private Label lbl_produccion;

    @FXML
    private Label lbl_completadas;

    private final Mad_OrdenTrabajo madOrdenTrabajo =
            new Mad_OrdenTrabajo();

    private final ObservableList<OrdenTrabajo> listaCompleta =
            FXCollections.observableArrayList();

    private final ObservableList<OrdenTrabajo> listaFiltrada =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            configurarTabla();
            cargarUsuario();
            cargarOrdenes();

            txt_buscar.textProperty().addListener(
                    (obs, anterior, actual) ->
                            aplicarFiltro()
            );

            tb_ordenes.setOnMouseClicked(
                    this::eventoDobleClick
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Error en MisOTController.initialize(): "
                    + e.getMessage(),
                    e
            );
        }
    }

    private void configurarTabla() {

        col_codigo.setCellValueFactory(
                new PropertyValueFactory<>("ot_codigo")
        );

        col_cliente.setCellValueFactory(
                new PropertyValueFactory<>("ot_cliente")
        );

        col_campania.setCellValueFactory(
                new PropertyValueFactory<>("ot_campania")
        );

        col_ciudad.setCellValueFactory(
                new PropertyValueFactory<>("ot_ciudad")
        );

        col_fecha.setCellValueFactory(
                new PropertyValueFactory<>("ot_fecha")
        );

        col_fecha_requerida.setCellValueFactory(
                new PropertyValueFactory<>("ot_fecha_requerida")
        );

        col_prioridad.setCellValueFactory(
                new PropertyValueFactory<>("ot_prioridad")
        );

        col_estado.setCellValueFactory(
                new PropertyValueFactory<>("ot_estado")
        );
    }

    private void cargarUsuario() {

        String usuario =
                Mod_VariablesGlobales.g_nombreUsuario;

        if (usuario == null ||
                usuario.trim().isEmpty()) {

            usuario = "Usuario actual";
        }

        lbl_usuario.setText(usuario);
    }

    private void cargarOrdenes() {

        try {

            ObservableList<OrdenTrabajo> datos =
                    madOrdenTrabajo.getOrdenTrabajos();

            listaCompleta.clear();

            if (datos != null) {

                String usuario =
                        normalizar(
                                Mod_VariablesGlobales
                                        .g_nombreUsuario
                        );

                /*
                 * Si todavía no existe responsable
                 * asignado en las OT, mostramos las OT
                 * para que el módulo sea funcional.
                 */
                if (usuario.isEmpty()) {

                    listaCompleta.addAll(datos);

                } else {

                    for (OrdenTrabajo ot : datos) {

                        if (ot == null) {
                            continue;
                        }

                        String responsable =
                                normalizar(
                                        ot.getOt_responsable()
                                );

                        if (responsable.isEmpty()
                                || responsable.contains(usuario)
                                || usuario.contains(responsable)) {

                            listaCompleta.add(ot);
                        }
                    }
                }
            }

            aplicarFiltro();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron cargar sus Órdenes de Trabajo.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void aplicarFiltro() {

        listaFiltrada.clear();

        String texto =
                normalizar(
                        txt_buscar.getText()
                );

        for (OrdenTrabajo ot :
                listaCompleta) {

            if (ot == null) {
                continue;
            }

            if (texto.isEmpty()
                    || contiene(
                            ot.getOt_codigo(),
                            texto
                    )
                    || contiene(
                            ot.getOt_cliente(),
                            texto
                    )
                    || contiene(
                            ot.getOt_campania(),
                            texto
                    )
                    || contiene(
                            ot.getOt_ciudad(),
                            texto
                    )
                    || contiene(
                            ot.getOt_prioridad(),
                            texto
                    )
                    || contiene(
                            ot.getOt_estado(),
                            texto
                    )) {

                listaFiltrada.add(ot);
            }
        }

        tb_ordenes.setItems(
                listaFiltrada
        );

        calcularResumen();
    }

    private void calcularResumen() {

        int total = listaFiltrada.size();
        int pendientes = 0;
        int artes = 0;
        int produccion = 0;
        int completadas = 0;

        for (OrdenTrabajo ot :
                listaFiltrada) {

            String estado =
                    normalizar(
                            ot.getOt_estado()
                    );

            if (estado.contains("PENDIENTE")) {

                pendientes++;

            } else if (estado.contains("ARTES")) {

                artes++;

            } else if (estado.contains("PRODUCCION")
                    || estado.contains("PRODUCCIÓN")) {

                produccion++;

            } else if (estado.contains("COMPLETADO")) {

                completadas++;
            }
        }

        lbl_total.setText(
                String.valueOf(total)
        );

        lbl_pendientes.setText(
                String.valueOf(pendientes)
        );

        lbl_artes.setText(
                String.valueOf(artes)
        );

        lbl_produccion.setText(
                String.valueOf(produccion)
        );

        lbl_completadas.setText(
                String.valueOf(completadas)
        );
    }

    private boolean contiene(
            String valor,
            String texto) {

        if (valor == null) {
            return false;
        }

        return normalizar(valor)
                .contains(texto);
    }

    private String normalizar(
            String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .trim()
                .toLowerCase();
    }

    @FXML
    private void acc_actualizar() {

        txt_buscar.clear();

        cargarUsuario();
        cargarOrdenes();
    }

    @FXML
    private void acc_limpiar() {

        txt_buscar.clear();

        aplicarFiltro();
    }

    @FXML
    private void acc_volver() {

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

    private void eventoDobleClick(
            MouseEvent evento) {

        if (evento.getButton()
                == MouseButton.PRIMARY
                && evento.getClickCount() == 2) {

            OrdenTrabajo ot =
                    tb_ordenes
                            .getSelectionModel()
                            .getSelectedItem();

            if (ot != null) {

                Mod_VariablesGlobales
                        .g_otSeleccionada =
                        ot.getOt_id();

                try {

                    App.setRoot("Principal");

                } catch (Exception e) {

                    e.printStackTrace();

                    fun_mensajeError(
                            "No se pudo abrir la Orden de Trabajo.\n\n"
                            + e.getMessage()
                    );
                }
            }
        }
    }

}//fin clase