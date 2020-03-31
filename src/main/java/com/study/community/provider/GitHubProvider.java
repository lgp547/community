package com.study.community.provider;

import com.alibaba.fastjson.JSON;
import com.study.community.dto.AccessTokenDTO;
import com.study.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        /*这段功能为：将数据发送到服务。------------------------------------------------------------*/
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        //把对象(accessTokenDTO)属性转到json,需要jar包(fastjson)
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            return null;
            //e.printStackTrace();
        }
    }


    public GitHubUser getUser(String accessToken){

        /*这段功能为：该程序下载一个URL并将其内容打印为字符串------------------------*/
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //把得到的信息自动解析为这个类(把json转到类)
            GitHubUser githubUser = JSON.parseObject(string, GitHubUser.class);
            return githubUser;
        } catch (IOException e) {
            return null;
            //e.printStackTrace();
        }
    }
}
