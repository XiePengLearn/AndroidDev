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
    //获取商户认证状态
    private static String GENERAL_COMEXAMINE = "/api/v1/comexamine";
    //角色类型页面
    private static String GENERAL_ROLETRPETOP = "/api/v1/roletrpetop";
    //普通物业添加日志
    private static String GENERAL_ADDPROPERTYLOG = "/api/v1/addpropertylog";
    //物业管理列表日志
    private static String GENERAL_PROPERTYLOGLISTS = "/api/v1/propertyloglists";
    //社区首页分类
    private static String GENERAL_COMCATELISTS = "/api/v1/comcatelists";
    //社区热点
    private static String GENERAL_COMMUHOTSPOT = "/api/v1/commuhotspot";
    //热点列表
    private static String GENERAL_COMHOTSPOTLIST = "/api/v1/comhotspotlist";
    //社区搜索
    private static String GENERAL_COMMUNITYSEARCH = "/api/v1/communitysearch";
    //社区列表
    private static String GENERAL_ORDERLISTS = "/api/v1/orderlists";
    //日志详情
    private static String GENERAL_LOGDETAILS = "/api/v1/logdetails";
    //审核通过
    private static String GENERAL_LOGEXAMINE = "/api/v1/logexamine";
    //审核拒绝
    private static String GENERAL_LOGREFUSE = "/api/v1/logrefuse";

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
}
