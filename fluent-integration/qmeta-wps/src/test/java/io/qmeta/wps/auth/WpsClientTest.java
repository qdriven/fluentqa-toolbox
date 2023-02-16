package io.qmeta.wps.auth;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import io.qmeta.wps.yunfile.WpsYunFileService;
import io.qmeta.wps.yunfile.exception.YunException;
import io.qmeta.wps.yunfile.response.CreateCommitFileResponse;
import io.qmeta.wps.yunfile.response.RemainingSpaceResponse;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
class WpsClientTest {

    @Autowired
    WpsAuthService service;

    @Autowired
    WpsYunFileService wpsYunFileService;


    @Test
    //visit: https://openapi.wps.cn/oauthapi/v2/authorize?response_type=code&appid=SX20200920ASZYIR&autologin=true&redirect_uri=http://www.baidu.com&scope=user_info&state=STATE
    // https://openapi.wps.cn/oauthapi/v2/authorize?response_type=code&appid=SX20200920ASZYIR&autologin=true&redirect_uri=http://www.baidu.com&scope=user_info,cloud_file,file_selector&state=STATE

    //locahost
    //https://openapi.wps.cn/oauthapi/v2/authorize?response_type=code&appid=SX20200920ASZYIR&autologin=true&redirect_uri=http://localhost:8080/code/callback&scope=user_info,cloud_file,file_selector&state=STATE
    @Ignore
    void testGetCode() throws IOException {
        String requestUrl = service.getRequestUrl();
        WebConnectionHtmlUnitDriver driver = new WebConnectionHtmlUnitDriver(BrowserVersion.CHROME);
        System.out.println(driver.getWebClient());
        driver.getWebClient().getOptions().setJavaScriptEnabled(true);
        driver.getWebClient().getOptions().setRedirectEnabled(true);
        driver.getWebClient().getCookieManager().setCookiesEnabled(true);//设置cookie是否可用
        Page p = driver.getWebClient().getPage(requestUrl);

        assert p != null;
    }

    @Test
//    {"result":0,"token":{"appid":"SX20200920ASZYIR","expires_in":86400,"access_token":"f8451c5d0e45fea5a18c630b9dc1f6f7","refresh_token":"8ab9b9644effde498608590cbeb42e0a","openid":"5LqsFW2B-8-o1IaZ1Zv6VQ"}}
        //access token: dca5bbcff71e3a011bc66bc49808c17a
    void testGetAccessToken() {
        String accessToken = service.getAccessToken();
        System.out.println(accessToken);
    }

    @Test
    public void testGetRemainSpace() {
        RemainingSpaceResponse rem = wpsYunFileService.getRemainingSpace();
        System.out.println(rem);
    }

    @Test
    public void testUploadFile() throws JSONException, YunException {
        String filePath = "/Users/Patrick/workspace/wip/qmeta-supplement/TestDocs.docx";
//        String filePath = "/Users/Patrick/workspace/wip/qmeta-supplement/README.md";
        String openFileId = "op2fM8IT2KJcWwCrJ1QAJCvw";
        String etag = "56e0b392d54fbb627f1f171e6418590d";
        CreateCommitFileResponse response = wpsYunFileService.uploadFile("0", new File(filePath),
                null);
        System.out.println(response);
        System.out.println("openfileid is " + response.getData().getOpen_fileid());
        System.out.println("openfilename is " + response.getData().getFile_name());
    }

//    2020-10-17 17:26:08.911  INFO 30590 --- [main] io.qmeta.wps.yunfile.WpsYunFileService   : upload file url is https://yun.wpscdn.cn/temp_487969732_352d292f81116f725093a2377fd7991c
//            2020-10-17 17:26:09.186  INFO 30590 --- [main] io.qmeta.wps.yunfile.WpsYunFileService   : upload etag is 3bc9c3aa5c04095373753b49ddb61370

    //fileid: opbMhH7Eu-EGgnaZucRRa42A
    //file id: opbMhH7Eu-EGgnaZucRRa42A
    @Test
    public void testGetSharedLinkUrl() {
//        String openFileId = "op2fM8IT2KJcWwCrJ1QAJCvw";
        String openFileId = "op3gUeQAnqIVKr4RbLy6GxQA";
        String sharedLinkUrl = wpsYunFileService.getWriteSharedLinkUrl(openFileId);
        System.out.println(sharedLinkUrl);
    }

    @Test
    public void getGetDownloadUrl() {
        String openFileId = "op3gUeQAnqIVKr4RbLy6GxQA";
        String sharedLinkUrl = wpsYunFileService.getDownloadUrl(openFileId);
        System.out.println(sharedLinkUrl);
    }

}