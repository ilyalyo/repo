package mate.flask;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilya_G on 09.02.2015.
 * Экземпляр класса - пользователь
 */
public class User {

    public int id;

    public String photo;

    public String first_name;

    public String last_name;

    public User(int id,String first_name,String last_name,String photo) {
        this.id=id;
        this.photo=photo;
        this.first_name=first_name;
        this.last_name=last_name;
    }
    /**
     * @param json
     * на входе стркоа в json типа:
     *  [{"id":"123123123","photo"http://vk..",...},{...},...]
     */
    public User(org.json.simple.JSONObject json){
        try{
            String id= json.get("uid").toString();
            this.photo= json.get("photo_50").toString();
            this.first_name= json.get("first_name").toString();
            this.last_name= json.get("last_name").toString();
            this.id=Integer.parseInt(id);
        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in User " + e.toString() +"|"+ json);
        }
    }


    public void print(){
        Log.e("DataRecord_Print", "id " + id);
        Log.e("DataRecord_Print", "photo " + photo);
        Log.e("DataRecord_Print", "first_name " + first_name);
        Log.e("DataRecord_Print", "last_name " + last_name);
    }
}
