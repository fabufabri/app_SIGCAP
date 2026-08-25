
package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import upse.SIGCAP.modelo.ItemOrdenTrabajo;

import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class NuevoItemOTController implements Initializable {

    @FXML
    private ComboBox<String> cmb_local;

    @FXML
    private ComboBox<String> cmb_producto;

    @FXML
    private TextField txt_descripcion;

    @FXML
    private ComboBox<String> cmb_medida;

    @FXML
    private TextField txt_medidaPersonalizada;

    @FXML
    private ComboBox<String> cmb_material;

    @FXML
    private Spinner<Integer> spn_cantidad;

    @FXML
    private ComboBox<String> cmb_instalacion;

    @FXML
    private TextField txt_observaciones;

    private ItemOrdenTrabajo item;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cargarDatos();

        spn_cantidad.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        100000,
                        1
                )
        );

        txt_medidaPersonalizada.setVisible(false);
        txt_medidaPersonalizada.setManaged(false);

        cmb_medida.valueProperty()
                .addListener(
                        (obs, anterior, actual) -> {

                            boolean personalizada =
                                    "Personalizada"
                                            .equalsIgnoreCase(actual);

                            txt_medidaPersonalizada.setVisible(
                                    personalizada
                            );

                            txt_medidaPersonalizada.setManaged(
                                    personalizada
                            );

                            if (!personalizada) {

                                txt_medidaPersonalizada.clear();
                            }
                        }
                );
    }

    private void cargarDatos() {

        cmb_local.setItems(
                FXCollections.observableArrayList(
                        "Seleccione local",
                        "UIO - Local 01",
                        "UIO - Local 02",
                        "UIO - Local 03",
                        "UIO - Local 04"
                )
        );

        cmb_producto.setItems(
                FXCollections.observableArrayList(
                        "Seleccione producto",
                        "Banner",
                        "Vinil",
                        "Roll Up",
                        "Caja de luz",
                        "Material POP",
                        "Letrero",
                        "Display"
                )
        );

        cmb_medida.setItems(
                FXCollections.observableArrayList(
                        "Seleccione medida",
                        "30 x 40 cm",
                        "50 x 70 cm",
                        "60 x 90 cm",
                        "80 x 120 cm",
                        "100 x 150 cm",
                        "120 x 200 cm",
                        "Personalizada"
                )
        );

        cmb_material.setItems(
                FXCollections.observableArrayList(
                        "Seleccione material",
                        "Vinil",
                        "Lona",
                        "PVC",
                        "Acrílico",
                        "Cartón",
                        "MDF",
                        "Sin especificar"
                )
        );

        cmb_instalacion.setItems(
                FXCollections.observableArrayList(
                        "NO",
                        "SI"
                )
        );

        cmb_local.getSelectionModel().selectFirst();
        cmb_producto.getSelectionModel().selectFirst();
        cmb_medida.getSelectionModel().selectFirst();
        cmb_material.getSelectionModel().selectFirst();
        cmb_instalacion.setValue("NO");
    }

    public void setItem(ItemOrdenTrabajo obj) {

        if (obj == null) {
            return;
        }

        item = obj;

        seleccionar(
                cmb_local,
                obj.getItm_local()
        );

        seleccionar(
                cmb_producto,
                obj.getItm_producto()
        );

        txt_descripcion.setText(
                valor(obj.getItm_descripcion())
        );

        seleccionar(
                cmb_material,
                obj.getItm_material()
        );

        spn_cantidad.getValueFactory()
                .setValue(
                        obj.getItm_cantidad() <= 0
                                ? 1
                                : obj.getItm_cantidad()
                );

        seleccionar(
                cmb_instalacion,
                obj.getItm_instalacion()
        );

        String medida =
                obj.getItm_medida();

        if (medida != null
                && cmb_medida.getItems()
                        .contains(medida)) {

            cmb_medida.setValue(
                    medida
            );

        } else {

            cmb_medida.setValue(
                    "Personalizada"
            );

            txt_medidaPersonalizada.setText(
                    valor(medida)
            );

            txt_medidaPersonalizada.setVisible(true);
            txt_medidaPersonalizada.setManaged(true);
        }
    }

    @FXML
    private void acc_aceptar() {

        if (!validar()) {
            return;
        }

        if (item == null) {

            item =
                    new ItemOrdenTrabajo();
        }

        item.setItm_local(
                cmb_local.getValue()
        );

        item.setItm_producto(
                cmb_producto.getValue()
        );

        item.setItm_descripcion(
                txt_descripcion.getText().trim()
        );

        item.setItm_medida(
                obtenerMedida()
        );

        item.setItm_material(
                cmb_material.getValue()
        );

        item.setItm_cantidad(
                spn_cantidad.getValue()
        );

        item.setItm_instalacion(
                cmb_instalacion.getValue()
        );

        if (item.getItm_estado() == null
                || item.getItm_estado()
                        .trim()
                        .isEmpty()) {

            item.setItm_estado(
                    "EN ARTES"
            );
        }

        if (item.getItm_progreso() < 0) {

            item.setItm_progreso(
                    0
            );
        }

        /*
         * NO se utiliza itm_observaciones porque
         * esa propiedad no existe actualmente
         * en ItemOrdenTrabajo.
         */

        cerrar();
    }

    private String obtenerMedida() {

        if ("Personalizada".equalsIgnoreCase(
                cmb_medida.getValue())) {

            return txt_medidaPersonalizada
                    .getText()
                    .trim();
        }

        return cmb_medida.getValue();
    }

    private boolean validar() {

        if (cmb_local.getValue() == null
                || cmb_local.getValue()
                        .equals("Seleccione local")) {

            fun_mensajeInformacion(
                    "Seleccione el local."
            );

            return false;
        }

        if (cmb_producto.getValue() == null
                || cmb_producto.getValue()
                        .equals("Seleccione producto")) {

            fun_mensajeInformacion(
                    "Seleccione el producto."
            );

            return false;
        }

        if (txt_descripcion.getText()
                .trim()
                .isEmpty()) {

            fun_mensajeInformacion(
                    "Ingrese la descripción."
            );

            txt_descripcion.requestFocus();

            return false;
        }

        if (cmb_medida.getValue() == null
                || cmb_medida.getValue()
                        .equals("Seleccione medida")) {

            fun_mensajeInformacion(
                    "Seleccione la medida."
            );

            return false;
        }

        if ("Personalizada".equalsIgnoreCase(
                cmb_medida.getValue())) {

            if (txt_medidaPersonalizada.getText()
                    .trim()
                    .isEmpty()) {

                fun_mensajeInformacion(
                        "Ingrese la medida personalizada."
                );

                txt_medidaPersonalizada.requestFocus();

                return false;
            }
        }

        if (cmb_material.getValue() == null
                || cmb_material.getValue()
                        .equals("Seleccione material")) {

            fun_mensajeInformacion(
                    "Seleccione el material."
            );

            return false;
        }

        if (spn_cantidad.getValue() == null
                || spn_cantidad.getValue() <= 0) {

            fun_mensajeInformacion(
                    "Ingrese una cantidad válida."
            );

            return false;
        }

        return true;
    }

    private void seleccionar(
            ComboBox<String> combo,
            String valor) {

        if (valor == null) {
            return;
        }

        if (!combo.getItems().contains(valor)) {

            combo.getItems().add(valor);
        }

        combo.setValue(valor);
    }

    private String valor(String texto) {

        return texto == null
                ? ""
                : texto;
    }

    @FXML
    private void acc_cancelar() {

        item = null;

        cerrar();
    }

    private void cerrar() {

        Stage stage =
                (Stage) cmb_local
                        .getScene()
                        .getWindow();

        stage.close();
    }

    public ItemOrdenTrabajo getItem() {

        return item;
    }

}//fin clase