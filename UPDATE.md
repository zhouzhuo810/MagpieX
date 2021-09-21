
> 2.0.5
> - BottomSheet 对话框样式调整。
> - 权限框架改成 XXPermissions 

> 2.0.4
> - Indicator 解除单行限制。

> 2.0.3
> - 修复对话框在TV上无法选中按钮问题。
>
> 2.0.2
> - 修复kt类名打印无效问题。

> 2.0.1
> - RvBaseAdapter、BaseActivity、BaseFragment 分别添加DEBUG模式下类名打印支持java或kt。

> 2.0.0
> - MarkView 兼容安卓系统的上下左右 padding 属性，且保证oval形状时个位数的情况下是个圆。

> 1.9.9
> - BaseActivity 提供 getOriginalContext() 方法。

> 1.9.8
> - RvBaseAdapter、BaseActivity、BaseFragment 分别添加DEBUG模式下类名打印，打印级别d。

> 1.9.7
> - 修复 LvBaseAdapter#getDropDownView() 方法布局id弄错了的问题。

> 1.9.6
> - 修复 TwoBtnTextDialog 显示异常问题。

> 1.9.5
> - 移除 BaseRecyclerViewAdapterHelper 库。

> 1.9.4
> - 修复 FileChooser 不弹出选择界面问题。

> 1.9.3
> - 修复只有一个 TextView 的布局不缩放字体大小的问题。

> 1.9.1
> - 升级 BRVAH 到 3.x 版本。

> 1.9.0
> - 新增 ViewPager2 指示器 Indicator2。

> 1.8.9
> - SimpleUtil 支持 setScaleTag 和 hasScaled 方法。

> 1.8.8
> - 修复 BaseApplication 中 isScreenAdaptDisable 方法无效问题；
> - 添加 x_dialog_btn_bg_drawable 自定义属性；
> - RxHelper 支持 debounce 方法；
> - 添加 RxBinding 库依赖；

> 1.8.7
> - 优化屏幕适配判断是否缩放的逻辑，避免过于频繁访问 SharedPreferences；

> 1.8.6
> - BaseApplication 添加`isScreenAdaptDisable`方法；

> 1.8.5
> - TitleBar添加`setOnRightClickListener`和`setOnMiddleClickListener`方法；

> 1.8.4
> - BaseAct和BaseFgm中添加shouldNotScreenAdapt方法，支持单个页面控制是否适配；

> 1.8.3
> - 屏幕适配支持开关，详见BaseUtil；
```
 /**
     * 设置屏幕适配是否开启
     *
     * @param enable 是否
     */
    public static void setScreenAdaptEnable(boolean enable) {
        SpUtil.putBoolean(Cons.SP_KEY_OF_SCREEN_ADAPT_ENABLE, enable);
    }

    /**
     * 屏幕适配是否开启
     *
     * @return 是否，默认开启
     */
    public static boolean isScreenAdaptEnable() {
        return SpUtil.getBoolean(Cons.SP_KEY_OF_SCREEN_ADAPT_ENABLE, true);
    }
```

> 1.8.2
> - RvBaseAdapter的ViewHolder添加getContext()方法；

> 1.8.1
> - BaseFragment兼容ViewPager2；

> 1.8.0
> - 升级RxJava到版本3；
> - 升级Retrofit到2.9.0;
> - 升级Glide到4.12.0;

> 1.7.6
> - 修复TwoBtnTextDialog文字超过屏幕时，按钮不显示的问题；

> 1.7.5
> - 修改LanguageUtil#isSupportLanguage()方法,没有配置`public Map<Integer, Locale> getSupportLanguages()`时，直接返回false；

> 1.7.4
> - 屏幕适配添加适配字体大小倍率缩放；

> 1.7.2
> - 屏幕适配类型支持"wh"，宽按宽缩放，高按高缩放；

> 1.7.2
> - BaseFragment新增`refreshDataIfNeeded(Bundle params)`方法。
> - BaseFragment新增`onLazyLoadData()`方法。

> 1.7.1
> - 解决BaseFragment不刷新问题；

> 1.7.0
> - 修复输入对话框左右按钮自定义无效问题；

> 1.6.9
> - 优化BaseFragment拦截返回按钮逻辑；

> 1.6.8
> - 修复Indicator切换文字颜色不对应问题；

> 1.6.7
> - 添加接口调试模式忽略接口配置；；

> 1.6.6
> - 修复Indicator设置marginStart导致圆角背景显示不全问题；

> 1.6.5
> - Indicator支持配置圆角背景效果;
> - 适配FloatingActionButton的fabCustomSize字段缩放；

> 1.6.4
> - Indicator支持颜色渐变和文字大小渐变;

> 1.6.3
> - SimpleUtil支持计算浮点数大小;

> 1.6.2
> - MarkView支持padding属性；
> - 修复MarkView边框绘制不全问题；

> 1.6.1
> - 修复拦截器指定app跳转无法返回原app问题；

> 1.6.0
> - 复制url拦截器支持复制后跳转到制定包名的app；

> 1.5.9
> - 接口拦截器分享和复制通知标题带上当前可见界面的类名；

> 1.5.8
> - 接口拦截器分享和复制通知内容带上当前可见界面的类名；

> 1.5.7
> - 修复onResume导致的奔溃问题；

