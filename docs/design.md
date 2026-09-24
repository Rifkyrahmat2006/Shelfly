---
name: Shelfly — Personal Digital Material Organizer
colors:
  primary: "#315CFF"
  secondary: "#667085"
  background: "#F7F8FC"
  surface: "#FFFFFF"
  surfaceVariant: "#EEF2FF"
  on-background: "#171A24"
  on-surface: "#252938"
  on-surface-variant: "#667085"
  outline: "#E2E5EC"
  error: "#D92D20"
  success: "#12B76A"
  warning: "#F79009"
typography:
  fontFamily: "Inter"
  display-lg: { fontSize: "32px", fontWeight: 700 }
  headline-lg: { fontSize: "24px", fontWeight: 700 }
  headline-md: { fontSize: "20px", fontWeight: 700 }
  title-md: { fontSize: "16px", fontWeight: 600 }
  body-lg: { fontSize: "16px", fontWeight: 400 }
  body-md: { fontSize: "14px", fontWeight: 400 }
  label-md: { fontSize: "14px", fontWeight: 600 }
  label-sm: { fontSize: "12px", fontWeight: 600 }
spacing: { xs: "4px", sm: "8px", md: "12px", lg: "16px", xl: "24px", xxl: "32px" }
rounded: { sm: "8px", md: "12px", lg: "16px", xl: "20px", full: "999px" }
elevation:
  card: "0 1px 3px rgba(16, 24, 40, 0.06)"
  floating: "0 8px 24px rgba(16, 24, 40, 0.10)"
---

# Shelfly Design System

## Overview

Shelfly is a modern, calm, personal digital material organizer. The interface uses the metaphor of a digital shelf without becoming overly literal or looking like an e-book reader.

The product is general-purpose: any person can organize digital materials into custom Shelves. The primary project use case is a student organizing learning materials downloaded from Moodle.

The UI should feel closer to a polished personal productivity app than to a file manager or LMS.

---

# Design Direction

## Core Concept

> Your materials, organized by context.

My Shelves → Material → Find → Open

Shelves are the primary organizational object. Search, Recent, Favorites, Filter, Sort are secondary retrieval mechanisms.

## Design Personality

Clean, modern, friendly, focused, structured, minimal, slightly expressive through document icons and Shelf visuals. Professional enough for academic and work contexts. Avoid childish, overly decorative, or traditional-library looks.

---

# Colors

- **Primary #315CFF** — primary CTA, active navigation, selected controls, focus states. Use intentionally, not as full-screen background.
- **Secondary #667085** — secondary actions, supporting labels, metadata, placeholder text.
- **Background #F7F8FC** — main app background, subtle separation from white cards.
- **Surface #FFFFFF** — cards, sheets, dialogs, search fields, rows.
- **Surface Variant #EEF2FF** — selected states, soft highlights, primary-related chips, empty-state containers.
- **On Background #171A24** — primary headings.
- **On Surface #252938** — body/card content.
- **On Surface Variant #667085** — secondary info, metadata.
- **Outline #E2E5EC** — borders, dividers.
- **Error #D92D20** — destructive actions, validation.
- **Success #12B76A** — successful import, saved state.
- **Warning #F79009** — missing files, potentially destructive warnings.

---

# Typography (Inter)

| Style | Size | Weight | Use |
|---|---|---|---|
| Display | 32px | 700 | Hero headings (sparingly) |
| Headline Large | 24px | 700 | Screen titles |
| Headline Medium | 20px | 700 | Shelf names, subsections |
| Title Medium | 16px | 600 | Material names, card titles |
| Body Large | 16px | 400 | Important descriptive text |
| Body Medium | 14px | 400 | Default body text |
| Label Medium | 14px | 600 | Buttons, interactive labels |
| Label Small | 12px | 600 | Metadata, badges |

Avoid excessive uppercase. Sentence case by default.

---

# Spacing (8px-based)

4 xs / 8 sm / 12 md / 16 lg / 24 xl / 32 xxl.
Screen padding: mobile 16px, larger screens 24px. Card padding: standard 16px, compact 12px.

# Corner Radius

8px compact controls · 12px inputs/buttons/cards · 16px larger cards/sheets · 20px prominent containers · full = pills/tags/avatars.
Don't mix sharp and heavily rounded components in same hierarchy.

# Elevation

Mostly flat — prefer borders/background contrast over shadows. Standard card: 1px outline, very low elevation. Floating shadow only for FAB, bottom sheet, modal, context menu.

---

# Iconography

Simple outlined icons, consistent stroke weight: Home, Shelf/library, File, PDF, Document, Presentation, Spreadsheet, Search, Filter, Sort, Star, Clock, More, Plus, Arrow back, Folder, Download/import, Delete, Edit, Move. Document type recognizable via icon, not color alone.

