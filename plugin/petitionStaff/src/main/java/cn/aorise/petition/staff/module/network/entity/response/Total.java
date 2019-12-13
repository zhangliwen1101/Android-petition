package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/15.
 */

public class Total {

    private Float village;
    private Float land;
    private Float cityBuild;
    private Float labourAndSocial;
    private Float sanitation;
    private Float education;
    private Float civilAdministration;
    private Float politicsAndLaw;
    private Float economy;
    private Float traffic;
    private Float tourismAndBusiness;
    private Float science;
    private Float environment;
    private Float partyAffairs;
    private Float organization;
    private Float disciplineInspection;

    @Override
    public String toString() {
        return "Total{" +
                "village='" + village + '\'' +
                ", land='" + land + '\'' +
                ", cityBuild='" + cityBuild + '\'' +
                ", labourAndSocial='" + labourAndSocial + '\'' +
                ", sanitation='" + sanitation + '\'' +
                ", education='" + education + '\'' +
                ", civilAdministration='" + civilAdministration + '\'' +
                ", politicsAndLaw='" + politicsAndLaw + '\'' +
                ", economy='" + economy + '\'' +
                ", traffic='" + traffic + '\'' +
                ", tourismAndBusiness='" + tourismAndBusiness + '\'' +
                ", science='" + science + '\'' +
                ", environment='" + environment + '\'' +
                ", partyAffairs='" + partyAffairs + '\'' +
                ", organization='" + organization + '\'' +
                ", disciplineInspection='" + disciplineInspection + '\'' +
                '}';
    }

    public Float getVillage() {
        return village;
    }

    public void setVillage(Float village) {
        this.village = village;
    }

    public Float getLand() {
        return land;
    }

    public void setLand(Float land) {
        this.land = land;
    }

    public Float getCityBuild() {
        return cityBuild;
    }

    public void setCityBuild(Float cityBuild) {
        this.cityBuild = cityBuild;
    }

    public Float getLabourAndSocial() {
        return labourAndSocial;
    }

    public void setLabourAndSocial(Float labourAndSocial) {
        this.labourAndSocial = labourAndSocial;
    }

    public Float getSanitation() {
        return sanitation;
    }

    public void setSanitation(Float sanitation) {
        this.sanitation = sanitation;
    }

    public Float getEducation() {
        return education;
    }

    public void setEducation(Float education) {
        this.education = education;
    }

    public Float getCivilAdministration() {
        return civilAdministration;
    }

    public void setCivilAdministration(Float civilAdministration) {
        this.civilAdministration = civilAdministration;
    }

    public Float getPoliticsAndLaw() {
        return politicsAndLaw;
    }

    public void setPoliticsAndLaw(Float politicsAndLaw) {
        this.politicsAndLaw = politicsAndLaw;
    }

    public Float getEconomy() {
        return economy;
    }

    public void setEconomy(Float economy) {
        this.economy = economy;
    }

    public Float getTraffic() {
        return traffic;
    }

    public void setTraffic(Float traffic) {
        this.traffic = traffic;
    }

    public Float getTourismAndBusiness() {
        return tourismAndBusiness;
    }

    public void setTourismAndBusiness(Float tourismAndBusiness) {
        this.tourismAndBusiness = tourismAndBusiness;
    }

    public Float getScience() {
        return science;
    }

    public void setScience(Float science) {
        this.science = science;
    }

    public Float getEnvironment() {
        return environment;
    }

    public void setEnvironment(Float environment) {
        this.environment = environment;
    }

    public Float getPartyAffairs() {
        return partyAffairs;
    }

    public void setPartyAffairs(Float partyAffairs) {
        this.partyAffairs = partyAffairs;
    }

    public Float getOrganization() {
        return organization;
    }

    public void setOrganization(Float organization) {
        this.organization = organization;
    }

    public Float getDisciplineInspection() {
        return disciplineInspection;
    }

    public void setDisciplineInspection(Float disciplineInspection) {
        this.disciplineInspection = disciplineInspection;
    }
}
