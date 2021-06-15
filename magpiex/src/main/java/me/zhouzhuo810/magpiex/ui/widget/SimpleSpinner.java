package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import me.zhouzhuo810.magpiex.ui.adapter.LvBaseAdapter;
import me.zhouzhuo810.magpiex.ui.widget.intef.ISpinnerData;

/**
 * Spinner控件封装，数据模型必须实现ISpinnerData接口
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:55 PM
 */
public class SimpleSpinner<T extends ISpinnerData> extends AppCompatSpinner {
    
    private int mLayoutId;
    private int mDropdownLayoutId;
    private int mTextViewId;
    
    private List<T> items;
    private OnSimpleSpinnerItemSelectedListener<T> mOnSimpleSpinnerItemSelectedListener;
    
    public SimpleSpinner(Context context) {
        super(context);
    }
    
    public SimpleSpinner(Context context, int mode) {
        super(context, mode);
    }
    
    public SimpleSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public SimpleSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public SimpleSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }
    
    public SimpleSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }
    
    /**
     * 选完之后的布局id
     *
     * @param mLayoutId 布局id
     * @return SimpleSpinner
     */
    public SimpleSpinner<T> setLayoutId(int mLayoutId) {
        this.mLayoutId = mLayoutId;
        return this;
    }
    
    /**
     * 下拉弹窗的布局id
     *
     * @param mDropdownLayoutId 布局id
     * @return SimpleSpinner
     */
    public SimpleSpinner<T> setDropdownLayoutId(int mDropdownLayoutId) {
        this.mDropdownLayoutId = mDropdownLayoutId;
        return this;
    }
    
    /**
     * 用来显示下拉内容的TextView的id，注意：setLayoutId 和 setDropdownLayoutId 中的TextView的id必须一致
     *
     * @param mTextViewId TextView的id
     * @return SimpleSpinner
     */
    public SimpleSpinner<T> setTextViewId(int mTextViewId) {
        this.mTextViewId = mTextViewId;
        return this;
    }
    
    
    public SimpleSpinner<T> setOnSimpleSpinnerItemSelectedListener(OnSimpleSpinnerItemSelectedListener<T> onSimpleSpinnerItemSelectedListener) {
        mOnSimpleSpinnerItemSelectedListener = onSimpleSpinnerItemSelectedListener;
        if (mOnSimpleSpinnerItemSelectedListener != null) {
            setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (mOnSimpleSpinnerItemSelectedListener != null) {
                        mOnSimpleSpinnerItemSelectedListener.onItemSelected(parent, view, position, id, items.get(position));
                    }
                }
                
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    if (mOnSimpleSpinnerItemSelectedListener != null) {
                        mOnSimpleSpinnerItemSelectedListener.onNothingSelected(parent);
                    }
                }
            });
        }
        return this;
    }
    
    public interface OnSimpleSpinnerItemSelectedListener<T> {
        
        /**
         * 选中回调
         */
        void onItemSelected(AdapterView<?> parent, View view, int position, long id, T data);
        
        /**
         * 啥也没选
         */
        void onNothingSelected(AdapterView<?> parent);
    }
    
    /**
     * 设置数据
     */
    public void setItems(List<T> items) {
        this.items = items;
        SimpleSpinnerAdapter<T> adapter = new SimpleSpinnerAdapter<>(getContext(), this.items);
        adapter.setDropdownLayoutId(mDropdownLayoutId);
        setAdapter(adapter);
    }
    
    /**
     * 设置数据
     */
    public void setItems(@NonNull T[] items) {
        this.items = Arrays.asList(items);
        SimpleSpinnerAdapter<T> adapter = new SimpleSpinnerAdapter<>(getContext(), this.items);
        adapter.setDropdownLayoutId(mDropdownLayoutId);
        setAdapter(adapter);
    }
    
    private class SimpleSpinnerAdapter<K extends ISpinnerData> extends LvBaseAdapter<K> {
        
        public SimpleSpinnerAdapter(Context context, List<K> data) {
            super(context, data);
        }
        
        @Override
        public int getLayoutId() {
            return mLayoutId;
        }
        
        @Override
        protected void fillDropDownData(ViewHolder holder, K item, int position) {
            super.fillDropDownData(holder, item, position);
            if (mTextViewId == 0) {
                throw new RuntimeException("Please invoke the method SimpleSpinner#setTextViewId().");
            }
            holder.setText(mTextViewId, item.getSpItemName());
        }
        
        @Override
        protected void fillData(ViewHolder holder, K item, int position) {
            holder.setText(mTextViewId, item.getSpItemName());
        }
    }
}


