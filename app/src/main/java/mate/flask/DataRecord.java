package mate.flask;


import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilya_G on 09.02.2015.
 * Экземпляр класса - запись хранящая в себе информацию о статусе пользователя isOnline
 * в определенный момент времени date
 */
public class DataRecord {

    public Timestamp date;

    //пока не сделано
    public boolean isMobile;

    public boolean isOnline;

    /**
     * @param json
     * на входе стркоа в json типа:
     *  [{"date":"2015-02-15 13:51:05","status":"0","device":""},{...},...]
     */
    public DataRecord(org.json.simple.JSONObject json){
        try{
            String device= json.get("device").toString();
            String date= json.get("date").toString();
            String status= json.get("status").toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = dateFormat.parse(date);

            this.date= new java.sql.Timestamp(parsedDate.getTime());

            this.isOnline="1".equals(status);
            //this.isMobile="1".equals(device);
        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in DataRecord " + e.toString());
        }
    }

    /*
    метод для вывода экземпляра класса в процессе отладки
     */
    public void print(){
        Log.e("DataRecord_Print", "date " + date.toString());
        Log.e("DataRecord_Print", "isOnline " + isOnline);
        Log.e("DataRecord_Print", "isMobile " + isMobile);
    }
}
