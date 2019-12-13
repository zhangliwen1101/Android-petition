package cn.aorise.petition.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/5.
 */

public class TPetitionInfo {
    private String GGLX;
    private int currentPage;
    private int pageSize;

    @Override
    public String toString() {
        return "TPetitionInfo{" +
                "GGLX='" + GGLX + '\'' +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }

    public String getGGLX() {
        return GGLX;
    }

    public void setGGLX(String GGLX) {
        this.GGLX = GGLX;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
