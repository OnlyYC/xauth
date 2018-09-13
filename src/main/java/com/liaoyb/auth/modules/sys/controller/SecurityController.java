package com.liaoyb.auth.modules.sys.controller;

import com.liaoyb.auth.security.AuthoritiesConstants;
import com.liaoyb.auth.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/api/security")
public class SecurityController {
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;


    /**
     * refreshToken
     *
     * @param refresh_token
     * @return
     */
    @PostMapping("/refreshToken")
    public OAuth2AccessToken refreshToken(String refresh_token) {
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(AuthoritiesConstants.DEFAULT_OAUTH_CLIENT);
        //TokenRequest
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), AuthoritiesConstants.DEFAULT_OAUTH_CLIENT, clientDetails.getAuthorizedGrantTypes(), "all");
        return authorizationServerTokenServices.refreshAccessToken(refresh_token, tokenRequest);
    }

    /**
     * 注销token
     */
    @PostMapping("/revokeToken")
    public void revokeToken(HttpServletRequest request) {
        String accessToken = TokenUtils.extractToken(request);
        consumerTokenServices.revokeToken(accessToken);
    }
}
