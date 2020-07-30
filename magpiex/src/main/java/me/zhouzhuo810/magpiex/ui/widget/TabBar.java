package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresApi;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.ColorUtil;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;

import static java.lang.annotation.RetentionPolicy.SOURCE;


/**
 * 底部Tab
 * Created by admin on 2017/7/27.
 */
public class TabBar extends LinearLayout {

    private RelativeLayout ll0;
    private ImageView iv0;
    private TextView tv0;
    private View line0;
    private RelativeLayout ll1;
    private ImageView iv1;
    private TextView tv1;
    private View line1;
    private RelativeLayout ll2;
    private ImageView iv2;
    private TextView tv2;
    private View line2;
    private RelativeLayout ll3;
    private ImageView iv3;
    private TextView tv3;
    private View line3;
    private RelativeLayout ll4;
    private ImageView iv4;
    private TextView tv4;
    private View line4;
    private RelativeLayout ll5;
    private ImageView iv5;
    private TextView tv5;
    private View line5;

    private int position = 0;

    private OnTabBarClick onTabBarClick;

    private int tabCount = 5;

    private int[] pressIcons;
    private int[] normalIcons;
    private int textSize;
    private int textColorNormal;
    private int textColorPress;
    private MarkView mv0;
    private MarkView mv1;
    private MarkView mv2;
    private MarkView mv3;
    private MarkView mv4;
    private MarkView mv5;
    private boolean showMarkView;
    private boolean showImg;
    private boolean showText;
    private boolean showUnderLine;
    private boolean autoIconColor;

    public static final int TAB_COUNT_TWO = 2;
    public static final int TAB_COUNT_THREE = 3;
    public static final int TAB_COUNT_FOUR = 4;
    public static final int TAB_COUNT_FIVE = 5;
    public static final int TAB_COUNT_SIX = 6;

    @Retention(SOURCE)
    @IntDef({TAB_COUNT_TWO, TAB_COUNT_THREE, TAB_COUNT_FOUR, TAB_COUNT_FIVE, TAB_COUNT_SIX})
    public @interface TabCount {
    }

    public interface OnTabBarClick {
        void onTabClick(ImageView iv, TextView tv, int position, boolean changed);
    }

