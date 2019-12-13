package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/16.
 */

public class TWorkWarningListt {
    private int currentPage;
    private int pageSize;
    private String LY;
    private String ZZJG;
    private String ZT;
    private String condition;

    @Override
    public String toString() {
        return "TWorkWarningListt{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", LY='" + LY + '\'' +
                ", ZZJG='" + ZZJG + '\'' +
                ", ZT='" + ZT + '\'' +
                ", condition='" + condition + '\'' +
                '}';
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

    public String getLY() {
        return LY;
    }

    public void setLY(String LY) {
        this.LY = LY;
    }

    public String getZZJG() {
        return ZZJG;
    }

    public void setZZJG(String ZZJG) {
        this.ZZJG = ZZJG;
    }

    public String getZT() {
        return ZT;
    }

    public void setZT(String ZT) {
        this.ZT = ZT;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
