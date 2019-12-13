package cn.aorise.petition.staff.module.network.entity.response;

import java.util.List;

import cn.aorise.petition.staff.ui.bean.StaffInfo;

/**
 * Created by Administrator on 2017/5/27.
 */

public class RStaffInfo {
    private String ZZJG;
    private List<StaffInfo> workpeopleList;

    @Override
    public String toString() {
        return "RStaffInfo{" +
                "ZZJG='" + ZZJG + '\'' +
                ", workpeopleList=" + workpeopleList +
                '}';
    }

    public String getZZJG() {
        return ZZJG;
    }

    public void setZZJG(String ZZJG) {
        this.ZZJG = ZZJG;
    }

    public List<StaffInfo> getWorkpeopleList() {
        return workpeopleList;
    }

    public void setWorkpeopleList(List<StaffInfo> workpeopleList) {
        this.workpeopleList = workpeopleList;
    }
}
