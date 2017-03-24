#!/bin/bash

if [ ! $INITIALIZED ] && [ $WILDFLY_USER ] && [ $WILDFLY_PASSWORD ]; then
    # create admin user
    ${JBOSS_HOME}/bin/add-user.sh -u -u ${WILDFLY_USER} -p ${WILDFLY_PASSOWORD} --silent
fi

export INITIALIZED=INITIALIZED

# start server in debug with admin console server and bind to any network in container
${JBOSS_HOME}/bin/standalone.sh -c standalone-full.xml --debug -b 0.0.0.0 -bmanagement 0.0.0.0