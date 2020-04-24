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
     implementation 'com.github.zhouzhuo810:MagpieX:1.1.2'
```

If you use this. That means you added dependencies below:
```
    //v7
    api 'androidx.appcompat:appcompat:1.1.0'
    //RecyclerView
    api 'androidx.recyclerview:recyclerview:1.1.0'
    //BaseRecyclerViewAdapterHelper
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.49-androidx'
    //EventBus
    api 'org.greenrobot:eventbus:3.1.1'
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
    api 'com.hjq:toast:8.0'
```

#### Screen Adapter

- Add UI Design size in your AndroidManifest.xml.

```xml
        <!--设计图的宽,单位是像素,推荐用markman测量-->
        <meta-data
            android:name="design_width"
            android:value="1080" />
        <!--设计图的高,单位是像素,推荐用markman测量，（可选配置项，配置了design_height则按宽高缩放，否则只按design_width缩放）-->
        <meta-data
            android:name="design_height"
            android:value="1920"/>
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
 `   ScreenAdapterUtil.getInstance().loadView(getWindow().getDecorView());` in `Activity#onCreate()` method after `setContentView()`.

- If you do not use BaseFragment, Then you should invoke the method
 `   ScreenAdapterUtil.getInstance().loadView(view);` in `Fragment#onCreateView()` method after `inflate()`.

- If you do not use LvBaseAdapter or RvBaseAdapter, Then you should invoke the method
 `ScreenAdapterUtil.getInstance().loadView(view);` when you create ViewHolder.

- If you want to use in custom View, you should invoke the method
`ScreenAdapterUtil.getInstance().getScaledValue(int);` when you what to scale the dynamic value.

- If you don't want to use `ScreenAdapterUtil.getInstance()`, just replace it with `SimpleUtil`.

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

### Update Logs

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





