package com.mustafaberk.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mustafaberk.dto.DictionaryRequest;
import com.mustafaberk.dto.DictionaryUpdateRequest;
import com.mustafaberk.dto.DtoDictionary;
import com.mustafaberk.dto.DtoUser;
import com.mustafaberk.enums.MessageType;
import com.mustafaberk.exception.BaseException;
import com.mustafaberk.exception.ErrorMessage;
import com.mustafaberk.model.Dictionary;
import com.mustafaberk.model.User;
import com.mustafaberk.repository.DictionaryRepository;
import com.mustafaberk.service.IDictionaryService;

@Service
public class DictionaryServiceImpl implements IDictionaryService{

	@Autowired
	private DictionaryRepository dictionaryRepository;
	
	private DtoDictionary generateDtoDictionary(Dictionary dictionary) {
		
		DtoUser dtoUser = new DtoUser();
		BeanUtils.copyProperties(dictionary.getUser(), dtoUser);
		
		DtoDictionary dtoDictionary = new DtoDictionary();
		
		BeanUtils.copyProperties(dictionary, dtoDictionary);
		dtoDictionary.setUser(dtoUser);
		
		return dtoDictionary;
	}
	
	private Dictionary generateDictionary(DictionaryRequest request,User user) {
		
		Dictionary dictionary = new Dictionary(request.getWord(), request.getMean(), request.getSentence(), user);
		
		return dictionary;
	}
	
	@Override
	public DtoDictionary saveWord(DictionaryRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = ( User ) authentication.getDetails();
		
		Dictionary dictionary = generateDictionary(request, user);
		
		Dictionary savedDictionary = dictionaryRepository.save(dictionary); 
		
		return generateDtoDictionary(savedDictionary);
	}

	@Override
	public List<DtoDictionary> getAllWords() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = ( User ) authentication.getDetails();
		
		List<Dictionary> dictionaries = dictionaryRepository.findByUser_Id(user.getId());

		List<DtoDictionary> dtoDictionaries = dictionaries.stream()
														  .map(this::generateDtoDictionary)
														  .toList();
		
		return dtoDictionaries;
	}

	@Override
	public DtoDictionary getRandomWord() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = ( User ) authentication.getDetails();
		
		List<Dictionary> dictionaries = dictionaryRepository.findByUser_Id(user.getId());
		
		Random random = new Random();
		
		// eger liste eleman sayisi 0 ise yani hic kelime kaydetmediyse hata verir asagid arandom.nextInt( dictionaries.size() ) icerisi pozitif olmali cunku pozitif sayi vericen ki o sayiya kadarki sayilardan random bir sayi uretebilsin
		// asagida random.nextInt( dictionaries.size() ) burada throw new IllegalArgumentException("bound must be positive"); hatasi firlatilir cunku icine pozitif bir deger istiyor 
		if ( dictionaries.size() <= 0 ) { 
			return new DtoDictionary();
		}
		
		// random.nextInt(100) = 0 ile 99 arasında rastgele bir tam sayı üretir (100 dahil değildir)
		int randomSayi = random.nextInt( dictionaries.size() ); // eleman sayisi 3 mesela 3 verdik buraya 3 dahil degil 2'ye kadar random uretir zaten oyle olmali ki indeksteki elemani alicaz
		
		Dictionary wantedDictionary = dictionaries.get(randomSayi); // .get = listedeki istedigin indeksteki elemani getirir
		
		return generateDtoDictionary(wantedDictionary);
	}

	@Override
	public DtoDictionary updateWord(Long wordId,DictionaryUpdateRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = ( User ) authentication.getDetails();
		
		Dictionary dictionary = dictionaryRepository.findById(wordId)
														 .orElseThrow( () -> new BaseException(new ErrorMessage(MessageType.WORD_NOT_FOUND, null)) );
		
		if (user.getId()!=dictionary.getUser().getId()) {
			throw new BaseException(new ErrorMessage(MessageType.USERS_NOT_MATCH, null));
		}
		
		// İlk olarak sol taraftaki request.getWord() != null kontrol edilir.Eğer request.getWord() gerçekten null ise, soldaki ifade false sonuç verir.
		// Java, && işleminde ilk koşul false olduğunda sonucun zaten false olacağını bildiği için sağ taraftaki koda hiç bakmaz.Sağ taraf çalıştırılmadığı için equals metoduna ulaşılamaz ve dolayısıyla NullPointerException fırlatılmaz.
		if ( request.getWord()!=null && !request.getWord().equals(dictionary.getWord()) ) { // word null degilse ve kelime ile guncellenmek istenen kelime esit degilse kelimeyi guncelle esitse kelimeyi guncellemeye gerek yok 
			dictionary.setWord(request.getWord());
		}
		
		if ( request.getMean()!=null && !request.getMean().equals(dictionary.getMean()) ) {
			dictionary.setMean(request.getMean());
		}
		
		if ( request.getSentence()!=null && !request.getSentence().equals(dictionary.getSentence()) ) {
			dictionary.setSentence(request.getSentence());
		}
		
		dictionaryRepository.save(dictionary);
		
		return generateDtoDictionary(dictionary);
	}

	@Override
	public void deleteWord(Long wordId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = ( User ) authentication.getDetails();
		
		Dictionary dictionary = dictionaryRepository.findById(wordId)
				 .orElseThrow( () -> new BaseException(new ErrorMessage(MessageType.WORD_NOT_FOUND, null)) );
		
		if (user.getId()!=dictionary.getUser().getId()) {
			throw new BaseException(new ErrorMessage(MessageType.USERS_NOT_MATCH, null));
		}
		
		dictionaryRepository.delete(dictionary);
		
	}

	
}
