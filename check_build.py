#!/usr/bin/env python3
import os
import sys

def check_project_structure():
    """프로젝트 구조를 확인합니다."""
    base_path = "/Users/michael/Workspace/Minimal TV"

    required_files = [
        "build.gradle.kts",
        "settings.gradle.kts",
        "gradle.properties",
        "app/build.gradle.kts",
        "app/src/main/AndroidManifest.xml",
        "app/src/main/java/com/example/minimaltv/MainActivity.kt"
    ]

    print("프로젝트 구조 확인:")
    for file_path in required_files:
        full_path = os.path.join(base_path, file_path)
        if os.path.exists(full_path):
            print(f"✓ {file_path}")
        else:
            print(f"✗ {file_path} - 누락됨")

    # 리소스 파일 확인
    res_files = [
        "app/src/main/res/values/themes.xml",
        "app/src/main/res/xml/backup_rules.xml",
        "app/src/main/res/xml/data_extraction_rules.xml"
    ]

    print("\n리소스 파일 확인:")
    for file_path in res_files:
        full_path = os.path.join(base_path, file_path)
        if os.path.exists(full_path):
            print(f"✓ {file_path}")
        else:
            print(f"✗ {file_path} - 누락됨")

    # 아이콘 파일 확인
    icon_dirs = ["mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi"]
    print("\n아이콘 파일 확인:")
    for density in icon_dirs:
        launcher_path = f"app/src/main/res/mipmap-{density}/ic_launcher.png"
        round_path = f"app/src/main/res/mipmap-{density}/ic_launcher_round.png"
        full_launcher = os.path.join(base_path, launcher_path)
        full_round = os.path.join(base_path, round_path)

        launcher_ok = os.path.exists(full_launcher)
        round_ok = os.path.exists(full_round)

        if launcher_ok and round_ok:
            print(f"✓ {density} 아이콘들")
        else:
            print(f"✗ {density} 아이콘들 - launcher: {'✓' if launcher_ok else '✗'}, round: {'✓' if round_ok else '✗'}")

if __name__ == "__main__":
    check_project_structure()
