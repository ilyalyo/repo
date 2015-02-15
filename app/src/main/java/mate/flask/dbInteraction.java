package mate.flask;

import android.os.AsyncTask;
import android.util.Log;


//import org.json.JSONObject;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ilya_G on 09.02.2015.
 */
public class dbInteraction {

    private int user_id;

  //  private HTTPGET httpget;

//    public AsyncTask.Status isDone;

    public dbInteraction(int id){
        this.user_id=id;
    }

    public boolean initialize(){
       // httpget=new HTTPGET();
      //  isDone=httpget.getStatus();
        return false;
    }

    public boolean addVictim(int id){
        return false;
    }

    public boolean delVictim(int id){
        return false;
    }

    public ArrayList<User> getMyVictims(){

        ArrayList<User> result=new ArrayList<User>();
        HTTPGET httpget=new HTTPGET();
        httpget.execute("http://cc25673.tmweb.ru/get_by_user.php?us=" + user_id );

        try{
            String jsonString = httpget.get();
            JSONParser parser=new JSONParser();
            Object obj = parser.parse(jsonString);
            JSONArray array = (JSONArray)obj;
            Log.e("log_tag_1", array.toString());
            Iterator<JSONArray> iterator= array.iterator();

            VK vk=new VK();
            while (iterator.hasNext())
            {
                int id=Integer.parseInt(iterator.next().get(0).toString());
                result.add(vk.getUser(id));
                //(new User(Integer.parseInt(iterator.next().get(0).toString())));
            }
        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in getMyVictims " + e.toString());
        }
        return result;
    }

    public ArrayList<DataRecord> getDataInterval(int victim_id){

        java.util.Date dateTo = new java.util.Date();
        java.util.Date dateFrom = new java.util.Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        Timestamp to= new Timestamp(dateTo.getTime());
        Timestamp from= new Timestamp(dateFrom.getTime());

        return getDataInterval(victim_id,from,to);
    }

    public ArrayList<DataRecord> getDataInterval(int victim_id, Timestamp from, Timestamp to){

        ArrayList<DataRecord> result=new ArrayList<DataRecord>();
        HTTPGET httpget=new HTTPGET();
        try{
            String url_from= URLEncoder.encode( from.toString() ,"UTF-8");
            String url_to= URLEncoder.encode( to.toString() ,"UTF-8");
            httpget.execute("http://cc25673.tmweb.ru/get_by_user.php?us=" + user_id +"&ac="+ victim_id+"&FROM="+ url_from+"&TO="+ url_to);

            String jsonString = httpget.get();

            Object obj= JSONValue.parse(jsonString);
            JSONArray jobj=(JSONArray)obj;
            for (int i =0;i<jobj.size();i++){
                org.json.simple.JSONObject jrecord= (org.json.simple.JSONObject)jobj.get(i);
                DataRecord record=new DataRecord(jrecord);
                result.add(record);
            }
        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in getDataInterval " + e.toString());
        }
        return result;
    }
}
