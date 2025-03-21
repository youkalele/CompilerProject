@echo off

REM Copyright 2020, Gerwin Klein, Régis Décamps, Steve Rowe
REM SPDX-License-Identifier: BSD-3-Clause
REM
REM Please adjust JFLEX_HOME and JFLEX_VERSION to suit your needs
REM (please do not add a trailing backslash)

if not defined JAVA_HOME set JAVA_HOME=C:\Users\matthewcarter\Downloads\CompilerProject1\CompilerProject1\CompilerProject1\Oracle_JDK-23
if not defined JFLEX_HOME set JFLEX_HOME=C:\Users\matthewcarter\Downloads\CompilerProject1\CompilerProject1\jflex-1.9.1
if not defined JFLEX_VERSION set JFLEX_VERSION=1.9.1

set JAVA_HOME=C:\Users\matthewcarter\Downloads\CompilerProject1\Scanner\CompilerProject1\CompilerProject1
set JFLEX_HOME=C:\Users\matthewcarter\Downloads\CompilerProject1\Scanner\CompilerProject1\jflex-1.9.1\jflex-1.9.1
java -Xmx128m -jar "%JFLEX_HOME%\lib\jflex-full-%JFLEX_VERSION%.jar" %*
