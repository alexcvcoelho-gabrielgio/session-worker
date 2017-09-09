all: uberjar
	$(MAKE) docker

docker:
	docker build -t session-work:0.0.1 .

uberjar:
	lein uberjar