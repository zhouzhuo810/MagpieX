[![](https://jitpack.io/v/zhouzhuo810/MagpieX.svg)](https://jitpack.io/#zhouzhuo810/MagpieX)
[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![MinSdk](https://img.shields.io/badge/MinSDK-17-orange.svg)](https://android-arsenal.com/api?level=17)

# MagpieX

 A powerful Android Develop Framework.


### How to use


```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

> For Phone And Pad (Support) .

```
     implementation 'com.github.zhouzhuo810:MagpieX:1.7.0'
```

If you use this. That means you added dependencies below:
```
    //v7
    api 'androidx.appcompat:appcompat:1.2.0'
    //RecyclerView
    api 'androidx.recyclerview:recyclerview:1.1.0'
    //Material Design
    api 'com.google.android.material:material:1.2.1'
    //BaseRecyclerViewAdapterHelper
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.49-androidx'
    //EventBus
    api 'org.greenrobot:eventbus:3.2.0'
    //Glide
    api 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'
    //RxJava2
    api "io.reactivex.rxjava2:rxjava:2.2.10"
    //RxAndroid2
    api 'io.reactivex.rxjava2:rxandroid:2.1.1'
    //Retrofit2
    api 'com.squareup.retrofit2:retrofit:2.5.0'
    //Retrofit2+RxJava2
    api 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
    //Retrofit2+Gson
    api 'com.squareup.retrofit2:converter-gson:2.5.0'
    //OkHttp打印请求数据
    api 'com.squareup.okhttp3:logging-interceptor:3.12.0'
    //OkHttp进度监听progressmanager
    api 'me.jessyan:progressmanager:1.5.0'
    //AndPermission
    api 'com.yanzhenjie:permission:2.0.3'
    //ToastUtils
    api 'com.hjq:toast:8.6'
    //ConstraintLayout
    api 'androidx.constraintlayout:constraintlayout:2.0.4'
```

#### Screen Adapter

- Add UI Design size in your AndroidManifest.xml.

```xml
        <!--设计图的宽, 单位是像素(必须配置)，注意：如果是电视，一体机，Pad则是设计图的高，比如1920x1080的电视，应该配置1080-->
        <meta-data
            android:name="design_width"
            android:value="1080" />
        <!--设计图的高, 单位是像素(必须配置)，注意：如果是电视，一体机，Pad则是设计图的宽，比如1920x1080的电视，应该配置1920-->
        <meta-data
            android:name="design_height"
            android:value="1920"/>
        <!-- 屏幕适配模式，(可选) auto:自动(默认)，width:按宽度，height:按高度  -->
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

- init Magpie Context in your custom Application.

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

- You must use The `MagpieTheme` Or `MagpieTheme.NoActionBar` Theme.

```xml
<style name="AppTheme" parent="MagpieTheme.NoActionBar">
    ...
</style>    
```

- ok, Just use `px` unit in your layout.

- If you do not use BaseActivity, Then you should invoke the method
 `SimpleUtil.resetScale(this);` and `SimpleUtil.scaleView(getWindow().getDecorView());` in `Activity#onCreate()` method after `setContentView()`.

- If you do not use BaseFragment, Then you should invoke the method
 `   SimpleUtil.scaleView(view);` in `Fragment#onCreateView()` method after `inflate()`.

- If you do not use LvBaseAdapter or RvBaseAdapter, Then you should invoke the method
 `SimpleUtil.scaleView(view);` when you create ViewHolder.

- If you want to use in custom View, you should invoke the method
`SimpleUtil.getScaledValue(int);` when you what to scale the dynamic value.

#### BaseApplication

it supports:
- Multi-Language.

#### BaseActivity

it supports:
- Multi-Style Dialog.
- Multi-Language support.
- Replace Fragment easily.


#### BaseFragment

it supports:
- Multi-Style Dialog.
- Load data lazily.

#### Utils

- BaseUtil
> This is the key util.

- ScreenAdapterUtil
> It's use for screen Adapter.

- ApiUtil
> It's for RxJava2+Retrofit2+OkHttp3 Api Creation.

- LanguageUtil
> It's for Language Setting.

- KeyboardUtil
> It's for Software Keyboard operating.

- ApkUtil
> It's for Apk Installation.

- BarUtils
> It's for StatusBar Setting.

- SpUtil
> It's use for SharedPreferences.

- StrUtil
> It's use for String operating.

- FileUtil
> It's use for File operating.

- DateUtil
> It's use for Date operating.

- RxHelper
> It's use for RxJava.

- CrashUtil
> It's for UncaughtExceptionHandler.

- CopyUtil
> It's for ClipboardManager.

- ImageUtil
> It's for bitmap operating.

- ToastUtil
> It's for simple Toast.

- NoticeUtil
> It's for Notification.


- NumberUtil
> It's for digital conversion.

#### Widgets

- Indicator
> It's for viewpager indicator.

- TabBar
> It's for bottom navigation.

- TitleBar
> It's same as ToolBar.

- MarkView
> It's for bandage.

- SimpleSpinner
> It's for using Spinner simply.

( These utils is added in v1.0.1.)

- ColorUtil
> It's for color operating.

- EditUtil
> It's for EditText operating.

- SimpleSpinner
> It's for Spinner operating.

- FontUtil
> It's for setting custom typeface in TextView.

- AssetsUtil
> It's for converting file's content to String in Assets.

- SimpleUtil
> It's for converting file's content to String in Assets.

- ShellUtil
> It's for running shell command.

- NetworkUtil
> It's for network status monitoring.

- CRC16
> It's for CRC16 encryption.

- ByteUtil
> It's for Byte operating.

- RandomUtil
> It's for generate a random number.

- PackageUtil
> It's for checking if app is installed.

- ShareUtil
> It's for sharing Text or File to other app.

- DrawableUtil
> Drawable相关工具类.

### Update Logs

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





