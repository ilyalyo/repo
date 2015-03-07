package mate.flask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Класс представляет из себя страницы Статистики и Аналитики
 */
public class Statistic extends Activity {

    //информация о выбранном пользователе
    static ArrayList<DataRecord> dateRecords;

    //ID выбранного пользователя
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        //получаем id параметр переданный из MyActivity
        Intent intent = getIntent();
        user_id= intent.getIntExtra(MyActivity.EXTRA_MESSAGE,-1);

        //получаем записи за 24 часа
        dateRecords=MyActivity.db.getDataInterval(user_id);

        //реализация 2х закладок
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

        //рисуем график аналитики на странице Аналитики
        try {
            DrawGraph drawGraph = new DrawGraph(this);
            drawGraph.setBackgroundColor(Color.WHITE);

            //пишем текущее число
            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView_statistic);

            TextView textView = (TextView) findViewById(R.id.textView);
            DateFormat dateFormat = new SimpleDateFormat("dd.MM");
            textView.setText("Today is: " + dateFormat.format(new Date()));

            scrollView.addView(drawGraph);


            // строим содержимое вкладки Статистика
            //GraphView graph = (GraphView) findViewById(R.id.graph);

            LinearLayout scrollView2 = (LinearLayout) findViewById(R.id.tab2);
            ArrayList<AnalyticRecord> dataAnalytic= MyActivity.db.getAnalytic(user_id);

            Log.e("log_tag", "1");
            DrawStatistic drawStat = new DrawStatistic(this,dataAnalytic);
            Log.e("log_tag", "1");
            drawStat.setBackgroundColor(Color.WHITE);
            //scrollView.addView(drawStat);
            scrollView2.addView(drawStat);
/*
            DataPoint[] analyticArray=new DataPoint[dataAnalytic.size()];
            double max=0;
            for(int i=0;i<dataAnalytic.size();i++){
                analyticArray[i]=   new DataPoint(dataAnalytic.get(i).hour, dataAnalytic.get(i).ratio);
                if(dataAnalytic.get(i).ratio>max)
                    max=dataAnalytic.get(i).ratio;
            }

            //фиксированные границы у графика
            graph.getViewport().setMinX(0);
            graph.getViewport().setMinY(0);

            graph.getViewport().setMaxX(23);
            graph.getViewport().setMaxY(max+0.1);

            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setXAxisBoundsManual(true);

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(analyticArray);
            graph.addSeries(series);*/
        }
        catch (Exception e)
        {
            Log.e("log_tag", "Error in Statistic " + e.toString());
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

    /**
     * Класс для рисования графика на вкладке Статистика
     */
    private class DrawGraph extends View {

        Paint p;
        Bitmap bitmap;
        int blockHeight=50;
        int height;
        ArrayList<DataRecord> dateRecords;

        public DrawGraph(Context context) {
            super(context);
            Log.e("log_tag", "11");
            dateRecords=Statistic.dateRecords;

            bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);

            p = new Paint();

            //приблизительная длина простыни графика, надо что то с этим поделать=(
            height =dateRecords.size()*blockHeight+100;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            Log.e("log_tag", "12");
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height =dateRecords.size()*blockHeight+100;
            setMeasuredDimension(width, height);
        }

        /**
         * @param canvas
         * рисуем график
         */
        @Override
        protected void onDraw(Canvas canvas) {
            Log.e("log_tag", "13");
            canvas.drawColor(Color.WHITE);

            //отступ сверху
            height=50;
            //отступ слева для прямоугольников
            int rect_x=10;
            //ширина прямоугольников
            int line_x=200;

            boolean lastStatus=! dateRecords.get(0).isOnline;
            //сколько было записей было подряд без изменения статуса
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
