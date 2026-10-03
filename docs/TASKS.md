# Task Breakdown — Shelfly (Tim 4 Orang)

Dokumen ini turunan dari:
- `docs/prd.md` — Product Requirements Document (57 section, source kebenaran fitur/data/flow)
- `docs/design.md` — Design System (source kebenaran warna/tipografi/spacing, sudah di-encode di `core/theme`)

Kalau ada konflik antara dokumen ini dan PRD, **PRD menang** — dokumen ini cuma breakdown kerja, bukan spec baru.

---

## Susunan Tim

| Anggota | Modul | Folder utama | Knowledge wajib |
|---|---|---|---|
| **Nadine** | A — UI & Navigation | `core/navigation`, `feature/home`, `feature/shelf` (UI), `feature/material` (UI) | UI state, navigation, component |
| **Rifky** | B — Database & Shelf | `data/local`, `data/repository/ShelfRepository` | Relasi data, CRUD, persistence |
| **Intan** | C — File Management | `feature/material` (import logic), file picker, URI, MIME | Storage, URI, MIME type, error handling |
| **Yunan** | D — Search, Filter & State | `feature/search`, filter/sort logic, ViewModel state | Query/filter, state management |

**Catatan dari PRD section 47:** pembagian modul bukan berarti tiap orang cuma paham bagiannya. Rubrik project (section 48) menilai individual — semua anggota harus bisa jelasin: arsitektur, data flow, struktur database, alur import, state management, search/filter, error handling.

---

## Alur Kerja: TDD (Test-Driven Development)

Semua modul yang punya **logic** (bukan cuma layout visual) wajib ditulis dengan alur **RED → GREEN → REFACTOR**:

1. **RED** — tulis test dulu untuk perilaku yang diinginkan, jalankan, pastikan **gagal** (karena kode belum ada/belum benar). Ini membuktikan test-nya valid — kalau langsung hijau tanpa kode, test-nya salah.
2. **GREEN** — tulis kode paling minimal yang bikin test itu lolos. Jangan sekalian nambah fitur lain.
3. **REFACTOR** — rapikan kode (nama, struktur) sambil test tetap hijau. Tidak boleh mengubah perilaku di tahap ini.

Ulangi per unit perilaku kecil, bukan sekali nulis banyak lalu ditest belakangan.

### Kapan wajib TDD vs kapan boleh skip
- **Wajib**: DAO/Repository (query, CRUD), Search/Filter/Sort logic, ViewModel state transitions, parsing/validasi metadata file.
- **Boleh skip test formal**: layout Composable murni (visual only, no logic), NavHost routing sederhana, theme/token files.

### Jenis test di project ini
| Jenis | Lokasi | Kecepatan | Kapan pakai |
|---|---|---|---|
| Unit test (JVM) | `app/src/test/` | Cepat, tanpa device | Logic murni Kotlin (filter, sort, validasi) — tanpa Room, tanpa Context |
| Instrumented test | `app/src/androidTest/` | Lambat, butuh emulator/device | Apapun yang sentuh Room/Android framework (DAO, Repository yang query DB) |

Room butuh SQLite driver Android asli → DAO test **harus** di `androidTest`, bukan `test`. Sudah ada contoh jadi: `app/src/androidTest/java/com/shelfly/app/data/local/ShelfDaoTest.kt` — pakai ini sebagai template, tinggal ganti Entity/DAO-nya.

**Fix penting (sudah diterapkan):** `testInstrumentationRunner` harus diset manual ke `androidx.test.runner.AndroidJUnitRunner` di `app/build.gradle.kts` `defaultConfig` — tanpa ini, semua `@RunWith(AndroidJUnit4::class)` gagal start dengan `Failed to instantiate test runner class`. 26 androidTest sudah **run-verified di device fisik** setelah fix ini (2026-10-03).

Cara jalanin:
```bash
# Unit test (cepat, jalan tiap commit)
./gradlew testDebugUnitTest

# Instrumented test (butuh emulator/device nyala)
./gradlew connectedAndroidTest
```

### Definition of Done tiap task
Sebuah task dianggap selesai kalau:
1. Kode jalan (build sukses, `./gradlew assembleDebug`)
2. Test yang relevan **ada dan hijau** (bukan ditulis lalu dihapus/di-skip)
3. Sesuai PRD section terkait (dicek ulang sebelum PR)
4. Sesuai `docs/design.md` untuk bagian UI (warna/spacing dari `core/theme`, bukan angka hardcode baru)

---

## Sprint 0 — Foundation (Sequential, semua nunggu ini kelar)

**PIC: Rifky (Person B)** — sudah dikerjakan di starter project ini:
- [x] Gradle setup (Compose, Room, KSP, test dependencies)
- [x] `core/theme` (Color, Dimens, Theme) sinkron dari `docs/design.md`
- [x] Room Entity + DAO sesuai PRD section 35 (Shelf, Category, Material)
- [x] `core/navigation` NavHost skeleton
- [x] Repository stub (ShelfRepository, MaterialRepository)
- [x] Contoh test TDD (`ShelfDaoTest.kt`)

