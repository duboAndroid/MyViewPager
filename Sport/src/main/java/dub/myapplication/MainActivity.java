package dub.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<ImageView> images;
    private int[] imageIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        imageIDs = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIDs.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(imageIDs[i]);
            images.add(imageView);
        }
        viewPager.setAdapter(new ViewPagerAdapter(images, null));
        //viewPager.setCurrentItem(viewPager.getAdapter().getCount() / 2);    // 滑到一半的地方
        handler.sendEmptyMessageDelayed(1, 3000);    // 3秒钟后显示下一页

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            handler.sendEmptyMessageDelayed(1, 3000);// 3秒钟后显示下一页
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(1);
    }
}
