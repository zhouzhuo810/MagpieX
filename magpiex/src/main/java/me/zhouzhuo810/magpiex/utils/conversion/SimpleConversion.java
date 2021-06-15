package me.zhouzhuo810.magpiex.utils.conversion;

import android.view.View;

import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.utils.loadviewhelper.AbsLoadViewHelper;


/**
 * 屏幕适配转换
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:45 PM
 */
public class SimpleConversion implements IConversion {

    @Override
    public void transform(View view, AbsLoadViewHelper loadViewHelper) {
        if (view.getLayoutParams() != null) {
            //防止重复缩放
            Object tag = view.getTag(R.id.view_scale_tag);
            if (tag instanceof Boolean && (Boolean) tag) {
                return;
            }
            loadViewHelper.loadWidthHeightFont(view);
            loadViewHelper.loadPadding(view);
            loadViewHelper.loadLayoutMargin(view);
            loadViewHelper.loadMinWidthAndHeight(view);
            loadViewHelper.loadCustomAttrs(view);
            view.setTag(R.id.view_scale_tag, true);
        }
    }
}
