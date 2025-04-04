#!/usr/bin/env bash

echo "Verificando configurações..."
echo "Certifique-se de que JAVA_HOME está configurado para o JDK correto."
echo "Certifique-se de que o Maven está no PATH do sistema."

# Verifica se o Maven está instalado
if ! command -v mvn &> /dev/null
then
    echo "❌ Erro: Maven não encontrado! Certifique-se de que está instalado e no PATH."
    exit 1
fi

# Executa Maven para copiar dependências e empacotar
echo "🔧 Iniciando build..."
mvn clean install --batch-mode --no-transfer-progress

if [ $? -eq 0 ]; then
    echo "✅ Build concluído com sucesso!"
else
    echo "❌ Erro durante a build!"
    exit 1
fi

