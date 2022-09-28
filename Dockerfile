FROM maven:3.8.6-openjdk-18 AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests


FROM eclipse-temurin:18-jdk
RUN mkdir /app
COPY --from=build /project/target/Taygeta-cli-1.0-SNAPSHOT.jar /app/Taygeta-cli-1.0-SNAPSHOT.jar
WORKDIR /app
CMD "java" "-jar" "Taygeta-cli-1.0-SNAPSHOT.jar"

	#docker build -t taygeta-cli --build-arg JAR_FILE=Taygeta-cli-1.0-SNAPSHOT .
	#docker run -it --rm taygeta-cli