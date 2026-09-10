package com.mustafaberk.service;

import java.util.List;

import com.mustafaberk.dto.DictionaryRequest;
import com.mustafaberk.dto.DictionaryUpdateRequest;
import com.mustafaberk.dto.DtoDictionary;

public interface IDictionaryService {

	public DtoDictionary saveWord(DictionaryRequest request);

	public List<DtoDictionary> getAllWords();
	
	public DtoDictionary getRandomWord();
	
	public DtoDictionary updateWord(Long wordId,DictionaryUpdateRequest request);
	
	public void deleteWord(Long wordId);
}
