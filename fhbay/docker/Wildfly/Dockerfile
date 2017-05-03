FROM jboss/wildfly:10.1.0.Final
MAINTAINER herzog.thomas81@gmail.com

USER root

ARG WILDFLY_ADMIN_USER
ARG WILDFLY_ADMIN_PASSWORD
ARG WILDFLY_USER
ARG WILDFLY_PASSWORD

WORKDIR ${JBOSS_HOME}

RUN curl -o ./db-driver.jar https://jdbc.postgresql.org/download/postgresql-42.0.0.jar

ADD docker-entrypoint.sh ./docker-entrypoint.sh
ADD install-offline.cli ./
ADD init.sql ./

RUN chmod +x ./*.sh
RUN chown -R jboss:jboss ./*

USER jboss

# install datasource, queue
RUN ${JBOSS_HOME}/bin/jboss-cli.sh --file=./install-offline.cli && \
    rm -rf ${JBOSS_HOME}/standalone/configuration/standalone_xml_history && \
    rm -rf install-offline.cli

RUN ${JBOSS_HOME}/bin/add-user.sh -u ${WILDFLY_ADMIN_USER} -p ${WILDFLY_ADMIN_PASSWORD}
RUN ${JBOSS_HOME}/bin/add-user.sh -a -u ${WILDFLY_USER} -p ${WILDFLY_PASSWORD} -g guest 

EXPOSE 8080
EXPOSE 9990
EXPOSE 8787

ENTRYPOINT ${JBOSS_HOME}/docker-entrypoint.sh
