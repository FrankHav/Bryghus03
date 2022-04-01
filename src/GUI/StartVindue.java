package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

    public class StartVindue extends Application {

        @Override
        public void init() {

        }

        @Override
        public void start(Stage stage) {
            stage.setTitle("Aarhus bryghus");
            BorderPane pane = new BorderPane();
            this.initContent(pane);

            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setHeight(500);
            stage.setWidth(800);
            stage.show();
        }

        private void initContent(BorderPane pane) {
            TabPane tabPane = new TabPane();
            this.initTabPane(tabPane);
            pane.setCenter(tabPane);
        }

        private void initTabPane(TabPane tabPane) {
            tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

            Tab tabOpret = new Tab("Produkter");


            ProduktgruppePane opretProduktgruppePane = new ProduktgruppePane();
            tabOpret.setContent(opretProduktgruppePane);

            tabPane.getTabs().add(tabOpret);


        }

    }
