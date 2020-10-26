package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;


/**
 * 角标
 * Created by admin on 2017/7/27.
 */
public class MarkView extends View {
    
    /**
     * {@link #OVAL} 圆形
     * {@link #RECT} 方形
     * {@link #POINT} 圆点
     */
    public enum MarkShape {
        OVAL,
        RECT,
        POINT
    }
    
    private int markNumber = 0;
    private int maxMarkNumber = 99;
    
    private Paint textPaint;
    private Paint bgPaint;
    private int textSize = 34;
    private int textColor = 0xffffffff;
    private int bgColor = 0xffff0000;
    private int bgShape = 0;
    private int pointSize = 24;
    
    public MarkView(Context context) {
        super(context);
        init(context, null);
    }
    
    public MarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public MarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    @RequiresApi(value = 21)
    public MarkView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        
        if (wMode == MeasureSpec.AT_MOST || wMode == MeasureSpec.UNSPECIFIED) {
            switch (bgShape) {
                case 0:
                case 1:
                    //oval or rect
                    if (markNumber > maxMarkNumber) {
                        float textWidth = textPaint.measureText(maxMarkNumber + "+");
                        float textHeight = textPaint.descent() - textPaint.ascent();
                        if (textWidth < textHeight) {
                            if (maxMarkNumber < 10) {
                                wSize = (int) (textHeight);
                            } else {
                                wSize = (int) (textHeight + textHeight / 2);
                            }
                        } else {
                            wSize = (int) (textWidth + textHeight / 2);
                        }
                    } else {
                        float textWidth = textPaint.measureText(markNumber + "");
                        float textHeight = textPaint.descent() - textPaint.ascent();
                        if (textWidth < textHeight) {
                            if (markNumber < 10) {
                                wSize = (int) (textHeight);
                            } else {
                                wSize = (int) (textHeight + textHeight / 2);
                            }
                        } else {
                            wSize = (int) (textWidth + textHeight / 2);
                        }
                    }
                    break;
                case 2:
                    //point
                    wSize = pointSize;
                    break;
                default:
                    break;
            }
            
        }
        
        if (hMode == MeasureSpec.AT_MOST || hMode == MeasureSpec.UNSPECIFIED) {
            switch (bgShape) {
                case 0:
                case 1:
                    //oval or rect
                    float textWidth = textPaint.measureText(markNumber + "");
                    float textHeight = textPaint.descent() - textPaint.ascent();
                    hSize = (int) textHeight;
                    break;
                case 2:
                    //point
                    hSize = pointSize;
                    break;
                default:
                    break;
            }
            
        }
        setMeasuredDimension(wSize, hSize);
    }
    
    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaints();
    }
    
    private void initPaints() {
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);
        bgPaint.setAntiAlias(true);
        
    }
    
    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.MarkView);
            markNumber = t.getInteger(R.styleable.MarkView_mv_markNumber, 0);
            maxMarkNumber = t.getInteger(R.styleable.MarkView_mv_maxMarkNumber, 99);
            textSize = t.getDimensionPixelSize(R.styleable.MarkView_mv_textSize, 34);
            textColor = t.getColor(R.styleable.MarkView_mv_textColor, 0xffffffff);
            bgColor = t.getColor(R.styleable.MarkView_mv_bgColor, 0xffff0000);
            bgShape = t.getInt(R.styleable.MarkView_mv_bgShape, 0);
            pointSize = t.getDimensionPixelSize(R.styleable.MarkView_mv_point_size, 24);
            t.recycle();
        }
        if (!isInEditMode()) {
            textSize = ScreenAdapterUtil.getInstance().getScaledValue(textSize);
            pointSize = ScreenAdapterUtil.getInstance().getScaledValue(pointSize);
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        if (bgShape != 2) {
            drawText(canvas);
        }
    }
    
    private void drawBg(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.top = 0;
        rectF.right = getWidth();
        rectF.bottom = getHeight();
        int width = getWidth();
        int height = getHeight();
        switch (bgShape) {
            case 0:
                //oval
                int radius = width > height ? height / 2 : width / 2;
                canvas.drawRoundRect(rectF, radius, radius, bgPaint);
                break;
            case 1:
                //rect
                canvas.drawRect(rectF, bgPaint);
                break;
            case 2:
                //point
                canvas.drawCircle(width / 2.0f, width / 2.0f, width / 2.0f, bgPaint);
                break;
            default:
                break;
        }
        
    }
    
    public MarkView setBgShape(MarkShape bgShape) {
        this.bgShape = bgShape.ordinal();
        return this;
    }
    
    private void drawText(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        if (markNumber > maxMarkNumber) {
            float textWidth = textPaint.measureText(maxMarkNumber + "+");
            float textHeight = textPaint.descent() + textPaint.ascent();
            canvas.drawText(maxMarkNumber + "+", (w - textWidth) / 2, (h - textHeight) / 2, textPaint);
        } else {
            float textWidth = textPaint.measureText(markNumber + "");
            float textHeight = textPaint.descent() + textPaint.ascent();
            canvas.drawText(markNumber + "", (w - textWidth) / 2, (h - textHeight) / 2, textPaint);
        }
    }
    
    public MarkView setMaxMarkNumber(int maxMarkNumber) {
        this.maxMarkNumber = maxMarkNumber;
        return this;
    }
    
    public MarkView setMarkNumber(int markNumber) {
        this.markNumber = markNumber;
        return this;
    }
    
    public MarkView setPointSize(int pointSize) {
        this.pointSize = ScreenAdapterUtil.getInstance().getScaledValue(pointSize);
        return this;
    }
    
    public int getMarkNumber() {
        return this.markNumber;
    }
    
    public MarkView setTextColor(int color) {
        this.textColor = color;
        textPaint.setColor(textColor);
        return this;
    }
    
    public MarkView setTextSizeInPx(int pxSize) {
        this.textSize = ScreenAdapterUtil.getInstance().getScaledValue(pxSize);
        textPaint.setTextSize(textSize);
        return this;
    }
    
    public MarkView setBgColor(int color) {
        this.bgColor = color;
        bgPaint.setColor(bgColor);
        return this;
    }
    
    public MarkView setTextColorRes(int colorRes) {
        this.textColor = getContext().getResources().getColor(colorRes);
        textPaint.setColor(textColor);
        return this;
    }
    
    public MarkView setBgColorRes(int colorRes) {
        this.bgColor = getContext().getResources().getColor(colorRes);
        bgPaint.setColor(bgColor);
        return this;
    }
    
    /**
     * 刷新控件
     */
    public void update() {
        requestLayout();
        invalidate();
    }
    
}
