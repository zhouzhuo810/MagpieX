package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.ColorUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;


/**
 * TitleBar
 * Created by zhouzhuo810 on 2017/7/25.
 */
public class TitleBar extends RelativeLayout {
    
    
    public static final int TITLE_GRAVITY_CENTER = 0;
    public static final int TITLE_GRAVITY_LEFT = 1;
    
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TITLE_GRAVITY_CENTER, TITLE_GRAVITY_LEFT})
    public @interface TITLE_GRAVITY {
    }
    
    
    private ImageView ivLeft;
    private TextView tvLeft;
    private LinearLayout llLeft;
    private TextView tvTitle;
    private ImageView ivRight;
    private TextView tvRight;
    private LinearLayout llRight;
    
    private OnTitleClick titleClick;
    private OnLeftClickListener onLeftClickListener;
    private OnMiddleClickListener onMiddleClickListener;
    private OnRightClickListener onRightClickListener;
    private MarkView mvLeft;
    private MarkView mvRight;
    
    public interface OnTitleClick {
        void onLeftClick(ImageView ivLeft, MarkView mv, TextView tvLeft);
        
        void onTitleClick(TextView tvTitle);
        
        void onRightClick(ImageView ivRight, MarkView mv, TextView tvRight);
    }
    
    public interface OnLeftClickListener {
        void onClick(ImageView ivLeft, MarkView mv, TextView tvLeft);
    }
    
    public interface OnMiddleClickListener {
        void onClick(TextView tvTitle);
    }
    
    public interface OnRightClickListener {
        void onClick(ImageView ivRight, MarkView mv, TextView tvRight);
    }
    
    /**
     * 设置标题、左边、右边按钮点击事件
     *
     * @param titleClick OnTitleClick
     */
    public void setOnTitleClickListener(OnTitleClick titleClick) {
        this.titleClick = titleClick;
        initEvent();
    }
    
    /**
     * 只设置左边按钮点击事件
     *
     * @param onLeftClickListener onLeftClickListener
     */
    public void setOnLeftClickListener(OnLeftClickListener onLeftClickListener) {
        this.onLeftClickListener = onLeftClickListener;
        llLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleClick != null) {
                    titleClick.onLeftClick(ivLeft, mvLeft, tvLeft);
                }
                if (TitleBar.this.onLeftClickListener != null) {
                    TitleBar.this.onLeftClickListener.onClick(ivLeft, mvLeft, tvLeft);
                }
            }
        });
    }

    /**
     * 只设置右边按钮点击事件
     *
     * @param onRightClickListener onRightClickListener
     */
    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
        llRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleClick != null) {
                    titleClick.onRightClick(ivRight, mvRight, tvRight);
                }
                if (TitleBar.this.onRightClickListener != null) {
                    TitleBar.this.onRightClickListener.onClick(ivRight, mvRight, tvRight);
                }
            }
        });
    }
    
    /**
     * 只设置中间标题文字点击事件
     *
     * @param onMiddleClickListener OnMiddleClickListener
     */
    public void setOnMiddleClickListener(OnMiddleClickListener onMiddleClickListener) {
        this.onMiddleClickListener = onMiddleClickListener;
        tvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleClick != null) {
                    titleClick.onTitleClick(tvTitle);
                }
                if (TitleBar.this.onMiddleClickListener != null) {
                    TitleBar.this.onMiddleClickListener.onClick(tvTitle);
                }
            }
        });
    }
    
    public TitleBar(Context context) {
        super(context);
        init(context, null);
    }
    
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attrs) {
        View root = LayoutInflater.from(context).inflate(R.layout.title_layout, this, false);
        ivLeft = root.findViewById(R.id.iv_back);
        tvLeft = root.findViewById(R.id.tv_back);
        llLeft = root.findViewById(R.id.ll_back);
        tvTitle = root.findViewById(R.id.tv_title);
        ivRight = root.findViewById(R.id.iv_right);
        tvRight = root.findViewById(R.id.tv_right);
        mvLeft = root.findViewById(R.id.mv_left);
        mvRight = root.findViewById(R.id.mv_right);
        llRight = root.findViewById(R.id.ll_right);
        initAttrs(context, attrs);
        addView(root);
        setGravity(Gravity.CENTER_VERTICAL);
    }
    
    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
            /*visibility*/
            boolean showLeftLayout = t.getBoolean(R.styleable.TitleBar_ttb_showLeftLayout, true);
            boolean showRightLayout = t.getBoolean(R.styleable.TitleBar_ttb_showRightLayout, false);
            boolean showLeftImg = t.getBoolean(R.styleable.TitleBar_ttb_showLeftImg, true);
            boolean showLeftText = t.getBoolean(R.styleable.TitleBar_ttb_showLeftText, false);
            boolean showLeftMarkView = t.getBoolean(R.styleable.TitleBar_ttb_showLeftMarkView, false);
            boolean showRightMarkView = t.getBoolean(R.styleable.TitleBar_ttb_showRightMarkView, false);
            boolean showTitle = t.getBoolean(R.styleable.TitleBar_ttb_showTitle, true);
            boolean showRightImg = t.getBoolean(R.styleable.TitleBar_ttb_showRightImg, false);
            boolean showRightText = t.getBoolean(R.styleable.TitleBar_ttb_showRightText, true);
            boolean autoIconColor = t.getBoolean(R.styleable.TitleBar_ttb_autoIconColor, false);
            setVisible(llLeft, showLeftLayout);
            setVisible(llRight, showRightLayout);
            setVisible(ivLeft, showLeftImg);
            setVisible(tvLeft, showLeftText);
            setVisible(tvTitle, showTitle);
            setVisible(ivRight, showRightImg);
            setVisible(tvRight, showRightText);
            setVisible(mvLeft, showLeftMarkView);
            setVisible(mvRight, showRightMarkView);
            /*gravity*/
            int titleGravity = t.getInt(R.styleable.TitleBar_ttb_titleGravity, 0);
            setTitleGravity(titleGravity);
            /*textSize*/
            int textSizeTitle = t.getDimensionPixelSize(R.styleable.TitleBar_ttb_textSizeTitle, 50);
            int textSizeTwoSide = t.getDimensionPixelSize(R.styleable.TitleBar_ttb_textSizeTwoSide, 40);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeTitle);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeTwoSide);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeTwoSide);
            /*textColor*/
            int color = t.getColor(R.styleable.TitleBar_ttb_textColorAll, Color.WHITE);
            tvTitle.setTextColor(color);
            tvLeft.setTextColor(color);
            tvRight.setTextColor(color);
            /*img*/
            int iconSize = t.getDimensionPixelSize(R.styleable.TitleBar_ttb_iconSize, 60);
            setImageSize(iconSize, false);
            int leftDrawableId = t.getResourceId(R.styleable.TitleBar_ttb_leftImg, -1);
            int rightDrawableId = t.getResourceId(R.styleable.TitleBar_ttb_rightImg, -1);
            if (leftDrawableId != -1) {
                ivLeft.setImageResource(leftDrawableId);
                if (autoIconColor) {
                    ColorUtil.setIconColor(ivLeft, color);
                }
            }
            if (rightDrawableId != -1) {
                ivRight.setImageResource(rightDrawableId);
                if (autoIconColor) {
                    ColorUtil.setIconColor(ivRight, color);
                }
            }
            /*text*/
            String leftText = t.getString(R.styleable.TitleBar_ttb_leftText);
            String rightText = t.getString(R.styleable.TitleBar_ttb_rightText);
            String titleText = t.getString(R.styleable.TitleBar_ttb_titleText);
            tvLeft.setText(leftText);
            tvRight.setText(rightText);
            tvTitle.setText(titleText);
            t.recycle();
        } else {
            setVisible(llLeft, true);
            setVisible(llRight, false);
            setVisible(ivLeft, false);
            setVisible(tvLeft, true);
            setVisible(tvTitle, true);
            setVisible(ivRight, true);
            setVisible(tvRight, false);
            setVisible(mvLeft, false);
            setVisible(mvRight, false);
            
            /*gravity*/
            setTitleGravity(TITLE_GRAVITY_CENTER);
            /*textSize*/
            int textSizeTitle = 50;
            int textSizeTwoSide = 40;
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeTitle);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeTwoSide);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeTwoSide);
            /*textColor*/
            tvTitle.setTextColor(Color.WHITE);
            tvLeft.setTextColor(Color.WHITE);
            tvRight.setTextColor(Color.WHITE);
            /*text*/
            tvLeft.setText("返回");
            tvTitle.setText("标题");
        }
    }
    
    /**
     * 设置标题的对其方式
     * 支持居中对齐
     * <p>
     * {@link TitleBar#TITLE_GRAVITY_CENTER}
     * <p>
     * 和左对齐
     * <p>
     * {@link TitleBar#TITLE_GRAVITY_LEFT}
     *
     * @param gravity 对齐方式
     */
    public void setTitleGravity(@TITLE_GRAVITY int gravity) {
        switch (gravity) {
            case TITLE_GRAVITY_LEFT:
                tvTitle.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                break;
            case TITLE_GRAVITY_CENTER:
            default:
                tvTitle.setGravity(Gravity.CENTER);
                break;
        }
        
    }
    
    private void setVisible(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? VISIBLE : GONE);
        }
    }
    
    private void initEvent() {
        
        tvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleClick != null) {
                    titleClick.onTitleClick(tvTitle);
                }
            }
        });
        
        llLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleClick != null) {
                    titleClick.onLeftClick(ivLeft, mvLeft, tvLeft);
                }
            }
        });
        
        llRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleClick != null) {
                    titleClick.onRightClick(ivRight, mvLeft, tvRight);
                }
            }
        });
    }
    
    public void setImageSize(int imageSize, boolean autoSize) {
        int size = imageSize;
        if (autoSize) {
            size = SimpleUtil.getScaledValue(imageSize);
        }
        LayoutParams lp0 = (LayoutParams) ivLeft.getLayoutParams();
        LayoutParams lp1 = (LayoutParams) ivRight.getLayoutParams();
        lp0.width = size;
        lp0.height = size;
        lp1.width = size;
        lp1.height = size;
        ivLeft.setLayoutParams(lp0);
        ivRight.setLayoutParams(lp1);
    }
    
    /**
     * 左边布局容器
     */
    public LinearLayout getLlLeft() {
        return llLeft;
    }
    
    /**
     * 左边的图标
     */
    public ImageView getIvLeft() {
        return ivLeft;
    }
    
    /**
     * 左边的文字
     */
    public TextView getTvLeft() {
        return tvLeft;
    }
    
    /**
     * 左边的角标
     */
    public MarkView getMvLeft() {
        return mvLeft;
    }
    
    /**
     * 标题
     */
    public TextView getTvTitle() {
        return tvTitle;
    }
    
    /**
     * 右边布局容器
     */
    public LinearLayout getLlRight() {
        return llRight;
    }
    
    /**
     * 右边的图标
     */
    public ImageView getIvRight() {
        return ivRight;
    }
    
    /**
     * 右边的文字
     */
    public TextView getTvRight() {
        return tvRight;
    }
    
    /**
     * 右边的角标
     */
    public MarkView getMvRight() {
        return mvRight;
    }
}
