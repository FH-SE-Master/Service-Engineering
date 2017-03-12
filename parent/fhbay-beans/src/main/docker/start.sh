#!/bin/bash

# install datasource, queue
${JBOSS_HOME}/bin/jboss-cli.sh --file=./install-offline.cli
# create admin user
${JBOSS_HOME}/bin/add-user.sh -u admin -p admin --silent
# create guest user
${JBOSS_HOME}/bin/add-user.sh -a -u jboss -p fhbay-mdb1 -g guest --silent
# start server in debug with admin console server and bind to any network in container
${JBOSS_HOME}/bin/standalone.sh -c standalone-full.xml --debug -b 0.0.0.0 -bmanagement 0.0.0.0