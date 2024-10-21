SHELL := /bin/bash
.PHONY: test install run help

test:
	@./gradlew clean build

run-api:
	@./gradlew clean bootRun

release: ## create release
	./scripts/create_release.sh
hotfix: ## create hotfix
	./scripts/create_hotfix.sh