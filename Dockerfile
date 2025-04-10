# 使用官方 Java 镜像作为基础镜像（也可以根据你使用的 Java 版本选择）
FROM openjdk:17-jdk

# 复制构建好的 jar 文件（请确认路径）
COPY target/aws-web-demo-0.0.1-SNAPSHOT.jar /app.jar

# 设置容器启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]
