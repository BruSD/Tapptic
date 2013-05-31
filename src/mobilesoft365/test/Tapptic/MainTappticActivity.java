package mobilesoft365.test.Tapptic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTappticActivity extends Activity {

    ListView TappList;
    final String ATTRIBUTE_NAME = "name";
    final String ATTRIBUTE_IMAGE = "image";

    ArrayList<ItemInList> loadedData = new ArrayList<ItemInList>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartPhon();

    }
    public void StartPhon(){
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TappList = (ListView)findViewById(R.id.tapp_list_view);
        TappList.setItemsCanFocus(true);

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();



        // Использование собственного шаблона
        SimpleAdapter adapter = new SimpleAdapter(this,
                data, R.layout.tapp_items_layout,
                new String[] {ATTRIBUTE_NAME},
                new int [] { R.id.tapp_text});
        TappList.setAdapter(adapter);

        new ListLoader(MainTappticActivity.this).execute();
    }

    public void updateItemList(ArrayList<ItemInList> loadResult) {
        loadedData = loadResult;

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for(ItemInList item : loadResult) {
            Map mapObject = new HashMap<String, Object>();
            mapObject.put(ATTRIBUTE_NAME, item.getName());
            mapObject.put(ATTRIBUTE_IMAGE, item.getImage());
            data.add(mapObject);
        }

        TappAdapter adapter =  new TappAdapter(this,
                data, R.layout.tapp_items_layout,
                new String[] {ATTRIBUTE_NAME, ATTRIBUTE_IMAGE},
                new int [] { R.id.tapp_text, R.id.tapp_image});

        TappList.setAdapter(adapter);
    }


    class TappAdapter extends SimpleAdapter {
        private List<? extends Map<String, ?>> data;
        private Context context;

        public TappAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            final ViewHolder holder;

            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = li.inflate(R.layout.tapp_items_layout, parent, false);
                holder = new ViewHolder();

                holder.TappTitle = (TextView)convertView.findViewById(R.id.tapp_text);
                holder.TappImage = (ImageView)convertView.findViewById(R.id.tapp_image);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.TappTitle.setText((String) data.get(position).get(ATTRIBUTE_NAME));
            holder.TappImage.setImageDrawable((Drawable) data.get(position).get(ATTRIBUTE_IMAGE));


            TappList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), TappDetailsActivity.class);
                    intent.putExtra("tappname", (String)  data.get(i).get(ATTRIBUTE_NAME));
                    startActivity(intent);
                }

            });

            return convertView;
        }

        public class ViewHolder {
            TextView TappTitle;
            ImageView TappImage;
        }
    }
}
