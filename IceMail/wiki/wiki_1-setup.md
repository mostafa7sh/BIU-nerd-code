# 🔧 Setup and Run

## Prerequisites:
- Docker + Docker Compose
- Android Studio or emulator

## To Run Backend:
```bash
cd backend
docker-compose up --build
```

- API available at: http://localhost:3000/api
- MongoDB and Bloom server launched automatically

## To Run Android App:
- Open `android` in Android Studio
- Run on emulator or physical device
- Base URL for API in emulator: `http://10.0.2.2:3000/api`
