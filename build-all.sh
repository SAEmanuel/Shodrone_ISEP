#!/usr/bin/env bash

echo "=============================="
echo "🔧 Verificando configurações"
echo "=============================="
echo "Certifique-se de que JAVA_HOME está configurado corretamente."
echo "Certifique-se de que o Maven está no PATH do sistema."

if ! command -v java &> /dev/null; then
    echo "❌ Erro: Java não encontrado!"
    exit 1
fi

if [ -z "$JAVA_HOME" ]; then
    echo "⚠️  Aviso: JAVA_HOME não está definido."
fi

if ! command -v mvn &> /dev/null; then
    echo "❌ Erro: Maven não encontrado!"
    exit 1
fi

echo "=============================="
echo "🔧 Iniciando build do projeto"
echo "=============================="

mkdir -p logs
mvn clean install --batch-mode --no-transfer-progress | tee logs/build.log

if [ $? -eq 0 ]; then
    echo "✅ Build concluído com sucesso!"
else
    echo "❌ Erro durante a build!"
    exit 1
fi
