package me.zhouzhuo810.magpiex.utils.conversion;

import android.view.View;

import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.loadviewhelper.AbsLoadViewHelper;


public class SimpleConversion implements IConversion {

    @Override
    public void transform(View view, AbsLoadViewHelper loadViewHelper) {
        if (view.getLayoutParams() != null) {
            Object tag = view.getTag(R.integer.view_scale_tag);
            if (tag instanceof Boolean && (Boolean) tag) {
                return;
            }
            loadViewHelper.loadWidthHeightFont(view);
            loadViewHelper.loadPadding(view);
            loadViewHelper.loadLayoutMargin(view);
            loadViewHelper.loadMinWidthAndHeight(view);
            view.setTag(R.integer.view_scale_tag, true);
        }
    }
}
