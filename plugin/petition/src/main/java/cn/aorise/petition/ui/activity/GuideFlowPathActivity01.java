package cn.aorise.petition.ui.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import cn.aorise.petition.R;
import cn.aorise.petition.databinding.PetitionGuide03Binding;
import cn.aorise.petition.ui.base.PetitionBaseActivity;

/**
 * Created by Administrator on 2017/5/24.
 */

public class GuideFlowPathActivity01 extends PetitionBaseActivity implements View.OnClickListener{
    private PetitionGuide03Binding mBinding;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_guide_03);
    }

    @Override
    protected void initEvent() {

    }
}
