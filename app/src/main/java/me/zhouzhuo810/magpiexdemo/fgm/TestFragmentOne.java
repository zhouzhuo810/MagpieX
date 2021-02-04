package me.zhouzhuo810.magpiexdemo.fgm;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import me.zhouzhuo810.magpiex.ui.fgm.BaseFragment;
import me.zhouzhuo810.magpiex.utils.ToastUtil;
import me.zhouzhuo810.magpiexdemo.R;
import me.zhouzhuo810.magpiexdemo.event.ChangeTextEvent;

public class TestFragmentOne extends BaseFragment {
    
    private TextView tvTab;
    private int mIndex;
    
    @Override
    public int getLayoutId() {
        return R.layout.fgm_test;
    }
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        tvTab = (TextView) findViewById(R.id.tv_tab);
    }
    
    @Override
    public void initData() {
        if (getArguments() != null) {
            mIndex = getArguments().getInt("index");
            tvTab.setText("Tab" + mIndex);
        }
    }
    
    @Override
    public void lazyLoadData() {
        tvTab.setText("i'm new one" + System.currentTimeMillis());
        //        new Handler().postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //                tvTab.setText("i'm new one"+System.currentTimeMillis());
        //                loadFinish = true;
        //            }
        //        }, 0);
    }
    
    @Override
    public void initEvent() {
        tvTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChangeTextEvent(mIndex));
            }
        });
    }
    
    @Override
    public boolean onBackPressed() {
        ToastUtil.showToast("1");
        return mIndex == 2;
    }
}
