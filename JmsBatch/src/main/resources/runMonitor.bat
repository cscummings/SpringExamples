echo on
set path="C:\Program Files\Java\jdk1.8.0_102\jre\bin";
set CLASSPATH=./config/*;./lib/*
SET /P QUALIFIER=Please enter the database reqion qualifier:

java -Djava.awt.headless=true -cp ./config/*  -jar lib/JmsBatch.jar --spring.config.location=./config/ --spring.profiles.active=application-dev nomads.schema=%QUALIFIER% spring.jpa.properties.hibernate.deault_schema=%QUALIFIER% database.schema=%QUALIFIER% spring.batch.table-prefix=%QUALIFIER%.BATCH_ $ *
 
 
