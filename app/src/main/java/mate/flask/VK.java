package mate.flask;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.util.LinkedHashMap;

/**
 * Created by Ilya_G on 11.02.2015.
 */
public class VK {
    public User getUser(int id){
        HTTPGET httpget=new HTTPGET();
        httpget.execute("https://api.vk.com/method/users.get?user_id=" +id +"&fields=photo_50");
        try{
            String jsonString = httpget.get();

            Object obj= JSONValue.parse(jsonString);
            JSONObject jobj=(JSONObject)obj;
            Object units = (JSONArray) jobj.get("response");


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
