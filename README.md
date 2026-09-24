# Shelfly

Personal Digital Material Organizer — Project Akhir Pemrograman Mobile.
Full spec: `docs/prd.md` (PRD, 57 section). Design tokens: `docs/design.md` (Stitch export). UI mockup per screen: `docs/stitch-screens/` (screenshot + HTML dari Stitch, lihat `index.md` di situ). Pembagian tugas detail per orang: `docs/TASKS.md`.

## Stack
Kotlin + Jetpack Compose + Room + Navigation Compose. MVVM + Repository pattern. Alur kerja: TDD (RED-GREEN-REFACTOR) — detail di `docs/TASKS.md`.

## Struktur
```
core/theme        Rifky (setup) — design tokens, typography, dipakai semua
core/navigation    Nadine — NavHost, routes
feature/home       Nadine — Home screen
feature/shelf      Nadine (UI) + Rifky (data) — Shelf detail, CRUD
feature/material   Nadine (UI) + Intan (logic) — File import, open, metadata
feature/search     Yunan — Search, filter, sort, favorite, recent
data/local         Rifky — Room entities + DAO
data/repository    Rifky (Shelf) + Intan (Material) — Repository layer
```

## Pembagian Modul (4 orang)
| Anggota | Modul | Fokus |
|---|---|---|
| **Nadine** | A — UI & Navigation | Home, NavHost, shared components (Shelf/Material Card, bottom sheet, dialog) |
| **Rifky** | B — Database & Shelf | Room entity/DAO, ShelfRepository, Shelf CRUD |
| **Intan** | C — File Management | File picker, import, open material, URI, error handling |
| **Yunan** | D — Search, Filter & State | Search, filter, sort, favorite, recent, ViewModel state |

Detail task per orang (dengan referensi section PRD dan test yang wajib ditulis): **`docs/TASKS.md`**.

Semua anggota wajib paham: arsitektur, data flow, struktur DB, state management (buat sesi tanya-jawab individual — PRD §48).

## Cara mulai (per anggota)
1. Clone repo, buka di Android Studio (Hedgehog+), biarkan Gradle sync.
2. **Butuh JDK 17** (bukan JDK bundled Android Studio versi baru yang kadang JDK 21/25 — Kotlin 1.9.24/KSP belum kompatibel dengan JDK 25). Install: `winget install EclipseAdoptium.Temurin.17.JDK` (Windows) atau download dari https://adoptium.net/temurin/releases/?version=17
   Lalu di Android Studio: **File → Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK** → pilih JDK 17 yang baru diinstall (atau "Download JDK" langsung dari situ).
3. Emulator/device Android 8.0+ (minSdk 26).
4. Baca `docs/TASKS.md` bagian modul masing-masing sebelum mulai coding.
5. Tulis test dulu (RED), baru kode (GREEN), baru rapikan (REFACTOR). Lihat `app/src/androidTest/.../ShelfDaoTest.kt` sebagai contoh.
6. Branch per task: `feature/a1-bottom-nav` dst (kode task dari TASKS.md), PR ke `develop`, 1 reviewer sebelum merge.
7. Jangan ubah `core/theme` kalau bukan PIC — semua screen konsumsi token dari situ.

## Testing
```bash
./gradlew testDebugUnitTest        # unit test (logic murni, cepat)
./gradlew connectedAndroidTest     # instrumented test (Room/DAO, butuh emulator/device nyala)
```

## Belum diisi (langkah selanjutnya)
Lihat checklist lengkap per modul di `docs/TASKS.md` Sprint 1-3. Ringkas:
- ViewModel per screen (Loading/Success/Empty/Error state) — Yunan.
- File picker + URI handling (Storage Access Framework) — Intan.
- Search/filter/sort logic — Yunan.
- UI screens penuh (bukan skeleton) — Nadine.
- Hilt DI kalau kompleksitas naik (skip dulu, pakai manual DI/Application class).

