package me.zhouzhuo810.magpiex.utils.conversion;

import android.view.View;

import me.zhouzhuo810.magpiex.utils.SimpleUtil;
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
            if (SimpleUtil.hasScaled(view)) {
                return;
            }
            loadViewHelper.loadWidthHeightFont(view);
            loadViewHelper.loadPadding(view);
            loadViewHelper.loadLayoutMargin(view);
            loadViewHelper.loadMinWidthAndHeight(view);
            loadViewHelper.loadCustomAttrs(view);
            SimpleUtil.setScaleTag(view, true);
        }
    }
}
