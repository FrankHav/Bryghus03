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
            stage.setHeight(750);
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

            Tab tabRundvisning = new Tab("Rundvisning");
            RundvisningPane opretRundvisningPane = new RundvisningPane();
            tabRundvisning.setContent(opretRundvisningPane);
            tabPane.getTabs().add(tabRundvisning);

            Tab tabOrdre = new Tab("Ordre");
            OpretOrdrePane opretOrdrePane = new OpretOrdrePane();
            tabOrdre.setContent(opretOrdrePane);
            tabPane.getTabs().add(tabOrdre);

            Tab tabUdlejning = new Tab("Udlejning");
            UdlejningPane udlejningPane = new UdlejningPane();
            tabUdlejning.setContent(udlejningPane);
            tabPane.getTabs().add(tabUdlejning);

            Tab tabDagensSalg = new Tab("Dagens Salg");
            DagensSalgPane dagensSalgPanePane = new DagensSalgPane();
            tabDagensSalg.setContent(dagensSalgPanePane);
            tabPane.getTabs().add(tabDagensSalg);

            Tab tabKlip = new Tab("Klip statistik");
            KlipPane klipPane = new KlipPane();
            tabKlip.setContent(klipPane);
            tabPane.getTabs().add(tabKlip);


        }

    }
