package macbook.example.contact_inderjitsingh_c0771917_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity
{
    //Animations
    Animation topAnimation,middleAnimation,bottomAnimation;
    View blueline,greenline,redline,redline2,greenline2;
    TextView middletext,bottomText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks
        blueline = findViewById(R.id.blueline);
        greenline = findViewById(R.id.greenline);
        redline = findViewById(R.id.redline);
        greenline2 = findViewById(R.id.greenline2);
        redline2 = findViewById(R.id.redline2);

        bottomText = findViewById(R.id.bottomText);
        middletext = findViewById(R.id.middleText);

        blueline.setAnimation(topAnimation);
        redline.setAnimation(topAnimation);
        greenline.setAnimation(topAnimation);
        redline2.setAnimation(topAnimation);
        greenline2.setAnimation(topAnimation);

        bottomText.setAnimation(bottomAnimation);
        middletext.setAnimation(middleAnimation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivityIntent = new Intent(SplashActivity.this,ContactViewActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        },6000);


    }
}
