// src/main/java/upse/SIGCAP/controlador/App.java

package upse.SIGCAP.controlador;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class App extends Application {

    private static Stage stagePrincipal;

    @Override
    public void start(Stage stage) {

        stagePrincipal = stage;

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            Objects.requireNonNull(
                                    App.class.getResource(
                                            "/upse/SIGCAP/vistas/Login.fxml"
                                    )
                            )
                    );

            Parent root =
                    loader.load();

            Scene scene =
                    new Scene(root);

            stage.setScene(scene);

            stage.setTitle(
                    "SIGCAP - Sistema Integral de Gestión"
            );

            stage.setResizable(false);

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    "No se pudo iniciar SIGCAP.",
                    e
            );
        }
    }

    public static Stage getStagePrincipal() {

        return stagePrincipal;
    }

    public static void setRoot(
            String fxml)
            throws IOException {

        if (stagePrincipal == null) {

            throw new IllegalStateException(
                    "La ventana principal no está inicializada."
            );
        }

        String ruta =
                "/upse/SIGCAP/vistas/"
                + fxml
                + ".fxml";

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Cargando pantalla: "
                + ruta
        );

        System.out.println(
                "========================================"
        );

        if (App.class.getResource(ruta)
                == null) {

            throw new IOException(
                    "No existe el archivo FXML:\n"
                    + ruta
            );
        }

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            App.class.getResource(
                                    ruta
                            )
                    );

            Parent root =
                    loader.load();

            Scene scene =
                    stagePrincipal.getScene();

            if (scene == null) {

                scene =
                        new Scene(root);

                stagePrincipal.setScene(
                        scene
                );

            } else {

                scene.setRoot(root);
            }

            System.out.println(
                    "Pantalla cargada correctamente: "
                    + fxml
            );

        } catch (Exception e) {

            System.err.println(
                    "ERROR CARGANDO FXML: "
                    + fxml
            );

            e.printStackTrace();

            throw e;
        }
    }

    public static Parent loadFXML(
            String fxml)
            throws IOException {

        String ruta =
                "/upse/SIGCAP/vistas/"
                + fxml
                + ".fxml";

        if (App.class.getResource(ruta)
                == null) {

            throw new IOException(
                    "No existe el archivo FXML:\n"
                    + ruta
            );
        }

        FXMLLoader loader =
                new FXMLLoader(
                        App.class.getResource(
                                ruta
                        )
                );

        return loader.load();
    }

    public static void cargarPrincipal() {

        try {

            setRoot("Dashboard");

            if (stagePrincipal != null) {

                stagePrincipal.setTitle(
                        "SIGCAP - Dashboard"
                );

                stagePrincipal.setResizable(
                        true
                );

                stagePrincipal.setMaximized(
                        true
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            mostrarError(
                    "No se pudo abrir Dashboard.",
                    e
            );
        }
    }

    private static void mostrarError(
            String titulo,
            Exception e) {

        Throwable causa = e;

        while (causa.getCause() != null) {

            causa = causa.getCause();
        }

        String mensaje =
                causa.getMessage();

        if (mensaje == null
                || mensaje.trim().isEmpty()) {

            mensaje =
                    causa.getClass()
                            .getSimpleName();
        }

        fun_mensajeError(
                titulo
                + "\n\n"
                + causa.getClass()
                        .getSimpleName()
                + ":\n"
                + mensaje
        );
    }

    public static void main(
            String[] args) {

        launch(args);
    }

}//fin clase