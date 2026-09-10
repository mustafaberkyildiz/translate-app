import { configureStore } from '@reduxjs/toolkit'
import UserReducer from './slices/UserSlice'
import DictionaryReducer from './slices/DictionarySlice'

export const store = configureStore({
    reducer: {
        user: UserReducer,
        dictionary: DictionaryReducer
    },
})

// 1. RootState tipini dışarı aktarıyoruz
export type RootState = ReturnType<typeof store.getState>

// 2. AppDispatch tipini dışarı aktarıyoruz
export type AppDispatch = typeof store.dispatch

// buradaki Root state'i const { todos } = useSelector((state: RootState) => state.todo);
// burada kullanicaz cunku state de tur istiyor turu de asagidaki gibi RootState = Return type'dan geliyor,state'in type bu yani
// bundan dolayi state turu olarak RootState 