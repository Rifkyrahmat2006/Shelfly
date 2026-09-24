# Product Requirements Document (PRD)

# Personal Digital Material Organizer

**Working Product Name:** Shelfly  
**Version:** 1.0  
**Project:** Project Akhir Mata Kuliah Pemrograman Mobile  
**Product Type:** Personal Digital Material Organizer  
**Primary Use Case:** Mahasiswa  
**Target Product:** General-purpose / pengguna umum

---

## 1. Ringkasan Produk

Shelfly adalah aplikasi mobile **personal digital material organizer** yang membantu pengguna mengorganisasi, menyimpan, mencari, dan mengakses kembali berbagai material digital berdasarkan konteks yang ditentukan pengguna sendiri.

Produk ini tidak diposisikan sebagai LMS, cloud storage, atau pengganti file manager. Shelfly merupakan **lapisan personal organization** untuk material digital yang berasal dari berbagai sumber.

Konsep utama aplikasi adalah **digital shelf (rak digital)**. Pengguna dapat membuat rak sesuai kebutuhan, memasukkan dokumen/material ke dalamnya, memberi kategori atau atribut tambahan, kemudian menemukan kembali material melalui struktur rak, pencarian, filter, favorit, dan riwayat akses.

### Prinsip utama

> **File manager menjawab "di mana file berada?", sedangkan Shelfly berfokus pada "untuk konteks apa file ini digunakan?".**

---

# 2. Latar Belakang

## 2.1 Masalah Umum

Pengguna saat ini memperoleh dokumen digital dari berbagai sumber, misalnya:

- LMS
- Aplikasi pesan
- Email
- Website
- Cloud storage
- Hasil berbagi file
- Transfer antarperangkat
- Download dari internet

Setelah file tersimpan di perangkat, pengelolaannya biasanya bergantung pada folder dan nama file.

Seiring bertambahnya jumlah dokumen, pengguna dapat mengalami kesulitan untuk:

- Mengetahui file tersebut berkaitan dengan apa.
- Mengelompokkan file berdasarkan konteks penggunaan.
- Menemukan kembali file yang pernah disimpan.
- Mengingat lokasi penyimpanan file.
- Membedakan file yang memiliki nama serupa.
- Menemukan kembali file yang pernah digunakan beberapa waktu sebelumnya.

Masalah fundamental yang ingin diselesaikan adalah:

> **Pengguna membutuhkan cara yang lebih berorientasi pada konteks untuk mengelola material digital, sehingga material yang berasal dari berbagai sumber dapat ditempatkan ke dalam ruang koleksi yang terstruktur dan mudah ditemukan kembali.**

---

# 3. Studi Kasus Utama: Mahasiswa

Walaupun Shelfly dirancang untuk pengguna umum, project ini menggunakan **mahasiswa sebagai objek/use case utama**.

## 3.1 Kondisi Saat Ini

Mahasiswa memperoleh materi dari berbagai sumber. Salah satu contoh adalah LMS Moodle.

Alur yang umum terjadi:

```text
Dosen
  ↓
Moodle
  ↓
Mahasiswa mengunduh materi
  ↓
File masuk ke Downloads
  ↓
File bercampur dengan file lain
  ↓
Beberapa waktu kemudian materi dibutuhkan kembali
  ↓
Mahasiswa kesulitan menemukannya
```

Contoh isi folder Downloads:

```text
Downloads/
├── Materi Pertemuan 5.pdf
├── Tugas.pdf
├── jurnal.pdf
├── Materi.pdf
├── document.pdf
├── Modul.pdf
├── Materi Pertemuan 3.pdf
└── file lainnya...
```

Masalahnya bukan semata-mata file tidak tersimpan.

Masalahnya adalah **file tersimpan tanpa konteks pengorganisasian yang sesuai dengan kebutuhan pengguna**.

---

# 4. Product Vision

> **Menjadi rak digital pribadi yang sederhana, fleksibel, dan mudah digunakan untuk mengubah kumpulan file digital yang berantakan menjadi koleksi material yang terorganisasi berdasarkan konteks pengguna.**

Shelfly harus memungkinkan pengguna untuk membuat struktur organisasi sendiri tanpa dipaksa mengikuti struktur akademik, pekerjaan, atau kategori tertentu.

---

# 5. Product Mission

Shelfly membantu pengguna:

1. Menyimpan material digital secara terorganisasi.
2. Mengelompokkan material berdasarkan konteks.
3. Menemukan material dengan cepat.
4. Mengakses kembali material yang pernah digunakan.
5. Mengelola koleksi digital secara fleksibel.

---

# 6. Value Proposition

## 6.1 Dibanding File Manager

File manager berfokus pada lokasi penyimpanan:

```text
Downloads/
Documents/
Pictures/
```

Shelfly berfokus pada konteks:

```text
📚 Belajar
📚 Pekerjaan
📚 Project
📚 Referensi
📚 Personal
```

## 6.2 Dibanding Cloud Storage

Cloud storage terutama berfokus pada:

- Penyimpanan
- Backup
- Sinkronisasi
- Berbagi file

Shelfly pada MVP berfokus pada:

