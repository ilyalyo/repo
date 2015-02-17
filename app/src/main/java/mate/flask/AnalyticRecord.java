package mate.flask;

import android.util.Log;

/**
 * Created by Ilya_G on 15.02.2015.
 * Экземпляр класса - определенный час {0-23}(можно будет сделать ENUM),
 * которому сопостовляется время проведенное в онлайне в этот час
 * относитльно времение проведенном в онлайне вобщем
 */
public class AnalyticRecord {

    int hour;
    double ratio;

    /**
     * @param json
     * вызывется из класса dbInteraction, объект типа :
     * [{"hours":"00","ratio":"0.0882"},{...},...]
     */
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
