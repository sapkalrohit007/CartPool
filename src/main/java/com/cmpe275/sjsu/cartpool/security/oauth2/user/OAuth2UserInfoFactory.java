package com.cmpe275.sjsu.cartpool.security.oauth2.user;

import com.cmpe275.sjsu.cartpool.error.BadRequestException;

import com.cmpe275.sjsu.cartpool.model.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new BadRequestException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}