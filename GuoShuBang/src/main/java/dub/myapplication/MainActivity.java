package dub.myapplication;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BannerUtils bannerUtils;
    private ArrayList<ImageView> images;
    private int[] imageIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guo_shu_bang);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        imageIDs = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIDs.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(imageIDs[i]);
            images.add(imageView);
        }
        bannerUtils = new BannerUtils(this, images, viewPager, linearLayout);
    }
}
