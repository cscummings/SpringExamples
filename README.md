# SpringExamples
Example Spring Applications that illustrate various uses of Spring FrameWork in a working environment

JmsBatch - reads a JMS IBM MQ, processes it, and then writes the result into a DB2 table. The Spring Batch tables
reside in a different schema on a different machine and utilizes the BatchConfigurer so that all tables are 
successfully accessed. This batch job also sends an email if there is a failure
