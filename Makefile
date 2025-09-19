.PHONY: build run

all: build run

build:
	@echo "=== Compilando ==="
	javac restaurante/*.java restaurante/*/*.java -d build
	@echo "=== Feito ==="

run:
	@echo "=== Executando ==="
	java -cp build restaurante/Main
	@echo "=== Feito ==="