#!/bin/bash
# This script will give you H2 database shell

java -cp h2.jar org.h2.tools.Shell -url "jdbc:h2:./h2db" -user "tully" -password "tully"