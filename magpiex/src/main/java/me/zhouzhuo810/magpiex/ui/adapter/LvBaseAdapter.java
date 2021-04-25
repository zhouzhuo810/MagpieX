package me.zhouzhuo810.magpiex.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * base adapter for ListView
 * Created by admin on 2017/7/31.
 */
public abstract class LvBaseAdapter<T> extends BaseAdapter {
    
    protected Context context;
    protected List<T> data;
    private int mDropdownLayoutId;
    
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
    
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
    
    public LvBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }
    
    public LvBaseAdapter(Context context, T[] data) {
        this.context = context;
        this.data = data == null ? null : Arrays.asList(data);
    }
    
    public void updateAll(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    
    public void setDropdownLayoutId(@LayoutRes int dropdownLayoutId) {
        this.mDropdownLayoutId = dropdownLayoutId;
    }
    
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
    
    public abstract int getLayoutId();
    
    @Override
    public T getItem(int i) {
        return data == null ? null : data.get(i);
    }
    
    @Override
    public long getItemId(int i) {
        return i;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getLayoutId(), viewGroup, false);
            if (!disableScale()) {
                SimpleUtil.scaleView(convertView);
            }
            holder = new ViewHolder(context, convertView, viewGroup, position);
            holder.mLayoutId = getLayoutId();
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        if (onItemClickListener != null) {
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
        if (onItemLongClickListener != null) {
            holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClickListener.onItemLongClick(v, position);
                }
            });
        }
        fillData(holder, getItem(position), position);
        return convertView;
    }
    
    
    /**
     * 是否禁用缩放
     *
     * @return 是否，默认false
     */
    protected boolean disableScale() {
        return false;
    }
    
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup viewGroup) {
        if (mDropdownLayoutId == 0) {
            return super.getDropDownView(position, convertView, viewGroup);
        } else {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(getLayoutId(), viewGroup, false);
                if (!disableScale()) {
                    SimpleUtil.scaleView(convertView);
                }
                holder = new ViewHolder(context, convertView, viewGroup, position);
                holder.mLayoutId = getLayoutId();
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.mPosition = position;
            }
            fillDropDownData(holder, getItem(position), position);
            return convertView;
        }
    }
    
    protected void fillDropDownData(ViewHolder holder, T item, int position) {
    
    }
    
    protected abstract void fillData(ViewHolder holder, T item, int position);
    
    public static class ViewHolder {
        private final SparseArray<View> mViews;
        protected int mPosition;
        private final View mConvertView;
        private final Context mContext;
        protected int mLayoutId;
        
        public ViewHolder(Context context, View itemView, ViewGroup parent, int position) {
            mContext = context;
            mConvertView = itemView;
            mPosition = position;
            mViews = new SparseArray<View>();
            mConvertView.setTag(this);
        }
        
        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
            if (convertView == null) {
                View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
                ViewHolder holder = new ViewHolder(context, itemView, parent, position);
                holder.mLayoutId = layoutId;
                return holder;
            } else {
                ViewHolder holder = (ViewHolder) convertView.getTag();
                holder.mPosition = position;
                return holder;
            }
        }
        
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
        
        public View getConvertView() {
            return mConvertView;
        }
        
        public int getLayoutId() {
            return mLayoutId;
        }
        
        public void updatePosition(int position) {
            mPosition = position;
        }
        
        public int getItemPosition() {
            return mPosition;
        }
        
        public ViewHolder setText(int viewId, CharSequence text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }
        
        public ViewHolder setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
            return this;
        }
        
        public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }
        
        public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
            ImageView view = getView(viewId);
            view.setImageDrawable(drawable);
            return this;
        }
        
        public ViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return this;
        }
        
        public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return this;
        }
        
        public ViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return this;
        }
        
        public ViewHolder setTextColorRes(int viewId, int textColorRes) {
            TextView view = getView(viewId);
            view.setTextColor(mContext.getResources().getColor(textColorRes));
            return this;
        }
        
        @SuppressLint("NewApi")
        public ViewHolder setAlpha(int viewId, float value) {
            getView(viewId).setAlpha(value);
            return this;
        }
        
        public ViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            return this;
        }
        
        public ViewHolder setGone(int viewId, boolean gone) {
            View view = getView(viewId);
            view.setVisibility(gone ? View.GONE : View.VISIBLE);
            return this;
        }
        
        public ViewHolder linkify(int viewId) {
            TextView view = getView(viewId);
            Linkify.addLinks(view, Linkify.ALL);
            return this;
        }
        
        public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
            for (int viewId : viewIds) {
                TextView view = getView(viewId);
                view.setTypeface(typeface);
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
            return this;
        }
        
        public ViewHolder setProgress(int viewId, int progress) {
            ProgressBar view = getView(viewId);
            view.setProgress(progress);
            return this;
        }
        
        public ViewHolder setProgress(int viewId, int progress, int max) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            view.setProgress(progress);
            return this;
        }
        
        public ViewHolder setMax(int viewId, int max) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            return this;
        }
        
        public ViewHolder setRating(int viewId, float rating) {
            RatingBar view = getView(viewId);
            view.setRating(rating);
            return this;
        }
        
        public ViewHolder setRating(int viewId, float rating, int max) {
            RatingBar view = getView(viewId);
            view.setMax(max);
            view.setRating(rating);
            return this;
        }
        
        public ViewHolder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return this;
        }
        
        public ViewHolder setTag(int viewId, int key, Object tag) {
            View view = getView(viewId);
            view.setTag(key, tag);
            return this;
        }
        
        public ViewHolder setChecked(int viewId, boolean checked) {
            Checkable view = (Checkable) getView(viewId);
            view.setChecked(checked);
            return this;
        }
        
        public ViewHolder setEnable(int viewId, boolean enable) {
            View view = getView(viewId);
            view.setEnabled(enable);
            return this;
        }
        
        public ViewHolder setShapeColor(int viewId, @ColorInt int color) {
            View view = getView(viewId);
            if (view.getBackground() instanceof GradientDrawable) {
                GradientDrawable drawable = (GradientDrawable) view.getBackground().mutate();
                drawable.setColor(color);
            }
            return this;
        }
        
        public ViewHolder setShapeColorRes(int viewId, @ColorRes int colorRes) {
            View view = getView(viewId);
            if (view.getBackground() instanceof GradientDrawable) {
                GradientDrawable drawable = (GradientDrawable) view.getBackground().mutate();
                drawable.setColor(mContext.getResources().getColor(colorRes));
            }
            return this;
        }
        
        /**
         * 关于事件的
         */
        public ViewHolder setOnClickListener(int viewId,
                                             View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return this;
        }
        
        public ViewHolder setOnTouchListener(int viewId,
                                             View.OnTouchListener listener) {
            View view = getView(viewId);
            view.setOnTouchListener(listener);
            return this;
        }
        
        public ViewHolder setOnLongClickListener(int viewId,
                                                 View.OnLongClickListener listener) {
            View view = getView(viewId);
            view.setOnLongClickListener(listener);
            return this;
        }
        
    }
}
