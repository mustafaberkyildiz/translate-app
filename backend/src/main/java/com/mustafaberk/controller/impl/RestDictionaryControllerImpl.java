package com.mustafaberk.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mustafaberk.controller.IRestDictionaryController;
import com.mustafaberk.controller.RestBaseController;
import com.mustafaberk.controller.RootEntity;
import com.mustafaberk.dto.DictionaryRequest;
import com.mustafaberk.dto.DictionaryUpdateRequest;
import com.mustafaberk.dto.DtoDictionary;
import com.mustafaberk.service.IDictionaryService;

import jakarta.validation.Valid;

@RequestMapping("/word")
@RestController
public class RestDictionaryControllerImpl extends RestBaseController implements IRestDictionaryController{

	@Autowired
	private IDictionaryService dictionaryService;
	
	@PostMapping()
	@Override
	public RootEntity<DtoDictionary> saveWord(@RequestBody @Valid DictionaryRequest request) {
		return ok(dictionaryService.saveWord(request));
	}

	@GetMapping()
	@Override
	public RootEntity<List<DtoDictionary>> getAllWords() {
		return ok(dictionaryService.getAllWords());
	}

	@GetMapping("/random")
	@Override
	public RootEntity<DtoDictionary> getRandomWord() {
		return ok(dictionaryService.getRandomWord());
	}

	@PatchMapping("/{id}")
	@Override
	public RootEntity<DtoDictionary> updateWord(@PathVariable(name = "id") Long wordId,@RequestBody DictionaryUpdateRequest request) {
		return ok(dictionaryService.updateWord(wordId, request));
	}

	@DeleteMapping("/{wordId}")
	@Override
	public RootEntity<String> deleteWord(@PathVariable Long wordId) {
		
		dictionaryService.deleteWord(wordId);
		
		return ok("Kelime Silindi !");
	}
	
	
}
