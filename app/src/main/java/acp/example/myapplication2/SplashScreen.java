package acp.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import acp.example.myapplication2.Logic.FlipperAdapterI;

public class SplashScreen extends AppCompatActivity {

    private AdapterViewFlipper adapterViewFlipper;
    //private static final String[] novoTxt = { "Receitas Doces", "Receitas Salgadas" };
    private static final int[] novaImg = { R.drawable.doces, R.drawable.salgadas };
    TextView txtLogo1, txtLogo2, text_view_progress;
    RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    Animation animation_in, animation_out;
    Animation fade_in, fade_out;
    private static int SPLASH_TIME = 3000;
    private int progr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 50;

        adapterViewFlipper = findViewById(R.id.viewFlipperS);
        FlipperAdapterI flipperAdapter = new FlipperAdapterI(this, novaImg/*, novoTxt*/ );

        adapterViewFlipper.setAdapter(flipperAdapter);
        adapterViewFlipper.setAutoStart(true);
        adapterViewFlipper.setFlipInterval(2000);

        txtLogo1 = findViewById(R.id.txtLogo1);
        txtLogo2 = findViewById(R.id.txtLogo2);
        relativeLayout = findViewById(R.id.relativeLayoutS);
        progressBar = findViewById(R.id.progress_bar);
        text_view_progress = findViewById(R.id.text_view_progress);

        ShimmerFrameLayout container1 =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        container1.startShimmer();
        ShimmerFrameLayout container2 =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container2);
        container2.startShimmer();

        txtLogo1.setVisibility(View.INVISIBLE);
        txtLogo2.setVisibility(View.INVISIBLE);

        animation_in = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        animation_out = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        txtAnimation();

        getSupportActionBar().hide();

        final Handler handler = new Handler();
        handler.postDelayed( new Runnable()
        {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.GONE);
                relativeLayout.setAnimation(fade_in);
                if(progr <= 100){
                    text_view_progress.setText("" + progr + "%");
                    progressBar.setProgress(progr);
                    progr = progr + 5;
                    handler.postDelayed(this, 200);
                } else {
                    handler.removeCallbacks(this);
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                }

            }
        }, SPLASH_TIME);

    }

    private void txtAnimation() {
        Animation txt = new AlphaAnimation(0.00f, 1.00f);
        txt.setDuration(SPLASH_TIME);
        txt.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                txtLogo1.setVisibility(View.INVISIBLE);
                txtLogo2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtLogo1.setVisibility(View.VISIBLE);
                txtLogo2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        txtLogo1.startAnimation(txt);
        txtLogo2.startAnimation(txt);
    }

}