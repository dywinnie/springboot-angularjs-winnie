# Spring Boot JWT Angular


## About
-   <ul>The ability to create, read, update and delete (CRUD) users</ul>
-   <ul>The admin user is able to assign 2 types of permissions to every user: 'ADMIN' or 'USER'
        <li>ADMIN: Allowed to create, read, update and delete users</li>
        <li>USER: Allowed to only read and delete their own profile, they should NOT be able to perform any other action</li>
    </ul>

## Requirements
This demo is build with with Maven 3 and Java 1.8.

## Steps to Setup the Spring Boot Back app

1. **Clone the application**

	```bash
	git clone https://github.com/dywinnie/springboot-angularjs-winnie.git
	cd springboot-angularjs-winnie
	```

2. **Run the app**

	You can run the spring boot app by typing the following command -

	```bash
	mvn spring-boot:run
	```

	Open your browser in http://localhost:8080/login.html.
	
	The server is running at http://localhost:8080

    Login users:
    Admin - admin:admin

  **Running the app will do the following**

1.  **Create MySQL database**

	```bash
	create dbtest
    username root
    password root
	```

2. **Change MySQL username and password as per your MySQL installation**

	+ open `src/main/resources/application.properties` file.

	+ notice `spring.datasource.username` and `spring.datasource.password` properties as per your mysql installation

3. **Run an insert in SQL**

   For database creation:
        Run dbtest.sql script from the springboot-angularjs-winnie/src/resources directory

	Highlight: The spring boot app uses role based authorization powered by spring security. Please execute the following sql queries in the database to insert the `USER` and `ADMIN` roles.

	```sql
	INSERT INTO user (id, user_name, password, first_name, last_name, enabled, lasspasswordresetdate) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 1, PARSEDATETIME('05-01-2018', 'dd-MM-yyyy'));
   
	INSERT INTO authority (id, name) VALUES(1, 'ROLE_USER');
	INSERT INTO authority (id, name) VALUES(2, 'ROLE_ADMIN');
    
    INSERT INTO user_authority (user_id, authority_id) VALUES(1, 1);
	```

	Any new user who signs up to the app is assigned the `ROLE_USER` by default.

 
   
## API Endpoints 
    
    
    http://localhost:8080/auth
    http://localhost:8080/api/user    
    http://localhost:8080/api/user/me    
    http://localhost:8080/api/users    
    http://localhost:8080/api/user/update/{authority_id}    
    http://localhost:8080/api/user/delete    
    http://localhost:8080/api/roles (optional)    
    

## Docker

   **Option 1**
	
   ```bash
    git clone https://github.com/dywinnie/springboot-angularjs-winnie.git
    cd springboot-angularjs-winnie
    mvn clean package docker:build
    docker build -t springboot-angularjs-winnie . -f ./src/main/docker/Dockerfile
    docker-compose up
   ```
    
   **Option 3**
       	
   ```bash
    docker run --name=mysql-dbtest -p 3306:3306 -e "MYSQL_ROOT_PASSWORD=root" -e "MYSQL_PASSWORD=root" -e "MYSQL_DATABASE=dbtest" mysql
    docker build -t springboot-angularjs-winnie . -f ./src/main/docker/Dockerfile
    docker run -p 8080:8080 springboot-angularjs-winnie -it --link springboot-angularjs-winnie:mysql --rm mysql sh -c 'exec mysql -h"8080" -P"8080" -u"root" -p"root"'
   ```
 
   **To enter mysql docker**
   
   ```bash
    docker run -it --link springboot-angularjs-winnie_mysql-dbtest_1:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
   ```

##  Encountered challenges
  
  1. Issues in no particular order:
  
        -ERROR: pull access denied for springboot-angularjs-winnie, repository does not exist or may require 'docker login'

        -ADD failed: stat /var/lib/docker/tmp/docker-builder681126597/target/springboot-angularjs-winnie.jar: no such file or directory
        
        -Error response from daemon: driver failed programming external connectivity on endpoint mysql-dbtest (cf44f5932edc08bc322d009cb9897c27e39838fa9b29f7ca3decc5d5b170e9cd): Error starting userland proxy: listen tcp 0.0.0.0:3306: bind: address already in use.

        -Could not get JDBC Connection; nested exception is org.apache.tomcat.dbcp.dbcp.SQLNestedException: Cannot create PoolableConnectionFactory (Communications link failure
        
        -3306 connection is already used.

  2. In either cases:
        
        -   **The following commands might be helpful**
            
             ```bash
             mvn clean package docker:build
 
             or
         
             docker build -t springboot-angularjs-winnie . -f ./src/main/docker/Dockerfile
             ```
            
        -   **You can always run the app**
        
        ```bash
        	mvn spring-boot:run
        ```
  3. Make sure of the following when using docker:
        
        -   mysql container is running on port 3306: docker start mysql-dbtest
        -   container springboot-angularjs-winnie exists, image java and mysql exists
        -   mysql-dbtest is linked to springboot-angularjs-winnie