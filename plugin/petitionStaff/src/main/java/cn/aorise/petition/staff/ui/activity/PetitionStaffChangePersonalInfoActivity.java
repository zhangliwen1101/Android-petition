package cn.aorise.petition.staff.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.AoriseUtil;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.popupwindow.PetitionStaffSelectHeadView;
import cn.aorise.petition.staff.common.popupwindow.Popuwindow_card_type;
import cn.aorise.petition.staff.common.popupwindow.Popuwindow_sex_choose;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityChangePersonalInfoBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TChangePersonalInfo;
import cn.aorise.petition.staff.module.network.entity.request.TYhm;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.module.network.entity.response.RPersonalInfo;
import cn.aorise.petition.staff.module.network.entity.response.RUpLoadFile;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/2.
 */

public class PetitionStaffChangePersonalInfoActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityChangePersonalInfoBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private PetitionStaffSelectHeadView select_headView;
    private String path;
    private static int REQUEST_CODE=100,ALL_PHOTO=200,RESULT_PHOTO=300;
    private Popuwindow_sex_choose popuwindow_sex_choose;
    private Popuwindow_card_type popuwindow_card_type;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TChangePersonalInfo tChangePersonalInfo=new TChangePersonalInfo();
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_petition_return==id){
            PetitionStaffChangePersonalInfoActivity.this.finish();
        } else if (R.id.rl_petition_right==id){
            ChangePersonalInfo();
        } else if (R.id.img==id) {
            backgroundAlpha(0.3f);
            setheadview();
        } else if (R.id.rl_date==id) {//出生日期选择
            final Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(PetitionStaffChangePersonalInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            popuwindow_sex_choose=new Popuwindow_sex_choose(PetitionStaffChangePersonalInfoActivity.this,this);
            popuwindow_sex_choose.setOnDismissListener(new poponDismissListener());
            //显示窗口
            popuwindow_sex_choose.showAtLocation(this.findViewById(R.id.textView23), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        }else if (R.id.rl_card_type==id) {//证件类型relayout
            //实例化SelectPicPopupWindow
            openActivity(PetitionStaffChooseZJLXActivity.class);
        } else if (R.id.rl_nation==id) {
            openActivity(PetitionStaffChangePersonalInfoNationActivity.class);
        } else if (R.id.rl_area_01==id) {
            openActivity(PetitionStaffChangePersonalInfoAddress.class);
        }
    }

    @Override
    protected void initData() {
            sp=getSharedPreferences(getString(R.string.petition_staff_short_time_sp),MODE_PRIVATE);
            editor=sp.edit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!sp.getString(getString(R.string.petition_staff_sp_MZ),"").equals("")){
            mBinding.txtMz.setText(sp.getString(getString(R.string.petition_staff_sp_MZ),""));
        }
        if (!sp.getString(getString(R.string.petition_staff_QY_SH),"").equals("")){
            mBinding.txtArea.setText(sp.getString(getString(R.string.petition_staff_QY_SH),"")+
                    sp.getString(getString(R.string.petition_staff_QY_S),"")+
                    sp.getString(getString(R.string.petition_staff_QY_X),""));
        }
        if (!sp.getString(getString(R.string.petition_staff_sp_ZJLX),"").equals("")){
            mBinding.txtZjlx.setText(sp.getString(getString(R.string.petition_staff_sp_ZJLX),""));
        }
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_change_personal_info);
        mBinding.rlPetitionReturn.setOnClickListener(this);
        mBinding.rlPetitionRight.setOnClickListener(this);
        mBinding.img.setOnClickListener(this);
        mBinding.rlDate.setOnClickListener(this);
        mBinding.rlSex.setOnClickListener(this);
        mBinding.rlCardType.setOnClickListener(this);
        mBinding.rlNation.setOnClickListener(this);
        mBinding.rlArea01.setOnClickListener(this);
        GetPersonalInfo();
    }

    @Override
    protected void initEvent() {

    }

    private void GetPersonalInfo() {

        TYhm tYhm=new TYhm();
        tYhm.setYHM(getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE).getString(getString(R.string.petition_staff_sp_YHM),""));

        System.out.println(GsonUtil.toJson(tYhm));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetPersonalInfo(GsonUtil.toJson(tYhm))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RPersonalInfo>>() {
                        }.getType(), new APICallback<APIResult<RPersonalInfo>>() {
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
                            public void onNext(APIResult<RPersonalInfo> rPersonalInfoAPIResult) {
                                System.out.println(rPersonalInfoAPIResult.toString());
                                if (rPersonalInfoAPIResult.getData().getZP()!=null){
                                    AoriseUtil.loadImage(PetitionStaffChangePersonalInfoActivity.this,mBinding.img,
                                            cn.aorise.petition.staff.common.Utils.url
                                                    +rPersonalInfoAPIResult.getData().getZP(),R.drawable.petition_staff_header,
                                            R.drawable.petition_staff_header);

                                   /* Picasso.with(PetitionStaffChangePersonalInfoActivity.this).
                                            load(new File("http://192.168.1.40:1111/"+rPersonalInfoAPIResult.getData().getZP())).
                                            error(R.drawable.petition_staff_header).fit().centerCrop().
                                            into(mBinding.img);*/
                                }
                                System.out.println("照片地址："+cn.aorise.petition.staff.common.Utils.url
                                        +rPersonalInfoAPIResult.getData().getZP());
                                System.out.println("所属部门"+rPersonalInfoAPIResult.getData().getSSBM());
                                if (rPersonalInfoAPIResult.getData().getXM()!=null){
                                    mBinding.txtName.setText(rPersonalInfoAPIResult.getData().getXM());
                                }
                                if (rPersonalInfoAPIResult.getData().getZJLX()!=null){
                                    mBinding.txtZjlx.setText(rPersonalInfoAPIResult.getData().getZJLX());
                                }
                                if (rPersonalInfoAPIResult.getData().getZJHM()!=null){
                                    mBinding.txtZjhm.setText(rPersonalInfoAPIResult.getData().getZJHM());
                                }
                                if (rPersonalInfoAPIResult.getData().getDHHM()!=null){
                                    mBinding.txtDhhm.setText(rPersonalInfoAPIResult.getData().getDHHM());
                                }
                                if (rPersonalInfoAPIResult.getData().getCSRQ()!=null){
                                    mBinding.txtCsrq.setText(rPersonalInfoAPIResult.getData().getCSRQ());
                                }
                                if (rPersonalInfoAPIResult.getData().getXB()!=null){
                                    mBinding.txtXb.setText(rPersonalInfoAPIResult.getData().getXB());
                                }
                                if (rPersonalInfoAPIResult.getData().getQY_SH()!=null){
                                    mBinding.txtArea.setText(rPersonalInfoAPIResult.getData().getQY_SH()+
                                    rPersonalInfoAPIResult.getData().getQY_S()+rPersonalInfoAPIResult.getData().getQY_X());
                                }
                                if (rPersonalInfoAPIResult.getData().getQY_S()!=null){

                                }
                                if (rPersonalInfoAPIResult.getData().getQY_X()!=null){

                                }
                                if (rPersonalInfoAPIResult.getData().getXXDZ()!=null){
                                    mBinding.txtAddress.setText(rPersonalInfoAPIResult.getData().getXXDZ());
                                }
                                if (rPersonalInfoAPIResult.getData().getMZ()!=null){
                                    mBinding.txtMz.setText(rPersonalInfoAPIResult.getData().getMZ());
                                }
                                if (rPersonalInfoAPIResult.getData().getBH()!=null){
                                    mBinding.txtBh.setText(rPersonalInfoAPIResult.getData().getBH());
                                }
                                if (rPersonalInfoAPIResult.getData().getZW()!=null){
                                    mBinding.txtZw.setText(rPersonalInfoAPIResult.getData().getZW());
                                } else {
                                    mBinding.txtZw.setText("无");
                                }
                                tChangePersonalInfo.setZP(rPersonalInfoAPIResult.getData().getZPID());
                                mBinding.txtBm.setText(rPersonalInfoAPIResult.getData().getSSBM());
                                editor.putString(getString(R.string.petition_staff_QY_SH),rPersonalInfoAPIResult.getData().getQY_SH());
                                editor.putString(getString(R.string.petition_staff_QY_SHDM),rPersonalInfoAPIResult.getData().getQY_SHDM());
                                editor.putString(getString(R.string.petition_staff_QY_S),rPersonalInfoAPIResult.getData().getQY_S());
                                editor.putString(getString(R.string.petition_staff_QY_SDM),rPersonalInfoAPIResult.getData().getQY_SDM());
                                editor.putString(getString(R.string.petition_staff_QY_X),rPersonalInfoAPIResult.getData().getQY_X());
                                editor.putString(getString(R.string.petition_staff_QY_XDM),rPersonalInfoAPIResult.getData().getQY_XDM());
                                editor.commit();

                            }

                            @Override
                            public void onMock(APIResult<RPersonalInfo> rPersonalInfoAPIResult) {

                            }
                        }));

        RxAPIManager.getInstance().add(TAG, subscription);
    }


    private void ChangePersonalInfo() {
        tChangePersonalInfo.setXM(mBinding.txtName.getText().toString());
        tChangePersonalInfo.setZJLX(mBinding.txtZjlx.getText().toString());
        tChangePersonalInfo.setZJHM(mBinding.txtZjhm.getText().toString());
        tChangePersonalInfo.setDHHM(mBinding.txtDhhm.getText().toString());
        tChangePersonalInfo.setCSRQ(mBinding.txtCsrq.getText().toString());
        tChangePersonalInfo.setXB(mBinding.txtXb.getText().toString());
        tChangePersonalInfo.setQY_SHDM(sp.getString(getString(R.string.petition_staff_QY_SHDM),""));
        tChangePersonalInfo.setQY_SH(sp.getString(getString(R.string.petition_staff_QY_SH),""));
        tChangePersonalInfo.setQY_S(sp.getString(getString(R.string.petition_staff_QY_S),""));
        tChangePersonalInfo.setQY_SDM(sp.getString(getString(R.string.petition_staff_QY_SDM),""));
        tChangePersonalInfo.setQY_X(sp.getString(getString(R.string.petition_staff_QY_X),""));
        tChangePersonalInfo.setQY_XDM(sp.getString(getString(R.string.petition_staff_QY_XDM),""));
        tChangePersonalInfo.setXXDZ(mBinding.txtAddress.getText().toString());
        tChangePersonalInfo.setMZ(mBinding.txtMz.getText().toString());
        tChangePersonalInfo.setBH(mBinding.txtBh.getText().toString());
        if (mBinding.txtZw.getText().toString().trim().equals("")) {
            tChangePersonalInfo.setZW("无");
        } else {
            tChangePersonalInfo.setZW(mBinding.txtZw.getText().toString());
        }
        //tChangePersonalInfo.setZW("无");
        tChangePersonalInfo.setYHM(getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE).getString(getString(R.string.petition_staff_sp_YHM),""));
        tChangePersonalInfo.setPT("android");
        tChangePersonalInfo.setBB(getVersion());
        tChangePersonalInfo.setMZID(sp.getString(getString(R.string.petition_staff_sp_MZID),""));
        System.out.println(GsonUtil.toJson(tChangePersonalInfo));
        Subscription subscription = PetitionStaffApiService.Factory.create().ChangePersonalInfo(GsonUtil.toJson(tChangePersonalInfo))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
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
                                if (rEmptyEntityAPIResult.getMsg().equals(getString(R.string.petition_staff_change_succeed))){
                                    PetitionStaffChangePersonalInfoActivity.this.finish();
                                }
                            }

                            @Override
                            public void onMock(APIResult<REmptyEntity> rEmptyEntityAPIResult) {

                            }
                        }));

        RxAPIManager.getInstance().add(TAG, subscription);
    }

    public String getVersion() {
             try {
                     PackageManager manager = this.getPackageManager();
                    PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                     String version = info.versionName;
                 return this.getString(R.string.version_name) + version;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return this.getString(R.string.can_not_find_version_name);
                 }
         }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.clear().commit();
    }

    //照片选择
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //就像onActivityResult一样这个地方就是判断你是从哪来的。
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                    showToast(R.string.petition_staff_showtoast_01);
                }
                break;
            default:
                onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setheadview() {
        //实例化SelectPicPopupWindow
        select_headView = new PetitionStaffSelectHeadView(this,listener);
        select_headView.setOnDismissListener(new poponDismissListener());
        //显示窗口
        select_headView.showAtLocation(this.findViewById(R.id.textView23), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }
    private Uri imageUri;//原图保存地址
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.btn_take_photo) {



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
                    verifyStoragePermissions(PetitionStaffChangePersonalInfoActivity.this);
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

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("return-data", false);



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
                                    System.out.println(rUpLoadFileAPIResult.toString());
                                    showToast(rUpLoadFileAPIResult.getMsg());
                                    if (rUpLoadFileAPIResult.getMsg().equals(getString(R.string.petition_staff_upload_successd))){
                                        tChangePersonalInfo.setZP(rUpLoadFileAPIResult.getData().getId());
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
