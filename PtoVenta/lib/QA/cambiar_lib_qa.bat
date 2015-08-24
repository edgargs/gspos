@echo off
cls
echo ==================================
echo Cambiar las librerias para QA
rem 10.07.2015 ERIOS
echo ==================================
cd ..
for /f %%G IN ('svn info ^| findstr /C:"Root Path" ^| findstr /C:trunk') do set isTrunk=1
if NOT DEFINED isTrunk (
:: Elimina jars
del orbis-wsclient-mifarma*.jar
copy QA\*.jar .
:: Confirma librerias
svn commit -m lib_qa
) else (echo Es trunk)
pause