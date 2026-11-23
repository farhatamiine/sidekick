# Sidekick — Claude Code guide

## 🧠 Second brain (Obsidian vault)
- Vault lives **outside the repo** at `~/Documents/sidekick/sidekick/` (call it `$BRAIN`).
- Key notes: `$BRAIN/NOW.md`, `$BRAIN/Projects/Sidekick/Board.md`, `$BRAIN/Ideas/Parking-Lot.md`, `$BRAIN/Sessions/`, `$BRAIN/Templates/`.
- Notes are short, bullets over paragraphs, `[[wikilinks]]` between notes.

## ▶️ Start of session
- Read `$BRAIN/NOW.md`, `$BRAIN/Projects/Sidekick/Board.md` and the latest note in `$BRAIN/Sessions/`.
- Tell me in 2-3 lines where we stopped and the first small step.

## 🔨 While working
- Stay on NOW.
- New ideas → one line in `$BRAIN/Ideas/Parking-Lot.md` (`- idea — why — #tag`), not built.
- Architecture decisions → new ADR in `$BRAIN/Decisions/` from `$BRAIN/Templates/Decision.md`.

## ⏹️ End of session
- Move cards on `Board.md` between lanes — keep the `kanban-plugin: board` frontmatter and the `%% kanban:settings %%` block intact.
- Write `$BRAIN/Sessions/YYYY-MM-DD.md` from `$BRAIN/Templates/Session.md`.
- Update `NOW.md` with a next step small enough to start in 5 minutes.

## 📐 Project rules
- Hexagonal / ports & adapters.
- Domain stays free of Spring, JPA and HTTP.
- Commit and push working milestones.
