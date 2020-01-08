package me.zhouzhuo810.magpiexdemo.adapter;

import android.content.Context;

import java.util.List;

import me.zhouzhuo810.magpiex.ui.adapter.RvBaseAdapter;
import me.zhouzhuo810.magpiexdemo.R;
import me.zhouzhuo810.magpiexdemo.api.entity.TestListEntity;


public class ScrollListAdapter extends RvBaseAdapter<TestListEntity> {

    public ScrollListAdapter(Context context, List<TestListEntity> data) {
        super(context, data);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.rv_item_test;
    }

    @Override
    protected void fillData(ViewHolder holder, TestListEntity item, int position) {
        holder.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_sex, item.getSex())
                .setText(R.id.tv_email, item.getEmail())
                .setText(R.id.tv_phone, item.getPhone())
                .setBackgroundColor(R.id.tv_phone, item.getBgColor());
    }
}
