package me.zhouzhuo810.magpiex.event;

/**
 * EventBus关闭所有Activity的事件
 */
public class CloseAllActEvent {

    private boolean defaultAnim;

    public CloseAllActEvent(boolean defaultAnim) {
        this.defaultAnim = defaultAnim;
    }

    public boolean isDefaultAnim() {
        return defaultAnim;
    }

    public void setDefaultAnim(boolean defaultAnim) {
        this.defaultAnim = defaultAnim;
    }
}
