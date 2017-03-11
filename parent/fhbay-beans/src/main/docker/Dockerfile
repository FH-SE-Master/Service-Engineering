FROM jboss/wildfly:10.1.0.Final
MAINTAINER herzog.thomas81@gmail.com

ARG app
# Version Management
WORKDIR /tmp

USER jboss

WORKDIR ${JBOSS_HOME}

ADD start.sh start.sh
ADD install-offline.cli install-offline.cli
ADD init.sql init.sql

USER root

RUN chmod +x start.sh

USER jboss

RUN ${JBOSS_HOME}/bin/jboss-cli.sh --file=./install-offline.cli

ADD maven/${app} /opt/jboss/wildfly/standalone/deployments/${app}

EXPOSE 8080
EXPOSE 9990

CMD "${JBOSS_HOME}/start.sh"