- Organisasi
- Konteks
- Retrieval
- Metadata
- Search
- Filter
- Favorite
- Recent access

## 6.3 Positioning

Shelfly bukan:

- LMS
- Cloud storage
- File manager konvensional
- PDF reader
- Document editor

Shelfly adalah:

> **Personal Digital Material Organizer**

---

# 7. Target User

Shelfly ditujukan untuk pengguna umum.

## 7.1 Mahasiswa

Contoh penggunaan:

```text
📚 Semester 5
├── Pemrograman Mobile
├── Basis Data
└── Jaringan Komputer
```

## 7.2 Pengajar

Contoh:

```text
📚 Materi Mengajar
├── Matematika
├── Fisika
└── Informatika
```

## 7.3 Pekerja

Contoh:

```text
📚 Pekerjaan
├── Project A
├── Project B
└── Dokumentasi
```

## 7.4 Pengguna Umum

Contoh:

```text
📚 Personal
├── Resep
├── Buku
└── Referensi
```

Core system tetap sama. Yang berbeda adalah konteks penggunaan.

---

# 8. Persona Utama

## Persona: Mahasiswa

### Karakteristik

Mahasiswa memiliki banyak material digital yang diperoleh dari:

- Moodle
- WhatsApp
- Telegram
- Email
- Website
- Google Drive
- Teman
- Dosen

### Pain Points

- File bercampur di Downloads.
- Nama file terkadang tidak informatif.
- Sulit mengingat lokasi file.
- Sulit menemukan materi lama.
- Harus melakukan pencarian manual melalui file manager.

### Goal

Mahasiswa ingin:

> "Kalau nanti saya butuh materi tertentu, saya bisa menemukannya dengan cepat tanpa harus mengingat file tersebut disimpan di mana."

---

# 9. Product Principles

## 9.1 Context Over Location

Organisasi material berdasarkan konteks, bukan hanya lokasi file.

## 9.2 Flexible by Default

Pengguna menentukan sendiri nama dan struktur Shelf.

## 9.3 Simple to Retrieve

Material yang sudah disimpan harus mudah ditemukan kembali.

## 9.4 Local-First for MVP

MVP memprioritaskan penyimpanan dan pemrosesan di perangkat tanpa ketergantungan cloud.

## 9.5 Explainable Implementation

Setiap fitur harus memiliki logika yang dapat dipahami dan dijelaskan oleh anggota kelompok.

---

# 10. Konsep Utama: Hybrid Information Architecture

Shelfly menggunakan pendekatan **hybrid**.

Shelf menjadi struktur organisasi utama.

Sementara:

- Search
- Filter
- Sort
- Favorites
- Recent

menjadi jalur alternatif untuk menemukan material.

Konsepnya:

```text
                         USER
                           │
                           ▼
                    ┌─────────────┐
                    │    HOME     │
                    └──────┬──────┘
                           │
            ┌──────────────┼──────────────┐
            ▼              ▼              ▼
       My Shelves        Recent        Favorites
            │
            ▼
        ┌─────────┐
        │  SHELF  │
        └────┬────┘
             │
       ┌─────┴─────┐
       ▼           ▼
   Category     Materials
                   │
                   ▼
                Detail
                   │
                   ▼
                 Open
```

---

# 11. Definisi Entitas

## 11.1 Shelf

Shelf adalah ruang koleksi digital yang dibuat pengguna untuk mengorganisasi material.

Contoh:

```text
📚 Belajar
📚 Pekerjaan
📚 Skripsi
📚 Project
```

Shelf tidak memiliki makna akademik secara default.

## 11.2 Material

Material adalah file/dokumen digital yang dikelola oleh aplikasi.

Contoh:

- PDF
- DOCX
- PPTX
- XLSX
- EPUB
- Image
- File dokumen lain yang didukung perangkat

## 11.3 Category

Category adalah pengelompokan tambahan yang dapat digunakan pengguna.

Contoh mahasiswa:

```text
Modul
Materi
Tugas
Referensi
```

Contoh pekerja:

```text
Report
Contract
Invoice
Documentation
```

Category sebaiknya fleksibel dan tidak hard-coded ke konteks mahasiswa.

## 11.4 Favorite

Atribut yang menunjukkan material dianggap penting oleh pengguna.

## 11.5 Recent

Daftar material yang terakhir diakses/dibuka.

## 11.6 Metadata

Informasi tentang material.

Contoh:

- Nama
- Tipe file
- Ukuran
- Tanggal ditambahkan
- Tanggal terakhir dibuka
- Shelf
- Category

---

# 12. Struktur Data Konseptual

```text
User
 │
 ├── Shelves
 │      │
 │      └── Materials
 │              │
 │              ├── Category
 │              ├── Favorite
 │              └── Recent Access
 │
 ├── Favorites
 │
 └── Recent
```

Struktur database konseptual:

```text
Shelf
 ├── id
 ├── name
 ├── description
 ├── icon
 └── createdAt

Category
 ├── id
 ├── name
 └── createdAt

Material
 ├── id
 ├── title
 ├── fileUri
 ├── fileType
 ├── fileSize
 ├── shelfId
 ├── categoryId
 ├── isFavorite
 ├── createdAt
 └── lastOpenedAt
```

