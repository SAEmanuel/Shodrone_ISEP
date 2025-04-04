@echo off
echo LOG: Generate Plantuml Diagrams

set "exportFormat=svg"
:: set "monochrome=true"
set "extra=-SdefaultFontName=Times New Roman -SdefaultFontSize=10"

for /r %%F in (docs\*.puml) do (
    echo Processing file: %%F
    java -jar libs\plantuml-1.2023.1.jar %extra% -t%exportFormat% "%%F"
)

echo Finished