package cn.aorise.petition.staff.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.List;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.utils.assist.AoriseUtil;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.FileUtils;
import cn.aorise.petition.staff.databinding.PetitionStaffStaffDetailActivityBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TDhhm;
import cn.aorise.petition.staff.module.network.entity.request.TStaffInfo;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfo;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfoDetail;
import cn.aorise.petition.staff.ui.adapter.StaffInfoAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/31.
 */

public class PetitionStaffstaffDetailInfoActivity extends PetitionStaffBaseActivity implements View.OnClickListener{
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private PetitionStaffStaffDetailActivityBinding mBinding;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(PetitionStaffstaffDetailInfoActivity.this,R.layout.petition_staff_staff_detail_activity);
        GetStaffInfoDetail();
        mBinding.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new AlertDialog.Builder(PetitionStaffstaffDetailInfoActivity.this).setTitle("拨打电话？")//设置对话框标题
                                    /*.setMessage("")//设置显示的内容*/
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮

                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub
                                if (mBinding.txtDhhm.length() != 0) {
                                    if (ContextCompat.checkSelfPermission(PetitionStaffstaffDetailInfoActivity.this,
                                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        // 没有获得授权，申请授权
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(PetitionStaffstaffDetailInfoActivity.this,
                                                Manifest.permission.CALL_PHONE)) {
                                            // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                                            // 弹窗需要解释为何需要该权限，再次请求授权


                                            // 帮跳转到该应用的设置界面，让用户手动授权
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", PetitionStaffstaffDetailInfoActivity.this.getPackageName(), null);
                                            intent.setData(uri);
                                            PetitionStaffstaffDetailInfoActivity.this.startActivity(intent);
                                        } else {
                                            // 不需要解释为何需要该权限，直接请求授权
                                            ActivityCompat.requestPermissions(PetitionStaffstaffDetailInfoActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE},
                                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                        }
                                    } else {
                                        // 已经获得授权，可以打电话
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mBinding.txtDhhm.getText().toString()));
                                        PetitionStaffstaffDetailInfoActivity.this.startActivity(intent);
                                    }
                                }

                            }

                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮

                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub

                    }

                }).show();//在按键响应事件中显示此对话框



                }
        });
    }

    @Override
    protected void initEvent() {

    }

    private void GetStaffInfoDetail() {
        TDhhm tDhhm=new TDhhm();
        tDhhm.setDHHM(getIntent().getStringExtra("dhhm"));
        System.out.println(GsonUtil.toJson(tDhhm));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetStaffInfoDetail(GsonUtil.toJson(tDhhm))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RStaffInfoDetail>>() {}.getType(), new
                                APICallback<APIResult<RStaffInfoDetail>>() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<RStaffInfoDetail> rStaffInfoDetailAPIResult) {
                                if (rStaffInfoDetailAPIResult.getData().getZP()!=null) {
                                    AoriseUtil.loadImage(PetitionStaffstaffDetailInfoActivity.this, mBinding.img, cn.aorise.petition.staff.common.Utils.url+rStaffInfoDetailAPIResult.getData().getZP());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getXM()!=null){
                                    mBinding.txtName.setText(rStaffInfoDetailAPIResult.getData().getXM());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getDHHM()!=null){
                                    mBinding.txtDhhm.setText(rStaffInfoDetailAPIResult.getData().getDHHM());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getBH()!=null){
                                    mBinding.txtBh.setText(rStaffInfoDetailAPIResult.getData().getBH());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getBM()!=null) {
                                    mBinding.txtBm.setText(rStaffInfoDetailAPIResult.getData().getBM());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getZW()!=null){
                                    mBinding.txtZw.setText(rStaffInfoDetailAPIResult.getData().getZW());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getQY()!=null){
                                    mBinding.txtArea.setText(rStaffInfoDetailAPIResult.getData().getQY());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getXXDZ()!=null){
                                    mBinding.txtAddress.setText(rStaffInfoDetailAPIResult.getData().getXXDZ());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getZJLX()!=null){
                                    mBinding.txtZjlx.setText(rStaffInfoDetailAPIResult.getData().getZJLX());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getZJHM()!=null) {
                                    mBinding.txtZjhm.setText(rStaffInfoDetailAPIResult.getData().getZJHM());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getCSRQ()!=null){
                                    mBinding.txtCsrq.setText(rStaffInfoDetailAPIResult.getData().getCSRQ());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getXB()!=null) {
                                    mBinding.txtXb.setText(rStaffInfoDetailAPIResult.getData().getXB());
                                }
                                if (rStaffInfoDetailAPIResult.getData().getMZ()!=null){
                                    mBinding.txtMz.setText(rStaffInfoDetailAPIResult.getData().getMZ());
                                }

                            }

                            @Override
                            public void onMock(APIResult<RStaffInfoDetail> rStaffInfoDetailAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

}
