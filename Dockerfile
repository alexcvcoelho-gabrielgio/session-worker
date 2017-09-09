FROM java:8-jre
COPY ./target/uberjar/ /usr/src/app
WORKDIR /usr/src/app
CMD java -jar session-worker-0.0.1-standalone.jar