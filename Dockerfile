FROM openjdk:8-jre-alpine
MAINTAINER Ed Moffatt <cmjug01@gmail.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar"]
# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD terraform-launcher-1.0.jar /usr/share/myservice/myservice.jar
