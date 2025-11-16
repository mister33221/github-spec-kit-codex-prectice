# Repository Guidelines

## Project Structure & Module Organization
This repo currently ships two key paths: `spec-kit-learning.md`, a Traditional-Chinese walkthrough of Spec Driven Development, and `.idea/`, which stores JetBrains configurations for the Copilot + Spec Kit lab. Keep conceptual specs beside the tutorial by adding new notes under `specs/<sequence>-<slug>/` (for example, `specs/001-metronome-web-app/`). Treat `AGENTS.md` as the quick-start overlay for AI contributors and refresh it whenever tooling changes.

## Build, Test, and Development Commands
Authoring happens through the Spec Kit CLI highlighted in `spec-kit-learning.md`. Bootstrap with `uvx --from git+https://github.com/github/spec-kit.git specify init --here`, which installs the Specify runtime without polluting global Python. Run `/speckit.constitution "<principles>"`, `/speckit.specify "<feature>"`, then `/speckit.plan`, `/speckit.tasks`, and `/speckit.implement`. Issue these commands from the repository root so the `.specify/` memory folder stays adjacent to the docs.

## Coding Style & Naming Conventions
Narrative prose should stay concise, spec-first, and primarily in Traditional Chinese with English reserved for commands or quoted requirements, matching `spec-kit-learning.md`. When creating files, prefer lowercase, hyphen-delimited slugs such as `metronome-web-app`. Keep prompt examples in fenced code blocks labeled as `text`, indent nested lists with two spaces, and avoid trailing whitespace so JetBrains default formatting stays quiet.

## Testing Guidelines
Specs call out `vitest` as the unit-test harness; mirror that by naming files `*.spec.ts` or `*.test.ts` and colocating them with the source under future `src/` folders. Every new feature must link to at least one executable test plus a checklist line in `.specify/memory/checklist.md`. When an AI task reports "test failed twice", pause implementation, document the failure in the checklist, and unblock only after reproducing locally with `pnpm vitest run <pattern>`.

## Commit & Pull Request Guidelines
The repository currently lacks git metadata, so adopt a lightweight Conventional Commits style (`feat:`, `fix:`, `docs:`) and append the spec identifier (for example, `docs: add spec 003 metronome`). Pull requests should quote the `/speckit.*` commands they used, attach screenshots of CLI runs when helpful, and mention whether constitution, spec, plan, and checklist files were updated. Always link the task ID produced by `/speckit.tasks` so reviewers can trace automation steps.

## Agent Workflow Tips
Before editing, agents must reread the constitution excerpt inside `spec-kit-learning.md` to stay aligned with the MVP/no-overdesign principle. Stage work in short iterations--update the spec or checklist first, run the applicable `/speckit.*` command, and record any TODOs beneath the relevant heading so the playbook stays self-healing for the next run.
