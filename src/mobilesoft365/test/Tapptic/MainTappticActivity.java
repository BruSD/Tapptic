package mobilesoft365.test.Tapptic;

import android.app.Activity;
import android.os.Bundle;

public class MainTappticActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new ListLoader(MainTappticActivity.this).execute();
    }
}
