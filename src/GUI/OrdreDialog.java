package GUI;

import Controller.Controller;
import Model.*;
import Storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class OrdreDialog extends Stage {
    private final Ordre ordre;

    public OrdreDialog(Ordre ordre) {
        this.ordre = ordre;

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        GridPane pane = new GridPane();
        this.initContent(pane);


        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private final ListView<Produktgruppe> lvwProduktgruppe = new ListView<>();
    private final ListView<Pris> lvwProdukt = new ListView<>();
    private final ListView<Salgsituation> lvwSalgsituation = new ListView<>();
    private final ListView<OrdreLinje> lvwOrdreLinje = new ListView<>();
    private final TextField txfProcentRabat = new TextField();
    private final TextField txfAftaltRabat = new TextField();
    private final TextField txfAntalProdukter = new TextField();
    private final TextField txfSamletPris = new TextField();
    private final TextField txfKlip = new TextField("0");
    private final ComboBox<String> boxBetaling = new ComboBox<>();
    private Ordre o = Controller.createOrdre(null,LocalDate.now());


    private Label lblError;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblAntalProdukter = new Label("Antal");
        pane.add(lblAntalProdukter,2,4);
        pane.add(txfAntalProdukter,3,4);

        Label lblKlip = new Label("Antal Klip");
        pane.add(lblKlip,2,5);
        pane.add(txfKlip,3,5);

        pane.add(txfSamletPris,3,6);

        Label lblProduktgruppe = new Label("Produktgruppe");
        pane.add(lblProduktgruppe, 1, 0);
/*
        pane.add(lvwProduktgruppe, 1, 1);
        lvwProduktgruppe.setPrefHeight(200);
        lvwProduktgruppe.setPrefWidth(200);
        lvwProduktgruppe.getItems().setAll(Controller.getProduktgrupper());
        ChangeListener<Produktgruppe> listener = (ov, o, n) -> this.selectionChanged();
        lvwProduktgruppe.getSelectionModel().selectedItemProperty().addListener(listener);*/


        lblError = new Label();
        pane.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");

        pane.add(lvwProdukt, 2, 1);
        //ChangeListener<Produkt> listener2 = (ov, o, n) -> this.selectionChanged2();
        lvwProdukt.setPrefHeight(200);
        lvwProdukt.setPrefWidth(200);
        //lvwProdukt.getSelectionModel().selectedItemProperty().addListener(listener2);


        pane.add(lvwSalgsituation,0,1);
        lvwSalgsituation.getItems().setAll(Controller.getSalgsSituation());
        ChangeListener<Salgsituation> listener3 = (ov, o, n) -> this.selectionChanged();
        lvwSalgsituation.setPrefHeight(200);
        lvwSalgsituation.setPrefWidth(200);
        lvwSalgsituation.getSelectionModel().selectedItemProperty().addListener(listener3);

        pane.add(lvwOrdreLinje,0,4);
        lvwOrdreLinje.getItems().setAll();
        lvwOrdreLinje.setPrefHeight(200);
        lvwOrdreLinje.setPrefWidth(200);



        Label lblRabat = new Label("Giv rabat: ");
        pane.add(lblRabat, 2, 2);
        pane.add(txfProcentRabat, 3, 2);

        Button btnOpretOrdrelinje = new Button("Tilføj til kurven");
        pane.add(btnOpretOrdrelinje, 2, 6);
        btnOpretOrdrelinje.setOnAction(event -> tilføjTilKurv());


        Label lblPris = new Label("Aftalt rabat: ");
        pane.add(lblPris, 2, 3);
        pane.add(txfAftaltRabat, 3, 3);


        Button btnAfslutOrdre = new Button("Afslut");
        pane.add(btnAfslutOrdre,1, 4);
        btnAfslutOrdre.setOnAction(event -> lukOrdre());

        pane.add(boxBetaling, 3,1);
        boxBetaling.getItems().setAll("Kort", "Kontant", "Mobilepay");


    }

    private void lukOrdre() {
        if (o.getBetalingsForm()==null)
            lblError.setText("Du skal vælge betalingsform!");
        else {
            o.setBetalingsForm(boxBetaling.getValue());
            this.hide();
        }
    }

    private void selectionChanged() {
        Salgsituation selected = lvwSalgsituation.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwProdukt.getItems().setAll(selected.getPrisArrayList());
        }
        if(!selected.getNavn().equals("Fredagsbar")){
            txfKlip.setEditable(false);
            txfKlip.setText("0");
        }
        else {
            txfKlip.setEditable(true);
            txfKlip.setText(String.valueOf(0));
        }

    }


    private void tilføjTilKurv() {
        Pris pris = lvwProdukt.getSelectionModel().getSelectedItem();
        int antal = Integer.parseInt(txfAntalProdukter.getText().trim());
        int antalKlip = Integer.parseInt(txfKlip.getText().trim());
        OrdreLinje oLinje = Controller.createOrdreLinje(antal,0 ,antalKlip,pris,o);
        lvwOrdreLinje.getItems().setAll(o.getOrdreLinjeArrayList());
         if(!txfAftaltRabat.getText().isEmpty()){
             FastRabat rabat = Controller.createFastRabat(Double.valueOf(txfAftaltRabat.getText().trim()));
             oLinje.setRabat(rabat);
         }
        if(!txfProcentRabat.getText().isEmpty()){
            ProcentRabat rabat2 = Controller.createProcentRabat(Double.valueOf(txfProcentRabat.getText().trim()));
            oLinje.setRabat(rabat2);
        }
        txfSamletPris.setText(String.valueOf(o.samletOrdrePris()));
    }
}
