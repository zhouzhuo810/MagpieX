package me.zhouzhuo810.magpiex.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.widget.AppCompatSpinner;
import me.zhouzhuo810.magpiex.ui.adapter.LvBaseAdapter;

public class SimpleSpinner extends AppCompatSpinner {

    private int mLayoutId;
    private int mDropdownLayoutId;
    private int mTextViewId;

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

    public SimpleSpinner setLayoutId(int mLayoutId) {
        this.mLayoutId = mLayoutId;
        return this;
    }

    public SimpleSpinner setDropdownLayoutId(int mDropdownLayoutId) {
        this.mDropdownLayoutId = mDropdownLayoutId;
        return this;
    }

    public SimpleSpinner setTextViewId(int mTextViewId) {
        this.mTextViewId = mTextViewId;
        return this;
    }

    /**
     * 设置数据
     *
     * @param items
     */
    public void setItems(String[] items) {
        SimpleSpinnerAdapter adapter = new SimpleSpinnerAdapter(getContext(), Arrays.asList(items));
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


