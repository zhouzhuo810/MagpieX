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
    implementation 'com.github.zhouzhuo810:MagpieX:2.0.7'
```


如果添加上述依赖，则会自动依赖如下第三方库:

```
  //appcompat
    api 'androidx.appcompat:appcompat:1.2.0'
    //RecyclerView
    api 'androidx.recyclerview:recyclerview:1.2.0'
    //Material Design
    api 'com.google.android.material:material:1.3.0'
    //EventBus
    api 'org.greenrobot:eventbus:3.2.0'
    //Glide
    api 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    //RxJava2
    api 'io.reactivex.rxjava3:rxjava:3.0.9'
    //RxAndroid2
    api 'io.reactivex.rxjava3:rxandroid:3.0.0'
    //RxBinding
    api 'com.jakewharton.rxbinding4:rxbinding:4.0.0'
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
    //ToastUtils
    api 'com.hjq:toast:8.8'
    //ConstraintLayout
    api 'androidx.constraintlayout:constraintlayout:2.0.4'
    //XXPermissions
    api 'com.github.getActivity:XXPermissions:12.2'
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

### 日志过滤标签说明

- PrintAdapterName : Adapter 创建时打印类名
- PrintActivityName : Activity 可见时打印类名
- PrintFragmentName : Fragment 可见时打印类名

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

[UPDATE.md](UPDATE.md)

### Proguard

see [app/proguard-rules.pro](app/proguard-rules.pro)

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





