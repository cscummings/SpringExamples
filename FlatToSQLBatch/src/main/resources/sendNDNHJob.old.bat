echo off
set CLASSPATH=./*;./lib/*;./SNAP_NDNH_BATCH-0.1.0-ALPHA/src/main;
set enddate=%DATE:~-4%-%DATE:~4,2%-%DATE:~7,2%

REM convert current date to julian substract 31 days convert back to yyyy-mm-dd format

for /F "tokens=2-4 delims=/ " %%a in ("%date%") do (
   set /A "a=(%%a-14)/12, JDN=(1461*(%%c+4800+a))/4+(367*(%%a-2-12*a))/12-(3*((%%c+4900+a)/100))/4+%%b-32075"
)
REM Subtract 31 days - should cover through last run
set /a jdn2 = %JDN%-31

REM CONVERT JULIAN DAY NUMBER TO MONTH, DAY, YEAR
SET /A W=(%JDN2%*100-186721625)/3652425, X=W/4, A=%JDN2%+1+W-X, B=A+1524, C=(B*100-12210)/36525, D=36525*C/100, E=(B-D)*10000/306001, F=306001*E/10000, DD=B-D-F, MM=E-1, YY=C-4716
IF %MM% GTR 12 SET /A MM-=12, YY+=1
REM INSERT LEFT ZEROS, IF NEEDED
IF %DD% LSS 10 SET DD=0%DD%
IF %MM% LSS 10 SET MM=0%MM%
REM Set begin date and include as --nomads.startdate=%begindate%
set begindate = %YY%-%MM%-%DD%

java -Djava.ext.dirs=./lib -jar lib/${project.artifactId}-${project.version}.jar  --spring.profiles.active=application-dev  --spring.batch.job.names=sendNDNHJob nomads.enddate="%enddate% $*
