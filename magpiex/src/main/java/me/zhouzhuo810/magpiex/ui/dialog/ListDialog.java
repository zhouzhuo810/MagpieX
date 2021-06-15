package me.zhouzhuo810.magpiex.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import me.zhouzhuo810.magpiex.ui.dialog.adapter.ListDialogAdapter;
import me.zhouzhuo810.magpiex.utils.ScreenAdapterUtil;
import me.zhouzhuo810.magpiex.utils.SimpleUtil;

/**
 * 列表对话框
 *
 * @author zhouzhuo810
 * @date 6/15/21 1:54 PM
 */
public class ListDialog extends DialogFragment {
    
    private List<String> items;
    private OnItemClick onItemClick;
    private DialogInterface.OnDismissListener dismissListener;
    private boolean alignLeft;
    private boolean landscape;
    private CharSequence title;
    private ListDialogAdapter adapter;
    
    public interface OnItemClick {
        void onItemClick(int position, String item);
    }
    
    /**
     * 设置对话框关闭监听
     *
     * @param dismissListener 监听
     * @return 自己
     */
    public ListDialog setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }
    
    /**
     * 设置点击事件
     *
     * @param onItemClick 点击回调
     * @return 自己
     */
    public ListDialog setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        return this;
    }
    
    /**
     * 设置标题
     *
     * @param title 标题，为空则表示不需要标题
     * @return 自己
     */
    public ListDialog setTitle(CharSequence title) {
        this.title = title;
        return this;
    }
    
    /**
     * 是否横屏显示
     *
     * @param landscape 是否
     * @return 自己
     */
    public ListDialog setLandscape(boolean landscape) {
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
    public ListDialog setAlignLeft(boolean alignLeft) {
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
    public ListDialog setItems(List<String> items) {
        this.items = items;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        return this;
    }
    
    
    public ListDialogAdapter getAdapter() {
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
            if (landscape) {
                getDialog().getWindow().setLayout(dm.widthPixels * 2 / 5, getDialog().getWindow().getAttributes().height);
            } else {
                getDialog().getWindow().setLayout(dm.widthPixels * 4 / 5, getDialog().getWindow().getAttributes().height);
            }
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //添加这一行
        if (getDialog() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        View rootView = inflater.inflate(landscape ? R.layout.layout_list_dialog_land : R.layout.layout_list_dialog, container, false);
        if (savedInstanceState != null) {
            dismiss();
            return rootView;
        }
        SimpleUtil.scaleView(rootView);
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        View line = rootView.findViewById(R.id.line_item);
        RecyclerView rv = rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        adapter = new ListDialogAdapter(getActivity(), items, landscape);
        if (TextUtils.isEmpty(title)) {
            line.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            tvTitle.setText("");
        } else {
            line.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
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
