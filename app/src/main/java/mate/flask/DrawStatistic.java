package mate.flask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Ilya_G on 06.03.2015.
 */
public class DrawStatistic  extends View {
    Paint p;
    Bitmap bitmap;
    int blockHeight=50;
    int width;
    int height;
    ArrayList<AnalyticRecord> dateRecords;

    public DrawStatistic(Context context,ArrayList<AnalyticRecord> records) {
        super(context);

        dateRecords=records;

        bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);

        p = new Paint();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("log_tag", "12");

        setMeasuredDimension(width, height);
    }

    /**
     * @param canvas
     * рисуем график
     */
    @Override
    protected void onDraw(Canvas canvas) {

        try{
            Log.e("log_tag", "in DrawStat ");
        canvas.drawColor(Color.WHITE);

        int k_width=width/dateRecords.size();

        for(int i =0;i<dateRecords.size()-1;i++){
            AnalyticRecord record=dateRecords.get(i);

            p.setColor(Color.rgb(2,119,189));

            p.setStyle(Paint.Style.FILL);
            Rect rect = new Rect(k_width*i,(int)(record.ratio*height),k_width*(i+1),1000);

            canvas.drawRect(rect, p);
            if(i%5==0)
                canvas.drawLines(new float[]{k_width*(i+1), 0 , k_width*(i+1), 1000}, p);
        }
            p.setColor(Color.rgb(0,0,0));
            canvas.drawLines(new float[]{0, 0 , 1000, 0}, p);
            canvas.drawLines(new float[]{0, (float)(height*0.1) ,1000,  (float)(height*0.1)}, p);

        }
        catch (Exception e){
            Log.e("log_tag", "Error in DrawStat " + e.toString());
        }
    }

}