package mate.flask;

import android.util.Log;

/**
 * Created by Ilya_G on 15.02.2015.
 */
public class AnalyticRecord {
    int hour;
    double ratio;

    public AnalyticRecord(int hour,double ratio){
        this.hour=hour;
        this.ratio=ratio;
    }
    public AnalyticRecord(org.json.simple.JSONObject json){
        try{
            String hour= json.get("hours").toString();
            String ratio= json.get("ratio").toString();
            this.hour= Integer.parseInt(hour);
            this.ratio=Double.parseDouble(ratio);
        }catch(Exception e){
            Log.e("log_tag", "Error in json parse in AnalyticRecord " + e.toString());
        }
    }

}
