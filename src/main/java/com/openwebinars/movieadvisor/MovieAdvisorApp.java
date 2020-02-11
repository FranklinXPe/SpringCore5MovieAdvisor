package com.openwebinars.movieadvisor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.openwebinars.movieadvisor.config.AppConfig;

public class MovieAdvisorApp {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);
		
		MovieAdvisorRunApp runApp=applicationContext.getBean(MovieAdvisorRunApp.class);
		runApp.run(args);
		 
		
		((AnnotationConfigApplicationContext)applicationContext).close();

	}

}
