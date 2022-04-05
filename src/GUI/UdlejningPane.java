package GUI;

import Controller.Controller;
import Model.*;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class UdlejningPane extends GridPane {
    private final ListView<Ordre> lvwUdlejningerIkkeAfregnet = new ListView<>();
    private final ListView<Ordre> lvwUdlejningerAfregnet = new ListView<>();
    private final DatePicker txfDato = new DatePicker();
    private final TextField txfPris = new TextField();
    private final TextField txfAntalPersoner = new TextField();
    private final TextField txfStartTid = new TextField();
    private final TextField txfSlutTid = new TextField();
    private final TextField txfBetalingsmetode = new TextField();
    private final TextField txfTotalPris = new TextField();

    private Label lblError;


    public UdlejningPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblProduktgruppe = new Label("Udlejnings oversigt");
        this.add(lblProduktgruppe, 0, 0);

        this.add(lvwUdlejningerIkkeAfregnet, 0, 1);
        lvwUdlejningerIkkeAfregnet.getItems().setAll(Controller.getUdlejningerIkkeAfklaret());
        ChangeListener<Ordre> listener = (ov, o, n) -> this.selectionChanged();
        lvwUdlejningerIkkeAfregnet.getSelectionModel().selectedItemProperty().addListener(listener);

        this.add(lvwUdlejningerAfregnet,1,1);
        lvwUdlejningerAfregnet.getItems().setAll(Controller.getUdlejningerAfklaret());


        lblError = new Label();
        this.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");


        Label lblPris = new Label("Start dato: ");
        this.add(lblPris, 2, 2);
        this.add(txfPris, 3, 2);

        Button btnOpretUdlejning = new Button("Opret udlejning");
        this.add(btnOpretUdlejning, 1, 4);
        btnOpretUdlejning.setOnAction(event -> opretUdlejning());

        Button btnAfregnUdlejning= new Button("Afregn udlejning");
        this.add(btnAfregnUdlejning, 1, 5);


        Label lblAntalPersoner = new Label("AntalPersoner: ");
        this.add(lblAntalPersoner, 2, 3);
        this.add(txfAntalPersoner, 3, 3);

        Label lblStartTid = new Label("Start tid: ");
        this.add(lblStartTid, 2, 4);
        this.add(txfStartTid, 3, 4);

        Label lblSlutTid = new Label("Slut tid: ");
        this.add(lblSlutTid, 2, 5);
        this.add(txfSlutTid, 3, 5);

        Label lblDato = new Label("AntalPersoner: ");
        this.add(lblDato, 2, 6);
        this.add(txfDato, 3, 6);

        Label lblBetalingsmetode = new Label("Betalingsmetode: ");
        this.add(lblBetalingsmetode, 2, 7);
        this.add(txfBetalingsmetode, 3, 7);

        Label lblTotalPris = new Label("Pris for rundvisning: ");
        this.add(lblTotalPris, 2, 8);
        this.add(txfTotalPris, 3, 8);
        txfTotalPris.setEditable(false);

    }

    private void selectionChanged() {
        Udlejning ordre = (Udlejning) lvwUdlejningerIkkeAfregnet.getSelectionModel().getSelectedItem();
        if (ordre != null) {
            txfTotalPris.setText(String.valueOf(ordre.samletOrdrePris()));
            txfBetalingsmetode.setText(ordre.getBetalingsForm());
           /* txfSlutTid.setText(ordre.getSlutTid());
            txfStartTid.setText(ordre.getStartTid());*/
            txfDato.setValue(ordre.getDato());
            txfPris.setText(String.valueOf(ordre.getOrdreLinjeArrayList().get(0).getPris().getProduktPris()));
            txfAntalPersoner.setText(String.valueOf(ordre.getOrdreLinjeArrayList().get(0).getAntalAfProdukter()));
        } else {
            txfTotalPris.clear();
            txfBetalingsmetode.clear();
            txfSlutTid.clear();
            txfStartTid.clear();
            txfPris.clear();
            txfAntalPersoner.clear();
        }

    }
    private void afregnUdlejning(){

    }

    private void opretUdlejning() {
        if (txfPris.getText().isEmpty()) {
            lblError.setText("Du skal vælge en pris.");
        }
        if (txfAntalPersoner.getText().isEmpty()) {
            lblError.setText("Vælg antal personer");
        }
        if (txfStartTid.getText().isEmpty()) {
            lblError.setText("Vælg start tid");
        }
        if (txfSlutTid.getText().isEmpty()) {
            lblError.setText("Vælg slut tid");
        }
        if (txfDato.getValue().isBefore(LocalDate.now())) {
            lblError.setText("Vælg en senere dato");
        }
        if (txfBetalingsmetode.getText().isEmpty()) {
            lblError.setText("Vælg en betalingsmetode");
        }

        else {
            double prisPrPerson = Double.parseDouble(txfPris.getText().trim());
            int antalPersoner = Integer.parseInt(txfAntalPersoner.getText().trim());
            String startTid = txfStartTid.getText().trim();
            String slutTid = txfSlutTid.getText().trim();
            LocalDate dato = txfDato.getValue();
            String betalingsmetode = txfBetalingsmetode.getText().trim();

            Produktgruppe produktgruppe = Storage.getProduktgruppeArrayList().get(0);
            Produkt produkt = produktgruppe.getProduktArrayList().get(0);
            Salgsituation salgsituation = Storage.getSalgsituationArrayList().get(0);
            Pris pris = salgsituation.createPris(Double.parseDouble(txfPris.getText()),0,produkt);


            Rundvisning rundvisning = Controller.createRundvisning(betalingsmetode,LocalDate.now(),startTid,slutTid,dato);
            lvwUdlejningerIkkeAfregnet.getItems().setAll(Controller.getRundvisninger());
            Controller.createOrdreLinje(antalPersoner,0,0,pris,rundvisning);

            selectionChanged();
            lblError.setText("");
        }

    }
}
