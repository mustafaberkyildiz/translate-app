import React, { useState } from "react";
import '../css/Login.css';
import { FaRegEye } from "react-icons/fa";
import { FaRegEyeSlash } from "react-icons/fa";
import { GiGraduateCap } from "react-icons/gi";
import { useAppDispatch, useAppSelector } from "../redux/hooks";
import { authenticate, register } from "../redux/slices/UserSlice";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";


function Login() {

    const dispatch = useAppDispatch();
    const { registerLoading, authenticateLoading } = useAppSelector(store => store.user);

    const navigate = useNavigate();

    const [showPassword, setShowPassword] = useState(false);

    const [loginType, setLoginType] = useState<boolean>(true);
    // true ise giris yap ekraninda olsun , false ise kayit ol ekraninda olsun

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [name, setName] = useState<string>("");
    const [surname, setSurname] = useState<string>("");

    const registerUser = () => {

        const registerRequest = {
            username: username,
            password: password,
            name: name,
            surname: surname
        }

        dispatch(register(registerRequest))
            .unwrap()
            .then(() => {

                Swal.fire({
                    icon: "success",
                    title: "Başarılı",
                    text: "TEBRİKLER KAYIT OLDUNUZ",
                    confirmButtonText: "Tamam",
                    confirmButtonColor: "#4f46e5" // Buton için şık bir mor/mavi ton (veya kendi buton renginiz)
                })
                    .then((result) => { // ekrana card geldikten sonra burasi calisir 

                        // Kullanıcı "Tamam" butonuna bastiysa if'e girer
                        if (result.isConfirmed) {
                            // Kullanıcıyı login sayfasına yönlendiriyoruz
                            window.location.href = "/";
                        }

                    })

            })
            .catch((error) => {

                if (typeof error === 'object') {
                    setInputControl(true);
                    return;
                }

                // sadece kullanici adi zaten mevcut hatasi var ise ekrana hata mesaji getiririz
                Swal.fire({
                    icon: "error",
                    title: "Uyarı",
                    text: error,
                    confirmButtonText: "Tamam",
                    confirmButtonColor: "#d33"
                })

                setInputControl(false);

            })

    }

    const authenticateUser = () => {

        const authRequest = {
            username: username,
            password: password
        }

        dispatch(authenticate(authRequest))
            .unwrap()
            .then(() => {

                navigate("/home");

            })
            .catch((error) => {

                if (typeof error === 'object') {
                    setInputControl(true);
                    return;
                }

                Swal.fire({
                    icon: "error",
                    title: "Uyarı",
                    text: error,
                    confirmButtonText: "Tamam",
                    confirmButtonColor: "#d33"
                })

                setInputControl(false);

            })


    }

    const [inputControl, setInputControl] = useState<boolean>(false);

    return (
        <div className="login-page">

            {/* Logo + Başlık */}
            <div className="brand">
                <div className="brand-icon">
                    <GiGraduateCap style={{ fontSize: "42px" }} />
                </div>

                <h1>WordUp</h1>
                <p>İngilizce kelime öğrenme uygulaması</p>
            </div>


            {/* Login Card */}
            <div className="login-card">

                {/* Login / Register */}
                <div className="auth-tabs">
                    <button className={`tab ${loginType ? "active" : ""}`} onClick={() => { setLoginType(true); setInputControl(false) }} >
                        Giriş Yap
                    </button>

                    <button className={`tab ${loginType ? "" : "active"}`} onClick={() => { setLoginType(false); setInputControl(false) }} >
                        Kayıt Ol
                    </button>
                </div>

                {/* Kullanici Adi */}
                <div className="form-group">
                    <label>KULLANICI ADI</label>

                    <input
                        type="input" value={username} onChange={(e) => { setUsername(e.target.value) }}
                    />
                </div>

                {/* Password */}
                <div className="form-group password-group">
                    <label>ŞİFRE</label>

                    <div className="password-wrapper">
                        <input
                            type={showPassword ? "text" : "password"}
                            placeholder="••••••••"
                            value={password} onChange={(e) => { setPassword(e.target.value) }}
                        />

                        <button
                            type="button"
                            className="password-eye"
                            onClick={() => setShowPassword(!showPassword)}
                        >
                            {showPassword ? (
                                <FaRegEyeSlash style={{ fontSize: "18px" }} />
                            ) : (
                                <FaRegEye style={{ fontSize: "18px" }} />
                            )}
                        </button>
                    </div>
                </div>

                {
                    loginType ? ""
                        :
                        <>
                            {/* ISIM */}
                            <div className="form-group">
                                <label>ISIM</label>

                                <input
                                    type="input" value={name} onChange={(e) => { setName(e.target.value) }}
                                />
                            </div>

                            {/* SOYISIM */}
                            <div className="form-group">
                                <label>SOYISIM</label>

                                <input
                                    type="input" value={surname} onChange={(e) => { setSurname(e.target.value) }}
                                />
                            </div>
                        </>

                }

                {
                    inputControl
                        ? <span className="inputControl" >Tüm alanları doldurunuz.</span>
                        : ""
                }


                {
                    loginType
                        ?
                        <>
                            <button type="submit" className="login-button" onClick={() => { authenticateUser() }}>
                                {authenticateLoading ? "Lutfen Bekleyin..." : "Giriş Yap"}
                            </button>
                        </>
                        :
                        <>
                            <button type="submit" className="login-button" onClick={() => { registerUser() }} >
                                {registerLoading ? "Lutfen Bekleyin..." : "Kayıt Ol"}
                            </button>
                        </>

                }

                {/* Register */}
                <div className="register-text">

                    {
                        loginType
                            ?
                            <>
                                <span>Hesabın yok mu?</span>
                                <button onClick={() => { setLoginType(false); setInputControl(false) }} >Kayıt Ol</button>
                            </>
                            :
                            <>
                                <span>Hesabın var mı?</span>
                                <button onClick={() => { setLoginType(true); setInputControl(false) }} >Giris Yap</button>
                            </>
                    }

                </div>

            </div>
        </div>
    );
}

export default Login;