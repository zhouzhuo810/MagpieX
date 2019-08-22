package me.zhouzhuo810.magpiex.ui.widget.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public V instantiateItem(ViewGroup container, int position) {
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTabText(data.get(position), position);
    }

    public abstract String getTabText(M m, int position);


}
