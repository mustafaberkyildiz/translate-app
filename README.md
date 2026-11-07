# 📚 Translate App – Kelime Öğrenme ve Tekrar Uygulaması

Translate App, kullanıcıların yabancı dilde öğrendikleri kelimeleri kaydedebildiği ve oyunlaştırılmış tekrar sistemiyle kelime bilgilerini geliştirebildiği full-stack bir web uygulamasıdır.

Proje, Java Spring Boot ve React kullanılarak geliştirilmiştir. Kullanıcıların kelimeleri yalnızca kaydetmesini değil, düzenli tekrar yaparak öğrenmelerini de amaçlar.

## 🚀 Proje Özellikleri

### 📖 Kelime Yönetimi
- Yeni kelime ve anlamlarını kaydetme
- Kaydedilen kelimeleri öğrenme amacıyla kullanma
- Kullanıcıya ait kelime koleksiyonu oluşturma

### 🎮 Kelime Tahmin Oyunu
- Kaydedilen kelimeler arasından rastgele kelime seçimi
- Seçilen kelimenin anlamını tahmin etme
- Verilen cevabı kontrol etme
- Kelime tekrarlarıyla öğrenilen bilgileri pekiştirme

## 💻 Kullanılan Teknolojiler

| Katman | Teknolojiler |
|---|---|
| Backend | Java 17, Spring Boot, Spring Security, Spring Data JPA |
| Frontend | React, TypeScript, Vite |
| State Management | Redux Toolkit |
| UI | Material UI |
| Veritabanı | MySQL |
| API İletişimi | REST API, Axios |
| Güvenlik | JWT |
| Diğer | Maven, Docker Compose, Git, GitHub |

## 🏗️ Sistem Mimarisi

Uygulama frontend, backend ve veritabanı katmanlarından oluşmaktadır.

- **Frontend:** React ve TypeScript ile geliştirilen kullanıcı arayüzü
- **Backend:** Spring Boot ile geliştirilen REST API
- **Veritabanı:** MySQL ile kelime ve uygulama verilerinin saklanması
- **İletişim:** Axios aracılığıyla frontend ve backend arasında HTTP istekleri

```text
         React + TypeScript
                 |
                 | HTTP / REST API
                 v
            Spring Boot
                 |
                 | Spring Data JPA
                 v
               MySQL
```

## 📁 Proje Yapısı

```text
translate-app/
│
├── backend/
│   └── Spring Boot uygulaması
│
├── frontend/
│   └── React + TypeScript uygulaması
│
├── docker-compose.yaml
├── .gitignore
└── README.md
```

## 🔄 Kelime Öğrenme Akışı

1. Kullanıcı öğrenmek istediği kelimeyi ve anlamını sisteme kaydeder.
2. Kelimeler uygulamanın veritabanında saklanır.
3. Kullanıcı kelime tekrar oyununu başlatır.
4. Sistem, kayıtlı kelimeler arasından rastgele bir kelime getirir.
5. Kullanıcı kelimenin anlamını tahmin eder.
6. Cevap kontrol edilir ve kullanıcı kelime bilgisini pekiştirir.

## 🎯 Projenin Amacı

Bu proje, yabancı dil öğrenimini destekleyen etkileşimli bir uygulama geliştirmek ve full-stack yazılım geliştirme konusunda deneyim kazanmak amacıyla oluşturulmuştur.

Proje kapsamında Spring Boot ile REST API geliştirme, veritabanı entegrasyonu, React ve TypeScript ile kullanıcı arayüzü oluşturma, frontend-backend iletişimi ve uygulama mimarisi konularında çalışmalar gerçekleştirilmiştir.

## 👨‍💻 Geliştirici

**Mustafa Berk Yıldız**

Bilgisayar Mühendisliği – Konya Teknik Üniversitesi

[GitHub Profilim](https://github.com/mustafaberkyildiz)
