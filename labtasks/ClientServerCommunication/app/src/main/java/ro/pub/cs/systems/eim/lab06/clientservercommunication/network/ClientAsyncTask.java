package ro.pub.cs.systems.eim.lab06.clientservercommunication.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedReader;
import java.net.Socket;

import ro.pub.cs.systems.eim.lab06.clientservercommunication.general.Constants;
import ro.pub.cs.systems.eim.lab06.clientservercommunication.general.Utilities;

public class ClientAsyncTask extends AsyncTask<String, String, Void> {

    private TextView serverMessageTextView;

    public ClientAsyncTask(TextView serverMessageTextView) {
        this.serverMessageTextView = serverMessageTextView;
    }

    @Override
    protected Void doInBackground(String... params) {

        String serverAddress = params[0];
        int serverPort = Integer.parseInt(params[1]);

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader bufferedReader = Utilities.getReader(socket);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                publishProgress(line);
            }

            socket.close();

            // ex 5 facut deja + d) student@eim:~$ ps a | while read x; do echo "$x" | nc -N -l 2000;

        } catch (Exception exception) {
            Log.e(Constants.TAG, "An exception has occurred: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        serverMessageTextView.setText("");

    }

    @Override
    protected void onProgressUpdate(String... values) {
        serverMessageTextView.append(values[0] + "\n");
    }

    @Override
    protected void onPostExecute(Void result) {}

}
