FROM clojure
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN lein deps
CMD ["lein", "with-profile", "+dev", "run"]