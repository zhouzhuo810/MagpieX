package me.zhouzhuo810.magpiex.ui.widget.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo810.magpiex.ui.widget.ShineTextView;
import me.zhouzhuo810.magpiex.ui.widget.map.entity.MapTextRectEntity;
import me.zhouzhuo810.magpiex.ui.widget.map.view.MapTextView;


/**
 * 地图Adapter
 */
public class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapViewHolder> {

    private Context mContext;
    private List<MapTextRectEntity> mRects;

    public MapAdapter(Context context, List<MapTextRectEntity> rects) {
        this.mContext = context;
        this.mRects = rects;
    }

    @NonNull
    @Override
    public MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MapTextRectEntity entity = mRects.get(viewType);
        if (entity.getOrientation() == MapTextView.EMPTY) {
            return new MapViewHolder(
                    new MapTextView(mContext, entity.rim.width(), entity.rim.height(), entity.getTextSize(), entity.getBorderWidthPx(), entity.getShiningDuration()));
        } else {
            return new MapViewHolder(
                    new MapTextView(mContext, entity.rim.width(), entity.rim.height(), entity.getOrientation(), entity.getTextSize(), entity.getBorderWidthPx(), entity.getShiningDuration()));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MapViewHolder holder, int position) {
        MapTextRectEntity entity = mRects.get(position);
        ((MapTextView) holder.itemView).setMachineName(entity.getName());
        int[] colors = entity.getShiningColors();
        if (colors != null && colors.length > 0) {
            if (colors.length == 1) {
                ((ShineTextView) ((MapTextView) holder.itemView).getChildAt(0)).setBackgroundColor(colors[0]);
            } else {
                ((ShineTextView) ((MapTextView) holder.itemView).getChildAt(0)).setShiningBgColors(colors);
                ((ShineTextView) ((MapTextView) holder.itemView).getChildAt(0)).startShiningBg();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mRects.size();
    }

    public static class MapViewHolder extends RecyclerView.ViewHolder {

        public MapViewHolder(View itemView) {
            super(itemView);
        }
    }
}
