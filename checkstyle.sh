#!/bin/bash

set -eu

mvn checkstyle:check \
    -Dcheckstyle.config.location=checkstyle.xml \
    -Dcheckstyle.failOnViolation=false \
    "$@"
