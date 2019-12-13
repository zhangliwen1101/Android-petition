package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/5/27.
 */

public class TStaffInfo {
    private String whereStr;

    @Override
    public String toString() {
        return "TStaffInfo{" +
                "whereStr='" + whereStr + '\'' +
                '}';
    }

    public String getWhereStr() {
        return whereStr;
    }

    public void setWhereStr(String whereStr) {
        this.whereStr = whereStr;
    }
}
