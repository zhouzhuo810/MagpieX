package me.zhouzhuo810.magpiex.ui.dialog.adapter;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import java.util.List;

import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.ui.adapter.RvBaseAdapter;


public class ListDialogAdapter extends RvBaseAdapter<String> {

    private boolean alignLeft;

    public boolean isAlignLeft() {
        return alignLeft;
    }

    public void setAlignLeft(boolean alignLeft) {
        this.alignLeft = alignLeft;
    }


    public ListDialogAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_lv_dialog;
    }

    @Override
    protected void fillData(ViewHolder holder, String item, int position) {
        TextView view = holder.getView(R.id.tv_name);
        view.setText(item);
        holder.setVisible(R.id.line_item, position != 0);
        if (alignLeft) {
            view.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        } else {
            view.setGravity(Gravity.CENTER);
        }
    }
}