Jika Tag dikembangkan:

```text
Material
   ↕
MaterialTag
   ↕
Tag
```

dengan relasi many-to-many.

---

# 13. Scope Produk

## 13.1 In Scope — MVP

Fitur utama:

1. Create Shelf
2. Read Shelf
3. Rename Shelf
4. Delete Shelf
5. Import Material
6. Penyimpanan metadata
7. Organisasi material ke Shelf
8. Category
9. Search
10. Filter
11. Sort
12. Favorite
13. Recently Opened
14. Detail Material
15. Open Material
16. Move Material
17. Delete/Remove Material
18. Local persistence
19. Empty state
20. Error handling

---

# 14. Out of Scope — MVP

Fitur berikut tidak menjadi bagian dari MVP:

- Integrasi langsung dengan Moodle.
- Sinkronisasi cloud.
- Multi-device synchronization.
- Shared shelf.
- Collaboration.
- OCR.
- AI classification.
- AI summarization.
- Editor PDF.
- Editor DOCX.
- Backend multi-user kompleks.

Fitur tersebut dapat dimasukkan ke roadmap masa depan.

---

# 15. Functional Requirements

| ID | Fitur | Requirement | Priority |
|---|---|---|---|
| F-01 | Manage Shelf | User dapat membuat, melihat, mengubah nama, dan menghapus Shelf. | Must |
| F-02 | Import Material | User dapat memilih dokumen dari perangkat dan memasukkannya ke aplikasi. | Must |
| F-03 | Material Metadata | Sistem menyimpan metadata material. | Must |
| F-04 | Category | User dapat mengelompokkan material dengan Category. | Must |
| F-05 | Search | User dapat mencari material berdasarkan nama. | Must |
| F-06 | Filter | User dapat memfilter berdasarkan Shelf, Category, dan tipe file. | Must |
| F-07 | Sort | User dapat mengurutkan material berdasarkan nama atau waktu. | Should |
| F-08 | Favorite | User dapat menandai material sebagai favorit. | Should |
| F-09 | Recent | Sistem mencatat material yang terakhir dibuka. | Should |
| F-10 | Open Material | User dapat membuka file menggunakan viewer/aplikasi yang tersedia. | Must |
| F-11 | Move Material | User dapat memindahkan material ke Shelf lain. | Should |
| F-12 | Delete Material | User dapat menghapus material dari koleksi. | Must |

---

# 16. Modul Aplikasi

## 16.1 Home / Dashboard

Home menjadi pusat navigasi.

Informasi yang ditampilkan:

- My Shelves
- Recent
- Favorites
- Search
- Shortcut Add Material

Contoh:

```text
┌──────────────────────────────┐
│ Halo!                        │
│ Material kamu                │
│                              │
│ 🔍 Cari material...          │
│                              │
│ My Shelves                   │
│                              │
│ ┌──────────┐ ┌──────────┐    │
│ │ 📚       │ │ 📚       │    │
│ │ Belajar  │ │ Pekerjaan│    │
│ │ 24 files │ │ 17 files │    │
│ └──────────┘ └──────────┘    │
│                              │
│ Recent                       │
│ 📄 Database Room.pdf         │
│ 📄 Android Navigation.pdf   │
│                              │
│                         ＋   │
└──────────────────────────────┘
```

---

# 17. Shelf Management

User dapat:

- Membuat Shelf.
- Melihat Shelf.
- Rename Shelf.
- Menghapus Shelf.
- Membuka Shelf.
- Melihat jumlah material.

Contoh:

```text
Create Shelf

Name
[ Pemrograman Mobile ]

Description
[ Materi belajar ]

[ Create ]
```

---

# 18. Material Management

User dapat mengimpor material dari perangkat.

Flow:

```text
Shelf
 ↓
Add Material
 ↓
System File Picker
 ↓
Select File
 ↓
Read Metadata
 ↓
Assign Category
 ↓
Save
 ↓
Material muncul di Shelf
```

Metadata minimal:

```text
Title
File URI / Path Reference
File Type
File Size
Shelf
Category
Created At
```

---

# 19. Search

Search menjadi salah satu fitur utama karena masalah awal produk adalah **retrieval**.

Contoh:

```text
Search:
[ database ]
```

Hasil:

```text
Pemrograman Mobile
📄 Database Room.pdf

Basis Data
📄 Database Normalization.pdf

Pemrograman Web
📄 Database Connection.pdf
```

### Search Requirement

- Case-insensitive.
- Menampilkan hasil berdasarkan nama material.
- Search tidak mengubah data.
- Search dapat dikombinasikan dengan filter jika implementasi memungkinkan.

---

# 20. Filter

Filter dapat berdasarkan:

- Shelf
- Category
- File Type

Contoh:

```text
Filter

Shelf
☑ Pemrograman Mobile

Type
☑ PDF

Category
☑ Modul

[ Apply ]
```

---

# 21. Sort

Pilihan:

```text
Sort By

○ Name A-Z
○ Name Z-A
○ Newest
○ Oldest
○ Recently Opened
```

Sorting hanya mengubah urutan tampilan.

---

# 22. Favorite

User dapat menandai material:

