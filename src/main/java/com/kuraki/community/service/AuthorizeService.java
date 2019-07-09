package com.kuraki.community.service;

import com.kuraki.community.dto.AccessTokenDTO;
import com.kuraki.community.dto.GithubUser;
import com.kuraki.community.mapper.UserMapper;
import com.kuraki.community.model.User;
import com.kuraki.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class AuthorizeService {

    @Value("${community.github.client.id}")
    private String clientId;

    @Value("${community.github.client.secret}")
    private String clientSecret;

    @Value("${community.github.authority.redirect-uri}")
    private String redirectUri;

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    public String callback(String code, String state, HttpServletRequest request) {
        AccessTokenDTO dto = new AccessTokenDTO();
        dto.setClientId(clientId);
        dto.setClientSecret(clientSecret);
        dto.setCode(code);
        dto.setRedirectUri(redirectUri);
        dto.setState(state);
        String accessToken = githubProvider.getAccessToken(dto);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        System.out.println("githubUser = " + githubUser);
        if (githubUser != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
            request.getSession().setAttribute("user", githubUser);
        }
        return null;
    }
}
