package io.qmeta.wps.auth;

import io.qmeta.wps.auth.request.RpcAuthorizedRequest;
import io.qmeta.wps.auth.response.GetAccessTokenResponse;
import io.qmeta.wps.auth.request.RefreshTokenRequest;
import io.qmeta.wps.auth.request.RpcTokenRequest;
import io.qmeta.wps.auth.response.RpcAuthorizeResponse;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * 1. 获取用户Code
 * 2. 然后调用getAccessToken, 获取AccessToken
 * 3. 定期更新RefreshToken
 * 4. 获取rpc_token, 使用getRpcToken
 * 5. 检查Rpc授权，使用checkRpcAuthorization
 */
public interface WpsAuthApi {

    @GET("oauthapi/v2/token")
    Call<GetAccessTokenResponse> getAccessToken(@Query("appkey") String appKey,
                                                @Query("appid") String appid,
                                                @Query("code") String code);

    @POST("oauthapi/v2/token")
    Call<GetAccessTokenResponse> refreshToken(@Body RefreshTokenRequest request);


    @POST("oauthapi/v2/rpc/token")
    Call<GetAccessTokenResponse> getRpcToken(@Body RpcTokenRequest request);

    @POST("oauthapi/v2/rpc/scope/authorize")
    Call<RpcAuthorizeResponse> checkRpcAuthorization(@Header(value = "Cookie") String cookie,
                                                     @Body RpcAuthorizedRequest request);


}
