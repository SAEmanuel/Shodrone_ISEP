@echo off
setlocal

:: Cria a pasta de logs se não existir
if not exist logs (
    mkdir logs
)

:: Define o ficheiro de log
set "LOG_FILE=logs\quickbuild.log"

:: Define a fase Maven (usa verify por defeito se nenhum argumento for dado)
if "%~1"=="" (
    set "PHASE=verify"
) else (
    set "PHASE=%~1"
)

echo 🔧 Quick build iniciado...
echo ➡️ Salvando log em: %LOG_FILE%

:: Executa Maven e redireciona para o ficheiro de log
mvn -B %PHASE% dependency:copy-dependencies -D maven.javadoc.skip=true > "%LOG_FILE%" 2>&1

:: Verifica se a build foi bem sucedida
if %ERRORLEVEL% EQU 0 (
    echo ✅ Build concluído com sucesso!
) else (
    echo ❌ Erro durante a build! Verifique o log em: %LOG_FILE%
    exit /b 1
)
