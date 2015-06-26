package com.dabast.common.web;

import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class HibernateThreadInterceptor implements WebRequestInterceptor {
	private ApplicationContext applicationContext; 
//	@Autowired
//	private SessionFactory sessionFactory;
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	@Override
	public void preHandle(WebRequest request) throws Exception {

	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex)
			throws Exception {
//		sessionFactory.getCurrentSession().getTransaction().commit();  
		
	}

//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext)
//			throws BeansException {
//		this.applicationContext=applicationContext;
//	}

}
