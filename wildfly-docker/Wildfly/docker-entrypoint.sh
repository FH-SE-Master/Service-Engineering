#!/bin/bash

# start server in debug with admin console server and bind to any network in container
${JBOSS_HOME}/bin/standalone.sh -c standalone-full.xml --debug -b 0.0.0.0 -bmanagement 0.0.0.0
