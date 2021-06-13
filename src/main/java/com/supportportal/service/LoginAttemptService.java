package com.supportportal.service;

import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS=5;
    private static final int ATTEMPTS_INCREMENT=1;
    private LoadingCache<String,Integer> loginAttemptCache;

    //initialisation de cache
    public LoginAttemptService(){
        super();
        loginAttemptCache= CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).
                maximumSize(100).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }
    // enleve  user from the  cache
    public void evitUserFromLoginAttemptCache(String nomuser){
        loginAttemptCache.invalidate(nomuser);
    }
    //add user to cache
    public void addUserToLoginAttemptCache(String nomuser)  {
        int attempts = 0;
        try {
            attempts = ATTEMPTS_INCREMENT + loginAttemptCache.get(nomuser);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loginAttemptCache.put(nomuser, attempts);

    }

    // si userattempts> = MAXIMUM_NUMBER_OF_ATTEMPTS :return faux
    public boolean hasExceededMaxAttempts(String nomuser) {
        try {
            return loginAttemptCache.get(nomuser) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } return  false ;
    }
}
