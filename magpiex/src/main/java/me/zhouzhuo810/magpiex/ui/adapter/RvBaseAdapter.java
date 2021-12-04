package me.zhouzhuo810.magpiex.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.util.Linkify;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.ColorUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * RvBaseAdapter
 *
 * @author admin
 * @date 2017/8/10
 */
public abstract class RvBaseAdapter<T> extends RecyclerView.Adapter<RvBaseAdapter.ViewHolder> {
    
    protected Context context;
    protected List<T> data;
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
    
    public RvBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        if (BaseUtil.isLogEnable()) {
            Log.d("PrintAdapterName", "(" + getClass().getSimpleName() + ".java:1)"+"\na.a(" + getClass().getSimpleName() + ".kt:1)");
        }
    }
    
    public RvBaseAdapter(Context context, T[] data) {
        this.context = context;
        this.data = data == null ? null : Arrays.asList(data);
        if (BaseUtil.isLogEnable()) {
            Log.d("PrintAdapterName", "(" + getClass().getSimpleName() + ".java:1)"+"\na.a(" + getClass().getSimpleName() + ".kt:1)");
        }
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getLayoutId(viewType), parent, false);
        if (!disableScale()) {
            SimpleUtil.scaleView(view);
        }
        return new ViewHolder(context, view);
    }
    
    /**
     * 是否禁用缩放
     *
     * @return 是否，默认false
     */
    protected boolean disableScale() {
        return false;
    }
    
    protected abstract int getLayoutId(int viewType);
    
    public List<T> getData() {
        return data;
    }
    
    public void updateAll(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }
    
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bindingAdapterPosition = holder.getBindingAdapterPosition();
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
                        return;
                    }
                    onItemClickListener.onItemClick(v, bindingAdapterPosition);
                }
            });
        }
        if (onItemLongClickListener != null) {
            holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int bindingAdapterPosition = holder.getBindingAdapterPosition();
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
                        return false;
                    }
                    return onItemLongClickListener.onItemLongClick(v, bindingAdapterPosition);
                }
            });
        }
        int bindingAdapterPosition = holder.getBindingAdapterPosition();
        if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
            fillData(holder, data.get(bindingAdapterPosition), bindingAdapterPosition);
        }
    }
    
    protected abstract void fillData(ViewHolder holder, T item, int position);
    
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> mViews;
        private final View mConvertView;
        private final Context mContext;
        
        
        public ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mConvertView = itemView;
            mViews = new SparseArray<View>();
        }
        
        /**
         * 通过viewId获取控件
         */
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
        
        /****以下为辅助方法*****/
        
        /**
         * 设置TextView的值
         */
        public ViewHolder setText(int viewId, CharSequence text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }
        
        /**
         * 设置TextView的字体大小
         */
        public ViewHolder setTextSize(int viewId, int unit, float textSize) {
            TextView tv = getView(viewId);
            tv.setTextSize(unit, textSize);
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
        
        /**
         * 设置ImageView的图标颜色（适用于纯色图标）
         */
        public ViewHolder setImageColor(int viewId, int color) {
            ImageView view = getView(viewId);
            ColorUtil.setIconColor(view, color);
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
        
        public ViewHolder setBackgroundDrawable(int viewId, Drawable drawable) {
            View view = getView(viewId);
            view.setBackground(drawable);
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
    
        public Context getContext() {
            return mContext;
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
