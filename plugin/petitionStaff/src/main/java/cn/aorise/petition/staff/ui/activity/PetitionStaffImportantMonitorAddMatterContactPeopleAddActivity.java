package cn.aorise.petition.staff.ui.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityAddContactPeopleBinding;
import cn.aorise.petition.staff.module.network.entity.request.addimportantmatter.ContactPeople;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import cn.aorise.petition.staff.ui.bean.Petition_contact_people;


/**
 * Created by Administrator on 2017/4/25.
 */

public class PetitionStaffImportantMonitorAddMatterContactPeopleAddActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityAddContactPeopleBinding mbinding;
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
            openActivity(PetitionStaffImportantMonitorAddMatterAddressActivity.class);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mbinding= DataBindingUtil.setContentView(this,R.layout.petition_staff_activity_add_contact_people);
        sp=getSharedPreferences(getString(R.string.petition_staff_short_time_sp),MODE_PRIVATE);
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
        if (!sp.getString(getString(R.string.petition_staff_contact_QY_SH),"").equals("")){
            mbinding.txtAddress.setText(sp.getString(getString(R.string.petition_staff_contact_QY_SH),"")+
                    sp.getString(getString(R.string.petition_staff_contact_QY_S),"")+
                    sp.getString(getString(R.string.petition_staff_contact_QY_X),""));
        }
    }
    private void SaveContactPeople(){
        if (!mbinding.edtName.getText().toString().trim().equals("")&&
                !mbinding.edtIdNumber.getText().toString().trim().equals("")&&
                !mbinding.txtAddress.getText().toString().trim().equals("--")&&
                !mbinding.edtDetailAddress.getText().toString().trim().equals("")) {
            Petition_contact_people people = new Petition_contact_people();
            people.setName(mbinding.edtName.getText().toString());
            people.setNum(mbinding.edtIdNumber.getText().toString());
            people.setAddress(mbinding.txtAddress.getText().toString() + mbinding.edtDetailAddress.
                    getText().toString());

            if ("".equals(sp.getString(getString(R.string.petition_staff_shareprefers_save_list), ""))) {
                List<Petition_contact_people> list = new ArrayList<>();
                list.add(people);
                String strJson = GsonUtil.toJson(list);
                System.out.println(strJson);
                editor.putString(getString(R.string.petition_staff_shareprefers_save_list), strJson);
                editor.commit();
            } else {
                List<Petition_contact_people> list = GsonUtil.fromJson(sp.getString(getString(R.string.petition_staff_shareprefers_save_list), ""),
                        new TypeToken<List<Petition_contact_people>>() {
                        }.getType());
                list.add(people);
                String strJson = GsonUtil.toJson(list);
                System.out.println(strJson);
                editor.putString(getString(R.string.petition_staff_shareprefers_save_list), strJson);
                editor.commit();
            }
            /*保存联名人信息*/
            ContactPeople contactPeople=new ContactPeople();

            contactPeople.setZJHM(mbinding.edtIdNumber.getText().toString());
            contactPeople.setXM(mbinding.edtName.getText().toString());
            contactPeople.setQY_SH(sp.getString(getString(R.string.petition_staff_contact_QY_SH),""));
            contactPeople.setQY_SHDM(sp.getString(getString(R.string.petition_staff_contact_QY_SHDM),""));
            contactPeople.setQY_S(sp.getString(getString(R.string.petition_staff_contact_QY_SH),""));
            contactPeople.setQY_SDM(sp.getString(getString(R.string.petition_staff_contact_QY_SDM),""));
            contactPeople.setQY_X(sp.getString(getString(R.string.petition_staff_contact_QY_X),""));
            contactPeople.setQY_XDM(sp.getString(getString(R.string.petition_staff_contact_QY_XDM),""));
            contactPeople.setDZ(mbinding.txtAddress.getText().toString() + mbinding.edtDetailAddress.
                    getText().toString());
            contactPeople.setDQ(mbinding.txtAddress.getText().toString());
            contactPeople.setXXDZ(mbinding.edtDetailAddress.
                    getText().toString());

            if ("".equals(sp.getString(getString(R.string.petition_staff_sp_contact_people), ""))) {
                List<ContactPeople> list = new ArrayList<>();
                list.add(contactPeople);
                String strJson = GsonUtil.toJson(list);
                System.out.println(strJson);
                editor.putString(getString(R.string.petition_staff_sp_contact_people), strJson);
                editor.commit();
            } else {
                List<ContactPeople> list = GsonUtil.fromJson(sp.getString(getString(R.string.petition_staff_sp_contact_people), ""),
                        new TypeToken<List<ContactPeople>>() {
                        }.getType());
                list.add(contactPeople);
                String strJson = GsonUtil.toJson(list);
                System.out.println(strJson);
                editor.putString(getString(R.string.petition_staff_sp_contact_people), strJson);
                editor.commit();
            }
            this.finish();
        } else {
            showToast(R.string.petition_staff_toast_error_02);
        }
    }
}
