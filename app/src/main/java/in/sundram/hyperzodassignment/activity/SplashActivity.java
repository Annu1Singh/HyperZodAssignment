package in.sundram.hyperzodassignment.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import in.sundram.hyperzodassignment.utils.IntentUtils;
import in.sundram.hyperzodassignment.R;

public class SplashActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private Animation animation_fadeIn, animation_fadeOut;
    private Context context = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            init();
            Glide.with(context).load(R.drawable.food_banner_two).into(imageView);
            animation_fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            animation_fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            textView.startAnimation(animation_fadeOut);
            imageView.startAnimation(animation_fadeIn);
            handler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        textView = findViewById(R.id.txt_v);
        imageView = findViewById(R.id.image_view);
    }

    private void handler() {
        try {
            new Handler().postDelayed(() -> IntentUtils.CommonIntentWithFadeInAndFadeOutTransitionAndFinishAffinity(context, MainActivity.class), 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}