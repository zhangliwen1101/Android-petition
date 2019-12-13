package cn.aorise.petition.module.network.entity.request;

/**
 * Created by Administrator on 2017/5/11.
 */

public class TQueryEvaluate {
    private int currentPage;
    private int pageSize;
    private int ZT;
    private String ZJHM;
    private String XFBH;

    @Override
    public String toString() {
        return "TQueryEvaluate{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", ZT=" + ZT +
                ", ZJHM='" + ZJHM + '\'' +
                ", XFBH='" + XFBH + '\'' +
                '}';
    }

    public String getXFBH() {

        return XFBH;
    }

    public void setXFBH(String XFBH) {
        this.XFBH = XFBH;
    }

    public String getZJHM() {
        return ZJHM;
    }

    public void setZJHM(String ZJHM) {
        this.ZJHM = ZJHM;
    }

    public int getZT() {
        return ZT;
    }

    public void setZT(int ZT) {
        this.ZT = ZT;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
