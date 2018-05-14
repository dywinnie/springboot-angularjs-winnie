# Spring Boot JWT Angular


## About
<ul>The ability to create, read, update and delete (CRUD) users</ul>
<ul>The admin user is able to assign 2 types of permissions to every user: 'ADMIN' or 'USER'
<li>ADMIN: Allowed to create, read, update and delete users</li>
<li>USER: Allowed to only read and delete their own profile, they should NOT be able to perform
any other action</li>
</ul>

## Requirements
This demo is build with with Maven 3 and Java 1.8.

## Steps to Setup the Spring Boot Back app

1. **Clone the application**

	```bash
	git clone https://github.com/dywinnie/springboot-angularjs-winnie.git
	cd springboot-angularjs-winnie
	```

2. **Create MySQL database**

	```bash
	create dbtest
    username root
    password root
	```

3. **Change MySQL username and password as per your MySQL installation**

	+ open `src/main/resources/application.properties` file.

	+ notice `spring.datasource.username` and `spring.datasource.password` properties as per your mysql installation

4. **Run the app**

	You can run the spring boot app by typing the following command -

	```bash
	mvn spring-boot:run
	```

	Open your browser in http://localhost:8080/login.html.
	
	The server is running at http://localhost:8080

    Login users:
    Admin - admin:admin

5. **Run an insert in SQL**
	
	The spring boot app uses role based authorization powered by spring security. Please execute the following sql queries in the database to insert the `USER` and `ADMIN` roles.

	```sql
	INSERT INTO user (id, user_name, password, first_name, last_name, enabled, lasspasswordresetdate) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 1, PARSEDATETIME('05-01-2018', 'dd-MM-yyyy'));
   
	INSERT INTO authority (id, name) VALUES(1, 'ROLE_USER');
	INSERT INTO authority (id, name) VALUES(2, 'ROLE_ADMIN');
    
    INSERT INTO user_authority (user_id, authority_id) VALUES(1, 1);
	```

	Any new user who signs up to the app is assigned the `ROLE_USER` by default.


## Docker

