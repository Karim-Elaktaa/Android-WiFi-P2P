package kelaktaa.android_wifi_p2p;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Karim on 21/01/2015.
 */
public class FileServerAsyncTask extends AsyncTask {
    private Context context;
    private TextView statusText;
    private String res = "wtf";
    private boolean isServer = false;

    public FileServerAsyncTask(Context context, View statusText, boolean isServer) {
        this.context = context;
        this.statusText = (TextView) statusText;
        this.isServer = isServer;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if(isServer) {
            launchServer();
        }
        else {
            launchClient();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if(isServer) {
            resServer();
        }
        else {
            resClient();
        }
    }

    public void launchServer () {
        try {
            Log.d("SERVER ON", "SERVER ON");

            ServerSocket serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();

            InputStream inputStream = client.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            inputLine = in.readLine();
            in.close();

            res = inputStream.toString();
            Log.d("SERVER ON", res);
            Log.d("SERVER ON", inputLine);
            res = inputLine;

            serverSocket.close();
        }
        catch(IOException e) {
            Log.e("AsyncTask", e.getMessage());
        }
    }

    public void resServer () {
            statusText.setText("RESULT - " + res);
    }

    public void launchClient () {

    }

    public void resClient () {
        statusText.setText("SEND - ");
    }
}

