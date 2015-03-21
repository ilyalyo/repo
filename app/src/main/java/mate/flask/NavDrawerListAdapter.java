package mate.flask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ilya_G on 21.03.2015.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> navDrawerItems;
    private ArrayList<Bitmap> navDrawerBitmaps;

    public NavDrawerListAdapter(Context context, ArrayList<User> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        this.navDrawerBitmaps=new ArrayList<Bitmap>();
        try{
            for (int i=0; i<this.navDrawerItems.size();i++){
                loadImg li=new loadImg();

                li.execute(this.navDrawerItems.get(i).photo);
                    navDrawerBitmaps.add(li.get());
                }
            }
        catch (Exception e){
            Log.e("log_tag", "Enter adapter"+e.toString());
        }
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.drawer_nav_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        //imgIcon.setBackgroundColor(Color.argb(1, 0, 0, 0));

        imgIcon.setImageBitmap(navDrawerBitmaps.get(position));
        txtTitle.setText(navDrawerItems.get(position).toString());

        return convertView;
    }


}