```text
☆ → ★
```

Material favorit dapat diakses melalui:

```text
Favorites
```

Contoh:

```text
⭐ Favorites

📄 Database Room.pdf
📄 Android Navigation.pdf
📄 Normalization.pdf
```

---

# 23. Recently Opened

Sistem mencatat waktu terakhir material dibuka.

Contoh:

```text
Recently Opened

📄 Database Room.pdf
Pemrograman Mobile
Opened 10 minutes ago

📄 TCP-IP.pdf
Jaringan
Opened yesterday
```

Tujuan fitur ini adalah mengurangi kebutuhan pencarian ulang.

---

# 24. Detail Material

Contoh:

```text
Database Room.pdf

Type
PDF

Size
2.4 MB

Shelf
Pemrograman Mobile

Category
Modul

Added
12 September 2026

Last Opened
14 September 2026

[ Open ]
[ Favorite ]
[ Move ]
[ Delete ]
```

---

# 25. Open Material

Ketika user menekan Open:

```text
Material
   ↓
Check file reference
   ↓
Check file availability
   ↓
Determine MIME type
   ↓
Open using suitable Android handler
```

Jika berhasil:

```text
→ External PDF Viewer / Application
```

Jika gagal:

```text
Unable to open this file.

The file may be unavailable or
there is no application that can
open this file.

[ OK ]
```

---

# 26. Move Material

User dapat memindahkan material:

```text
Move Material

Current Shelf:
Pemrograman Mobile

Move To:
○ Semester 5
● Project
○ Referensi

[ Move ]
```

Setelah berhasil:

```text
Material
Current Shelf → New Shelf
```

---

# 27. Delete Behavior

Penghapusan harus menggunakan konfirmasi.

Contoh:

```text
Delete Material?

Database Room.pdf

This material will be removed
from your collection.

[ Cancel ] [ Remove ]
```

Jika implementasi memisahkan metadata dan file fisik, aturan penghapusan file harus ditentukan secara eksplisit.

---

# 28. Empty States

Empty state harus memberikan arahan tindakan.

## Belum ada Shelf

```text
📚
Belum ada Shelf

Buat Shelf pertama untuk
mulai mengorganisasi material.

[ Create Shelf ]
```

## Shelf belum memiliki material

```text
📄
Belum ada material

Tambahkan file pertama ke Shelf ini.

[ Add Material ]
```

## Search tidak menemukan hasil

```text
🔍
Material tidak ditemukan

Coba gunakan kata kunci lain
atau hapus filter yang aktif.
```

---

# 29. Error Handling

Aplikasi harus menangani kondisi berikut:

| Kondisi | Expected Behavior |
|---|---|
| Belum ada Shelf | Empty state + CTA Create Shelf |
| Shelf kosong | Empty state + CTA Import Material |
| Search tidak menemukan hasil | No result state |
| File tidak ditemukan | Error yang jelas |
| File tidak dapat dibuka | Error + feedback |
| Import dibatalkan | Tidak membuat material baru |
| Delete Shelf berisi material | Konfirmasi + jelaskan dampak |
| Database gagal | Error state, tidak crash |
| Permission/storage issue | Feedback yang dapat dipahami |

---

# 30. Non-Functional Requirements

| ID | Area | Requirement |
|---|---|---|
| NFR-01 | Usability | Alur import dan pencarian dapat dipahami tanpa tutorial panjang. |
| NFR-02 | Performance | Search dan navigasi koleksi lokal harus terasa responsif pada data uji project. |
| NFR-03 | Reliability | Aplikasi tidak crash ketika file tidak tersedia atau format tidak dapat dibuka. |
| NFR-04 | Data Persistence | Metadata tetap tersedia setelah aplikasi ditutup dan dibuka kembali. |
| NFR-05 | Maintainability | Kode dipisahkan berdasarkan tanggung jawab. |
| NFR-06 | Privacy | MVP tidak mengirim dokumen pribadi ke server. |
| NFR-07 | Accessibility | Teks, tombol, feedback, dan navigasi cukup jelas untuk penggunaan normal. |

---

# 31. Technical Architecture

Arsitektur yang direkomendasikan:

```text
┌──────────────────────────────┐
│          UI Layer            │
│ Screens / Components         │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│       ViewModel / State      │
│ UI State & User Interaction  │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│      Business / Use Case     │
│ Search / Filter / Move / etc │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│          Repository          │
└──────────────┬───────────────┘
               │
        ┌──────┴──────┐
        ▼             ▼
┌─────────────┐ ┌──────────────┐
│ Local DB    │ │ File Access  │
│ Room/SQLite │ │ URI/Storage  │
└─────────────┘ └──────────────┘
```

---

# 32. Recommended Technical Components

Implementasi akhir mengikuti teknologi yang diwajibkan/diperbolehkan pada mata kuliah.

Komponen konseptual:

### Presentation

- Screens
- Components
- Navigation
- UI state

### ViewModel / State Management

- Loading
- Success
- Empty
- Error
- Search state
- Filter state
- Selected Shelf

### Repository

Menjadi abstraksi antara UI/business logic dan sumber data.

### Local Database

Menyimpan:

