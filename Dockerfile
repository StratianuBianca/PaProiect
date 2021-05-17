FROM tomcat:latest
ADD target/correct.war correct.war
EXPOSE 8091
CMD ["catalina.sh", "run"]
ENTRYPOINT ["java","-war","/correct.war"]
