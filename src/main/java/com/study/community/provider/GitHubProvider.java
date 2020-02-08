package com.study.community.provider;

import com.alibaba.fastjson.JSON;
import com.study.community.dto.AccessTokenDTO;
import com.study.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        /*这段功能为：将数据发送到服务。------------------------------------------------------------*/
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        //json基于JavaScript语言的轻量级的数据交换格式
        //这里就是accessTokenDTO，转换为json需要jar包(fastjson)
        //在这个网站搜索https://mvnrepository.com/
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);//把内容转到json
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;

        } catch (IOException e) {
//            e.printStackTrace();
        }
        return null;
        /*这段功能为：将数据发送到服务。------------------------------------------------------------*/
    }


    public GithubUser getUser(String accessToken){

        /*这段功能为：该程序下载一个URL并将其内容打印为字符串------------------------------------------------------------*/
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //把得到的信息自动解析为这个类
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//把json转到类
            return githubUser;
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return null;
        /*这段功能为：该程序下载一个URL并将其内容打印为字符串------------------------------------------------------------*/
    }
}
