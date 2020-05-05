# Workout-app-backend

The backend for a work out app. Client-coach functionalities with fully customizable exercise and meal plans.

## Built With

* [Spring](https://spring.io/) - Backend framework
* [Spring Boot](https://spring.io/projects/spring-boot)

## Authors

* **Glynn Leininger** - [glynn29](https://github.com/glynn29)

* **Josh Haunty** -  [sharkguy95](https://github.com/sharkguy95)

* **John Irle**  - [JohnIrle](https://github.com/JohnIrle)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Getting Started

On your machine using your IDE, checkout from VCS to the following repository: https://github.com/se5910/workout-app-backend

The first time running the server will throw update errors will occur as there are no tables to update. This is normal. Data.sql will be imported and additional data can be created using the user interface. Each restart of server will wipe data not located in data.sql.

### Prerequisites

You will need a MySQL database called "workout" deployed on your local 127.0.0.1 server.

### Installing

1. Checkout from VCS
2. Start MySQL Server with DB "workout"
3. Build and Run project

Coach Login : 
  Username : josh@hype4fitness.com
  Password : test

Upon login with example coach, you will have a client you can approve and begin creation of exercise plans.

## Running the tests

Using PostMan, API routes can be hit with dummy data to test for errors.

1. Register a user, login, and create client. -> Copy login authentication token and paste it into Authentication under each successive client test (Click on 'Headers(*num*)' and replace authentication).
2. Login coach (default, josh@hype4fitness.com) -> Copy login authenitcation token and paste it into Authentication udner each successive coach test. (Click on 'Headers(*num*)' and replace authentication).
3. Approve newly registered clients using authenitcation token from coach login.
4. Once client is approved, follow exercise plan creation path using coach authentication.

** Note : Client can only utilize GET Methods (try with client authentication token). Coach -> GET, POST, DELETE ***

## Deployment