- Shelf
- Category
- Material metadata

### File Access Layer

Menangani:

- File picker
- URI
- MIME type
- File availability
- Open file

---

# 33. Local-First Strategy

MVP menggunakan pendekatan local-first.

```text
User
 ↓
Shelfly
 ├── Local Database
 │      └── Metadata
 │
 └── Device Storage / URI
        └── Actual File
```

Keuntungan:

- Tidak membutuhkan backend kompleks.
- Tidak membutuhkan koneksi internet untuk fungsi inti.
- Lebih menjaga privasi dokumen.
- Fokus project dapat diarahkan pada mobile development.

---

# 34. Keputusan Penyimpanan File

Ada dua alternatif yang perlu dipertimbangkan.

## Option A — Menyimpan Reference/URI

Database menyimpan:

```text
fileUri
```

Aplikasi menggunakan URI untuk mengakses file.

### Kelebihan

- Tidak perlu menduplikasi file.
- Lebih hemat storage.
- Lebih dekat dengan system file picker.

### Risiko

- URI/file dapat menjadi tidak tersedia.
- Harus menangani permission dan persistence dengan benar.

## Option B — Menyalin File ke App Storage

Ketika user mengimpor:

```text
External File
     ↓
Copy
     ↓
Application Storage
```

### Kelebihan

- Aplikasi memiliki salinan yang lebih terkontrol.
- Struktur penyimpanan dapat dikelola aplikasi.

### Risiko

- Membutuhkan storage tambahan.
- Perlu aturan jelas untuk penghapusan dan pengelolaan file.

### Keputusan

Untuk MVP, mekanisme final harus dipilih setelah mempertimbangkan versi Android, API yang digunakan, dan aturan storage yang berlaku pada target aplikasi.

---

# 35. Database Model

Model awal:

```text
Shelf
-----
id PK
name
description
icon
createdAt
```

```text
Category
--------
id PK
name
createdAt
```

```text
Material
--------
id PK
title
fileUri
fileType
fileSize
shelfId FK
categoryId FK nullable
isFavorite
createdAt
lastOpenedAt nullable
```

Relasi:

```text
Shelf 1 ───── N Material

Category 1 ───── N Material
```

Jika Tag ditambahkan:

```text
Material N ───── N Tag
```

dengan tabel penghubung:

```text
MaterialTag
-----------
materialId
tagId
```

---

# 36. User Stories

| ID | User Story | Acceptance Criteria |
|---|---|---|
| US-01 | Sebagai user, saya ingin membuat Shelf agar material dapat dikelompokkan. | Shelf baru muncul di Home dan dapat dibuka. |
| US-02 | Sebagai user, saya ingin mengimpor file dari perangkat. | File berhasil dipilih dan metadata tersimpan. |
| US-03 | Sebagai user, saya ingin melihat material dalam Shelf. | Hanya material dari Shelf tersebut yang ditampilkan. |
| US-04 | Sebagai user, saya ingin mencari material. | Query nama menampilkan hasil yang sesuai. |
| US-05 | Sebagai user, saya ingin memfilter material. | Hasil berubah sesuai filter aktif. |
| US-06 | Sebagai user, saya ingin menandai material favorit. | Material muncul di Favorites. |
| US-07 | Sebagai user, saya ingin melihat material terakhir dibuka. | Material terbaru muncul pada Recent. |
| US-08 | Sebagai user, saya ingin membuka material. | File dibuka dengan handler yang sesuai atau menampilkan error. |
| US-09 | Sebagai user, saya ingin menghapus material. | Material hilang setelah konfirmasi. |
| US-10 | Sebagai user, saya ingin memindahkan material. | Material muncul di Shelf baru dan tidak muncul pada Shelf lama. |

---

# 37. User Journey Mahasiswa

## Before

```text
Moodle
 ↓
Download
 ↓
Downloads
 ↓
File bercampur
 ↓
Butuh materi lagi
 ↓
Cari manual
 ↓
Sulit ditemukan
```

## After

```text
Moodle
 ↓
Download
 ↓
Shelfly
 ↓
Select Shelf
 ↓
Import Material
 ↓
Category / Metadata
 ↓
Material terorganisasi
 ↓
Beberapa minggu kemudian
 ↓
Search / Shelf / Recent / Favorite
 ↓
Material ditemukan
 ↓
Open
```

---

# 38. Example Student Scenario

Mahasiswa mengunduh:

```text
Materi Database Room.pdf
```

dari Moodle.

Mahasiswa membuka Shelfly.

Memilih:

```text
📚 Semester 5
```

kemudian:

```text
📖 Pemrograman Mobile
```

Kemudian import:

```text
Materi Database Room.pdf
```

Metadata:

```text
Title:
Materi Database Room

Category:
Modul

Shelf:
Pemrograman Mobile
```

Beberapa minggu kemudian mahasiswa membutuhkan materi tersebut.

Daripada membuka:

```text
Downloads/
```

mahasiswa dapat:

```text
Shelfly
 ↓
Search
 ↓
"Database Room"
 ↓
Materi Database Room.pdf
 ↓
Open
```

---

# 39. Fitur Prioritas

## P0 — Wajib

