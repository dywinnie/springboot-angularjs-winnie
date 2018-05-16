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
    
    Endpoints:
    http://localhost:8080/auth
    http://localhost:8080/api/user
    http://localhost:8080/api/user/me
    http://localhost:8080/api/users
    http://localhost:8080/api/user/update/{authority_id}
    http://localhost:8080/api/user/delete
    http://localhost:8080/api/roles (optional)    
    

5. **Run an insert in SQL**
	
	Highlight: The spring boot app uses role based authorization powered by spring security. Please execute the following sql queries in the database to insert the `USER` and `ADMIN` roles.

	```sql
	INSERT INTO user (id, user_name, password, first_name, last_name, enabled, lasspasswordresetdate) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 1, PARSEDATETIME('05-01-2018', 'dd-MM-yyyy'));
   
	INSERT INTO authority (id, name) VALUES(1, 'ROLE_USER');
	INSERT INTO authority (id, name) VALUES(2, 'ROLE_ADMIN');
    
    INSERT INTO user_authority (user_id, authority_id) VALUES(1, 1);
	```

	Any new user who signs up to the app is assigned the `ROLE_USER` by default.

    For database creation:
        Run dbtest.sql script from the springboot-angularjs-winnie/src/resources directory


## Docker (Challenges)

  **Encountered challenges**
1. You should have an existing container of mysql and springboot-angularjs-winnie with the database script ready before the docker-compose up runs. Otherwise you may encounter a Communication link failure or that 3306 port is already in use.
2. Two ways to run the docker but both had challenges of connection issue.

**Option 1**
    	
    docker run --name=mysql-dbtest -p 3306:3306 -e "MYSQL_ROOT_PASSWORD=root" -e "MYSQL_PASSWORD=root" -e "MYSQL_DATABASE=dbtest" mysql
    docker build -t springboot-angularjs-winnie . -f ./src/main/docker/Dockerfile
    docker run -it --link springboot-angularjs-winnie_mysql-dbtest_1:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
	

   **To enter mysql docker**
   
    docker run -it --link springboot-angularjs-winnie_mysql-dbtest_1:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
	
    
   **Option 2**
	
	git clone https://github.com/dywinnie/springboot-angularjs-winnie.git
	cd springboot-angularjs-winnie

	docker-compose up
    
    
    
        
     