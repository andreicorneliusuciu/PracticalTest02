package practicaltest02.pdsd.systems.cs.pub.ro.practicaltest02;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String url;
    private TextView weatherForecastTextView;

    private Socket socket;

    public ClientThread(
            String address,
            int port,
            String url, TextView weatherForecastTextView) {
        this.address = address;
        this.port = port;
        this.url = url;
        this.weatherForecastTextView = weatherForecastTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {

            }

            Log.i("==CLIENT CONECTAT===", "\n\n===============================Clinet Conectat==================\n\n");

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {
                printWriter.println(address);
                printWriter.flush();
                printWriter.println(port);
                printWriter.flush();
                printWriter.println(url);
                printWriter.flush();
                String weatherInformation;
                while ((weatherInformation = bufferedReader.readLine()) != null) {
                    final String finalizedWeatherInformation = weatherInformation;
                    weatherForecastTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            weatherForecastTextView.append(finalizedWeatherInformation + "\n");
                        }
                    });
                }
            } else {

            }
            socket.close();
        } catch (IOException ioException) {

        }
    }

}
