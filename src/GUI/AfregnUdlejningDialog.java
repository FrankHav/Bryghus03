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

public class AfregnUdlejningDialog extends Stage {
    private final Ordre ordre;
    private final ListView<OrdreLinje> lvwOrdreLinje = new ListView<>();
    private final TextField txfAntal = new TextField();
    private final TextField txfSamletPris = new TextField();
    private final ComboBox<String> boxBetalingForm = new ComboBox<>();
    private Label lblError;
    double sum = 0;


    public AfregnUdlejningDialog(Ordre ordre){
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.ordre=ordre;

        GridPane pane = new GridPane();
        this.initContent(pane);


        Scene scene = new Scene(pane);
        this.setScene(scene);
    }
    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);


        pane.add(lvwOrdreLinje,1,0,1,1);
        lvwOrdreLinje.getItems().setAll(ordre.getOrdreLinjeArrayList());
        ChangeListener<OrdreLinje> listener = (ov, o, n) -> this.selectionChanged();
        lvwOrdreLinje.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblAntal = new Label("Antal brugte produkter");
        pane.add(lblAntal,2,1);
        pane.add(txfAntal, 2, 2);

        Label lblSamletPris = new Label("Samlet pris: ");
        pane.add(lblSamletPris,3,1);
        pane.add(txfSamletPris,3,2);
        txfSamletPris.setEditable(false);

        Button btnOK = new Button("Ok");
        pane.add(btnOK,2,3);
        btnOK.setOnAction(event -> okAction());

        pane.add(boxBetalingForm, 1,1);
        boxBetalingForm.getItems().setAll("Kort", "Kontant", "Mobilepay");

        Button btnAfslut = new Button("Afslut");
        pane.add(btnAfslut,3,3);
        btnAfslut.setOnAction(event -> afslutAction());

        lblError = new Label();
        pane.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");


    }

    private void afslutAction(){
        ordre.setBetalingsForm(boxBetalingForm.getValue());
        if (ordre.getBetalingsForm()==null)
            lblError.setText("Du skal vælge betalingsform!");
        else
            this.hide();
    }

    private void okAction(){
        int antal = Integer.parseInt(txfAntal.getText().trim());

        OrdreLinje selected = lvwOrdreLinje.getSelectionModel().getSelectedItem();
        double calc = antal * selected.getPris().getProduktPris();
        txfSamletPris.setText(String.valueOf(sum += calc - selected.individueltPant()));
        lvwOrdreLinje.getItems().setAll(ordre.getOrdreLinjeArrayList());
    }

    private void selectionChanged() {
        OrdreLinje selected = lvwOrdreLinje.getSelectionModel().getSelectedItem();
        if (selected != null) {
        }
    }
}
