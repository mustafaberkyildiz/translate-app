import React, { useState } from 'react';
import { Outlet, Link, useNavigate } from 'react-router-dom';
import '../css/Home.css'; // CSS dosyamızı bağlıyoruz

// İkonları dahil ediyoruz
import { FaGraduationCap, FaBookOpen, FaDumbbell } from 'react-icons/fa';
import { MdLogout } from "react-icons/md";
import { useAppDispatch } from '../redux/hooks';
import { logoutFromCookie } from '../redux/slices/UserSlice';


function Home() {

    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    const [dataType, setDataType] = useState<boolean>(true);
    // true ise kelimelerim kismi false ise kelime calis kismi ekrana gelir


    const logoutFromSession = () => {

        dispatch(logoutFromCookie())
            .unwrap()
            .then(() => {
                navigate("/");
            })

    }


    return (
        <div className="home-container">

            {/* Üst Kısım (Navbar) */}
            <nav className="navbar">

                <div className="nav-brand">
                    <div className="logo-icon">
                        <FaGraduationCap />
                    </div>
                    <span className="brand-name">WordUp</span>
                </div>

                <div className="nav-links">

                    {/* Aktif olan butona "active" class'ını verdik */}
                    <Link to="/home/words" className={`nav-btn ${dataType ? "active" : ""} `} onClick={() => { setDataType(true) }} >
                        <FaBookOpen className="btn-icon" />
                        Kelimelerim
                    </Link>

                    <Link to="/home/exercise" className={`nav-btn ${dataType ? "" : "active"} `} onClick={() => { setDataType(false) }} >
                        <FaDumbbell className="btn-icon" />
                        Kelime Çalış
                    </Link>

                </div>

                <div>
                    <MdLogout style={{ fontSize: "18px", cursor: "pointer" }} onClick={logoutFromSession} />
                </div>

            </nav>


            {/* Alt Kısım (Değişen İçerik) */}
            <main className="main-content">
                <Outlet />
            </main>

        </div>
    );
}

export default Home;