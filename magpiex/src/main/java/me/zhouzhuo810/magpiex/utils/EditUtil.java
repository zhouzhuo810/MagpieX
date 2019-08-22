package me.zhouzhuo810.magpiex.utils;

import android.widget.EditText;

/**
 * EditText操作相关
 */
public class EditUtil {


    /**
     * 获取光标所在行的内容（不包括换行符）
     *
     * @param et EditText
     * @return 内容
     */
    public static CharSequence getEditTextCursorLineContent(EditText et) {
        final String content = et.getText().toString();
        if (content.length() == 0) {
            return content;
        }
        //获取行头下标
        int lineHeadIndex = getEditTextCursorLineFirstIndex(et);
        //获取行尾下标
        int lineEndIndex = getEditTextCursorLineLastIndex(et);
        //获取当前行内容
        return content.substring(lineHeadIndex, lineEndIndex + 1);
    }

    /**
     * 获取光标所在行的内容（不包括换行符）
     *
     * @param et EditText
     * @return 内容
     */
    public static String getEditTextCursorLineContentString(EditText et) {
        final String content = et.getText().toString();
        if (content.length() == 0) {
            return content;
        }
        //获取行头下标
        int lineHeadIndex = getEditTextCursorLineFirstIndex(et);
        //获取行尾下标
        int lineEndIndex = getEditTextCursorLineLastIndex(et);
        //获取当前行内容
        return content.substring(lineHeadIndex, lineEndIndex + 1);
    }

    /**
     * 获取某个下标所在行的内容（不包括换行符）
     *
     * @param et    EditText
     * @param index 下标
     * @return 内容
     */
    public static String getEditTextLineContentWithIndex(EditText et, int index) {
        final String content = et.getText().toString();
        if (content.length() == 0) {
            return content;
        }
        //获取行头下标
        int lineHeadIndex = getEditTextLineFirstIndexWithIndex(et, index);
        //获取行尾下标
        int lineEndIndex = getEditTextLineLastIndexWithIndex(et, index);
        //获取当前行内容
        return content.substring(lineHeadIndex, lineEndIndex + 1);
    }

    /**
     * 获取某个下标所在行的行首下标（不包括换行符）
     *
     * @param et EditText
     * @return 下标
     */
    public static int getEditTextCursorLineFirstIndex(EditText et) {
        int index = et.getSelectionStart();
        if (index < 0) {
            index = 0;
        }
        return getEditTextLineFirstIndexWithIndex(et, index);
    }

    /**
     * 获取某个下标所在行的行首下标（不包括换行符）
     *
     * @param et    EditText
     * @param index 下标
     * @return 下标
     */
    public static int getEditTextLineFirstIndexWithIndex(EditText et, int index) {
        final String content = et.getText().toString();
        if (index > 0) {
            for (int i = index - 1; i >= 0; i--) {
                if (content.length() > i) {
                    if (content.charAt(i) == '\n') {
                        return i + 1;
                    } else if (i == 0) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 获取光标所在行的行尾下标（不包括换行符）
     *
     * @param et EditText
     * @return 下标
     */
    public static int getEditTextCursorLineLastIndex(EditText et) {
        int index = et.getSelectionStart();
        if (index < 0) {
            index = 0;
        }
        return getEditTextLineLastIndexWithIndex(et, index);
    }

    /**
     * 获取某个下标所在行的行尾下标（不包括换行符）
     *
     * @param et    EditText
     * @param index 下标
     * @return 下标
     */
    public static int getEditTextLineLastIndexWithIndex(EditText et, int index) {
        final String content = et.getText().toString();
        if (index >= 0) {
            for (int i = index; i < content.length(); i++) {
                if (content.charAt(i) == '\n') {
                    return i - 1;
                } else if (i == content.length() - 1) {
                    return i;
                }
            }
            if (index > 0) {
                return index - 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

}
