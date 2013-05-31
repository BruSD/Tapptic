package mobilesoft365.test.Tapptic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ScrollView;

/**
 * Created with IntelliJ IDEA.
 * User: BruSD
 * Date: 30.05.13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class TappDetailsActivity extends Activity {

    ScrollView scrollAlpha;
    Button OnOffAlfaButton;

    AlphaAnimation offAnimation;
    AlphaAnimation onAnimation;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tapp_details_layout);

        String name = getIntent().getStringExtra("tappname");

        OnOffAlfaButton = (Button)findViewById(R.id.on_off_button);

        OnOffAlfaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHideScroll();
            }
            });
        new ObjectLoader(TappDetailsActivity.this).execute(name);
    }

    public void ShowHideScroll(){
        scrollAlpha = (ScrollView)findViewById(R.id.scroll_alpha);
        if (OnOffAlfaButton.getText() == getText(R.string.off_text))     {

            offAnimation = new AlphaAnimation(1.0f, 0.0f);
            offAnimation.setDuration(2000);
            offAnimation.setFillAfter(true);
            scrollAlpha.startAnimation(offAnimation);

            offAnimation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationEnd(Animation arg0) {
                    OnOffAlfaButton.setEnabled(true);
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationStart(Animation arg0) {
                    // TODO Auto-generated method stub
                    OnOffAlfaButton.setText(R.string.on_text);
                    OnOffAlfaButton.setEnabled(false);
                }

            });
        } else{

            onAnimation = new AlphaAnimation(0.0f, 1.0f);
            onAnimation.setDuration(2000);
            onAnimation.setFillAfter(true);
            scrollAlpha.startAnimation(onAnimation);
            onAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    OnOffAlfaButton.setText(R.string.off_text);
                    OnOffAlfaButton.setEnabled(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    OnOffAlfaButton.setEnabled(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }


}
