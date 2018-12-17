package com.suncompass.plateform.sys.security;

import com.suncompass.plateform.kernel.security.Digest;
import com.suncompass.plateform.kernel.util.Encodes;
import com.suncompass.plateform.sys.entity.User;
import com.suncompass.plateform.sys.entity.UserStateEnum;
import com.suncompass.plateform.sys.service.LogService;
import com.suncompass.plateform.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dengbq
 */
public class FormUsernamePasswordAuthenticator implements Authenticator<UsernamePasswordCredentials> {
    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Override
    public void validate(UsernamePasswordCredentials credentials, WebContext context) {
        if (credentials == null) {
            throw new CredentialsException("No credential");
        }
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        User user = userService.findByUsername(username);
        if (user != null) {
            if (UserStateEnum.LOCKED.ordinal() == user.getState()) {
                throw new AuthenticationException("msg:该已帐号禁止登录.");
            }
            if (UserStateEnum.NOT_ACTIVE.ordinal() == user.getState()) {
                throw new AuthenticationException("msg:该已帐号未激活.");
            }
        }

        byte[] salt = Encodes.decodeHex(user.getSalt());
        SimpleHash hash = new SimpleHash("md5", password, salt, 2);
        if(!user.getPassword().equals(hash.toString())){
            throw new CredentialsException("Username : '" + username + "' does not match password");
        }

        final CommonProfile profile = new CommonProfile();
        profile.setId(username);
        profile.addAttribute(Pac4jConstants.USERNAME, username);
        credentials.setUserProfile(profile);
    }

}
