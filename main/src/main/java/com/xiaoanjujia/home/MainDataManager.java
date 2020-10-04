package com.xiaoanjujia.home;

import com.google.gson.Gson;
import com.xiaoanjujia.common.model.BaseDataManager;
import com.xiaoanjujia.common.model.DataManager;
import com.xiaoanjujia.common.model.http.BaseApiService;
import com.xiaoanjujia.common.util.FillUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author：xp on 2018/4/20 18:26.
 */

public class MainDataManager extends BaseDataManager {

    private static String KPI_ROOT_URL = "https://a.xiaoanjujia.com";//预生产环境接口
    private static String HK_ROOT_URL = "http://hk.xiaoanjujia.com";//预生产环境接口
    public static String HK_ROOT_URL_PIC = "https://36.112.172.190:6114";//海康 图片 接口
    //注册
    private static String GENERAL_REGISTER = "/api/v1/register";
    //获取验证码
    private static String GENERAL_REGISTER_CODE = "/api/identify";
    //登录
    private static String GENERAL_LOGIN = "/api/v1/login";
    //忘记密码
    private static String GENERAL_FORGER = "/api/v1/editPassword";
    //上传图片
    private static String GENERAL_UPLOAD_IMAGE = "/api/image";
    //提交商户认证
    private static String GENERAL_GET_COMMUNITY = "/api/v1/getcommunity";
    //商户发布订单(有更改字段)
    private static String GENERAL_GETCOMUORDER = "/api/v1/getcomuorder";
    //用户余额用户余额
    private static String GENERAL_BALANCE = "/api/v1/balance";
    //获取商户认证状态
    private static String GENERAL_COMEXAMINE = "/api/v1/comexamine";
    //角色类型页面
    private static String GENERAL_ROLETRPETOP = "/api/v1/roletrpetop";
    //普通物业添加日志
    private static String GENERAL_ADDPROPERTYLOG = "/api/v1/addpropertylog";
    //物业管理列表日志
    private static String GENERAL_PROPERTYLOGLISTS = "/api/v1/propertyloglists";
    //普通物业往期查询
    private static String GENERAL_ORDINARYLOGLISTS = "/api/v1/ordinaryloglists";
    //社区首页分类
    private static String GENERAL_COMCATELISTS = "/api/v1/comcatelists";
    //物业电话
    private static String PROPHONE = "/api/v1/prophone";
    private static String PRODISPLAY = "/api/v1/prodisplay";
    private static String APPSTATUS = "/api/v1/appstatus";
    //社区热点
    private static String GENERAL_COMMUHOTSPOT = "/api/v1/commuhotspot";
    //热点列表
    private static String GENERAL_COMHOTSPOTLIST = "/api/v1/comhotspotlist";
    //社区搜索
    private static String GENERAL_COMMUNITYSEARCH = "/api/v1/communitysearch";
    //社区列表
    private static String GENERAL_ORDERLISTS = "/api/v1/orderlists";
    //分类列表
    private static String GENERAL_CATEODLISTS = "/api/v1/cateodlists";
    //日志详情
    private static String GENERAL_LOGDETAILS = "/api/v1/logdetails";
    //审核通过
    private static String GENERAL_LOGEXAMINE = "/api/v1/logexamine";
    //审核拒绝
    private static String GENERAL_LOGREFUSE = "/api/v1/logrefuse";
    //审核拒绝
    private static String GENERAL_ORDERDETAILED = "/api/v1/orderdetailed";

    //社区订单评论
    private static String GENERAL_ORDERCOMMENTS = "/api/v1/ordercomments";
    //新增评论
    private static String GENERAL_GETCOMMON = "/api/v1/getCommon";
    //评论列表
    private static String GENERAL_COMMENTSLISTS = "/api/v1/commentslists";
    //新增点赞
    private static String GENERAL_GETLIKE = "/api/v1/getlike";
    //商户统计
    private static String GENERAL_CONUT = "/api/v1/conut";

    //新增访客
    private static String GENERAL_ADDVISIT = "/api/v1/addvisit";
    //新增联系
    private static String GENERAL_ADDCONTACT = "/api/v1/addcontact";
    //新增粉丝
    private static String GENERAL_ADDFANS = "/api/v1/addfans";

