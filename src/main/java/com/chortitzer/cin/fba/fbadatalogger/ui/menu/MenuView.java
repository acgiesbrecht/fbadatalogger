package com.chortitzer.cin.fba.fbadatalogger.ui.menu;

import com.chortitzer.cin.fba.fbadatalogger.ui.config.ConfigView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.inject.Inject;

public class MenuView implements FxmlView<MenuViewModel> {


    @InjectViewModel
    private MenuViewModel viewModel;

    @Inject
    private Stage primaryStage;

    @FXML
    void mnuConfig() {
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                return FluentViewLoader.fxmlView(ConfigView.class).load().getView();
            }
        };
        loadTask.setOnSucceeded(event -> {
            setView(loadTask.getValue());
        });
        new Thread(loadTask).start();
    }


    private void setView(Parent view) {
        BorderPane bp = (BorderPane) primaryStage.getScene().getRoot();
        bp.setCenter(view);
    }

}
