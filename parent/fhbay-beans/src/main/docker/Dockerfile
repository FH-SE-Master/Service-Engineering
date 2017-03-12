FROM jboss/wildfly:10.1.0.Final
MAINTAINER herzog.thomas81@gmail.com

ARG app

USER root

RUN mkdir ${JBOSS_HOME}/ide

WORKDIR ${JBOSS_HOME}/ide

ADD start.sh start.sh
ADD install-offline.cli install-offline.cli
ADD init.sql init.sql
ADD maven/${app} ${app}

RUN chmod +x start.sh
RUN chown -R jboss:jboss ./*
RUN mv ${app} ../standalone/deployments/${app}

USER jboss

EXPOSE 8080
EXPOSE 9990

CMD "${JBOSS_HOME}/ide/start.sh"

