package mate.flask;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

/**
 * Created by Ilya_G on 11.02.2015.
 * Класс для взаимодействия с VK
 */
public class VK {
    public ArrayList<User> getUsers(Integer [] ids ){

        ArrayList<User> users=new ArrayList<User>();

        HTTPGET httpget=new HTTPGET();
        String url="https://api.vk.com/method/users.get?user_ids=";

        for(int i=0;i<ids.length-1;i++)
            url+= ids[i] +",";
        url+=ids[ids.length-1]+"&fields=photo_50";
        httpget.execute(url);

        try{
            String jsonString = httpget.get();

            Object obj= JSONValue.parse(jsonString);
            JSONObject jobj1=(JSONObject)obj;
            Object units = jobj1.get("response");
            JSONArray jobj=(JSONArray)units;
            for (int i =0;i<jobj.size();i++){
                org.json.simple.JSONObject jrecord= (org.json.simple.JSONObject)jobj.get(i);
                User record=new User(jrecord);
                users.add(record);
            }

             /*   Object obj= JSONValue.parse(jsonString);
                JSONObject jobj=(JSONObject)obj;
                Object units = jobj.get("response");

                String first_name=((JSONObject)((JSONArray)units).get(0)).get("first_name").toString();
                String last_name=((JSONObject)((JSONArray)units).get(0)).get("last_name").toString();
                String photo=((JSONObject)((JSONArray)units).get(0)).get("photo_50").toString();

                users.add( new User(ids[1],first_name,last_name,photo));*/
        return users;

        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in VK" + e.toString());
        }
        return null;
    }

    public User getUser(int id){

        HTTPGET httpget=new HTTPGET();
        httpget.execute("https://api.vk.com/method/users.get?user_id=" +id +"&fields=photo_50");
        try{

            String jsonString = httpget.get();
            Object obj= JSONValue.parse(jsonString);
            JSONObject jobj=(JSONObject)obj;
            Object units = jobj.get("response");

            String first_name=((JSONObject)((JSONArray)units).get(0)).get("first_name").toString();
            String last_name=((JSONObject)((JSONArray)units).get(0)).get("last_name").toString();
            String photo=((JSONObject)((JSONArray)units).get(0)).get("photo_50").toString();

            return new User(id,first_name,last_name,photo);

        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in VK" + e.toString());
        }
        return null;
    }
}
