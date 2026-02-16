FROM openjdk:21
RUN mkdir /app
COPY ./Job_Search*jar ./app/job_search.jar
WORKDIR /app

EXPOSE 8888
CMD ["java", "-jar", "job_search.jar"]