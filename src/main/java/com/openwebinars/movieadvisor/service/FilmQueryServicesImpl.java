package com.openwebinars.movieadvisor.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openwebinars.movieadvisor.dao.FilmDao;
import com.openwebinars.movieadvisor.model.Film;

@Service
public class FilmQueryServicesImpl implements FilmQueryService {
	
	@Autowired
	private FilmDao dao;
	
	//Para enlazar los parametros del query, vamos a inicializar un predicate
	private Predicate<Film> predicate;
	
	/*================== Otros metodos ===============*/
	@PostConstruct
	public void init() { //El @PostConstruct y @Predestroy pertenecen a las anotaciones que manejan el ciclo de vida de un bean
		// Nos permite anotar UN método cualquiera, siempre y cuando no reciba argumentos y debe devolver VOID.
		predicate=null;
	}

	@Override
	public Collection<Film> excec() {
		
		return dao.findAll().stream().filter(predicate).collect(Collectors.toList());
			
	}

	@Override
	public FilmQueryService anyGenre(String... genres) {
		// TODO Auto-generated method stub
		Predicate<Film> pAnyGenre=(film -> Arrays.stream(genres).anyMatch(film.getGenres()::contains));
		predicate=(predicate ==null)? pAnyGenre: predicate.and(pAnyGenre);
		
		return this;
		
	}

	@Override
	public FilmQueryService allGenres(String... genres) {
		Predicate<Film> pAllGenres =(film -> Arrays.stream(genres).allMatch(film.getGenres()::contains));
		
		predicate=(predicate ==null)? pAllGenres:predicate.and(pAllGenres);	
		return this;
	}

	@Override
	public FilmQueryService year(String year) {
		Predicate<Film> pYear=(film -> film.getYear().equalsIgnoreCase(year));
		
		predicate=(predicate ==null)? pYear: predicate.and(pYear);
		
		return this;
	}

	@Override
	public FilmQueryService betweenYears(String from, String to) {
		Predicate<Film> pBetweenYears =(film -> {
			LocalDate fromYear=LocalDate.of(Integer.valueOf(from), 1, 1);
			LocalDate toYear=LocalDate.of(Integer.valueOf(from), 1, 3);
			LocalDate filmYear=LocalDate.of(Integer.parseInt(film.getYear()), 1, 2);
			
			return filmYear.isAfter(fromYear) && filmYear.isBefore(toYear);
		});
		
		// TODO Auto-generated method stub
		predicate =(predicate ==null) ? pBetweenYears : predicate.and(pBetweenYears);
		
		return this;
	}

	@Override
	public FilmQueryService titleContains(String title) {
		Predicate<Film> pTitleContains =(film -> film.getTitle().toLowerCase().contains(title.toLowerCase()));
		
		predicate=(predicate ==null)? pTitleContains : predicate.and(pTitleContains);
		return this;
	}

}
