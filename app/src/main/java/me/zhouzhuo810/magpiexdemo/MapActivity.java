package me.zhouzhuo810.magpiexdemo;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.widget.MapRecyclerView;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.MapEntity;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.MapTextRectEntity;
import me.zhouzhuo810.magpiex.ui.widget.map.view.MapTextView;
import me.zhouzhuo810.magpiex.utils.AssetsUtil;
import me.zhouzhuo810.magpiex.utils.CollectionUtil;
import me.zhouzhuo810.magpiexdemo.api.entity.MainInfoBean;


public class MapActivity extends BaseActivity {

    private MapRecyclerView rv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public boolean shouldSupportMultiLanguage() {
        return false;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        rv = findViewById(R.id.rv);
    }

    @Override
    public void initData() {
        rv.setMapEntity(getTestData());
    }

    @Override
    public void initEvent() {

    }


    private MapEntity getTestData() {
        String json = AssetsUtil.getFileToStringFromAssets("test.json");
        Gson gson = new Gson();
        MainInfoBean mainInfoBean = gson.fromJson(json, MainInfoBean.class);
        if (mainInfoBean.getCode().equals("1")) {
            List<MapTextRectEntity> list = generateData(mainInfoBean.getData().getMap());
            return new MapEntity(mainInfoBean.getData().getDesignHeight(), mainInfoBean.getData().getDesignWidth(), list);
        }
        return null;
    }

    private List<MapTextRectEntity> generateData(List<MainInfoBean.DataEntity.MapEntity> map) {
        List<MapTextRectEntity> list = new ArrayList<>();
        for (MainInfoBean.DataEntity.MapEntity mapEntity : map) {
            if (TextUtils.isEmpty(mapEntity.getMacName())) {
                MapTextRectEntity entity = new MapTextRectEntity(mapEntity.getMacName(), Color.BLACK,1,
                        new Rect(Integer.valueOf(mapEntity.getLeftTop().split(",")[0]),
                                Integer.valueOf(mapEntity.getLeftTop().split(",")[1]),
                                Integer.valueOf(mapEntity.getRightBottom().split(",")[0]),
                                Integer.valueOf(mapEntity.getRightBottom().split(",")[1])));
                list.add(entity);
            } else {
                MapTextRectEntity entity = new MapTextRectEntity(mapEntity.getMacName(), MapTextView.HORIZONTAL,
                        Color.BLACK, 1, mapEntity.getTextSize(), 3, generateColors(mapEntity.getColor()),
                        new Rect(Integer.valueOf(mapEntity.getLeftTop().split(",")[0]),
                                Integer.valueOf(mapEntity.getLeftTop().split(",")[1]),
                                Integer.valueOf(mapEntity.getRightBottom().split(",")[0]),
                                Integer.valueOf(mapEntity.getRightBottom().split(",")[1])));
                list.add(entity);
            }
        }
        return list;
    }

    private int[] generateColors(List<MainInfoBean.DataEntity.MapEntity.ColorEntity> color) {
        if (CollectionUtil.isEmpty(color)) {
            return null;
        } else {
            int[] colors = new int[color.size()];
            for (int i = 0; i < color.size(); i++) {
                MainInfoBean.DataEntity.MapEntity.ColorEntity colorEntity = color.get(i);
                colors[i] = Color.parseColor(colorEntity.getValue());
            }
            return colors;
        }
    }


    @Override
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState) {
        return false;
    }
}
