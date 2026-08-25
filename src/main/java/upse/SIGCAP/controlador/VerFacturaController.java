package upse.SIGCAP.controlador;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import upse.SIGCAP.mad.Mad_Cliente;
import upse.SIGCAP.mad.Mad_Factura;

import upse.SIGCAP.modelo.Cliente;
import upse.SIGCAP.modelo.Factura;

public class VerFacturaController implements Initializable {

    @FXML
    private TextField txt_numfactura;

    @FXML
    private TextField txt_fecha;

    @FXML
    private TextField txt_documento;

    @FXML
    private TextField txt_nombres;

    @FXML
    private TextArea txt_direccion;

    @FXML
    private TextField txt_telefono;

    @FXML
    private TextField txt_correo;

    @FXML
    private TextField txt_subtotal;

    @FXML
    private TextField txt_subtotalcero;

    @FXML
    private TextField txt_iva;

    @FXML
    private TextField txt_total;

    @FXML
    private Button btn_anular;

    @FXML
    private Button btn_imprimir;

    @FXML
    private Button btn_cerrar;

    private final Mad_Factura madFactura;

    private final Mad_Cliente madCliente;

    private int fac_id;

    public VerFacturaController() {

        madFactura =
                new Mad_Factura();

        madCliente =
                new Mad_Cliente();
    }

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {
    }

    public void cargarFactura(
            int idFactura) {

        fac_id = idFactura;

        try {

            Factura factura =
                    madFactura.buscaFacturaxId(
                            idFactura
                    );

            if (factura == null) {

                mostrarError(
                        "No se encontró la factura."
                );

                return;
            }

            txt_numfactura.setText(
                    factura.getFac_numero()
            );

            if (factura.getFac_fecha() != null) {

                txt_fecha.setText(
                        new SimpleDateFormat(
                                "dd/MM/yyyy"
                        ).format(
                                factura.getFac_fecha()
                        )
                );
            } else {

                txt_fecha.clear();
            }

            Cliente cliente =
                    madCliente.buscaClientexId(
                            factura.getCli_id()
                    );

            if (cliente != null) {

                txt_documento.setText(
                        cliente.getCli_ruc() == null
                        ? ""
                        : cliente.getCli_ruc()
                );

                txt_nombres.setText(
                        cliente.getCli_nombre() == null
                        ? ""
                        : cliente.getCli_nombre()
                );

                txt_telefono.setText(
                        cliente.getCli_telefono() == null
                        ? ""
                        : cliente.getCli_telefono()
                );

                txt_correo.setText(
                        cliente.getCli_correo() == null
                        ? ""
                        : cliente.getCli_correo()
                );

                txt_direccion.setText(
                        cliente.getCli_contacto() == null
                        ? ""
                        : cliente.getCli_contacto()
                );
            }

            txt_subtotal.setText(
                    "0.00"
            );

            txt_subtotalcero.setText(
                    "0.00"
            );

            txt_iva.setText(
                    String.format(
                            "%.2f",
                            factura.getFac_iva()
                    )
            );

            txt_total.setText(
                    String.format(
                            "%.2f",
                            factura.getFac_total()
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    "No se pudo cargar la factura.\n\n"
                    + obtenerCausa(e)
            );
        }
    }

    @FXML
    private void acc_anular(
            ActionEvent event) {

        try {

            Factura factura =
                    madFactura.buscaFacturaxId(
                            fac_id
                    );

            if (factura == null) {

                mostrarError(
                        "No se encontró la factura."
                );

                return;
            }

            Alert alerta =
                    new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            alerta.setTitle(
                    "Confirmación"
            );

            alerta.setHeaderText(null);

            alerta.setContentText(
                    "¿Desea anular esta factura?"
            );

            if (alerta.showAndWait()
                    .orElse(ButtonType.CANCEL)
                    != ButtonType.OK) {

                return;
            }

            factura.setFac_estado(
                    "E"
            );

            boolean resultado =
                    madFactura.mantFactura(
                            factura
                    );

            if (resultado) {

                Alert ok =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                ok.setTitle(
                        "Sistema"
                );

                ok.setHeaderText(null);

                ok.setContentText(
                        "Factura anulada correctamente."
                );

                ok.showAndWait();

                cerrarVentana();

            } else {

                mostrarError(
                        "No fue posible anular la factura."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    "No se pudo anular la factura.\n\n"
                    + obtenerCausa(e)
            );
        }
    }

    @FXML
    private void acc_imprimir(
            ActionEvent event) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alerta.setTitle(
                "Sistema"
        );

        alerta.setHeaderText(null);

        alerta.setContentText(
                "La impresión de la factura "
                + "aún no ha sido implementada."
        );

        alerta.showAndWait();
    }

    @FXML
    private void acc_cerrar(
            ActionEvent event) {

        cerrarVentana();
    }

    @FXML
    private void acc_cerrar() {

        cerrarVentana();
    }

    private void cerrarVentana() {

        if (btn_cerrar != null
                && btn_cerrar.getScene() != null) {

            Stage stage =
                    (Stage) btn_cerrar
                            .getScene()
                            .getWindow();

            stage.close();
        }
    }

    private String obtenerCausa(
            Exception e) {

        Throwable causa = e;

        while (causa.getCause() != null) {

            causa =
                    causa.getCause();
        }

        if (causa.getMessage() == null
                || causa.getMessage()
                        .trim()
                        .isEmpty()) {

            return causa.getClass()
                    .getSimpleName();
        }

        return causa.getClass()
                .getSimpleName()
                + ": "
                + causa.getMessage();
    }

    private void mostrarError(
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alerta.setTitle(
                "Mensaje del Sistema"
        );

        alerta.setHeaderText(null);

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }

}