package com.xiaoanjujia.home.composition.me.certification_merchants;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.multi.view.MultiImageSelectorActivity;
import com.xiaoanjujia.common.util.CommonUtil;
import com.xiaoanjujia.common.util.LogUtilsxp;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.tool.Api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xiepeng
 */
@Route(path = "/CertificationMerchantsActivity/CertificationMerchantsActivity")
public class CertificationMerchantsActivity extends BaseActivity implements CertificationMerchantsContract.View {
    @Inject
    CertificationMerchantsPresenter mPresenter;
    private static final String TAG = "CertificationMerchantsActivity";
    @BindView(R2.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R2.id.main_title_back)
    ImageView mainTitleBack;
    @BindView(R2.id.main_title_text)
    TextView mainTitleText;
    @BindView(R2.id.main_title_right)
    ImageView mainTitleRight;
    @BindView(R2.id.main_title_container)
    LinearLayout mainTitleContainer;
    @BindView(R2.id.edit_merchant_name)
    EditText editMerchantName;
    @BindView(R2.id.edit_company_name)
    EditText editCompanyName;
    @BindView(R2.id.edit_merchant_phone)
    EditText editMerchantPhone;
    @BindView(R2.id.edit_merchant_code)
    EditText editMerchantCode;
    @BindView(R2.id.uploading_company_certificate_gd)
    GridView uploadingCompanyCertificateGd;
    @BindView(R2.id.uploading_company_certificate_ll)
    LinearLayout uploadingCompanyCertificateLl;
    @BindView(R2.id.uploading_special_certificate_gd)
    GridView uploadingSpecialCertificateGd;
    @BindView(R2.id.uploading_special_certificate_ll)
    LinearLayout uploadingSpecialCertificateLl;

