package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import model.Statistics;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by ribra on 11/11/2015.
 */
public class StatisticsController implements Initializable {

    @FXML
    BarChart<String, Integer> placeChart;

    @FXML
    CategoryAxis placeCategory;

    @FXML
    NumberAxis placeNumber;


    public void initializePlaceChart() {
        placeChart.setTitle("Where did you hear about AIESEC?");
        placeCategory.setLabel("Place");
        placeNumber.setLabel("Number");

        final String[] places = {"VK", "Instagram", "Friend who is a member", "Official website (aiesec.kz)"
                                ,"University", "Facebook", "Other", "AIESEC Partner", "Poster"};

        final XYChart.Series<String, Integer> series = new XYChart.Series();
        Statistics stat = Statistics.getInstance();
        final int [] numbers = new int[places.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = stat.getAmount(places[i]);
        }
        for (int i = 0; i < numbers.length; i++) {
            series.getData().add(new XYChart.Data<String, Integer>(places[i], numbers[i]));
        }


        placeChart.getData().add(series);
    }

    public void initialize(URL location, ResourceBundle resources) {
        initializePlaceChart();
    }
}
