FROM eclipse-temurin:17.0.5_8-jre-alpine

COPY ./build/install/app-book/lib/app-book-1.0-SNAPSHOT.jar ./
COPY ./build/install/app-book/lib ./lib

CMD ["java", "-cp", "app-book-1.0-SNAPSHOT.jar:./lib/*", "com.distribuida.Servidor"]

EXPOSE 8080