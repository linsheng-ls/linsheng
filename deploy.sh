#!/bin/bash

echo "==== Step 1: 编译打包项目 ===="
cd ~/aws-web-demo
mvn clean package

echo "==== Step 2: 检查并杀掉占用 8080 端口的旧进程 ===="
PID=$(sudo lsof -t -i:8080)
if [ "$PID" != "" ]; then
  echo "发现旧进程 PID: $PID，正在杀掉..."
  kill -9 $PID
else
  echo "没有找到旧进程"
fi

echo "==== Step 3: 运行最新 Jar 包 ===="
nohup java -jar target/aws-web-demo-0.0.1-SNAPSHOT.jar > ~/app.log 2>&1 &

echo "==== 部署完成！可以通过 http://<你的公网IP>:8080 访问 ===="

