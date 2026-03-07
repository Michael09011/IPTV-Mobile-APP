# Minimal TV - Android IPTV application

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue) ![Android](https://img.shields.io/badge/Android-15-green) ![Gradle](https://img.shields.io/badge/Gradle-9.0.0-blue)

<img src="https://github.com/user-attachments/assets/b5b27858-6db6-4a0f-956a-54a20decd698" width="300" alt="Screenshot">

Minimal TV는 고성능 미디어 엔진과 세련된 Jetpack Compose UI를 결합한 차세대 IPTV 플레이어입니다. 사용자 중심의 와이어프레임 설계를 바탕으로 **안드로이드 15(API 35)** 환경에 최적화되었으며, 안드로이드 8.0(Oreo) 이상 버전과의 폭넓은 호환성을 제공합니다.

## 🚀 최신 업데이트 (v1.0.1)

*   **플레이어 사이드바 도입**: 영상 시청 중 화면 좌측 사이드바를 통해 실시간으로 채널 목록을 확인하고 즉시 전환할 수 있습니다. (가로/세로 모드 공통 지원)
*   **3개국 언어 완벽 지원**: 한국어, 영어, 일본어 환경을 모두 지원하며, 설정 메뉴에서 실시간으로 변경 가능합니다.
*   **최근 시청 채널 세션**: 메인 화면 상단에 최근 시청한 채널 5개를 로고와 함께 배치하여 빠른 재접속을 지원합니다.
*   **UI/UX 정밀 최적화**: 투명도 50%의 반투명 사이드바 디자인과 상단 바 로고 적용으로 더욱 현대적인 인터페이스를 완성했습니다.

## 📁 프로젝트 구조 (Project Structure)

```text
com.example.minimaltv
├── data
│   ├── local           # Room Database, SettingsManager (데이터 저장 및 앱 설정 관리)
│   ├── model           # Playlist, Channel (Entity 데이터 구조)
│   └── parser          # M3uParser (M3U 메타데이터 및 로고 추출 로직)
├── player              # ExoPlayerManager (HLS 전용 소스 빌더 포함 재생 엔진)
├── ui
│   ├── channel         # 실시간 채널 검색 및 카테고리 필터링 UI
│   ├── favorites       # 즐겨찾기 그리드 및 토글 관리
│   ├── player          # 사이드바 채널 전환 및 몰입형 플레이어
│   ├── playlist        # 최근 시청 채널 및 플레이리스트 관리 UI
│   ├── settings        # 다국어 설정, EPG 및 릴리즈 사이트 연동
│   └── theme           # Material 3 Edge-to-Edge 테마
└── MainActivity.kt     # 앱 진입점 및 고도화된 Navigation 설정
```

## 🌟 주요 업데이트 기능

### 1. 지능형 채널 관리
*   **실시간 검색 및 유지**: 검색어가 채널 이동 시에도 초기화되지 않아 탐색이 편리하며, 메인 화면 복귀 시에만 지능적으로 초기화됩니다.
*   **카테고리 필터**: 뉴스, 영화, 스포츠 등 장르별로 채널을 분류하여 탐색 편의성 극대화.
*   **채널 로고 연동**: `Coil` 엔진을 통해 M3U 리스트의 실제 방송국 로고를 고화질로 로드.

### 2. 혁신적인 플레이어 경험
*   **전천후 사이드바**: 상단 토글 버튼으로 언제든 채널 목록을 호출할 수 있으며, 투명 배경으로 시청 몰입감을 방해하지 않습니다.
*   **스마트 컨트롤 바**: 음량 슬라이더, 즐겨찾기 토글, 화면 비율(Fit/Fill/Zoom) 조정 기능을 상단 바에 통합 배치.
*   **시스템 바 투명화**: 최신 안드로이드의 Edge-to-Edge를 적용하여 상하단 바 영역까지 영상을 꽉 차게 감상할 수 있습니다.

### 3. 고도화된 설정 및 보안
*   **릴리즈 사이트 연동**: 설정 메뉴에서 공식 GitHub 릴리즈 페이지로 즉시 이동하여 최신 업데이트를 확인할 수 있습니다.
*   **데이터 독립성**: 각 플레이리스트 간의 채널 데이터가 섞이지 않도록 독립적인 컨텍스트 로딩 시스템 구축.
*   **최신 SDK 지원**: 안드로이드 15(API 35) 환경에서의 안정적인 구동 및 하드웨어 가속 지원.

## 🛠 기술 스택

*   **프론트엔드**: Android (Jetpack Compose, Material 3, Coil)
*   **백엔드**: 없음 (클라이언트 전용 앱)
*   **미디어 엔진**: Androidx Media3 (ExoPlayer + HLS 모듈)
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

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 있습니다.

---

Copyright © 2026 Michael. All rights reserved.
