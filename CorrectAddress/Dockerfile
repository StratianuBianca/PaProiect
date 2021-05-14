FROM openjdk:11
WORKDIR '/app'
ADD target/correct.jar correct.jar
EXPOSE 8091
COPY . .
ENTRYPOINT ["java", "-jar","correct.jar"]
