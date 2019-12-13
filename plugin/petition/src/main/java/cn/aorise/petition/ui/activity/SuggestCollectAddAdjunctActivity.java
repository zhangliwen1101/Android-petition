package cn.aorise.petition.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.R;
import cn.aorise.petition.common.FileUtils;
import cn.aorise.petition.common.Utils;
import cn.aorise.petition.databinding.PetitionActivityFillAdjunctBinding;
import cn.aorise.petition.databinding.PetitionActivitySuggestCollectAddAdjunctBinding;
import cn.aorise.petition.module.network.Mock;
import cn.aorise.petition.module.network.PetitionApiService;
import cn.aorise.petition.module.network.entity.request.TAddAdviceCollect;
import cn.aorise.petition.module.network.entity.response.RAddAdviceCollect;
import cn.aorise.petition.module.network.entity.response.RUpLoadFile;
import cn.aorise.petition.module.network.entity.response.Rsubmit;
import cn.aorise.petition.ui.adapter.AddAdjunctAdapter;
import cn.aorise.petition.ui.adapter.AddFileAdapter;
import cn.aorise.petition.ui.base.PetitionBaseActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/28.
 */

public class SuggestCollectAddAdjunctActivity extends PetitionBaseActivity implements View.OnClickListener{
    private PetitionActivitySuggestCollectAddAdjunctBinding mBinding;
    private static int REQUEST_CODE=100,ALL_PHOTO=200,RESULT_PHOTO=300,FILE_SELECT_CODE=400;
    private List<String> picturePath=new ArrayList<>();
    private List<String> filePath=new ArrayList<>();
    private List<String> allPath=new ArrayList<>();
    private AddAdjunctAdapter mAdapter;
    private AddFileAdapter addFileAdapter;
    private static final String TAG = AboutActivity.class.getSimpleName();
    private int i;
    private StringBuilder ParamsFj=new StringBuilder();//上传请求需要的参数FJ字符串；
    private List<String> FJID=new ArrayList<>();//上传文件之后返回的ID的集合
    private SharedPreferences sp,sp1;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.ll_add_picture==id) {
            allPhoto();
        } else if (R.id.ll_add_file==id) {
            showFileChooser();
        } else if (R.id.rl_submit==id) {
            allPath.clear();
            FJID.clear();
            UpLoadFile();//上传文件并保存地址

        }
    }

    @Override
    protected void initData() {

        getPermisson();

        mAdapter=new AddAdjunctAdapter(picturePath,this);
        addFileAdapter=new AddFileAdapter(filePath,this);
        sp=getSharedPreferences(getString(R.string.petition_sharepre_nomal),MODE_PRIVATE);
        sp1=getSharedPreferences(getString(R.string.petition_sharepre_name),MODE_PRIVATE);
        editor=sp.edit();
    }

    @Override
    protected void initView() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_activity_suggest_collect_add_adjunct);
        mBinding.llAddPicture.setOnClickListener(this);
        mBinding.llAddFile.setOnClickListener(this);
        mBinding.rlSubmit.setOnClickListener(this);

        mBinding.mlistview.setAdapter(mAdapter);
        mBinding.mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                if (picturePath.size()==0){
                    allPhoto();
                }else{
                    if (i==picturePath.size()&&picturePath.size()<=4){
                        allPhoto();
                    }else if (i<=4){
                        final File file = new File(picturePath.get(i));
                        if( file != null && file.isFile() == true){
                            new AlertDialog.Builder(SuggestCollectAddAdjunctActivity.this).setTitle("系统提示")//设置对话框标题

                                    /*.setMessage("")//设置显示的内容*/

                                    .setPositiveButton("预览",new DialogInterface.OnClickListener() {//添加确定按钮

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                            // TODO Auto-generated method stub
                                            Intent intent = new Intent();
                                            intent.setAction(android.content.Intent.ACTION_VIEW);
                                            intent.setDataAndType(Uri.fromFile(file), "image/*");
                                            startActivity(intent);

                                        }

                                    }).setNegativeButton("删除",new DialogInterface.OnClickListener() {//添加返回按钮

                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件
                                    // TODO Auto-generated method stub
                                    picturePath.remove(i);
                                    mAdapter.notifyDataSetChanged();
                                }

                            }).show();//在按键响应事件中显示此对话框



                        }
                    }
                }
            }
        });


        mBinding.mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(SuggestCollectAddAdjunctActivity.this).setTitle("系统提示")//设置对话框标题
                                    /*.setMessage("")//设置显示的内容*/
                        .setPositiveButton("预览",new DialogInterface.OnClickListener() {//添加确定按钮

                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub
                                FileUtils.openFile(SuggestCollectAddAdjunctActivity.this,new File(filePath.get(position)));
                            }

                        }).setNegativeButton("删除",new DialogInterface.OnClickListener() {//添加返回按钮

                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub
                        filePath.remove(position);
                        addFileAdapter.notifyDataSetChanged();
                    }

                }).show();//在按键响应事件中显示此对话框

            }
        });
    }




    @Override
    protected void initEvent() {

    }
    //调用系统图片选择器
    private void allPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
            Cursor cursor = SuggestCollectAddAdjunctActivity.this.getContentResolver().query(data.getData(),
                    new String[]{MediaStore.Images.Media.DATA},null,null,null);
            //游标移到第一位，即从第一位开始读取
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            System.out.println(path);
            File file = new File(path);
            if (file.length() / (1024 * 1024) > 10) {
                showToast("文件大小不能超过10M");
            } else {
                picturePath.add(path);
                //upLoad(path);
                mAdapter.notifyDataSetChanged();
            }
            //adapter.notifyDataSetChanged();
            //startPhoneZoom(Uri.fromFile(new File(path)));
        }else if (requestCode == REQUEST_CODE){
            //相机返回结果，调用系统裁剪

        }else if(requestCode == RESULT_PHOTO) {
            //设置裁剪返回的位图
            Bundle bundle = data.getExtras();
            if (bundle!=null){
                Bitmap bitmap = bundle.getParcelable("data");
                //将裁剪后得到的位图在组件中显示
                //persion_head.setImageBitmap(bitmap);

                //st=bitmapToBase64(bitmap);
                Message message=new Message();
                message.what=100;
                //handler.sendMessage(message);
            }
        } else if (requestCode==FILE_SELECT_CODE) {
            Uri uri = data.getData();
            String path = FileUtils.getPath(this, uri);

            if (path != null) {
                File file = new File(path);
                if (file.length() / (1024 * 1024) > 10) {
                    showToast("文件大小不能超过10M");
                } else {
                    if (path != null) {
                        if (path.substring(path.lastIndexOf("."), path.length()).equals(".doc") ||
                                path.substring(path.lastIndexOf("."), path.length()).equals(".docx") ||
                                path.substring(path.lastIndexOf("."), path.length()).equals(".gif") ||
                                path.substring(path.lastIndexOf("."), path.length()).equals(".jip") ||
                                path.substring(path.lastIndexOf("."), path.length()).equals(".jpeg") ||
                                path.substring(path.lastIndexOf("."), path.length()).equals(".png") ||
                                path.substring(path.lastIndexOf("."), path.length()).equals(".bmp") ||
                                path.substring(path.lastIndexOf("."), path.length()).equals(".txt") ||
                                path.substring(path.lastIndexOf("."), path.length()).equals(".pdf")) {
                            filePath.add(path);
                        } else {
                            showToast(R.string.petition_show_error);
                        }
                    } else {
                        showToast(R.string.petition_error_toast_01);
                    }

                    mBinding.mylistview.setAdapter(addFileAdapter);
                    addFileAdapter.notifyDataSetChanged();
                }
            }else {
                showToast(R.string.petition_error_toast_01);
            }
        }
    }
    //调用系统文件选择功能
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println(filePath.size());

    }
    private void upLoad(String picpath){
        File file = new File(picpath);
        uploadFile(file);
    }
    private void uploadFile(File file) {
        long length = file.length();
        if (!(length / (1024 * 1024) > 1000)) {
            //uploadfile
            final RequestBody requestBody = RequestBody.create(MediaType.parse("./."), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            Subscription subscription = PetitionApiService.Factory.create().upDateInfoImage(photo)
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

                                    FJID.add(rUpLoadFileAPIResult.getData().getId());

                                    if (i==(filePath.size()+picturePath.size())){
                                        ParamsFj=new StringBuilder();
                                        for(int b=0;b<FJID.size();b++){
                                            if(b>0)
                                                ParamsFj.append(",");
                                            ParamsFj.append(FJID.get(b));
                                        }
                                    }
                                    if (FJID.size()==allPath.size()){
                                        System.out.println("这是我凭借的字符串"+ParamsFj.toString());
                                        //showToast("正在上传");
                                        AddAdvice();
                                    }
                                }

                                @Override
                                public void onMock(APIResult<RUpLoadFile> rUpLoadFileAPIResult) {

                                }
                            }));
            RxAPIManager.getInstance().add(TAG, subscription);


        }
    }
    private void UpLoadFile(){
        allPath.clear();
        for (int n=0;n<filePath.size();n++){
            allPath.add(filePath.get(n));
        }
        for (int j=0;j<picturePath.size();j++) {
            allPath.add(picturePath.get(j));
        }
        if (allPath.size()>5) {
            showToast("只能增加五个附件");
        } else if (allPath.size()==0){
            AddAdvice();
        }else {
            for (i = 0; i < allPath.size(); i++) {
                upLoad(allPath.get(i));
                System.out.println("循环了" + i);
            }
        }
    }
    private String makerequest() {
        TAddAdviceCollect tAddAdviceCollect=new TAddAdviceCollect();
        tAddAdviceCollect.setFJ(ParamsFj.toString());
        tAddAdviceCollect.setJYLXY(sp.getString(getString(R.string.petition_suggest_collect_JYLXY),""));
        tAddAdviceCollect.setJYLXYDM(sp.getString(getString(R.string.petition_suggest_collect_JYLXYDM),""));
        tAddAdviceCollect.setJYLXE(sp.getString(getString(R.string.petition_suggest_collect_JYLXE),""));
        tAddAdviceCollect.setJYLXEDM(sp.getString(getString(R.string.petition_suggest_collect_JYLXEDM),""));
        tAddAdviceCollect.setJYLXS(sp.getString(getString(R.string.petition_suggest_collect_JYLXS),""));
        tAddAdviceCollect.setJYLXSDM(sp.getString(getString(R.string.petition_suggest_collect_JYLXSDM),""));
        tAddAdviceCollect.setJYRZJH(sp1.getString(getString(R.string.petition_shareprefers_XFR_ZJHM),""));
        tAddAdviceCollect.setNR(sp.getString(getString(R.string.petition_suggest_collect_fill_content),""));
        tAddAdviceCollect.setSFGK(sp.getString(getString(R.string.petition_suggest_collect_SFGK),""));
        return GsonUtil.toJson(tAddAdviceCollect);
    }
    private void AddAdvice(){
        System.out.println(makerequest());
        makerequest();
        System.out.println(makerequest());
        Subscription subscription = PetitionApiService.Factory.create().AddAdviceCollect(makerequest())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_QUESTION_TYPE,
                        new TypeToken<APIResult<RAddAdviceCollect>>() {
                        }.getType(), new APICallback<APIResult<RAddAdviceCollect>>() {
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
                            public void onNext(APIResult<RAddAdviceCollect> rAddAdviceCollectAPIResult) {
                                showToast(rAddAdviceCollectAPIResult.getMsg());
                                if (rAddAdviceCollectAPIResult.getMsg().equals(getString(R.string.petition_01))) {
                                    SuggestCollectAddNewActivity.instance.finish();
                                    SuggestCollectAddAdjunctActivity.this.finish();
                                    SuggestCollectActivity.instance.finish();
                                    openActivity(SuggestCollectActivity.class);
                                }else {
                                    showToast(rAddAdviceCollectAPIResult.getMsg());
                                }


                            }

                            @Override
                            public void onMock(APIResult<RAddAdviceCollect> rAddAdviceCollectAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
    /*动态获取读取文件权限*/
    private void getPermisson(){
        if (ActivityCompat.checkSelfPermission(SuggestCollectAddAdjunctActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((BaseActivity) SuggestCollectAddAdjunctActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

}
