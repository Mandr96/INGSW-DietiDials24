FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src
RUN gradle build -q --no-daemon --exclude-task test

FROM openjdk:17

RUN mkdir /app


COPY --from=build /home/gradle/src/build/libs/*.jar /app/DemoApplication.jar

ENTRYPOINT ["java","-jar","/app/DemoApplication.jar"]