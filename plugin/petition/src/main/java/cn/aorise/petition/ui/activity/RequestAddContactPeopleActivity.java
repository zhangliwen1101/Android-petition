package cn.aorise.petition.ui.activity;

import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.PetitionApplication;
import cn.aorise.petition.R;
import cn.aorise.petition.common.IsIDCard;
import cn.aorise.petition.databinding.PetitionActivityAddContactPeopleBinding;
import cn.aorise.petition.ui.base.PetitionBaseActivity;
import cn.aorise.petition.ui.bean.Petition_contact_people;

/**
 * Created by Administrator on 2017/4/25.
 */

public class RequestAddContactPeopleActivity extends PetitionBaseActivity implements View.OnClickListener {
    private PetitionActivityAddContactPeopleBinding mbinding;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_submit==id) {
            SaveContactPeople();
        } else if (R.id.rl_address==id) {
            openActivity(RequestAddContactPeopleAddress.class);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mbinding= DataBindingUtil.setContentView(this,R.layout.petition_activity_add_contact_people);
        sp=getSharedPreferences(getString(R.string.petition_sharepre_nomal),MODE_PRIVATE);
        editor=sp.edit();
        mbinding.rlSubmit.setOnClickListener(this);
        mbinding.rlAddress.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        setview();
    }

    private void setview(){
        /*mbinding.edtName.setText(sp.getString(getString(R.string.petition_shareprefers_people_name),""));
        mbinding.edtIdNumber.setText(sp.getString(getString(R.string.petition_shareprefers_people_id_card_number),""));
        mbinding.edtDetailAddress.setText(sp.getString(getString(R.string.petition_shareprefers_people_detail_address),""));*/
        mbinding.txtAddress.setText(sp.getString(getString(R.string.petition_shareprefers_contact_people_SH),"")
        +sp.getString(getString(R.string.petition_shareprefers_contact_people_S),"")
        +sp.getString(getString(R.string.petition_shareprefers_contact_people_X),""));
    }
    private void SaveContactPeople(){
        if (!mbinding.edtName.getText().toString().trim().equals("")&&
                !mbinding.edtIdNumber.getText().toString().trim().equals("")&&
                !mbinding.txtAddress.getText().toString().trim().equals("")&&
                !mbinding.edtDetailAddress.getText().toString().trim().equals("")&&
                IsIDCard.IDCardValidate(mbinding.edtIdNumber.getText().toString()).equals("")) {
            Petition_contact_people people = new Petition_contact_people();
            people.setXM(mbinding.edtName.getText().toString());
            people.setZJHM(mbinding.edtIdNumber.getText().toString());
            people.setDZ(/*mbinding.txtAddress.getText().toString() + */mbinding.edtDetailAddress.
                    getText().toString());
            people.setQY_SH(sp.getString(getString(R.string.petition_shareprefers_contact_people_SH),""));
            people.setQY_SHDM(sp.getString(getString(R.string.petition_shareprefers_contact_people_SHDM),""));
            people.setQY_S(sp.getString(getString(R.string.petition_shareprefers_contact_people_S),""));
            people.setQY_SDM(sp.getString(getString(R.string.petition_shareprefers_contact_people_SDM),""));
            people.setQY_X(sp.getString(getString(R.string.petition_shareprefers_contact_people_X),""));
            people.setQY_XDM(sp.getString(getString(R.string.petition_sharepre_SFD_XDM),""));
            if ("".equals(sp.getString(getString(R.string.petition_shareprefers_save_list), ""))) {
                List<Petition_contact_people> list = new ArrayList<>();
                list.add(people);
                String strJson = GsonUtil.toJson(list);
                System.out.println(strJson);
                editor.putString(getString(R.string.petition_shareprefers_save_list), strJson);
                editor.commit();
            } else {
                List<Petition_contact_people> list = GsonUtil.fromJson(sp.getString(getString(R.string.petition_shareprefers_save_list), ""),
                        new TypeToken<List<Petition_contact_people>>() {
                        }.getType());
                list.add(people);
                String strJson = GsonUtil.toJson(list);
                System.out.println(strJson);
                editor.putString(getString(R.string.petition_shareprefers_save_list), strJson);
                editor.commit();
            }
            this.finish();
        } else {
            showToast(R.string.petition_toast_error_03);
        }
    }
}
