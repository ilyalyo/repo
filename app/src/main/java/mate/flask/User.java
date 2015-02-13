package mate.flask;

import android.util.Log;

/**
 * Created by Ilya_G on 09.02.2015.
 */
public class User {

    public int id;

    public String photo;

    public String first_name;

    public String last_name;

    public User(int id) {
        this.id = id;
    }

    public User(int id,String first_name,String last_name,String photo) {
        this.id=id;
        this.photo=photo;
        this.first_name=first_name;
        this.last_name=last_name;
    }
    public void print(){
        Log.e("DataRecord_Print", "id " + id);
        Log.e("DataRecord_Print", "photo " + photo);
        Log.e("DataRecord_Print", "first_name " + first_name);
        Log.e("DataRecord_Print", "last_name " + last_name);
    }
}
