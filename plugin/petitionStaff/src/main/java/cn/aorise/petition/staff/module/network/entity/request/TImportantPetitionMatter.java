package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/1.
 */

public class TImportantPetitionMatter {
    private int currentPage;
    private int pageSize;
    private String whereStr;
    private String JB;
    private String LXY;

    @Override
    public String toString() {
        return "TImportantPetitionMatter{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", whereStr='" + whereStr + '\'' +
                ", JB='" + JB + '\'' +
                ", LXY='" + LXY + '\'' +
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

    public String getWhereStr() {
        return whereStr;
    }

    public void setWhereStr(String whereStr) {
        this.whereStr = whereStr;
    }

    public String getJB() {
        return JB;
    }

    public void setJB(String JB) {
        this.JB = JB;
    }

    public String getLXY() {
        return LXY;
    }

    public void setLXY(String LXY) {
        this.LXY = LXY;
    }
}
