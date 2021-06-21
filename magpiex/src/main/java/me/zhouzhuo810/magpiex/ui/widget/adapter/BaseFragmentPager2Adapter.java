package me.zhouzhuo810.magpiex.ui.widget.adapter;


import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import me.zhouzhuo810.magpiex.ui.widget.intef.IResProvider;


/**
 * Fragment+ViewPager适配器
 *
 * @author zz
 * @date 2016/8/22
 */
public abstract class BaseFragmentPager2Adapter extends FragmentStateAdapter implements IResProvider {
    
    private List<String> titles;
    
    public BaseFragmentPager2Adapter(FragmentActivity activity, String[] titles) {
        super(activity);
        if (titles != null) {
            this.titles = Arrays.asList(titles);
        }
    }
    
    public BaseFragmentPager2Adapter(FragmentActivity activity, List<String> titles) {
        super(activity);
        this.titles = titles;
    }
    
    public BaseFragmentPager2Adapter(Fragment fragment, String[] titles) {
        super(fragment);
        if (titles != null) {
            this.titles = Arrays.asList(titles);
        }
    }
    
    public BaseFragmentPager2Adapter(Fragment fragment, List<String> titles) {
        super(fragment);
        this.titles = titles;
    }
    
    public List<String> getTitles() {
        return titles;
    }
    
    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
    
    
    public void setPageTitle(int position, String title) {
        if (position >= 0 && titles != null && position < titles.size()) {
            titles.set(position, title);
            notifyDataSetChanged();
        }
    }
    
    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }
    
    public CharSequence getPageTitle(int position) {
        if (titles == null) {
            return null;
        }
        if (position >= 0 && position < titles.size()) {
            return titles.get(position);
        }
        return null;
    }
    
    @Override
    public CharSequence getTitle(int position) {
        if (titles == null) {
            return null;
        }
        if (position >= 0 && position < titles.size()) {
            return titles.get(position);
        }
        return null;
    }
    
    /**
     * 获取Fragment
     *
     * @param position ViewPager页码
     * @return Fragment
     */
    protected abstract Fragment getFragment(int position);
    
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getFragment(position);
    }
}
