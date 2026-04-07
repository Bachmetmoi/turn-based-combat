@echo off
cd /d "%~dp0src"
if not exist "..\out" mkdir "..\out"
dir /s /b *.java > ..\sources.txt
javac -d ..\out @..\sources.txt
cd ..\out
java App
pause