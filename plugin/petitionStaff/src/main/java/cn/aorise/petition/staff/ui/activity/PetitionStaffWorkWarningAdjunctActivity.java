package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;


import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.Utils;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityQueryEvaluateAdjunctListBinding;
import cn.aorise.petition.staff.ui.adapter.AddFileAdapter;
import cn.aorise.petition.staff.ui.adapter.QueryEvaluateAdjunctAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;


/**
 * Created by Administrator on 2017/5/22.
 */

public class PetitionStaffWorkWarningAdjunctActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityQueryEvaluateAdjunctListBinding mBinding;
    private String adjunctList;
    private String[] data;
    private List<String> PictureList=new ArrayList<>();
    private List<String> FileList=new ArrayList<>();
    private List<String> FileListName=new ArrayList<>();
    private List<String> PictureListName=new ArrayList<>();
    private QueryEvaluateAdjunctAdapter mAdapter;
    private AddFileAdapter addFileAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {
        getPathData();
        if (PictureList!=null) {
            mAdapter = new QueryEvaluateAdjunctAdapter(PictureList, this);
        }
        if (FileList!=null) {
            addFileAdapter = new AddFileAdapter(FileList, this);
        }
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_query_evaluate_adjunct_list);

        if (PictureList!=null) {
            mBinding.mlistview.setAdapter(mAdapter);
        }
        if (FileList!=null) {
            mBinding.mylistview.setAdapter(addFileAdapter);
        }
        mBinding.mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PetitionStaffWorkWarningAdjunctActivity.this,PetitionStaffWorkWarningAdjunctPictureActivity.class);
                intent.putExtra("path",PictureList.get(position));
                startActivity(intent);

            }
        });
        mBinding.mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse(Utils.url+FileList.get(position).toString()));
                startActivity(i);
            }
        });
        /*AoriseUtil.loadImage(QueryEvaluateDetailAdjunctActivity.this,mBinding.imgDouble,"http://192.168.1.40:1111/XFWD/2017/05/12/4c6ba902e34b4d0ab7b8ff510eb8c4e3.jpg");*/
    }

    @Override
    protected void initEvent() {

    }

    private void getPathData(){
        adjunctList=getIntent().getStringExtra("fj");
        System.out.println("附件列表："+adjunctList);
        if (!adjunctList.equals("")) {
            data = adjunctList.split(",");
            for (int i = 0; i < data.length; i++) {
                if (i%2==0) {
                    if (data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".doc") ||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".docx") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".gif") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".jip") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".jpeg") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".png") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".bmp") ||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".txt") ||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".pdf")||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".xlsx")||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".xls")) {
                        FileList.add(data[i]);
                    } else {
                        if (!data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".csv")) {
                            PictureList.add(data[i]);
                        }
                    }
                }else {
                    if (data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".doc") ||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".docx") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".gif") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".jip") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".jpeg") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".png") ||
                            //data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".bmp") ||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".txt") ||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".pdf")||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".xlsx")||
                            data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".xls")) {
                        FileList.add(data[i]);
                    } else {
                        if (!data[i].substring(data[i].lastIndexOf("."), data[i].length()).equals(".csv")) {
                            PictureList.add(data[i]);
                        }
                    }

                }
            }
            System.out.println(FileList + "-" + PictureList);

        }
    }

    private void downloadFile(){

    }

}
