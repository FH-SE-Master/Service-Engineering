FROM jboss/base-jdk:8

ARG KEYCLOAK_VERSION
ARG POSTGRES_DRIVER_VERSION

# Enables signals getting passed from startup script to JVM
# ensuring clean shutdown when container is stopped.
ENV LAUNCH_JBOSS_IN_BACKGROUND 1
ENV JBOSS_HOME /opt/jboss/keycloak

USER root

RUN yum install -y wget

WORKDIR /opt/jboss/

RUN wget http://central.maven.org/maven2/org/postgresql/postgresql/${POSTGRES_DRIVER_VERSION}/postgresql-${POSTGRES_DRIVER_VERSION}.jar \
    && wget https://downloads.jboss.org/keycloak/$KEYCLOAK_VERSION/keycloak-${KEYCLOAK_VERSION}.tar.gz \
    && tar -zxf ./keycloak-${KEYCLOAK_VERSION}.tar.gz \
    && mv ./keycloak-${KEYCLOAK_VERSION} ./keycloak \
    && rm ./keycloak-${KEYCLOAK_VERSION}.tar.gz \
    && mkdir -p ./keycloak/modules/system/layers/base/org/postgresql/jdbc/main \
    && mv ./postgresql-${POSTGRES_DRIVER_VERSION}.jar ./keycloak/modules/system/layers/base/org/postgresql/jdbc/main/postgres-jdbc.jar

ADD docker-entrypoint.sh ${JBOSS_HOME}/
ADD setLogLevel.xsl ./
ADD changeDatabase.xsl ./
ADD module.xml ./keycloak/modules/system/layers/base/org/postgresql/jdbc/main/
ADD prepare-offline.cli ./

RUN chown -R jboss:0 ./ \
    && chmod -R g+rw ./ \
    && chmod -R +x ${JBOSS_HOME}/*.sh

USER jboss

RUN java -jar /usr/share/java/saxon.jar -s:./keycloak/standalone/configuration/standalone.xml -xsl:./setLogLevel.xsl -o:./keycloak/standalone/configuration/standalone.xml \
    && java -jar /usr/share/java/saxon.jar -s:./keycloak/standalone/configuration/standalone-ha.xml -xsl:./setLogLevel.xsl -o:./keycloak/standalone/configuration/standalone-ha.xml \
    && java -jar /usr/share/java/saxon.jar -s:./keycloak/standalone/configuration/standalone.xml -xsl:./changeDatabase.xsl -o:./keycloak/standalone/configuration/standalone.xml \
    && java -jar /usr/share/java/saxon.jar -s:./keycloak/standalone/configuration/standalone-ha.xml -xsl:./changeDatabase.xsl -o:./keycloak/standalone/configuration/standalone-ha.xml \
    && rm ./changeDatabase.xsl \
    && rm ./setLogLevel.xsl

RUN ${JBOSS_HOME}/bin/jboss-cli.sh --file=./prepare-offline.cli \
    && rm prepare-offline.cli

#Fix: http://stackoverflow.com/questions/20965737/docker-jboss7-war-commit-server-boot-failed-in-an-unrecoverable-manner
RUN rm -rf ${JBOSS_HOME}/standalone/configuration/standalone_xml_history/current

EXPOSE 8080

ENTRYPOINT [ "/opt/jboss/keycloak/docker-entrypoint.sh" ]

