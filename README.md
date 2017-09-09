# Session-worker
Session-worker manages session-wise commands.
It fetches commands from kafka and then process it.

Session-worker processes data into two databases Mongo and Datomic. 
Mongo will have only "latest version" of the data while Datomic keeps all historic data.

This is made so then the queries processes query only on mongo while all historical analysis will be executed against Datomic.

## Usage

To run it needs to connect with kafka, mongo and datomic.

In near future we will create a a docker-compose or a makefile to made this easier but for now it is something like this:

```
KAFKA="localhot:9092" \
MONGO="mongodb://localhost:27017/main" \
DATOMIC="datomic:men://localhost/" \
lein run
```

This is not the only way to setup env variables for more info check [environ](https://github.com/weavejester/environ#usage).

Copyright © 2017 alexcvcoelho e gabrielgio

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