---

# Navigation

Mobile-first bottom navigation: **Home / Shelves / Recent / Favorites**. Search prominent from Home and Shelves (top search field). Don't over-populate bottom nav.

---

# Screen Architecture (16 screens recommended, MVP priority 1–10)

1. **Home** — greeting, search, My Shelves (2-col cards), Recent, FAB Add Material, bottom nav.
2. **Shelves** — list of Shelf cards (icon, name, count, last updated), FAB Create Shelf.
3. **Shelf Detail** — search-in-shelf, filter chips (All/PDF/Docs/PPT), sort dropdown, material list, FAB Add Material.
4. **Material Detail** — file icon hero, title, context, metadata table (type/size/added/last opened), primary CTA "Open Material", secondary Favorite/Move.
5. **Add Material Flow** — step 1 system file picker, step 2 metadata (Shelf dropdown, Category dropdown), Save.
6. **Create Shelf** — bottom sheet: name, description (optional), icon picker.
7. **Edit Shelf** — same fields + Save Changes; Delete separated as destructive.
8. **Search** — search field, result count, result list, empty state.
9. **Filter & Sort** — bottom sheet with radio groups (Shelf/File Type/Category), Reset/Apply; separate Sort sheet (Newest/Oldest/Name A-Z/Z-A/Recently Opened).
10. **Favorites** — clean list, star icon, empty state with CTA.
11. **Recent** — grouped by Today/Yesterday.
12. Empty states (No Shelves / Empty Shelf / No Search Results).
13. Loading states — skeleton only for async; inline progress for local ops, avoid full-screen spinners for simple queries.
14. Error states — human-readable copy (e.g. "This file is no longer available." not stack traces).
15. Destructive action confirm dialogs (Delete Shelf/Material) — Error color, never primary color.
16. Missing File error screen.

---

# Components

- **Buttons**: Primary (filled, white text, 12px radius, ~48dp height), Secondary (surface+outline), Text button (See all/Cancel), Icon button (adequate touch target).
- **Inputs**: white surface, 1px outline, 12px radius, 48–52dp height, Primary focus indicator.
- **Chips**: filter/category/file-type; selected = soft Primary/SurfaceVariant bg + Primary text; unselected = surface + outline + secondary text.
- **Cards**: Material card (icon, title, context/category, type+size, favorite, overflow) and Shelf card (icon, name, count, optional recent activity) — keep info-dense but not tall.
- **Document icons**: PDF/DOC/PPT/XLS/EPUB/Image/Other — semantic mapping per type.
- **FAB**: Primary color, used for "Add Material" mainly on Home; avoid redundant FAB where CTA already visible.

---

# Interaction Principles

- Fast retrieval: Home → Search → Material → Open, or Home → Shelf → Material → Open (max path).
- Progressive disclosure: secondary actions behind overflow/bottom sheets, not all upfront.
- Preserve context: search query, active filter, scroll position when navigating back.

# Responsive

Mobile: 1-col material list, 2-col shelf cards, bottom nav, bottom sheets.
Tablet/large: 2–3 col Shelf grid, wider list, optional nav rail, persistent filters. Don't just stretch mobile cards.

# Accessibility

Strong contrast, no color-only status signaling, descriptive labels for icon-only controls, large touch targets, readable body text (≥12px), obvious focus/selected states.

# Microcopy

Concise, friendly, non-technical. "This file is no longer available." not `FileNotFoundException`. "No materials found" not "Query returned 0 records."

# Do's / Don'ts

**Do**: Shelf as central metaphor, calm/uncluttered UI, prominent search, consistent radius, Primary used sparingly, actionable empty states, distinct destructive actions.
**Don't**: LMS look, hardcode Semester/Course/Lecturer into core UI, literal wooden bookshelf everywhere, overload Home, multiple competing accents, heavy shadows everywhere, Delete visually equal to Open, cloud/sync concepts in MVP, AI as visual gimmick.

---

# Prototype Flow (for demo)

```
HOME → Open "Learning" Shelf → SHELF DETAIL → Add Material
  → File Picker → ADD MATERIAL → Save → SHELF DETAIL
  → Open Material → MATERIAL DETAIL → Open Material
```
Then retrieval: `HOME → SEARCH → query → RESULTS → MATERIAL DETAIL → OPEN`
Then secondary: `HOME → Recent` / `HOME → Favorites`

# Visual Hierarchy Rule

One dominant action per screen (Home→Search/open Shelf, Shelf Detail→Open/Add Material, Material Detail→Open Material, Create Shelf→Create, Add Material→Save, Search→Find, Filter→Apply). Never multiple primary-colored buttons competing.

# Final Design Intent

> A clean personal workspace for digital materials — not a complicated document management system, not a student-only LMS, not a traditional file manager.
