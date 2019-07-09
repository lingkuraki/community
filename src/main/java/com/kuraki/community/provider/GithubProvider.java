package com.kuraki.community.provider;

import com.alibaba.fastjson.JSON;
import com.kuraki.community.dto.AccessTokenDTO;
import com.kuraki.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    private static final MediaType MEDIATYPE = MediaType.get("application/json; charset=utf-8");

    @Value("${community.github.access-token.url}")
    private String getAccessTokenUrl;

    @Value("${community.github.user-information}")
    private String getGithubUserInfoUrl;

    @Autowired
    private OkHttpClient okHttpClient;

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        String json = JSON.toJSONString(accessTokenDTO);
        RequestBody body = RequestBody.create(MEDIATYPE, json);
        Request request = new Request.Builder()
                .url(getAccessTokenUrl)
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
        getGithubUserInfoUrl = getGithubUserInfoUrl + accessToken;
        Request request = new Request.Builder().url(getGithubUserInfoUrl).build();
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
