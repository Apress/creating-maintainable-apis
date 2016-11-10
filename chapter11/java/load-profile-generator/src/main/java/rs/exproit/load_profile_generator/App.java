package rs.exproit.load_profile_generator;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;

import rs.exproit.load_profile_generator.protocol.LoadProfileRPC;

/**
 * Starts/stops this RPC service on a configurable port number.
 */
public final class App {
    private static final String DATA_FILE = "load_profiles.avro";
    private volatile Server server;
    private volatile LoadProfileRPCImpl loadProfileRPC;

    public App() throws IOException {
        loadProfileRPC = new LoadProfileRPCImpl(DATA_FILE);
    }
    
    public App(LoadProfileRPCImpl loadProfileRPC) throws IOException {
        this.loadProfileRPC = loadProfileRPC;        
    }

    /**
     * Starts the service if not already started. Trying to start a previously started service
     * does nothing.
     * @throws IOException if any error occurs during the startup.
     */
    public void start(int port) throws IOException {
        if (server == null) {
            server = new NettyServer(
                    new SpecificResponder(LoadProfileRPC.class, loadProfileRPC), 
                    new InetSocketAddress(port));        
        }
    }
    
    /**
     * Stops the service if it is running. Once stopped it cannot be restarted anymore.
     */
    public void stop() {
        if (server != null) {
            try {
                loadProfileRPC.shutdown();
            } finally {
                server.close();
            }
        }
    }

    /**
     * Get the port number on what the service is running.
     * @throws IllegalStateException if the service isn't running.
     */
    public int port() {
        if (server != null) {
            return server.getPort();
        } else {
            throw new IllegalStateException();
        }
    }
    
    /**
     * The main entry point of this service.
     * 
     * @param args the only command line argument is the port number.
     * @throws IOException if any error occurs during the startup.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("You need to specify the port number!");
            System.exit(1);
        }
        
        final App app = new App();
        int port = Integer.parseInt(args[0]);
        app.start(port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                app.stop();
            }
        });
        
        try {
            // We should wait until the Netty server starts up.
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
