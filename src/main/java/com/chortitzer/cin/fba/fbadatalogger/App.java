package com.chortitzer.cin.fba.fbadatalogger;

import com.chortitzer.cin.fba.fbadatalogger.ui.main.MainView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends MvvmfxCdiApplication {

    private static final BorderPane root = new BorderPane();
    public static Stage mainStage;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void startMvvmfx(final Stage stage) throws Exception {

        /*final ViewTuple<MainView, MainViewModel> tuple
                = FluentViewLoader.fxmlView(MainView.class).load().getView();*/

        final Scene scene = new Scene(FluentViewLoader.fxmlView(MainView.class).load().getView());

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        mainStage = stage;
        //stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/cin-icon.png")));
        stage.setScene(scene);
        stage.show();
    }

}
