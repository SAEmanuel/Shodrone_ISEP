@echo off

echo Verificando configuracoes...
echo Certifique-se de que JAVA_HOME esta configurado para o JDK correto.
echo Certifique-se de que o Maven esta no PATH do sistema.

:: Cria a pasta de logs se nao existir
if not exist logs (
    mkdir logs
)

:: Gera nome do ficheiro de log com data e hora
for /f %%i in ('powershell -Command "Get-Date -Format yyyy-MM-dd_HH-mm-ss"') do set "LOG_FILE=logs\build_%%i.log"

:: Verifica se o Maven está instalado
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Erro: Maven nao encontrado! Certifique-se de que esta instalado e no PATH.
    exit /b 1
)

:: Executa Maven e guarda o log
echo 🔧 Iniciando build...
mvn clean install --batch-mode --no-transfer-progress > "%LOG_FILE%" 2>&1

if %ERRORLEVEL% EQU 0 (
    echo ✅ Build concluido com sucesso!
    echo Log salvo em: %LOG_FILE%
) else (
    echo ❌ Erro durante a build!
    echo Verifique o log em: %LOG_FILE%
    exit /b 1
)
