import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable{

    private BufferedReader inputStream;
    private DataOutputStream outputStream;

    public Connector() {
        //Open Socket

        try {
            ServerSocket ssock = new ServerSocket(7789);
            System.out.println("Listening");

            Socket sock = ssock.accept();
            System.out.println("Connected");
        } catch (IOException e){

        }

    }

    @Override
    public void run() {
        try{
            //Listen here
            Socket clientSocket = new Socket("localhost", 6789);
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while(true){

                outputStream.writeBytes("heall");
                String response = inputStream.readLine();
                clientSocket.close();
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

    public void login(String playerName){
        try{
            outputStream.writeBytes("login " + playerName);
            String response = inputStream.readLine();
            // check if response is OK
            if (response.equals("OK")){

            }
            // maybe a response checker class
        } catch (IOException e){

        }

    }
}
