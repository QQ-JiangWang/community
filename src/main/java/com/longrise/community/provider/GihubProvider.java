package com.longrise.community.provider;

import com.alibaba.fastjson.JSON;
import com.longrise.community.dto.AccessTokenDTO;

import com.longrise.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GihubProvider {
//    f07ac363dc2583607e499a7e3dd930bdaef84df9
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            System.out.println("access_token="+str);
            if(str != null){
                String[] split = str.split("&");
                String token = split[0].split("=")[1];
                System.out.println("access_token="+token);
                return token;
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+token)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            System.out.println("access_token="+str);
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        }catch (IOException e){

        }
        return null;
    }
}
