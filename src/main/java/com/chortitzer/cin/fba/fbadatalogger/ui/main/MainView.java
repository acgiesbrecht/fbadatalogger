package com.chortitzer.cin.fba.fbadatalogger.ui.main;

import com.chortitzer.cin.fba.fbadatalogger.model.fba.HistoricoModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MainView implements FxmlView<MainViewModel> {

    @FXML
    TableView<HistoricoModel> itemsTable;

    @InjectViewModel
    private MainViewModel viewModel;

    public void initialize() {
        itemsTable.setItems(viewModel.itemsList);
    }
}
