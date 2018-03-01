package com.chortitzer.cin.fba.fbadatalogger.ui.config;

import de.saxsys.mvvmfx.ViewModel;

import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class ConfigViewModel implements ViewModel {

    public void initialize() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get("C:\test");
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            while (true) {
                WatchKey key;
                try {
                    // wait for a key to be available
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    // get event type
                    WatchEvent.Kind<?> kind = event.kind();

                    // get file name
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    System.out.println(kind.name() + ": " + fileName);

                    if (kind == OVERFLOW) {
                        continue;
                    } else if (kind == ENTRY_CREATE) {
                        // process create event
                    } else if (kind == ENTRY_DELETE) {
                        // process delete event
                    } else if (kind == ENTRY_MODIFY) {
                        // process modify event
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
    }

}
