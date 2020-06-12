import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

// This class deals with http request
public class WebProcessor implements Runnable {
    private PrintStream out;
    private InputStream input;

    public WebProcessor(Socket socket){
        try {
            input = socket.getInputStream();
            out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String fileName = parse(input);
            File file = new File(fileName);
            if (file.exists()) {
                this.sendOut(fileName);
            } else {
                this.sendError(404, "File not fund!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String parse(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String inputContent = in.readLine();
        String request[] = inputContent.split(" ");
        String method = request[0];
        String[] fileName = request[1].split("\\/");
        String httpVersion = request[2];
        System.out.println("request:");
        System.out.println(Arrays.deepToString(request));
        System.out.println("Method: " + method + ", file name: " + Arrays.deepToString(fileName) + ", HTTP version: " + httpVersion);
        return fileName[1];
    }

    public void sendOut(String fileName) throws IOException {
        List<String> fileStrList = MyFileIO.toStringList(fileName);

        StringBuilder content = new StringBuilder();
        for(String line : fileStrList){
            content.append(line+"\n");
        }
        String strCon = content.toString();

        out.println("HTTP/1.1 " + 200 + " " + "OK");
        out.println("Content-type:text/html");
        out.println();
        out.println(strCon);
        out.println();
        out.flush();
        out.close();
    }

    public void sendError(int errNum, String errMsg) {
        out.println("HTTP/1.1 " + errNum + " " + errMsg);
        out.println("Content-type:text/html");
        out.println();
        out.println("<html>");
        out.println("<meta content='text/html; charset=gb2312' http-equiv='Content-Type'/>");
        out.println("<head><title>Error " + errNum + "--" + errMsg + "</title></head>");
        out.println("<h1>" + errNum + " " + errMsg + "</h1>");
        out.println("</html>");
        out.println();
        out.flush();
        out.close();
        System.out.println(errNum+",,,,"+errMsg);
    }
}