> 1.5.6
> - 修复startActivity导致的奔溃问题；

> 1.5.5
> - 适配9.0 WebView多进程使用问题；

> 1.5.4
> - 适配ConstraintLayout的guideBegin和guideEnd；

> 1.5.3
> - 修复ConstraintLayout设置minHeight导致的适配问题；
> - 适配ConstraintLayout相关属性；

> 1.5.2
> - 尝试修复TextView设置minHeight导致的适配问题；

> 1.5.1
> 1.5.0
> - 当MarkView最大值为0时不限制最大值；

> 1.4.9
> - 修复MarkView在Stroke模式显示异常问题；

> 1.4.8
> - MarkView支持Stroke模式；

> 1.4.7
> - 修复ScrollListRecyclerView和ScrollGridRecyclerView报错问题；

> 1.4.6
> - 兼容创建WebView导致多语言失效问题；
> - 将`LanguageUtil`的过时方法替换；

> 1.4.5
> - 修复对话框底部确认取消按钮没有垂直方向对齐问题；

> 1.4.4
> - 修复重启APP后Application级别的Context获取字符串没有适配多语言问题；
> - 新增`DrawableUtil`工具类；

> 1.4.3
> - 部分工具类加上注视，适配框架判断是否缩放的Tag改成id类型；

> 1.4.2
> - 修复缩放setMaxHeight造成的幕适配问题；

> 1.4.1
> - 参考阿里编码规范修改部分代码；
> - 增加WebUtil工具；

> 1.4.0
>- 修复对话框show之后立即关闭导致无法关闭的问题；

> 1.3.9
>- 修改RxHelper参数int为long类型；

> 1.3.8
>- 新增`RvQuickAdapter`和`RvMultiItemQuickAdapter`适配器；
>- `AutoSplitTextView`新增`MIN_TEXT_LENGTH_TO_START_THREAD`来决定是否启用线程,默认1000；

> 1.3.7
>- 缩放添加Tag防止重复缩放；

> 1.3.6
>- 修复`androidx.appcompat:appcompat:1.2.0`导致多语言切换失效问题;

> 1.3.5
>- 修改屏幕适配的方向判断逻辑；

> 1.3.4
>- 修改屏幕适配的方向判断逻辑；

> 1.3.3
>- 修改屏幕适配的方向判断逻辑；

> 1.3.2
>- 修改`CopyNoticeInterceptor`和`ShareNoticeInterceptor`只展示接口路径；

> 1.3.1
>- 修复`CopyNoticeInterceptor`和`ShareNoticeInterceptor`点击不同通知获取通知内容不对应问题；

> 1.3.0
- 修复嵌套Fragment，如果父Fragment HIDDEN后再恢复，子Fragment只恢复了最后一次可见的界面，其它子Fragment在父Fragment HIDDEN之前也是Show状态界面未恢复Bug

> 1.2.9
- 修复屏幕适配横竖屏切换适配出错问题；

> 1.2.8
- 修复BaseFragment的setUserVisibleHint调用错误问题；
- 修复appcompat库升级版本导致多语言切换问题，暂时降版本；

> 1.2.7
- 修改TitleBar，移除RlLef他和RlRight；

> 1.2.6
- PackageUtil支持获取版本号和版本名称；

> 1.2.5
- BaseFragment拦截RootView的触摸事件；

> 1.2.4
- 新增NumberUtil；

> 1.2.3
- 修改OneProgressDialog显示时屏幕常亮；
- 修改TabBar支持6个；

> 1.2.2 (Published)
- 修改BaseFragment部分逻辑；

> 1.2.1 (Published)
- 修改BaseFragment部分逻辑；
- 修改文本对话框对齐方式规则；
- 添加线程池工具类；


>> 1.2.0 (Published)
- 修改对话框回收逻辑；
- 优化键盘工具类，和文件工具类；

> 1.1.9 (Published)
- 修改KeyboardUtil；

> 1.1.7 (Published)
- 修改BaseFragment逻辑；

> 1.1.6 (Published)
- 更改initView顺序，升级ToastUtils；

> 1.1.5 (Published)
> 1.1.4 (Published)
> 1.1.3 (Published)

- 修改BaseFragment逻辑；

> 1.1.1 (Published)

- 2020/4/23 ；多语言支持自定义语言；修复若干bug；

> 1.1.0 (Published)

- 2020/2/12 修复RvBaseAdapter的bug；

> 1.0.9 (Published)

- 2020/1/8 集成TV的滚动控件和地图控件；
- 2020/1/8 解决Application Context获取字符串时国际化无效问题；

> 1.0.8 (Published)

- 2019/12/13 改造SimpleSpinner；

> 1.0.6 (Published)

- 2019/9/25 加入一个按钮普通对话框；
- 2019/9/25 对话框支持横屏模式；

> 1.0.4 (Published)

- 2019/9/18 修改CopyUtil的String为CharSequence；

> 1.0.3 (Published)

- 2019/9/16 支持夜间模式颜色；

> 1.0.2 (Published)

- 2019/9/10 TabBar添加动态设置文字颜色功能；

> 1.0.1 (Published)

- 2019/9/6 TabBar添加setTabNames方法

> 1.0.0 (Published)

- 2018/9/2 initial commit.