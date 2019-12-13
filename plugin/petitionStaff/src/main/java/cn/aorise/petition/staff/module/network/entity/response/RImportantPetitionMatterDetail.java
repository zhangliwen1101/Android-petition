package cn.aorise.petition.staff.module.network.entity.response;

import java.util.List;

import cn.aorise.petition.staff.ui.bean.ComplainEntity;
import cn.aorise.petition.staff.ui.bean.PeopleInfo;
import cn.aorise.petition.staff.ui.bean.PeopleInfo1;
import cn.aorise.petition.staff.ui.bean.PetitionEntity;

/**
 * Created by Administrator on 2017/6/1.
 */

public class RImportantPetitionMatterDetail {
    private ComplainEntity complainEntity;
    private PetitionEntity petitionEntity;
    private List<PeopleInfo1> peopleAll;

    @Override
    public String toString() {
        return "RImportantPetitionMatterDetail{" +
                "complainEntity=" + complainEntity +
                ", petitionEntity=" + petitionEntity +
                ", peopleAll=" + peopleAll +
                '}';
    }

    public ComplainEntity getComplainEntity() {
        return complainEntity;
    }

    public void setComplainEntity(ComplainEntity complainEntity) {
        this.complainEntity = complainEntity;
    }

    public PetitionEntity getPetitionEntity() {
        return petitionEntity;
    }

    public void setPetitionEntity(PetitionEntity petitionEntity) {
        this.petitionEntity = petitionEntity;
    }

    public List<PeopleInfo1> getPeopleAll() {
        return peopleAll;
    }

    public void setPeopleAll(List<PeopleInfo1> peopleAll) {
        this.peopleAll = peopleAll;
    }
}
