package dub.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager自动轮播
 */
public class BannerUtils {

    private List<ImageView> banners;//链接集合
    private ArrayList<ImageView> imgPaths;//链接集合
    private ViewPager mViewPager;
    private LinearLayout linearLayout;//小圆点的布局
    private Context context;
    ArrayList<ImageView> datas = new ArrayList<>();//新数据
    List<ImageView> list = new ArrayList<>();//控件集合
    private boolean isStart = true;
    private int type = 0;//是否点击图片隐藏viewpager 0不隐藏  1隐藏


    public BannerUtils(Context contexts, List<ImageView> banners, ViewPager mViewPager, LinearLayout linearLayout) {
        this.context = contexts;
        this.banners = banners;
        this.mViewPager = mViewPager;
        this.linearLayout = linearLayout;
        initBanner();//初始化
        scrollViewPager();//无限循环
    }

    public BannerUtils(Context contexts, ArrayList<ImageView> imgPaths, ViewPager mViewPager, LinearLayout linearLayout) {
        this.context = contexts;
        this.imgPaths = imgPaths;
        this.mViewPager = mViewPager;
        this.linearLayout = linearLayout;
        initBannerBigImage();//初始化
        scrollViewPager();//无限循环
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int item = mViewPager.getCurrentItem();
            switch (msg.what) {
                //接收消息，滚动ViewPager
                case 100:
                    if (item >= imgPaths.size() - 1) {
                        item = 0;
                        mViewPager.setCurrentItem(item);
                    } else {
                        item++;
                        mViewPager.setCurrentItem(item);
                    }
                    break;
            }
        }
    };

    //初始化banner
    private void initBanner() {
        datas.clear();
        linearLayout.removeAllViews();
        //数据
        datas.addAll(banners);//将数据添加到集合
        datas.add(banners.get(0));//头加最后
        datas.add(0, banners.get(banners.size() - 1));//最后加头
        //图片地址集合
        imgPaths = new ArrayList<>();
        for (ImageView b : datas) {
            imgPaths.add(b);
        }
        //初始化控件
        for (int i = 0; i < datas.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setTag(i);
            //ImageLoader.getInstance().displayImage(imgPaths.get(i), imageView);
            imageView = imgPaths.get(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            list.add(imageView);

            //获取圆点图片的数据源
            View view = new View(context);
            view.setBackgroundResource(R.mipmap.icon01);
            //给View设置宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dp2px(context, 10), dp2px(context, 10));
            view.setLayoutParams(params);
            //将view添加到linearLayout容器中
            linearLayout.addView(view);
        }
        //第一个圆点选中
        linearLayout.getChildAt(1).setBackgroundResource(R.mipmap.icon02);
        //隐藏0和最后圆点
        linearLayout.getChildAt(0).setVisibility(View.GONE);
        linearLayout.getChildAt(datas.size() - 1).setVisibility(View.GONE);
        //设置适配器
        mViewPager.setAdapter(new BannerPageAdapter());
        mViewPager.setCurrentItem(1, false);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //控制轮播
                if (position == 0 && positionOffset == 0.0) {//当滑动0的时候
                    mViewPager.setCurrentItem(datas.size() - 2, false);//跳到倒数第二个
                } else if (position == datas.size() - 1 && positionOffset == 0.0) {//当滑动最后一个的时候
                    mViewPager.setCurrentItem(1, false);//跳到第一个
                }
            }

            @Override
            public void onPageSelected(int position) {
                //控制圆点
                if (position != 0 && position != datas.size() - 1) {
                    for (int i = 0; i < datas.size(); i++) {
                        linearLayout.getChildAt(i).setBackgroundResource(R.mipmap.icon01);
                    }
                    linearLayout.getChildAt(position).setBackgroundResource(R.mipmap.icon02);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //初始化banner
    private void initBannerBigImage() {
        //添加数据
        imgPaths.add(imgPaths.get(0));//头加最后
        imgPaths.add(0, imgPaths.get(imgPaths.size() - 1));//最后加头
        //清空控件
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        //初始化控件
        for (int i = 0; i < imgPaths.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setTag(i);
            //ImageLoader.getInstance().displayImage(imgPaths.get(i), imageView);
            imageView = imgPaths.get(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //预览图片
                    //showBigImage(imgPaths.get((int) view.getTag()));
                }
            });

            list.add(imageView);

            //获取圆点图片的数据源
            View view = new View(context);
            view.setBackgroundResource(R.mipmap.icon01);
            //给View设置宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px(context, 10), dp2px(context, 10));
            view.setLayoutParams(params);
            //将view添加到linearLayout容器中
            linearLayout.addView(view);
        }
        //第一个圆点选中
        linearLayout.getChildAt(1).setBackgroundResource(R.mipmap.icon02);
        //隐藏0和最后圆点
        linearLayout.getChildAt(0).setVisibility(View.GONE);
        linearLayout.getChildAt(imgPaths.size() - 1).setVisibility(View.GONE);
        //设置适配器
        mViewPager.setAdapter(new BannerPageAdapter());
        mViewPager.setCurrentItem(1, false);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //控制轮播
                if (position == 0 && positionOffset == 0.0) {//当滑动0的时候
                    mViewPager.setCurrentItem(imgPaths.size() - 2, false);//跳到倒数第二个
                } else if (position == imgPaths.size() - 1 && positionOffset == 0.0) {//当滑动最后一个的时候
                    mViewPager.setCurrentItem(1, false);//跳到第一个
                }
            }

            @Override
            public void onPageSelected(int position) {
                //控制圆点
                if (position != 0 && position != imgPaths.size() - 1) {
                    for (int i = 0; i < imgPaths.size(); i++) {
                        linearLayout.getChildAt(i).setBackgroundResource(R.mipmap.icon01);
                    }
                    linearLayout.getChildAt(position).setBackgroundResource(R.mipmap.icon02);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //点击放大图片的轮播
    private void initBannerNoSelect() {
        //添加数据
        imgPaths.add(imgPaths.get(0));//头加最后
        imgPaths.add(0, imgPaths.get(imgPaths.size() - 1));//最后加头
        //清空控件
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        //初始化控件
        for (int i = 0; i < imgPaths.size(); i++) {
            //创建图片
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //ImageLoader.getInstance().displayImage(imgPaths.get(i), imageView);
            imageView = imgPaths.get(i);
            imageView.setTag(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //LogUtil.i("点击放大图片");
                    //showBigImage(imgPaths.get((int) view.getTag()));
                }
            });
            list.add(imageView);

            //将view添加到linearLayout容器中
            if (linearLayout != null) {
                //创建圆点
                View view = new View(context);
                view.setBackgroundResource(R.mipmap.icon01);
                //给View设置宽高
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        dp2px(context, 10), dp2px(context, 10));
                view.setLayoutParams(params);
                linearLayout.addView(view);
            }
        }

        if (linearLayout != null) {
            //第一个圆点选中
            linearLayout.getChildAt(1).setBackgroundResource(R.mipmap.icon02);
            //隐藏0和最后圆点
            linearLayout.getChildAt(0).setVisibility(View.GONE);
            linearLayout.getChildAt(imgPaths.size() - 1).setVisibility(View.GONE);
        }

        //设置ViewPager适配器
        mViewPager.setAdapter(new BannerPageAdapter());
        mViewPager.setCurrentItem(1, false);
        //ViewPager滑动监听（控制轮播和圆点
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //控制轮播
                if (position == 0 && positionOffset == 0.0) {//当滑动0的时候
                    mViewPager.setCurrentItem(imgPaths.size() - 2, false);//跳到倒数第二个
                } else if (position == imgPaths.size() - 1 && positionOffset == 0.0) {//当滑动最后一个的时候
                    mViewPager.setCurrentItem(1, false);//跳到第一个
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (linearLayout != null) {
                    //控制圆点
                    if (position != 0 && position != imgPaths.size() - 1) {
                        for (int i = 0; i < imgPaths.size(); i++) {
                            linearLayout.getChildAt(i).setBackgroundResource(R.mipmap.icon01);
                        }
                        linearLayout.getChildAt(position).setBackgroundResource(R.mipmap.icon02);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //开启线程循环发送消息
    private void scrollViewPager() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (isStart) {
                        sleep(3000);
                        handler.sendEmptyMessage(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //停止轮播调用的方法
    public void stop() {
        isStart = false;
    }

    //ViewPager的适配器
    public class BannerPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);//todo   java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

    }

    /*protected void showBigImage(String imgPath) {
        Intent intent = new Intent(context, ShowBigImageActivity.class);
        intent.putExtra("path", imgPath);
        context.startActivity(intent);
    }*/

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