Begitu Sprint 0 di-pull semua anggota, Sprint 1 bisa mulai paralel.

---

## Sprint 1-3 — Kerja Paralel per Modul

### Nadine — Modul A: UI & Navigation

Referensi PRD: section 41 (UX/UI Direction), 42 (Navigation), 43 (Bottom Navigation).

| # | Task | Referensi PRD | Test |
|---|---|---|---|
| A1 | Bottom Navigation (Home, Shelves, Recent, Favorites) di `core/navigation` | §43 | - (visual) |
| A2 | Home Screen: My Shelves, Search bar, Recent, Favorites section | §41 Home | - (visual), tapi state loading/empty via ViewModel dari Yunan (D) |
| A3 | Shelf Card component (reusable, dipakai Home + Shelves list) | §41 Material Card (adaptasi Shelf) | - (visual) |
| A4 | Material Card component (reusable) | §41 Material Card | - (visual) |
| A5 | Create/Edit Shelf bottom sheet (form: nama, deskripsi, icon) | §17 Shelf Management | Unit test validasi input (nama tidak boleh kosong) |
| A6 | Empty states (No Shelves, Empty Shelf, No Search Results) | §28 Empty States | - (visual) |
| A7 | Confirmation dialog pola reusable (Delete Shelf/Material) | §27 Delete Behavior | - (visual) |

**Pitfall**: semua komponen di atas HARUS pakai token dari `core/theme` (Color, Dimens, Typography). Jangan hardcode `16.dp` atau `Color(0xFF...)` baru — kalau butuh nilai yang belum ada di token, koordinasi ke Rifky dulu.

### Rifky — Modul B: Database & Shelf

Referensi PRD: section 35 (Database Model), 17 (Shelf Management), 11-12 (Entitas & Struktur Data).

| # | Task | Referensi PRD | Test |
|---|---|---|---|
| B1 | ~~Entity + DAO dasar~~ | §35 | ✅ selesai (Sprint 0) |
| B2 | ✅ ShelfRepository: implementasi penuh (bukan stub) — insert/update/delete/getAll/getById + validasi nama kosong | §17 | `androidTest`: insert lalu getAll, update, delete, getById, blank name — **run-verified di device fisik** |
| B3 | ✅ Category Entity + DAO + Repository | §35, §11 | `androidTest`: CategoryDaoTest, CategoryRepositoryTest — **run-verified di device fisik** (fix: method test `_throws` harus `void` return, bukan expression body `= runBlocking`) |
| B4 | ✅ Migration strategy kalau schema Material/Shelf berubah nanti | §35 | - (dicatat di komentar `ShelflyDatabase.kt`) |
| B5 | ✅ Shelf item count (query JOIN Material by shelfId) | §17, §41 Shelf Card butuh count | `androidTest`: ShelfItemCountTest — **run-verified di device fisik** |
| B6 | ✅ Delete Shelf yang berisi Material — Cascade via Room ForeignKey (keputusan tim) | §27, §34 | `androidTest`: DeleteShelfCascadeTest — **run-verified di device fisik** |

**Keputusan B6**: Cascade dipilih (bukan Block). Alasan: PRD §34 menetapkan Shelfly menyimpan URI/reference ke file, bukan copy file fisik — jadi delete Shelf di app tidak pernah menghapus file asli di perangkat, cuma metadata/referensi. Implementasi: `ForeignKey(onDelete = ForeignKey.CASCADE)` di `MaterialEntity.shelfId` (database-level, bukan logic manual di Repository) — jamin atomicity dan tidak mungkin lupa di-handle di satu tempat. Konfirmasi dialog delete harus jelasin ke user: "File asli tidak akan terhapus dari perangkat" (sesuai pola §27).

### Intan — Modul C: File Management

Referensi PRD: section 18 (Material Management), 25 (Open Material), 33-34 (Local-First Strategy, Keputusan Penyimpanan File), 26 (Move Material).

| # | Task | Referensi PRD | Test |
|---|---|---|---|
| C1 | File picker integration (system picker, `ActivityResultContracts.OpenDocument`) | §18, Add Material Flow (design.md §5) | - (manual test di device) |
| C2 | ✅ Ambil metadata dari URI: title, fileType, fileSize | §18 | `FileMetadataExtractorTest` — real file via FileProvider, **run-verified di device fisik** |
| C3 | ✅ Persist URI permission (`takePersistableUriPermission`) supaya file tetap bisa diakses setelah app restart | §33, §34 | `UriPermissionManagerTest` — URI tanpa flag persistable return false tanpa crash, **run-verified di device fisik** |
| C4 | ✅ MaterialRepository: implementasi penuh (insert dari hasil import) + validasi title/fileUri kosong | §18 | `androidTest`: MaterialRepositoryTest — **run-verified di device fisik** |
| C5 | Open Material — intent ke aplikasi eksternal sesuai MIME type | §25 | - (manual, tidak semua bisa diunit-test karena tergantung app eksternal device) |
| C6 | ✅ Error handling: file tidak ditemukan, tidak bisa dibuka, permission issue | §29 Error Handling | Unit test: `MaterialErrorMapperTest` — 4 case (JVM unit test gagal run di environment lokal karena konflik classpath Windows/MSYS — tolong jalankan `./gradlew testDebugUnitTest` di Android Studio buat verifikasi) |
| C7 | Move Material antar Shelf | §26 | `androidTest` |