Fitur yang harus ada agar produk memiliki core functionality:

- Shelf CRUD
- Import Material
- Material Metadata
- Local Persistence
- Open Material
- Search dasar
- Empty State
- Error Handling

## P1 — Penting

- Category
- Filter
- Sort
- Favorite
- Recent
- Move Material

## P2 — Jika Waktu Cukup

- Tag
- Smart suggestion berdasarkan nama file
- Duplicate detection
- Metadata lebih kaya

## P3 — Future Product

- Moodle integration
- Cloud sync
- Multi-device
- Collaboration
- AI
- OCR

---

# 40. Fitur yang Sengaja Tidak Dibuat pada MVP

Beberapa fitur terlihat menarik tetapi berpotensi memperbesar scope secara signifikan.

## Tidak membuat LMS

Karena tujuan aplikasi bukan:

- Mengelola kelas.
- Mengelola dosen.
- Mengelola nilai.
- Mengelola assignment.
- Menggantikan Moodle.

## Tidak membuat cloud storage

Karena tujuan MVP adalah organization dan retrieval.

## Tidak langsung membuat AI

AI bukan inti masalah.

Jika organisasi material belum berjalan baik, AI tidak memberikan value utama.

## Tidak membuat document editor

Membuka dan mengelola material sudah cukup untuk MVP.

---

# 41. UX/UI Direction

## Visual Concept

Metafora utama:

> **Digital Shelf / Rak Digital**

Namun metafora digunakan secara ringan.

Aplikasi tidak boleh terasa seperti:

- E-book reader.
- Perpustakaan digital.
- LMS.

Visual harus tetap terasa sebagai:

> modern personal organizer.

## Home

Fokus pada:

- My Shelves
- Search
- Recent
- Favorites

## Material Card

Contoh:

```text
┌──────────────────────────────┐
│ 📄  Database Room.pdf    ★   │
│     Pemrograman Mobile       │
│     PDF • 2.4 MB             │
└──────────────────────────────┘
```

---

# 42. Navigation

Navigasi yang disarankan:

```text
Home
│
├── My Shelves
│     └── Shelf Detail
│           └── Material Detail
│
├── Recent
│     └── Material Detail
│
├── Favorites
│     └── Material Detail
│
└── Search
      └── Search Result
            └── Material Detail
```

---

# 43. Proposed Bottom Navigation

Contoh:

```text
┌──────────────────────────────────┐
│                                  │
│             CONTENT              │
│                                  │
├──────────────────────────────────┤
│  Home    Shelves    Recent   ★   │
└──────────────────────────────────┘
```

Search dapat menjadi prominent action di Home atau top app bar.

---

# 44. Testing Plan

| ID | Test | Expected Result | Type |
|---|---|---|---|
| T-01 | Create Shelf | Shelf muncul setelah dibuat. | Functional |
| T-02 | Import PDF | PDF masuk ke Shelf dan metadata tersimpan. | Functional |
| T-03 | Restart App | Shelf/material tetap tersedia. | Persistence |
| T-04 | Search | Query menemukan material relevan. | Functional |
| T-05 | Filter | Hasil sesuai filter. | Functional |
| T-06 | Favorite | Favorite tetap tersimpan setelah restart. | Persistence |
| T-07 | Recent | Material terakhir dibuka muncul di Recent. | Functional |
| T-08 | Missing File | Aplikasi tidak crash dan menampilkan error. | Negative |
| T-09 | Unsupported Viewer | Feedback diberikan jika file tidak dapat dibuka. | Negative |
| T-10 | Delete | Material/Shelf terhapus setelah konfirmasi sesuai aturan. | Functional |
| T-11 | Move | Material berpindah Shelf. | Functional |
| T-12 | Empty State | Kondisi kosong memiliki CTA yang sesuai. | UX |

---

# 45. Success Metrics untuk Project

Project dianggap berhasil apabila:

1. User dapat membuat Shelf tanpa bantuan.
2. User dapat mengimpor material.
3. User dapat menemukan kembali material menggunakan Shelf.
4. User dapat menemukan material menggunakan Search.
5. User dapat menggunakan Filter.
6. User dapat membuka material.
7. Data tetap tersedia setelah aplikasi direstart.
8. Aplikasi tidak crash pada skenario utama.
9. Error penting ditangani dengan feedback yang jelas.
10. Seluruh anggota kelompok memahami data flow dan alasan desain aplikasi.

---

# 46. Risks & Mitigation

| ID | Risk | Mitigation |
|---|---|---|
| R-01 | Scope terlalu luas | Kunci MVP pada local-first material organization. |
| R-02 | Android storage/URI bermasalah | Gunakan mekanisme file picker yang sesuai dan uji persistence. |
| R-03 | File tidak dapat dibuka | Validasi URI/MIME type dan berikan error. |
| R-04 | Anggota hanya memahami modul sendiri | Lakukan code walkthrough dan latihan tanya jawab. |
| R-05 | UI bagus tetapi logika lemah | Prioritaskan data flow, CRUD, search/filter, dan error handling. |
| R-06 | Data hilang setelah restart | Uji database persistence dan lifecycle aplikasi. |
| R-07 | Waktu habis karena fitur tambahan | Gunakan prioritas P0/P1/P2/P3. |

