package GUI;

import Controller.Controller;
import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.ArrayList;

public class UdlejningDialog extends Stage {
    private final ListView<Pris> lvwProdukt = new ListView<>();
    private final ListView<OrdreLinje> lvwOrdreLinje = new ListView<>();
    private final TextField txfAntalProdukter = new TextField();
    private final TextField txfPantPris = new TextField();
    private final DatePicker dpStartDato = new DatePicker();
    private final DatePicker dpfSlutDato = new DatePicker();
    private Udlejning udlejning = Controller.createUdlejning(null,LocalDate.now(),null,null);


    private Label lblError;


 public UdlejningDialog(){
    this.initStyle(StageStyle.UTILITY);
    this.initModality(Modality.APPLICATION_MODAL);
    this.setResizable(false);

    GridPane pane = new GridPane();
    this.initContent(pane);


    Scene scene = new Scene(pane);
    this.setScene(scene);
}
    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblAntalProdukter = new Label("Antal");
        pane.add(lblAntalProdukter,3,4);
        pane.add(txfAntalProdukter,3,5);

        Label lblStartDato = new Label("StartDato: ");
        pane.add(lblStartDato, 3, 0);
        pane.add(dpStartDato, 3, 1);



        Label lblSlutDato = new Label("SlutDato: ");
        pane.add(lblSlutDato, 3, 2);
        pane.add(dpfSlutDato, 3, 3);


        Label lblPant = new Label("Samlet pant");
        pane.add(lblPant,3,5);
        pane.add(txfPantPris,3,6);
        txfPantPris.setEditable(false);

        Label lblProdukt = new Label("Produkter");
        pane.add(lblProdukt, 0, 0);

        lblError = new Label();
        pane.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");

        pane.add(lvwProdukt, 0, 2);
        lvwProdukt.getItems().setAll(Controller.getPantProdukt());
        lvwProdukt.setPrefHeight(200);
        lvwProdukt.setPrefWidth(400);


        pane.add(lvwOrdreLinje,0,4);
        lvwOrdreLinje.getItems().setAll();
        lvwOrdreLinje.setPrefHeight(200);
        lvwOrdreLinje.setPrefWidth(200);

        Button btnOpretOrdrelinje = new Button("Tilføj produkt");
        pane.add(btnOpretOrdrelinje, 1, 4);
        btnOpretOrdrelinje.setOnAction(event -> tilføjTilUdlejning());

        Button btnAfslutOrdre = new Button("Afslut");
        pane.add(btnAfslutOrdre,2, 6);
        btnAfslutOrdre.setOnAction(event -> afslutUdlejning());


    }


    private void afslutUdlejning(){
        udlejning.setStartDato(dpStartDato.getValue());
        udlejning.setSlutDato(dpfSlutDato.getValue());
     if(udlejning.getStartDato()==null || udlejning.getSlutDato()==null){
         lblError.setText("Husk at sætte dato");
     }
     else {
         this.hide();

     }
    }



    private void tilføjTilUdlejning() {
        Pris pris = lvwProdukt.getSelectionModel().getSelectedItem();
        int antal = Integer.parseInt(txfAntalProdukter.getText().trim());
        OrdreLinje oLinje = Controller.createOrdreLinje(antal,0,0,pris,udlejning);
        lvwOrdreLinje.getItems().setAll(udlejning.getOrdreLinjeArrayList());
        txfPantPris.setText(String.valueOf(udlejning.samletPant()));

    }
}
