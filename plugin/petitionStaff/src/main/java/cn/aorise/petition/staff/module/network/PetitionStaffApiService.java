package cn.aorise.petition.staff.module.network;

import java.util.List;

import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.utils.assist.DebugUtil;
import cn.aorise.petition.staff.module.network.entity.response.RAllAddress;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyze;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyzeOrgan;
import cn.aorise.petition.staff.module.network.entity.response.RAnalyzeType;
import cn.aorise.petition.staff.module.network.entity.response.RDMIDMC;
import cn.aorise.petition.staff.module.network.entity.response.RDisputeCheck;
import cn.aorise.petition.staff.module.network.entity.response.RDmMc;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionMatter;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionMatterDetail;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionPeople;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionPeopleDetail;
import cn.aorise.petition.staff.module.network.entity.response.RLogin;
import cn.aorise.petition.staff.module.network.entity.response.RPersonalInfo;
import cn.aorise.petition.staff.module.network.entity.response.RPetitionInfoSubmission;
import cn.aorise.petition.staff.module.network.entity.response.RPetitionType;
import cn.aorise.petition.staff.module.network.entity.response.RQuestion;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfo;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfoDetail;
import cn.aorise.petition.staff.module.network.entity.response.RUpLoadFile;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarning;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarningList;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarningListDetail;
import cn.aorise.petition.staff.module.network.entity.response.RXinfangLeixing;
import cn.aorise.petition.staff.module.network.entity.response.RZerenDanwei;
import cn.aorise.petition.staff.ui.bean.WhereInfo;
import cn.aorise.petition.staff.ui.fragment.WentiLeixingInfo;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pc on 2017/3/8.
 */
public interface PetitionStaffApiService {

