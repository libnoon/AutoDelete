/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fbauzac;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 *
 * @author noon
 */
public class AutoDelete {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Path directory = Paths.get(args[0]);
        System.out.format("Watching directory: %s%n", directory.toAbsolutePath());
        WatchService watchService = FileSystems.getFileSystem(Paths.get("/").toUri()).newWatchService();
        directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        while (true) {
            WatchKey watchKey = watchService.take();
            for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                WatchEvent.Kind kind = watchEvent.kind();
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    System.out.println("Overflow");
                    continue;
                }
                WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                Path relPath = watchEventPath.context();
                System.out.format("Entry created: %s%n", relPath);
                if (relPath.toString().equals("del")) {
                    Path fullPath = directory.resolve(relPath);
                    System.out.format("Deleting: %s%n", fullPath.toString());
                    Files.deleteIfExists(fullPath);
                }
            }
            watchKey.reset();
        }
    }

}
