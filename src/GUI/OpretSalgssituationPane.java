package GUI;

import Controller.Controller;
import Model.*;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OpretSalgssituationPane extends GridPane {
    private final ListView<Salgsituation> lvwSalgssituation = new ListView<>();
    private final ListView<Produktgruppe> lvwProduktgruppe = new ListView<>();
    private final ListView<Produkt> lvwProdukt = new ListView<>();
    private Label lblError;


    public OpretSalgssituationPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblSalgssituation = new Label("Salgssituationer");
        this.add(lblSalgssituation, 0, 0);

        this.add(lvwSalgssituation, 0, 1);
        lvwSalgssituation.getItems().setAll(Controller.getSalgsSituation());
        ChangeListener<Salgsituation> listener = (ov, o, n) -> this.selectionChanged();
        lvwSalgssituation.getSelectionModel().selectedItemProperty().addListener(listener);

        this.add(lvwProduktgruppe, 1, 1);
        lvwProduktgruppe.getItems().setAll(Controller.getProduktgrupper());

        this.add(lvwProdukt, 2, 1);
        ChangeListener<Produktgruppe> listener2 = (ov, o, n) -> this.selectionChanged2();
        lvwProduktgruppe.getSelectionModel().selectedItemProperty().addListener(listener2);

        Button btnOpret = new Button("Opret salgssituation");
        this.add(btnOpret, 2, 0);
        btnOpret.setOnAction(event -> salgssituationDialog());


        lblError = new Label();
        this.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");


    }

    private void selectionChanged() {
        Salgsituation selected = lvwSalgssituation.getSelectionModel().getSelectedItem();
        if (selected != null) {
        }
    }

    private void selectionChanged2() {
        Produktgruppe selected = lvwProduktgruppe.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwProdukt.getItems().setAll(selected.getProduktArrayList());
        }
    }


    private void salgssituationDialog() {
        SalgssituationDialog salgssituationDialog = new SalgssituationDialog();
        salgssituationDialog.showAndWait();
        lvwSalgssituation.getItems().setAll(Controller.getSalgsSituation());
        int index = lvwSalgssituation.getItems().size() - 1;
        lvwSalgssituation.getSelectionModel().select(index);
        lvwSalgssituation.getItems().setAll(Controller.getSalgsSituation());
    }
}