    @GET("Login/petitionLogin")
    Observable<APIResult<RLogin>> PetitionLogin(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/queryWorkPeople")
    Observable<APIResult<List<RStaffInfo>>> GetStaffInfo(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/letterInfo")
    Observable<APIResult<RStaffInfoDetail>> GetStaffInfoDetail(@Query("strRequest") String strRequest);

    @GET("ImporComplainantInfo/importantPeople")
    Observable<APIResult<List<RImportantPetitionPeople>>> GetImportantPetitionPeople(@Query("strRequest") String strRequest);

    @GET("ImporComplainantInfo/importantPeople")
    Observable<APIResult<List<RImportantPetitionPeople>>> GetImportantMonitor(@Query("strRequest") String strRequest);

    @GET("ImporComplainantInfo/impPeopleInfo")
    Observable<APIResult<RImportantPetitionPeopleDetail>> GetImportantPetitionPeopleDetail(@Query("strRequest") String strRequest);

    @GET("DictionaryInfo/AllLXY")
    Observable<APIResult<List<RPetitionType>>> GetPetitionType(@Query("strRequest") String strRequest);

    @GET("DictionaryInfo/AllLevel")
    Observable<APIResult<List<RPetitionType>>> GetPetitionLevel(@Query("strRequest") String strRequest);

    @GET("ImporPetitionLetter/impPetition")
    Observable<APIResult<List<RImportantPetitionMatter>>> GetImportantPetitionMatter(@Query("strRequest") String strRequest);

    @GET("ImporPetitionLetter/impPetitionMatter")
    Observable<APIResult<RImportantPetitionMatterDetail>> GetImportantPetitionMatterDetail(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/disputeCheck")/*纠纷排查*/
    Observable<APIResult<List<RDisputeCheck>>> GetDisputeCheck(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/changePetitionImfo")
    Observable<APIResult<RPersonalInfo>> GetPersonalInfo(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/savePetitionImfo")
    Observable<APIResult<REmptyEntity>> ChangePersonalInfo(@Query("strRequest") String strRequest);

    @GET("ComplainantInfo/petitionVerifyPassword")
    Observable<APIResult<REmptyEntity>> VerifyPassword(@Query("strRequest") String strRequest);

    @GET("ComplainantInfo/verifycodeSend")
    Observable<APIResult<REmptyEntity>> GetVerificationCode(@Query("strRequest") String strRequest);

    @GET("ComplainantInfo/verifyMessage")
    Observable<APIResult<REmptyEntity>> VerifyCode(@Query("strRequest") String strRequest);

    @GET("ComplainantInfo/petitionChangePhoneNum")
    Observable<APIResult<REmptyEntity>> petitionChangePhoneNum(@Query("strRequest") String strRequest);

    @GET("ComplainantInfo/petitionChangePassword")
    Observable<APIResult<REmptyEntity>> petitionChangePassword(@Query("strRequest") String strRequest);

    @GET("PetitionLetter/petitionImfoPush")
    Observable<APIResult<RPetitionInfoSubmission>> GetPetitionInfoPush(@Query("zzjg") String strRequest);

    @Multipart
    @POST("DictionaryInfo/fileUpload")
    Observable<APIResult<RUpLoadFile>> upDateInfoImage(@Part MultipartBody.Part file);

    @GET("DictionaryInfo/regionalLink")
    Observable<APIResult<List<RQuestion>>> getAddress(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/queryStatistic")
    Observable<APIResult<List<Integer>>> GetAnalyze(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/AQBJLQuery")
    Observable<APIResult<List<Float>>> GetQunzhogncanpingLv(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/XFType")
    Observable<APIResult<RXinfangLeixing>> GetXinfangleixing(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/XFBMSLYQuery")
    Observable<APIResult<List<Float>>> GetAnqiBanjieLv(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/XFBMMYLQuery")
    Observable<APIResult<List<Float>>> GetWangxinzhanbiLv(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/ZRDWMYLQuery")
    Observable<APIResult<List<Float>>> GetBumenmanyiLv(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/QZCPLQuery")
    Observable<APIResult<List<Float>>> GetCanPingLv(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/WXZBLQuery")
    Observable<APIResult<List<Float>>> Getwangxinzhanbi(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/WLTJQuery")
    Observable<APIResult<List<Float>>> GetJishiShouliLv(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/ZRBMSLYQuery")
    Observable<APIResult<List<Float>>> GetZerendanweimanyilv(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/GetAllOrg")
    Observable<APIResult<List<RZerenDanwei>>> GetResponsible(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/GetAllIssure")
    Observable<APIResult<List<WentiLeixingInfo>>> Getwentileixing(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/petitionTypeStatistics")
    Observable<APIResult<RAnalyzeType>> GetAnalyzeType(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/ProcessingMechanism")
    Observable<APIResult<List<RAnalyzeOrgan>>> GetAnalyzeOrgan(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/workEarlyWarning")
    Observable<APIResult<List<RWorkWarning>>> GetWorkWarning(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/visit")
    Observable<APIResult<List<RWorkWarningList>>> GetWorkWarningList(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/overdue")
    Observable<APIResult<List<RWorkWarningList>>> GetWorkWarningListOverdue(@Query("strRequest") String strRequest);

    @GET("PetitionImfo/workEarlyWarningAll")
    Observable<APIResult<RWorkWarningListDetail>> GetWorkWarningListDetail(@Query("strRequest") String strRequest);

    @GET("DictionaryInfo/GetNation")
    Observable<APIResult<List<RQuestion>>> getNation(@Query("strRequest") String strRequest);

    @GET("PetitionLetter/queryCLJG")//获取去向列表
    Observable<APIResult<List<WhereInfo>>> getWhere(@Query("strRequest") String strRequest);

    @GET("DictionaryInfo/Zidian")
    Observable<APIResult<List<RDMIDMC>>> getZJLX(@Query("strRequest") String strRequest);

    @GET("ImporComplainantInfo/addImpPeople")
    Observable<APIResult<REmptyEntity>> AddImportantPeople(@Query("strRequest") String strRequest);

    @FormUrlEncoded
    @POST("ImporPetitionLetter/addImpPetition")//
    Observable<APIResult<REmptyEntity>> AddImportantMatter(@Field("strRequest") String strRequest);

    @FormUrlEncoded
    @POST("PetitionLetter/complaintByCL")//提交转送
    Observable<APIResult<REmptyEntity>> AddTransPort(@Field("strRequest") String strRequest);

    @GET("DictionaryInfo/problemType")
    Observable<APIResult<List<RQuestion>>> getQuestionType(@Query("strRequest") String strRequest);

    @GET("DictionaryInfo/AreaData")
    Observable<APIResult<List<RAllAddress>>> getAllAddress(@Query("strRequest") String strRequest);

    @GET("DictionaryInfo/IssueData")
    Observable<APIResult<List<RAllAddress>>> getAllType(@Query("strRequest") String strRequest);

    class Factory {
        public Factory() {
        }

        public static PetitionStaffApiService create() {
            return RetrofitFactory.getInstance().create(DebugUtil.isDebug(), PetitionStaffApiService.class, API.BASE_URL);
        }
    }
}
