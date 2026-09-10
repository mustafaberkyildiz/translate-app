package com.mustafaberk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mustafaberk.model.Dictionary;
import java.util.List;


@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long>{

	List<Dictionary> findByUser_Id(Long userId);
	
}
