package mate.flask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;


public class MyActivity extends Activity {

    public final static String EXTRA_MESSAGE = "mate.flask.MyActivity.user_id";
    private int USER_ID=25719572;

    public static dbInteraction db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        db=new dbInteraction(USER_ID);
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
                        try{
                            openStatistic(v,btn.getId());
                        }
                        catch(Exception e){
                            Log.e("log_tag", "Error in view " + e.toString());
                        }
                      /*  TextView textv = new TextView(v.getContext());
                        if (counter == 0)
                            init();
                        textv.setText(arr.get(counter).date.toString() + " | " + arr.get(counter).isOnline);
                        if(arr.get(counter++).isOnline)
                            textv.setBackgroundColor(Color.argb(150, 255, 92, 0));
                        ll.addView(textv);*/
                    }
                });
                ll.addView(web);
                ll.addView(btn);
                lmm.addView(ll);
            }
            lm.addView(lmm);

        }catch(Exception e) {
            Log.e("log_tag", "Error in myActivity " + e.toString());
        }
    }

    public void openStatistic(View view,int id) {
        Intent intent = new Intent(this, Statistic.class);
        intent.putExtra(EXTRA_MESSAGE, id);
        startActivity(intent);
    }

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
