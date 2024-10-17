SHELL := /bin/bash
.PHONY: test install run help

release: ## create release
	./scripts/create_release.sh