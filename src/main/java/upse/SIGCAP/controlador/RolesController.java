// src/main/java/upse/SIGCAP/controlador/RolesController.java

package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import upse.SIGCAP.general.Mod_RolesPermisos;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class RolesController
        implements Initializable {

    @FXML
    private ComboBox<String> cmb_rol;

    @FXML
    private CheckBox chk_dashboard;

    @FXML
    private CheckBox chk_nueva_ot;

    @FXML
    private CheckBox chk_buscar_ot;

    @FXML
    private CheckBox chk_mis_ot;

    @FXML
    private CheckBox chk_produccion;

    @FXML
    private CheckBox chk_artes;

    @FXML
    private CheckBox chk_terceros;

    @FXML
    private CheckBox chk_instalaciones;

    @FXML
    private CheckBox chk_catalogos;

    @FXML
    private CheckBox chk_reportes;

    @FXML
    private CheckBox chk_configuracion;

    @FXML
    private CheckBox chk_usuarios;

    @FXML
    private CheckBox chk_roles;

    @FXML
    private Button btn_todos;

    @FXML
    private Button btn_ninguno;

    @FXML
    private Button btn_guardar;

    @FXML
    private Button btn_cerrar;

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {

        cmb_rol.getItems().clear();

        cmb_rol.getItems().addAll(
                Mod_RolesPermisos.ADMINISTRADOR,
                Mod_RolesPermisos.COORDINADOR,
                Mod_RolesPermisos.DISENO,
                Mod_RolesPermisos.PRODUCCION,
                Mod_RolesPermisos.INSTALACIONES,
                Mod_RolesPermisos.CONSULTA
        );

        cmb_rol.setValue(
                Mod_RolesPermisos.COORDINADOR
        );

        cmb_rol.valueProperty()
                .addListener(
                        (obs, anterior, actual) ->
                                cargarPermisos(actual)
                );

        cargarPermisos(
                cmb_rol.getValue()
        );
    }

    private void cargarPermisos(
            String rol) {

        if (rol == null) {
            return;
        }

        Map<String, Boolean> permisos =
                Mod_RolesPermisos
                        .getPermisos(rol);

        chk_dashboard.setSelected(
                valor(
                        permisos,
                        "dashboard"
                )
        );

        chk_nueva_ot.setSelected(
                valor(
                        permisos,
                        "nueva_ot"
                )
        );

        chk_buscar_ot.setSelected(
                valor(
                        permisos,
                        "buscar_ot"
                )
        );

        chk_mis_ot.setSelected(
                valor(
                        permisos,
                        "mis_ot"
                )
        );

        chk_produccion.setSelected(
                valor(
                        permisos,
                        "produccion"
                )
        );

        chk_artes.setSelected(
                valor(
                        permisos,
                        "artes"
                )
        );

        chk_terceros.setSelected(
                valor(
                        permisos,
                        "terceros"
                )
        );

        chk_instalaciones.setSelected(
                valor(
                        permisos,
                        "instalaciones"
                )
        );

        chk_catalogos.setSelected(
                valor(
                        permisos,
                        "catalogos"
                )
        );

        chk_reportes.setSelected(
                valor(
                        permisos,
                        "reportes"
                )
        );

        chk_configuracion.setSelected(
                valor(
                        permisos,
                        "configuracion"
                )
        );

        chk_usuarios.setSelected(
                valor(
                        permisos,
                        "usuarios"
                )
        );

        chk_roles.setSelected(
                valor(
                        permisos,
                        "roles"
                )
        );
    }

    private boolean valor(
            Map<String, Boolean> mapa,
            String clave) {

        return Boolean.TRUE.equals(
                mapa.get(clave)
        );
    }

    @FXML
    private void acc_todos() {

        cambiarTodos(true);
    }

    @FXML
    private void acc_ninguno() {

        cambiarTodos(false);
    }

    private void cambiarTodos(
            boolean valor) {

        chk_dashboard.setSelected(valor);
        chk_nueva_ot.setSelected(valor);
        chk_buscar_ot.setSelected(valor);
        chk_mis_ot.setSelected(valor);
        chk_produccion.setSelected(valor);
        chk_artes.setSelected(valor);
        chk_terceros.setSelected(valor);
        chk_instalaciones.setSelected(valor);
        chk_catalogos.setSelected(valor);
        chk_reportes.setSelected(valor);
        chk_configuracion.setSelected(valor);
        chk_usuarios.setSelected(valor);
        chk_roles.setSelected(valor);
    }

    @FXML
    private void acc_guardar() {

        try {

            if (cmb_rol.getValue() == null) {

                fun_mensajeError(
                        "Seleccione un rol."
                );

                return;
            }

            Map<String, Boolean> permisos =
                    new HashMap<>();

            permisos.put(
                    "dashboard",
                    chk_dashboard.isSelected()
            );

            permisos.put(
                    "nueva_ot",
                    chk_nueva_ot.isSelected()
            );

            permisos.put(
                    "buscar_ot",
                    chk_buscar_ot.isSelected()
            );

            permisos.put(
                    "mis_ot",
                    chk_mis_ot.isSelected()
            );

            permisos.put(
                    "produccion",
                    chk_produccion.isSelected()
            );

            permisos.put(
                    "artes",
                    chk_artes.isSelected()
            );

            permisos.put(
                    "terceros",
                    chk_terceros.isSelected()
            );

            permisos.put(
                    "instalaciones",
                    chk_instalaciones.isSelected()
            );

            permisos.put(
                    "catalogos",
                    chk_catalogos.isSelected()
            );

            permisos.put(
                    "reportes",
                    chk_reportes.isSelected()
            );

            permisos.put(
                    "configuracion",
                    chk_configuracion.isSelected()
            );

            permisos.put(
                    "usuarios",
                    chk_usuarios.isSelected()
            );

            permisos.put(
                    "roles",
                    chk_roles.isSelected()
            );

            Mod_RolesPermisos.guardarPermisos(
                    cmb_rol.getValue(),
                    permisos
            );

            fun_mensajeInformacion(
                    "Los permisos del rol "
                    + cmb_rol.getValue()
                    + " fueron guardados correctamente."
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudieron guardar los permisos.\n\n"
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