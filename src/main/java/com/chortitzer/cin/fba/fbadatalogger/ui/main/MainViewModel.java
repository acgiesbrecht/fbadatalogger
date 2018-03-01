package com.chortitzer.cin.fba.fbadatalogger.ui.main;

import com.chortitzer.cin.fba.fbadatalogger.model.fba.HistoricoModel;
import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.*;
import java.time.LocalDateTime;

import static java.nio.file.StandardWatchEventKinds.*;

public class MainViewModel implements ViewModel {

    public ObservableList<HistoricoModel> itemsList = FXCollections.observableArrayList();

    HistoricoModel historicoModel;

    public void initialize() {
        initWatch();
    }

    private void initWatch() {
        Service<HistoricoModel> service = new Service<HistoricoModel>() {
            @Override
            protected Task<HistoricoModel> createTask() {
                return new Task<HistoricoModel>() {
                    @Override
                    protected HistoricoModel call() throws Exception {
                        try {
                            WatchService watcher = FileSystems.getDefault().newWatchService();
                            Path directory = Paths.get("C:/test");
                            directory.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

                            while (true) {
                                WatchKey key;
                                try {
                                    // wait for a key to be available
                                    key = watcher.take();
                                } catch (InterruptedException ex) {
                                    break;
                                }

                                for (WatchEvent<?> event : key.pollEvents()) {
                                    // get event type
                                    WatchEvent.Kind<?> kind = event.kind();

                                    // get file name
                                    @SuppressWarnings("unchecked")
                                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                                    //Path fileName = ev.context();

                                    Path fileName = directory.resolve(ev.context());

                                    System.out.println(kind.name() + ": " + fileName.toFile().getPath());

                                    historicoModel = new HistoricoModel();
                                    historicoModel.setFechahora(LocalDateTime.now());
                                    historicoModel.setArchivo(fileName.toString());
                                    historicoModel.setEvento(kind.toString());

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            itemsList.add(historicoModel);
                                        }
                                    });

                                    if (kind == OVERFLOW) {
                                        continue;
                                    } else if (kind == ENTRY_CREATE) {
                                        Thread.sleep(500);
                                        Boolean locked = true;
                                        Reader in = null;
                                        while (locked) {
                                            try {
                                                in = new FileReader(fileName.toFile());
                                                locked = false;
                                            } catch (FileNotFoundException ex) {
                                                Thread.sleep(250);
                                            }
                                        }

                                        Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').withHeader().parse(in);
                                        for (CSVRecord record : records) {
                                            historicoModel.setContenido(record.get("Fecha"));
                                            historicoModel.setContenido(historicoModel.getContenido().concat(" - ").concat(record.get("Receta")));
                                        }
                                        in.close();
                                        locked = true;
                                        while (locked) {
                                            try {
                                                Files.delete(fileName);
                                                locked = false;
                                            } catch (FileNotFoundException ex) {
                                                Thread.sleep(250);
                                            }
                                        }

                                    } else if (kind == ENTRY_DELETE) {

                                    } else if (kind == ENTRY_MODIFY) {

                                    }
                                }

                                // IMPORTANT: The key must be reset after processed
                                boolean valid = key.reset();
                                if (!valid) {
                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return historicoModel;
                    }
                };
            }
        };
        service.restart();
    }

    private void updateItemsList() {
        Service<ObservableList<HistoricoModel>> service = new Service<ObservableList<HistoricoModel>>() {
            @Override
            protected Task<ObservableList<HistoricoModel>> createTask() {
                return new Task<ObservableList<HistoricoModel>>() {
                    @Override
                    protected ObservableList<HistoricoModel> call() throws Exception {
                        // ObservableList<HistoricoModel> list = FXCollections.observableArrayList(dao.findAll());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //         itemsList.setAll(list);   //here you get the return value of your service
                            }
                        });
                        return null; //list;
                    }
                };
            }
        };
    }
}
