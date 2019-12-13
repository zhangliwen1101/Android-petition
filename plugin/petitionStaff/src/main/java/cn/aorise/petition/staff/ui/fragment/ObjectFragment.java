package cn.aorise.petition.staff.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffHomeFragmentBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffObjectFragmentBinding;
import cn.aorise.petition.staff.ui.activity.PetitionStaffDisputeCheckActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffImportantPetitionMatterActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffImportantPetitionPeopleActivity;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseFragment;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ObjectFragment extends PetitionStaffBaseFragment implements View.OnClickListener {
    private PetitionStaffObjectFragmentBinding mBinding;
    public ObjectFragment() {

    }

    public static ObjectFragment newInstance() {
        ObjectFragment fragment = new ObjectFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_import_object==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffImportantPetitionPeopleActivity.class);
            startActivity(intent);
        } else if (R.id.rl_import_items==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffImportantPetitionMatterActivity.class);
            startActivity(intent);
        } else if (R.id.rl_inflict_check==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffDisputeCheckActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.petition_staff_object_fragment,container,false);
        mBinding.rlImportObject.setOnClickListener(this);
        mBinding.rlImportItems.setOnClickListener(this);
        mBinding.rlInflictCheck.setOnClickListener(this);
        return mBinding.getRoot();
    }
}