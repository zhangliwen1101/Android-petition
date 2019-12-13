package cn.aorise.petition.staff.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffAnalyzeFragmentBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffHomeFragmentBinding;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeBumenmanyilvActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeBumenmanyilvActivity01;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeBumenmanyilvActivity02;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeOnTimeActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeOrganActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeSatisfactionActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeShapeActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeTimelyActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeTypeActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeWangxinzhanbilvActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffAnalyzeZeRenDanWeiManYiLvActivity;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseFragment;

/**
 * Created by Administrator on 2017/5/25.
 */

public class AnalyzeFragment extends PetitionStaffBaseFragment implements View.OnClickListener{
    private PetitionStaffAnalyzeFragmentBinding mBinding;

    public AnalyzeFragment() {

    }
    public static AnalyzeFragment newInstance(){
        AnalyzeFragment fragment=new AnalyzeFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_01==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeShapeActivity.class);
            startActivity(intent);
        } else if (R.id.rl_02==id) {//五率统计
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeTimelyActivity.class);
            startActivity(intent);
        } else if (R.id.rl_03==id) {//信访部门及时受理率
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeOnTimeActivity.class);
            startActivity(intent);
        } else if (R.id.rl_zerendanweimanyilv==id) {//责任单位及时受理率
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeZeRenDanWeiManYiLvActivity.class);
            startActivity(intent);
        }else if (R.id.rl_04==id) { //按期办结率
            // TODO: 2017/10/26 群众参评率
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeSatisfactionActivity.class);
            startActivity(intent);
        } else if (R.id.rl_05==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeTypeActivity.class);
            startActivity(intent);
        } else if (R.id.rl_06==id) {
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeOrganActivity.class);
            startActivity(intent);
        } else if (R.id.rl_wangxinzhanbilv==id) { //信访部门满意率
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeWangxinzhanbilvActivity.class);
            startActivity(intent);
        } else if (R.id.rl_bumenmanyilv==id) {//责任单位满意率
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeBumenmanyilvActivity.class);
            startActivity(intent);
        } else if (R.id.rl_10==id) {//参评率
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeBumenmanyilvActivity01.class);
            startActivity(intent);
        } else if (R.id.rl_11 == id) {//网信占比率
            Intent intent=new Intent(getActivity(), PetitionStaffAnalyzeBumenmanyilvActivity02.class);
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
        mBinding= DataBindingUtil.inflate(inflater, R.layout.petition_staff_analyze_fragment,container,false);
        mBinding.rl01.setOnClickListener(this);
        mBinding.rl02.setOnClickListener(this);
        mBinding.rl03.setOnClickListener(this);
        mBinding.rl04.setOnClickListener(this);
        mBinding.rl05.setOnClickListener(this);
        mBinding.rl06.setOnClickListener(this);
        mBinding.rlZerendanweimanyilv.setOnClickListener(this);
        mBinding.rlWangxinzhanbilv.setOnClickListener(this);
        mBinding.rlBumenmanyilv.setOnClickListener(this);
        mBinding.rl10.setOnClickListener(this);
        mBinding.rl11.setOnClickListener(this);
        return mBinding.getRoot();
    }
}
