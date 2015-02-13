package mate.flask;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        VK vk=new VK();
        User user=vk.getUser(25719572);
        user.print();
        final dbInteraction db=new dbInteraction(25719572);
        db.initialize();

        ArrayList<User> users=db.getMyVictims();
        db.getDataInterval(10379683);


        //final LinearLayout lm = (LinearLayout) findViewById(R.id.linear);
        ScrollView lm = (ScrollView) findViewById(R.id.scrollView);
        final LinearLayout lmm = (LinearLayout) findViewById(R.id.linear);
        try{
            for(int i=0;i<users.size();i++)
            {
                // Create LinearLayout
                final LinearLayout ll = new LinearLayout(this);

                ll.setOrientation(LinearLayout.VERTICAL);
                // Create Button
                final Button btn = new Button(this);
                btn.setId(users.get(i).id);
                btn.setText(users.get(i).first_name +" " + users.get(i).last_name);

                WebView web = new WebView(this);
                web.loadUrl(users.get(i).photo);
                web.setBackgroundColor(Color.argb(1, 0, 0, 0));
                btn.setOnClickListener(new View.OnClickListener() {
                    int counter = 0;
                    ArrayList<DataRecord> arr;

                    private void init() {
                        arr = new ArrayList<DataRecord>();
                        try {
                            arr = db.getDataInterval(btn.getId());
                        } catch (Exception e) {
                            Log.e("log_tag", "Error in view " + e.toString());
                        }
                    }

                    public void onClick(View v) {
                        TextView textv = new TextView(v.getContext());
                        if (counter == 0)
                            init();
                        textv.setText(arr.get(counter).date.toString() + " | " + arr.get(counter).isOnline);
                        if(arr.get(counter++).isOnline)
                            textv.setBackgroundColor(Color.argb(150, 255, 92, 0));
                        ll.addView(textv);
                    }
                });

                ll.addView(web);
                ll.addView(btn);
                lmm.addView(ll);
            }
            lm.addView(lmm);

        }catch(Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        /*
        HTTPGET h=new HTTPGET();
        TextView textv = (TextView)findViewById(R.id.textView);
        try{
            textv.setText(  h.execute("12").get());
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
        }
        */
    }
    /*
    public void changText() {
        TextView textv = (TextView)findViewById(R.id.textView);
        textv.setText("Text Has Been Changed");
        BufferedReader in = null;
        String data = null;
    }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
