package com.openwebinars.movieadvisor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages  = "com.openwebinars.movieadvisor")//Detecta en este paquete los beans que debe crear en el ApplicationContext (contenedor) buscando clases con @Component
@PropertySource("classpath:/movieadvisor.properties") // Cargamos los valores desde nuestra clase de configuracion
public class AppConfig {
	
	@Value("${file.path}")
	private String file;
	
	@Value("${file.csv.separator}")
	private String separator;
	
	@Value ("${file.csv.list_separator}")
	private String listSeparator;
	
	

	public String getFile() {
		return file;
	}

	public String getSeparator() {
		return separator;
	}

	public String getListSeparator() {
		return listSeparator;
	}

}
