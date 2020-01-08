package me.zhouzhuo810.magpiex.ui.widget.scroll;


import androidx.recyclerview.widget.RecyclerView;

public abstract class OnVerticalScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop();
        } else if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom();
        }
        if (dy < 0) {
            onScrolledUp(dy);
        } else if (dy > 0) {
            onScrolledDown(dy);
        }
        onScrolled(dy);
    }

    protected abstract void onScrolledToTop();

    protected abstract void onScrolledToBottom();

    public void onScrolled(int dy) {
    }

    public void onScrolledUp(int dy) {
    }

    public void onScrolledDown(int dy) {
    }
}
