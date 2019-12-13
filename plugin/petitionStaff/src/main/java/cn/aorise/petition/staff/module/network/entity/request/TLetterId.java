package cn.aorise.petition.staff.module.network.entity.request;

/**
 * Created by Administrator on 2017/6/1.
 */

public class TLetterId {
    private String letterID;

    @Override
    public String toString() {
        return "TLetterId{" +
                "letterID='" + letterID + '\'' +
                '}';
    }

    public String getLetterID() {
        return letterID;
    }

    public void setLetterID(String letterID) {
        this.letterID = letterID;
    }
}
