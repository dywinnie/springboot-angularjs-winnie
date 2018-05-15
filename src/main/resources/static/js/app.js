var app = angular.module('app', ['ngRoute','ngResource','ngStorage']);
app.config(function($routeProvider){
    $routeProvider
        .when('/users',{
            templateUrl: '/views/usersList.html',
            controller: 'UserController',
            controllerAs: 'ctrl',
            resolve: {
                prepUserService: function(UserService) {
                    return UserService.loadAllUsers();
                }
            }
        })
        .when('/user/me',{
            templateUrl: '/views/userMe.html',
            controller: 'UserController',
            controllerAs: 'ctrl',
            resolve: {
                prepUserService: function(UserService) {
                    return UserService.loadCurrentUser();
                }
            }
        })
        .when('/roles',{
            templateUrl: '/views/rolesList.html',
            controller: 'RoleController',
            controllerAs:'ctrl',
            resolve: {
                prepRoleService: function(RoleService) {
                    return RoleService.loadAllRoles();
                }
            }
        })
        .when('/createUser',{
            templateUrl: '/views/create_user.html',
            controller: 'UserController',
            controllerAs:'ctrl'
        })
        .otherwise(
            { redirectTo: '/'}
        );
});

app.constant('urls', {
    USER_SERVICE_API : 'http://localhost:8080/api/users',
    CURRENT_USER_SERVICE_API : 'http://localhost:8080/api/user/me',
    CREATE_USER_SERVICE_API : 'http://localhost:8080/api/user',
    EDIT_USER_SERVICE_API : 'http://localhost:8080/api/user/update/',
    DELETE_USER_SERVICE_API : 'http://localhost:8080/api/user/delete',
    ROLE_SERVICE_API : ' http://localhost:8080/api/roles',
    TOKEN_KEY : 'jwtToken',
    USERNAME_KEY : 'userName'
});

app.factory('mainService', function(urls) {

    function getJwtToken() {
        return localStorage.getItem(urls.TOKEN_KEY);
    }

    function setJwtToken(token) {
        localStorage.setItem(urls.TOKEN_KEY, token);
    }

    function getUserName() {
        return localStorage.getItem(urls.USERNAME_KEY);
    }

    function setUserName(userName) {
        localStorage.setItem(urls.USERNAME_KEY, userName);
    }

    function removeJwtToken() {
        localStorage.removeItem(urls.TOKEN_KEY);
    }

    function removeCurrentUser() {
        localStorage.removeItem(urls.USERNAME_KEY);
    }

    function createAuthorizationTokenHeader() {
        var token = getJwtToken();
        if (token) {
            return {"Authorization": "Bearer " + token};
        } else {
            return {};
        }
    }

    function removeStorage(){
        localStorage.clear();
        removeJwtToken();
        removeCurrentUser();
        location.hash="";
        location.reload();
    }

    return {
        getJwtToken: getJwtToken,
        setJwtToken: setJwtToken,
        getUserName: getUserName,
        setUserName: setUserName,
        removeJwtToken: removeJwtToken,
        removeCurrentUser: removeCurrentUser,
        removeStorage: removeStorage,
        createAuthorizationTokenHeader: createAuthorizationTokenHeader
    };
});

