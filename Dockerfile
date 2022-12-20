FROM openjdk:17
EXPOSE 8088
ADD build/libs/ClevertecTestTask-1.0-plain.jar api.jar
ENTRYPOINT ["java","-jar","api.jar"]