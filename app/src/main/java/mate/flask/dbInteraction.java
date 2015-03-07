package mate.flask;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

//1
/**
 * Created by Ilya_G on 09.02.2015.
 * Класс для взаимодействия с БД
 */
public class dbInteraction {

    private int user_id;

    public dbInteraction(int id){
        this.user_id=id;
    }

    public boolean initialize(){
       // httpget=new HTTPGET();
      //  isDone=httpget.getStatus();
        return false;
    }

    /*
        Еще не реализовано
     */
    public boolean addVictim(int id){
        return false;
    }

    /*
        Еще не реализовано
     */
    public boolean delVictim(int id){
        return false;
    }

    /**
     * @return
     * Метод возвращает всех жертв( набор экземпляров класса User) текущего пользователя
     */
    public ArrayList<User> getMyVictims(){

        ArrayList<Integer> users=new ArrayList<Integer>();
        HTTPGET httpget=new HTTPGET();
        httpget.execute("http://cc25673.tmweb.ru/get_by_user.php?us=" + user_id );

        try{
            String jsonString = httpget.get();
            JSONParser parser=new JSONParser();
            Object obj = parser.parse(jsonString);
            JSONArray array = (JSONArray)obj;
            Iterator<JSONArray> iterator= array.iterator();

            VK vk=new VK();
            while (iterator.hasNext())
            {
                int id=Integer.parseInt(iterator.next().get(0).toString());
                users.add(id);
            }
            return vk.getUsers(users.toArray(new Integer[users.size()]));
        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in dbInteraction getMyVictims " + e.toString());
        }
        return null;
    }

    /**
     * @param victim_id
     * @return
     * Метод возвращает множество экземпляров AnalyticRecord
     * по заданному id
     */
    public ArrayList<AnalyticRecord> getAnalytic(int victim_id){

        ArrayList<AnalyticRecord> result=new ArrayList<AnalyticRecord>();
        HTTPGET httpget=new HTTPGET();
        try{
            httpget.execute("http://cc25673.tmweb.ru/get_analytic.php?victim=" + victim_id);

            String jsonString = httpget.get();

            Object obj= JSONValue.parse(jsonString);
            JSONArray jobj=(JSONArray)obj;
            for (int i =0;i<jobj.size();i++){
                org.json.simple.JSONObject jrecord= (org.json.simple.JSONObject)jobj.get(i);
                AnalyticRecord record=new AnalyticRecord(jrecord);
                result.add(record);
            }
        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in dbInteraction getAnalytic " + e.toString());
        }
        return result;
    }

    /**
     * @param victim_id
     * @return
     * Записи о активности пользователя за неделю
     */
    public ArrayList<DataRecord> getWeekDataInterval(int victim_id){
        java.util.Date dateTo = new java.util.Date();
        java.util.Date dateFrom = new java.util.Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L*7L);
        Timestamp to= new Timestamp(dateTo.getTime());
        Timestamp from= new Timestamp(dateFrom.getTime());

        return getDataInterval(victim_id,from,to);
    }

    /**
     * @param victim_id
     * @return
     * * Записи о активности пользователя за 24 часа
     */
    public ArrayList<DataRecord> getDataInterval(int victim_id){

        java.util.Date dateTo = new java.util.Date();
        java.util.Date dateFrom = new java.util.Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        Timestamp to= new Timestamp(dateTo.getTime());
        Timestamp from= new Timestamp(dateFrom.getTime());

        return getDataInterval(victim_id,from,to);
    }

    /**
     * @param victim_id
     * @param from
     * @param to
     * @return
     * Записи о активности пользователя за заданный промежуток времени
     */
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
