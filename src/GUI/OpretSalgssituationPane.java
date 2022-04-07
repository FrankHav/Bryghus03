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
    private final TextField txfPris = new TextField();
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

        lvwProduktgruppe.refresh();
        this.add(lvwProduktgruppe, 1, 1);
        lvwProduktgruppe.getItems().setAll(Controller.getProduktgrupper());
        ChangeListener<Produktgruppe> listener2 = (ov, o, n) -> this.selectionChanged2();
        lvwProduktgruppe.getSelectionModel().selectedItemProperty().addListener(listener2);

        this.add(lvwProdukt, 2, 1);
        ChangeListener<Produkt> listener3 = (ov, o, n) -> this.selectionChanged3();
        lvwProdukt.getSelectionModel().selectedItemProperty().addListener(listener3);

        Button btnOpret = new Button("Opret salgssituation");
        this.add(btnOpret, 0, 3);
        btnOpret.setOnAction(event -> salgssituationDialog());

        Button btnOpretPris = new Button("Opret pris");
        this.add(btnOpretPris, 1, 3);
        btnOpretPris.setOnAction(event -> opretPrisDialog());

        Label lblPris = new Label("Pris for valgte vare");
        this.add(lblPris,2,3);
        this.add(txfPris,2,4);
        txfPris.setEditable(false);

        lblError = new Label();
        this.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");



    }

    private void selectionChanged() {
        Salgsituation selected = lvwSalgssituation.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwProduktgruppe.getItems().setAll(Controller.getProduktgrupper());
        }
    }

    private void selectionChanged2() {
        Produktgruppe selected = lvwProduktgruppe.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwProdukt.getItems().setAll(selected.getProduktArrayList());
        }
    }
    private void selectionChanged3(){
        Produkt selected = lvwProdukt.getSelectionModel().getSelectedItem();
        if(selected!=null){
            txfPris.setText(String.valueOf(getPris(lvwSalgssituation.getSelectionModel().getSelectedItem(),selected)));
        }
    }


    private void opretPrisDialog(){
        if(lvwProdukt.getSelectionModel().getSelectedItem() == null)
            lblError.setText("du skal vælge et produkt, og en salgsituation, at sætte en pris for");

            else {
            PrisDialog prisDialog = new PrisDialog(lvwSalgssituation.getSelectionModel().getSelectedItem(), lvwProdukt.getSelectionModel().getSelectedItem());
            prisDialog.showAndWait();
            lvwSalgssituation.getItems().setAll(Controller.getSalgsSituation());
            int index = lvwSalgssituation.getItems().size() - 1;
            lvwSalgssituation.getSelectionModel().select(index);
            lvwSalgssituation.getItems().setAll(Controller.getSalgsSituation());
            lblError.setText("");
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



    private double getPris(Salgsituation salgsituation, Produkt produkt){
        double pris = 0;
        for(Pris p: salgsituation.getPrisArrayList())
            if(p.getProdukt().equals(produkt)){
               pris = p.getProduktPris();
                }

        return pris;
    }
}
