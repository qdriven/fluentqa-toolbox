package io.qmeta.wps.auth;

import cn.hutool.core.date.LocalDateTimeUtil;
import io.qmeta.wps.auth.exception.AuthException;
import io.qmeta.wps.auth.response.GetAccessTokenResponse;
import io.qmeta.wps.common.WpsApiKeyHolder;
import io.qmeta.wps.common.http.RetrofitWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class WpsAuthService {

    private final String LATEST_KEY = "LATEST";
    private Map<String, Response<GetAccessTokenResponse>> accessTokenMap = new ConcurrentHashMap<>();
    private final WpsApiKeyHolder apiKeyHolder;
    private WpsAuthApi wpsAuthClient;

    public WpsAuthService(WpsApiKeyHolder apiKeyHolder) {
        this.apiKeyHolder = apiKeyHolder;
        this.wpsAuthClient = RetrofitWrapper.getRetrofit(this.apiKeyHolder.getOpenApiServer())
                .create(WpsAuthApi.class);
    }

    @SneakyThrows
    public String getRequestUrl() throws AuthException {
        String allScope = WpsOpenAPIScope.allScopes();
        String redirectUrl = "http://localhost:8080/code/callback";
        String requestUrl = apiKeyHolder.getOpenApiServer()
                + "/oauthapi/v2/authorize?response_type=code&appid=" + apiKeyHolder.getAppId() + "&autologin=true&redirect_uri=" + redirectUrl + "&scope=" + allScope + "&state=STATE";
        log.info("the request url is "+requestUrl);
        return requestUrl;
    }

    /**
     * 1. Async Task to Get Code
     * 2. Async Task to Refresh Token
     * 3. Get AccessToken
     */

    public void asyncGetCode() {
    }

    private void updateAccessTokenMap(Response response) {
        this.accessTokenMap.put(LATEST_KEY, response);
        this.accessTokenMap.put(String.valueOf(LocalDateTimeUtil.now().getNano()), response);
    }

    //已经成功
    public String getAccessToken() {
        Call<GetAccessTokenResponse> request = this.wpsAuthClient.getAccessToken(
                this.apiKeyHolder.getAppKey(), this.apiKeyHolder.getAppId(),
                this.apiKeyHolder.getCode()
        );
        Response<GetAccessTokenResponse> response;
        try {
            response = request.execute();
            updateAccessTokenMap(response);
            assert response.body() != null;
            return response.body().getToken().getAccess_token();
        } catch (IOException e) {
            throw new AuthException(e);
        }

    }

    public void refreshToken() {
    }
}
