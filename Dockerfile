#FROM openjdk:21-ea-slim
#RUN mkdir /app
#COPY ./Job_Search*jar ./app/job_search.jar
#WORKDIR /app
#
#EXPOSE 8888
#CMD ["java", "-jar", "job_search.jar"]

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY Job_Search*.jar app.jar
EXPOSE 8888
CMD ["java","-jar","app.jar"]
