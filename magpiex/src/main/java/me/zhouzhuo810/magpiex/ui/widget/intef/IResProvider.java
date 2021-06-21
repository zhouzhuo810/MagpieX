package me.zhouzhuo810.magpiex.ui.widget.intef;

import java.nio.charset.Charset;

public interface IResProvider {

    public int getSelectedIcon(int position);

    public int getUnselectedIcon(int position);

    public CharSequence getTitle(int position);
}
