package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class DashboardController implements Initializable {

    @FXML
    private BarChart<String, Number> bar_estado;

    @FXML
    private PieChart pie_estado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cargarGraficos();
    }

    private void cargarGraficos() {

        bar_estado.getData().clear();
        pie_estado.getData().clear();

        XYChart.Series<String, Number> serie =
                new XYChart.Series<>();

        serie.setName("Órdenes");

        serie.getData().add(
                new XYChart.Data<>("Programado", 1)
        );

        serie.getData().add(
                new XYChart.Data<>("En Artes", 3)
        );

        serie.getData().add(
                new XYChart.Data<>("En Producción", 9)
        );

        serie.getData().add(
                new XYChart.Data<>("En Instalación", 3)
        );

        serie.getData().add(
                new XYChart.Data<>("Completado", 2)
        );

        bar_estado.getData().add(serie);

        pie_estado.getData().add(
                new PieChart.Data(
                        "En Producción",
                        9
                )
        );

        pie_estado.getData().add(
                new PieChart.Data(
                        "En Artes",
                        3
                )
        );

        pie_estado.getData().add(
                new PieChart.Data(
                        "En Instalación",
                        3
                )
        );

        pie_estado.getData().add(
                new PieChart.Data(
                        "Completado",
                        2
                )
        );

        pie_estado.getData().add(
                new PieChart.Data(
                        "Programado",
                        1
                )
        );
    }

    @FXML
    private void acc_dashboard() {

        try {

            App.setRoot("Dashboard");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void acc_nuevaOT() {

        try {

            FXMLLoaderHelper.abrirFormularioOT(0);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void acc_buscarOT() {

        abrirPantalla("BuscarOT");
    }

    @FXML
    private void acc_misOT() {

        abrirPantalla("MisOT");
    }

    @FXML
    private void acc_produccionGrafica() {

        abrirPantalla("ProduccionGrafica");
    }

    @FXML
    private void acc_artes() {

        abrirPantalla("Artes");
    }

    @FXML
    private void acc_terceros() {

        abrirPantalla("Terceros");
    }

    @FXML
    private void acc_instalaciones() {

        abrirPantalla("Instalaciones");
    }

    @FXML
    private void acc_catalogos() {

        abrirPantalla("Catalogos");
    }

    @FXML
    private void acc_proformas() {

        abrirPantalla("Proformas");
    }

    @FXML
    private void acc_facturacion() {

        abrirPantalla("Facturacion");
    }

    @FXML
    private void acc_reportes() {

        abrirPantalla("Reportes");
    }

    @FXML
    private void acc_configuracion() {

        abrirPantalla("Configuracion");
    }

    private void abrirPantalla(String pantalla) {

        try {

            App.setRoot(pantalla);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}