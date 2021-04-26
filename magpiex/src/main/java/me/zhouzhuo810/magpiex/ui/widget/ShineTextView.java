package me.zhouzhuo810.magpiex.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.AppCompatTextView;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import me.zhouzhuo810.magpiex.utils.RxHelper;

/**
 * 定时闪烁背景颜色的TextView
 */
public class ShineTextView extends AppCompatTextView {
    public static final boolean DEBUG = true;
    
    private boolean mAnimatingBg = false;
    private boolean mAnimatingTextColor = false;
    private int mDuration = 1;
    private int[] colorsBg;
    private int[] colorsText;
    private Disposable subscribeBg;
    private Disposable subscribeTextColor;
    private int originTextColor;
    
    public ShineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        originTextColor = getCurrentTextColor();
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        originTextColor = color;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    
    public void setShiningBgColors(int... colors) {
        this.colorsBg = colors;
    }
    
    public void setShiningTextColors(int... colors) {
        this.colorsText = colors;
    }
    
    @SuppressLint("CheckResult")
    public void startShiningBg() {
        if (mAnimatingBg) {
            return;
        }
        mAnimatingBg = true;
        /*定时器闪烁*/
        if (mDuration == 0) {
            mDuration = 1;
        }
        subscribeBg = Observable.interval(0, mDuration, TimeUnit.SECONDS)
            .compose(RxHelper.<Long>io_main())
            .subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    int i = (int) (aLong % colorsBg.length);
                    setBackgroundColor(colorsBg[i]);
                }
            });
    }
    
    public void stopShiningBg() {
        mAnimatingBg = false;
        if (subscribeBg != null && !subscribeBg.isDisposed()) {
            subscribeBg.dispose();
        }
        setBackgroundColor(Color.TRANSPARENT);
    }
    
    public void stopShiningBg(int bgColor) {
        mAnimatingBg = false;
        if (subscribeBg != null && !subscribeBg.isDisposed()) {
            subscribeBg.dispose();
        }
        setBackgroundColor(bgColor);
    }
    
    @SuppressLint("CheckResult")
    public void startShiningTextColor() {
        if (mAnimatingTextColor) {
            return;
        }
        originTextColor = getCurrentTextColor();
        mAnimatingTextColor = true;
        /*定时器闪烁*/
        if (mDuration == 0) {
            mDuration = 1;
        }
        subscribeTextColor = Observable.interval(0, mDuration, TimeUnit.SECONDS)
            .compose(RxHelper.<Long>io_main())
            .subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    int i = (int) (aLong % colorsText.length);
                    setTextColor(colorsText[i]);
                }
            });
    }
    
    public void stopShiningTextColor() {
        mAnimatingTextColor = false;
        if (subscribeTextColor != null && !subscribeTextColor.isDisposed()) {
            subscribeTextColor.dispose();
        }
        setTextColor(originTextColor);
    }
    
    public void stopShiningTextColor(int textColor) {
        mAnimatingTextColor = false;
        if (subscribeTextColor != null && !subscribeTextColor.isDisposed()) {
            subscribeTextColor.dispose();
        }
        setTextColor(textColor);
    }
    
    public int getDurationSeconds() {
        return mDuration;
    }
    
    public void setDurationWithSeconds(int duration) {
        if (duration <= 0) {
            duration = 1;
        }
        mDuration = duration;
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopShiningBg();
        stopShiningTextColor();
    }
}