    //    存放加号的list
    List<Bitmap> jiaListBitMap;
    private ProducGridAdapter adapter;
    private ArrayList<String> mSelectPath;
    //启动activity 资质拍照的请求码
    private static final int REQUEST_IMAGE = 4;
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    //   存放所有图片的list(不包括加号)
    List<Bitmap> listBitMap;
    List<Bitmap> listBitMapTemp;
    private List<String> aptitudePatnList;//资质图片路径

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_merchants);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();
        initData();
        myOnclick();
    }

    private void initView() {
        DaggerCertificationMerchantsActivityComponent.builder()
                .appComponent(getAppComponent())
                .certificationMerchantsPresenterModule(new CertificationMerchantsPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        //      放+的list
        jiaListBitMap = new ArrayList<Bitmap>();
        listBitMap = new ArrayList<Bitmap>();
        aptitudePatnList = new ArrayList<>();//资质图片路径
    }

    private void initData() {

        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("ACTION", "I002");


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(LoginResponse loginResponse) {
        try {
            int code = loginResponse.getStatus();
            String msg = loginResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LoginResponse.DataBean data = loginResponse.getData();


            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }

    }


    @Override
    public void showProgressDialogView() {
        showProgressDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hiddenProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destory();
        }
    }

    /**
     * 点击事件
     */
    private void myOnclick() {

        uploadingCompanyCertificateGd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == jiaListBitMap.size() - 1) {
                    setImgMode();
                }
            }
        });
    }

    //  相册，相机模式设置
    private void setImgMode() {
        Intent intent = new Intent(CertificationMerchantsActivity.this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        // 选择模式，1表示多选,0表示单选
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, 1);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    /**
     * 绑定加号
     */
    private void bindGridView() {
        //              把加号放到list中并绑定，到gridView中
        jiaListBitMap = bindJia();
        hengping();
        //      把数据绑定一下
        adapter = new ProducGridAdapter(jiaListBitMap, this);
        uploadingCompanyCertificateGd.setAdapter(adapter);
    }

    private void hengping() {
        ViewGroup.LayoutParams params = uploadingCompanyCertificateGd.getLayoutParams();
        // dishtype，welist为ArrayList
        int dishtypes = jiaListBitMap.size();
        //      图片之间的距离
        params.width = CommonUtil.dip2px(mContext, 90) * dishtypes;
        Log.d("看看这个宽度", params.width + "" + jiaListBitMap.size());
        uploadingCompanyCertificateGd.setLayoutParams(params);
        //设置列数为得到的list长度
        uploadingCompanyCertificateGd.setNumColumns(jiaListBitMap.size());
    }

    /**
     * 拍照和相册返回的数据
     */

    //  绑定加号
    private List<Bitmap> bindJia() {
        //              把加号放到list中并绑定，到gridView中
        InputStream is = getResources().openRawResource(R.raw.icon_addpic_unfocused1);

        Bitmap myBitmapJia = BitmapFactory.decodeStream(is);
        jiaListBitMap.add(myBitmapJia);
        return jiaListBitMap;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {

            if (resultCode == RESULT_OK) {
                listBitMap.clear();
                aptitudePatnList.clear();
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                //              把数据转化成Bitmap,放选中的图片
                listBitMapTemp = new ArrayList<Bitmap>();
                for (String path : mSelectPath) {
                    if (aptitudePatnList.size() > 9) {

                    } else {
                        Bitmap bitmap = decodeSampledBitmapFromFd(path, 400, 400);
                        //                  把bitmap放进去list中
                        listBitMapTemp.add(bitmap);
                        //                    将图片保存到sd卡中 得到图片名 放在集合中
                        String aptitudePath = saveImageToGallery(mContext, bitmap);

                        LogUtilsxp.e2(TAG, "aptitudePath:" + aptitudePath);
                        aptitudePatnList.add(aptitudePath);
                    }


                }
                listBitMap.addAll(listBitMapTemp);
                if (listBitMap.size() == 10) {
                    listBitMap.remove(9);
                    aptitudePatnList.remove(9);

                }
                if (listBitMap.size() == 11) {
                    listBitMap.remove(9);
                    listBitMap.remove(10);
                    aptitudePatnList.remove(9);
                    aptitudePatnList.remove(10);
                }
                if (listBitMap.size() == 12) {
                    listBitMap.remove(9);
                    listBitMap.remove(10);
                    listBitMap.remove(11);
                    aptitudePatnList.remove(9);
                    aptitudePatnList.remove(10);
                    aptitudePatnList.remove(11);
                }
                if (listBitMap.size() == 13) {
                    listBitMap.remove(9);
                    listBitMap.remove(10);
                    listBitMap.remove(11);
                    listBitMap.remove(12);
                    aptitudePatnList.remove(9);
                    aptitudePatnList.remove(10);
                    aptitudePatnList.remove(11);
                    aptitudePatnList.remove(12);
                }


                LogUtilsxp.e2(TAG, "listBitMap.size()" + listBitMap.size());
                //              清空所有数据
                jiaListBitMap.clear();
                bindJia();
                //              把加号和图片放一起
                jiaListBitMap.addAll(0, listBitMap);
                //              当选择9张图片时，删除加号
                if (jiaListBitMap.size() == 10) {
                    //                  第10张图片下标是9
                    jiaListBitMap.remove(9);
                }
                if (jiaListBitMap.size() == 11) {
                    //                  第10张图片下标是9
                    jiaListBitMap.remove(9);
                    jiaListBitMap.remove(10);
                }
                if (jiaListBitMap.size() == 12) {
                    //                  第10张图片下标是9
                    jiaListBitMap.remove(9);
                    jiaListBitMap.remove(10);
                    jiaListBitMap.remove(11);
                }
                if (jiaListBitMap.size() == 13) {
                    //                  第10张图片下标是9
                    jiaListBitMap.remove(9);
                    jiaListBitMap.remove(10);
                    jiaListBitMap.remove(11);
                    jiaListBitMap.remove(12);
                }
                hengping();
                //      把数据绑定一下
                adapter.notifyDataSetChanged();
            }
        }
    }
    // 从sd卡上加载图片
    public static Bitmap decodeSampledBitmapFromFd(String pathName,
                                                   int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }

    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
                                            int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "putBird");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";

        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        //        try {
        //            MediaStore.Images.Media.insertImage(context.getContentResolver(),
        //                    file.getAbsolutePath(), fileName, null);
        //        } catch (FileNotFoundException e) {
        //            e.printStackTrace();
        //        }
        // 最后通知图库更新
        //        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return fileName;
    }

}
