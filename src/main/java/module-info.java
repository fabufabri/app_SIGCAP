module ec.edu.upse.sigcap {
    requires javafx.controls;
    requires javafx.fxml;

    opens ec.edu.upse.sigcap to javafx.fxml;
    exports ec.edu.upse.sigcap;
}