    //详情判断是否能领取红包
    private static String GENERAL_ACTIONCHECKBONUS = "/api/v1/actioncheckbonus";
    //领取红包
    private static String GENERAL_WATCHBONUS = "/api/v1/watchbonus";

    public MainDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static MainDataManager getInstance(DataManager dataManager) {
        return new MainDataManager(dataManager);
    }

    /*
     *验证短信验证码注册/登陆 （只做示例，无数据返回）
     */
    public Disposable login(DisposableObserver<ResponseBody> consumer, String mobile, String verifyCode) {

        return changeIOToMainThread(getService(MainApiService.class).login(mobile, verifyCode), consumer);
    }


    public Disposable getMainData(int start, int count, DisposableObserver<ResponseBody> consumer) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("start", start);
        map.put("count", count);
        return changeIOToMainThread(getService(BaseApiService.class).executeGet("http://www.baidu.com", map), consumer);

    }

    public List<String> getTypeOfNameData() {
        ArrayList<String> list = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            list.add("家用电器");
        }
        return list;
    }

    public <S> Disposable getData(DisposableObserver<S> consumer, final Class<S> clazz, final String fillName) {
        return Observable.create(new ObservableOnSubscribe<S>() {
            @Override
            public void subscribe(ObservableEmitter<S> e) throws Exception {
                InputStream is = getContext().getAssets().open(fillName);
                String text = FillUtil.readTextFromFile(is);
                Gson gson = new Gson();
                e.onNext(gson.fromJson(text, clazz));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(consumer);
    }

    /**
     * 获取注册数据
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegisterData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_REGISTER, mapParameters, mapHeaders), consumer);

    }

    /**
     * 获取忘记密码数据
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegisterForgerData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePutHeader
                (KPI_ROOT_URL + GENERAL_FORGER, mapParameters, mapHeaders), consumer);

    }

    /**
     * 获取验证码
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegisretCodeData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_REGISTER_CODE, mapParameters, mapHeaders), consumer);

    }

    /**
     * 获取登录数据
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getLoginData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_LOGIN, mapParameters, mapHeaders), consumer);
    }

    /**
     * 提交商户认证
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getFeedBackData(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_GET_COMMUNITY, mapParameters, mapHeaders), consumer);
    }

    /**
     * 商户发布订单(有更改字段)
     * private static String GENERAL_GETCOMUORDER = "/api/v1/getcomuorder";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getComuorder(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_GETCOMUORDER, mapParameters, mapHeaders), consumer);
    }

    /**
     * 用户余额用户余额
     * private static String GENERAL_BALANCE = "/api/v1/balance";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getBalance(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_BALANCE, mapParameters, mapHeaders), consumer);
    }

    /**
     * 上传图片
     *
     * @param mapHeaders 请求头
     * @param consumer   consumer
     * @return Disposable
     * @Url() String url,
     * @PartMap Map<String, RequestBody> map,
     * @Part List<MultipartBody.Part> parts,
     * @HeaderMap TreeMap<String, String> headers
     */
    public Disposable executePostImageHeader(TreeMap<String, String> mapHeaders, Map<String, RequestBody> map, List<MultipartBody.Part> parts, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostImageHeaderNoParam
                (KPI_ROOT_URL + GENERAL_UPLOAD_IMAGE, parts, mapHeaders), consumer);
    }

    /**
     * 获取商户认证状态
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getComexamine(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_COMEXAMINE, mapParameters, mapHeaders), consumer);
    }

    /**
     * 角色类型页面
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getTypeOfRole(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ROLETRPETOP, mapParameters, mapHeaders), consumer);
    }

    /**
     * 普通物业添加日志
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getAddPropertyLog(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ADDPROPERTYLOG, mapParameters, mapHeaders), consumer);
    }

    /**
     * 物业管理列表日志
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getPropertyLoglists(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_PROPERTYLOGLISTS, mapParameters, mapHeaders), consumer);
    }

    /**
     * 普通物业往期查询
     * private static String GENERAL_ORDINARYLOGLISTS = "/api/v1/ordinaryloglists";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getOrdinaryLogLists(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ORDINARYLOGLISTS, mapParameters, mapHeaders), consumer);
    }

    /**
     * 社区首页分类
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getComcateLists(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_COMCATELISTS, mapParameters, mapHeaders), consumer);
    }
    /**
     * 物业电话
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getProPhone(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostNoParam
                (KPI_ROOT_URL + PROPHONE, mapParameters, mapHeaders), consumer);
    }
    /**
     * 物业电话
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getProDisplay(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostNoParam
                (KPI_ROOT_URL + PRODISPLAY, mapParameters, mapHeaders), consumer);
    }/**
     * app更新
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getAppUpdate(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostNoParam
                (KPI_ROOT_URL + APPSTATUS, mapParameters,mapHeaders), consumer);
    }
    /**
     * 社区热点
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getCommuhotSpot(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_COMMUHOTSPOT, mapParameters, mapHeaders), consumer);
    }

    /**
     * 热点列表
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getComhotSpotList(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_COMHOTSPOTLIST, mapParameters, mapHeaders), consumer);
    }

    /**
     * 社区搜索
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getCommunitySearch(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_COMMUNITYSEARCH, mapParameters, mapHeaders), consumer);
    }

    /**
     * 社区列表
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getOrderLists(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ORDERLISTS, mapParameters, mapHeaders), consumer);
    }

    /**
     * 分类列表
     * private static String GENERAL_CATEODLISTS = "/api/v1/cateodlists";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getCateodLists(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_CATEODLISTS, mapParameters, mapHeaders), consumer);
    }

    /**
     * 日志详情
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getLogDetails(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_LOGDETAILS, mapParameters, mapHeaders), consumer);
    }

    /**
     * 审核通过
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getLogExamine(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_LOGEXAMINE, mapParameters, mapHeaders), consumer);
    }

    /**
     * 审核拒绝
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getLogRefuse(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_LOGREFUSE, mapParameters, mapHeaders), consumer);
    }

    /**
     * 社区列表详情
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getCommunityDetailsResponse(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ORDERDETAILED, mapParameters, mapHeaders), consumer);
    }

    /**
     * 社区订单评论
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getOrderComments(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ORDERCOMMENTS, mapParameters, mapHeaders), consumer);
    }

    /**
     * 新增评论
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getCommon(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_GETCOMMON, mapParameters, mapHeaders), consumer);
    }

    /**
     * //评论列表
     * private static String GENERAL_COMMENTSLISTS = "/api/v1/commentslists";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getCommentsLists(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_COMMENTSLISTS, mapParameters, mapHeaders), consumer);
    }

    /**
     * 新增点赞
     * private static String GENERAL_GETLIKE = "/api/v1/getlike";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getCommentsLike(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_GETLIKE, mapParameters, mapHeaders), consumer);
    }

    /**
     * 商户统计
     * private static String GENERAL_CONUT = "/api/v1/conut";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getCount(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_CONUT, mapParameters, mapHeaders), consumer);
    }


    /**
     * 新增访客
     * private static String GENERAL_ADDVISIT = "/api/v1/addvisit";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getAddVisit(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ADDVISIT, mapParameters, mapHeaders), consumer);
    }

    /**
     * 新增联系
     * private static String GENERAL_ADDCONTACT = "/api/v1/addcontact";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getAddContact(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ADDCONTACT, mapParameters, mapHeaders), consumer);
    }

    /**
     * 详情判断是否能领取红包
     * private static String GENERAL_ACTIONCHECKBONUS = "/api/v1/actioncheckbonus";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getActionCheckBonus(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_ACTIONCHECKBONUS, mapParameters, mapHeaders), consumer);
    }

    /**
     * 领取红包
     * private static String GENERAL_WATCHBONUS = "/api/v1/watchbonus";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getWatchBonus(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_WATCHBONUS, mapParameters, mapHeaders), consumer);
    }


    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //以下是海康 接口
    //根据人员唯一字段获取人员详细信息
    private static String HK_INTERFACE_VISITOR_PERSONINFO = "/artemis/visitor/personInfo";
    //查询二维码
    private static String HK_INTERFACE_BAR_CODE = "/artemis/persionHouse/barCode";
    //访客预约
    private static String HK_VISITOR_APPOINTMENT = "/artemis/visitor/appointment";
    //访客查询来访记录
    private static String HK_VISITING_RECORDS = "/artemis/visitor/visitingRecords";
    //访客预约记录查询
    private static String HK_APPOINTMENT_RECORDS = "/artemis/visitor/appointmentRecords";

    //分页获取房屋信息
    private static String HK_PERSION_HOUSE_HOUSES = "/artemis/persionHouse/houses";
    //分页获取人房信息
    private static String HK_PERSION_HOUSE_RELATION = "/artemis/persionHouse/relation";
    //生成访客动态二维码内容
    private static String HK_VISITOR_AUTH_QCODE = "/artemis/visitor/authQcode";
    //添加人脸分组
    private static String HK_ADDFACE_GROUP = "/artemis/face/addFaceGroup";
    //查询人脸分组
    private static String HK_QUERY_FACE_GROUP = "/artemis/face/queryFaceGroup";
    //人脸评分
    private static String HK_FACE_CHECK_FACE = "/artemis/face/checkFace";
    //添加人脸
    private static String HK_FACE_ADD_FACE = "/artemis/face/addFace";
    //修改人脸
    private static String HK_FACE_UPDATE_FACE = "/artemis/face/updateFace";
    //查询人脸
    private static String HK_FACE_QUERY_FACE = "/artemis/face/queryFace";
    //获取根区域
    private static String HK_REGION_REGION_ROOT = "/artemis/region/regionRoot";
    //查询区域列表v2
    private static String HK_REGION_REGION_LIST = "/artemis/region/regionList";
    //根据区域编号获取下一级区域列表v2
    private static String HK_REGION_NEXT_REGION = "/artemis/region/nextRegion";
    //添加人员
    private static String HK_PERSION_HOUSE_SINGLE_ADD = "/artemis/persionHouse/singleAdd";
    //分页获取区域列表V1.0
    private static String HK_REGION_QUERY_REGION_PAGE = "/artemis/region/queryRegionPage";
    //取单个区域信息
    private static String HK_REGION_REGION_INFO = "/artemis/region/regionInfo";
    //添加入住信息
    private static String HK_PERSION_HOUSE_ADD = "/artemis/persionHouse/add";
    //删除入住信息
    private static String HK_PERSION_HOUSE_DELETE = "/artemis/persionHouse/delete";

    //查询入住信息
    private static String HK_PERSION_HOUSE_QUERY = "/artemis/persionHouse/query";


    /**
     * 查询入住信息
     * private static String HK_PERSION_HOUSE_QUERY = "/artemis/persionHouse/query";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getPersionHouseQuery(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_PERSION_HOUSE_QUERY, mapParameters), consumer);
    }

    /**
     * 删除入住信息
     * private static String HK_PERSION_HOUSE_DELETE = "/artemis/persionHouse/delete";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getPersionHouseDelete(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_PERSION_HOUSE_DELETE, mapParameters), consumer);
    }

    /**
     * 添加入住信息
     * private static String HK_PERSION_HOUSE_ADD = "/artemis/persionHouse/add";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getPersionHouseAdd(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_PERSION_HOUSE_ADD, mapParameters), consumer);
    }

    /**
     * 取单个区域信息
     * private static String HK_REGION_REGION_INFO = "/artemis/region/regionInfo";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegioRegionInfo(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_REGION_REGION_INFO, mapParameters), consumer);
    }

    /**
     * 分页获取区域列表V1.0
     * private static String HK_REGION_QUERY_REGION_PAGE = "/artemis/region/queryRegionPage";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegionQueryRegionPage(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_REGION_QUERY_REGION_PAGE, mapParameters), consumer);
    }

    /**
     * 添加人员
     * private static String HK_PERSION_HOUSE_SINGLE_ADD = "/artemis/persionHouse/singleAdd";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getPersionHouseSingleAdd(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_PERSION_HOUSE_SINGLE_ADD, mapParameters), consumer);
    }

    /**
     * 根据区域编号获取下一级区域列表v2
     * private static String HK_REGION_NEXT_REGION = "/artemis/region/nextRegion";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegionNextRegion(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_REGION_NEXT_REGION, mapParameters), consumer);
    }

    /**
     * 查询区域列表v2
     * private static String HK_REGION_REGION_LIST = "/artemis/region/regionList";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegionRegionList(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_REGION_REGION_LIST, mapParameters), consumer);
    }

    /**
     * 获取根区域
     * private static String HK_REGION_REGION_ROOT = "/artemis/region/regionRoot";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegionRegionRoot(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_REGION_REGION_ROOT, mapParameters), consumer);
    }

    /**
     * 查询人脸
     * private static String HK_FACE_QUERY_FACE = "/artemis/face/queryFace";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getFaceQueryFace(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_FACE_QUERY_FACE, mapParameters), consumer);
    }

    /**
     * 添加人脸
     * private static String HK_FACE_ADD_FACE = "/artemis/face/addFace";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getFaceAddFace(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_FACE_ADD_FACE, mapParameters), consumer);
    }
/**
     * 修改人脸
     * private static String HK_FACE_ADD_FACE = "/artemis/face/addFace";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getFaceUpdateFace(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_FACE_UPDATE_FACE, mapParameters), consumer);
    }

    /**
     * 人脸评分
     * private static String HK_FACE_CHECK_FACE = "/artemis/face/checkFace";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getFaceCheckFace(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_FACE_CHECK_FACE, mapParameters), consumer);
    }

    /**
     * 查询人脸分组
     * private static String HK_QUERY_FACE_GROUP = "/artemis/face/queryFaceGroup";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getQueryFaceGroup(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_QUERY_FACE_GROUP, mapParameters), consumer);
    }

    /**
     * 添加人脸分组
     * private static String HK_ADDFACE_GROUP = "/artemis/face/addFaceGroup";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getAddFaceGroup(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_ADDFACE_GROUP, mapParameters), consumer);
    }

    /**
     * 生成访客动态二维码内容
     * private static String HK_VISITOR_AUTH_QCODE = "/artemis/visitor/authQcode";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getVisitorAuthQcode(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_VISITOR_AUTH_QCODE, mapParameters), consumer);
    }

    /**
     * 分页获取人房信息
     * private static String HK_PERSION_HOUSE_RELATION = "/artemis/persionHouse/relation";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getPersionHouseRelation(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_PERSION_HOUSE_RELATION, mapParameters), consumer);
    }

    /**
     * 分页获取房屋信息
     * private static String HK_PERSION_HOUSE = "/artemis/persionHouse/houses";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getPersionHouse(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_PERSION_HOUSE_HOUSES, mapParameters), consumer);
    }

    /**
     * 根据人员唯一字段获取人员详细信息
     * private static String HK_INTERFACE_VISITOR_PERSONINFO = "/artemis/visitor/personInfo";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getVisitorPersonInfo(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_INTERFACE_VISITOR_PERSONINFO, mapParameters), consumer);
    }

    /**
     * 查询二维码
     * private static String HK_INTERFACE_BAR_CODE = "/artemis/persionHouse/barCode";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getBarCode(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_INTERFACE_BAR_CODE, mapParameters), consumer);
    }

    /**
     * 访客预约
     * private static String HK_VISITOR_APPOINTMENT = "/artemis/visitor/appointment";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getVisitorAppointment(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_VISITOR_APPOINTMENT, mapParameters), consumer);
    }

    /**
     * 查询访客来访记录
     * private static String HK_VISITING_RECORDS = "/artemis/visitor/visitingRecords";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getVisitingRecords(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_VISITING_RECORDS, mapParameters), consumer);
    }

    /**
     * 访客预约记录查询
     * private static String HK_APPOINTMENT_RECORDS = "/artemis/visitor/appointmentRecords";
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getAppointmentRecords(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHk
                (HK_ROOT_URL + HK_APPOINTMENT_RECORDS, mapParameters), consumer);
    }
}
