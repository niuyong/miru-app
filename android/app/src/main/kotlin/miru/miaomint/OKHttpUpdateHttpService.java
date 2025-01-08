package miru.miaomint;

/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.hjq.toast.ToastUtils;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;
import com.xuexiang.xupdate.service.DownloadService;
import com.xuexiang.xupdate.service.OnFileDownloadListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 使用okhttp
 *
 * @author xuexiang
 * @since 2018/7/10 下午4:04
 */
public class OKHttpUpdateHttpService implements IUpdateHttpService {

    private boolean mIsPostJson;
    private NetTools netTools = new NetTools();
    private final String TAG = "OKHttpUpdateHttpService";

    public OKHttpUpdateHttpService() {
        this(false);
    }

    public OKHttpUpdateHttpService(boolean isPostJson) {
        mIsPostJson = isPostJson;
    }

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, final @NonNull Callback callBack) {
        Request_Interface request = netTools.exe();
        //对 发送请求 进行封装
        Map querymap = new HashMap();
        querymap.put("mobile", binding.phoneNumber.getText().toString());
        Call<Data<Map<String, String>>> call = request.getVerificationCode(querymap);
        call.enqueue(new retrofit2.Callback<Data<Map<String, String>>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Data<Map<String, String>>> call, Response<Data<Map<String, String>>> response) {
                callBack.onSuccess(response);
                Log.v(TAG, "回调成功" + map);
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<Data<Map<String, String>>> call, Throwable t) {
                callBack.onError(t);
                Log.e(TAG, "回调失败：" + t.getMessage() + "," + t);
            }
        });
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, final @NonNull Callback callBack) {
        Request_Interface request = netTools.exe();
        //对 发送请求 进行封装
        Map querymap = new HashMap();
        querymap.put("mobile", binding.phoneNumber.getText().toString());
        Call<Data<Map<String, String>>> call = request.getVerificationCode(querymap);
        call.enqueue(new retrofit2.Callback<Data<Map<String, String>>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Data<Map<String, String>>> call, Response<Data<Map<String, String>>> response) {
                callBack.onSuccess(response);
                Log.v(TAG, "回调成功" + map);
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<Data<Map<String, String>>> call, Throwable t) {
                callBack.onError(t);
                Log.e(TAG, "回调失败：" + t.getMessage() + "," + t);
            }
        });
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, final @NonNull DownloadCallback callback) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        DownloadInterceptor interceptor = new DownloadInterceptor(executorService, null);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        final DownloadService api = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .build()
                .create(DownloadService.class);
        new Thread(() -> {
            try {
                Response<ResponseBody> result = api.downloadWithDynamicUrl(rUrl).execute();
                File file = writeFile(filePath, result.body().byteStream());
                if (listener != null){
                    executor.execute(()->{
                        listener.onFinish(file);
                    });
                }

            } catch (IOException e) {
                if (listener != null){
                    executor.execute(()->{
                        listener.onFailed(e.getMessage());
                    });
                }
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void cancelDownload(@NonNull String url) {
    }

    private Map<String, String> transform(Map<String, Object> params) {
        Map<String, String> map = new TreeMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }


}
