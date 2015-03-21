package mate.flask;

/**
 * Created by Ilya_G on 21.03.2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Класс для рисования графика на вкладке Статистика
 */
public class DrawGraph extends View {

    Paint p;
    Bitmap bitmap;
    int blockHeight=50;
    int height;
    ArrayList<DataRecord> dateRecords;

    public DrawGraph(Context context,  ArrayList<DataRecord> dateRecords) {
        super(context);
        this.dateRecords=dateRecords;

        bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);

        p = new Paint();

        //приблизительная длина простыни графика, надо что то с этим поделать=(
        height =dateRecords.size()*blockHeight+100;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height =calcHeight();
        setMeasuredDimension(width, height);
    }
    private int calcHeight(){
        int result=50;

        boolean lastStatus=! dateRecords.get(0).isOnline;
        //сколько было записей было подряд без изменения статуса
        int inSequence=0;

        for(int i =0;i<dateRecords.size();i++){
            DataRecord record=dateRecords.get(i);

            if(lastStatus!=record.isOnline) {
                inSequence=0;
            }
            else{
                inSequence++;
            }
            if(inSequence<5){
                result+=blockHeight;
            }
            lastStatus=record.isOnline;
        }

        return result;
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