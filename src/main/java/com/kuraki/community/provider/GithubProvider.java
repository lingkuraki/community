package com.kuraki.community.provider;

import com.alibaba.fastjson.JSON;
import com.kuraki.community.dto.AccessTokenDTO;
import com.kuraki.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public static final MediaType MEDIATYPE = MediaType.get("application/json; charset=utf-8");

    @Autowired
    private OkHttpClient okHttpClient;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        String url = "https://github.com/login/oauth/access_token";
        String json = JSON.toJSONString(accessTokenDTO);
        RequestBody body = RequestBody.create(MEDIATYPE, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            String string = response.body().string();
            String accessToken = string.split("&")[0].split("=")[1];
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getGithubUser(String accessToken) {
        String url = "https://api.github.com/user?access_token=" + accessToken;
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String json = response.body().string();
            GithubUser githubUser = JSON.parseObject(json, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
