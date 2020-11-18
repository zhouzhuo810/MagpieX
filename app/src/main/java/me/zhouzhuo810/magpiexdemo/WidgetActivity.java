package me.zhouzhuo810.magpiexdemo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.widget.MarkView;

public class WidgetActivity extends BaseActivity {
    
    private MarkView mMv;
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mMv = findViewById(R.id.mv);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_widget;
    }
    
    @Override
    public void initData() {
    
    }
    
    public void decMarkView(View v) {
        mMv.setMarkNumber(mMv.getMarkNumber() - 1).update();
    }
    
    public void addMarkView(View v) {
        mMv.setMarkNumber(mMv.getMarkNumber() + 1).update();
    }
    
    @Override
    public void initEvent() {
    
    }
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return false;
    }
}
