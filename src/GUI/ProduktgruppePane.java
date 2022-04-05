package GUI;


import Controller.Controller;
import Model.Produkt;
import Model.Produktgruppe;
import Storage.Storage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;

public class ProduktgruppePane extends GridPane {

    private final ListView<Produktgruppe> lvwProduktgruppe = new ListView<>();
    private final ListView<Produkt> lvwProdukt = new ListView<>();
    private final TextField txfProduktgruppeNavn = new TextField();
    private final TextField txfProduktNavn = new TextField();
    private final TextField txfProduktBeskrivelse = new TextField();
    private Label lblError;


    public ProduktgruppePane() {
        Controller.initStorage();
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblProduktgruppe = new Label("Produktgruppe");
        this.add(lblProduktgruppe, 0, 0);

        this.add(lvwProduktgruppe, 0, 1);
        lvwProduktgruppe.getItems().setAll(Controller.getProduktgrupper());
        ChangeListener<Produktgruppe> listener = (ov, o, n) -> this.selectionChanged();
        lvwProduktgruppe.getSelectionModel().selectedItemProperty().addListener(listener);


        lblError = new Label();
        this.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");

        this.add(lvwProdukt, 1, 1);
        ChangeListener<Produkt> listener2 = (ov, o, n) -> this.selectionChanged2();
        //lvwProdukt.getSelectionModel().selectedItemProperty().addListener(listener2);


        Button btnProduktgruppe = new Button("Opret Produktgruppe");
        this.add(btnProduktgruppe, 0, 2);
        this.add(txfProduktgruppeNavn, 1, 2);
        btnProduktgruppe.setOnAction(event -> opretProduktgruppe());


        Label lblProduktNavn = new Label("Navn: ");
        this.add(lblProduktNavn, 2, 2);
        this.add(txfProduktNavn, 3, 2);

        Button btnOpretProdukt = new Button("Opret Produkt");
        this.add(btnOpretProdukt, 2, 5);
        btnOpretProdukt.setOnAction(event -> opretProdukt());


        Label lblProduktBeskrivelse = new Label("Beskrivelse: ");
        this.add(lblProduktBeskrivelse, 2, 3);
        this.add(txfProduktBeskrivelse, 3, 3);


    }

    private void selectionChanged() {
        Produktgruppe selected = lvwProduktgruppe.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwProdukt.getItems().setAll(selected.getProduktArrayList());
        }
    }

    private void selectionChanged2() {
        Produkt selected = lvwProdukt.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwProdukt.getItems().setAll();
        }
    }

    private void opretProduktgruppe() {
        if (!txfProduktgruppeNavn.getText().isEmpty()) {
            String navn = txfProduktgruppeNavn.getText();
            Produktgruppe produktgruppe = Controller.createProduktGruppe(navn);
            lvwProduktgruppe.getItems().setAll(Storage.getProduktgruppeArrayList());
            lblError.setText("");
        } else lblError.setText("Produktgruppe skal have et navn");

    }

    private void opretProdukt() {
        if (lvwProduktgruppe.getSelectionModel().getSelectedItem() == null) {
        lblError.setText("Du skal vælge en produktgruppe.");
        }
        if (txfProduktBeskrivelse.getText().isEmpty() || txfProduktNavn.getText().isEmpty()) {
            lblError.setText("Produkt skal have en beskrivelse og et navn");
        }

        else {
            String navn = txfProduktNavn.getText().trim();
            String beskrivelse = txfProduktBeskrivelse.getText().trim();
            Produktgruppe produktgruppe = lvwProduktgruppe.getSelectionModel().getSelectedItem();
            Controller.createProdukt(navn, beskrivelse,produktgruppe);
            selectionChanged();
            lblError.setText("");
        }

    }
}
