package cn.aorise.petition.staff.module.network.entity.request.addimportantmatter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */

public class TaddImportantMatter {
    private importantMonitor ImportantMonitor;
    private importantPeople importantPeople;
    private List<ContactPeople> PetitionEntity;
    private String CJR;
    private String CJRID;
    private String PT;
    private String BB;

    @Override
    public String toString() {
        return "TaddImportantMatter{" +
                "ImportantMonitor=" + ImportantMonitor +
                ", importantPeople=" + importantPeople +
                ", PetitionEntity=" + PetitionEntity +
                ", CJR='" + CJR + '\'' +
                ", CJRID='" + CJRID + '\'' +
                ", PT='" + PT + '\'' +
                ", BB='" + BB + '\'' +
                '}';
    }

    public importantMonitor getImportantMonitor() {
        return ImportantMonitor;
    }

    public void setImportantMonitor(importantMonitor importantMonitor) {
        ImportantMonitor = importantMonitor;
    }

    public cn.aorise.petition.staff.module.network.entity.request.addimportantmatter.importantPeople getImportantPeople() {
        return importantPeople;
    }

    public void setImportantPeople(cn.aorise.petition.staff.module.network.entity.request.addimportantmatter.importantPeople importantPeople) {
        this.importantPeople = importantPeople;
    }

    public List<ContactPeople> getPetitionEntity() {
        return PetitionEntity;
    }

    public void setPetitionEntity(List<ContactPeople> petitionEntity) {
        PetitionEntity = petitionEntity;
    }

    public String getCJR() {
        return CJR;
    }

    public void setCJR(String CJR) {
        this.CJR = CJR;
    }

    public String getCJRID() {
        return CJRID;
    }

    public void setCJRID(String CJRID) {
        this.CJRID = CJRID;
    }

    public String getPT() {
        return PT;
    }

    public void setPT(String PT) {
        this.PT = PT;
    }

    public String getBB() {
        return BB;
    }

    public void setBB(String BB) {
        this.BB = BB;
    }
}
