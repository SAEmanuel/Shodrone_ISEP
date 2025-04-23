#!/usr/bin/env bash

# Vai para a raiz do projeto (onde está o pom.xml)
cd "$(dirname "$0")/../.."

# Cria a pasta de logs dentro de scripts
mkdir -p scripts/logs

# Caminho do ficheiro de log
LOG_FILE="scripts/logs/quickbuild.log"

echo "🔧 Quick build iniciado..."
echo "➡️ Salvando log em: $LOG_FILE"

# Executa o Maven e salva o output no log
mvn -B "$1" dependency:copy-dependencies verify -D maven.javadoc.skip=true > "$LOG_FILE" 2>&1

# Verifica se a build teve sucesso
if [ $? -eq 0 ]; then
    echo "✅ Build concluído com sucesso!"
else
    echo "❌ Erro durante a build! Verifique o log em: $LOG_FILE"
    exit 1
fi
