package me.zhouzhuo810.magpiexdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.widget.Indicator;
import me.zhouzhuo810.magpiex.ui.widget.adapter.BaseFragmentPagerAdapter;
import me.zhouzhuo810.magpiex.utils.ToastUtil;
import me.zhouzhuo810.magpiexdemo.event.ChangeTextEvent;
import me.zhouzhuo810.magpiexdemo.fgm.TestFragmentOne;

public class PagerActivity extends BaseActivity {
    
    private Indicator indicator;
    private ViewPager viewPager;
    private Indicator indicator2;
    private Indicator indicator3;
    private Indicator indicator4;
    private Indicator indicator5;
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return false;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_pager;
    }
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        indicator = findViewById(R.id.indicator);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
        indicator4 = findViewById(R.id.indicator4);
        indicator5 = findViewById(R.id.indicator5);
        viewPager = findViewById(R.id.view_pager);
    }
    
    @Override
    public void initData() {
        List<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("军事");
        titles.add("生活");
        titles.add("爱情");
        titles.add("汽车");
        titles.add("美女");
        titles.add("小说");
        titles.add("更多更长");
        titles.add("更多更长更长");
        titles.add("更多更长更长更长");
        titles.add("更多更长更长更长更长");
        final List<Fragment> fgms = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            fgms.add(TestFragmentOne.newInstance(TestFragmentOne.class, bundle));
        }
        viewPager.setOffscreenPageLimit(titles.size());
        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), titles) {
            @Override
            public int getSelectedIcon(int position) {
                return R.mipmap.ic_launcher;
            }
            
            @Override
            public int getUnselectedIcon(int position) {
                return R.mipmap.ic_launcher_round;
            }
            
            @Override
            protected Fragment getFragment(int position) {
                return fgms.get(position);
            }
        });
        indicator.setViewPager(viewPager);
        indicator2.setViewPager(viewPager);
        indicator3.setViewPager(viewPager);
        indicator4.setViewPager(viewPager);
        indicator5.setViewPager(viewPager);
    }
    
    @Override
    public void initEvent() {
    
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTextEvent(ChangeTextEvent event) {
        ToastUtil.showShortToast("修改"+event.getIndex());
        indicator.updateText(event.getIndex(), "修改"+event.getIndex());
    }
    
}
