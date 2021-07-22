[![](https://jitpack.io/v/zhouzhuo810/MagpieX.svg)](https://jitpack.io/#zhouzhuo810/MagpieX)
[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![MinSdk](https://img.shields.io/badge/MinSDK-17-orange.svg)](https://android-arsenal.com/api?level=17)

# MagpieX

一个 Android 快速开发框架。


### 怎么用


```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```


```
    implementation 'com.github.zhouzhuo810:MagpieX:1.9.6'
```


如果添加上述依赖，则会自动依赖如下第三方库:

```
    //appcompat
    api 'androidx.appcompat:appcompat:1.2.0'
    //RecyclerView
    api 'androidx.recyclerview:recyclerview:1.1.0'
    //Material Design
    api 'com.google.android.material:material:1.2.1'
    //EventBus
    api 'org.greenrobot:eventbus:3.2.0'
    //Glide
    api 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    //RxJava2
    api 'io.reactivex.rxjava3:rxjava:3.0.9'
    //RxAndroid2
    api 'io.reactivex.rxjava3:rxandroid:3.0.0'
    //Retrofit2
    api 'com.squareup.retrofit2:retrofit:2.9.0'
    //Retrofit2+RxJava2
    api 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'
    //Retrofit2+Gson
    api 'com.squareup.retrofit2:converter-gson:2.9.0'
    //OkHttp打印请求数据
    api 'com.squareup.okhttp3:logging-interceptor:3.12.12'
    //OkHttp进度监听 ProgressManager
    api 'me.jessyan:progressmanager:1.5.0'
    //AndPermission
    api 'com.yanzhenjie:permission:2.0.3'
    //ToastUtils
    api 'com.hjq:toast:8.8'
    //ConstraintLayout
    api 'androidx.constraintlayout:constraintlayout:2.0.4'
```

#### 屏幕适配配置

- 在你的 AndroidManifest.xml 文件中的 application 标签中添加如下配置.

```xml
        <!--设计图的宽, 单位是像素(必须配置)，注意：如果是电视，一体机，Pad则是设计图的高，比如1920x1080的电视，应该配置1080-->
        <meta-data
            android:name="design_width"
            android:value="1080" />
        <!--设计图的高, 单位是像素(必须配置)，注意：如果是电视，一体机，Pad则是设计图的宽，比如1920x1080的电视，应该配置1920-->
        <meta-data
            android:name="design_height"
            android:value="1920"/>
        <!-- 屏幕适配模式，(可选) auto:自动(默认)，width:按宽度，height:按高度，wh:宽按宽，高按高  -->
        <!-- width: 按宽度缩放(宽高基于竖屏状态下定义，比如横屏设备1920x1080的宽度还是1080，而不是1920) -->
        <!-- height: 按高度缩放(宽高基于竖屏状态下定义，比如横屏设备1920x1080的宽度还是1080，而不是1920) -->
        <!-- auto: 横屏设备按高度，竖屏设备按宽度(宽高基于竖屏状态下定义，比如横屏设备1920x1080的宽度还是1080，而不是1920) -->
        <!-- wh: 宽按宽度缩放，高按高度缩放(宽高基于竖屏状态下定义，比如横屏设备1920x1080的宽度还是1080，而不是1920) -->
        <meta-data
            android:name="screen_adapt_type"
            android:value="width" />
        <!--设计图对应的标准dpi,根据下面的那张图找到对应的dpi,比如1080就对应480dpi,如果拿到的是其他宽度的设计图,那么选择一个相近的dpi就好了-->
        <!--
            宽         	    240 	320 	480 	720     1080 	1440
            DPI等级	        LDPI	MDPI	HDPI	XHDPI	XXHDPI	XXXHDPI
            DPI数值	        120	    160	    240	    320	    480	    640
        -->
        <meta-data
            android:name="design_dpi"
            android:value="480" />

        <!--全局字体的大小倍数,有时候老板会觉得你的所有的字小了或者大了,你总不能一个一个去改吧-->
        <meta-data
            android:name="font_size"
            android:value="1.0" />

        <!--你的布局里面用的是px这就写px,你的布局里面用的是dp这就写dp,要统一,不要一会儿px一会儿dp,字体也用px或者dp,不要用sp,微信qq用的肯定不是sp.-->
        <meta-data
            android:name="unit"
            android:value="px" />
```

- 在你的自定义 Application 继承 BaseApplication ，并在 onCreate 方法中添加如下代码.

```java

public class MyApplication extends BaseApplication {

    private Map<Integer, Locale> mSupportLanguages;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化8.0通知渠道
        NoticeUtil.initNoticeChannel("您的渠道id", "您的渠道名称", "您的渠道描述", 0, true);
    }


    @Override
    public boolean shouldSupportMultiLanguage() {
        return true;
    }

    @Override
    public Map<Integer, Locale> getSupportLanguages() {
        if (mSupportLanguages == null) {
            mSupportLanguages = new HashMap<>();
            mSupportLanguages.put(MyCons.LANGUAGE_CH_SIMPLE, Locale.SIMPLIFIED_CHINESE);
            mSupportLanguages.put(MyCons.LANGUAGE_CH_COMPLEX, Locale.TRADITIONAL_CHINESE);
            mSupportLanguages.put(MyCons.LANGUAGE_EN, Locale.ENGLISH);
            mSupportLanguages.put(MyCons.LANGUAGE_VI, new Locale("vi"));
        }
        return mSupportLanguages;
    }

}

- 注意 styles 文件中 AppTheme 必须继承 `MagpieTheme` 或者 `MagpieTheme.NoActionBar`.

```xml
<style name="AppTheme" parent="MagpieTheme.NoActionBar">
    ...
</style>    
```

- 最后注意所有布局中使用 px 作为尺寸单位，包括字体大小.

- 如果你的 Activity 不继承 BaseActivity, 那你必须在 Activity 的 onCreate 方法的 setContentView 后面调用
 `SimpleUtil.resetScale(this);` and `SimpleUtil.scaleView(getWindow().getDecorView());`。

- 如果你的 Fragment 不继承 BaseFragment, 那你必须在 Fragment 的 onViewCreated 方法的 inflate 后面调用
 `SimpleUtil.scaleView(view);`。

- 如果你的列表适配器不继承 LvBaseAdapter or RvBaseAdapter, 那么你必须在创建完 ViewHolder 后，对 ViewHolder的view进行缩放。
 `SimpleUtil.scaleView(view);`.

- 如果你使用自定义View，涉及到尺寸计算的时候，可以调用如下方法计算缩放后的值。
`SimpleUtil.getScaledValue(int);`。

#### 工具类简介

- [BaseUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/BaseUtil.java)
> 核心工具类，持有 application 对象。

- [SimpleUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/SimpleUtil.java)
> 常用工具类，包括了屏幕适配和资源获取等相关方法。

- [ApiUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/ApiUtil.java)
> RxJava2+Retrofit2+OkHttp3 Api对象初始化工具.

- [LanguageUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/LanguageUtil.java)
> 多语言切换工具.

- [KeyboardUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/KeyboardUtil.java)
> 键盘操作工具.

- [ApkUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/ApkUtil.java)
> 安装 APK 工具.

- [BarUtils](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/BarUtils.java)
> 状态栏操作工具类。

- [SpUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/SpUtil.java)
> SharedPreferences 操作工具类。

- [StrUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/StrUtil.java)
> 字符串操作工具类。

- [FileUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/FileUtil.java)
> 文件操作工具类。

- [DateUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/DateUtil.java)
> 日期转换工具类。

- [RxHelper](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/RxHelper.java)
> RxJava 倒计时，轮训，异步任务工具类。

- [CrashUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/CrashUtil.java)
> UncaughtExceptionHandler 异常捕获工具类。

- [CopyUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/CopyUtil.java)
> 剪切板工具类。

- [ImageUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/ImageUtil.java)
> 图片操作工具类。

- [ToastUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/ToastUtil.java)
> 吐司工具类。

- [NoticeUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/NoticeUtil.java)
> 通知工具类。

- [NumberUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/NumberUtil.java)
> 数字转换工具类。

- [ColorUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/ColorUtil.java)
> 颜色处理工具类.

- [EditUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/EditUtil.java)
> 输入框相关操作工具类.

- [FontUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/FontUtil.java)
> 设置定义字体.

- [AssetsUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/AssetsUtil.java)
> Assets 内容转 String 工具.

- [ShellUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/ShellUtil.java)
> Shell 操作工具.

- [NetworkUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/NetworkUtil.java)
> 网络状态工具.

- [CRC16](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/CRC16.java)
> CRC16 校验工具类.

- [CollectionUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/CollectionUtil.java)
> 集合操作工具类。

- [ByteUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/ByteUtil.java)
> 字节操作工具类.

- [RandomUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/RandomUtil.java)
> 随机数生成工具.

- [PackageUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/PackageUtil.java)
> 检查 app 是否安装，获取 app 版本号等。

- [ShareUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/ShareUtil.java)
> 分享文本和文件的工具类。

- [DrawableUtil](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/utils/DrawableUtil.java)
> Drawable相关工具类.

#### 框架包含的控件

- [Indicator](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/ui/widget/Indicator.java)
> ViewPager 顶部导航控件.

- [Indicator2](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/ui/widget/Indicator2.java)
> ViewPager2 顶部导航控件.

- [TabBar](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/ui/widget/TabBar.java)
> 底部导航控件.

- [TitleBar](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/ui/widget/TitleBar.java)
> 标题栏控件.

- [MarkView](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/ui/widget/MarkView.java)
> 自定义角标控件.

- [SimpleSpinner](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/ui/widget/SimpleSpinner.java)
> 下拉操作控件。

- [ShineTextView](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/ui/widget/ShineTextView.java)
> 多种颜色闪烁的TextView。

- [ScrollListRecyclerView](https://github.com/zhouzhuo810/MagpieX/blob/master/magpiex/src/main/java/me/zhouzhuo810/magpiex/ui/widget/ScrollListRecyclerView.java)
> 自动滚动的 RecyclerView。


### 全局属性配置说明

```
    <!--  列表对话框列表文字颜色  -->
    <attr name="x_item_text_color" format="color" />
    <!--  列表对话框列表点击后的背景颜色 v21- -->
    <attr name="x_item_bg_color" format="color" />
    <!--  列表对话框列表点击后的背景颜色 v21+ -->
    <attr name="x_item_bg_color_21" format="color" />
    <!--  对话框标题背景颜色 -->
    <attr name="x_dialog_title_bg_color" format="color" />
    <!--  对话框内容背景颜色 -->
    <attr name="x_dialog_content_bg_color" format="color" />
    <!--  对话框标题文字颜色 -->
    <attr name="x_dialog_title_text_color" format="color" />
    <!--  对话框内容文字颜色 -->
    <attr name="x_dialog_content_text_color" format="color" />
    <!--  对话框按钮文字颜色 -->
    <attr name="x_dialog_btn_text_color" format="color" />
    <!--  对话框按钮背景资源 -->
    <attr name="x_dialog_btn_bg_drawable" format="reference" />
    <!-- loading 对话框 ios 样式的菊花图标 -->
    <attr name="x_dialog_ios_loading_drawable" format="reference" />
    <!-- loading 对话框 ios 样式的文字颜色 -->
    <attr name="x_dialog_ios_loading_text_color" format="reference" />

```

### 更新日志

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

### Proguard

For magpiex, see [app/proguard-rules.pro](app/proguard-rules.pro)

### Thanks

[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)

[AndroidScreenAdaptation](https://github.com/yatoooon/AndroidScreenAdaptation)


### License

```
Copyright © zhouzhuo810

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```





