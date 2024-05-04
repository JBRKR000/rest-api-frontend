package com.project.service;

import com.project.model.Zadanie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ZadanieService {
	Zadanie getZadanie(Integer zadanieId);

	Zadanie setZadanie(Zadanie zadanie);

	void deleteZadanie(Integer zadanieId);

	Page<Zadanie> getZadania(Pageable pageable);

	Page<Zadanie> getZadaniaProjektu(Integer projektId, Pageable pageable);
}