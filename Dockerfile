FROM tomcat:latest
ADD target/correct.war ./usr/local/tomcat/webapps/
EXPOSE 8091
CMD ["catalina.sh", "run"]
