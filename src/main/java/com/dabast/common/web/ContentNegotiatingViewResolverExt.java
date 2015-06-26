package com.dabast.common.web;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * User: lxd
 * Date: 11-12-30
 * Time: 下午5:49
 */
public class ContentNegotiatingViewResolverExt extends ContentNegotiatingViewResolver {
    private List<ViewResolver> viewResolvers;

    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
        super.setViewResolvers(viewResolvers);
    }

    public void loadPrefix(String prefix){
    	if(viewResolvers != null){
	        for (ViewResolver resolver:viewResolvers){
	            if(resolver instanceof InternalResourceViewResolver){
	                InternalResourceViewResolver resourceViewResolver = (InternalResourceViewResolver)resolver;
	                resourceViewResolver.setPrefix("/"+prefix+"/");
	                setViewResolvers(viewResolvers);
                    resourceViewResolver.clearCache();
	                return;
	            }
	        }
    	}
    }
}
