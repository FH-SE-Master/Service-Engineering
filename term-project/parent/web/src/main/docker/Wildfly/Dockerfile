FROM jboss/wildfly:10.1.0.Final
MAINTAINER herzog.thomas81@gmail.com

USER root

WORKDIR ${JBOSS_HOME}

RUN curl -o ./db-driver.jar https://jdbc.postgresql.org/download/postgresql-42.0.0.jar

ADD docker-entrypoint.sh ./
ADD install-offline.cli ./
ADD init.sql ./
ADD wildfly-jsf-installer-10.1.0.Final.cli ./wildfly-jsf-installer.cli

RUN chmod -R +x ./*.sh
RUN chown -R jboss:jboss ./*

USER jboss

# install datasource, queue
RUN ${JBOSS_HOME}/bin/jboss-cli.sh --file=./install-offline.cli && \
    rm -rf ${JBOSS_HOME}/standalone/configuration/standalone_xml_history && \
    rm -rf install-offline.cli && \
    rm -rf wildfly-jsf-installer.cli

ADD sso-rest-web.war ${JBOSS_HOME}/standalone/deployments

EXPOSE 8080
EXPOSE 9990

ENTRYPOINT [ "/opt/jboss/wildfly/docker-entrypoint.sh" ]

