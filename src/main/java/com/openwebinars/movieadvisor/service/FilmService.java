package com.openwebinars.movieadvisor.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openwebinars.movieadvisor.dao.FilmDao;
import com.openwebinars.movieadvisor.model.Film;

@Service
public class FilmService {
	
	@Autowired
	private FilmDao filmDao;
	@Autowired
	private FilmQueryService queryService;
	
	public Collection<String> findAllGenres(){
		List<String> result=null;
		
		result=filmDao.findAll()
				.stream()
				.map(f ->f.getGenres())
				.flatMap(lista ->lista.stream())
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		
		return result;
	}
	
	
	public Collection<Film> findByAnyGenre(String... genres){
		return queryService.anyGenre(genres).excec();
	}
	
	public Collection<Film> findByAllGenres(String... genres){
		return queryService.anyGenre(genres).excec();
	}
	
	public Collection<Film> findByYear(String year){
		return queryService.year(year).excec();
	}
	
	public Collection<Film> findBetweenYears(String from, String to)
	{
		return queryService.betweenYears(from, to).excec();
	}
	
	public Collection<Film> findByTitleContains(String title){
		return queryService.titleContains(title).excec();
	}
	
	public Collection<Film> findAll(){
		return filmDao.findAll();
	}
	

}
