package me.zhouzhuo810.magpiexdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.adapter.LvBaseAdapter;
import me.zhouzhuo810.magpiex.ui.widget.MarkView;
import me.zhouzhuo810.magpiex.ui.widget.SimpleSpinner;
import me.zhouzhuo810.magpiex.ui.widget.SimpleStringSpinner;
import me.zhouzhuo810.magpiex.ui.widget.TitleBar;
import me.zhouzhuo810.magpiex.utils.ToastUtil;
import me.zhouzhuo810.magpiexdemo.api.entity.SimpleSpinnerEntity;

public class SpinnerTestActivity extends BaseActivity {

    private TitleBar titleBar;
    private Spinner spTest1;
    private Spinner spTest2;
    private SimpleStringSpinner spCustom1;
    private SimpleStringSpinner spCustom2;
    private SimpleSpinner<SimpleSpinnerEntity> spCustom3;

    @Override
    public boolean shouldSupportMultiLanguage() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_spinner;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        titleBar = findViewById(R.id.title_bar);
        spTest1 = findViewById(R.id.sp_test_1);
        spTest2 = findViewById(R.id.sp_test_2);
        spCustom1 = findViewById(R.id.sp_custom_1);
        spCustom2 = findViewById(R.id.sp_custom_2);
        spCustom3 = findViewById(R.id.sp_custom_3);
    }

    @Override
    public void initData() {
        String[] workFraq = getResources().getStringArray(R.array.list_test_data);
        LvBaseAdapter<String> adapter = new LvBaseAdapter<String>(this, workFraq) {
            @Override
            public int getLayoutId() {
                return R.layout.spinner_item;
            }

            @Override
            protected void fillData(ViewHolder holder, String item, int position) {
                holder.setText(R.id.tv_sp_content, item);
            }
        };
        spTest1.setAdapter(adapter);
        spTest1.setSelection(0);
        spTest2.setAdapter(adapter);
        spTest2.setSelection(0);

        
        
        spCustom1.setLayoutId(R.layout.spinner_item)
                .setDropdownLayoutId(R.layout.spinner_item)
                .setTextViewId(R.id.tv_sp_content)
                .setItems(workFraq);
        spCustom2.setLayoutId(R.layout.spinner_item)
                .setDropdownLayoutId(R.layout.spinner_item)
                .setTextViewId(R.id.tv_sp_content)
                .setItems(workFraq);
        
        List<SimpleSpinnerEntity> list = new ArrayList<>();
        list.add(new SimpleSpinnerEntity("1", "test1"));
        list.add(new SimpleSpinnerEntity("2", "test2"));
        list.add(new SimpleSpinnerEntity("3", "test3"));
        spCustom3.setLayoutId(R.layout.spinner_item)
                .setDropdownLayoutId(R.layout.spinner_item)
                .setTextViewId(R.id.tv_sp_content)
                .setOnSimpleSpinnerItemSelectedListener(new SimpleSpinner.OnSimpleSpinnerItemSelectedListener<SimpleSpinnerEntity>() {
    
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id, SimpleSpinnerEntity data) {
                        ToastUtil.showToast(data.getSpItemName());
                    }
    
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    
                    }
                })
                .setItems(list);
    }

    @Override
    public void initEvent() {
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
}
