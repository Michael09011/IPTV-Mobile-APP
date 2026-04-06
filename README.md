# Minimal TV - Android IPTV application

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue) ![Android](https://img.shields.io/badge/Android-15-green) ![Gradle](https://img.shields.io/badge/Gradle-9.0.0-blue) ![Android Studio](https://img.shields.io/badge/Android%20Studio-2024.2-blue?style=flat-square&logo=androidstudio&logoColor=white) ![Gemini](https://img.shields.io/badge/Gemini-ready-purple?style=flat-square&logo=google&logoColor=white) [![Telegram](https://img.shields.io/badge/Telegram-Join-blue?logo=telegram)](https://t.me/micaca090)


<img width="256" height="256" alt="icon" src="https://github.com/user-attachments/assets/0211dcff-dc75-4376-8172-2e69b1f9c512" />

Minimal TV는 Jetpack Compose UI와 Media3 재생 엔진을 사용하는 안드로이드 IPTV 플레이어입니다. Android 8.0 이상에서 실행되며, Android 15(API 35)에 최적화되어 있습니다.

## 주요 기능

* 플레이리스트 및 EPG 지원
* HLS 기반 스트리밍 재생
* 즐겨찾기 및 최근 시청 목록
* 백그라운드 자동 갱신(WorkManager)
* 채널 검색 및 필터
* Edge-to-Edge 전체 화면 지원
* 언어변경 옵션

## 프로젝트 구조

```text
com.example.minimaltv
├── data
│   ├── local       # Room 데이터베이스, 설정 저장소
│   ├── model       # Playlist, Channel, EpgProgram
│   ├── parser      # M3U 및 EPG 파서
│   └── worker      # 백그라운드 업데이트 Worker
├── player          # ExoPlayer 관련 재생 관리
├── ui
│   ├── channel     # 채널 목록 및 검색 화면
│   ├── favorites   # 즐겨찾기 화면
│   ├── player      # 재생 화면 및 컨트롤
│   ├── playlist    # 플레이리스트 관리 화면
│   ├── settings    # 앱 설정 화면
│   └── theme       # Material 3 테마
└── MainActivity.kt # 앱 진입점 및 내비게이션
```

## 기술 스택

![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-7f52ff?style=flat-square&logo=jetbrains&logoColor=white) ![Media3](https://img.shields.io/badge/Androidx%20Media3-339933?style=flat-square&logo=android&logoColor=white) ![ExoPlayer](https://img.shields.io/badge/ExoPlayer-000000?style=flat-square&logo=google&logoColor=white) ![WorkManager](https://img.shields.io/badge/WorkManager-3DDC84?style=flat-square&logo=android&logoColor=white) ![Room](https://img.shields.io/badge/Room-3DDC84?style=flat-square&logo=sqlite&logoColor=white) ![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=flat-square&logo=kotlin&logoColor=white) ![Android 15](https://img.shields.io/badge/Android%2015-3DDC84?style=flat-square&logo=android&logoColor=white)

* Android Jetpack Compose
* Androidx Media3 / ExoPlayer
* WorkManager
* Room
* Kotlin 2.0.21
* Target SDK 35

## 빌드 및 실행

### 1. 프로젝트 복제
```bash
git clone https://github.com/michael/IPTV-Mobile-APP.git
cd IPTV-Mobile-APP
```

### 2. Android Studio
1. Android Studio에서 프로젝트를 엽니다.
2. Gradle Sync가 완료되면 `Run 'app'` 버튼을 클릭합니다.

### 3. CLI 빌드
```bash
./gradlew assembleDebug
./gradlew installDebug
```

## 시스템 요구 사항

* Android 8.0 이상
* Kotlin 2.0.21
* Gradle 9.0.0

## 라이선스

MIT License

---

Copyright © 2026 Michael. All rights reserved.
