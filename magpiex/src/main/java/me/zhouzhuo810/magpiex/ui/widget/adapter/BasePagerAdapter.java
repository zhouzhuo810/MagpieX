package me.zhouzhuo810.magpiex.ui.widget.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.zhouzhuo810.magpiex.ui.widget.intef.IResProvider;


/**
 * Created by zz on 2016/8/22.
 */
public abstract class BasePagerAdapter<V extends View, M> extends PagerAdapter implements IResProvider {
    
    protected Context context;
    private List<V> views;
    protected List<M> data;
    
    public BasePagerAdapter(final Context context, List<V> views, List<M> data) {
        this.context = context;
        this.views = views;
        this.data = data;
    }
    
    public void setViews(List<V> views) {
        this.views = views;
    }
    
    public void setData(List<M> data) {
        this.data = data;
    }
    
    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }
    
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    
    @NonNull
    @Override
    public V instantiateItem(@NonNull ViewGroup container, int position) {
        V view = views.get(position);
        if (data != null) {
            bindData(view, data.get(position));
        }
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewPager) container).addView(view);
        return view;
    }
    
    public abstract void bindData(V view, M m);
    
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return getTabText(data.get(position), position);
    }
    
    /**
     * 获取Tab的文字
     *
     * @param m        数据模型
     * @param position ViewPager页码
     * @return 文字
     */
    public abstract CharSequence getTabText(M m, int position);
    
    
}
