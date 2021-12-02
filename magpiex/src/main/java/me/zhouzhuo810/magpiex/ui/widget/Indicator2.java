package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.ui.widget.adapter.BaseFragmentPager2Adapter;
import me.zhouzhuo810.magpiex.ui.widget.en.IndicatorType;
import me.zhouzhuo810.magpiex.ui.widget.intef.IPagerIndicator2;
import me.zhouzhuo810.magpiex.ui.widget.intef.IResProvider;
import me.zhouzhuo810.magpiex.utils.ColorUtil;
import me.zhouzhuo810.magpiex.utils.NavigatorHelper;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;


/**
 * ViewPager2 指示器
 *
 * @author zhouzhuo810
 * @date 6/21/21 3:18 PM
 */
public class Indicator2 extends HorizontalScrollView implements IPagerIndicator2, NavigatorHelper.OnNavigatorScrollListener {
    
    private IndicatorType indicatorType = IndicatorType.RoundPoint;
    
    private ViewPager2 mViewPager2;
    
    private LinearLayout mIndicatorContainer;
    private boolean shouldExpand = false;
    private Paint selectPaint;
    private Paint unSelectPaint;
    private Paint underlinePaint;
    private Paint roundBgPaint;
    private Paint gapLinePaint;
    private int colorSelectPoint = 0xff438cff;
    private int colorUnSelectPoint = 0xff000000;
    private int selectPointSize = 100;
    private int unSelectPointSize = 90;
    private int spacing = 8;
    
    private boolean showUnderline = true;
    private boolean showGapLine = false;
    private boolean showRoundBg = false;
    private int gapLineColor = 0xff000000;
    private int gapLineWidth = 1;
    private int gapLinePadding = 0;
    private int tabTextColorSelect = 0xff438cff;
    private int tabTextColorUnSelect = 0xff000000;
    private int tabRoundBgColor = 0xff438cff;
    private int tabTextSizeUnSelect = 30;
    private int tabIconTextMargin = 10;
    private int tabTextSizeSelect = 40;
    private int underlineHeight = 10;
    private int underlinePadding = 0;
    private int underlineColor = 0xff438cff;
    private int tabBgNormalId;
    private int tabBgSelectId;
    private TabOrientation tabTextIconOrientation = TabOrientation.VERTICAL;
    
    private int tabPadding = 24;
    private int tabIconSize = 80;
    
    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;
    
    private int currentPosition = 0;
    private float currentPositionOffset = 0f;
    
    private int tabCount;
    private int lastScrollX = 0;
    
    private boolean horizontalHideIconMode = false;
    private ViewPager2.OnPageChangeCallback mOnPageChangeListener;
    private RecyclerView.AdapterDataObserver mDataSetObserver;
    
    private NavigatorHelper mNavigatorHelper;
    
    
    public enum TabOrientation {
        VERTICAL, HORIZONTAL
    }
    
    public Indicator2(Context context) {
        super(context);
        init(context, null);
    }
    
