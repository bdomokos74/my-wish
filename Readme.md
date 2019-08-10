### Wish list app with Google sign-in and token auth

Inspired by https://medium.com/better-programming/10-coding-prompts-for-your-portfolio-4a5266d86ede

### Architecture

![Token Auth Architecture](token-auth-arch.jpg?raw=true "Token Auth") 

### Run resource server:

* Configure Google sign in, create a project as described in https://developers.google.com/identity/sign-in/web/sign-in
  
  Authorized JavaScript origins: http://localhost:4200
  
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

* You need Node.js and angular-cli to be installed . ( check [https://angular.io/cli] )
* To run the Angular app with angular-cli, pass the client id as an environment variable.

  ```
  cd my-wish-ui
  GOOGLE_CLIENT_ID=123...apps.googleusercontent.com ng serve
  ```

### Notes

* The weppack DefinePlugin makes the GOOGLE_CLIENT_ID variable available at build time. The configuration is in ```extra-webpack.config.js```.
The contents are merged with the default configuration.

  For this to work, I had to update angular, angular-cli and install the following modules:

  ```
  ng update @angular/cli @angular/core
  npm i -D @angular-builders/custom-webpack
  npm i -D @angular-builders/dev-server
  ```
  See https://blog.angularindepth.com/customizing-angular-cli-build-an-alternative-to-ng-eject-v2-c655768b48cc

### TODO

* Run UI server and resource server in Docker containers
* Add db server, use docker-compose to start all containers

