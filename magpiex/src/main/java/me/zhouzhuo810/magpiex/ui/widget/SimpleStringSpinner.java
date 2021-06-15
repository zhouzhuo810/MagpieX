package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.widget.AppCompatSpinner;
import me.zhouzhuo810.magpiex.ui.adapter.LvBaseAdapter;

/**
 * Spinner控件封装，数据类型必须是字符串
 *
 * @author zhouzhuo810
 */
public class SimpleStringSpinner extends AppCompatSpinner {
    
    private int mLayoutId;
    private int mDropdownLayoutId;
    private int mTextViewId;
    
    private List<String> items;
    private OnSimpleStringSpinnerItemSelectedListener<String> mOnSimpleSpinnerItemSelectedListener;
    
    public SimpleStringSpinner(Context context) {
        super(context);
    }
    
    public SimpleStringSpinner(Context context, int mode) {
        super(context, mode);
    }
    
    public SimpleStringSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public SimpleStringSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public SimpleStringSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }
    
    public SimpleStringSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }
    
    public SimpleStringSpinner setLayoutId(int mLayoutId) {
        this.mLayoutId = mLayoutId;
        return this;
    }
    
    public SimpleStringSpinner setDropdownLayoutId(int mDropdownLayoutId) {
        this.mDropdownLayoutId = mDropdownLayoutId;
        return this;
    }
    
    public SimpleStringSpinner setTextViewId(int mTextViewId) {
        this.mTextViewId = mTextViewId;
        return this;
    }
    
    
    public SimpleStringSpinner setOnSimpleStringSpinnerItemSelectedListener(OnSimpleStringSpinnerItemSelectedListener<String> onSimpleSpinnerItemSelectedListener) {
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
    
    public interface OnSimpleStringSpinnerItemSelectedListener<T> {
        
        void onItemSelected(AdapterView<?> parent, View view, int position, long id, T data);
        
        void onNothingSelected(AdapterView<?> parent);
    }
    
    /**
     * 设置数据
     */
    public void setItems(List<String> items) {
        this.items = items;
        SimpleSpinnerAdapter adapter = new SimpleSpinnerAdapter(getContext(), this.items);
        adapter.setDropdownLayoutId(mDropdownLayoutId);
        setAdapter(adapter);
    }
    
    /**
     * 设置数据
     */
    public void setItems(String[] items) {
        this.items = Arrays.asList(items);
        SimpleSpinnerAdapter adapter = new SimpleSpinnerAdapter(getContext(), this.items);
        adapter.setDropdownLayoutId(mDropdownLayoutId);
        setAdapter(adapter);
    }
    
    private class SimpleSpinnerAdapter extends LvBaseAdapter<String> {
        
        public SimpleSpinnerAdapter(Context context, List<String> data) {
            super(context, data);
        }
        
        @Override
        public int getLayoutId() {
            return mLayoutId;
        }
        
        @Override
        protected void fillDropDownData(ViewHolder holder, String item, int position) {
            super.fillDropDownData(holder, item, position);
            if (mTextViewId == 0) {
                throw new RuntimeException("Please invoke the method SimpleSpinner#setTextViewId().");
            }
            holder.setText(mTextViewId, item);
        }
        
        @Override
        protected void fillData(ViewHolder holder, String item, int position) {
            holder.setText(mTextViewId, item);
        }
    }
}


