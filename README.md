<p align="center">
  <h1 align="center">📰 NewsApp</h1>
  <p align="center">
    Modern Android News Application built using Kotlin & MVVM Architecture
  </p>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Language-Kotlin-blue?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Architecture-MVVM-orange?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Networking-Retrofit-green?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Async-Coroutines-purple?style=for-the-badge"/>
</p>

---

## 📌 About The Project

**NewsApp** is a clean and scalable Android application that fetches real-time news articles from a remote API and displays them in a modern, responsive UI.

The project focuses on:
- Clean architecture principles  
- MVVM design pattern  
- Lifecycle-aware components  
- Maintainable and structured code  

---

## 🏗️ Project Architecture

The application follows a layered MVVM structure:

```
📦 NewsApp
 ┣ 📂 data
 ┃ ┣ 📂 api
 ┃ ┗ 📂 repository
 ┣ 📂 model
 ┣ 📂 ui
 ┗ 📂 viewmodel
```

### 🔹 Layer Responsibilities

- **UI Layer** → Activities / Fragments handling user interaction  
- **ViewModel Layer** → Manages UI state & business logic  
- **Data Layer** → Handles API communication & data abstraction  

This separation ensures scalability, readability, and easier debugging.

---

## 🚀 Features

- 🗞️ Real-time News Headlines  
- 🔎 Search News by Keyword  
- 📂 Category-based Filtering  
- 🌐 REST API Integration  
- ⚡ Smooth & Responsive UI  
- 📡 Asynchronous Data Handling  

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| Architecture | MVVM |
| Networking | Retrofit |
| Concurrency | Kotlin Coroutines |
| JSON Parsing | Gson / Moshi |
| Image Loading | Glide / Coil |
| UI | XML + ViewBinding |

---

## 🌐 API Provider

This project uses the **NewsAPI** service to fetch news data.

🔗 **Get your API key at:**  
https://newsapi.org/

Visit that link above, create a free account, and generate your key.

---

## 🔐 API Configuration

To run this project:

1. Go to https://newsapi.org/  
2. Sign up and get your API key.
3. Add the key inside `local.properties`:

```
NEWS_API_KEY=your_api_key_here
```

The API key is excluded from version control for security reasons.

---

## 📸 Screenshots

<p align="center">
  <img src="screenshots/home.png" width="250"/>
  <img src="screenshots/details.png" width="250"/>
</p>

---

## 📥 Installation

```bash
git clone https://github.com/yourusername/NewsApp.git
```

Open in Android Studio → Sync Gradle → Run on Emulator or Physical Device.

---

## 📈 Future Improvements

- 🔖 Bookmark Feature  
- 💾 Offline Caching (Room Database)  
- 📄 Pagination Support  
- 🔔 Push Notifications  
- 🧪 Unit Testing  

---

<p align="center">
  Developed by <b>Mikhil Ajmeera</b><br>
  B.Tech CSE | Android Developer
</p>
