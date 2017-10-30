package com.itheima20.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    private ViewPager viewPager;
    private TextView title;
    private int imageIDs[];
    private ArrayList<View> dots;
    private ArrayList<ImageView> images;
    private String titles[];
    private MyPagerAdapter adapter;

    private int oldPosition = 0;//��һ��ҳ��λ��
    private ScheduledExecutorService scheduledExecutor;
    private int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        // ͼƬ
        imageIDs = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIDs.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(imageIDs[i]);
            images.add(imageView);
        }
        // ����
        titles = new String[]{"���������ף��ҾͲ��ܵ���", "�����ֻ��������ٳ������ϸ������˴�ϳ�",
                "���ر�����Ӱ�������", "������TV�������", "��Ѫ��˿�ķ�ɱ"
        };

        //Բ��
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp);
        title = (TextView) findViewById(R.id.title);

        //����
        title.setText(titles[0]);

        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    private class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            //���µ�ǰҳ��
            currentItem = position;
            //����
            title.setText(titles[position % images.size()]);
            //Բ��
            dots.get(position % images.size()).setBackgroundResource(R.drawable.dot_focused);
            dots.get(oldPosition % images.size()).setBackgroundResource(R.drawable.dot_normal);
            oldPosition = position;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
//			return imageIDs.length;
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //�������
        @Override
        public Object instantiateItem(ViewGroup viewPager, int position) {
//			return super.instantiateItem(viewPager, position);
            viewPager.addView(images.get(position % images.size()));
            return images.get(position % images.size());
        }

        //ɾ������
        @Override
        public void destroyItem(ViewGroup viewPager, int position, Object object) {
//			super.destroyItem(container, position, object);
            viewPager.removeView(images.get(position % images.size()));
        }
    }

    private class MyPagerTask implements Runnable {

        @Override
        public void run() {
            //�л�ͼƬ
//			viewPager.setCurrentItem(item)
            currentItem++;
//			currentItem = (currentItem+1) % images.size();
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //ָ����ǰҳ��
            viewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleAtFixedRate(new MyPagerTask(), 2, 2, TimeUnit.SECONDS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //ֹͣ�л�
        scheduledExecutor.shutdown();
    }
}
