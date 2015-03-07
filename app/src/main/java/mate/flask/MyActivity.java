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

/**
 * Первая страница приложения
 */
public class MyActivity extends Activity {

    //строка для передачи параметров в Statistic acivity
    public final static String EXTRA_MESSAGE = "mate.flask.MyActivity.user_id";

    // заглушка - текущий пользователь
    private int USER_ID=25719572;

    //объект для взаимодействия с БД
    public static dbInteraction db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        db=new dbInteraction(USER_ID);
        db.initialize();
        //получаем всех жертв текущего пользователя
        ArrayList<User> users=db.getMyVictims();
        //Визуализация списка жертв
        ScrollView lm = (ScrollView) findViewById(R.id.scrollView);
        final LinearLayout lmm = (LinearLayout) findViewById(R.id.linear);
        try{
            for(int i=0;i<users.size();i++)
            {

                final LinearLayout ll = new LinearLayout(this);

                ll.setOrientation(LinearLayout.VERTICAL);

                final Button btn = new Button(this);
                btn.setId(users.get(i).id);
                btn.setText(users.get(i).first_name +" " + users.get(i).last_name);

                WebView web = new WebView(this);
                web.loadUrl(users.get(i).photo);
                //костыль, фотки почему то мерцают
                web.setBackgroundColor(Color.argb(1, 0, 0, 0));

                btn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        try{
                            //переход на Statistic активити
                            openStatistic(v, btn.getId());
                        }
                        catch(Exception e){
                            Log.e("log_tag", "Error in MyActivity onClick " + e.toString());
                        }
                    }
                });
                ll.addView(web);
                ll.addView(btn);
                lmm.addView(ll);
            }
            lm.addView(lmm);
            Log.e("log_tag", "END");
        }catch(Exception e) {
            Log.e("log_tag", "Error in MyActivity " + e.toString());
        }
    }

    /**
     * @param view
     * @param id
     * открываем новую Activity
     */
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
