package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by 李世林 on 2017/10/25.
 */

public class TAnalyze01 {
    private String dateStart;
    private String dateEnd;

    @Override
    public String toString() {
        return "TAnalyze01{" +
                "dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                '}';
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
