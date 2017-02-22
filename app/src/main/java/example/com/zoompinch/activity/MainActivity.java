package example.com.zoompinch.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.zoompinch.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.photoOne)
    ImageView photoOne;

    @BindView(R.id.photoTwo)
    ImageView photoTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadImage(photoOne);
        loadImage(photoTwo);
    }

    private void loadImage(ImageView imageView) {
        Glide.with(this)
                .load(getString(R.string.img_url))
                .into(imageView);
    }
    private void transition(View view, String url, String id) {
        Intent intent = new Intent(this, ZoomPichActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("id", id);

        ViewCompat.setTransitionName(view, id);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, id);

        startActivity(intent, options.toBundle());
    }

    @OnClick(R.id.photoOne)
    public void onClickPhotoOne(View view){
        transition(photoOne, getString(R.string.img_url), getString(R.string.transition));
    }

    @OnClick(R.id.photoTwo)
    public void onClickPhotoTwo(View view){
        transition(photoTwo, getString(R.string.img_url), getString(R.string.transition));
    }
}
