package me.zhouzhuo810.magpiex.ui.widget.adapter;


import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import me.zhouzhuo810.magpiex.ui.widget.intef.IResProvider;


/**
 * Fragment+ViewPager适配器
 * @author zz
 * @date 2016/8/22
 */
public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter implements IResProvider {
    
    private List<String> titles;
    
    public BaseFragmentPagerAdapter(FragmentManager manager, String[] titles) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        if (titles != null) {
            this.titles = Arrays.asList(titles);
        }
    }
    
    public BaseFragmentPagerAdapter(FragmentManager manager, List<String> titles) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.titles = titles;
    }
    
    public BaseFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, List<String> titles) {
        super(fm, behavior);
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
    public int getCount() {
        return titles == null ? 0 : titles.size();
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
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
     * @param position ViewPager页码
     * @return Fragment
     */
    protected abstract Fragment getFragment(int position);
    
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }
    
    
}
