# Authentication Manager

### Requirements:
* Create an HTTP-based server API based on persistent storage (perhaps a database). You should also create a client consuming the API, demonstrating that the solution satisfies the below requirements.

Minimum requirements - The application should allow a user to:
1. Register
2. Authenticate
3. Access a list of timestamps showing the user's last 5 successful login attempts. The user should only be able to see it’s own list.

## Prerequisites:

Install the below software in the developement environment.

1. maven 3.3.9
2. java 1.8
3. docker 1.12 (https://docs.docker.com/install/)
4. git
5. postman (https://support.getpostman.com/)

### Getting the source code

```
clone the source code
> git clone https://github.com/thiyagu06/auth-manager.git
cd auth-manager
```

### Running from source

###### setup database

```
Run the postgresql database
> docker run -d -p 5432:5432 --name izettle-auth -e POSTGRES_PASSWORD={{password}} postgres:11
Create a schema in the postgres command line.
> docker run -it --rm --link izettle-auth:postgres postgres psql -h postgres -U postgres
create a schema
postgres:> CREATE SCHEMA "izettle-auth" AUTHORIZATION postgres;

```
###### Running the application
```

Update the schema name,database credentials and schema in the dev profile.
https://github.com/thiyagu06/auth-manager/blob/master/src/main/resources/application-dev.properties
Run the application
mvn spring-boot:run -Dspring-boot.run.profiles=dev

```

### Running from pre-built Docker Images

The images are already published to the docker hub.

```
Update the env.sh file with required values.
> source env.sh
Run the application
> docker-compose up -d
verify whether container is running
> docker ps

```

#### Building the Docker images

###### Build Database image

```
> cd database
> docker build -t thiyagu06/izettle-auth-management-postgres:1.0 .
verify the image
> docker images
```
###### Build service image

```
> mvn package dockerfile:build
verify the image
> docker images
```

### Testing the application

The postman scripts are avaliable at https://github.com/thiyagu06/auth-manager/tree/master/postman. import both collection and environment json file in postman and execute the request in the order. All the request postman script is self explanatory for the scenario it tests. Update the Authorization header after successful login. Update the environment variable based your development environment.

1. Successfully register the user
2. Login with registered user credentials
3. view the successful login attempts

### Troubleshooting tips

1. If you face any problem for connecting with docker daemon when building service images using mvn command, refer the below url to enable tls connection to daemon.
https://forums.docker.com/t/spotify-docker-maven-plugin-cant-connect-to-localhost-2375/9093# auth-manager