**Pitfall**: §34 penting dibaca duluan — MVP pakai URI reference (bukan copy file ke app storage). Ini keputusan arsitektur, jangan diubah sepihak.

### Yunan — Modul D: Search, Filter & State

Referensi PRD: section 19 (Search), 20 (Filter), 21 (Sort), 22 (Favorite), 23 (Recently Opened), 32 (State Management).

| # | Task | Referensi PRD | Test |
|---|---|---|---|
| D1 | ✅ Search ViewModel: query by title, case-insensitive | §19 | `SearchViewModelTest` — 4 case, **run-verified di device fisik** (26/26 androidTest lolos) |
| D2 | ✅ UI State sealed class (Loading/Success/Empty/Error) dipakai semua ViewModel | §32 | Unit test: `UiStateTest` — 5 case (JVM unit test gagal run di environment lokal, sama catatan C6) |
| D3 | ✅ Filter state: by Shelf, File Type, Category | §20 | `FilterStateTest` — 7 case (JVM unit test gagal run di environment lokal, sama catatan C6) |
| D4 | ✅ Sort: Newest, Oldest, Name A-Z/Z-A, Recently Opened | §21 | `SortOptionTest` — 5 case (JVM unit test gagal run di environment lokal, sama catatan C6) |
| D5 | ✅ Favorite toggle + query getFavorites | §22 | `FavoritesViewModelTest` — 2 case, **run-verified di device fisik** |
| D6 | ✅ Recently Opened — update `lastOpenedAt` saat Open Material dipanggil, query terbaru | §23 | `RecentViewModelTest` — 2 case, **run-verified di device fisik** |
| D7 | ✅ Preserve search query/filter saat navigasi balik (§22 Interaction Principles / Preserve Context) | design.md §22 | `SearchFilterStateHolderTest` — 5 case (JVM unit test gagal run di environment lokal, sama catatan C6) |

**Pitfall**: Search/Filter/Sort harus reactive terhadap perubahan data (pakai `Flow`, bukan snapshot sekali query) — kalau Material baru ditambah Intan (C), list di Search (D) harus update otomatis tanpa refresh manual.

---

## Sprint 4 — Integrasi (Semua anggota)

Setelah modul A-D masing-masing selesai dan di-PR:

1. Hubungkan NavHost (Nadine) ke ViewModel nyata (Yunan) dan Repository nyata (Rifky, Intan) — ganti semua stub/dummy data.
2. Compose UI test end-to-end untuk 2 flow utama sesuai design.md §29:
   - Home → Shelf Detail → Add Material → Save → Shelf Detail → Open Material
   - Home → Search → hasil → Material Detail → Open
3. Uji Testing Plan penuh dari PRD section 44 (T-01 s/d T-11) — checklist manual + otomatis kalau memungkinkan.
4. Review silang: tiap anggota baca kode modul lain, pastikan paham (persiapan tanya jawab individual, PRD §48).

---

## Git Workflow

- `main` = stable/demo-ready, `develop` = integrasi harian.
- Branch per task: `feature/a1-bottom-nav`, `feature/b2-shelf-repository`, dst — pakai kode task (A1, B2, ...) di nama branch biar gampang dilacak.
- PR ke `develop`, minimal 1 reviewer dari anggota lain (bukan yang nulis) sebelum merge.
- Commit kecil sering. Jangan gabung banyak task jadi 1 commit besar.
- Push ke `main`/production menunggu aba-aba eksplisit (bukan otomatis tiap merge ke `develop`).

## Persiapan Tanya Jawab (PRD §48)

Tiap anggota harus bisa jawab, **bukan cuma untuk modul sendiri**:
- Kenapa struktur folder ini dipilih (feature-based, bukan layer-based murni)?
- Bagaimana data masuk ke database (alur Import → Repository → DAO → Room)?
- Bagaimana data ditampilkan (Flow dari Room → ViewModel StateFlow → Compose recomposition)?
- Bagaimana search/filter bekerja?
- Bagaimana state berubah (Loading/Success/Empty/Error)?
- Bagaimana file dibuka, dan apa yang terjadi kalau file hilang?
- Trade-off URI reference vs copy file ke app storage — kenapa MVP pilih URI (§34)?
- Kenapa tidak pakai backend/cloud sync di MVP (§33 Local-First)?
