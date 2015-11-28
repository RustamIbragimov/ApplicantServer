package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Excel;
import model.JDBCDriver;
import model.Person;
import model.Server;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextField portTextField;

    @FXML
    private Label errorPortLabel;

    @FXML
    private Label ipAddressLabel;

    private boolean isCorrectPort;
    private boolean isServerStarted;


    @FXML
    public void importAction() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files *.xls *.xslx", "*.xls", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Excel excel = new Excel(file);
            List<Person> list = excel.build();
            JDBCDriver driver = JDBCDriver.getInstance();
            driver.addAll(list);
        }
    }

    @FXML
    public void exportAction() {
        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(null);
        if (selectedDirectory != null) {
            String path = selectedDirectory.getAbsolutePath();
            List<Person> list = JDBCDriver.getInstance().getAll();
            Excel.export(list, path);
        }
    }

    @FXML
    public void startServerAction() {
        boolean isPortAvailable = Server.isPortAvailable(portTextField.getText());
        if (isCorrectPort && isPortAvailable) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    int portNumber = Integer.valueOf(portTextField.getText());
                    isServerStarted = true;
                    Server.getInstance().launch(portNumber);
                }
            });
            t.start();
        }
    }


    @FXML
    public void showStatistics() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/statistics.fxml"));
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle("Statistics");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isCorrectPort(String port) {
        int length = port.length();
        if (length == 0) return true;
        if (length >= 5) return false;
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(port.charAt(i))) return false;
        }
        if (Integer.valueOf(port) > 65535) return false;
        return true;
    }

    public void initialize(URL location, ResourceBundle resources) {
        isCorrectPort = false;
        isServerStarted = false;
        portTextField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!isCorrectPort(newValue)) {
                    errorPortLabel.setText("Incorrect port");
                    isCorrectPort = false;
                }
                else {
                    errorPortLabel.setText("");
                    isCorrectPort = true;
                }
            }
        });

        ipAddressLabel.setText(Server.getIpAddress());
    }
}
