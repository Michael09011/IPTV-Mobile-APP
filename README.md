# Minimal TV - Android IPTV application

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue) ![Android](https://img.shields.io/badge/Android-15-green) ![Gradle](https://img.shields.io/badge/Gradle-9.0.0-blue)

<img src="https://github.com/user-attachments/assets/b5b27858-6db6-4a0f-956a-54a20decd698" width="300" alt="Screenshot">

Minimal TV는 고성능 미디어 엔진과 세련된 Jetpack Compose UI를 결합한 차세대 IPTV 플레이어입니다. 사용자 중심의 와이어프레임 설계를 바탕으로 **안드로이드 15(API 35)** 환경에 최적화되었으며, 안드로이드 8.0(Oreo) 이상 버전과의 폭넓은 호환성을 제공합니다.

## 🚀 최신 업데이트 (v1.0.2)

*   **지능형 자동 업데이트 시스템**: `WorkManager`를 도입하여 앱이 꺼져 있어도 설정한 주기(6/12/24시간)마다 M3U 및 EPG를 자동으로 갱신하고 알림을 제공합니다.
*   **초저지연 UI 렌더링**: 리컴포지션 최적화 및 `derivedStateOf` 적용으로 수천 개의 채널 리스트에서도 부드러운 스크롤과 즉각적인 검색 반응을 보장합니다.
*   **스마트 메모리 관리**: `Coil` 이미지 엔진 최적화를 통해 채널 로고 로딩 시 메모리 점유율을 대폭 낮췄습니다.
*   **데이터 무결성 강화**: 채널 고유 ID 시스템을 도입하여 플레이리스트 새로고침 시에도 즐겨찾기 상태가 완벽하게 유지됩니다.

## 📁 프로젝트 구조 (Project Structure)

```text
com.example.minimaltv
├── data
│   ├── local           # Room Database, SettingsManager, UpdateInterval
│   ├── model           # Playlist, Channel, EpgProgram
│   ├── parser          # M3uParser, EpgParser
│   └── worker          # UpdateWorker (백그라운드 자동 갱신 엔진)
├── player              # ExoPlayerManager (HLS 최적화 엔진)
├── ui
│   ├── channel         # 고성능 채널 필터링 및 검색 UI
│   ├── favorites       # 즐겨찾기 관리
│   ├── player          # 사이드바 연동 몰입형 플레이어
│   ├── playlist        # 최근 시청 및 플레이리스트 관리
│   ├── settings        # 자동 업데이트 주기 및 다국어 설정
│   └── theme           # Material 3 Edge-to-Edge 테마
└── MainActivity.kt     # 앱 진입점 및 NavHost 설정
```

## 🌟 주요 업데이트 기능

### 1. 지능형 자동 갱신 (New)
*   **백그라운드 동기화**: `WorkManager` 기반 스케줄링으로 최신 채널 목록을 놓치지 않고 유지.
*   **상태 표시줄 알림**: 업데이트 완료 시 시스템 알림을 통해 갱신된 플레이리스트 개수 확인 가능.

### 2. 혁신적인 플레이어 경험
*   **전천후 사이드바**: 시청 중 즉시 호출 가능한 반투명 채널 목록.
*   **스마트 컨트롤**: 음량, 비율(Fit/Fill/Zoom), 즐겨찾기를 한 곳에서 제어.
*   **Edge-to-Edge**: 안드로이드 15의 풀스크린 모드를 완벽 지원하여 광활한 시야 제공.

### 3. 고성능 아키텍처
*   **독립적 컨텍스트**: 플레이리스트 간 데이터 섞임 방지 로직 적용.
*   **고유 ID 시스템**: 스트림 URL 기반 해싱으로 데이터 중복 방지 및 즐겨찾기 보존.

## 🛠 기술 스택

*   **프론트엔드**: Android (Jetpack Compose, Material 3, Coil)
*   **미디어 엔진**: Androidx Media3 (ExoPlayer + HLS)
*   **백그라운드 작업**: WorkManager
*   **데이터베이스**: Room Persistence Library
*   **언어**: Kotlin 2.0.21
*   **타겟 SDK**: 35 (Android 15)

## 🚀 빌드 가이드 (Build Guide)

### 1. 프로젝트 복제
```bash
git clone https://github.com/michael/IPTV-Mobile-APP.git
cd IPTV-Mobile-APP
```

### 2. IDE에서 빌드
1.  Android Studio Ladybug 이상에서 프로젝트 오픈.
2.  Gradle Sync 완료 후 상단 **Run 'app'** 버튼 클릭.

### 3. 터미널 명령어로 빌드 (CLI)
*   **APK 빌드 (Debug)**: `./gradlew assembleDebug`
*   **기기에 설치**: `./gradlew installDebug`

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 있습니다.

---

Copyright © 2026 Michael. All rights reserved.
