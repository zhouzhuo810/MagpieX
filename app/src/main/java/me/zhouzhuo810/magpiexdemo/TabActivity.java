package me.zhouzhuo810.magpiexdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.widget.TabBar;
import me.zhouzhuo810.magpiexdemo.fgm.TestFragmentFour;
import me.zhouzhuo810.magpiexdemo.fgm.TestFragmentOne;
import me.zhouzhuo810.magpiexdemo.fgm.TestFragmentThree;
import me.zhouzhuo810.magpiexdemo.fgm.TestFragmentTwo;

public class TabActivity extends BaseActivity {
    
    TabBar tabBar;
    private TestFragmentOne fgm1;
    private TestFragmentTwo fgm2;
    private TestFragmentThree fgm3;
    private TestFragmentFour fgm4;
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return false;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_tab;
    }
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tabBar = findViewById(R.id.tab_bar);
    }

/*    private void changeToPosition(int position, Bundle bundle) {
        switch (position) {
            case 0:
                replaceFragment(R.id.fgm_container, TestFragmentOne.class, fgm1, bundle);
                break;
            case 1:
                replaceFragment(R.id.fgm_container, TestFragmentOne.class, fgm2, bundle);
                break;
            case 2:
                replaceFragment(R.id.fgm_container, TestFragmentOne.class, fgm3, bundle);
                break;
            case 3:
                replaceFragment(R.id.fgm_container, TestFragmentOne.class, fgm4, bundle);
                break;

        }
    }*/
    
    private void changeToPositionBetter(int position, Bundle bundle) {
        switch (position) {
            case 0:
                fgm1 = addOrShowFragment(R.id.fgm_container, TestFragmentOne.class, bundle);
                break;
            case 1:
                fgm2 = addOrShowFragment(R.id.fgm_container, TestFragmentTwo.class, bundle);
                break;
            case 2:
                fgm3 = addOrShowFragment(R.id.fgm_container, TestFragmentThree.class, bundle);
                break;
            case 3:
                fgm4 = addOrShowFragment(R.id.fgm_container, TestFragmentFour.class, bundle);
                break;
            
        }
    }
    
    @Override
    public void initData() {
        tabBar.setNormalIconRes(
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round
        );
        tabBar.setPressIconRes(
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
        );
    }
    
    @Override
    public void initEvent() {
        tabBar.setOnTabBarClickListener(new TabBar.OnTabBarClick() {
            @Override
            public void onTabClick(ImageView iv, TextView tv, int position, boolean changed) {
                Bundle bundle = new Bundle();
                bundle.putInt("index", position);
                changeToPositionBetter(position, bundle);
            }
        });
        
        //这个要在设置setOnTabBarClickListener监听之后调用才会触发onTabClick方法
        tabBar.setSelection(0);
    }
}
