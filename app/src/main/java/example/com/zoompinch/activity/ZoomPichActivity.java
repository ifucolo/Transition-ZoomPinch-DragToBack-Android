package example.com.zoompinch.activity;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.zoompinch.R;
import example.com.zoompinch.util.Util;
import example.com.zoompinch.widget.FrameTouch;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ZoomPichActivity extends AppCompatActivity implements FrameTouch.FrameOnTouch{

    @BindView(R.id.photo)
    ImageView photo;

    @BindView(R.id.backgroundZoom)
    FrameTouch frame;

    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_pich);
        overridePendingTransition(R.anim.enter_anim, R.anim.enter_anim);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        String imgUrl = intent.getStringExtra("url");
        String id = intent.getStringExtra("id");

        ViewCompat.setTransitionName(photo, id);

        mAttacher = new PhotoViewAttacher(photo);
        mAttacher.setMinimumScale(0.5f);
        mAttacher.setMaximumScale(mAttacher.getMaximumScale());
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);

        supportPostponeEnterTransition();
        Glide.with(this)
                .load(imgUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        photo.setImageDrawable(resource);
                        mAttacher.update();
                    }
                });

        frame.setFrameOnTouch(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAttacher.cleanup();
    }

    @Override
    public void onBackPressed() {
        mAttacher.cleanup();
        super.onBackPressed();
    }


    private double getDistance() {
        int distance = (int) Math.sqrt(photo.getTranslationY()*photo.getTranslationY() + photo.getTranslationX()*photo.getTranslationX());
        return Util.pxToDp(this, distance);
    }

    @Override
    public void onFrameTouchUp() {
        if (getDistance() > 50) {
            onBackPressed();
        } else {
            AnimatorSet animatorSet = new AnimatorSet();

            ObjectAnimator colorAnimator = ObjectAnimator.ofInt(frame, "backgroundColor", calcColor(), Color.BLACK);
            colorAnimator.setEvaluator(new ArgbEvaluator());

            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(photo, "translationX", 0),
                    ObjectAnimator.ofFloat(photo, "translationY", 0),
                    colorAnimator
            );

            animatorSet.start();
        }
    }

    @Override
    public void onScrollMovie(float x, float y) {
        if (mAttacher.getScale() <= 1) {
            frame.setBackgroundColor(calcColor());
            photo.setTranslationY(photo.getTranslationY() - y);
            photo.setTranslationX(photo.getTranslationX() - x);
        }
    }

    private int calcColor() {
        return Color.argb((int)Math.max(0,255 - getDistance() * 1.5), 0, 0, 0);
    }

}
