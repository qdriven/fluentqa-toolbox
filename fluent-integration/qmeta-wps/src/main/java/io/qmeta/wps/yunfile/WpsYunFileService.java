package io.qmeta.wps.yunfile;

import cn.hutool.core.bean.BeanUtil;
import io.qmeta.wps.common.WpsApiKeyHolder;
import io.qmeta.wps.common.http.OkHttpClientWrapper;
import io.qmeta.wps.common.http.RequestBody;
import io.qmeta.wps.common.http.Response;
import io.qmeta.wps.common.http.RetrofitWrapper;
import io.qmeta.wps.util.FileUtils;
import io.qmeta.wps.yunfile.exception.YunException;
import io.qmeta.wps.yunfile.exception.YunHttpIOException;
import io.qmeta.wps.yunfile.request.*;
import io.qmeta.wps.yunfile.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Call;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class WpsYunFileService {
    private final WpsApiKeyHolder apiKeyHolder;
    private WpsYunFileApi wpsYunFileApi;
    @Value("${wps.tempFileFolder}")
    private String tempFileFolder;

    /**
     * 初始化wps yun file api client
     *
     * @param apiKeyHolder
     */
    public WpsYunFileService(WpsApiKeyHolder apiKeyHolder) {
        this.apiKeyHolder = apiKeyHolder;
        this.wpsYunFileApi = RetrofitWrapper.
                getRetrofit(this.apiKeyHolder.getOpenApiServer())
                .create(WpsYunFileApi.class);
    }

    /**
     * 获取剩余空间
     *
     * @return
     */
    public RemainingSpaceResponse getRemainingSpace() {
        Call<RemainingSpaceResponse> request = wpsYunFileApi.
                remainingSpace(apiKeyHolder.getAccessToken()
                        , apiKeyHolder.getAppId());
        return RetrofitWrapper.send(request).body();
    }

    /**
     * Create Folder
     * 默认为目目录下最后一层
     *
     * @return
     */
    private CreateFolderResponse createFolder(String folderName) {

        String openParentId = "0"; //默认父目录
        Call<CreateFolderResponse> request = wpsYunFileApi.createFolder(
                CreateFolderRequest.builder().appid(this.apiKeyHolder.getAppId())
                        .open_parentid(openParentId).access_token(apiKeyHolder.getAccessToken())
                        .name(folderName).build()
        );
        return RetrofitWrapper.send(request).body();
    }

    public CreateCommitFileResponse createOrUpdateFile(MultipartFile file, String openFileId) throws IOException, YunException {
        String path = this.tempFileFolder + file.getOriginalFilename();
        File uploadedFile = new File(path);
        org.apache.commons.io.FileUtils.copyToFile(file.getInputStream(), uploadedFile);
        return uploadFile("0", uploadedFile, openFileId);
    }

    /**
     * 1. 请求上传文件获取上传文件的url
     * 2. 上传文件到这个url，这个路径在金山云上面
     * 2. 提交上传的文件返回open_fileid
     */
    public CreateCommitFileResponse uploadFile(String parentId, File file, String openFileId) throws YunException {
        String fileName = file.getName();
        String sha1 = FileUtils.getFileSHA1(file);
        long size = FileUtils.size(file);
        UploadUrlDataResponse response = RetrofitWrapper.send(
                wpsYunFileApi.requestFileUpload(
                        UploadUrlDataRequest.builder()
                                .access_token(apiKeyHolder.getAccessToken())
                                .appid(this.apiKeyHolder.getAppId())
                                .open_parentid(parentId)
                                .name(fileName).sha1(sha1)
                                .size(size).build()
                )
        ).body();
        assert response != null;
        log.info("upload file url is " + response.getUploadUrl());
        String etag = getUploadFileETag(response, file);
        log.info("upload etag is " + etag);
        if (openFileId == null) {
            return RetrofitWrapper.send(wpsYunFileApi.commitFile(
                    CommitFileRequest.builder()
                            .access_token(apiKeyHolder.getAccessToken())
                            .appid(this.apiKeyHolder.getAppId())
                            .etag(etag)
                            .open_parentid(parentId)
                            .name(fileName).sha1(sha1).size(size)
                            .build()
            )).body();
        } else {
            return RetrofitWrapper.send(wpsYunFileApi.updateFile(
                    CommitFileRequest.builder()
                            .access_token(apiKeyHolder.getAccessToken())
                            .appid(this.apiKeyHolder.getAppId())
                            .etag(etag)
                            .open_parentid(parentId)
                            .open_fileid(openFileId)
                            .name(fileName).sha1(sha1).size(size)
                            .build()
            )).body();
        }

    }

    private String getUploadFileETag(UploadUrlDataResponse response, File file) throws YunHttpIOException {
        OkHttpClientWrapper wrapper = new OkHttpClientWrapper();
        RequestBody requestBody = new RequestBody(file);
        Response uploadFileResp = wrapper.url(response.getUploadUrl())
                .put(requestBody).addHeaders(response.getHeaders())
                .execute();
        return uploadFileResp.getHeader("ETag").replaceAll("\"", "");
    }

    public String getReadSharedLinkUrl(String openFileId) {
        return getSharedLinkUrl(openFileId, "read");
    }

    public String getWriteSharedLinkUrl(String openFileId) {
        return getSharedLinkUrl(openFileId, "write");
    }

    public String getSharedLinkUrl(String openFileId, String permission) {
        Map<String, Object> requestMap = BeanUtil.beanToMap(
                CreateShareLinkRequest.builder()
                        .access_token(apiKeyHolder.getAccessToken())
                        .appid(this.apiKeyHolder.getAppId())
                        .open_fileid(openFileId)
                        .permission(permission)
                        .period(2592000)//30天
                        .build()
        );
        SharedLinkResponse response = RetrofitWrapper.sendAndReturnBody(wpsYunFileApi.getSharedLink(requestMap));
        return response.getLink_url();
    }

    /**
     * download url 10分钟有效
     *
     * @param openFileId
     * @return
     */
    public String getDownloadUrl(String openFileId) {
        Map<String, Object> reqMap = BeanUtil.beanToMap(FileRequest.builder()
                .access_token(apiKeyHolder.getAccessToken())
                .appid(apiKeyHolder.getAppId()).open_fileid(openFileId).build());
        DownloadUrlResponse response = RetrofitWrapper.sendAndReturnBody(wpsYunFileApi.getDownloadUrl(reqMap));
        return response.getUrl();
    }
}
