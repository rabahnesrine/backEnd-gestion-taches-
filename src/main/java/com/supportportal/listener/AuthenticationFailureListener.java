package com.supportportal.listener;

import com.supportportal.service.LoginAttemptService;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
public  class AuthenticationFailureListener {
    private LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }
    @EventListener
    public void onAuthentificationFailure(AuthenticationFailureBadCredentialsEvent event)  {
// on va recupere authentification from actuel event(auth failure bad credenti....) dans un object
        Object principal =event.getAuthentication().getPrincipal();
        if(principal instanceof  String){
            String username= event.getAuthentication().getPrincipal().toString();
            loginAttemptService.addUserToLoginAttemptCache(username);
        }


    }



}
