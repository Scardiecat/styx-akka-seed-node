# styx-akka-seed-node
A akka seed node


## Styx-utils:

Add the following to /etc/sysconfig/docker
 ```
 OPTIONS="-H 0.0.0.0:5555 -H unix:///var/run/docker.sock"
 ```

Now all kinds of very useful Docker-related information is available on port 5555 inside your container.


## Docker support

Build docker container with:
*   ```sbt docker:stage```
*   ```sbt docker:publishLocal```
*   ```sbt docker:publish```

## Run on AWS

e.g.

```
 docker run -p 9101:2551 --rm styx-akka-seed-node:0.0.1 --seed 172.31.62.160:9101,172.31.62.160:9102
 docker run -p 9102:2551 --rm styx-akka-seed-node:0.0.1 --seed 172.31.62.160:9101,172.31.62.160:9102
  ```