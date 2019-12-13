package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/5/31.
 */

public class TImportantPeople {
    private int currentPage;
    private int pageSize;
    private String whereStr;

    @Override
    public String toString() {
        return "TImportantPeople{" +
                "currentPage='" + currentPage + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", whereStr='" + whereStr + '\'' +
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
}
