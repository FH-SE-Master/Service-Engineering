FROM jboss/wildfly:10.1.0.Final
MAINTAINER herzog.thomas81@gmail.com

USER root

RUN mkdir ${JBOSS_HOME}/ide

WORKDIR ${JBOSS_HOME}/ide

RUN curl -o ./db-driver.jar https://jdbc.postgresql.org/download/postgresql-42.0.0.jar

ADD start.sh start.sh
ADD install-offline.cli install-offline.cli
ADD init.sql init.sql
ADD maven/web.war web.war

RUN chmod +x start.sh
RUN chown -R jboss:jboss ./*
RUN mv web.war ../standalone/deployments/web.war

USER jboss

EXPOSE 8080
EXPOSE 9990

CMD "${JBOSS_HOME}/ide/start.sh"

