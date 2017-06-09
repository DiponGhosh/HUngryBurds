package com.learnandroid.dipon.hungryburds;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, Animation.AnimationListener {

    final int NUMBER_OF_BURDS = 10;
    final long AVERAGE_SHOW_TIME = 1000L;
    final long MINIMUM_SHOW_TIME = 500L;
    final long SHOW_TIME = 3000L;

    Random random = new Random();
    int countShown = 0, countClicked = 0;

    RelativeLayout relativeLayout;
    TextView textView;
    int displayWidth;
    int displayHeight;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private Thread thread;

    /* Activity Methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final MyActivity myActivity = this;


        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        //relativeLayout.setOnTouchListener(this);
        textView = (TextView) findViewById(R.id.textView);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;

       // Log.d("X-VALUE", displayWidth);
       // Log.d("Y-VALUE", displayHeight);

        prefs = getPreferences(MODE_PRIVATE);
        editor = prefs.edit();
    }


    //bujhi nai....bujhte hbe
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*public boolean onTouch(View v) {

    }*/

    @Override
    public void onResume () {
        super.onResume ();
        countClicked = countShown = 0;
        textView.setText(R.string.nothing);
        showABurd();

    }

    /* Game Methods */

    void showABurd(){

        //random thing
        long duration = SHOW_TIME ;

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.leftMargin = random.nextInt(displayWidth) * 7 / 8;
        params.topMargin = random.nextInt(displayHeight) * 4 / 5;

        ImageView burd = new ImageView(this);
        burd.setOnClickListener((View.OnClickListener) this);
        burd.setLayoutParams(params);
        burd.setImageResource(R.drawable.head_focus);
        burd.setVisibility(View.INVISIBLE);

        relativeLayout.addView(burd);

        AlphaAnimation animation = new AlphaAnimation(0.0F, 1.0F);
        animation.setDuration(duration);
        animation.setAnimationListener((Animation.AnimationListener) this);
        burd.setAnimation(animation);
    }

    //method to count score and display

    private void showScore() {
        int highsScore = prefs.getInt("highScore", 0);

        if(countClicked >= highsScore){
            highsScore = countClicked;
            editor.putInt("highScore", highsScore);
            editor.commit();
        }

        textView.setText("Your Score: " + countClicked + "\nHigh Score: " + highsScore);
    }

    /* onClickListener Method */

    public void onClick(View view){
        countClicked++;
        ( (ImageView) view ).setImageResource(R.drawable.head_hurt);
        view.setVisibility(View.INVISIBLE);
    }

    /* AnimationListener Methods */

    public void onAnimationEnd(Animation animation) {
        if (++countShown < NUMBER_OF_BURDS) {
            showABurd();
        } else {
            showScore();
        }
    }

    public void onAnimationRepeat(Animation arg0) { }
    public void onAnimationStart(Animation arg0)  { }
}
