
'use strict';

angular.module('app').factory('UserService',
    ['$localStorage', '$http', '$q', 'urls', 'mainService',
        function ($localStorage, $http, $q, urls, mainService) {

            var factory = {
                loadAllUsers: loadAllUsers,
                getAllUsers: getAllUsers,
                createUser: createUser,
                loadCurrentUser: loadCurrentUser,
                getCurrentUser: getCurrentUser
            };

            return factory;

            function loadAllUsers() {
                console.log('Fetching all users');
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API, {
                    headers: mainService.createAuthorizationTokenHeader()
                })
                    .then(
                        function (response) {
                            $localStorage.users = response.data;
                            console.log("response users", response.data);
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            var errMsg = "";
                            if (errResponse.status === 403) {
                                errMsg = errResponse.data.message + ". No authorization to View Summary";
                            } else {
                                errMsg = "An unexpected error occured: " + errResponse.data.message;
                            }
                            $('#loginErrorModal')
                                .modal("show")
                                .find(".modal-body")
                                .empty()
                                .html("<p>"+ errMsg +"</p>");
                            console.error('Error while loading users');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllUsers(){
                return $localStorage.users;
            }

            function loadCurrentUser() {
                console.log('Fetching current user');
                var deferred = $q.defer();
                $http.post(urls.CURRENT_USER_SERVICE_API,
                    JSON.stringify({userName:mainService.getUserName()}),
                    {
                        headers: mainService.createAuthorizationTokenHeader()
                    })
                    .then(
                        function (response) {
                            $localStorage.user = response.data;
                            console.log("response current user", response.data);
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading current user');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getCurrentUser(){
                return $localStorage.user;
            }

            function createUser(user) {
                console.log('Creating User angular',user);
                var deferred = $q.defer();
                $http.post(urls.CREATE_USER_SERVICE_API, user, {
                    headers: mainService.createAuthorizationTokenHeader()
                })
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while creating User : ' + errMsg);
                            var errMsg = "";
                            if (errResponse.status === 400) {
                                errMsg = "User already exists!";
                            } else {
                                errMsg = "An unexpected error occured: " + errResponse.data.message;
                            }
                            $('#loginErrorModal')
                                .modal("show")
                                .find(".modal-body")
                                .empty()
                                .html("<p>"+ errMsg +"</p>");
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
        }

    ]);
