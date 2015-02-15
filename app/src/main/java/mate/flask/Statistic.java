package mate.flask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Statistic extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        TabHost tabs = (TabHost) findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");

        spec.setContent(R.id.tab1);
        spec.setIndicator("Статистика");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Аналитика");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        try {
            DrawGraph drawGraph = new DrawGraph(this);
            drawGraph.setBackgroundColor(Color.WHITE);
            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView_statistic);
            TextView textView = (TextView) findViewById(R.id.textView);
            DateFormat dateFormat = new SimpleDateFormat("dd.MM");
            Date date = new Date();
            textView.setText("Today is: " + dateFormat.format(date));
            scrollView.addView(drawGraph);
        }
        catch (Exception e)
        {
            Log.e("log_tag", "Error in statistic " + e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.statistic, menu);
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

    private class DrawGraph extends View {

        Paint p;
        Bitmap bitmap;
        int blockHeight=50;
        int height;
        ArrayList<DataRecord> dateRecords;

        public DrawGraph(Context context) {
            super(context);

            Intent intent = getIntent();

            int user_id= intent.getIntExtra(MyActivity.EXTRA_MESSAGE,-1);

            dateRecords=MyActivity.db.getDataInterval(user_id);

            bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);

            p = new Paint();

            height =dateRecords.size()*blockHeight+100;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // Compute the height required to render the view
            // Assume Width will always be MATCH_PARENT.
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height =dateRecords.size()*blockHeight+100; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            canvas.drawColor(Color.WHITE);

            height=50;
            int rect_x=10;
            int line_x=200;
            boolean lastStatus=! dateRecords.get(0).isOnline;
            int inSequence=0;

            for(int i =0;i<dateRecords.size();i++){
                DataRecord record=dateRecords.get(i);

                if(lastStatus!=record.isOnline) {
                    inSequence=0;

                    p.setTextSize(30);
                    String shortDate = new SimpleDateFormat("HH:mm").format(record.date);

                    p.setColor(Color.rgb(0, 0, 0));
                    canvas.drawText(shortDate, line_x + 20, height - 10, p);
                    p.setColor(Color.rgb(2,119,189));
                    canvas.drawLines(new float[]{rect_x, height , line_x + 100, height }, p);
                }
                else{
                    inSequence++;
                }
                if(inSequence<5){
                    Rect rect = new Rect(rect_x,height,rect_x+100,height+blockHeight);
                    if(record.isOnline)
                        p.setColor(Color.rgb(2,119,189));
                    else
                        p.setColor(Color.rgb(255, 255, 255));

                    p.setStyle(Paint.Style.FILL);
                    canvas.drawRect(rect, p);

                    height+=blockHeight;
                }

                lastStatus=record.isOnline;
            }
            p.setColor(Color.rgb(0, 0, 0));
            canvas.drawText("END", line_x + 20, height - 10, p);
            height+=100;
          //  this.requestLayout();
        }

    }
}