    public TabBar(Context context) {
        super(context);
        init(context, null);
    }

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(value = 21)
    public TabBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }
        View root = LayoutInflater.from(context).inflate(R.layout.tab_bar_layout, this, false);
        ll0 = (RelativeLayout) root.findViewById(R.id.rl0);
        iv0 = (ImageView) root.findViewById(R.id.iv0);
        tv0 = (TextView) root.findViewById(R.id.tv0);
        mv0 = (MarkView) root.findViewById(R.id.mv0);
        line0 = root.findViewById(R.id.line_0);
        ll1 = (RelativeLayout) root.findViewById(R.id.rl1);
        iv1 = (ImageView) root.findViewById(R.id.iv1);
        tv1 = (TextView) root.findViewById(R.id.tv1);
        mv1 = (MarkView) root.findViewById(R.id.mv1);
        line1 = root.findViewById(R.id.line_1);
        ll2 = (RelativeLayout) root.findViewById(R.id.rl2);
        iv2 = (ImageView) root.findViewById(R.id.iv2);
        tv2 = (TextView) root.findViewById(R.id.tv2);
        mv2 = (MarkView) root.findViewById(R.id.mv2);
        line2 = root.findViewById(R.id.line_2);
        ll3 = (RelativeLayout) root.findViewById(R.id.rl3);
        iv3 = (ImageView) root.findViewById(R.id.iv3);
        tv3 = (TextView) root.findViewById(R.id.tv3);
        mv3 = (MarkView) root.findViewById(R.id.mv3);
        line3 = root.findViewById(R.id.line_3);
        ll4 = (RelativeLayout) root.findViewById(R.id.rl4);
        iv4 = (ImageView) root.findViewById(R.id.iv4);
        tv4 = (TextView) root.findViewById(R.id.tv4);
        mv4 = (MarkView) root.findViewById(R.id.mv4);
        line4 = root.findViewById(R.id.line_4);
        ll5 = (RelativeLayout) root.findViewById(R.id.rl5);
        iv5 = (ImageView) root.findViewById(R.id.iv5);
        tv5 = (TextView) root.findViewById(R.id.tv5);
        mv5 = (MarkView) root.findViewById(R.id.mv5);
        line5 = root.findViewById(R.id.line_5);
        initAttrs(context, attrs);
        initEvent();
        addView(root);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.TabBar);
            int imageSize = t.getDimensionPixelSize(R.styleable.TabBar_tb_imageSize, 60);
            setImageSize(imageSize, false);
            int textTopMargin = t.getDimensionPixelSize(R.styleable.TabBar_tb_textTopMargin, 5);
            setTextTopMargin(textTopMargin, false);
            textSize = t.getDimensionPixelSize(R.styleable.TabBar_tb_textSize, 40);
            int markPointSize = t.getDimensionPixelSize(R.styleable.TabBar_tb_markPointSize, 24);
            setMarkPointSize(markPointSize);
            int markTextSize = t.getDimensionPixelSize(R.styleable.TabBar_tb_markTextSize, 34);
            setMarkTextSize(markTextSize);
            int markTextColor = t.getColor(R.styleable.TabBar_tb_markTextColor, 0xffffffff);
            setMarkTextColor(markTextColor);
            int markBgColor = t.getColor(R.styleable.TabBar_tb_markBgColor, 0xffff0000);
            setMarkBgColor(markBgColor);
            showMarkView = t.getBoolean(R.styleable.TabBar_tb_showMarkView, false);
            showImg = t.getBoolean(R.styleable.TabBar_tb_showImg, true);
            showText = t.getBoolean(R.styleable.TabBar_tb_showText, true);
            showUnderLine = t.getBoolean(R.styleable.TabBar_tb_showUnderline, false);
            autoIconColor = t.getBoolean(R.styleable.TabBar_tb_autoIconColor, false);

            setVisible(mv0, showMarkView);
            setVisible(mv1, showMarkView);
            setVisible(mv2, showMarkView);
            setVisible(mv3, showMarkView);
            setVisible(mv4, showMarkView);
            setVisible(mv5, showMarkView);

            setVisible(tv0, showText);
            setVisible(tv1, showText);
            setVisible(tv2, showText);
            setVisible(tv3, showText);
            setVisible(tv4, showText);
            setVisible(tv5, showText);

            setVisible(iv0, showImg);
            setVisible(iv1, showImg);
            setVisible(iv2, showImg);
            setVisible(iv3, showImg);
            setVisible(iv4, showImg);
            setVisible(iv5, showImg);

            textColorNormal = t.getColor(R.styleable.TabBar_tb_textColorNormal, 0x7f999999);
            textColorPress = t.getColor(R.styleable.TabBar_tb_textColorPress, 0xff000000);
            tabCount = t.getInt(R.styleable.TabBar_tb_tabCount, 5);
            CharSequence[] textArray = t.getTextArray(R.styleable.TabBar_tb_tabNames);
            if (textArray != null) {
                if (tabCount == textArray.length) {
                    for (int i = 0; i < textArray.length; i++) {
                        switch (tabCount) {
                            case 1:
                                if (i == 0) {
                                    tv0.setText(textArray[0]);
                                }
                                setVisible(ll1, false);
                                setVisible(ll2, false);
                                setVisible(ll3, false);
                                setVisible(ll4, false);
                                setVisible(ll5, false);
                                break;
                            case 2:
                                if (i == 0) {
                                    tv0.setText(textArray[0]);
                                }
                                if (i == 1) {
                                    tv1.setText(textArray[1]);
                                }
                                setVisible(ll2, false);
                                setVisible(ll3, false);
                                setVisible(ll4, false);
                                setVisible(ll5, false);
                                break;
                            case 3:
                                if (i == 0) {
                                    tv0.setText(textArray[0]);
                                }
                                if (i == 1) {
                                    tv1.setText(textArray[1]);
                                }
                                if (i == 2) {
                                    tv2.setText(textArray[2]);
                                }
                                setVisible(ll3, false);
                                setVisible(ll4, false);
                                setVisible(ll5, false);
                                break;
                            case 4:
                                if (i == 0) {
                                    tv0.setText(textArray[0]);
                                }
                                if (i == 1) {
                                    tv1.setText(textArray[1]);
                                }
                                if (i == 2) {
                                    tv2.setText(textArray[2]);
                                }
                                if (i == 3) {
                                    tv3.setText(textArray[3]);
                                }
                                setVisible(ll4, false);
                                setVisible(ll5, false);
                                break;
                            case 5:
                                if (i == 0) {
                                    tv0.setText(textArray[0]);
                                }
                                if (i == 1) {
                                    tv1.setText(textArray[1]);
                                }
                                if (i == 2) {
                                    tv2.setText(textArray[2]);
                                }
                                if (i == 3) {
                                    tv3.setText(textArray[3]);
                                }
                                if (i == 4) {
                                    tv4.setText(textArray[4]);
                                }
                                setVisible(ll5, false);
                                break;
                            case 6:
                                if (i == 0) {
                                    tv0.setText(textArray[0]);
                                }
                                if (i == 1) {
                                    tv1.setText(textArray[1]);
                                }
                                if (i == 2) {
                                    tv2.setText(textArray[2]);
                                }
                                if (i == 3) {
                                    tv3.setText(textArray[3]);
                                }
                                if (i == 4) {
                                    tv4.setText(textArray[4]);
                                }
                                if (i == 5) {
                                    tv5.setText(textArray[5]);
                                }
                                break;
                        }

                    }
                } else {
                    throw new RuntimeException("Tab名称和Tab数量不一致");
                }
            }
            t.recycle();
        } else {
            textSize = 40;
        }
        tv0.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    
        textSize = ScreenAdapterUtil.getInstance().getScaledValue(textSize);
    }

    public int getSelection() {
        return position;
    }

    
    public TabBar setTabNames(String... tabNames) {
        if (tabNames.length == 0) {
            return this;
        }
        this.tabCount = tabNames.length;
        for (int i = 0; i < tabNames.length; i++) {
            switch (tabCount) {
                case 1:
                    if (i == 0) {
                        tv0.setText(tabNames[0]);
                    }
                    setVisible(ll1, false);
                    setVisible(ll2, false);
                    setVisible(ll3, false);
                    setVisible(ll4, false);
                    setVisible(ll5, false);
                    break;
                case 2:
                    if (i == 0) {
                        tv0.setText(tabNames[0]);
                    }
                    if (i == 1) {
                        tv1.setText(tabNames[1]);
                    }
                    setVisible(ll2, false);
                    setVisible(ll3, false);
                    setVisible(ll4, false);
                    setVisible(ll5, false);
                    break;
                case 3:
                    if (i == 0) {
                        tv0.setText(tabNames[0]);
                    }
                    if (i == 1) {
                        tv1.setText(tabNames[1]);
                    }
                    if (i == 2) {
                        tv2.setText(tabNames[2]);
                    }
                    setVisible(ll3, false);
                    setVisible(ll4, false);
                    setVisible(ll5, false);
                    break;
                case 4:
                    if (i == 0) {
                        tv0.setText(tabNames[0]);
                    }
                    if (i == 1) {
                        tv1.setText(tabNames[1]);
                    }
                    if (i == 2) {
                        tv2.setText(tabNames[2]);
                    }
                    if (i == 3) {
                        tv3.setText(tabNames[3]);
                    }
                    setVisible(ll4, false);
                    setVisible(ll5, false);
                    break;
                case 5:
                    if (i == 0) {
                        tv0.setText(tabNames[0]);
                    }
                    if (i == 1) {
                        tv1.setText(tabNames[1]);
                    }
                    if (i == 2) {
                        tv2.setText(tabNames[2]);
                    }
                    if (i == 3) {
                        tv3.setText(tabNames[3]);
                    }
                    if (i == 4) {
                        tv4.setText(tabNames[4]);
                    }
                    setVisible(ll5, false);
                    break;
                case 6:
                    if (i == 0) {
                        tv0.setText(tabNames[0]);
                    }
                    if (i == 1) {
                        tv1.setText(tabNames[1]);
                    }
                    if (i == 2) {
                        tv2.setText(tabNames[2]);
                    }
                    if (i == 3) {
                        tv3.setText(tabNames[3]);
                    }
                    if (i == 4) {
                        tv4.setText(tabNames[4]);
                    }
                    if (i == 5) {
                        tv5.setText(tabNames[5]);
                    }
                    break;
            }
        }
        return this;
    }
    
    /**
     * Set the icon resource id when tab is pressed.
     *
     * @param iconResIds iconResIds
     * @return self
     */
    public TabBar setPressIconRes(int... iconResIds) {
        this.pressIcons = iconResIds;
        return this;
    }

    /**
     * Set the icon resource id when tab is normal.
     *
     * @param iconResIds iconResIds
     * @return self
     */
    public TabBar setNormalIconRes(int... iconResIds) {
        this.normalIcons = iconResIds;
        return this;
    }

    public TabBar setTextSize(int textSizePx) {
        this.textSize = ScreenAdapterUtil.getInstance().getScaledValue(textSizePx);
        return this;
    }


    public TabBar setTextTopMargin(int marginTop) {
        setTextTopMargin(marginTop, true);
        return this;
    }

    private TabBar setTextTopMargin(int marginTop, boolean autoSize) {
        int size = marginTop;
        if (autoSize) {
            size = ScreenAdapterUtil.getInstance().getScaledValue(marginTop);
        }
        LayoutParams lp0 = (LayoutParams) tv0.getLayoutParams();
        LayoutParams lp1 = (LayoutParams) tv1.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) tv2.getLayoutParams();
        LayoutParams lp3 = (LayoutParams) tv3.getLayoutParams();
        LayoutParams lp4 = (LayoutParams) tv4.getLayoutParams();
        LayoutParams lp5 = (LayoutParams) tv5.getLayoutParams();
        lp0.topMargin = size;
        lp1.topMargin = size;
        lp2.topMargin = size;
        lp3.topMargin = size;
        lp4.topMargin = size;
        lp5.topMargin = size;
        tv0.setLayoutParams(lp0);
        tv1.setLayoutParams(lp1);
        tv2.setLayoutParams(lp2);
        tv3.setLayoutParams(lp3);
        tv4.setLayoutParams(lp4);
        tv5.setLayoutParams(lp5);
        return this;
    }

    public TabBar setImageSize(int imageSize) {
        setImageSize(imageSize, true);
        return this;
    }

    public TabBar setImageSize(int imageSize, boolean autoSize) {
        int size = imageSize;
        if (autoSize) {
            size = ScreenAdapterUtil.getInstance().getScaledValue(imageSize);
        }
        LayoutParams lp0 = (LayoutParams) iv0.getLayoutParams();
        LayoutParams lp1 = (LayoutParams) iv1.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) iv2.getLayoutParams();
        LayoutParams lp3 = (LayoutParams) iv3.getLayoutParams();
        LayoutParams lp4 = (LayoutParams) iv4.getLayoutParams();
        LayoutParams lp5 = (LayoutParams) iv5.getLayoutParams();
        lp0.width = size;
        lp0.height = size;
        lp1.width = size;
        lp1.height = size;
        lp2.width = size;
        lp2.height = size;
        lp3.width = size;
        lp3.height = size;
        lp4.width = size;
        lp4.height = size;
        lp5.width = size;
        lp5.height = size;
        iv0.setLayoutParams(lp0);
        iv1.setLayoutParams(lp1);
        iv2.setLayoutParams(lp2);
        iv3.setLayoutParams(lp3);
        iv4.setLayoutParams(lp4);
        iv5.setLayoutParams(lp5);
        return this;
    }

    public TabBar setMarkPointSize(int pointSizePx) {
        mv0.setPointSize(pointSizePx);
        mv1.setPointSize(pointSizePx);
        mv2.setPointSize(pointSizePx);
        mv3.setPointSize(pointSizePx);
        mv4.setPointSize(pointSizePx);
        mv5.setPointSize(pointSizePx);
        return this;
    }

    public TabBar setMarkTextSize(int textSizePx) {
        mv0.setTextSizeInPx(textSizePx);
        mv1.setTextSizeInPx(textSizePx);
        mv2.setTextSizeInPx(textSizePx);
        mv3.setTextSizeInPx(textSizePx);
        mv4.setTextSizeInPx(textSizePx);
        mv5.setTextSizeInPx(textSizePx);
        return this;
    }

    public TabBar setMarkTextColor(int color) {
        mv0.setTextColor(color);
        mv1.setTextColor(color);
        mv2.setTextColor(color);
        mv3.setTextColor(color);
        mv4.setTextColor(color);
        mv5.setTextColor(color);
        return this;
    }

    public TabBar setMarkTextColorRes(int colorRes) {
        mv0.setTextColorRes(colorRes);
        mv1.setTextColorRes(colorRes);
        mv2.setTextColorRes(colorRes);
        mv3.setTextColorRes(colorRes);
        mv4.setTextColorRes(colorRes);
        mv5.setTextColorRes(colorRes);
        return this;
    }

    public TabBar setMarkBgColor(int color) {
        mv0.setBgColor(color);
        mv1.setBgColor(color);
        mv2.setBgColor(color);
        mv3.setBgColor(color);
        mv4.setBgColor(color);
        mv5.setBgColor(color);
        return this;
    }

    public TabBar setMarkBgColorRes(int colorRes) {
        mv0.setBgColorRes(colorRes);
        mv1.setBgColorRes(colorRes);
        mv2.setBgColorRes(colorRes);
        mv3.setBgColorRes(colorRes);
        mv4.setBgColorRes(colorRes);
        mv5.setBgColorRes(colorRes);
        return this;
    }

    public TabBar showMarkViewAt(int position) {
        switch (position) {
            case 0:
                mv0.setVisibility(VISIBLE);
                break;
            case 1:
                mv1.setVisibility(VISIBLE);
                break;
            case 2:
                mv2.setVisibility(VISIBLE);
                break;
            case 3:
                mv3.setVisibility(VISIBLE);
                break;
            case 4:
                mv4.setVisibility(VISIBLE);
                break;
            case 5:
                mv5.setVisibility(VISIBLE);
                break;
        }
        return this;
    }

    public TabBar setMarkShapeAtPosition(int position, MarkView.MarkShape bgShape) {
        switch (position) {
            case 0:
                mv0.setBgShape(bgShape);
                break;
            case 1:
                mv1.setBgShape(bgShape);
                break;
            case 2:
                mv2.setBgShape(bgShape);
                break;
            case 3:
                mv3.setBgShape(bgShape);
                break;
            case 4:
                mv4.setBgShape(bgShape);
                break;
            case 5:
                mv5.setBgShape(bgShape);
                break;
        }
        return this;
    }

    public TabBar setMarkNumberAtPosition(int position, int markNumber) {
        switch (position) {
            case 0:
                mv0.setMarkNumber(markNumber);
                break;
            case 1:
                mv1.setMarkNumber(markNumber);
                break;
            case 2:
                mv2.setMarkNumber(markNumber);
                break;
            case 3:
                mv3.setMarkNumber(markNumber);
                break;
            case 4:
                mv4.setMarkNumber(markNumber);
                break;
            case 5:
                mv5.setMarkNumber(markNumber);
                break;
        }
        return this;
    }

    public TabBar setMaxMarkNumberAtPosition(int position, int maxMarkNumber) {
        switch (position) {
            case 0:
                mv0.setMaxMarkNumber(maxMarkNumber);
                break;
            case 1:
                mv1.setMaxMarkNumber(maxMarkNumber);
                break;
            case 2:
                mv2.setMaxMarkNumber(maxMarkNumber);
                break;
            case 3:
                mv3.setMaxMarkNumber(maxMarkNumber);
                break;
            case 4:
                mv4.setMaxMarkNumber(maxMarkNumber);
                break;
            case 5:
                mv5.setMaxMarkNumber(maxMarkNumber);
                break;
        }
        return this;
    }

    public TabBar hideMarkViewAt(int position) {
        switch (position) {
            case 0:
                mv0.setVisibility(GONE);
                break;
            case 1:
                mv1.setVisibility(GONE);
                break;
            case 2:
                mv2.setVisibility(GONE);
                break;
            case 3:
                mv3.setVisibility(GONE);
                break;
            case 4:
                mv4.setVisibility(GONE);
                break;
            case 5:
                mv5.setVisibility(GONE);
                break;
        }
        return this;
    }

    public void update() {
        mv0.update();
        mv1.update();
        mv2.update();
        mv3.update();
        mv4.update();
        mv5.update();
        tv0.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (showImg) {
            if (pressIcons != null && pressIcons.length > 0) {
                for (int i = 0; i < pressIcons.length; i++) {
                    if (position == i) {
                        switch (i) {
                            case 0:
                                iv0.setImageResource(pressIcons[0]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv0, textColorPress);
                                }
                                tv0.setTextColor(textColorPress);
                                if (showUnderLine) {
                                    line0.setVisibility(VISIBLE);
                                    line0.setBackgroundColor(textColorPress);

                                    setVisible2(line1, false);
                                    setVisible2(line2, false);
                                    setVisible2(line3, false);
                                    setVisible2(line4, false);
                                }
                                break;
                            case 1:
                                iv1.setImageResource(pressIcons[1]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv1, textColorPress);
                                }
                                tv1.setTextColor(textColorPress);
                                if (showUnderLine) {
                                    line1.setVisibility(VISIBLE);
                                    line1.setBackgroundColor(textColorPress);

                                    setVisible2(line0, false);
                                    setVisible2(line2, false);
                                    setVisible2(line3, false);
                                    setVisible2(line4, false);
                                }
                                break;
                            case 2:
                                iv2.setImageResource(pressIcons[2]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv2, textColorPress);
                                }
                                tv2.setTextColor(textColorPress);
                                if (showUnderLine) {
                                    line2.setVisibility(VISIBLE);
                                    line2.setBackgroundColor(textColorPress);

                                    setVisible2(line0, false);
                                    setVisible2(line1, false);
                                    setVisible2(line3, false);
                                    setVisible2(line4, false);
                                }
                                break;
                            case 3:
                                iv3.setImageResource(pressIcons[3]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv3, textColorPress);
                                }
                                tv3.setTextColor(textColorPress);
                                if (showUnderLine) {
                                    line3.setVisibility(VISIBLE);
                                    line3.setBackgroundColor(textColorPress);

                                    setVisible2(line0, false);
                                    setVisible2(line1, false);
                                    setVisible2(line2, false);
                                    setVisible2(line4, false);
                                }
                                break;
                            case 4:
                                iv4.setImageResource(pressIcons[4]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv4, textColorPress);
                                }
                                tv4.setTextColor(textColorPress);
                                if (showUnderLine) {
                                    line4.setVisibility(VISIBLE);
                                    line4.setBackgroundColor(textColorPress);

                                    setVisible2(line0, false);
                                    setVisible2(line1, false);
                                    setVisible2(line2, false);
                                    setVisible2(line3, false);
                                }
                                break;
                            case 5:
                                iv5.setImageResource(pressIcons[5]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv5, textColorPress);
                                }
                                tv5.setTextColor(textColorPress);
                                if (showUnderLine) {
                                    line5.setVisibility(VISIBLE);
                                    line5.setBackgroundColor(textColorPress);

                                    setVisible2(line0, false);
                                    setVisible2(line1, false);
                                    setVisible2(line2, false);
                                    setVisible2(line3, false);
                                    setVisible2(line4, false);
                                }
                                break;
                        }
                        break;
                    }
                }
            }
            if (normalIcons != null && normalIcons.length > 0) {
                for (int i = 0; i < normalIcons.length; i++) {
                    if (position != i) {
                        switch (i) {
                            case 0:
                                iv0.setImageResource(normalIcons[0]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv0, textColorNormal);
                                }
                                tv0.setTextColor(textColorNormal);
                                break;
                            case 1:
                                iv1.setImageResource(normalIcons[1]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv1, textColorNormal);
                                }
                                tv1.setTextColor(textColorNormal);
                                break;
                            case 2:
                                iv2.setImageResource(normalIcons[2]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv2, textColorNormal);
                                }
                                tv2.setTextColor(textColorNormal);
                                break;
                            case 3:
                                iv3.setImageResource(normalIcons[3]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv3, textColorNormal);
                                }
                                tv3.setTextColor(textColorNormal);
                                break;
                            case 4:
                                iv4.setImageResource(normalIcons[4]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv4, textColorNormal);
                                }
                                tv4.setTextColor(textColorNormal);
                                break;
                            case 5:
                                iv5.setImageResource(normalIcons[5]);
                                if (autoIconColor) {
                                    ColorUtil.setIconColor(iv5, textColorNormal);
                                }
                                tv5.setTextColor(textColorNormal);
                                break;
                        }
                    }
                }
            }
        } else if (showText) {
            tv0.setTextColor(position == 0 ? textColorPress : textColorNormal);
            tv1.setTextColor(position == 1 ? textColorPress : textColorNormal);
            tv2.setTextColor(position == 2 ? textColorPress : textColorNormal);
            tv3.setTextColor(position == 3 ? textColorPress : textColorNormal);
            tv4.setTextColor(position == 4 ? textColorPress : textColorNormal);
            tv5.setTextColor(position == 5 ? textColorPress : textColorNormal);

            if (showUnderLine) {
                line0.setBackgroundColor(position == 0 ? textColorPress : textColorNormal);
                line1.setBackgroundColor(position == 1 ? textColorPress : textColorNormal);
                line2.setBackgroundColor(position == 2 ? textColorPress : textColorNormal);
                line3.setBackgroundColor(position == 3 ? textColorPress : textColorNormal);
                line4.setBackgroundColor(position == 4 ? textColorPress : textColorNormal);
                line5.setBackgroundColor(position == 5 ? textColorPress : textColorNormal);

                setVisible2(line0, position == 0);
                setVisible2(line1, position == 1);
                setVisible2(line2, position == 2);
                setVisible2(line3, position == 3);
                setVisible2(line4, position == 4);
                setVisible2(line5, position == 5);
            }
        }

    }

    private void setVisible(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    private void setVisible2(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? VISIBLE : INVISIBLE);
        }
    }

    private void initEvent() {
        ll0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelection(0);
            }
        });
        ll1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelection(1);
            }
        });
        ll2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelection(2);
            }
        });
        ll3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelection(3);
            }
        });
        ll4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelection(4);
            }
        });
        ll5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelection(5);
            }
        });
    }

    public RelativeLayout getLl0() {
        return ll0;
    }

    public RelativeLayout getLl1() {
        return ll1;
    }

    public RelativeLayout getLl2() {
        return ll2;
    }

    public RelativeLayout getLl3() {
        return ll3;
    }

    public RelativeLayout getLl4() {
        return ll4;
    }
    
    public RelativeLayout getLl5() {
        return ll5;
    }
    
    public void setSelection(int position) {
        if (showImg) {
            if (normalIcons == null) {
                throw new RuntimeException("normalIcons can not be null if showImg = true.Please invoke the method TabBar#setNormalIconRes().");
            }
            if (pressIcons == null) {
                throw new RuntimeException("pressIcons can not be null if showImg = true.Please invoke the method TabBar#setPressIconRes().");
            }
        }
        if (onTabBarClick != null) {
            switch (position) {
                case 0:
                    onTabBarClick.onTabClick(iv0, tv0, position, this.position != position);
                    break;
                case 1:
                    onTabBarClick.onTabClick(iv1, tv1, position, this.position != position);
                    break;
                case 2:
                    onTabBarClick.onTabClick(iv2, tv2, position, this.position != position);
                    break;
                case 3:
                    onTabBarClick.onTabClick(iv3, tv3, position, this.position != position);
                    break;
                case 4:
                    onTabBarClick.onTabClick(iv4, tv4, position, this.position != position);
                    break;
                case 5:
                    onTabBarClick.onTabClick(iv5, tv5, position, this.position != position);
                    break;
            }
        }
        this.position = position;
        update();
    }

    public void setOnTabBarClickListener(OnTabBarClick onTabBarClick) {
        this.onTabBarClick = onTabBarClick;
    }
    
    
    public ImageView getIv0() {
        return iv0;
    }
    
    public ImageView getIv1() {
        return iv1;
    }
    
    public ImageView getIv2() {
        return iv2;
    }
    
    public ImageView getIv3() {
        return iv3;
    }
    
    public ImageView getIv4() {
        return iv4;
    }
    
    public ImageView getIv5() {
        return iv5;
    }
    
    public TextView getTv0() {
        return tv0;
    }
    
    public TextView getTv1() {
        return tv1;
    }
    
    public TextView getTv2() {
        return tv2;
    }
    
    public TextView getTv3() {
        return tv3;
    }
    
    public TextView getTv4() {
        return tv4;
    }
    
    public TextView getTv5() {
        return tv5;
    }
    
    public TabBar setTextColorPress(int textColorPress) {
        this.textColorPress = textColorPress;
        return this;
    }
    
    public TabBar setTextColorNormal(int textColorNormal) {
        this.textColorNormal = textColorNormal;
        return this;
    }
    
}
