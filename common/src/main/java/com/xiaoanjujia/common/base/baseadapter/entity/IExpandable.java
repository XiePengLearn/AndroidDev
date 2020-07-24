package com.xiaoanjujia.common.base.baseadapter.entity;

import java.util.List;

/**
 * implement the interface if the item is expandable
 * Created by xp on 2016/8/8.
 */
public interface IExpandable<T> {
    boolean isExpanded();
    void setExpanded(boolean expanded);
    List<T> getSubItems();

    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    int getLevel();
}
