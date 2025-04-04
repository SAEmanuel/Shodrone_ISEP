@echo off

echo Verificando configuracoes...
echo Certifique-se de que JAVA_HOME esta configurado para o JDK correto.
echo Certifique-se de que o Maven esta no PATH do sistema.

:: Verifica se o Maven está instalado
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Erro: Maven nao encontrado! Certifique-se de que esta instalado e no PATH.
    exit /b 1
)

:: Executa Maven para copiar dependencias e empacotar
echo 🔧 Iniciando build...
mvn clean install --batch-mode --no-transfer-progress

if %ERRORLEVEL% EQU 0 (
    echo ✅ Build concluido com sucesso!
) else (
    echo ❌ Erro durante a build!
    exit /b 1
)
