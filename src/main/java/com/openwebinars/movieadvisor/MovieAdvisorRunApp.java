package com.openwebinars.movieadvisor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.attribute.standard.JobOriginatingUserName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openwebinars.movieadvisor.model.Film;
import com.openwebinars.movieadvisor.service.FilmQueryService;
import com.openwebinars.movieadvisor.service.FilmService;

@Component
public class MovieAdvisorRunApp { // Si fuera un MVC haria el papel de @Controller
	@Autowired
	private FilmService filmService;
	@Autowired
	private FilmQueryService filmQueryService;
	
	@Autowired
	private MovieAdvisorHelp help;

	public void run(String[] args) {// Recibe los argumentos que le pasemos en MovieAdvisorApp
		
		if(args.length <1) {
		
			System.out.println("Error en la sintaxis");
			System.out.println(help.getHelp());
		}else if(args.length == 1 ){
			// Si tenemos solamente uno es posible que la busqueda lo estemos haciendo por alguno de los argumentos "sueltos"
			// O bien queremos listarlo por: Genero,
			switch (args[0].toLowerCase().trim()) {
				case "-lg":
					filmService.findAllGenres().forEach(System.out::println);
					break;
				case "-h":
					System.out.println(help.getHelp());
					break;
				default:
					System.out.println("Error de sintaxis");
					System.out.println(help.getHelp());
			}// end switch
			
		}else if( (args.length %2) !=0) {
			//El numero de argumentos debe venir en parejas (pares) (a excepción de -lg y -h) por ejemplo, si queremos poner:
			// -t "titutlo" . si queremos poner por año:  -y "año"
			System.out.println("Error de sintaxis");
			System.out.println(help.getHelp());
			
		}else if(args.length >8) { // Al igual que el tope es de 4 parejas
			System.out.println("Error de sintaxis");
			System.out.println(help.getHelp());
		}else { // quiere decir que nos estan pasando los argumentos correctos que estamos necesitando
			
			List<String[]> argumentos=new ArrayList<>();
			
			for(int i=0;i <args.length ; i +=2) {// los vamos a tomar en parejas: El comando y su valor
				argumentos.add(new String[] {args[i],args[i+1]});
			}
			
			boolean error=false;
			
			for(String[] argumento:argumentos) {
				switch (argumento[0].toLowerCase()) {// el argumento[0] es el comando pasado como argumento
					case "-ag":
						filmQueryService.anyGenre(argumento[1].split(","));
						break;
					case "-tg":
						filmQueryService.allGenres(argumento[1].split(","));
						break;
					case "-y":
						filmQueryService.year(argumento[1]);
						break;
					case "-b":// Si es para el caso de rango de años: spliteamos los valores del comando
						String[] years=argumento[1].split(",");
						filmQueryService.betweenYears(years[0],years[1]);
						break;
					case "-t":
						filmQueryService.titleContains(argumento[1]);
						break;
					default: error =true;
							System.out.println("Error de sintaxis");
							System.out.println(help.getHelp());
				}
			}
			
			if(!error) {
				Collection<Film> result=filmQueryService.excec();
				System.out.printf("%s\t%-50s\t%s\t%s\n","ID","Titulo","Año","Generos");
				if(result != null) {
					result.forEach(f ->System.out.printf("%s\t%-50s\t%s\t%s\n",
							f.getId(),f.getTitle(),f.getYear(),f.getGenres().stream().collect(Collectors.joining(", "))
							));
					
				}else {
					System.out.println("No hay películas que cumplan esos criterios. Lo sentimos.");
				}
			}
			
		}
	} 
}
