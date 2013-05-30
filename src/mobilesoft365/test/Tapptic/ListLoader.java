package mobilesoft365.test.Tapptic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
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
 * User: misa
 * Date: 30.05.13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
public class ListLoader extends AsyncTask<Void, Void, ArrayList<ItemInList>> {

    private Activity activity;
    private ProgressDialog dialog;

    public ListLoader(Activity currentActivity){
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
    protected void onPostExecute(ArrayList<ItemInList> result) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        if (activity instanceof MainTappticActivity)
            ((MainTappticActivity)activity).updateItemList(result);
    }

    private void parseError(String text) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
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
    protected ArrayList<ItemInList> doInBackground(Void... voids) {
        ArrayList<ItemInList> itemList = new ArrayList<ItemInList>();

        URL url = null;
        try {
            url = new URL("http://dev.tapptic.com/test/json.php");
        } catch (MalformedURLException e) {
            return itemList;
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

                    JSONArray array = new JSONArray(total.toString());
                    for (int i=0; i<array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String name = object.getString("name");
                        Drawable image = fetchDrawable(object.getString("image"));
                        itemList.add(new ItemInList(name, image));
                    }

                }
                catch (IOException e) {
                    parseError("Parsing IOException");
                }
                catch (JSONException e) {
                    parseError("Parsing JSONException");
                }

            }
            else {
                parseError("Bad connection. Please check your connection or try again later");
            }
        }
        catch (IOException e) {
            parseError("Bad connection. Please check your connection or try again later");
        }

        try {
            Thread.sleep(3000,0);
        }
        catch (InterruptedException e){}

        return itemList;
    }
}
