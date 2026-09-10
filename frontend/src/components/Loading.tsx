import React from 'react'
import Backdrop from '@mui/material/Backdrop'
import CircularProgress from '@mui/material/CircularProgress'
import { useState } from 'react'

function Loading() {



    return (
        <Backdrop
            sx={{ color: "white", zIndex: (theme) => theme.zIndex.drawer + 1 }}
            open={false}
        >
            <CircularProgress color="inherit" />
        </Backdrop>
    )
}

export default Loading