// src/main/java/upse/SIGCAP/controlador/ClienteController.java
package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import upse.SIGCAP.mad.Mad_Cliente;
import upse.SIGCAP.modelo.Cliente;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class ClienteController implements Initializable {

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_ruc;

    @FXML
    private TextField txt_contacto;

    @FXML
    private TextField txt_telefono;

    @FXML
    private TextField txt_correo;

    @FXML
    private ComboBox<String> cmb_estado;

    @FXML
    private Button btn_grabar;

    @FXML
    private Button btn_cancelar;

    private int bandera = 0;

    private final Mad_Cliente madCliente =
            new Mad_Cliente();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cmb_estado.getItems().clear();

        cmb_estado.getItems().add("Activo");
        cmb_estado.getItems().add("Inactivo");

        cmb_estado.setValue("Activo");
    }

    @FXML
    private void acc_grabar() {

        String nombre =
                txt_nombre.getText() == null
                        ? ""
                        : txt_nombre.getText().trim();

        String ruc =
                txt_ruc.getText() == null
                        ? ""
                        : txt_ruc.getText().trim();

        String contacto =
                txt_contacto.getText() == null
                        ? ""
                        : txt_contacto.getText().trim();

        String telefono =
                txt_telefono.getText() == null
                        ? ""
                        : txt_telefono.getText().trim();

        String correo =
                txt_correo.getText() == null
                        ? ""
                        : txt_correo.getText().trim();

        if (nombre.isEmpty()) {

            fun_mensajeInformacion(
                    "Ingrese el nombre del cliente."
            );

            txt_nombre.requestFocus();

            return;
        }

        if (!correo.isEmpty()
                && !correo.contains("@")) {

            fun_mensajeInformacion(
                    "Ingrese un correo electrónico válido."
            );

            txt_correo.requestFocus();

            return;
        }

        String estado =
                "Activo".equals(
                        cmb_estado.getValue()
                )
                        ? "A"
                        : "E";

        Cliente obj =
                new Cliente();

        obj.setCli_id(bandera);
        obj.setCli_nombre(nombre);
        obj.setCli_ruc(
                ruc.isEmpty() ? null : ruc
        );
        obj.setCli_contacto(
                contacto.isEmpty() ? null : contacto
        );
        obj.setCli_telefono(
                telefono.isEmpty() ? null : telefono
        );
        obj.setCli_correo(
                correo.isEmpty() ? null : correo
        );
        obj.setCli_estado(estado);

        try {

            boolean resultado =
                    madCliente.mantCliente(obj);

            if (resultado) {

                fun_mensajeInformacion(
                        bandera == 0
                                ? "Cliente registrado correctamente."
                                : "Cliente actualizado correctamente."
                );

                cerrar();

            } else {

                fun_mensajeError(
                        "No se pudo guardar el cliente."
                );
            }

        } catch (Exception e) {

            fun_mensajeError(
                    "Error al guardar el cliente.\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_cancelar() {

        cerrar();
    }

    public void recuperarCliente(String id) {

        limpiar();

        if (id == null
                || id.trim().isEmpty()) {

            bandera = 0;

            txt_nombre.requestFocus();

            return;
        }

        try {

            int clienteId =
                    Integer.parseInt(id);

            Cliente obj =
                    madCliente.buscaClientexId(
                            clienteId
                    );

            if (obj == null) {

                fun_mensajeError(
                        "No se encontró el cliente."
                );

                return;
            }

            bandera = obj.getCli_id();

            txt_nombre.setText(
                    valor(obj.getCli_nombre())
            );

            txt_ruc.setText(
                    valor(obj.getCli_ruc())
            );

            txt_contacto.setText(
                    valor(obj.getCli_contacto())
            );

            txt_telefono.setText(
                    valor(obj.getCli_telefono())
            );

            txt_correo.setText(
                    valor(obj.getCli_correo())
            );

            cmb_estado.setValue(
                    "A".equalsIgnoreCase(
                            obj.getCli_estado()
                    )
                            ? "Activo"
                            : "Inactivo"
            );

        } catch (NumberFormatException e) {

            fun_mensajeError(
                    "El identificador del cliente no es válido."
            );

        } catch (Exception e) {

            fun_mensajeError(
                    "No se pudo recuperar el cliente.\n\n"
                    + e.getMessage()
            );
        }
    }

    private void limpiar() {

        bandera = 0;

        txt_nombre.clear();
        txt_ruc.clear();
        txt_contacto.clear();
        txt_telefono.clear();
        txt_correo.clear();

        cmb_estado.setValue("Activo");
    }

    private String valor(String texto) {

        return texto == null
                ? ""
                : texto;
    }

    private void cerrar() {

        Stage stage =
                (Stage) btn_cancelar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase