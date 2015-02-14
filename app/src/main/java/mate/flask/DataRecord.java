package mate.flask;


import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Ilya_G on 09.02.2015.
 */
public class DataRecord {

    public Timestamp date;

    public boolean isMobile;

    public boolean isOnline;

    public DataRecord(Timestamp date, boolean isOnline, boolean isMobile){
        this.date=date;
        this.isOnline=isOnline;
        this.isMobile=isMobile;
    }

    public DataRecord(JSONArray json){
        Iterator<String> iterator = json.iterator();
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(iterator.next());
            this.date= new java.sql.Timestamp(parsedDate.getTime());

            this.isOnline="1".equals(iterator.next());
            //this.isMobile=isMobile;
        }catch(Exception e){//this generic but you can control another types of exception
            Log.e("log_tag", "Error in json parse in DataRecord " + e.toString());
        }
    }

    public void print(){
        Log.e("DataRecord_Print", "date " + date.toString());
        Log.e("DataRecord_Print", "isOnline " + isOnline);
        Log.e("DataRecord_Print", "isMobile " + isMobile);
    }
}