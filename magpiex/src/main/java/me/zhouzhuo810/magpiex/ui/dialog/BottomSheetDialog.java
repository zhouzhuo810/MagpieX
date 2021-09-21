package me.zhouzhuo810.magpiex.ui.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.zhouzhuo810.magpiex.R;
import me.zhouzhuo810.magpiex.ui.adapter.RvBaseAdapter;
import me.zhouzhuo810.magpiex.ui.dialog.adapter.BottomListDialogAdapter;
import me.zhouzhuo810.magpiex.ui.dialog.adapter.ListDialogAdapter;
import me.zhouzhuo810.magpiex.utils.DrawableUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * 底部弹出对话框
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:54 PM
 */
public class BottomSheetDialog extends DialogFragment {
    
    private List<String> items;
    private OnItemClick onItemClick;
    private boolean alignLeft;
    private boolean landscape;
    private DialogInterface.OnDismissListener dismissListener;
    private BottomListDialogAdapter adapter;
    
    public interface OnItemClick {
        void onItemClick(int position, String item);
    }
    
    /**
     * 设置对话框关闭监听
     *
     * @param dismissListener 监听
     * @return 自己
     */
    public BottomSheetDialog setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }
    
    
    /**
     * 设置点击事件
     *
     * @param onItemClick 点击回调
     * @return 自己
     */
    public BottomSheetDialog setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        return this;
    }
    
    /**
     * 是否横屏显示
     *
     * @param landscape 是否
     * @return 自己
     */
    public BottomSheetDialog setLandscape(boolean landscape) {
        this.landscape = landscape;
        if (adapter != null) {
            adapter.setLandscape(landscape);
            adapter.notifyDataSetChanged();
        }
        return this;
    }
    
    public boolean isLandscape() {
        return landscape;
    }
    
    /**
     * 设置左对齐
     *
     * @param alignLeft 是否左对齐，否则居中对齐
     * @return 自己
     */
    public BottomSheetDialog setAlignLeft(boolean alignLeft) {
        this.alignLeft = alignLeft;
        if (adapter != null) {
            adapter.setAlignLeft(this.alignLeft);
            adapter.notifyDataSetChanged();
        }
        return this;
    }
    
    /**
     * 设置数据
     *
     * @param items 列表数据集合
     * @return 自己
     */
    public BottomSheetDialog setItems(List<String> items) {
        this.items = items;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        return this;
    }
    
    
    public BottomListDialogAdapter getAdapter() {
        return adapter;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() == null) {
            return;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //添加这一行
        if (getDialog() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        //设置dialog的 进出 动画
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().setWindowAnimations(R.style.BottomSheetDialog);
            getDialog().getWindow().setGravity(Gravity.BOTTOM);
        }
        View rootView = inflater.inflate(landscape ? R.layout.layout_bottom_sheet_dialog_land : R.layout.layout_bottom_sheet_dialog, container, false);
        if (savedInstanceState != null) {
            dismissDialog();
            return rootView;
        }
        SimpleUtil.scaleView(rootView);
        TextView tvCancel = rootView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(v -> {
            dismissDialog();
        });
        RecyclerView rv = rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        adapter = new BottomListDialogAdapter(getActivity(), items, landscape);
        adapter.setAlignLeft(alignLeft);
        adapter.setOnItemClickListener(new RvBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                dismissDialog();
                if (onItemClick != null) {
                    onItemClick.onItemClick(position, items.get(position));
                }
            }
        });
        rv.setAdapter(adapter);
        return rootView;
    }
    
    
    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 注意,不要用super.dismiss(),bug 同上show()
     * super.onDismiss就没问题
     */
    public void dismissDialog() {
        super.dismissAllowingStateLoss();
    }
    
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }
}
