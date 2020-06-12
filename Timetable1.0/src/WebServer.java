import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private ServerSocket serverSocket;

    public void startServer(int port) {
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Web server started on: " + port);
            while(true) {
                Socket socket = serverSocket.accept();
                Thread processThread = new Thread(new WebProcessor(socket));
                processThread.start();
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
