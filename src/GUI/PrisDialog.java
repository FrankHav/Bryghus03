package GUI;

import Controller.Controller;
import Model.Produkt;
import Model.Salgsituation;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrisDialog extends Stage {
    private final Salgsituation salgsituation;
    private final Produkt produkt;
    public PrisDialog(Salgsituation salgsituation, Produkt produkt) {
        this.salgsituation = salgsituation;
        this.produkt = produkt;
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private Label lblError;
    private TextField txfPris = new TextField();
    private TextField txfAntalKlip = new TextField();


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblNavn = new Label("Pris");
        pane.add(lblNavn, 0, 0);

        pane.add(txfPris, 1, 1);
        pane.add(txfAntalKlip, 1, 2);

        Button btnOpret = new Button("Opret salgssituation");
        pane.add(btnOpret, 1, 0);
        btnOpret.setOnAction(event -> okAction());

    }

    public void okAction(){
        int pris = Integer.parseInt(txfPris.getText().trim());
        int antalKlip = Integer.parseInt(txfAntalKlip.getText().trim());
        salgsituation.createPris(pris, antalKlip,produkt);
        this.hide();
    }

}
