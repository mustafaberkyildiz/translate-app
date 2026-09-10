import React, { useEffect, useState } from 'react'
import { useAppDispatch, useAppSelector } from '../redux/hooks'
import { getRandomWord } from '../redux/slices/DictionarySlice';
import '../css/Exercise.css'
import { HiOutlineSpeakerWave } from "react-icons/hi2";
import { TbMenu4 } from "react-icons/tb";
import { BsArrowRepeat } from "react-icons/bs";


function Exercise() {

    const dispatch = useAppDispatch();

    const { randomWord } = useAppSelector(store => store.dictionary);

    // Eğer randomWord null ise( en basta null veriyoruz DictionarySlice kisminda sonra burada useEffect icinde randomWord degiskenine deger ataniyor), sağ taraftaki boş objeyi {} baz alır.
    const { id, word, mean, sentence } = randomWord || {};

    const [showExplain, setShowExplain] = useState<boolean>(false);


    useEffect(() => {
        console.log("Random Word Updated")
        console.log(randomWord)
    }, [randomWord])

    useEffect(() => {

        dispatch(getRandomWord());

    }, [])

    const sendRandomWord = () => {

        setShowExplain(false);

        dispatch(getRandomWord());

    }

    return (
        <div className='main-exercise'>

            <div className='exercise-card'>
                <h2 className="exercise-title">{word}</h2>

                <div style={{ display: "flex", flexDirection: "row", textAlign: "center" }}>
                    <HiOutlineSpeakerWave className='speakerIcon' />
                    <span style={{ marginTop: "-4px", fontSize: "14px", color: "#097686" }}>İngilizce</span>
                </div>

                {
                    showExplain
                        ?
                        <>
                            <div className="exercise-MeanBox">
                                <span className="exercise-label">ANLAMI</span>
                                <p className="exercise-mean">{mean}</p>
                            </div>

                            <div className="exercise-SentenceBox">
                                <span className="exercise-label">ÖRNEK CÜMLE</span>
                                <p className="exercise-sentence">"{sentence}"</p>
                            </div>
                        </>
                        :
                        <>
                            <TbMenu4 style={{ color: "gray", marginTop: "50px", fontSize: "90px", opacity: "0.5" }} />

                            <span style={{ color: "gray", marginTop: "50px", fontSize: "14px" }}>Anlamını görmek için butona bas</span>
                        </>
                }

            </div>

            <button className='show-btn' onClick={() => { setShowExplain(true) }} >Anlamını Göster</button>

            <button className='repeat-btn' onClick={sendRandomWord} >
                <BsArrowRepeat style={{ marginRight: "7px", paddingTop: "3px", fontSize: "17px" }} />
                Yeni Kelime
            </button>

        </div>
    )
}

export default Exercise