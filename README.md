# 🔍 Lost & Found App

A modern Android application that helps users report and find lost and found items using intelligent matching and real-time notifications.

---

## ✨ Features

### Core Functionality
- 🔐 **User Authentication** – Firebase Email/Password registration and login  
- 📱 **Item Reporting** – Report lost or found items with photos and location  
- 🎯 **Smart Matching** – Intelligent matching using category, location, and description  
- 🔔 **Push Notifications** – Real-time alerts when matches are found  
- 👤 **User Profiles** – Manage account and view personal items  
- 🖼️ **Image Storage** – Cloud storage for item photos  

---

## 🛠️ Technical Stack

- **Language:** Java (Android)  
- **Backend:** Firebase (Auth, Firestore, Storage, Cloud Messaging)  
- **Architecture:** MVVM-ready with clean separation of concerns  
- **UI Framework:** Android Material Design  
- **Minimum SDK:** API 24 (Android 7.0)  
- **Target SDK:** API 34 (Android 14)  

---

## 📊 Build Status

- **Build:** ✅ Successful  
- **Version:** 1.0  
- **Gradle:** Configured and tested  
- **Dependencies:** Firebase Auth, Firestore, Storage, Cloud Messaging  

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version recommended)  
- JDK 11 or higher  
- Firebase project configured  
- `google-services.json` file  

---

### Installation

#### 1. Clone the repository
```bash
git clone <repository-url>
cd LostandFoundApp

---

Project Structure
LostandFoundApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/lostandfoundapp/
│   │   │   ├── models/        # Data models (Item, User)
│   │   │   ├── fragments/     # UI Fragments (Home, Matches, Profile)
│   │   │   ├── utils/         # Firebase & Matching utilities
│   │   │   ├── activities/    # Screen implementations
│   │   │   └── MyFirebaseMessagingService.java
│   │   ├── res/
│   │   │   ├── layout/        # XML layouts
│   │   │   ├── menu/          # Navigation menu
│   │   │   └── drawable/      # Icons and drawables
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts       # Dependencies & build config
│   └── google-services.json   # Firebase config
├── gradle/
├── settings.gradle.kts
└── README.md
