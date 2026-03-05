# Minimal TV - 안드로이드 IPTV APP

Minimal TV는 고성능 미디어 엔진과 세련된 Jetpack Compose UI를 결합한 차세대 IPTV 플레이어입니다. 사용자 중심의 와이어프레임 설계를 바탕으로 **안드로이드 15(API 35)** 환경에 완벽 최적화되었습니다.

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
│   ├── player          # 가로 모드 전환 및 몰입형 전체 화면 플레이어
│   ├── playlist        # 이름 수정, 업데이트, 삭제 등 플리 관리 UI
│   ├── settings        # EPG URL 설정 및 하드웨어 가속 토글
│   └── theme           # Material 3 Edge-to-Edge 테마 및 투명 시스템 바
└── MainActivity.kt     # 앱 진입점 및 고도화된 Navigation 설정
```

## 🌟 주요 업데이트 기능

### 1. 지능형 채널 관리
*   **실시간 검색**: 수백 개의 채널 중 원하는 방송을 즉시 찾아주는 실시간 필터링 시스템.
*   **카테고리 필터**: 뉴스, 영화, 스포츠 등 장르별로 채널을 분류하여 탐색 편의성 극대화.
*   **채널 로고 연동**: `Coil` 엔진을 통해 M3U 리스트의 실제 방송국 로고를 고화질로 로드.

### 2. 혁신적인 플레이어 경험
*   **원터치 전체 화면**: 아이콘 클릭 한 번으로 가로 모드 및 몰입형(Immersive) 모드 전환.
*   **스마트 컨트롤 바**: 하단 재생바 영역에 채널 전환(이전/다음) 및 재생 컨트롤 통합 배치.
*   **시스템 바 투명화**: 안드로이드 15의 Edge-to-Edge를 적용하여 회색 바 없는 깨끗한 UI 제공.

### 3. 고도화된 설정 및 보안
*   **EPG 소스 커스텀**: 사용자 정의 XMLTV URL을 통해 방송 가이드 연동 가능.
*   **데이터 영속성**: 플레이리스트 이름 수정 및 삭제 등 모든 변경 사항이 Room DB에 즉시 저장.

## 🛠 기술 스택

*   **UI**: Jetpack Compose (Material 3), **Coil (Image Loading)**
*   **Media**: Androidx Media3 (ExoPlayer + **HLS 모듈**)
*   **Language**: Kotlin 2.0.21
*   **Target SDK**: 35 (Android 15)

## 🚀 빌드 가이드

```sh
# 서명되지 않은 디버그 APK 빌드
./gradlew clean assembleDebug
```

*   **APK 경로**: `app/build/outputs/apk/debug/app-debug.apk`

---
*Michael
