package org.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class Controller {
    private final String ipAdress = "ipAdress";
    private final String sectionName = "sectionName";
    private final String appVersion = "appVersion";
    private final String login = "login";

    private LogQuery logQuery;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startParse_button;

    @FXML
    private Tab quontity_tab;

    @FXML
    private Label allRecords_field_q;

    @FXML
    private Label ipAdress_field_q;

    @FXML
    private Label sectionName_field_q;

    @FXML
    private Label appVersion_field_q;

    @FXML
    private Label login_field_q;

    @FXML
    private Button sum_button;

    @FXML
    private Button ip_sum_button;

    @FXML
    private Button sectionName_sum_button;

    @FXML
    private Button appVersion_sum_button;

    @FXML
    private Button login_sum_button;

    @FXML
    private Tab freq_tab;

    @FXML
    private Label ipAdress_freq_max;

    @FXML
    private Label ipAdress_freq_min;

    @FXML
    private Label sectionName_freq_max;

    @FXML
    private Label sectionName_freq_min;

    @FXML
    private Label appVersion_freq_max;

    @FXML
    private Label appVersion_freq_min;

    @FXML
    private Label login_freq_max;

    @FXML
    private Label login_freq_min;

    @FXML
    private Label ipAdress_freq_max_q;

    @FXML
    private Label sectionName_freq_max_q;

    @FXML
    private Label appVersion_freq_max_q;

    @FXML
    private Label login_freq_max_q;

    @FXML
    private Label ipAdress_freq_min_q;

    @FXML
    private Label sectionName_freq_min_q;

    @FXML
    private Label appVersion_freq_min_q;

    @FXML
    private Label login_freq_min_q;

    @FXML
    private Button ip_freq_button;

    @FXML
    private Button sectionName_freq_button;

    @FXML
    private Button appVersion_freq_button;

    @FXML
    private Button login_freq_button;

    @FXML
    void initialize() {
        startParse_button.setOnAction(event -> {
            startParse_button.setDisable(true);
            new Parser().parserLog();

            logQuery = new LogQuery();
            sum_button.setDisable(false);
            ip_sum_button.setDisable(false);
            ip_freq_button.setDisable(false);
            sectionName_sum_button.setDisable(false);
            sectionName_freq_button.setDisable(false);
            appVersion_sum_button.setDisable(false);
            appVersion_freq_button.setDisable(false);
            login_sum_button.setDisable(false);
            login_freq_button.setDisable(false);
        });

        sum_button.setOnAction(event -> {
            allRecords_field_q.setText(logQuery.allRecords().toString());
        });

        ip_sum_button.setOnAction(event -> {
            ipAdress_field_q.setText(logQuery.returnRecordQuonty(ipAdress).toString());
        });

        sectionName_sum_button.setOnAction(event -> {
            sectionName_field_q.setText(logQuery.returnRecordQuonty(sectionName).toString());
        });

        appVersion_sum_button.setOnAction(event -> {
            appVersion_field_q.setText(logQuery.returnRecordQuonty(appVersion).toString());
        });

        login_sum_button.setOnAction(event -> {
            login_field_q.setText(logQuery.returnRecordQuonty(login).toString());
        });

        ip_freq_button.setOnAction(event -> {
            String[] str = logQuery.returnRecordMax(ipAdress);
            ipAdress_freq_max.setText(str[0]);
            ipAdress_freq_max_q.setText(str[1]);

            str = logQuery.returnRecordMin(ipAdress);
            ipAdress_freq_min.setText(str[0]);
            ipAdress_freq_min_q.setText(str[1]);
        });

        sectionName_freq_button.setOnAction(event -> {
            String[] str = logQuery.returnRecordMax(sectionName);
            sectionName_freq_max.setText(str[0]);
            sectionName_freq_max_q.setText(str[1]);

            str = logQuery.returnRecordMin(sectionName);
            sectionName_freq_min.setText(str[0]);
            sectionName_freq_min_q.setText(str[1]);
        });

        appVersion_freq_button.setOnAction(event -> {
            String[] str = logQuery.returnRecordMax(appVersion);
            appVersion_freq_max.setText(str[0]);
            appVersion_freq_max_q.setText(str[1]);

            str = logQuery.returnRecordMin(appVersion);
            appVersion_freq_min.setText(str[0]);
            appVersion_freq_min_q.setText(str[1]);
        });

        login_freq_button.setOnAction(event -> {
            String[] str = logQuery.returnRecordMax(login);
            login_freq_max.setText(str[0]);
            login_freq_max_q.setText(str[1]);

            str = logQuery.returnRecordMin(login);
            login_freq_min.setText(str[0]);
            login_freq_min_q.setText(str[1]);
        });
    }
}
