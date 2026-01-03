# Lost & Found App

A modern Android application that helps users report, discover, and recover lost and found items using smart matching logic and real-time notifications.

This project demonstrates real-world Android development using Firebase, clean architecture principles, and a complete end-to-end user flow.
A working APK is available for direct installation on Android devices.

📦 Download & Install (APK)
✅ Option 1: Download from GitHub Releases (Recommended)

The latest stable APK can be downloaded here:

👉 Download Latest APK

Steps to install:

Download the APK on your Android device

Open the file

Enable “Install from unknown sources” if prompted

Install and launch the app

⚠️ Option 2: APK Included in Repository

If you cloned this repository, the APK can also be found at:

/apk/LostAndFoundApp-v1.0.apk


⚠️ Note: Using GitHub Releases is preferred for versioning and updates.

✨ Features
Core Functionality

🔐 User Authentication – Firebase Email/Password login & registration

📱 Item Reporting – Report lost or found items with images and location

🎯 Smart Matching Engine – Rule-based matching using category, location, and description

🔔 Push Notifications – Alerts when potential matches are found

👤 User Profiles – View and manage your reported items

🖼️ Cloud Image Storage – Secure photo uploads via Firebase Storage

🛠️ Tech Stack

Platform: Android

Language: Java

Backend: Firebase (Auth, Firestore, Storage, Cloud Messaging)

Architecture: MVVM-ready, clean separation of concerns

UI: Material Design

Minimum SDK: API 24 (Android 7.0)

Target SDK: API 34 (Android 14)

📊 Project Status

✅ APK build available

📱 Tested on physical Android device

🔥 Firebase services connected

🚧 Actively improving features & UI

🚀 Getting Started (Developers)
Prerequisites

Android Studio (latest recommended)

JDK 11+

Firebase project

google-services.json

Setup
git clone https://github.com/your-username/your-repo.git
cd LostAndFoundApp


Place google-services.json in the app/ directory

Enable Firebase services:

Authentication (Email/Password)

Firestore

Cloud Storage

Cloud Messaging

Build the project:

./gradlew clean build


Run on device or emulator:

./gradlew installDebug

📁 Project Structure
LostAndFoundApp/
├── app/
│   ├── src/main/java/com/example/lostandfoundapp/
│   │   ├── models/
│   │   ├── fragments/
│   │   ├── activities/
│   │   ├── utils/
│   │   └── MyFirebaseMessagingService.java
│   ├── res/
│   └── AndroidManifest.xml
├── apk/
│   └── LostAndFoundApp-v1.0.apk
├── gradle/
├── README.md
└── settings.gradle.kts

🧠 Smart Matching Engine

Matching is performed using a weighted scoring system:

Category Match: 40%

Location Similarity: 35%

Description Keywords: 25%

Minimum Match Threshold: 60%

This approach allows accurate and explainable matching without heavy ML dependencies.

📱 User Flow
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

🧪 Testing
Completed

Manual testing on Android device

Firebase authentication flow

Image uploads & retrieval

Planned

Unit tests for matching logic

UI navigation tests

Firebase rule hardening

🚀 Roadmap
Phase 1 (Core)

 Improve reporting UI

 Enhance matches screen

 Refine notification logic

Phase 2 (Enhancements)

 In-app messaging

 User reputation system

 Item return confirmation flow

Phase 3 (Advanced)

 Cloud Functions for auto-matching

 Location radius filtering

 Optional ML-based matching

🤝 Contributing

Contributions are welcome.

Fork the repository

Create a feature branch

Commit changes

Open a pull request

📝 License

This project is licensed under the MIT License.
See the LICENSE file for details.

👤 Author

Developed as a real-world Android project focused on problem solving, clean architecture, and cloud integration.

📞 Support

If you encounter issues or have feature requests, please open a GitHub issue.

Last Updated: October 2025
Version: 1.0
APK Status: ✅ Available
