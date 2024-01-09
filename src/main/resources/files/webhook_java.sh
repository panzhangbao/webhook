#!/bin/bash
# JAVA 自动化部署

DEPLOY_PATH=/data/file/webhook
JAR_BACKUP_PATH=/home/admin/java/backup
JAR_PATH=/home/admin/java/jar
JAR_NAME=webhook.jar

cd ${JAR_PATH}
cp ${JAR_NAME} ${JAR_BACKUP_PATH}/${JAR_NAME}_$(date "+%Y%m%d%H%M")
cp  ${DEPLOY_PATH}/${JAR_NAME} ${JAR_PATH}/${JAR_NAME}
kill -9 $(ps -ef | grep ${JAR_NAME} | grep -v grep | awk '{print $2}')
nohup java -Xms1024m -Xmx1024m -jar ${JAR_NAME} > /dev/null 2>&1 &