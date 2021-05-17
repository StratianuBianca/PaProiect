FROM tomcat:latest
ADD . .
EXPOSE 8091
CMD ["catalina.sh", "run"]
ENTRYPOINT ["java","-war","/correct.war"]
