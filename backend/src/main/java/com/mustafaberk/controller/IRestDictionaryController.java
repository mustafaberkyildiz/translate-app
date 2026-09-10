package com.mustafaberk.controller;

import java.util.List;

import com.mustafaberk.dto.DictionaryRequest;
import com.mustafaberk.dto.DictionaryUpdateRequest;
import com.mustafaberk.dto.DtoDictionary;

public interface IRestDictionaryController {

	public RootEntity<DtoDictionary> saveWord(DictionaryRequest request);
	
	public RootEntity<List<DtoDictionary>> getAllWords();
	
	public RootEntity<DtoDictionary> getRandomWord();
	
	public RootEntity<DtoDictionary> updateWord(Long wordId,DictionaryUpdateRequest request);
	
	public RootEntity<String> deleteWord(Long wordId);
}
