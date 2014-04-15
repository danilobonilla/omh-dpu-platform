omh-dpu-platform
================

A DPU server platform with corresponding JS UI and sample DVU's.
Provides a structured environment in which DPU's can be implemented and easily
configured.

Requires:
- Maven 2.0.x
- Java 6

After cloning repository, do the following:

1) cd dpu-platform

2) mvn install:install-file -DartifactId=Concordia -Dversion=1.1.1 -DgroupId=name.jenkins.paul.john.concordia -Dfile=/path/to/Concordia.jar (this file is not available in maven repos)

3) mvn install -Dmaven.test.skip=true

4) cp dpu-api/target/dpu-api-1.0-SNAPSHOT.war (rename if needed) to Tomcat6 'webapps' dir

5) Start tomcat at http://localhost:8080/dpu-api-1.0-SNAPSHOT