---

# 47. Pembagian Modul Tim

Contoh untuk 4 anggota:

| Anggota | Modul | Output | Pengetahuan Wajib |
|---|---|---|---|
| A | UI & Navigation | Home, Shelf, Detail, Navigation | UI state, navigation, component |
| B | Database & Shelf | Entity, DAO, Repository, Shelf CRUD | Relasi data, CRUD, persistence |
| C | File Management | File picker, import, metadata, open | Storage, URI, MIME type, error |
| D | Search, Filter & State | Search, filter, sort, favorite, recent | Query/filter, state management |

**Catatan penting:** pembagian modul bukan berarti setiap anggota hanya memahami bagiannya.

Karena penilaian individual dapat menguji pemahaman kode, seluruh anggota harus memahami:

- Arsitektur.
- Data flow.
- Struktur database.
- Alur import.
- State management.
- Search/filter.
- Error handling.

---

# 48. Strategi Berdasarkan Rubrik Project

Berdasarkan rubrik tugas yang digunakan dalam project, penilaian memiliki komponen kelompok dan individual. Karena pemahaman kode dan evaluasi/tanya jawab menjadi komponen penting, project harus dirancang agar mudah dijelaskan secara teknis.

## Video Demo

Demo harus memperlihatkan:

```text
Problem
 ↓
Moodle / Download
 ↓
File berantakan
 ↓
Shelfly
 ↓
Import
 ↓
Organize
 ↓
Search
 ↓
Open
```

## Source Code

Source code harus menunjukkan:

- Separation of concerns.
- Struktur folder yang jelas.
- Naming konsisten.
- Repository/data layer.
- State management.
- Error handling.

## Pemahaman Kode

Setiap anggota harus mampu menjawab:

- Mengapa struktur tersebut dipilih?
- Bagaimana data masuk ke database?
- Bagaimana data ditampilkan?
- Bagaimana search bekerja?
- Bagaimana filter bekerja?
- Bagaimana state berubah?
- Bagaimana file dibuka?
- Apa yang terjadi ketika file hilang?
- Bagaimana persistence dilakukan?

## Tanya Jawab

Siapkan pembahasan:

- Trade-off storage URI vs app storage.
- Alasan menggunakan database lokal.
- Alasan tidak menggunakan backend.
- Alasan tidak mengintegrasikan Moodle pada MVP.
- Edge cases.
- Error handling.
- Data flow.
- Arsitektur aplikasi.

---

# 49. Proposed Demo Script

## Scene 1 — Problem

Tunjukkan folder Downloads:

```text
Downloads/
├── Materi.pdf
├── Modul.pdf
├── Tugas.pdf
├── document.pdf
└── ...
```

Narasi:

> "Materi telah berhasil di-download, tetapi ketika dibutuhkan kembali, pengguna harus mencarinya di antara banyak file lain."

## Scene 2 — Solution

Buka Shelfly.

Tampilkan:

```text
My Shelves
├── Belajar
├── Pekerjaan
└── Personal
```

## Scene 3 — Import

Buka:

```text
Belajar
 ↓
Add Material
```

Pilih file.

## Scene 4 — Organize

Set:

```text
Category: Modul
```

## Scene 5 — Retrieve

Beberapa saat kemudian:

```text
Search:
"Database"
```

Material ditemukan.

## Scene 6 — Open

Klik material.

```text
Open
```

File dibuka.

## Scene 7 — Additional Features

Tunjukkan:

- Favorite
- Recent
- Filter
- Sort
- Move

## Scene 8 — Persistence

Tutup aplikasi.

Buka kembali.

Material masih tersedia.

## Closing

Tekankan:

> "Shelfly bukan LMS baru dan bukan sekadar file manager. Shelfly mengorganisasi material berdasarkan konteks yang ditentukan pengguna."

---

# 50. Product Differentiation

Perbedaan utama:

| Product | Fokus |
|---|---|
| LMS | Distribusi dan pengelolaan pembelajaran |
| File Manager | Lokasi file pada perangkat |
| Cloud Storage | Penyimpanan, backup, sinkronisasi |
| PDF Reader | Membaca dokumen |
| Shelfly | Organisasi dan retrieval material berdasarkan konteks |

---

# 51. Future Roadmap

## Phase 1 — MVP

Core local organization:

```text
Shelf
Material
Category
Search
Filter
Favorite
Recent
Open
```

## Phase 2 — Smart Organization

Potensi fitur:

- Tag suggestion.
- Duplicate detection.
- Automatic category suggestion.
- Better metadata extraction.

Contoh:

```text
"Materi Pemrograman Mobile Pertemuan 5.pdf"

↓ Smart suggestion

Shelf:
Pemrograman Mobile

Category:
Materi

Meeting:
5
```

## Phase 3 — Learning Context

Khusus untuk use case pembelajaran:

- Progress membaca.
- Notes.
- Bookmark halaman.
- Learning session.
- Study statistics.

## Phase 4 — Cloud & Sync

- Backup.
- Multi-device sync.
- Cloud storage.
- Restore.

## Phase 5 — Source Integration

