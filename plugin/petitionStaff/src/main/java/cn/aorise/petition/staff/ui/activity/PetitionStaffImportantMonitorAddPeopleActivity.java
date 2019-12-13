package cn.aorise.petition.staff.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.popupwindow.FileUitlity;
import cn.aorise.petition.staff.common.popupwindow.PetitionStaffSelectHeadView;
import cn.aorise.petition.staff.common.popupwindow.Popuwindow_sex_choose;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityImportantMonitorAddpeopleBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TAddImportantPeople;
import cn.aorise.petition.staff.module.network.entity.request.TLXFJDM;
import cn.aorise.petition.staff.module.network.entity.response.RDMIDMC;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.module.network.entity.response.RUpLoadFile;
import cn.aorise.petition.staff.ui.adapter.ZJLXAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.aorise.petition.staff.ui.activity.PetitionStaffChangePersonalInfoActivity.verifyStoragePermissions;

/**
 * Created by Administrator on 2017/6/20.
 */

public class PetitionStaffImportantMonitorAddPeopleActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityImportantMonitorAddpeopleBinding mBinding;
    private Popuwindow_sex_choose popuwindow_sex_choose;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private PetitionStaffSelectHeadView select_headView;
    private String path;
    private static int REQUEST_CODE=100,ALL_PHOTO=200,RESULT_PHOTO=300;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private TAddImportantPeople tAddImportantPeople=new TAddImportantPeople();
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_date==id) {//出生日期选择
            final Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(PetitionStaffImportantMonitorAddPeopleActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    c.set(year, monthOfYear, dayOfMonth);
                    mBinding.txtCsrq.setText(DateFormat.format("yyy-MM-dd", c));
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if (R.id.btn_man==id) {//性别选择
            popuwindow_sex_choose.dismiss();
            backgroundAlpha(1f);
            mBinding.txtXb.setText(getString(R.string.petition_staff_man));
        } else if (R.id.btn_woman==id) {//性别选择
            popuwindow_sex_choose.dismiss();
            backgroundAlpha(1f);
            mBinding.txtXb.setText(getString(R.string.petition_staff_woman));
        }else if (R.id.rl_sex==id) {//性别relayout
            backgroundAlpha(0.3f);
            popuwindow_sex_choose=new Popuwindow_sex_choose(PetitionStaffImportantMonitorAddPeopleActivity.this,this);
            popuwindow_sex_choose.setOnDismissListener(new PetitionStaffImportantMonitorAddPeopleActivity.poponDismissListener());
            //显示窗口
            popuwindow_sex_choose.showAtLocation(this.findViewById(R.id.textView30), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        }else if (R.id.rl_nation==id) {
            openActivity(PetitionStaffChangePersonalInfoNationActivity.class);
        }else if (R.id.img==id) {
            backgroundAlpha(0.3f);
            setheadview();
        }else if (R.id.rl_petition_right==id){
            AddImportantPeople();
        }else if (R.id.rl_petition_return==id){
            PetitionStaffImportantMonitorAddPeopleActivity.this.finish();
        }else if (R.id.rl_card_type==id){
            openActivity(PetitionStaffChooseZJLXActivity.class);
        }else if (R.id.rl_dz==id){
            openActivity(PetitionStaffChangePersonalInfoAddress.class);
        }

    }

    @Override
    protected void initData() {
        sp=getSharedPreferences(getString(R.string.petition_staff_short_time_sp),MODE_PRIVATE);
        editor=sp.edit();
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this,R.layout.petition_staff_activity_important_monitor_addpeople);
        mBinding.rlDate.setOnClickListener(this);
        mBinding.rlSex.setOnClickListener(this);
        mBinding.rlNation.setOnClickListener(this);
        mBinding.img.setOnClickListener(this);
        mBinding.rlPetitionRight.setOnClickListener(this);
        mBinding.rlPetitionReturn.setOnClickListener(this);
        mBinding.rlCardType.setOnClickListener(this);
        mBinding.rlDz.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!sp.getString(getString(R.string.petition_staff_sp_MZ),"").equals("")){
            mBinding.txtMz.setText(sp.getString(getString(R.string.petition_staff_sp_MZ),""));
        }
        if (!sp.getString(getString(R.string.petition_staff_sp_ZJLX),"").equals("")){
            mBinding.txtZjlx.setText(sp.getString(getString(R.string.petition_staff_sp_ZJLX),""));
        }
        if (!sp.getString(getString(R.string.petition_staff_QY_SH),"").equals("")){
            mBinding.txtAddress.setText(sp.getString(getString(R.string.petition_staff_QY_SH),"")+
                    sp.getString(getString(R.string.petition_staff_QY_S),"")+
                    sp.getString(getString(R.string.petition_staff_QY_X),""));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.clear().commit();
    }

    @Override
    protected void initEvent() {

    }
    private void AddImportantPeople() {
        tAddImportantPeople.setZJLX(sp.getString(getString(R.string.petition_staff_sp_ZJLX),""));
        tAddImportantPeople.setZJLXID(sp.getString(getString(R.string.petition_staff_sp_ZJLXID),""));
        tAddImportantPeople.setZJHM(mBinding.txtZjhm.getText().toString());
        tAddImportantPeople.setXM(mBinding.txtName.getText().toString());
        tAddImportantPeople.setCSRQ(mBinding.txtCsrq.getText().toString());
        tAddImportantPeople.setXB(mBinding.txtXb.getText().toString());
        tAddImportantPeople.setMZ(sp.getString(getString(R.string.petition_staff_sp_MZ),""));
        tAddImportantPeople.setMZID(sp.getString(getString(R.string.petition_staff_sp_MZID),""));
        tAddImportantPeople.setQY_SH(sp.getString(getString(R.string.petition_staff_QY_SH),""));
        tAddImportantPeople.setQY_SHDM(sp.getString(getString(R.string.petition_staff_QY_SHDM),""));
        tAddImportantPeople.setQY_S(sp.getString(getString(R.string.petition_staff_QY_S),""));
        tAddImportantPeople.setQY_SDM(sp.getString(getString(R.string.petition_staff_QY_SDM),""));
        tAddImportantPeople.setQY_X(sp.getString(getString(R.string.petition_staff_QY_X),""));
        tAddImportantPeople.setQY_XDM(sp.getString(getString(R.string.petition_staff_QY_XDM),""));
        tAddImportantPeople.setXXDZ(mBinding.txtDetailAddress.getText().toString());
        tAddImportantPeople.setDHHM(mBinding.txtDhhm.getText().toString());
        tAddImportantPeople.setCYZK(mBinding.txtCyzk.getText().toString());
        tAddImportantPeople.setZZMM(mBinding.txtZzmm.getText().toString());
        tAddImportantPeople.setCJR(getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE).getString(getString(R.string.petition_staff_sp_XM),""));
        tAddImportantPeople.setCJRID(getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE).getString(getString(R.string.petition_staff_sp_ID),""));
        tAddImportantPeople.setPT("android");
        tAddImportantPeople.setBB(getVersion());

        System.out.println(GsonUtil.toJson(tAddImportantPeople));
        Subscription subscription = PetitionStaffApiService.Factory.create().AddImportantPeople(GsonUtil.toJson(tAddImportantPeople))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cn.aorise.petition.staff.common.Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<REmptyEntity>>() {
                        }.getType(), new APICallback<APIResult<REmptyEntity>>() {
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
                            public void onNext(APIResult<REmptyEntity> rEmptyEntityAPIResult) {
                                showToast(rEmptyEntityAPIResult.getMsg());
                                if (rEmptyEntityAPIResult.getMsg().equals(getString(R.string.petition_staff_add_succeed))){
                                    PetitionStaffImportantMonitorAddPeopleActivity.this.finish();
                                }
                            }

                            @Override
                            public void onMock(APIResult<REmptyEntity> rEmptyEntityAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    private void setheadview() {
        //实例化SelectPicPopupWindow
        select_headView = new PetitionStaffSelectHeadView(this,listener);
        select_headView.setOnDismissListener(new PetitionStaffImportantMonitorAddPeopleActivity.poponDismissListener());
        //显示窗口
        select_headView.showAtLocation(this.findViewById(R.id.textView30), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.btn_take_photo) {
                select_headView.dismiss();
                backgroundAlpha(1f);
                //调用手机照相机的方法,通过Intent调用系统相机完成拍照，并调用系统裁剪器裁剪照片
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //创建文件路径,头像保存的路径
                File file = FileUitlity.getInstance(PetitionStaffImportantMonitorAddPeopleActivity.this).makeDir("head_image");
                //定义图片路径和名称

                path = file.getParent() + File.separatorChar + System.currentTimeMillis() + ".jpg";
                //保存图片到Intent中，并通过Intent将照片传给系统裁剪器
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                //图片质量
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                //启动有返回的Intent，即返回裁剪好的图片到RoundImageView组件中显示
                //动态获取拍照权限
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(PetitionStaffImportantMonitorAddPeopleActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PetitionStaffImportantMonitorAddPeopleActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
                        return;
                    } else {
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                } else {

                }


            } else if (i == R.id.btn_pick_photo) {
                select_headView.dismiss();
                backgroundAlpha(1f);
                allPhoto();

            }
        }
    };

    //调用手机相册
    private void allPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,ALL_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果返回码不为-1，则表示不成功
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == ALL_PHOTO){
            //调用相册
            Cursor cursor = this.getContentResolver().query(data.getData(),
                    new String[]{MediaStore.Images.Media.DATA},null,null,null);
            //游标移到第一位，即从第一位开始读取
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            //调用系统裁剪
            uploadFile(new File(path));
            Bitmap bm = BitmapFactory.decodeFile(path);
            mBinding.img.setImageBitmap(bm);
            //startPhoneZoom(Uri.fromFile(new File(path)));
        }else if (requestCode == REQUEST_CODE){
            //相机返回结果，调用系统裁剪
            startPhoneZoom(Uri.fromFile(new File(path)));
        }else if(requestCode == RESULT_PHOTO) {
            //设置裁剪返回的位图
            Bundle bundle = data.getExtras();
            if (bundle!=null){
                Bitmap bitmap = bundle.getParcelable("data");
                //将裁剪后得到的位图在组件中显示
                Glide.clear(mBinding.img);
                mBinding.img.setImageBitmap(bitmap);
                try {
                    verifyStoragePermissions(PetitionStaffImportantMonitorAddPeopleActivity.this);
                    saveFile(bitmap,"001.jpg");
                    uploadFile(new File(getSDPath()+"/revoeye/001.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("chucuo el");
                }

            }
        }
    }
    //调用系统裁剪的方法
    private void startPhoneZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //是否可裁剪
        intent.putExtra("corp", "true");
        //裁剪器高宽比
        intent.putExtra("aspectY",1);
        intent.putExtra("aspectX",1);
        //设置裁剪框高宽
        intent.putExtra("outputX",150);
        intent.putExtra("outputY", 150);
        //返回数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,RESULT_PHOTO);
    }
    private void uploadFile(File file) {
        long length = file.length();
        if (!(length / (1024 * 1024) > 1000)) {
            //uploadfile
            final RequestBody requestBody = RequestBody.create(MediaType.parse("./."), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            Subscription subscription = PetitionStaffApiService.Factory.create().upDateInfoImage(photo)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN, new TypeToken<RUpLoadFile>() {
                            }.getType(),
                            new APICallback<APIResult<RUpLoadFile>>() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onError(Throwable throwable) {

                                }

                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onNext(APIResult<RUpLoadFile> rUpLoadFileAPIResult) {

                                    showToast(rUpLoadFileAPIResult.getMsg());
                                    if (rUpLoadFileAPIResult.getMsg().equals(getString(R.string.petition_staff_upload_successd))){
                                        tAddImportantPeople.setZPID(rUpLoadFileAPIResult.getData().getId());
                                    }

                                }

                                @Override
                                public void onMock(APIResult<RUpLoadFile> rUpLoadFileAPIResult) {

                                }
                            }));
            RxAPIManager.getInstance().add(TAG, subscription);


        }
    }

    public void saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() +"/revoeye/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
    }

    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /*获取动态权限*/
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
// Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
// We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
