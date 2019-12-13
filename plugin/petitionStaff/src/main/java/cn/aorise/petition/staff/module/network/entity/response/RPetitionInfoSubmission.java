package cn.aorise.petition.staff.module.network.entity.response;

/**
 * Created by Administrator on 2017/6/6.
 */

public class RPetitionInfoSubmission {
    private String WeChat;
    private String Letter;
    private String Visit;
    private String TEL;
    private String APP;
    private String mialbox;
    private String Aceepte;
    private String Transaction;
    private String Check;
    private String Near;
    private String Overdue;
    private String internet;

    @Override
    public String toString() {
        return "RPetitionInfoSubmission{" +
                "WeChat='" + WeChat + '\'' +
                ", Letter='" + Letter + '\'' +
                ", Visit='" + Visit + '\'' +
                ", TEL='" + TEL + '\'' +
                ", APP='" + APP + '\'' +
                ", mialbox='" + mialbox + '\'' +
                ", Aceepte='" + Aceepte + '\'' +
                ", Transaction='" + Transaction + '\'' +
                ", Check='" + Check + '\'' +
                ", Near='" + Near + '\'' +
                ", Overdue='" + Overdue + '\'' +
                ", internet='" + internet + '\'' +
                '}';
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String getWeChat() {
        return WeChat;
    }

    public void setWeChat(String weChat) {
        WeChat = weChat;
    }

    public String getLetter() {
        return Letter;
    }

    public void setLetter(String letter) {
        Letter = letter;
    }

    public String getVisit() {
        return Visit;
    }

    public void setVisit(String visit) {
        Visit = visit;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getAPP() {
        return APP;
    }

    public void setAPP(String APP) {
        this.APP = APP;
    }

    public String getMialbox() {
        return mialbox;
    }

    public void setMialbox(String mialbox) {
        this.mialbox = mialbox;
    }

    public String getAceepte() {
        return Aceepte;
    }

    public void setAceepte(String aceepte) {
        Aceepte = aceepte;
    }

    public String getTransaction() {
        return Transaction;
    }

    public void setTransaction(String transaction) {
        Transaction = transaction;
    }

    public String getCheck() {
        return Check;
    }

    public void setCheck(String check) {
        Check = check;
    }

    public String getNear() {
        return Near;
    }

    public void setNear(String near) {
        Near = near;
    }

    public String getOverdue() {
        return Overdue;
    }

    public void setOverdue(String overdue) {
        Overdue = overdue;
    }
}
