FROM jboss-wildfly-keycloak-oracle-jdk8
MAINTAINER herzog.thomas81@gmail.com

# Version Management
WORKDIR /tmp

USER root

ADD start.sh start.sh
RUN dos2unix *.sh
RUN chmod +x start.sh

USER jboss

EXPOSE 8080
EXPOSE 9990

CMD "/tmp/start.sh"

