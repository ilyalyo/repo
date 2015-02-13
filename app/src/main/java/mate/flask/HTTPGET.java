package mate.flask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;


public class HTTPGET extends AsyncTask<String, String, String> {

    /*@Override
    protected void onPreExecute() {}
    */
    @Override
    protected String doInBackground(String... urls) {
        String url = urls[0];
        BufferedReader in = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            URI website = new URI(url);
            request.setURI(website);
            HttpResponse response = httpclient.execute(request);
            in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            return in.readLine();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        return "";
    }

   /* @Override
    protected void  onPostExecute(String result){
        onPostExecute(result);
    }
    */
}
