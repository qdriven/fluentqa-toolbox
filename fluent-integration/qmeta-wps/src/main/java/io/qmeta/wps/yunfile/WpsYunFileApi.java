package io.qmeta.wps.yunfile;

import io.qmeta.wps.yunfile.request.CommitFileRequest;
import io.qmeta.wps.yunfile.request.CreateFolderRequest;
import io.qmeta.wps.yunfile.request.FileOperationRequest;
import io.qmeta.wps.yunfile.request.UploadUrlDataRequest;
import io.qmeta.wps.yunfile.response.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;


public interface WpsYunFileApi {
    //TODO: 需要开通权限
    @GET("oauthapi/v2/appfile/remaining")
    Call<RemainingSpaceResponse> remainingSpace(@Query("access_token") String access_token, @Query("appid") String appid);

    @POST("oauthapi/v3/app/folders/create")
    Call<CreateFolderResponse> createFolder(@Body CreateFolderRequest request);

    @POST("oauthapi/v3/app/files/create")
    Call<CreateCommitFileResponse> createFile(@Body CreateFolderRequest request);

    @PUT("oauthapi/v4/app/files/upload/request")
    Call<UploadUrlDataResponse> requestFileUpload(@Body UploadUrlDataRequest request);

    @POST("oauthapi/v3/app/files/upload/commit")
    Call<CreateCommitFileResponse> commitFile(@Body CommitFileRequest request);

    @PUT("oauthapi/v3/app/files/upload/update")
    Call<CreateCommitFileResponse> updateFile(@Body CommitFileRequest request);

    @GET("oauthapi/v3/app/files/link")
    Call<SharedLinkResponse> getSharedLink(@QueryMap Map<String,Object> createShareLinkRequest);

    @GET("oauthapi/v3/app/files/download/url")
    Call<DownloadUrlResponse> getDownloadUrl(@QueryMap Map<String,Object> fileRequest);

    /**
     * how to upload file to wps cloud
     * 1. requestFileUpload to get download url
     * 2. PUT download url with all headers from getRequestFileUpload
     * 3. commitFile to confirm uploaod file
     * 4. updateFile to update file
     */

    //not used yet
    @GET("oauthapi/v3/app/files/list")
    Call<FileListResponse> getFileList(@QueryMap Map<String, Object> queryMap);

    @PUT("oauthapi/v3/app/files/rename")
    Call<FileOperationResponse> renameFile(@Body FileOperationRequest request);

    @POST("oauthapi/v3/app/files/copy")
    Call<FileOperationResponse> copyFiles(@Body FileOperationRequest request);

    @POST("oauthapi/v3/app/files/move")
    Call<FileOperationResponse> moveFiles(@Body FileOperationRequest request);

    @DELETE("oauthapi/v3/app/files/delete")
    Call<FileOperationResponse> deleteFiles(@Body FileOperationRequest request);


    @GET("oauthapi/v3/app/files/searchbyname")
    Call<FileListResponse> searchFileByName(@Body FileOperationRequest request);

    @GET("oauthapi/v3/app/files/searchbybycontent")
    Call<FileListResponse> searchFileByContent(@Body FileOperationRequest request);

    @GET("oauthapi/v2/file/info")
    Call<FileListResponse> getFileInfo(@QueryMap Map queryMap);

    @GET("oauthapi/v2/file/link/info")
    Call<SharedLinkInfoResponse> getFileSharedLinkInfo(@QueryMap Map<String, Object> createSharedLinkRequest);

    /**
     * 没有返回直接到页面，这个方法不能使用
     * @param openFileId
     * @param appid
     * @param sameOpenFileId
     * @return
     */
    @Deprecated
    @GET("oauthapi/v2/file/preview/{open_fileid}")
    Call<FileListResponse> previewFile(@Path("open_fileid") String openFileId, @Query("appid")String appid,
                                       @Query("open_fileid")String sameOpenFileId);

}
