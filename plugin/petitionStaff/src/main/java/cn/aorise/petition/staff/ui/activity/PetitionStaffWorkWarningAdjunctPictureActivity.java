package cn.aorise.petition.staff.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.Utils;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityQueryEvaluateDetailAdjunctPictureBinding;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;


/**
 * Created by Administrator on 2017/5/23.
 */

public class PetitionStaffWorkWarningAdjunctPictureActivity extends PetitionStaffBaseActivity implements View.OnClickListener{
    private String picturePath;
    private PetitionStaffActivityQueryEvaluateDetailAdjunctPictureBinding mBinding;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_query_evaluate_detail_adjunct_picture);
        picturePath=getIntent().getStringExtra("path");
        System.out.println(picturePath);
        Glide.with(this).load(Utils.url+picturePath).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                WindowManager manager = (WindowManager) PetitionStaffWorkWarningAdjunctPictureActivity.this
                        .getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                int height = display.getWidth() * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = mBinding.imgDouble.getLayoutParams();
                para.height = height;
                para.width = display.getWidth();
                mBinding.imgDouble.setImageBitmap(resource);
            }
        });
    }

    @Override
    protected void initEvent() {

    }
}
