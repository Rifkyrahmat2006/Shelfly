# Task Breakdown — Shelfly (4 Anggota)

Referensi: `docs/prd.md` (section 47 Pembagian Modul, 35 Database Model), `docs/design.md` (Design System).

Urutan sprint: **Sprint 0 (Foundation, sequential)** → **Sprint 1–3 (paralel per modul)** → **Sprint 4 (integrasi)**.

---

## Sprint 0 — Foundation (sebelum kerja paralel, ±1 hari)

Harus selesai duluan, semua depend ke sini:

| Task | PIC | Output |
|---|---|---|
| Setup repo, branch protection, `develop` branch | A | Repo siap |
| Isi `core/theme` (Color, Typography, Spacing, Radius) dari `docs/design.md` | A | Sudah dibuat: `Color.kt`, `Dimens.kt`, `Theme.kt` |
| Room Entity + DAO (Shelf, Category, Material) | B | Sudah dibuat: `Entities.kt`, `ShelfDao.kt`, `MaterialDao.kt`, `ShelflyDatabase.kt` |
| NavHost + routes | A | Sudah dibuat: `ShelflyNavHost.kt` |

Status: kerangka sudah ada di repo. Sprint 0 tinggal review bareng, lanjut Sprint 1.

---

## Person A — UI & Navigation

**Modul:** Home, Shelves list, Navigation, shared components. Owner `core/theme`.

### Sprint 1
- [ ] `HomeScreen`: greeting text, search bar (navigate ke Search), "My Shelves" horizontal/grid card (2 kolom), section "Recently Opened" (List, ambil dari `MaterialRepository`), FAB Add Material.
- [ ] Bottom navigation bar: Home / Shelves / Recent / Favorites (`NavigationBar` Material3), reflect active route.
- [ ] Shared components di `core/ui/components/`: `PrimaryButton`, `SecondaryButton`, `ShelflyCard` (radius 12dp, outline 1px sesuai design.md), `SearchField`.

### Sprint 2
- [ ] `ShelvesScreen` (daftar semua Shelf) — card: icon, nama, jumlah material, last updated. FAB Create Shelf → bottom sheet (`CreateShelfSheet`).
- [ ] `EditShelfScreen` / sheet — form name+description+icon, tombol Delete terpisah (warna Error, bukan Primary).
- [ ] Empty state components: `EmptyState(icon, title, subtitle, ctaLabel, onCta)` reusable — dipakai di semua screen (No Shelves, Empty Shelf, No Search Result).

### Sprint 3
- [ ] Polish transisi antar screen (Navigation Compose animasi masuk/keluar sesuai design personality: calm, bukan flashy).
- [ ] Pastikan semua screen lain (punya B/C/D) sudah konsumsi `ShelflyTheme`, `Spacing`, `Radius` — bukan hardcode angka.
- [ ] Review visual hierarchy: 1 CTA dominan per screen (design.md section 30).

**Definition of Done:** Home + Shelves + navigasi jalan penuh pakai data asli dari Room (bukan dummy), empty state muncul kalau data kosong.

---

## Person B — Database & Shelf

**Modul:** Room, Repository, Shelf CRUD, ViewModel Shelf.

### Sprint 1
- [ ] Review/lengkapi `ShelfDao`, `MaterialDao` — pastikan query yang dibutuhkan Person D (search/filter/sort) sudah ada atau siap ditambah kolaboratif.
- [ ] `ShelfRepository` — implementasi penuh: `getAllShelves()`, `getShelfById()`, `insertShelf()`, `updateShelf()`, `deleteShelf()`.
- [ ] `ShelfViewModel` (di `feature/shelf/`) — state: `Loading / Success(List<Shelf>) / Empty / Error`, expose via `StateFlow`.

### Sprint 2
- [ ] Create Shelf flow: validasi nama tidak kosong, simpan ke Room, refresh list otomatis (Flow reaktif dari Room, bukan manual refresh).
- [ ] Edit Shelf: update record, handle Delete Shelf (dengan konfirmasi dialog — cek behavior di PRD section 27 "Delete Behavior": apa yang terjadi ke Material saat Shelf-nya dihapus — set null/cascade, putuskan & dokumentasikan).
- [ ] `ShelfDetailViewModel` — ambil material by `shelfId`, expose count buat ditampilkan di card.

### Sprint 3
- [ ] Database migration strategy kalau skema berubah (untuk MVP, `fallbackToDestructiveMigration()` cukup — jangan overengineer Migration class kalau belum perlu).
- [ ] Unit test sederhana: insert Shelf → query balik → assert data sama (pakai in-memory Room DB).
- [ ] Bantu integrasi: pastikan `MaterialRepository` (Person C) bisa query material by shelfId dengan benar (relasi FK).

**Definition of Done:** Create/Read/Update/Delete Shelf berfungsi penuh, persist setelah app di-restart, state Loading/Empty/Error kebaca di UI Person A.

---

## Person C — File Management

**Modul:** File picker, import, metadata, open material, error handling.

