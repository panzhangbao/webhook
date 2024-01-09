#!/bin/bash

# vue sing ×Ô¶¯²¿Êð

DEPLOY_PATH=/data/file/webhook
VUE_BACKUP_PATH=/home/admin/vue/backup
VUE_PATH=/home/admin/vue
VUE_NAME=website

cd ${VUE_PATH}
zip -r ${VUE_BACKUP_PATH}/${VUE_NAME}_$(date "+%Y%m%d%H%M").zip ${VUE_NAME}
rm -rf ${VUE_NAME}
unzip  ${DEPLOY_PATH}/${VUE_NAME}.zip -d ./