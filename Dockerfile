
FROM mysql:latest

ENV MYSQL_DATABASE=client_order \
    MYSQL_ROOT_PASSWORD=password \
#    GOSU_VERSION=1.17 \
#    MYSQL_MAJOR=innovation \
#    MYSQL_VERSION=8.3.0-1.el8 \
#    MYSQL_SHELL_VERSION=8.3.0-1.el8

EXPOSE 3306
