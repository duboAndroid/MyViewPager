package dub.myapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
	private Context context;
	private List<ImageView> imageResUrl;//图片id
	private OnViewPageClickListener onViewPageClickListener;

	public ViewPagerAdapter(List<ImageView> imageResUrl, OnViewPageClickListener onViewPageClickListener) {
		this.imageResUrl = imageResUrl;
		this.onViewPageClickListener = onViewPageClickListener;
	}

	/** 返回有多少页 */
	@Override
	public int getCount() {
		return imageResUrl.size() * 10000 * 100;	// 返回一个这么大的值是为了实现无限循环
	}

	/** 用于判断ViewPager是否可以复用 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;	// 固定写法
	}
	
	/**
	 * 跟ListView中的Adpater中的getView方法类似，用于创建一个Item
	 * @param container ViewPager
	 * @param position 要生成item的位置
	 */
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView imageView = new ImageView(container.getContext());
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		//ImageLoader.getInstance().displayImage(imageResUrl.get(position % imageResUrl.size()),imageView);//异步加载网络图片
		//imageView.setBackgroundResource(imageResUrl[position % imageResUrl.size()]);
		imageView = imageResUrl.get(position % imageResUrl.size());
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(null != onViewPageClickListener)onViewPageClickListener.viewPageClick(position % imageResUrl.size());
			}
		});
		container.addView(imageView);	// 把一个item添加到ViewPager容器
		return imageView;
	}
	
	/**
	 * 销毁一个Item
	 * @param container ViewPager
	 * @param position 要销毁item的位置
	 * @param object instantiateItem方法的返回值
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((ImageView) object);
	}

	public interface OnViewPageClickListener{
		void viewPageClick(int index);
	}
}
