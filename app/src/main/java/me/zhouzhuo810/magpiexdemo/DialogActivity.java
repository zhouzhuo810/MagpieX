package me.zhouzhuo810.magpiexdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.zhouzhuo810.magpiex.ui.act.BaseActivity;
import me.zhouzhuo810.magpiex.ui.dialog.BottomSheetDialog;
import me.zhouzhuo810.magpiex.ui.dialog.ListDialog;
import me.zhouzhuo810.magpiex.ui.dialog.OneBtnProgressDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnEditDialog;
import me.zhouzhuo810.magpiex.ui.dialog.TwoBtnTextDialog;
import me.zhouzhuo810.magpiex.ui.widget.MarkView;
import me.zhouzhuo810.magpiex.ui.widget.TitleBar;
import me.zhouzhuo810.magpiex.utils.RxHelper;
import me.zhouzhuo810.magpiex.utils.ToastUtil;

/**
 * 对话框Activity
 */
public class DialogActivity extends BaseActivity {
    
    @Override
    public boolean shouldSupportMultiLanguage() {
        return true;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_dialog;
    }
    
    @Override
    public boolean shouldNotInvokeInitMethods(Bundle savedInstanceState) {
        return false;
    }
    
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
    
    }
    
    @Override
    public void initData() {
    }
    
    @Override
    public void initEvent() {
        TitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClick() {
            @Override
            public void onLeftClick(ImageView ivLeft, MarkView mv, TextView tvLeft) {
                closeAct();
            }
            
            @Override
            public void onTitleClick(TextView tvTitle) {
            
            }
            
            @Override
            public void onRightClick(ImageView ivRight, MarkView mv, TextView tvRight) {
            
            }
        });
    }
    
    public void listDialog(View v) {
        String[] items = getResources().getStringArray(R.array.list_test_data);
        showListDialog(getString(R.string.app_name), items, true, false,  new ListDialog.OnItemClick() {
            @Override
            public void onItemClick(int position, String item) {
                ToastUtil.showToast("position = " + position + ", content = " + item);
            }
        });
    }
    
    public void twoBtnTextDialog(View v) {
        //        showTwoBtnDialog(null, getString(R.string.check_update_text), true, new TwoBtnTextDialog.OnTwoBtnClick() {
/*        showTwoBtnTextDialog(getString(R.string.app_name), getString(R.string.check_update_text), true, new TwoBtnTextDialog.OnTwoBtnTextClick() {
            @Override
            public void onLeftClick(TextView v) {
                ToastUtil.showToast(v.getText().toString().trim());
            }
            
            @Override
            public void onRightClick(TextView v) {
                ToastUtil.showToast(v.getText().toString().trim());
            }
        });*/
//        showTwoBtnTextDialog(getString(R.string.app_name), "1. 【报工录入】增加重量模式，录入重量转换为数量\n" +
//            "2. 【次品记录】增加重量模式，录入重量转换为数量\n" +
//            "3. 【首页-显示内容设置】增加“型号规格”字段\n" +
//            "4. 【停机记录】选择故障类停机原因时，停机状态变为“故障", true, new TwoBtnTextDialog.OnTwoBtnTextClick() {
//            @Override
//            public void onLeftClick(TextView v) {
//                ToastUtil.showToast(v.getText().toString().trim());
//            }
//
//            @Override
//            public void onRightClick(TextView v) {
//                ToastUtil.showToast(v.getText().toString().trim());
//            }
//        });
        showTwoBtnTextDialog(getString(R.string.app_name), "1. 【报工录入】增加重量模式，录入重量转换为数量\n" +
            "2. 【次品记录】增加重量模式，录入重量转换为数量\n" +
            "3. 【首页-显示内容设置】增加“型号规格”字段\n" +
            "4. 【停机记录】选择故障类停机原因时，停机状态变为“故障”\n" +
            "5. 【模具试模】新增试模申请时，试模类型无默认，选择维修记录后变为“维修后试模”\n" +
            "6. 【模具/设备在修列表】挂起时不允许结束，点击[结束]提示“该维修任务已挂起，请先结束挂起”\n" +
            "7. 【计划单/任务单】APP与WEB页面字段统一\n" +
            "8. 【计划单分配】计划单分配页面“任务备注”默认显示“计划备注”\n" +
            "9. 【任务单】已审核任务单编辑、删除操作提示优化\n" +
            "10. 新增及编辑页面字段填写内容超出限定长度时增加提示\n" +
            "11. 【我的】安卓与ios页面统一\n" +
            "12. 【登录】安卓与ios页面统一\n" +
            "13. 【点检/保养/维修/试模/呼叫】支持设备/模具编号查询\n" +
            "14. 【次品记录】次品记录列表显示设备信息\n" +
            "15. 其它功能优化与bug修复\n" +
            "1. 【报工录入】增加重量模式，录入重量转换为数量\n" +
            "2. 【次品记录】增加重量模式，录入重量转换为数量\n" +
            "3. 【首页-显示内容设置】增加“型号规格”字段\n" +
            "4. 【停机记录】选择故障类停机原因时，停机状态变为“故障”\n" +
            "5. 【模具试模】新增试模申请时，试模类型无默认，选择维修记录后变为“维修后试模”\n" +
            "6. 【模具/设备在修列表】挂起时不允许结束，点击[结束]提示“该维修任务已挂起，请先结束挂起”\n" +
            "7. 【计划单/任务单】APP与WEB页面字段统一\n" +
            "8. 【计划单分配】计划单分配页面“任务备注”默认显示“计划备注”\n" +
            "9. 【任务单】已审核任务单编辑、删除操作提示优化\n" +
            "10. 新增及编辑页面字段填写内容超出限定长度时增加提示\n" +
            "11. 【我的】安卓与ios页面统一\n" +
            "12. 【登录】安卓与ios页面统一\n" +
            "13. 【点检/保养/维修/试模/呼叫】支持设备/模具编号查询\n" +
            "14. 【次品记录】次品记录列表显示设备信息\n" +
            "15. 其它功能优化与bug修复", true, new TwoBtnTextDialog.OnTwoBtnTextClick() {
            @Override
            public void onLeftClick(TextView v) {
                ToastUtil.showToast(v.getText().toString().trim());
            }

            @Override
            public void onRightClick(TextView v) {
                ToastUtil.showToast(v.getText().toString().trim());
            }
        });
    }
    
    public void oneBtnTextDialog(View v) {
        showOneBtnTextDialog(getString(R.string.app_name), getString(R.string.check_update_text), getString(R.string.magpie_ok_text), true, new TwoBtnTextDialog.OnOneBtnTextClick() {
            @Override
            public void onBtnClick(TextView v) {
                ToastUtil.showToast("ok");
            }
        });
    }
    
    public void twoBtnEditDialog(View v) {
        //        showTwoBtnEditDialog(getString(R.string.app_name), null, getString(R.string.please_input_text), false, null, new TwoBtnEditDialog.OnTwoBtnEditClick() {
        showTwoBtnEditDialog(getString(R.string.app_name), null, getString(R.string.please_input_text), InputType.TYPE_CLASS_NUMBER, true, null, new TwoBtnEditDialog.OnTwoBtnEditClick() {
            @Override
            public void onLeftClick(String etContent) {
                ToastUtil.showToast(etContent);
            }
            
            @Override
            public void onRightClick(String etContent) {
                ToastUtil.showToast(etContent);
            }
        });
    }
    
    public void horizontalProgressDialog(View v) {
        final Disposable[] subscribe = new Disposable[1];
        
        showOneBtnProgressDialog(getString(R.string.app_name), getString(R.string.updating_text) + "0%",
            getString(R.string.magpie_cancel_text), false,
            new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (subscribe[0] != null && !subscribe[0].isDisposed()) {
                        subscribe[0].dispose();
                    }
                }
            }, new OneBtnProgressDialog.OnProgressListener() {
                @Override
                public void onStart(final ProgressBar pb, final TextView tvMsg, final TextView tvOk) {
                    //使用RxJava模拟下载
                    subscribe[0] = Observable.intervalRange(0, 101, 0, 100, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (pb != null) {
                                    pb.setProgress(aLong.intValue());
                                    if (tvMsg != null) {
                                        tvMsg.setText(getString(R.string.updating_text) + pb.getProgress() + "%");
                                    }
                                }
                                if (aLong >= 100) {
                                    if (tvMsg != null) {
                                        tvMsg.setText("下载完成");
                                    }
                                    if (tvOk != null) {
                                        tvOk.setText("立即安装");
                                    }
                                    
                                }
                            }
                        });
                }
                
                @Override
                public void onBtnClick() {
                    ToastUtil.showToast("btn clicked");
                }
            });
    }
    
    public void loadingDialog(View v) {
        //        showLoadingDialog(getString(R.string.app_name), getString(R.string.loading_text), false, true);
        //        showLoadingDialog(getString(R.string.loading_text));
        showLoadingDialog(null, getString(R.string.loading_text), false, true);
        
        RxHelper.countDown(5, new Consumer<Long>() {
            @Override
            public void accept(Long disposable) throws Exception {
//                showLoadingDialog("倒计时：" + (5 - disposable - 1) + "s");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                hideLoadingDialog();
            }
        });
        
        
    }
    
    public void bottomSheetDialog(View v) {
        String[] items = getResources().getStringArray(R.array.bottom_sheet_test_data);
        showBottomSheet(items, true, true,  new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ToastUtil.showToast("dialog canceled");
            }
        }, new BottomSheetDialog.OnItemClick() {
            @Override
            public void onItemClick(int position, String item) {
                ToastUtil.showToast("position = " + position + ", content = " + item);
            }
        });
    }
    
    public void closeAll(View v) {
        closeAllAct();
    }
    
    @Override
    public boolean isLandscapeDialog() {
        return false;
    }
    
    @Override
    public String getOkText() {
        return super.getOkText();
    }
    
    @Override
    public String getCancelText() {
        return getString(R.string.cancel_text);
    }
}
