package me.zhouzhuo810.magpiex.utils.intercepter;


import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import me.zhouzhuo810.magpiex.cons.Cons;
import me.zhouzhuo810.magpiex.utils.BaseUtil;
import me.zhouzhuo810.magpiex.utils.NoticeUtil;
import me.zhouzhuo810.magpiex.utils.SpUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 全局参数拦截器，用于设置全局请求参数，通知栏弹出通知，点击复制请求路径
 *
 * @author zhouzhuo810
 */
public class CopyNoticeInterceptor implements Interceptor {
    
    private static final String TAG = CopyNoticeInterceptor.class.getSimpleName();
    
    private int mLogoId;
    private String mChannelId;
    private String mNoticeTitle;
    private String mTargetAppPackageName;
    
    private static final Charset UTF8 = Charset.forName("UTF-8");
    
    public CopyNoticeInterceptor(int logoId, String channelId, String noticeTitle, String targetAppPackageName) {
        this.mLogoId = logoId;
        this.mChannelId = channelId;
        this.mNoticeTitle = noticeTitle;
        this.mTargetAppPackageName = targetAppPackageName;
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
                //发通知，用于复制url
                NoticeUtil.showNormalNoticeWithCopyAction(BaseUtil.getApp(), mNoticeTitle + "-" + clazzName, request.url().encodedPath(), "POST: " + request.url() + "\nBody: " + body + "\nClass: " + clazzName + "\n", true, false,
                    mLogoId, false, mChannelId, mTargetAppPackageName);
            } else {
                //发通知，用于复制url
                NoticeUtil.showNormalNoticeWithCopyAction(BaseUtil.getApp(), mNoticeTitle + "-" + clazzName, request.url().encodedPath(), "GET: " + request.url() + "\nClass: " + clazzName + "\n", true, false,
                    mLogoId, false, mChannelId, mTargetAppPackageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            // Buffer the entire body.
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            requestInfo += "\n" + "RESPONSE：" + buffer.clone().readString(UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, requestInfo);
        return response;
    }
}
