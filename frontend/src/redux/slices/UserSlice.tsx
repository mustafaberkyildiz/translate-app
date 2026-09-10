import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import axios from 'axios'
import axiosInstance from '../axiosInstance';

const BASE_URL = import.meta.env.VITE_BASE_URL;

interface RegisterRequest {
    username: string,
    password: string,
    name: string,
    surname: string
}

interface AuthRequest {
    username: string,
    password: string
}

interface UserState {
    registerLoading: boolean
    authenticateLoading: boolean
}

const initialState: UserState = {
    registerLoading: false,
    authenticateLoading: false
}

export const register = createAsyncThunk<any, RegisterRequest>("register", async (request, thunkAPI) => {
    try {

        const response = await axios.post(`${BASE_URL}/register`, request);

        console.log(response.data);

        return response.data;

    } catch (error: any) {
        return thunkAPI.rejectWithValue(error.response?.data?.exception?.message);
    }
})

export const authenticate = createAsyncThunk<any, AuthRequest>("authenticate", async (request, thunkAPI) => {
    try {

        const response = await axios.post(`${BASE_URL}/authenticate`, request, { withCredentials: true });

        console.log(response.data);

        return response.data;

    } catch (error: any) {
        return thunkAPI.rejectWithValue(error.response?.data?.exception?.message);
    }
})

export const logoutFromCookie = createAsyncThunk<any>("logout", async (_, thunkAPI) => {
    try {

        const response = await axiosInstance.post("/logoutFromCookie");

        console.log(response.data);

        return response.data;

    } catch (error: any) {
        return thunkAPI.rejectWithValue(error.response?.data?.exception?.message);
    }
})

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {

    },
    extraReducers: (builder) => {

        builder.addCase(authenticate.fulfilled, (state, action) => {

            state.authenticateLoading = false;

            localStorage.setItem("accessToken", action.payload.payload.accessToken);

        })

        builder.addCase(authenticate.pending, (state) => {

            state.authenticateLoading = true;

        })

        builder.addCase(authenticate.rejected, (state, action) => {

            state.authenticateLoading = false;

            console.log(action.payload)

        })

        // ---------------------

        builder.addCase(register.fulfilled, (state, action) => {
            state.registerLoading = false;
        })

        builder.addCase(register.pending, (state) => {
            state.registerLoading = true;
        })

        builder.addCase(register.rejected, (state, action) => {
            state.registerLoading = false;
            console.log(action.payload)
        })

        // ---------------------

        builder.addCase(logoutFromCookie.fulfilled, (state, action) => {

            localStorage.removeItem("accessToken");

        })

    }
})

export const { } = userSlice.actions

export default userSlice.reducer