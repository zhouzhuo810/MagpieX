package me.zhouzhuo810.magpiex.ui.adapter.rule;

import java.util.List;

/**
 * 用于adapter本地搜索
 *
 * @param <T>
 */
public interface ISearch<T> {
    /**
     * 开始搜索
     *
     * @param list    搜索前的list
     * @param content 搜索内容
     */
    void startSearch(List<T> list, String content);

    /**
     * 取消搜索
     *
     * @param list 搜索前的list
     */
    void cancelSearch(List<T> list);

}
