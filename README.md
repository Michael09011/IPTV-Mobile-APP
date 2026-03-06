# Minimal TV - Android IPTV application

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue) ![Android](https://img.shields.io/badge/Android-15-green) ![Gradle](https://img.shields.io/badge/Gradle-9.0.0-blue)

<img src="https://github.com/user-attachments/assets/b5b27858-6db6-4a0f-956a-54a20decd698" width="300" alt="Screenshot">

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

*   **프론트엔드**: Android (Jetpack Compose, Material 3, Coil)
*   **백엔드**: 없음 (클라이언트 전용 앱)
*   **미디어 엔진**: Androidx Media3 (ExoPlayer + HLS 모듈)
*   **언어**: Kotlin 2.0.21
*   **타겟 SDK**: 35 (Android 15)

## 🚀 빌드 가이드 (Build Guide)

이 프로젝트를 로컬 환경에서 빌드하고 실행하려면 다음 단계를 따르세요.

### 1. 사전 요구 사항
*   **Android Studio**: Ladybug (2024.2.1) 이상 권장
*   **JDK**: 17 또는 21 버전
*   **Gradle**: 9.0.0 (프로젝트에 포함된 Gradle Wrapper 사용)

### 2. 프로젝트 복제
```bash
git clone https://github.com/michael/IPTV-Mobile-APP.git
cd IPTV-Mobile-APP
```

### 3. IDE에서 빌드
1.  Android Studio를 실행하고 **Open**을 선택합니다.
2.  `IPTV-Mobile-APP` 디렉토리를 선택하여 엽니다.
3.  Gradle Sync가 완료될 때까지 기다립니다.
4.  상단 도구 모음에서 **Run 'app'** 버튼을 클릭하여 실행합니다.

### 4. 터미널 명령어로 빌드 (CLI)
IDE 없이 터미널에서 직접 빌드하려면 다음 명령어를 사용하세요.

*   **APK 빌드 (Debug)**:
    ```bash
    ./gradlew assembleDebug
    ```
*   **기기에 설치**:
    ```bash
    ./gradlew installDebug
    ```
*   **프로젝트 클린**:
    ```bash
    ./gradlew clean
    ```
*   **유닛 테스트 실행**:
    ```bash
    ./gradlew test
    ```

### 5. 문제 해결
*   `local.properties` 파일이 없는 경우, 프로젝트 루트에 직접 생성하고 `sdk.dir` 경로를 설정해야 할 수 있습니다.
*   Gradle 권한 문제가 발생하면 `chmod +x gradlew` 명령어를 실행하세요.

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 있습니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

---

Copyright © 2026 Michael. All rights reserved.
