SRC= restaurante/Main.java restaurante/Restaurante.java

.PHONY: build run

all: build run

build:
	@echo "=== Buildando ==="
	javac $(SRC) -d build
	@echo "=== Feito ==="

run:
	@echo "=== Executando ==="
	java -cp build restaurante/Main
	@echo "=== Feito ==="