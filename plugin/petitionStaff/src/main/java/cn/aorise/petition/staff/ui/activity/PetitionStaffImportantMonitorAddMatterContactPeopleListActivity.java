package cn.aorise.petition.staff.ui.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityRequestContactListBinding;
import cn.aorise.petition.staff.ui.adapter.BundingContactPeopleAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import cn.aorise.petition.staff.ui.bean.Petition_contact_people;


/**
 * Created by Administrator on 2017/4/25.
 */

public class PetitionStaffImportantMonitorAddMatterContactPeopleListActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityRequestContactListBinding mbinding;
    private BundingContactPeopleAdapter madapter;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {
        sp=getSharedPreferences(getString(R.string.petition_staff_short_time_sp),MODE_PRIVATE);
        editor=sp.edit();


    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mbinding= DataBindingUtil.setContentView(this,R.layout.petition_staff_activity_request_contact_list);
        setPetition_title(this, getString(R.string.petition_staff_contact_list_name), getString(R.string.petition_staff_add1), PetitionStaffImportantMonitorAddMatterContactPeopleAddActivity.class);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }


    private void getData(){
        if (!sp.getString(getString(R.string.petition_staff_shareprefers_save_list),"").equals("")) {
            final List<Petition_contact_people> data = GsonUtil.fromJson(sp.getString(getString(
                    R.string.petition_staff_shareprefers_save_list), ""), new TypeToken<List<Petition_contact_people>>(){}.getType());
            System.out.println(data);
            madapter=new BundingContactPeopleAdapter(data,this);
            madapter.notifyDataSetChanged();
            mbinding.listView.setAdapter(madapter);
            mbinding.refresh.setEnabled(false);
            mbinding.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        data.remove(position);
                        madapter.notifyDataSetChanged();
                        editor.putString(getString(
                                R.string.petition_staff_shareprefers_save_list),GsonUtil.toJson(data));
                        editor.commit();

                    return false;
                }
            });

        }
    }
}