- Moodle.
- Google Drive.
- Email.
- Other document sources.

## Phase 6 — Collaboration

- Shared Shelf.
- Group collection.
- Permissions.
- Collaborative material management.

---

# 52. Open Questions

Beberapa keputusan teknis dan produk masih perlu dikunci:

1. Teknologi UI:
   - XML
   - Jetpack Compose

2. Database:
   - Room
   - SQLite

3. Apakah MVP membutuhkan login?
   - Rekomendasi awal: **tidak**, jika tetap local-first.

4. Mekanisme file:
   - URI reference
   - Copy ke app storage

5. Category:
   - Global
   - Per Shelf

6. Tag:
   - MVP
   - Future feature

7. Preview:
   - External viewer
   - Internal viewer

8. Apakah Shelf dapat memiliki sub-Shelf?
   - Rekomendasi MVP: **belum**, untuk menjaga scope tetap sederhana.

9. Apakah satu material dapat berada di lebih dari satu Shelf?
   - Rekomendasi MVP: **satu material memiliki satu Shelf utama**.
   - Shortcut/akses lintas Shelf dapat dikembangkan kemudian.

---

# 53. Recommended MVP Final

Jika waktu project terbatas, versi final yang paling realistis:

```text
                    SHELLY MVP
                        │
            ┌───────────┴───────────┐
            │                       │
          SHELF                  MATERIAL
            │                       │
       ┌────┴────┐          ┌───────┼────────┐
       │         │          │       │        │
     Create    Delete     Import  Detail   Open
       │                     │
     Rename                  │
                             ▼
                     ┌───────────────┐
                     │   Metadata    │
                     └───────┬───────┘
                             │
                  ┌──────────┼──────────┐
                  ▼          ▼          ▼
                Search     Filter    Favorite
                                         │
                                         ▼
                                       Recent
```

---

# 54. Product Definition

## Nama Produk

**Shelfly**

## Product Category

Personal Digital Material Organizer

## Tagline Kandidat

> **Organize what matters.**

Alternatif:

> **Your materials, organized.**

atau:

> **Find it when you need it.**

## One-Liner

> **Shelfly adalah rak digital pribadi yang membantu pengguna mengorganisasi dan menemukan kembali material digital berdasarkan konteks pengguna.**

## Core Problem

> Dokumen digital dari berbagai sumber mudah menumpuk di perangkat tanpa konteks organisasi yang jelas sehingga sulit ditemukan kembali.

## Core Solution

> Memberikan sistem Shelf yang fleksibel untuk mengorganisasi material berdasarkan konteks pengguna, dilengkapi Search, Filter, Favorite, dan Recent Access.

## Primary Use Case

> Mahasiswa mengelola materi pembelajaran yang telah diunduh dari Moodle.

## Product Scope

> General-purpose, local-first, personal material organization.

---

# 55. Final Product Statement

> **Shelfly bukan aplikasi khusus mahasiswa.**
>
> Mahasiswa digunakan sebagai **use case** karena permasalahan pengelolaan materi pembelajaran digital merupakan contoh konkret dari masalah yang ingin diselesaikan.
>
> Core product Shelfly bersifat general-purpose: siapa pun dapat membuat Shelf berdasarkan konteks mereka sendiri.
>
> Dengan pendekatan hybrid, Shelf menjadi struktur organisasi utama, sedangkan Search, Filter, Favorite, dan Recent menjadi cara alternatif untuk mengakses material.
>
> Dengan demikian, produk tidak berusaha menggantikan LMS, cloud storage, maupun file manager. Shelfly mengisi kebutuhan yang berada di antara sumber dokumen dan pengguna: **mengubah kumpulan file digital menjadi koleksi material yang terorganisasi dan mudah ditemukan kembali.**

---

# 56. Ringkasan Arsitektur Produk

```text
                    ┌──────────────────────┐
                    │        USER          │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      SHELLY APP      │
                    └──────────┬───────────┘
                               │
             ┌─────────────────┼─────────────────┐
             │                 │                 │
             ▼                 ▼                 ▼
        My Shelves          Recent           Favorites
             │
             ▼
          Shelf
             │
       ┌─────┴─────┐
       ▼           ▼
   Category     Material
                    │
       ┌────────────┼────────────┐
       ▼            ▼            ▼
    Search        Filter       Sort
                    │
                    ▼
                 Detail
                    │
                    ▼
                  Open
                    │
          ┌─────────┴─────────┐
          ▼                   ▼
    Local Database       Device File
      Metadata            / URI
```

---

# 57. Status Dokumen

**PRD Version:** 1.0

**Status:** Draft / Product Direction Approved

**Core Concept:** Approved

**Product Approach:** General-purpose

**Primary Use Case:** Mahasiswa

**Information Architecture:** Hybrid

**MVP Direction:** Local-first

**Next Design Activities:**

1. Finalisasi nama dan branding.
2. Finalisasi fitur P0/P1.
3. Finalisasi user flow.
4. Finalisasi database/ERD.
5. Finalisasi technical stack.
6. Wireframe UI.
7. Pembagian task kelompok.
8. Sprint implementation.
9. Testing.
10. Demo preparation.

---

## End of PRD v1.0
