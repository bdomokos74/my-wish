### Wish list app with Google sign-in and token auth

Inspired by [https://medium.com/better-programming/10-coding-prompts-for-your-portfolio-4a5266d86ede]

### Architecture

![Token Auth Architecture](token-auth-arch.jpg?raw=true "Token Auth") 

### Run resource server:

* Configure Google sign in, create a project as described in [https://developers.google.com/identity/sign-in/web/sign-in]
* Create an application.properties in the root dir, with the Google client id obtained by the previous step. e.g:
```
client_id.google:123.....apps.googleusercontent.com
```
* Run the project from the command line
```
cd my-wish-resources
gradlew runBoot
```
* Test with curl
```
curl http://localhost:8080/resource

# {"timestamp":"2019-08-10T12:34:53.932+0000","status":403,"error":"Forbidden","message":"Access Denied","path":"/resource"}
```

* Run the UI as described in the next part. Browse to localhost:4200. Open inspect window, console. 
Click top right icon, Sign in with Google. Copy the token_id logged from the console.
```
curl -H "X-Auth-Token:eyJhbGci....gBUg" http://localhost:8080/resource

# {"id":"6848c870-e496-4f14-8f94-fd8fef341343","content":"Hello World2"}
``` 

### Run the UI

* To run the Angular app with angular-cli:

```
cd my-wish-ui
ng serve
```

You need Node.js and angular-cli to be installed . ( check [https://angular.io/cli] )

### TODO

* Run UI server and resource server in docker containers
* Add db server, use docker-compose to start all containers

