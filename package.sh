#!/bin/bash

set -eu

mkdir -p deploy
zip -r deploy/assignment.zip README.md pom.xml src
