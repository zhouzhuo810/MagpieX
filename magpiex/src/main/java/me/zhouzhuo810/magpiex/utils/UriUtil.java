package me.zhouzhuo810.magpiex.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

public class UriUtil {
    
    private UriUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * filePath转Uri
     *
     * @param context   上下文
     * @param filePath  文件路径
     * @param authority FileProvider
     * @return Uri
     */
    public static Uri filePath2Uri(Context context, String filePath, String authority) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        if (Build.VERSION.SDK_INT > 23) {
            return FileProvider.getUriForFile(context, authority, new File(filePath));
        } else {
            return Uri.fromFile(new File(filePath));
        }
    }
    
    /**
     * Uri转filePath
     *
     * @param context 上下文
     * @param uri     Uri
     * @return 路径
     */
    public static String uriToFilePath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return uriToFileApiQ(context, uri);
        } else if (Build.VERSION.SDK_INT >= 19) {
            return getPathHighLevel(context, uri);
        } else {
            return getPathSmallLevel(context, uri);
        }
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    
    
    /**
     * Try to return the absolute file path from the given Uri
     *
     * @return the file path or null
     */
    private static String getPathSmallLevel(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index == -1) {
                        index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    }
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    
    /**
     * 专为Android4.4以上设计的从Uri获取文件路径
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String getPathHighLevel(final Context context, final Uri uri) {
        
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (id.contains(":")) {
                    final String[] split = id.split(":");
                    final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(split[1]));
                    
                    return getDataColumn(context, contentUri, null, null);
                } else {
                    final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                    
                    return getDataColumn(context, contentUri, null, null);
                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                
                return getDataColumn(context, contentUri, selection, selectionArgs);
            } else if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        
        return null;
    }
    
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        
        Cursor cursor = null;
        final String column = MediaStore.Images.ImageColumns.DATA;
        final String[] projection = {column};
        
        String path = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null);
            if (cursor != null && cursor.moveToFirst()) {
                final int columnIndex = cursor.getColumnIndexOrThrow(column);
                path = cursor.getString(columnIndex);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            String saveToPath = context.getExternalFilesDir(null) + File.separator + "images";
            File dir = new File(saveToPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            return copyUriToFile(context, uri, new File(saveToPath + File.separator + fileName));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }
    
    public static String copyUriToFile(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtil.copyStream(inputStream, outputStream);
            if (inputStream != null) {
                inputStream.close();
            }
            outputStream.close();
            return dstFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Android 10 以上适配
     *
     * @param context Context
     * @param uri     Uri
     * @return 路径
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String uriToFileApiQ(Context context, Uri uri) {
        File file = null;
        //android10以上转换
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            if (uri.getPath() == null) {
                return null;
            }
            file = new File(uri.getPath());
        } else if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null) {
                return null;
            }
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(context.getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    if (is != null) {
                        FileUtils.copy(is, fos);
                    }
                    file = cache;
                    fos.close();
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        if (file == null) {
            return null;
        }
        return file.getAbsolutePath();
    }
    
    /**
     * 授予打开的文档树永久性的读写权限
     *
     * @param data onResult的data
     * @param uri  Uri
     */
    public static void grantUriPermission(Intent data, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            try {
                BaseUtil.getApp().getContentResolver().takePersistableUriPermission(uri, takeFlags);
            } catch (Exception ignored) {
            }
        }
    }
}
