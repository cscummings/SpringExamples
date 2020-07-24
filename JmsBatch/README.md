# JmsBatch
Use Spring Batch to read a IBM MQ JMS Queue , transform message and use JPAWriter to write to a database.
Spring Batch tables are at a different jdbc:url than application tables
so a SpringBatch Configurer is used
