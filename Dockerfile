FROM tomcat:latest
ADD . .
CMD ["catalina.sh","sh", "-c", "java $JAVA_OPTS -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -war /correct.war"]
