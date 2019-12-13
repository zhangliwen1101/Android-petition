package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/15.
 */

public class TImportantMonitor {
    private int currentPage;
    private int pageSize;
    private String currentUser;


    @Override
    public String toString() {
        return "TImportantMonitor{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", currentUser='" + currentUser + '\'' +
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

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
