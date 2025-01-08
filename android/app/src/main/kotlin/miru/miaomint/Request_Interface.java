package miru.miaomint;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface Request_Interface {
    /**
     * 获取文稿列表
     * @param manuscript
     * @return
     */
    @GET("ggetapk?")
    Call<Data> ggetapk(@QueryMap Map<String, Object> query);
    // @GET注解的作用:采用Get方法发送网络请求

    /**
     * 获取手机验证码
     * @param query
     * @return
     */
    @POST("pgetapk?")
    Call<Data> pgetapk(@QueryMap Map<String, String> query);
}

