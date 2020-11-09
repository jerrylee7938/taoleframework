package com.taole.toolkit.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("configInfo")
public class ConfigInfo {

    @Value("#{configProperties['imageUrl']}")
    private String imageUrl;

    @Value("#{configProperties['uploadUrl']}")
    private String uploadUrl;
    @Value("#{configProperties['shareUrl']}")
    private String shareUrl;
    @Value("#{configProperties['weiChatUrl']}")
    private String weiChatUrl;

    public String getImageUrl() {
        return imageUrl;
    }


    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getWeiChatUrl() {
        return weiChatUrl;
    }

    public void setWeiChatUrl(String weiChatUrl) {
        this.weiChatUrl = weiChatUrl;
    }
}
