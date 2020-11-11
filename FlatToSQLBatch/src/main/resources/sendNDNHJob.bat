echo on
set path="C:\Program Files\Java\jre7\bin";
set CLASSPATH="C:\Users\ccummings\workspace\Neon\SNAP_NDNH_BATCH-0.1.0-ALPHA\Deployables\config";
SET /P QUALIFIER=Please enter the reqion qualifier:

java -cp "C:\Users\ccummings\workspace\Neon\SNAP_NDNH_BATCH-0.1.0-ALPHA\Deployables\config"; -Djava.ext.dirs=./config;./lib -jar lib/SNAP_NDNH_BATCH-0.1.0-ALPHA.jar --spring.config.location=./config/ --spring.profiles.active=application --spring.batch.job.names=sendNDNHJob nomads.schema=%QUALIFIER% spring.jpa.properties.hibernate.deault_schema=%QUALIFIER% database.schema=%QUALIFIER% spring.batch.table-prefix=%QUALIFIER%.BATCH_ $*



