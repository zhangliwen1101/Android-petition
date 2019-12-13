package cn.aorise.petition.staff.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffHomeFragmentBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffPersonalFragmentBinding;
import cn.aorise.petition.staff.ui.activity.PetitionStaffChangeBundingPhoneActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffChangePasswordActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffChangePersonalInfoActivity;

import cn.aorise.petition.staff.ui.activity.PetitionStaffImportantMonitorActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffLoginActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffPetitionInfoSubmission;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseFragment;

/**
 * Created by Administrator on 2017/5/25.
 */

public class PersonalFragment extends PetitionStaffBaseFragment implements View.OnClickListener {
    private PetitionStaffPersonalFragmentBinding mBinding;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public PersonalFragment() {

    }

    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_edit_personal_info==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffChangePersonalInfoActivity.class);
            startActivity(intent);
        } else if (R.id.rl_change_phonenum==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffChangeBundingPhoneActivity.class);
            startActivity(intent);
        } else if (R.id.rl_change_password==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffChangePasswordActivity.class);
            startActivity(intent);
        } else if (R.id.rl_import_submission==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffImportantMonitorActivity.class);
            startActivity(intent);
        } else if (R.id.rl_petition_submission01==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffPetitionInfoSubmission.class);
            startActivity(intent);
        } else if (R.id.rl_exit_login==id) {
            sp=getActivity().getSharedPreferences(getString(R.string.petition_staff_sp_info), Context.MODE_PRIVATE);
            editor=sp.edit();
            editor.clear().commit();
            Toast.makeText(getActivity(), getString(R.string.petition_staff_login_out), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(), PetitionStaffLoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.petition_staff_personal_fragment,container,false);
        mBinding.rlEditPersonalInfo.setOnClickListener(this);
        mBinding.rlChangePhonenum.setOnClickListener(this);
        mBinding.rlChangePassword.setOnClickListener(this);
        mBinding.rlImportSubmission.setOnClickListener(this);
        mBinding.rlPetitionSubmission01.setOnClickListener(this);
        mBinding.rlExitLogin.setOnClickListener(this);
        return mBinding.getRoot();
    }
}