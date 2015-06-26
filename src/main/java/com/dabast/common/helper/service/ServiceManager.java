package com.dabast.common.helper.service;

import com.dabast.common.cache.ICacheFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
public class ServiceManager implements InitializingBean{

    private static boolean inited;
    public static ICacheFactory cacheFactory;

    //public static IProjectTypeInvertService projectTypeInvertService;
    public static void setInited(boolean inited) {
        ServiceManager.inited = inited;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        inited = true;
    }

    public static boolean isInited() {
        return inited;
    }
    public  ICacheFactory getCacheFactory() {
        return cacheFactory;
    }

    public  void setCacheFactory(ICacheFactory cacheFactory) {
        ServiceManager.cacheFactory = cacheFactory;
    }
	

}
