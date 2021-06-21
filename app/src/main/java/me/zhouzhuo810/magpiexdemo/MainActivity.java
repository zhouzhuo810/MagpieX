package me.zhouzhuo810.magpiexdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.dialog.ListDialog;
import me.zhouzhuo810.magpiex.utils.CollectionUtil;
import me.zhouzhuo810.magpiex.utils.LanguageUtil;
import me.zhouzhuo810.magpiex.utils.RxHelper;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;
import me.zhouzhuo810.magpiex.utils.ToastUtil;
import me.zhouzhuo810.magpiexdemo.api.Api;
import me.zhouzhuo810.magpiexdemo.api.entity.GetWeatherList;
import me.zhouzhuo810.magpiexdemo.constants.MyCons;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    
    private EditText etCity;
    private Button btnGo;
    private TextView tvResult;
    private Button btnLanguage;
    private Button btnDialog;
    private View btnTitle;
    private Button btnDownload;
    private Button btnPager;
    private Button btnPager2;
    private Button btnTab;
    private Button tvMap;
    private Button tvScrollList;
    private Button tvScrollGrid;
    private Button btnTools;
    private Button btnSpinner;
    private TextView mTvWidget;
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return true;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        btnLanguage = findViewById(R.id.btn_language);
        btnDialog = findViewById(R.id.btn_dialog);
        btnTitle = findViewById(R.id.btn_title);
        btnDownload = findViewById(R.id.btn_download);
        btnPager = findViewById(R.id.btn_pager);
        btnPager2 = findViewById(R.id.btn_pager2);
        btnTab = findViewById(R.id.btn_tab);
        etCity = findViewById(R.id.et_city);
        btnGo = findViewById(R.id.btn_go);
        tvMap = findViewById(R.id.tv_map);
        tvScrollList = findViewById(R.id.tv_scroll_list);
        tvScrollGrid = findViewById(R.id.tv_scroll_grid);
        mTvWidget = findViewById(R.id.tv_widget);
        btnTools = findViewById(R.id.btn_tools);
        btnSpinner = findViewById(R.id.btn_spinner);
        tvResult = findViewById(R.id.tv_result);
    }
    
    @Override
    public void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add((i + 1) + "#");
        }
        Collections.shuffle(list);
        Log.e(TAG, "排序前：\n" + list.toString());
        CollectionUtil.sort(list, false);
        Log.e(TAG, "排序后：\n" + list.toString());
        
        //        ToastUtil.showToast(SimpleUtil.getString(R.string.bottom_sheet_dialog));
        
    }
    
    
    @Override
    public void initEvent() {
        
        btnLanguage.setOnClickListener(this);
        
        btnDialog.setOnClickListener(this);
        
        btnTitle.setOnClickListener(this);
        
        btnDownload.setOnClickListener(this);
        
        btnGo.setOnClickListener(this);
        
        btnPager.setOnClickListener(this);
        
        btnPager2.setOnClickListener(this);
        
        btnTab.setOnClickListener(this);
        
        btnTools.setOnClickListener(this);
        
        btnSpinner.setOnClickListener(this);
        
        tvMap.setOnClickListener(this);
        
        tvScrollList.setOnClickListener(this);
        
        tvScrollGrid.setOnClickListener(this);
        
        mTvWidget.setOnClickListener(this);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                String city = etCity.getText().toString().trim();
                Api.getApi()
                    .getWeatherList(city)
                    .compose(RxHelper.<GetWeatherList>io_main())
                    .subscribe(new Observer<GetWeatherList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        
                        }
                        
                        @Override
                        public void onNext(GetWeatherList getWeatherList) {
                            tvResult.setText(getWeatherList.toString());
                        }
                        
                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showShortToast(e.getMessage());
                        }
                        
                        @Override
                        public void onComplete() {
                        
                        }
                    });
                
                break;
            case R.id.btn_language:
                String[] items = getResources().getStringArray(R.array.language);
                showListDialog(getString(R.string.app_name), items, true, false, new ListDialog.OnItemClick() {
                    @Override
                    public void onItemClick(int position, String item) {
                        switch (position) {
                            case 0:
                                LanguageUtil.setGlobalLanguage(MyCons.LANGUAGE_CH_SIMPLE);
                                recreate();
                                break;
                            case 1:
                                LanguageUtil.setGlobalLanguage(MyCons.LANGUAGE_CH_COMPLEX);
                                recreate();
                                break;
                            case 2:
                                LanguageUtil.setGlobalLanguage(MyCons.LANGUAGE_EN);
                                recreate();
                                break;
                            case 3:
                                LanguageUtil.setGlobalLanguage(MyCons.LANGUAGE_VI);
                                recreate();
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            case R.id.btn_dialog:
                startAct(DialogActivity.class);
                break;
            case R.id.btn_title:
                startAct(TitleActivity.class);
                break;
            case R.id.btn_download:
                startAct(DownloadActivity.class);
                break;
            case R.id.btn_pager:
                startAct(PagerActivity.class);
                break;
            case R.id.btn_pager2:
                startAct(Pager2Activity.class);
                break;
            case R.id.btn_tab:
                startAct(TabActivity.class);
                break;
            case R.id.btn_tools:
                //                ToastUtil.showShortToast(SimpleUtil.getString(R.string.back_text));
                startAct(ToolsActivity.class);
                break;
            case R.id.btn_spinner:
                startAct(SpinnerTestActivity.class);
                break;
            case R.id.tv_map:
                startAct(MapActivity.class);
                break;
            case R.id.tv_scroll_grid:
                startAct(ScrollGridActivity.class);
                break;
            case R.id.tv_scroll_list:
                startAct(ScrollListActivity.class);
                break;
            case R.id.tv_widget:
                startAct(WidgetActivity.class);
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        
    }
    
    @Override
    public void onBackPressed() {
        ToastUtil.showToast(SimpleUtil.getString(R.string.back_text));
    }
}
