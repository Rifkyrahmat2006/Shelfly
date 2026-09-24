# Stitch Screens — Shelfly

Sumber: [Stitch project](https://stitch.withgoogle.com/projects/18007234712974719131) via REST API `stitch.googleapis.com/v1`.
Tiap screen: `.png` (screenshot asli) + `.html` (generated code, referensi struktur/layout — BUKAN buat di-copy-paste, cuma acuan translate ke Compose).

## Screens (14, sesuai `docs/design.md` §28)

- **Shelfly - Home Dashboard** — [home-dashboard.png](home-dashboard.png) | [home-dashboard.html](home-dashboard.html)
- **Shelfly - All Shelves** — [all-shelves.png](all-shelves.png) | [all-shelves.html](all-shelves.html)
- **Shelfly - Shelf Detail** — [shelf-detail.png](shelf-detail.png) | [shelf-detail.html](shelf-detail.html)
- **Shelfly - Material Detail** — [material-detail.png](material-detail.png) | [material-detail.html](material-detail.html)
- **Shelfly - Search Results** — [search-results.png](search-results.png) | [search-results.html](search-results.html)
- **Shelfly - Favorites** — [favorites.png](favorites.png) | [favorites.html](favorites.html)
- **Shelfly - Recent** — [recent.png](recent.png) | [recent.html](recent.html)
- **Shelfly - Create Shelf Modal** — [create-shelf-modal.png](create-shelf-modal.png) | [create-shelf-modal.html](create-shelf-modal.html)
- **Shelfly - Add Material** — [add-material.png](add-material.png) | [add-material.html](add-material.html)
- **Shelfly - Filter & Sort Sheet** — [filter-sort-sheet.png](filter-sort-sheet.png) | [filter-sort-sheet.html](filter-sort-sheet.html)
- **Shelfly - Empty Shelf State** — [empty-shelf-state.png](empty-shelf-state.png) | [empty-shelf-state.html](empty-shelf-state.html)
- **Shelfly - Missing File Error State** — [missing-file-error-state.png](missing-file-error-state.png) | [missing-file-error-state.html](missing-file-error-state.html)
- **Shelfly - Delete Confirmation Dialog** — [delete-confirmation-dialog.png](delete-confirmation-dialog.png) | [delete-confirmation-dialog.html](delete-confirmation-dialog.html)

## Overview (referensi peta semua screen, bukan 1 screen individual)
- [interactive-clickable-prototype.png](interactive-clickable-prototype.png) — canvas desktop gabungan semua screen + flow arrow

## Cara pakai (per anggota)
1. Buka `.png` screen yang jadi tanggung jawab kamu (lihat `docs/TASKS.md`).
2. `.html` referensi struktur/spacing/warna kalau `.png` kurang jelas — **jangan** disalin mentah, tetap translate manual ke Composable pakai token dari `core/theme` (`Color.kt`, `Dimens.kt`).
3. Kalau desain di Stitch update, ambil ulang via REST API `stitch.googleapis.com/v1/projects/18007234712974719131` (lihat `manifest.json` buat screen ID & prompt asli — download URL di situ sudah dibuang karena signed URL Google expire, generate ulang lewat API kalau perlu fetch lagi).
