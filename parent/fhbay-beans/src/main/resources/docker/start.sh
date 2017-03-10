#!/bin/bash

${JBOSS_HOME}/bin/add-user.sh admin admin --silent
${JBOSS_HOME}/bin/standalone.sh --debug -b 0.0.0.0 -bmanagement 0.0.0.0