package mate.flask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

/**
 * Created by Ilya_G on 21.03.2015.
 */
    class loadImg extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }


        @Override
        protected Bitmap doInBackground(String... params) {
            Log.e("log_tag", "load img");

            Bitmap image=null;

            try
            {
                image = downloadBitmap(params[0]);
            }
            catch(Exception e)
            {
                Log.e("ImageDownloader", "Something went wrong while" + params.toString());
                Log.e("ImageDownloader", "Something went wrong while" + params[0]);
                e.printStackTrace();
            }
            return image;
        }

        private Bitmap downloadBitmap(String url) {
            // initilize the default HTTP client object
            final DefaultHttpClient client = new DefaultHttpClient();
            Bitmap  image = null;
            //forming a HttoGet request
            final HttpGet getRequest = new HttpGet(url);
            try {

                HttpResponse response = client.execute(getRequest);

                //check 200 OK for success
                final int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Log.w("ImageDownloader", "Error " + statusCode +
                            " while retrieving bitmap from " + url);
                    return null;

                }

                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    try {
                        // getting contents from the stream
                        inputStream = entity.getContent();

                        // decoding stream data back into image Bitmap that android understands
                        image = BitmapFactory.decodeStream(inputStream);


                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            } catch (Exception e) {
                // You Could provide a more explicit error message for IOException
                getRequest.abort();
                Log.e("ImageDownloader", "Something went wrong while" +
                        " retrieving bitmap from " + url + e.toString());
            }

            return image;
        }
    }


