package ro.pub.cs.systems.eim.lab06.singlethreadedserver.network;

import android.util.Log;
import android.widget.EditText;

import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.lab06.singlethreadedserver.general.Constants;
import ro.pub.cs.systems.eim.lab06.singlethreadedserver.general.Utilities;

public class CommunicationThread extends Thread {

    private final Socket socket;
    private final EditText serverTextEditText;

    public CommunicationThread(Socket socket, EditText serverTextEditText) {
        this.socket = socket;
        this.serverTextEditText = serverTextEditText;
    }

    @Override
    public void run() {
        try {
            // simulare întârziere 3 secunde
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            PrintWriter printWriter = Utilities.getWriter(socket);
            printWriter.println(serverTextEditText.getText().toString());

            socket.close();
            Log.v(Constants.TAG, "Connection handled on separate thread");

        } catch (Exception e) {
            Log.e(Constants.TAG, "CommunicationThread error: " + e.getMessage());
            if (Constants.DEBUG) {
                e.printStackTrace();
            }
        }
    }
}
