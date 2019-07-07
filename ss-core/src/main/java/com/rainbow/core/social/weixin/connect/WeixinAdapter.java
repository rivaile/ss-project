package com.rainbow.core.social.weixin.connect;

import com.rainbow.core.social.weixin.api.Weixin;
import com.rainbow.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author denglin
 * @version V1.0
 * @Description: 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 * @ClassName: WeixinAdapter
 * @date 2018/9/19 15:54
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

    private String openId;

    public WeixinAdapter() {
    }

    public WeixinAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(Weixin weixin) {
        return true;
    }

    @Override
    public void setConnectionValues(Weixin weixin, ConnectionValues values) {
        WeixinUserInfo profile = weixin.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(Weixin weixin) {
        return null;
    }

    @Override
    public void updateStatus(Weixin weixin, String s) {
        //do nothing
    }
}