### Sprint 1
- [ ] File picker: `ActivityResultContracts.OpenDocument()` (Storage Access Framework) — pilih file dari device, ambil `Uri`, minta persistable permission (`takePersistableUriPermission`) biar bisa diakses lagi setelah app restart.
- [ ] Ekstrak metadata dari `Uri`: nama file, MIME type, ukuran file (pakai `ContentResolver.query` dengan `DocumentsContract`).
- [ ] `AddMaterialScreen` (2 step sesuai design.md): step 1 pilih file, step 2 form Shelf dropdown + Category dropdown + Save.

### Sprint 2
- [ ] `MaterialDetailScreen`: tampilkan file icon by type (mapping PDF/DOCX/PPTX/XLSX/Image/Other — design.md section 20), metadata table (type, size, added, last opened).
- [ ] Open Material: `Intent.ACTION_VIEW` dengan `Uri` + grant permission flag, handle "No compatible app found" (try-catch `ActivityNotFoundException` → tampilkan error state, bukan crash).
- [ ] Update `lastOpenedAt` di Room setiap kali material dibuka (buat fitur Recent milik Person D).

### Sprint 3
- [ ] Error handling penuh: file hilang/dipindah (`Uri` gak bisa diakses lagi) → tampilkan "This file is no longer available" + opsi Remove from Shelf (bukan crash, bukan stack trace mentah — sesuai design.md microcopy tone).
- [ ] Move Material antar Shelf (update `shelfId` di Room).
- [ ] Delete Material (dengan konfirmasi dialog, warna Error).

**Definition of Done:** Import file dari device → tersimpan di Shelf terpilih → bisa dibuka → metadata akurat → file hilang ditangani tanpa crash.

---

## Person D — Search, Filter & State

**Modul:** Search, filter, sort, favorite, recent, state management pattern.

### Sprint 1
- [ ] `SearchViewModel` + `SearchScreen`: query by title (case-insensitive, `LIKE '%query%'` di DAO), debounce input (jangan query tiap keystroke — pakai `snapshotFlow`/`debounce` 300ms).
- [ ] Search result list pakai `MaterialCard` reusable dari Person A.
- [ ] Empty state "No materials found" pakai komponen `EmptyState` dari Person A.

### Sprint 2
- [ ] Filter bottom sheet: by Shelf, File Type, Category (radio, sesuai design.md — bukan multi-select checkbox biar konsisten dgn spec). Reset/Apply button.
- [ ] Sort: Newest / Oldest / Name A-Z / Z-A / Recently Opened — implement di query DAO (`ORDER BY`).
- [ ] Favorite toggle: update `isFavorite` di Room, `FavoritesScreen` list terpisah (ambil `WHERE isFavorite = 1`).

### Sprint 3
- [ ] `RecentScreen`: ambil material `ORDER BY lastOpenedAt DESC`, group by Today/Yesterday/Earlier (logic date grouping pakai `LocalDate`).
- [ ] Preserve state saat navigasi balik (search query & filter aktif tetap ada — simpan di ViewModel, jangan di Composable local state biar survive recomposition/navigation).
- [ ] Kombinasi Search + Filter aktif bersamaan — pastikan query gabungan benar (title match AND filter shelf AND filter type).

**Definition of Done:** Search real-time jalan, filter+sort mengubah hasil sesuai kombinasi, Favorite & Recent persist dan sinkron dengan aksi di screen lain.

---

## Sprint 4 — Integrasi (semua orang, ±1-2 hari)

- [ ] Semua screen connect end-to-end: Home → Shelf → Material → Open (full flow tanpa dummy data).
- [ ] Jalankan Testing Plan PRD section 44 (T-01 s/d T-11) manual, catat hasil.
- [ ] Cross-review: tiap orang baca kode modul teman (wajib paham semua bagian untuk sesi tanya-jawab individual — PRD section 47).
- [ ] Rekam video demo sesuai flow PRD section 48 (Problem → Moodle/Download → File berantakan → Shelfly → Import → Organize → Search → Open).
- [ ] Siapkan jawaban Tanya Jawab (PRD section 48): trade-off URI vs app storage, alasan local DB, alasan no backend, edge cases, error handling.

---

## Dependency Graph (siapa nunggu siapa)

```
A (theme+nav)  ──┬──> semua screen pakai token dari sini
B (Room+Repo)  ──┼──> C butuh MaterialDao untuk simpan hasil import
                 └──> D butuh query search/filter/sort di DAO

Sprint 1: A & B jalan duluan (foundation dipakai C & D)
Sprint 2: C & D mulai setelah DAO dasar dari B ready
Sprint 3: semua paralel, saling isi gap
Sprint 4: integrasi bareng
```

## Git Workflow

- Branch: `feature/<nama>-<modul>` mis. `feature/rifky-search`.
- Commit kecil sering, push tiap fitur kelar (bukan nunggu numpuk).
- PR ke `develop`, 1 reviewer dari anggota lain (bukan self-merge).
- `main` cuma menerima merge dari `develop` yang sudah stabil, biasanya menjelang deadline/demo.
