import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import axios from 'axios'
import axiosInstance from '../axiosInstance';


const BASE_URL = import.meta.env.VITE_BASE_URL;

interface DictionaryRequest {
    word: string,
    mean: string,
    sentence: string
}

interface updateRequest {
    wordId: number,
    dictionaryRequest: DictionaryRequest
}

export interface WordType {
    id: number,
    word: string,
    mean: string,
    sentence: string,
}

interface DictionaryState {
    wordsLoading: boolean,
    wordList: WordType[],
    randomWord: WordType | null,
    randomWordLoading: boolean
}

const initialState: DictionaryState = {
    wordsLoading: false,
    wordList: [],
    randomWord: null,
    randomWordLoading: false
}


export const deleteWord = createAsyncThunk<any, number>("delete", async (wordId, thunkAPI) => {
    try {

        const response = await axiosInstance.delete(`/word/${wordId}`);

        console.log(response.data);

        return response.data;

    } catch (error: any) {
        console.log(error.response?.data?.exception?.message);
        return thunkAPI.rejectWithValue(error.response?.data?.exception?.message);
    }
})

export const updateWord = createAsyncThunk<any, updateRequest>("update", async (request, thunkAPI) => {
    try {

        const response = await axiosInstance.patch(`/word/${request.wordId}`, request.dictionaryRequest);

        console.log(response.data);

        return response.data;

    } catch (error: any) {
        console.log(error.response?.data?.exception?.message);
        return thunkAPI.rejectWithValue(error.response?.data?.exception?.message);
    }
})

export const getRandomWord = createAsyncThunk<any>("getRandom", async (_, thunkAPI) => {
    try {

        const response = await axiosInstance.get("/word/random");

        console.log(response.data);

        return response.data;

    } catch (error: any) {
        return thunkAPI.rejectWithValue(error.response?.data?.exception?.message);
    }
})

export const getAllWords = createAsyncThunk<any>("getAll", async (_, thunkAPI) => {
    try {

        const response = await axiosInstance.get("/word");

        console.log(response.data);

        return response.data;

    } catch (error: any) {
        return thunkAPI.rejectWithValue(error.response?.data?.exception?.message);
    }
})

export const saveWord = createAsyncThunk<any, DictionaryRequest>("save", async (request, thunkAPI) => {
    try {

        const response = await axiosInstance.post("/word", request);

        console.log(response.data);

        return response.data;

    } catch (error: any) {
        console.log(error.response?.data?.exception?.message);
        return thunkAPI.rejectWithValue(error.response?.data?.exception?.message);
    }
})


export const dictionarySlice = createSlice({
    name: 'counter',
    initialState,
    reducers: {

    },
    extraReducers: (builder) => {

        builder.addCase(getAllWords.fulfilled, (state, action) => {

            state.wordsLoading = false;

            // payload ve status donuyorum ya ben RootState turunde ordan payloadi aldim 
            state.wordList = action.payload.payload;

        })

        builder.addCase(getAllWords.pending, (state) => {
            state.wordsLoading = true;
        })

        builder.addCase(getAllWords.rejected, (state, action) => {
            state.wordsLoading = false;
            console.log(action.payload)
        })

        // ---------------------

        builder.addCase(getRandomWord.fulfilled, (state, action) => {

            state.randomWordLoading = false;

            state.randomWord = action.payload.payload;

        })

        builder.addCase(getRandomWord.pending, (state) => {
            state.randomWordLoading = true;
        })

        builder.addCase(getRandomWord.rejected, (state, action) => {
            state.randomWordLoading = false;
            console.log(action.payload)
        })

        // ---------------------


    }
})

export const { } = dictionarySlice.actions

export default dictionarySlice.reducer