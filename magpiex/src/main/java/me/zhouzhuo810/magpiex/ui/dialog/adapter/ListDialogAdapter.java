package me.zhouzhuo810.magpiex.ui.dialog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.TextView;

import java.util.List;

import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.ui.adapter.RvBaseAdapter;
import me.zhouzhuo810.magpiex.utils.DrawableUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * 列表对话框适配器
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:53 PM
 */
public class ListDialogAdapter extends RvBaseAdapter<String> {
    
    private boolean alignLeft;
    private boolean landscape;
    
    public boolean isAlignLeft() {
        return alignLeft;
    }
    
    public void setAlignLeft(boolean alignLeft) {
        this.alignLeft = alignLeft;
    }
    
    public boolean isLandscape() {
        return landscape;
    }
    
    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }
    
    public ListDialogAdapter(Context context, List<String> data, boolean landscape) {
        super(context, data);
        this.landscape = landscape;
    }
    
    @Override
    protected int getLayoutId(int viewType) {
        return landscape ? R.layout.item_lv_dialog_land : R.layout.item_lv_dialog;
    }
    
    @Override
    protected void fillData(ViewHolder holder, String item, int position) {
        TextView view = holder.getView(R.id.tv_name);
        view.setText(item);
        holder.itemView.setBackgroundResource(R.drawable.item_bg_selector);
        holder.setVisible(R.id.line_item, position != 0);
        if (alignLeft) {
            view.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        } else {
            view.setGravity(Gravity.CENTER);
        }
    }
}
