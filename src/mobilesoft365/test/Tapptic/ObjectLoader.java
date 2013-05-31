package mobilesoft365.test.Tapptic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: MediumMG
 * Date: 30.05.13
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
public class ObjectLoader extends AsyncTask<String, Void, ItemObject> {

    private Activity activity;
    private ProgressDialog dialog;

    public ObjectLoader(Activity currentActivity){
        this.activity = currentActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(activity);
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

    }

    @Override
    protected void onPostExecute(ItemObject result) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        if (activity instanceof TappDetailsActivity)
            ((TappDetailsActivity) activity).updateItem(result);
    }

    private void parseError(String text) {
        /*
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
        */
    }

    public static Drawable fetchDrawable(String imageURLString) {
        Drawable image = null;
        try {
            URL imageURL = new URL(imageURLString);
            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                image = Drawable.createFromStream(inputStream, "src");
            }
            else {
                image = null;
            }
        }
        catch (MalformedURLException e) {
            image = null;
        }
        catch (IOException e) {
            image = null;
        }

        return image;
    }


    @Override
    protected ItemObject doInBackground(String... strings) {

        ItemObject result = null;
        if (strings.length < 1)
            return result;

        URL url = null;
        try {
            url = new URL("http://dev.tapptic.com/test/json.php?name="+strings[0]);
        } catch (MalformedURLException e) {
            return result;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(stream));

                try {
                    // Read response until the end
                    String line = "";
                    StringBuilder total = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        total.append(line);
                    }

                    JSONObject jObject = new JSONObject(total.toString());

                    String name = jObject.getString("name");
                    String text = jObject.getString("text");
                    Drawable image = fetchDrawable(jObject.getString("image"));

                    result = new ItemObject(name, text, image);
                }
                catch (IOException e) {
                    parseError("Parsing IOException");
                }
                catch (JSONException e) {
                    parseError("Parsing JSONException");
                }

            }
            else {
                parseError("Bad connection. Please check your connection and try again later");
            }
        }
        catch (IOException e) {
            parseError("Bad connection. Please check your connection and try again later");
        }

        return result;
    }
}
