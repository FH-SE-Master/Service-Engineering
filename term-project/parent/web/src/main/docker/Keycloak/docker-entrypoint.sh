#!/bin/bash

if [ ! $INITIALIZED ] && [ $KEYCLOAK_USER ] && [ $KEYCLOAK_PASSWORD ]; then
    keycloak/bin/add-user-keycloak.sh --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD
fi

export INITIALIZED=INITIALIZED

${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0

exit 0
