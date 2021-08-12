package me.zhouzhuo810.magpiexdemo;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.widget.MarkView;
import me.zhouzhuo810.magpiex.utils.DrawableUtil;

public class WidgetActivity extends BaseActivity {
    
    private MarkView mMv;
    private TextView mTvShape;
    private TextView mTvGradient;
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mMv = findViewById(R.id.mv);
        mTvShape = findViewById(R.id.tv_shape);
        mTvGradient = findViewById(R.id.tv_gradient);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_widget;
    }
    
    @Override
    public void initData() {
        // new DrawableUtil.ShapeBuilder()
        //     .shape(GradientDrawable.RECTANGLE)
        //     .radius(SimpleUtil.getScaledValue(5))
        //     .color(Color.RED)
        //     .strokeWidth(2)
        //     .strokeDashWidth(5)
        //     .strokeDashGap(5)
        //     .strokeColor(Color.BLUE)
        //     .applyTo(mTvShape);
        mTvShape.setBackground(DrawableUtil.roundShape(5, Color.RED, Color.BLUE, 2, 5, 5));
        // new DrawableUtil.ShapeBuilder()
        //     .shape(GradientDrawable.RECTANGLE)
        //     .radius(SimpleUtil.getScaledValue(5))
        //     .gradient(true)
        //     .fromColor(Color.RED)
        //     .toColor(Color.BLUE)
        //     .strokeWidth(2)
        //     .strokeDashWidth(5)
        //     .strokeDashGap(5)
        //     .strokeColor(Color.BLUE)
        //     .applyTo(mTvGradient);
        mTvGradient.setBackground(DrawableUtil.gradientShape(5, Color.RED, Color.BLUE, GradientDrawable.Orientation.LEFT_RIGHT,
            Color.BLUE, 2, 5, 5));
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
