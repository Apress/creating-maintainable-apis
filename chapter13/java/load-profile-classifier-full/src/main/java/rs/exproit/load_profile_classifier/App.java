package rs.exproit.load_profile_classifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Starts the LP Classifier (it will begin listening for events in an endless loop).
 */
public final class App {
    private static class EventProcessor implements Observer {
        @Override
        public void update(Observable o, Object arg) {
            System.out.println(arg);
        }      
    }

    /**
     * The main entry point of this service.
     * 
     * @param args the number of readers to start up (if it is bigger then the number
     * of available partitions, then some of them will be idle).
     * @throws IOException if any error occurs during the startup.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("You need to specify the number of consumers!");
            System.exit(1);
        }
        
        int numReaders = Integer.parseInt(args[0]);
        ExecutorService executor = Executors.newFixedThreadPool(numReaders);
        final List<LoadProfileReader> readers = new ArrayList<>();

        for (int i = 0; i < numReaders; i++) {
            LoadProfileReader reader = new LoadProfileReader();
            reader.addObserver(new EventProcessor());
            readers.add(reader);
            executor.submit(reader);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
          @Override
          public void run() {
              readers.forEach(LoadProfileReader::shutdown);
              executor.shutdown();
              try {
                  executor.awaitTermination(10, TimeUnit.SECONDS);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
        });
    }    
}
