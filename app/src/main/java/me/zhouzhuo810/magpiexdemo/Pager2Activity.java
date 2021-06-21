package me.zhouzhuo810.magpiexdemo;

import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.widget.Indicator2;
import me.zhouzhuo810.magpiex.ui.widget.adapter.BaseFragmentPager2Adapter;
import me.zhouzhuo810.magpiex.ui.widget.adapter.BaseFragmentPagerAdapter;
import me.zhouzhuo810.magpiex.utils.ToastUtil;
import me.zhouzhuo810.magpiexdemo.event.ChangeTextEvent;
import me.zhouzhuo810.magpiexdemo.fgm.TestFragmentOne;

public class Pager2Activity extends BaseActivity {
    
    private Indicator2 indicator;
    private ViewPager2 viewPager;
    private Indicator2 indicator2;
    private Indicator2 indicator3;
    private Indicator2 indicator4;
    private Indicator2 indicator5;
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return false;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_pager2;
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
        viewPager.setAdapter(new BaseFragmentPager2Adapter(this, titles) {
            @Override
            protected Fragment getFragment(int position) {
                return fgms.get(position);
            }
    
            @Override
            public int getSelectedIcon(int position) {
                return R.mipmap.ic_launcher;
            }
    
            @Override
            public int getUnselectedIcon(int position) {
                return R.mipmap.ic_launcher_round;
            }
    
        });
        indicator.setViewPager2(viewPager);
        indicator2.setViewPager2(viewPager);
        indicator3.setViewPager2(viewPager);
        indicator4.setViewPager2(viewPager);
        indicator5.setViewPager2(viewPager);
    }
    
    @Override
    public void initEvent() {
    
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeTextEvent(ChangeTextEvent event) {
        ToastUtil.showShortToast("修改" + event.getIndex());
        indicator.updateText(event.getIndex(), "修改" + event.getIndex());
    }
    
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
