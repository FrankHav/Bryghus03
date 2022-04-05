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

public class OpretOrdrePane extends GridPane {
    private final ListView<Ordre> lvwOrdre = new ListView<>();
    private final ListView<OrdreLinje> lvwOrdreInfo = new ListView<>();
    private Label lblError;


    public OpretOrdrePane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblOrdre = new Label("Ordre");
        this.add(lblOrdre, 0, 0);

        this.add(lvwOrdre, 0, 1);
        lvwOrdre.getItems().setAll(Controller.getOrdrer());
        ChangeListener<Ordre> listener = (ov, o, n) -> this.selectionChanged();
        lvwOrdre.getSelectionModel().selectedItemProperty().addListener(listener);


        lblError = new Label();
        this.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");

        this.add(lvwOrdreInfo, 1, 1);



        Button btnOpretOrdre = new Button("Nyt salg");
        this.add(btnOpretOrdre, 0, 2);
        btnOpretOrdre.setOnAction(event -> opretOrdreDialog());


    }

    private void selectionChanged() {
        Ordre selected = lvwOrdre.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwOrdreInfo.getItems().setAll(selected.getOrdreLinjeArrayList());
        }
    }



    private void opretOrdreDialog() {
        OrdreDialog ordreDialog = new OrdreDialog(null);
        ordreDialog.showAndWait();
        lvwOrdre.getItems().setAll(Controller.getOrdrer());
        int index = lvwOrdre.getItems().size()-1;
        lvwOrdre.getSelectionModel().select(index);

    }

}
