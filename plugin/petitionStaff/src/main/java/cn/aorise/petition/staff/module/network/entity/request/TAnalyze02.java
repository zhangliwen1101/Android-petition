package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by 李世林 on 2017/10/25.
 */

public class TAnalyze02 {
    private String dateStart;
    private String dateEnd;
    private String danwei;

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    @Override
    public String toString() {
        return "TAnalyze02{" +
                "dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", danwei='" + danwei + '\'' +
                '}';
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }
}
