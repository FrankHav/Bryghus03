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

public class SalgssituationDialog extends Stage {

    public SalgssituationDialog() {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private Label lblError;
    private TextField txfNavn = new TextField();


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblNavn = new Label("Navn");
        pane.add(lblNavn, 0, 0);

        pane.add(txfNavn, 0, 1);

        Button btnOpret = new Button("Opret salgssituation");
        pane.add(btnOpret, 1, 0);
        btnOpret.setOnAction(event -> okAction());

    }

    public void okAction(){
        String navn = txfNavn.getText().trim();
        Controller.createSalgsSituation(navn);
        this.hide();
    }


}
