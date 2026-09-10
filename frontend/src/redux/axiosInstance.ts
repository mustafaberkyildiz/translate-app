import axios from "axios";

const BASE_URL = import.meta.env.VITE_BASE_URL;

const axiosInstance = axios.create({
    baseURL: BASE_URL,
    withCredentials: true // Cookie'lerin otomatik taşınması için kritik!
})

// ! Her istek atılmadan hemen önce araya girer
axiosInstance.interceptors.request.use(

    (config) => {

        const token = localStorage.getItem("accessToken");

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        // ** Buradaki config, Axios'un o an atacağı isteğin tüm yapılandırma ayarlarını (URL, headers, method, data vb.) içeren bir nesnedir.
        // return config; diyerek, üzerinde değişiklik yaptığımız (token eklenmiş) o güncel ayar nesnesini Axios'a geri veriyoruz. 
        // Axios da bu güncel config nesnesini alıp isteği sunucuya fırlatıyor. Eğer return config; yazmazsan istek askıda kalır ve sunucuya hiç gitmez.

        return config;
    },
    (error) => {
        return Promise.reject(error);
        // Promise.reject(error) satırı, istek atılmadan önce (istemci tarafında) bir hata oluşursa (örneğin config ayarlanırken bir şeyler ters giderse) bu hatayı yakalayıp 
        // isteği yapan yere (yani senin bu axiosInstance'ı çağırdığın yerdeki catch bloğuna) fırlatır.
    }

);

axiosInstance.interceptors.response.use(

    (response) => {
        return response;
    },
    async (error) => {

        const originalConfig = error.config;

        console.log("TUM ERROR RESPONSE:", error.response)
        console.log("HTTP STATUS:", error.response?.status);
        console.log("RESPONSE DATA:", error.response?.data);

        if (error.response?.status == 401 && !originalConfig._retry) {

            originalConfig._retry = true;

            try {

                const response = await axios.post(`${BASE_URL}/refreshToken`, {}, { withCredentials: true });
                // withCredentials:true diyince cerezleri kabul ediyorum gibi dusun yani cookie olusturabiliriz demis olduk(izin verdik yani cookie olusturmaya)

                const newAccessToken = response.data.payload.accessToken;

                localStorage.setItem("accessToken", newAccessToken);

                originalConfig.headers.Authorization = `Bearer ${newAccessToken}`;

                // Hataya sebep olan orijinal isteği yeni token ile tekrar çalıştırıp çağrıldığı yere veriyoruz
                return axiosInstance(originalConfig); // axiosInstance icine configuration ayarlarini verdik 

            } catch (refreshError) {

                // burdaki catch bloguna giriyorsa refreshToken'in suresi bitmistir 
                // ondan dolayi localStorage'den tokeni siliyoruz ve kullaniciyi login sayfasina yonlendiriyoruz
                localStorage.removeItem("accessToken");

                window.location.href = "/";

                return Promise.reject(refreshError);
            }

        }

        return Promise.reject(error);

    }

);

export default axiosInstance;