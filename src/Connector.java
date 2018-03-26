import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable{


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
            while(true){
                String sentence;
                String modifiedSentence;

                Socket clientSocket = new Socket("localhost", 6789);
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                outputStream.writeBytes("heall");
                modifiedSentence = inputStream.readLine();
                System.out.println("FROM SERVER: " + modifiedSentence);
                clientSocket.close();
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
}
