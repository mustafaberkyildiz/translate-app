import React from 'react';
import '../css/Word.css'; // CSS dosyamızı ekledik
import { deleteWord, type WordType } from '../redux/slices/DictionarySlice';
import { RiDeleteBin6Line } from "react-icons/ri";
import { useAppDispatch } from '../redux/hooks';


interface propsWord {
    myWord: WordType;
}

function Word({ myWord }: propsWord) {

    const { id, word, mean, sentence } = myWord;

    const dispatch = useAppDispatch();

    const deleteDictionary = () => {

        const isConfirm = window.confirm("Silmek Istediginize Emin Misiniz ?");

        if (isConfirm) {

            dispatch(deleteWord(id));

        }

    }

    return (
        <div className="word-card">

            {/* Üst Kısım: Kelime ve Seviye */}
            <div className="word-header">
                <h2 className="word-title">{word}</h2>
                <RiDeleteBin6Line className='deleteIcon' onClick={deleteDictionary} />
            </div>

            {/* Orta Kısım: Anlam Kutusu */}
            <div className="meaning-box">
                <span className="section-label meaning-label">ANLAMI</span>
                <p className="meaning-text">{mean}</p>
            </div>

            {/* Alt Kısım: Örnek Cümle */}
            <div className="sentence-box">
                <span className="section-label sentence-label">ÖRNEK CÜMLE</span>
                <p className="sentence-text">"{sentence}"</p>
            </div>

        </div>
    );
}

export default Word;