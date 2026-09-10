import React, { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../redux/hooks'
import { getAllWords, saveWord } from '../redux/slices/DictionarySlice';
import '../css/WordList.css'
import { FaSearch, FaPlus } from 'react-icons/fa';
import Word from './Word';


function WordList() {

    const dispatch = useAppDispatch();

    const { wordList } = useAppSelector(store => store.dictionary);

    const [searchInput, setSearchInput] = useState<string>("");

    const [inputControl, setInputControl] = useState<boolean>(false);

    // Modal aç/kapa durumunu tutan state
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

    // Yeni kelime ekleme form state'leri (İleride dispatch ile göndermek için hazır)
    const [newWord, setNewWord] = useState<string>("");
    const [newMean, setNewMean] = useState<string>("");
    const [newSentence, setNewSentence] = useState<string>("");

    // Kaydetme işlemi
    const handleSaveWord = () => {
        // Burada dispatch ile veritabanına ekleme işlemini yapabilirsiniz

        const saveWordRequest = {
            word: newWord,
            mean: newMean,
            sentence: newSentence
        }

        dispatch(saveWord(saveWordRequest))
            .unwrap()
            .then(() => {

                // İşlem bitince formu temizle ve modalı kapat
                setNewWord("");
                setNewMean("");
                setNewSentence("");

                setIsModalOpen(false);

                setInputControl(false);

            })
            .catch((error) => {

                setInputControl(true);
                // altta tum alanlari doldurun uyarisi cikarmak icin true yaptik

            })


    }


    useEffect(() => {

        console.log("WordList Updated");
        console.log(wordList)

    }, [wordList])

    useEffect(() => {

        dispatch(getAllWords());

    }, [])


    return (
        <div className="kelimelerim-container">

            {/* Üst Kısım: Başlık ve Buton */}
            <div className="kelimelerim-header">

                <div className="header-titles">
                    <h1>Kelimelerim</h1>
                    <span className="word-count">{wordList.length} kelime</span>
                </div>

                <button className="add-word-btn" onClick={() => setIsModalOpen(true)} >
                    <FaPlus className="btn-icon" />
                    Kelime Ekle
                </button>

            </div>

            {/* Alt Kısım: Sadece Arama Çubuğu */}
            <div className="search-container">

                <div className="search-box">
                    <FaSearch className="search-icon" />
                    <input
                        type="text"
                        placeholder="Kelimede ara..."
                        className="search-input"
                        value={searchInput}
                        onChange={(e) => { setSearchInput(e.target.value) }}
                    />
                </div>

            </div>

            <div className='words-container'>
                {
                    wordList &&
                    wordList
                        .filter(word => word.word.toLowerCase().includes(searchInput.toLowerCase()))
                        .map((word) => (
                            <Word myWord={word} key={word.id} />
                        ))
                }
            </div>

            {/* --- MODAL (AÇILIR PENCERE) KISMI --- */}
            {isModalOpen && (
                <div className="modal-overlay">

                    <div className="modal-content">

                        {/* Modal Başlık ve Kapatma Butonu */}
                        <div className="modal-header">
                            <h2>Yeni Kelime Ekle</h2>
                            <button className="close-btn" onClick={() => { setIsModalOpen(false); setInputControl(false) }} >×</button>
                        </div>

                        {/* Modal Form Alanları */}
                        <div className="modal-body">

                            <div className="input-group">
                                <label>KELİME (İNGİLİZCE)</label>
                                <input
                                    type="text"
                                    placeholder="örn: Resilient"
                                    value={newWord}
                                    onChange={(e) => setNewWord(e.target.value)}
                                />
                            </div>

                            <div className="input-group">
                                <label>ANLAMI (TÜRKÇE)</label>
                                <input
                                    type="text"
                                    placeholder="örn: Dayanıklı, güçlü"
                                    value={newMean}
                                    onChange={(e) => setNewMean(e.target.value)}
                                />
                            </div>

                            <div className="input-group">
                                <label>ÖRNEK CÜMLE (İSTEĞE BAĞLI)</label>
                                <input
                                    type="text"
                                    placeholder="I will resilient ..."
                                    value={newSentence}
                                    onChange={(e) => setNewSentence(e.target.value)}
                                />
                            </div>

                            {
                                inputControl
                                    ? <span className="inputControl" >Tüm alanları doldurunuz.</span>
                                    : ""
                            }

                            <button className="save-modal-btn" onClick={handleSaveWord}>
                                Kelimeyi Kaydet
                            </button>

                        </div>

                    </div>

                </div>
            )}

        </div>
    )
}

export default WordList