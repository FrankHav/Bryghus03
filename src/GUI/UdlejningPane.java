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
    private final ComboBox<String> boxBetalingForm = new ComboBox<>();


    private Label lblError;


    public UdlejningPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblUdlejningUdgående = new Label("Udgående udlejninger");
        this.add(lblUdlejningUdgående, 0, 0);
        Label lblUdlejningAfsluttet = new Label("Afsluttet udlejninger");
        this.add(lblUdlejningAfsluttet, 2, 0);

        this.add(lvwUdlejningerIkkeAfregnet, 0, 1);
        lvwUdlejningerIkkeAfregnet.getItems().setAll(Controller.getUdlejningerIkkeAfklaret());


        this.add(lvwUdlejningerAfregnet,2,1);
        lvwUdlejningerAfregnet.getItems().setAll(Controller.getUdlejningerAfklaret());

        lblError = new Label();
        this.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");

        Button btnOpretUdlejning = new Button("Opret udlejning");
        this.add(btnOpretUdlejning, 0, 4);
        btnOpretUdlejning.setOnAction(event -> opretUdlejningDialog());

        Button btnAfregnUdlejning= new Button("Afregn udlejning");
        this.add(btnAfregnUdlejning, 1, 2);

        this.add(boxBetalingForm, 1,1);
        boxBetalingForm.getItems().setAll("Kort", "Kontant", "Mobilepay");



    }


    private void afregnUdlejning(){

    }

    private void opretUdlejningDialog() {
        UdlejningDialog udlejningDialog = new UdlejningDialog();
        udlejningDialog.showAndWait();
        lvwUdlejningerIkkeAfregnet.getItems().setAll(Controller.getUdlejningerIkkeAfklaret());
    }
}
