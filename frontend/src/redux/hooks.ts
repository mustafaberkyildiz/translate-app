import { useDispatch, useSelector } from 'react-redux';
import type { TypedUseSelectorHook } from 'react-redux'; // type burada eklendi
import type { RootState, AppDispatch } from './store'; // store.ts dosyanın yolu


// Artık normal useSelector yerine bunu kullanacağız:
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
// ** yani sunu diyoruz useSelector yerine useAppSelector kullanicam tipimde TypedUseSelectorHook<RootState> bu diyorum  

// Artık normal useDispatch yerine useAppDispatch kullanacağız:
export const useAppDispatch = () => useDispatch<AppDispatch>();