### webhook
- 目的：方便通知内、外网服务器，执行其内部已有脚本
- 功能：支持 IP 白名单、webhook 密钥、可执行脚本、文件上传，具体可在 `application.yml` 中配置


- 打包：`mvn clean package -Dfile.encoding=UTF-8 -Dmaven.testskip=true -B -e -U`
- 运行：`nohup java -jar webhook.jar > /dev/null 2>&1`
- 运行有日志：`nohup java -jar webhook.jar > ./webhook.log 2>&1`


- webhook 推送示例：`curl "http://localhost:12345/webhook?secret=yourSecret&name=yourScriptName"`
- 文件上传示例：`curl -X POST -F "file=@/path/to/file" -F "secret=yourSecret" http://localhost:12345/webhook/file`