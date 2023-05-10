# SpringMongoDb
This project is an application encompasing a back-end Web API written using [Spring Boot](https://spring.io), [MongoDB](https://www.mongodb.com/) as Database and [Keycloak](https://www.keycloak.org/) as IAM

There is Container Orchestration Support for the solution with Docker-Compose. 

## Setup without Docker
The MongoDb database is based on [Create a web API with ASP.NET Core and MongoDB](https://docs.microsoft.com/en-us/aspnet/core/tutorials/first-mongo-app?view=aspnetcore-5.0&tabs=visual-studio).

## Setup with Docker
The back-end solution contains a docker-compose.yml for the container orchestration. 

The docker-compose.yml also references some docker volumes. They should be created using `docker create volume <volume-name>`. For Keycloak volume is needed to apply the procedure described in [How to persist the default keycloak database in docker?](https://stackoverflow.com/questions/74714713/how-to-persist-the-default-keycloak-database-in-docker).

The network for the orchestration is declared in docker-compose.yml.

### Docker Desktop

It is needed a valid [Docker License](https://www.docker.com/pricing) to work with [Visual Studio](https://visualstudio.microsoft.com/downloads/). As alternative, you can use a Linux Distro of preference (e.g. Ubuntu) and [Visual Studio Code](https://code.visualstudio.com/). Linux can run on [WSL](https://docs.microsoft.com/en-us/windows/wsl/).
