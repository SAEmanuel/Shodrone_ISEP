#!/usr/bin/env bash

# Caminho absoluto para a raiz do projeto (onde está o script)
PROJECT_ROOT="$(cd "$(dirname "$0")" && pwd)"

# Cria a pasta de logs se não existir
mkdir -p "$PROJECT_ROOT/logs"

# Caminho do ficheiro de log
LOG_FILE="$PROJECT_ROOT/logs/quickbuild.log"

echo "🔧 Quick build iniciado..."
echo "➡️ Salvando log em: $LOG_FILE"

# Verifica se existe um argumento (como -DskipTests, por exemplo)
EXTRA_ARGS=""
if [ -n "$1" ]; then
    EXTRA_ARGS="$1"
fi

# Executa o Maven e salva o output no log
cd "$PROJECT_ROOT"
mvn -B $EXTRA_ARGS dependency:copy-dependencies verify -D maven.javadoc.skip=true > "$LOG_FILE" 2>&1

# Verifica se a build teve sucesso
if [ $? -eq 0 ]; then
    echo "✅ Build concluído com sucesso!"
else
    echo "❌ Erro durante a build! Verifique o log em: $LOG_FILE"
    exit 1
fi
