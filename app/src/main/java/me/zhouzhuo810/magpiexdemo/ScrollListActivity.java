package me.zhouzhuo810.magpiexdemo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.widget.MarkView;
import me.zhouzhuo810.magpiex.ui.widget.ScrollListRecyclerView;
import me.zhouzhuo810.magpiex.ui.widget.TitleBar;
import me.zhouzhuo810.magpiex.utils.RandomUtil;
import me.zhouzhuo810.magpiex.utils.RxHelper;
import me.zhouzhuo810.magpiexdemo.adapter.ScrollListAdapter;
import me.zhouzhuo810.magpiexdemo.api.entity.TestListEntity;

public class ScrollListActivity extends BaseActivity {
    
    private ScrollListRecyclerView<TestListEntity> mScrollListRecyclerView;
    private ScrollListAdapter mScrollListAdapter;
    private Disposable mInterval;
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return false;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_scroll_list;
    }
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mScrollListRecyclerView = findViewById(R.id.rv);
        TitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setOnLeftClickListener(new TitleBar.OnLeftClickListener() {
            @Override
            public void onClick(ImageView ivLeft, MarkView mv, TextView tvLeft) {
                closeAct();
            }
        });
    }
    
    @Override
    public void initData() {
        mScrollListAdapter = new ScrollListAdapter(this, null);
        mScrollListRecyclerView.setAdapter(mScrollListAdapter);
        
        randomDataTask();
        
    }
    
    
    private void randomDataTask() {
        cancelDisposable(mInterval);
        mInterval = RxHelper.interval(5, TimeUnit.SECONDS, new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                List<TestListEntity> list = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    list.add(new TestListEntity("姓名" + i, "男", "test@qq.com", RandomUtil.getRandomSixInt() + "", RandomUtil.getRandomColorBlackToWhite()));
                }
                mScrollListRecyclerView.setNewData(list, false);
            }
        });
    }
    
    
    @Override
    public void initEvent() {
    
    }
    
    @Override
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState) {
        return false;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelDisposable(mInterval);
    }
    
    @Override
    public boolean needAlwaysScreenOn() {
        return true;
    }
}
