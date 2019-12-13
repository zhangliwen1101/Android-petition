package cn.aorise.petition.staff.ui.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityDisputeCheckDetailBinding;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;

/**
 * Created by Administrator on 2017/6/2.
 */

public class PetitionStaffDisputeCheckDetailActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityDisputeCheckDetailBinding mBinding;


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_dispute_check_detail);
        mBinding.txtJb.setText(getIntent().getStringExtra("jb"));
        mBinding.txtDz.setText(getIntent().getStringExtra("dz"));
        mBinding.txtLx.setText(getIntent().getStringExtra("lx"));
        mBinding.txtNr.setText(getIntent().getStringExtra("nr"));
        mBinding.txtSfd.setText(getIntent().getStringExtra("sfd"));
    }

    @Override
    protected void initEvent() {

    }
}
