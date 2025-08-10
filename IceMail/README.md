# 📧 Full Gmail Clone – Android + Node.js + MongoDB + C++ Bloom Filter

This is a full-stack email application inspired by Gmail, developed for **Advanced Systems Programming (ASP) – Exercise 5**.

It features a modern **Android app** using MVVM and Room, connected to a **Node.js backend** with **MongoDB**, and a **Bloom Filter TCP server** written in C++ for blacklist support.

---

## 📁 Project Structure

```
📁 android/         # Full Android app (Java, MVVM, Room, Retrofit)
📁 backend/         # Node.js backend with Docker, MongoDB, Bloom server
📁 wiki/            # Documentation (setup, screenshots, architecture, JIRA)
📄 README.md            # This file
```

---

## 📱 Android App Features

- ✅ Register & Login (with validations, profile picture, JWT auth)
- ✅ View inbox and individual emails
- ✅ Send emails to other users
- ✅ Save and manage drafts
- ✅ Mark emails as important
- ✅ Create, assign, edit and delete labels
- ✅ Search mails by subject/content
- ✅ Material Design UI with navigation drawer
- ✅ Logout with session handling

> Technologies: `Java`, `Room`, `LiveData`, `Retrofit`, `ViewModel`, `MVVM`, `Material UI`

---

## 🖥️ Backend Features (Node.js)

- REST API for users and mails
- MongoDB schema using Mongoose
- JWT-based access protection
- Multipart image upload
- Bloom Filter TCP client integration (port `12345`)
- Express + Dockerized deployment

> Main routes:
```
POST   /users             → Register user (image + validations)
POST   /tokens            → Login and get JWT
GET    /mails             → Get inbox
POST   /mails             → Send mail
GET    /mails/drafts      → Get saved drafts
POST   /mails/:id/draft   → Save mail as draft
GET    /mails/important   → Filter by important
POST   /mails/:id/important → Toggle important
GET    /mails/search?q=   → Search mails
POST   /labels            → Create label
PUT    /labels/:id        → Edit label
DELETE /labels/:id        → Delete label
POST   /mails/:id/label   → Assign label to mail
```

---

## 🐳 Docker Setup

To run the entire backend (Node.js + MongoDB + Bloom filter):

```bash
cd backend
docker-compose up --build
```

- `Node.js` API: `http://localhost:3000/api`
- `Bloom Filter TCP`: port `12345`
- `MongoDB`: local container

> From Android emulator: use `http://10.0.2.2:3000/api`

---

## 🚀 Running the Android App

1. Open `dir-android/` in Android Studio
2. Connect an emulator or real device
3. Build & run the app
4. Ensure server is running (see above)
5. Register/login and start mailing!

---

## 🗂️ Documentation (in dir-wiki)

- 📘 `1-setup.md`: How to build and run
- 📸 `2-screenshots.md`: UI screens
- 🧱 `3-architecture.md`: System structure
- 📋 `4-jira.md`: Sprint board and task management

---

## 👨‍💻 Authors

- **Saleh Sarsur**, **Murad Khalaily**, **Mostafa Shlsh** – Android developer, full MVVM implementation


---

## 🔐 Notes

- No secrets, keys, or passwords are stored
- App and backend validated manually on emulator and Docker

---

## ✅ Final Checklist

- [x] Android app: working, styled, tested
- [x] Backend: Node.js + MongoDB + Bloom integration
- [x] JWT secured endpoints
- [x] Dockerized backend
- [x] Documentation in wiki
