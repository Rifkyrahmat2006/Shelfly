# Shelfly

Personal Digital Material Organizer — Project Akhir Pemrograman Mobile.
Full spec: `docs/prd.md` (source: Stitch export).

## Stack
Kotlin + Jetpack Compose + Room + Navigation Compose. MVVM + Repository pattern.

## Struktur
```
core/theme        Person A — design tokens, typography
core/navigation    Person A — NavHost, routes
feature/home       Person A — Home screen
feature/shelf      Person B — Shelf detail, CRUD
feature/material   Person C — File import, open, metadata
feature/search     Person D — Search, filter, sort, favorite, recent
data/local         Room entities + DAO (Person B setup, dipakai semua)
data/repository    Repository layer (abstraksi UI <-> Room)
```

## Pembagian Modul (4 orang)
| Anggota | Modul | Fokus |
|---|---|---|
| A | UI & Navigation | Home, theme, NavHost, shared components |
| B | Database & Shelf | Room entity/DAO, ShelfRepository, Shelf CRUD |
| C | File Management | File picker, import, open material, error handling |
| D | Search, Filter & State | Search, filter, sort, favorite, recent, ViewModel state |

Semua anggota wajib paham: arsitektur, data flow, struktur DB, state management (buat sesi tanya-jawab individual).

## Cara mulai (per anggota)
1. Clone repo, buka di Android Studio (Hedgehog+), biarkan Gradle sync.
2. Butuh JDK 17. Emulator/device Android 8.0+ (minSdk 26).
3. Branch per fitur: `feature/nama-fitur`, PR ke `develop`, 1 reviewer sebelum merge.
4. Jangan ubah `core/theme` kalau bukan PIC — semua screen konsumsi token dari situ.

## Belum diisi (langkah selanjutnya)
- ViewModel per screen (Loading/Success/Empty/Error state) — lihat PRD section 32.
- File picker + URI handling (Storage Access Framework) — Person C.
- Search/filter/sort logic — Person D.
- Hilt DI kalau kompleksitas naik (skip dulu, pakai manual DI/Application class).
