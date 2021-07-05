################################################################
## Description:                                               ##
## BASE Image for: OpenJDK 11 with CentOS 7 Linux                ##
################################################################
FROM adoptopenjdk/openjdk11:ubi

###############################
## Setup primary application ##
###############################

RUN mkdir -p /opt/assignment
RUN mkdir -p /sr/main/resources


COPY build/libs/CriblAssignment.jar /opt/assignment/CriblAssignment.jar
COPY testng.xml /opt/assignment/testng.xml

#CMD ["java", "-jar", "/opt/assignment/CriblAssignment.jar"]