    public Indicator2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        
    }
    
    public Indicator2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        
    }
    
    private void init(Context context, AttributeSet attrs) {
        setHorizontalScrollBarEnabled(false);
        
        setFillViewport(true);
        setWillNotDraw(false);
        
        mNavigatorHelper = new NavigatorHelper();
        mNavigatorHelper.setNavigatorScrollListener(this);
        
        //init attrs
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Indicator2);
            boolean isNeedScaleInPx = a.getBoolean(R.styleable.Indicator2_i2_isNeedScaleInPx, true);
            
            shouldExpand = a.getBoolean(R.styleable.Indicator2_i2_shouldTabExpand, false);
            int indicatorInt = a.getInt(R.styleable.Indicator2_i2_indicator_type, 0);
            int tabOriInt = a.getInt(R.styleable.Indicator2_i2_tabOrientation, 0);
            colorSelectPoint = a.getColor(R.styleable.Indicator2_i2_selectPointColor, 0xff438cff);
            colorUnSelectPoint = a.getColor(R.styleable.Indicator2_i2_normalPointColor, 0xff000000);
            selectPointSize = a.getDimensionPixelSize(R.styleable.Indicator2_i2_selectPointSize, 100);
            unSelectPointSize = a.getDimensionPixelSize(R.styleable.Indicator2_i2_normalPointSize, 90);
            spacing = a.getDimensionPixelSize(R.styleable.Indicator2_i2_pointSpacing, 8);
            tabBgNormalId = a.getResourceId(R.styleable.Indicator2_i2_normalTabBg, -1);
            tabBgSelectId = a.getResourceId(R.styleable.Indicator2_i2_selectTabBg, -1);
            
            tabTextColorSelect = a.getColor(R.styleable.Indicator2_i2_selectTabTextColor, 0xff438cff);
            tabTextColorUnSelect = a.getColor(R.styleable.Indicator2_i2_normalTabTextColor, 0xff000000);
            tabTextSizeSelect = a.getDimensionPixelSize(R.styleable.Indicator2_i2_selectTabTextSize, 40);
            tabTextSizeUnSelect = a.getDimensionPixelSize(R.styleable.Indicator2_i2_normalTabTextSize, 40);
            tabIconTextMargin = a.getDimensionPixelSize(R.styleable.Indicator2_i2_tabIconTextMargin, 10);
            showGapLine = a.getBoolean(R.styleable.Indicator2_i2_showGapLine, false);
            gapLineWidth = a.getDimensionPixelSize(R.styleable.Indicator2_i2_gapLineWidth, 1);
            gapLinePadding = a.getDimensionPixelSize(R.styleable.Indicator2_i2_gapLinePadding, 0);
            gapLineColor = a.getColor(R.styleable.Indicator2_i2_gapLineColor, 0xff000000);
            showUnderline = a.getBoolean(R.styleable.Indicator2_i2_showUnderline, true);
            underlineHeight = a.getDimensionPixelSize(R.styleable.Indicator2_i2_underlineHeight, 10);
            underlinePadding = a.getDimensionPixelSize(R.styleable.Indicator2_i2_underlinePadding, 20);
            showRoundBg = a.getBoolean(R.styleable.Indicator2_i2_showRoundBg, false);
            tabPadding = a.getDimensionPixelSize(R.styleable.Indicator2_i2_tabPadding, 24);
            tabIconSize = a.getDimensionPixelSize(R.styleable.Indicator2_i2_tabIconSize, 80);
            underlineColor = a.getColor(R.styleable.Indicator2_i2_underlineColor, 0xff438cff);
            tabRoundBgColor = a.getColor(R.styleable.Indicator2_i2_roundBgColor, 0xff438cff);
            horizontalHideIconMode = a.getBoolean(R.styleable.Indicator2_i2_tabIsHorizontalHideIcon, false);
            
            if (isNeedScaleInPx && !isInEditMode()) {
                selectPointSize = SimpleUtil.getScaledValue(selectPointSize);
                unSelectPointSize = SimpleUtil.getScaledValue(unSelectPointSize);
                spacing = SimpleUtil.getScaledValue(spacing);
                tabTextSizeSelect = SimpleUtil.getScaledValue(tabTextSizeSelect, true);
                tabIconTextMargin = SimpleUtil.getScaledValue(tabIconTextMargin);
                tabTextSizeUnSelect = SimpleUtil.getScaledValue(tabTextSizeUnSelect, true);
                underlineHeight = SimpleUtil.getScaledValue(underlineHeight);
                underlinePadding = SimpleUtil.getScaledValue(underlinePadding);
                tabPadding = SimpleUtil.getScaledValue(tabPadding);
                tabIconSize = SimpleUtil.getScaledValue(tabIconSize);
                gapLineWidth = SimpleUtil.getScaledValue(gapLineWidth);
                gapLinePadding = SimpleUtil.getScaledValue(gapLinePadding);
            }
            
            switch (indicatorInt) {
                case 0:
                    indicatorType = IndicatorType.RoundPoint;
                    break;
                case 1:
                    indicatorType = IndicatorType.TabWithText;
                    break;
                case 2:
                    indicatorType = IndicatorType.TabWithIcon;
                    break;
                case 3:
                    indicatorType = IndicatorType.TabWithIconAndText;
                    break;
            }
            switch (tabOriInt) {
                case 0:
                    tabTextIconOrientation = TabOrientation.VERTICAL;
                    break;
                case 1:
                    tabTextIconOrientation = TabOrientation.HORIZONTAL;
                    break;
            }
            a.recycle();
        }
        if (indicatorType == IndicatorType.RoundPoint) {
            setMinimumHeight(selectPointSize > unSelectPointSize ? selectPointSize * 2 : unSelectPointSize * 2);
        }
        //initial paints
        initPaints();
        //initial linearLayout
        initContainer(context);
        //initial child layout params
        initParams();
        
    }
    
    private void initPaints() {
        unSelectPaint = new Paint();
        unSelectPaint.setAntiAlias(true);
        unSelectPaint.setColor(colorUnSelectPoint);
        selectPaint = new Paint();
        selectPaint.setAntiAlias(true);
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(colorSelectPoint);
        underlinePaint = new Paint();
        underlinePaint.setTextSize(tabTextSizeSelect);
        underlinePaint.setColor(underlineColor);
        underlinePaint.setStyle(Paint.Style.FILL);
        underlinePaint.setAntiAlias(true);
        roundBgPaint = new Paint();
        roundBgPaint.setTextSize(tabTextSizeSelect);
        roundBgPaint.setColor(tabRoundBgColor);
        roundBgPaint.setStyle(Paint.Style.FILL);
        roundBgPaint.setAntiAlias(true);
        gapLinePaint = new Paint();
        gapLinePaint.setColor(gapLineColor);
        gapLinePaint.setStyle(Paint.Style.FILL);
        gapLinePaint.setAntiAlias(true);
    }
    
    private void initContainer(Context context) {
        mIndicatorContainer = new LinearLayout(context);
        mIndicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
        mIndicatorContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mIndicatorContainer.setGravity(Gravity.CENTER_VERTICAL);
        addView(mIndicatorContainer);
    }
    
    private void initParams() {
        defaultTabLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (indicatorType) {
            case RoundPoint:
                drawPoints(canvas);
                break;
            case TabWithIcon:
            case TabWithText:
            case TabWithIconAndText:
                if (showUnderline) {
                    drawUnderline(canvas);
                }
                if (showRoundBg) {
                    drawRoundBg(canvas);
                }
                if (showGapLine) {
                    drawGapLine(canvas);
                }
                break;
        }
    }
    
    private void drawGapLine(Canvas canvas) {
        if (mViewPager2 != null) {
            for (int i = 0; i < mIndicatorContainer.getChildCount() - 1; i++) {
                View item = getItem(i);
                if (item != null) {
                    float lineLeft = item.getRight() - gapLineWidth / 2.0f;
                    float topY = item.getTop() + gapLinePadding;
                    float bottomY = item.getBottom() - gapLinePadding;
                    canvas.drawRect(lineLeft, topY, lineLeft + gapLineWidth, bottomY, gapLinePaint);
                }
            }
        }
    }
    
    private void drawUnderline(Canvas canvas) {
        if (mViewPager2 != null) {
            View currentTab = getItem(currentPosition);
            if (currentTab != null) {
                float lineLeft = currentTab.getLeft() + underlinePadding;
                float lineRight = currentTab.getRight() - underlinePadding;
                
                // if there is an offset, start interpolating left and right coordinates between current and next tab
                if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
                    View nextTab = getItem(currentPosition + 1);
                    final float nextTabLeft = nextTab.getLeft() + underlinePadding;
                    final float nextTabRight = nextTab.getRight() - underlinePadding;
                    
                    lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
                    lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
                }
                canvas.drawRect(lineLeft, getHeight() - underlineHeight, lineRight, getHeight(), underlinePaint);
            }
        }
    }
    
    private void drawRoundBg(Canvas canvas) {
        if (mViewPager2 != null) {
            View currentTab = getItem(currentPosition);
            if (currentTab != null) {
                float lineLeft = currentTab.getLeft();
                float lineRight = currentTab.getRight();
                
                // if there is an offset, start interpolating left and right coordinates between current and next tab
                if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
                    View nextTab = getItem(currentPosition + 1);
                    final float nextTabLeft = nextTab.getLeft();
                    final float nextTabRight = nextTab.getRight();
                    
                    lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
                    lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
                }
                float textHeight = roundBgPaint.descent() - roundBgPaint.ascent();
                float top = (getHeight() - textHeight) / 2.0f - tabPadding / 3.0f;
                float radius = (textHeight) / 2.0f + tabPadding / 3.0f;
                canvas.drawRoundRect(new RectF(lineLeft, top, lineRight, getHeight() - top), radius, radius, roundBgPaint);
            }
        }
    }
    
    private void drawPoints(Canvas canvas) {
        if (mViewPager2 != null && mViewPager2.getAdapter() != null) {
            int count = mViewPager2.getAdapter().getItemCount();
            int index = mViewPager2.getCurrentItem();
            int uLeft = (getWidth() - unSelectPointSize * count - spacing * (count - 1)) / 2;
            int y = getHeight() / 2;
            int sR = selectPointSize / 2;
            int uR = unSelectPointSize / 2;
            for (int i = 0; i < count; i++) {
                int uX = uLeft + 2 * i * uR + uR + i * spacing;
                canvas.drawCircle(uX, y, uR, unSelectPaint);
            }
            float uX = uLeft + (2 * index + 1) * uR + index * spacing;
            canvas.drawCircle(uX, y, sR, selectPaint);
        }
    }
    
    @Override
    public Indicator2 setCurrentItem(int position, boolean animate) {
        if (mViewPager2 != null) {
            mViewPager2.setCurrentItem(position, animate);
        } else {
            select(position);
        }
        return this;
    }
    
    @Override
    public Indicator2 updateText(int position, String title) {
        if (mViewPager2 == null) {
            return this;
        }
        RecyclerView.Adapter adapter = mViewPager2.getAdapter();
        if (adapter != null) {
            if (adapter instanceof BaseFragmentPager2Adapter) {
                BaseFragmentPager2Adapter pager2Adapter = (BaseFragmentPager2Adapter) adapter;
                pager2Adapter.setPageTitle(position, title);
                TextView tv = (TextView) getItem(position);
                tv.setText(pager2Adapter.getPageTitle(position));
            }
        }
        return this;
    }
    
    @Override
    public Indicator2 setViewPager2(final ViewPager2 viewPager2) {
        RecyclerView.Adapter adapter = viewPager2.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.mViewPager2 = viewPager2;
        mDataSetObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                initIndicator();
            }
        };
        adapter.registerAdapterDataObserver(mDataSetObserver);
        initIndicator();
        addPageChangeListener();
        return this;
    }
    
    @Override
    public Indicator2 select(int position) {
        currentPosition = position;
        switch (indicatorType) {
            case TabWithIcon:
                selectIcon(currentPosition);
                break;
            case TabWithText:
                selectText(currentPosition);
                break;
            case TabWithIconAndText:
                selectIconAndText(currentPosition);
                break;
            default:
                break;
        }
        return this;
    }
    
    @Override
    public Indicator2 setTabTextColorSelect(int tabTextColorSelect) {
        this.tabTextColorSelect = tabTextColorSelect;
        return this;
    }
    
    @Override
    public Indicator2 setTabTextColorUnSelect(int tabTextColorUnSelect) {
        this.tabTextColorUnSelect = tabTextColorUnSelect;
        return this;
    }
    
    @Override
    public Indicator2 setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        underlinePaint.setColor(this.underlineColor);
        return this;
    }
    
    @Override
    public Indicator2 update() {
        switch (indicatorType) {
            case TabWithIcon:
                selectIcon(currentPosition);
                break;
            case TabWithText:
                selectText(currentPosition);
                break;
            case TabWithIconAndText:
                selectIconAndText(currentPosition);
                break;
            default:
                break;
        }
        invalidate();
        return this;
    }
    
    @Override
    public Indicator2 setTabTextIconOrientation(TabOrientation orientation) {
        if (indicatorType != IndicatorType.TabWithIconAndText) {
            return this;
        }
        this.tabTextIconOrientation = orientation;
        switch (orientation) {
            case VERTICAL:
                if (mViewPager2.getAdapter() != null) {
                    for (int i = 0; i < mViewPager2.getAdapter().getItemCount(); i++) {
                        LinearLayout ll = (LinearLayout) getItem(i);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        ImageView iv = (ImageView) ll.getChildAt(0);
                        iv.setVisibility(VISIBLE);
                        TextView tv = (TextView) ll.getChildAt(1);
                        LinearLayout.LayoutParams ivP = (LinearLayout.LayoutParams) iv.getLayoutParams();
                        LinearLayout.LayoutParams tvP = (LinearLayout.LayoutParams) tv.getLayoutParams();
                        ivP.topMargin = 0;
                        tvP.leftMargin = 0;
                        tvP.topMargin = tabIconTextMargin;
                        iv.setLayoutParams(ivP);
                        tv.setLayoutParams(tvP);
                    }
                }
                break;
            case HORIZONTAL:
                if (mViewPager2.getAdapter() != null) {
                    for (int i = 0; i < mViewPager2.getAdapter().getItemCount(); i++) {
                        LinearLayout ll = (LinearLayout) getItem(i);
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ImageView iv = (ImageView) ll.getChildAt(0);
                        TextView tv = (TextView) ll.getChildAt(1);
                        LinearLayout.LayoutParams ivP = (LinearLayout.LayoutParams) iv.getLayoutParams();
                        LinearLayout.LayoutParams tvP = (LinearLayout.LayoutParams) tv.getLayoutParams();
                        ivP.topMargin = 0;
                        tvP.topMargin = 0;
                        tvP.bottomMargin = 0;
                        tvP.leftMargin = tabIconTextMargin;
                        iv.setLayoutParams(ivP);
                        tv.setLayoutParams(tvP);
                    }
                }
                break;
            
        }
        return this;
    }
    
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SaveState saveState = new SaveState(superState);
        saveState.position = currentPosition;
        return saveState;
    }
    
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SaveState savedState = (SaveState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        select(savedState.position);
    }
    
    static class SaveState extends BaseSavedState {
        int position;
        
        @Override
        public int describeContents() {
            return 0;
        }
        
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.position);
        }
        
        public SaveState(Parcelable superState) {
            super(superState);
        }
        
        protected SaveState(Parcel in) {
            super(in);
            this.position = in.readInt();
        }
        
        public static final Creator<SaveState> CREATOR = new Creator<SaveState>() {
            @Override
            public SaveState createFromParcel(Parcel source) {
                return new SaveState(source);
            }
            
            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };
    }
    
    private void scrollToChild(int position, float offset) {
        if (tabCount == 0) {
            return;
        }
        
        View selectedChild = getItem(position);
        if (selectedChild != null) {
            int selectedWidth = selectedChild.getWidth();
            View nextChild = position + 1 < mIndicatorContainer.getChildCount() ? mIndicatorContainer.getChildAt(position + 1) : null;
            int nextWidth = nextChild != null ? nextChild.getWidth() : 0;
            int scrollOffset = (int) ((float) (selectedWidth + nextWidth) * 0.5F * offset);
            int newScrollX = selectedChild.getLeft() + selectedWidth / 2 - this.getWidth() / 2 + scrollOffset;
            
            if (newScrollX != lastScrollX) {
                lastScrollX = newScrollX;
                scrollTo(newScrollX, 0);
            }
        }
        
    }
    
    private void selectIcon(int position) {
        if (mViewPager2 == null) {
            return;
        }
        if (mViewPager2.getAdapter() != null) {
            for (int i = 0; i < mViewPager2.getAdapter().getItemCount(); i++) {
                LinearLayout ll = (LinearLayout) getItem(i);
                ImageView iv = (ImageView) ll.getChildAt(0);
                if (i == position) {
                    if (tabBgSelectId != -1) {
                        ll.setBackgroundResource(tabBgSelectId);
                    }
                    int icon = ((IResProvider) mViewPager2.getAdapter()).getSelectedIcon(i);
                    iv.setImageResource(icon);
                } else {
                    if (tabBgNormalId != -1) {
                        ll.setBackgroundResource(tabBgNormalId);
                    }
                    int icon = ((IResProvider) mViewPager2.getAdapter()).getUnselectedIcon(i);
                    iv.setImageResource(icon);
                }
            }
        }
    }
    
    private void selectIconAndText(int position) {
        if (mViewPager2 == null) {
            return;
        }
        if (mViewPager2.getAdapter() != null) {
            if (!(mViewPager2.getAdapter() instanceof IResProvider)) {
                throw new RuntimeException("ViewPager 's Adapter must implement IResProvider.");
            }
            for (int i = 0; i < mViewPager2.getAdapter().getItemCount(); i++) {
                LinearLayout ll = (LinearLayout) getItem(i);
                ImageView iv = (ImageView) ll.getChildAt(0);
                TextView tv = (TextView) ll.getChildAt(1);
                if (i == position) {
                    iv.setVisibility(VISIBLE);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeSelect);
                    tv.setTextColor(tabTextColorSelect);
                    if (tabBgSelectId != -1) {
                        ll.setBackgroundResource(tabBgSelectId);
                    }
                    int icon = ((IResProvider) mViewPager2.getAdapter()).getSelectedIcon(i);
                    iv.setImageResource(icon);
                } else {
                    if (horizontalHideIconMode && tabTextIconOrientation == TabOrientation.HORIZONTAL) {
                        iv.setVisibility(GONE);
                    } else {
                        iv.setVisibility(VISIBLE);
                    }
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeUnSelect);
                    tv.setTextColor(tabTextColorUnSelect);
                    if (tabBgNormalId != -1) {
                        ll.setBackgroundResource(tabBgNormalId);
                    }
                    int icon = ((IResProvider) mViewPager2.getAdapter()).getUnselectedIcon(i);
                    iv.setImageResource(icon);
                }
            }
        }
    }
    
    private void selectText(final int position) {
        if (mViewPager2 == null) {
            return;
        }
        if (mViewPager2.getAdapter() != null) {
            for (int i = 0; i < mViewPager2.getAdapter().getItemCount(); i++) {
                final TextView tv = (TextView) getItem(i);
                if (i == position) {
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeSelect);
                    tv.setTextColor(tabTextColorSelect);
                    if (tabBgSelectId != -1) {
                        tv.setBackgroundResource(tabBgSelectId);
                    }
                } else {
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeUnSelect);
                    tv.setTextColor(tabTextColorUnSelect);
                    if (tabBgNormalId != -1) {
                        tv.setBackgroundResource(tabBgNormalId);
                    }
                }
            }
        }
    }
    
    public TabOrientation getTabTextIconOrientation() {
        return tabTextIconOrientation;
    }
    
    private View getItem(int position) {
        return mIndicatorContainer.getChildAt(position);
    }
    
    public RecyclerView.AdapterDataObserver getDataSetObserver() {
        return mDataSetObserver;
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            if (mDataSetObserver != null) {
                if (mViewPager2 != null) {
                    RecyclerView.Adapter adapter = mViewPager2.getAdapter();
                    if (adapter != null) {
                        adapter.unregisterAdapterDataObserver(mDataSetObserver);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initIndicator() {
        if (mViewPager2.getAdapter() == null) {
            if (mNavigatorHelper != null) {
                mNavigatorHelper.setTotalCount(0);
            }
            return;
        }
        tabCount = mViewPager2.getAdapter().getItemCount();
        if (mNavigatorHelper != null) {
            mNavigatorHelper.setTotalCount(tabCount);
        }
        switch (indicatorType) {
            case RoundPoint:
                setMinimumWidth(selectPointSize > unSelectPointSize ? selectPointSize * tabCount + spacing * (tabCount - 1) : unSelectPointSize * tabCount + spacing * (tabCount - 1));
                break;
            case TabWithText:
                setUpText();
                selectText(this.mViewPager2.getCurrentItem());
                break;
            case TabWithIcon:
                setUpIcons();
                selectIcon(this.mViewPager2.getCurrentItem());
                break;
            case TabWithIconAndText:
                setUpIconsAndText();
                selectIconAndText(this.mViewPager2.getCurrentItem());
                break;
        }
    }
    
    private void setUpIconsAndText() {
        mIndicatorContainer.removeAllViews();
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(tabIconSize, tabIconSize);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (tabTextIconOrientation == TabOrientation.VERTICAL) {
            ivParams.topMargin = 0;
            tvParams.leftMargin = 0;
            tvParams.topMargin = tabIconTextMargin;
        } else {
            ivParams.topMargin = 0;
            tvParams.topMargin = 0;
            tvParams.leftMargin = tabIconTextMargin;
        }
        
        if (mViewPager2 == null) {
            return;
        }
        if (mViewPager2.getAdapter() != null) {
            if (!(mViewPager2.getAdapter() instanceof IResProvider)) {
                throw new RuntimeException("ViewPager 's Adapter must implement IResProvider.");
            }
            for (int i = 0; i < mViewPager2.getAdapter().getItemCount(); i++) {
                ImageView iv = new ImageView(getContext());
                int icon = ((IResProvider) mViewPager2.getAdapter()).getUnselectedIcon(i);
                iv.setImageResource(icon);
                
                TextView tv = new TextView(getContext());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(tabTextColorUnSelect);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSizeUnSelect);
                tv.setIncludeFontPadding(false);
                tv.setText(((IResProvider) mViewPager2.getAdapter()).getTitle(i));
                
                LinearLayout ll = new LinearLayout(getContext());
                ll.setGravity(Gravity.CENTER);
                if (tabTextIconOrientation == TabOrientation.VERTICAL) {
                    ll.setOrientation(LinearLayout.VERTICAL);
                } else {
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                }
                ll.addView(iv, 0, ivParams);
                ll.addView(tv, 1, tvParams);
                ll.setPadding(tabPadding, 0, tabPadding, 0);
                final int finalI = i;
                ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setCurrentItem(finalI, true);
                    }
                });
                mIndicatorContainer.addView(ll, i, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
            }
        }
    }
    
    private void setUpIcons() {
        mIndicatorContainer.removeAllViews();
        if (mViewPager2 == null) {
            return;
        }
        if (mViewPager2.getAdapter() != null) {
            if (!(mViewPager2.getAdapter() instanceof IResProvider)) {
                throw new RuntimeException("ViewPager 's Adapter must implement IResProvider.");
            }
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(tabIconSize, tabIconSize);
            for (int i = 0; i < mViewPager2.getAdapter().getItemCount(); i++) {
                ImageView iv = new ImageView(getContext());
                int icon = ((IResProvider) mViewPager2.getAdapter()).getUnselectedIcon(i);
                iv.setImageResource(icon);
                
                LinearLayout ll = new LinearLayout(getContext());
                ll.setGravity(Gravity.CENTER);
                ll.addView(iv, 0, ivParams);
                ll.setPadding(tabPadding, 0, tabPadding, 0);
                final int finalI = i;
                ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setCurrentItem(finalI, true);
                    }
                });
                if (!shouldExpand) {
                    defaultTabLayoutParams.leftMargin = tabPadding;
                    defaultTabLayoutParams.rightMargin = tabPadding;
                }
                mIndicatorContainer.addView(ll, i, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
            }
        }
        
    }
    
    private void setUpText() {
        mIndicatorContainer.removeAllViews();
        if (mViewPager2 == null) {
            return;
        }
        if (mViewPager2.getAdapter() != null) {
            for (int i = 0; i < mViewPager2.getAdapter().getItemCount(); i++) {
                TextView tv = new TextView(getContext());
                tv.setFocusable(true);
                tv.setClickable(true);
                tv.setGravity(Gravity.CENTER);
                tv.setIncludeFontPadding(false);
                tv.setText(((IResProvider) mViewPager2.getAdapter()).getTitle(i));
                final int finalI = i;
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setCurrentItem(finalI, true);
                    }
                });
                tv.setPadding(tabPadding, 0, tabPadding, 0);
                
                mIndicatorContainer.addView(tv, i, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
            }
        }
    }
    
    private void addPageChangeListener() {
        if (mOnPageChangeListener == null) {
            mOnPageChangeListener = new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPosition = position;
                    currentPositionOffset = positionOffset;
                    
                    if (mNavigatorHelper != null) {
                        mNavigatorHelper.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                    
                    switch (indicatorType) {
                        case TabWithIcon:
                        case TabWithText:
                        case TabWithIconAndText:
                            if (position >= 0 && position < mIndicatorContainer.getChildCount()) {
                                scrollToChild(position, positionOffset);
                            }
                            break;
                    }
                    
                    if (showUnderline || showGapLine || showRoundBg) {
                        invalidate();
                    }
                }
                
                @Override
                public void onPageSelected(int position) {
                    if (mNavigatorHelper != null) {
                        mNavigatorHelper.onPageSelected(position);
                    }
                }
                
                @Override
                public void onPageScrollStateChanged(int state) {
                    if (mNavigatorHelper != null) {
                        mNavigatorHelper.onPageScrollStateChanged(state);
                    }
                }
            };
            mViewPager2.registerOnPageChangeCallback(mOnPageChangeListener);
        }
    }
    
    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (mViewPager2 != null) {
            View nextTab = getItem(index);
            if (indicatorType == IndicatorType.TabWithText || indicatorType == IndicatorType.TabWithIconAndText) {
                if (tabTextSizeSelect > 0 && tabTextSizeUnSelect > 0 && tabTextSizeSelect != tabTextSizeUnSelect) {
                    final float nextTextSize = tabTextSizeSelect + (tabTextSizeUnSelect - tabTextSizeSelect) * (1 - enterPercent);
                    if (nextTab instanceof TextView) {
                        final TextView tv = (TextView) nextTab;
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, nextTextSize);
                            }
                        });
                    }
                }
                if (tabTextColorSelect != tabTextColorUnSelect) {
                    final int nextColor = ColorUtil.computeColor(tabTextColorUnSelect, tabTextColorSelect, enterPercent);
                    if (nextTab instanceof TextView) {
                        final TextView tv = (TextView) nextTab;
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setTextColor(nextColor);
                            }
                        });
                    }
                }
            }
        }
    }
    
    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (mViewPager2 != null) {
            View currentTab = getItem(index);
            if (indicatorType == IndicatorType.TabWithText || indicatorType == IndicatorType.TabWithIconAndText) {
                if (tabTextSizeSelect > 0 && tabTextSizeUnSelect > 0 && tabTextSizeSelect != tabTextSizeUnSelect) {
                    final float curTextSize = tabTextSizeSelect + (tabTextSizeUnSelect - tabTextSizeSelect) * leavePercent;
                    if (currentTab instanceof TextView) {
                        final TextView tv = (TextView) currentTab;
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, curTextSize);
                            }
                        });
                    }
                }
                if (tabTextColorSelect != tabTextColorUnSelect) {
                    final int curColor = ColorUtil.computeColor(tabTextColorSelect, tabTextColorUnSelect, leavePercent);
                    if (currentTab instanceof TextView) {
                        final TextView tv = (TextView) currentTab;
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setTextColor(curColor);
                            }
                        });
                    }
                }
            }
        }
    }
    
    @Override
    public void onSelected(int index, int totalCount) {
        switch (indicatorType) {
            case TabWithIcon:
                selectIcon(index);
                break;
            case TabWithIconAndText:
                selectIconAndText(index);
                break;
        }
    }
    
    @Override
    public void onDeselected(int index, int totalCount) {
        
    }
}
