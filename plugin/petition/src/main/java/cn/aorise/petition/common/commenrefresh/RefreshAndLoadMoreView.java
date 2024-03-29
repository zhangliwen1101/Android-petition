package cn.aorise.petition.common.commenrefresh;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.aorise.petition.R;

public class RefreshAndLoadMoreView extends SwipeRefreshLayout {
    private LoadMoreListView mLoadMoreListView;  
  
    /**  
     * 构造方法，用于在布局文件中用到这个自定义SwipeRefreshLayout控件  
     * @param context  
     * @param attrs  
     */  
    public RefreshAndLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);  
        Resources res = getResources();
        //通过颜色资源文件设置进度动画的颜色资源  
        setColorSchemeColors(res.getColor(R.color.refresh_color_1),
                res.getColor(R.color.refresh_color_2),  
                res.getColor(R.color.refresh_color_3),  
                res.getColor(R.color.refresh_color_4));  
    }  
    public void setLoadMoreListView(LoadMoreListView mLoadMoreListView) {  
        this.mLoadMoreListView = mLoadMoreListView;  
    }  
  
    /**  
     * 触屏事件,如果ListView不为空且数据还在加载中，则继续加载直至完成加载才触摸此事件  
     * @param ev  
     * @return  
     */  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLoadMoreListView != null && mLoadMoreListView.isLoading()) {  
            return false;  
        }  
        return super.onTouchEvent(ev);  
    }  
}  