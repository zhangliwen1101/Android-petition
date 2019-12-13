package cn.aorise.petition.module.network.entity.response;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class RAllAddress1 {
    private String MC;
    private String DM;
    private String FJDM;
    private List<RAllAddress2> children;

    @Override
    public String toString() {
        return "RAllAddress1{" +
                "MC='" + MC + '\'' +
                ", DM='" + DM + '\'' +
                ", FJDM='" + FJDM + '\'' +
                ", children=" + children +
                '}';
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }

    public String getFJDM() {
        return FJDM;
    }

    public void setFJDM(String FJDM) {
        this.FJDM = FJDM;
    }

    public List<RAllAddress2> getChildren() {
        return children;
    }

    public void setChildren(List<RAllAddress2> children) {
        this.children = children;
    }
}
