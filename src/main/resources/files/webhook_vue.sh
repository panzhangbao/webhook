#!/bin/bash
# vue 自动化部署

DEPLOY_PATH=/data/file/webhook
VUE_BACKUP_PATH=/home/admin/vue/backup
VUE_PATH=/home/admin/vue
VUE_NAME=platform

cd ${VUE_PATH}
mv -r ${VUE_NAME} ${VUE_BACKUP_PATH}/${VUE_NAME}_$(date "+%Y%m%d%H%M")
cp ${DEPLOY_PATH}/${VUE_NAME}.zip ./
unzip ${VUE_NAME}.zip