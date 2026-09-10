import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Login from '../pages/Login'
import Home from '../pages/Home'
import WordList from '../components/WordList'
import Exercise from '../components/Exercise'

function RouterConfig() {

    return (
        <div>

            <Routes>

                <Route path='/' element={<Login />} />

                <Route path='/home' element={<Home />} >

                    {/* !! Kullanıcı direkt /home adresine geldiğinde, alt taraftaki <Outlet/> alanına otomatik olarak WordList bileşenini bas */}
                    <Route index element={<WordList />} />

                    <Route path='words' element={<WordList />} />

                    <Route path='exercise' element={<Exercise />} />

                </Route>

            </Routes>

        </div>
    )
}

export default RouterConfig