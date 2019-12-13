package cn.aorise.petition.staff.module.network.entity.response;

import java.util.List;

/**
 * Created by 李世林 on 2017/10/26.
 */

public class RXinfangLeixing {


    private List<Integer> datas;
    private List<Integer> datas1;
    private List<String> text;
    private List<String> text1;

    @Override
    public String toString() {
        return "RXinfangLeixing{" +
                "datas=" + datas +
                ", datas1=" + datas1 +
                ", text=" + text +
                ", text1=" + text1 +
                '}';
    }

    public List<Integer> getDatas() {
        return datas;
    }

    public void setDatas(List<Integer> datas) {
        this.datas = datas;
    }

    public List<Integer> getDatas1() {
        return datas1;
    }

    public void setDatas1(List<Integer> datas1) {
        this.datas1 = datas1;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<String> getText1() {
        return text1;
    }

    public void setText1(List<String> text1) {
        this.text1 = text1;
    }
}
