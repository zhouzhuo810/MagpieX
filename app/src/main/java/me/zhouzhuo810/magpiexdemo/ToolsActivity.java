package me.zhouzhuo810.magpiexdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.widget.MarkView;
import me.zhouzhuo810.magpiex.ui.widget.TitleBar;
import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.NoticeUtil;

public class ToolsActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_tools;
    }
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return false;
    }
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
    
    }
    
    @Override
    public void initData() {
    
    }
    
    @Override
    public void initEvent() {
        TitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClick() {
            @Override
            public void onLeftClick(ImageView ivLeft, MarkView mv, TextView tvLeft) {
                closeAct();
            }
            
            @Override
            public void onTitleClick(TextView tvTitle) {
            
            }
            
            @Override
            public void onRightClick(ImageView ivRight, MarkView mv, TextView tvRight) {
            
            }
        });
    }
    
    public void noticeTools(View v) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(BaseUtil.getApp(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        
        NoticeUtil.showNormalNotice(getString(R.string.app_name), getString(R.string.notice_tools),
            true, false, R.mipmap.ic_launcher,
            true, true, null, intent);
    }
}
