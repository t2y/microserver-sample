# microserver-sample

minimal rest api server with jetty and jersey for microservice

## How to build and run

```bash
$ gradle run
> Task :run
19:02:04.321 [main] INFO  com.example.microserver.ServerMain - listening on port 8000
19:02:04.364 [main] DEBUG c.e.microserver.RootResourceConfig - initialize RootResourceConfig
19:02:04.401 [main] DEBUG c.e.microserver.RootResourceConfig - User(id=1, name=bob, birthDay=1995-01-17)
19:02:04.402 [main] DEBUG c.e.microserver.RootResourceConfig - User(id=2, name=john, birthDay=2005-01-11)
19:02:04.402 [main] DEBUG c.e.microserver.RootResourceConfig - User(id=3, name=mary, birthDay=1998-04-05)
19:02:05.504 [main] INFO  com.example.microserver.ServerMain - server started
```

## request to localhost

### list

```bash
$ curl -s -H 'Auth-Token: secret' localhost:8000/users | jq .
```

### create 

```bash
$ curl -X POST -s -H "Content-Type: application/json" -d '{"name": "sala", "birthDay": "2010-06-12"}' localhost:8000/users/create | jq .
{
  "name": "sala",
  "birthDay": "2010-06-12",
  "id": 4
}
```

### retrieve

```
$ curl -X GET -H 'Auth-Token: secret' -s localhost:8000/users/3 | jq .
{
  "name": "mary",
  "birthDay": "1998-04-05",
  "id": 3
}
```

### update

```bash
$ curl -X PUT -s -H "Content-Type: application/json" -d '{"name": "salala", "birthDay": "2008-06-12"}' localhost:8000/users/4 | jq .
{
  "name": "salala",
  "birthDay": "2008-06-12",
  "id": 4
}
$ curl -X GET -s localhost:8000/users/4 | jq .
{
  "name": "salala",
  "birthDay": "2008-06-12",
  "id": 4
}
```

### delete

```bash
$ curl -X DELETE -s localhost:8000/users/4 | jq .
{
  "name": "salala",
  "birthDay": "2008-06-12",
  "id": 4
}
$ curl -X GET -s localhost:8000/users/4 | jq .
{
  "id": "4",
  "error": "User not found"
}
```

## References

This sample code is inspired from as below.

* https://github.com/gilday/how-to-microservice
* https://jersey.github.io/documentation/latest/jaxrs-resources.html
