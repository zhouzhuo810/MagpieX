package me.zhouzhuo810.magpiex.utils.intercepter;


import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import androidx.annotation.Nullable;
import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.CollectionUtil;
import me.zhouzhuo810.magpiex.utils.NoticeUtil;
import me.zhouzhuo810.magpiex.utils.SpUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 全局参数拦截器，用于设置全局请求参数，通知栏弹出通知，点击分享请求路径
 *
 * @author zhouzhuo810
 */
public class ShareNoticeInterceptor implements Interceptor {
    
    private static final String TAG = ShareNoticeInterceptor.class.getSimpleName();
    
    private int mLogoId;
    private String mChannelId;
    private String mNoticeTitle;
    private List<String> ignoredApi;
    
    private static final Charset UTF8 = Charset.forName("UTF-8");
    
    public ShareNoticeInterceptor(int logoId, String channelId, String noticeTitle) {
        this.mLogoId = logoId;
        this.mChannelId = channelId;
        this.mNoticeTitle = noticeTitle;
    }
    
    public ShareNoticeInterceptor(int logoId, String channelId, String noticeTitle, @Nullable List<String> ignoredApi) {
        this(logoId, channelId, noticeTitle);
        this.ignoredApi = ignoredApi;
    }
    
    @SuppressWarnings("ConstantConditions")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        
        String requestInfo = ".\nREQUEST URL：" + request.url();
        try {
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            }
            String body = requestBuffer.readString(requestBuffer.size() > 2048 ? 2048 : requestBuffer.size(), UTF8);
            String clazzName = SpUtil.getString(Cons.SP_KEY_OF_CURRENT_ACTIVITY_OR_FRAGMENT_NAME);
            if (!TextUtils.isEmpty(body)) {
                requestInfo += "\nREQUEST BODY：" + body;
                //发通知，用于分享url
                if (!hasIgnored(request.url().encodedPath())) {
                    NoticeUtil.showNormalNoticeWithShareAction(BaseUtil.getApp(), mNoticeTitle + " - " + clazzName, request.url().encodedPath(), "POST: " + request.url() + "\n\nBody: " + body + "\n\nClass: " + clazzName + "\n", true, false,
                        mLogoId, false, mChannelId);
                }
            } else {
                //发通知，用于分享url
                if (!hasIgnored(request.url().encodedPath())) {
                    NoticeUtil.showNormalNoticeWithShareAction(BaseUtil.getApp(), mNoticeTitle + " - " + clazzName, request.url().encodedPath(), "GET: " + request.url() + "\n\nClass: " + clazzName + "\n", true, false,
                        mLogoId, false, mChannelId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            requestInfo += "\n" + "RESPONSE：" + buffer.clone().readString(UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, requestInfo);
        return response;
    }
    
    /**
     * 是否忽略此API
     *
     * @param encodedPath 接口路径
     * @return 是否忽略
     */
    private boolean hasIgnored(String encodedPath) {
        if (CollectionUtil.isEmpty(ignoredApi)) {
            return false;
        }
        return ignoredApi.contains(encodedPath);
    }
    
}
