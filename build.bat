@echo off
set SRC=src
set BIN=bin
set LIB=lib\flatlaf-3.5.1.jar
set MAIN=metodosNumericos.Main

echo Compilando...
if not exist "%BIN%" mkdir "%BIN%"
dir /s /b "%SRC%\*.java" > sources.txt
javac -d "%BIN%" -cp "%LIB%" @sources.txt
if %errorlevel% neq 0 ( echo Error de compilacion & exit /b 1 )
del sources.txt
echo Compilacion exitosa.

echo.
echo Ejecutando...
java -cp "%BIN%;%LIB%" %MAIN%