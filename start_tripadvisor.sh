#!/bin/bash

database_url=$1
database_user=$2
database_password=$3

start_tripadvisor() {
  server_port=$1
  database_url=$2
  database_user=$3
  database_password=$4

  java -Dspring.datasource.url="${database_url}" \
       -Dspring.datasource.username="${database_user}" \
       -Dspring.datasource.password="${database_password}" \
       -Dserver.address=127.0.0.1 \
       -Dserver.port=${server_port} \
       -jar tripadvisor-service-0.0.1-SNAPSHOT.jar
}

start_tripadvisor 5000 "${database_url}" "${database_user}" "${database_password}"