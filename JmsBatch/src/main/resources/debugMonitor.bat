echo on
set path="C:\Program Files\Java\jdk1.8.0_102\jre\bin";
set CLASSPATH=./config/*;./lib/*

java -Xdebug -Xrunjdwp:transport=dt_socket,address="5005",server=y -Djava.awt.headless=true -cp ./config/*  -jar lib/JmsBatch.jar --spring.config.location=./config/ --spring.profiles.active=application-dev nomads.schema=TEST spring.jpa.properties.hibernate.deault_schema=TEST database.schema=TEST spring.batch.table-prefix=TEST.BATCH_ $ *


