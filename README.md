# Lost & Found App
A modern Android application to help users report and find lost and found items using intelligent matching and real-time notifications.


# Features
Core Functionality
🔐 User Authentication - Firebase Email/Password registration and login
📱 Item Reporting - Report lost or found items with photos and location
🎯 Smart Matching - AI-powered matching using category, location, and description
🔔 Push Notifications - Real-time alerts when matches are found
👤 User Profiles - Manage account and view personal items
🖼️ Image Storage - Cloud storage for item photos


# Technical Stack
Language: Java (Android)
Backend: Firebase (Auth, Firestore, Storage, Cloud Messaging)
Architecture: MVVM-ready with clean separation of concerns
UI Framework: Android Material Design
Minimum SDK: API 24 (Android 7.0)
Target SDK: API 34 (Android 14)

# Build Status
BUILD: ✅ SUCCESSFUL
VERSION: 1.0
GRADLE: Configured and tested
DEPENDENCIES: All Firebase products integrated

# Getting Started
Prerequisites
Android Studio (latest version recommended)
JDK 11 or higher
Firebase project configured
google-services.json file in app directory
Installation
Clone the repository bash git clone <repository-url> cd LostandFoundApp

Configure Firebase

Place your google-services.json in app/ directory
Ensure Firebase services are enabled in Firebase Console:
Authentication (Email/Password)
Firestore Database
Cloud Storage
Cloud Messaging
Build the project bash ./gradlew clean build

Run on emulator or device bash ./gradlew installDebug

# 📁 Project Structure
LostandFoundApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/lostandfoundapp/
│   │   │   ├── models/          # Data models (Item, User)
│   │   │   ├── fragments/       # UI Fragments (Home, Matches, Profile)
│   │   │   ├── utils/           # Firebase & Matching utilities
│   │   │   ├── Activities/      # Screen implementations
│   │   │   └── MyFirebaseMessagingService.java
│   │   ├── res/
│   │   │   ├── layout/          # XML layouts
│   │   │   ├── menu/            # Navigation menu
│   │   │   └── drawable/        # Icons and drawables
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts         # Dependencies & build config
│   └── google-services.json     # Firebase config
├── gradle/
├── settings.gradle.kts
└── README.md

# Smart Matching Engine

Matching is performed using a weighted scoring system:
Category Match: 40%
Location Similarity: 35%
Description Keywords: 25%
Minimum Match Threshold: 60%
This approach allows accurate and explainable matching without heavy ML dependencies.

# User Flow
Register/Login
   ↓
Home Screen
   ↓
Report Lost or Found Item
   ↓
Firebase Upload (Image + Data)
   ↓
Matching Engine
   ↓
Match Found → Push Notification


