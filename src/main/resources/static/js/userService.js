
'use strict';

angular.module('app').factory('UserService',
    ['$localStorage', '$http', '$q', 'urls', 'mainService',
        function ($localStorage, $http, $q, urls, mainService) {

            var factory = {
                deleteRow: deleteRow,
                editRow: editRow,
                loadAllUsers: loadAllUsers,
                getAllUsers: getAllUsers,
                createUser: createUser,
                loadCurrentUser: loadCurrentUser,
                getCurrentUser: getCurrentUser,
                save: save,
                showDeleteModal: showDeleteModal,
                submit: submit,
                deleteSelf: deleteSelf
            };

            return factory;

            function deleteSelf(){
                deleteRow(getCurrentUser()[0]);
                mainService.removeStorage();
                $('#loginErrorModal')
                    .modal("show")
                    .find(".modal-body")
                    .empty()
                    .html("<p>Deleting your user account.</p>");
            }

            function submit(){
                $('#editModal').modal("hide");
            }

            function save(user){
                console.log('Updating User: ',user);
                $('#editModal').modal("hide");
                var deferred = $q.defer();
                $http.post(urls.EDIT_USER_SERVICE_API + user.authority_id, user, {
                    headers: mainService.createAuthorizationTokenHeader()
                })
                    .then(
                        function (response) {
                            loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            var errMsg = "";
                            if (errResponse.status === 400) {
                                errMsg = "User does not exist!";
                            } else {
                                errMsg = "An unexpected error occured: " + errResponse.data.message;
                            }
                            console.error('Error while updating User : ' + errMsg);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function showDeleteModal(){
                $('#editModal').modal("show");
            }

            function deleteRow(userEdit){
                var deferred = $q.defer();
                $http.post(urls.DELETE_USER_SERVICE_API,
                    JSON.stringify(userEdit),
                    {
                        headers: mainService.createAuthorizationTokenHeader()
                    })
                    .then(
                        function (response) {
                            console.log("response deleting users", response.data);
                            if(mainService.getUserName()){
                                loadAllUsers();
                            }
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while deleting users' + errResponse);
                            deferred.reject(errResponse);
                        }
                    );
                $('#editModal').modal("hide");
                return deferred.promise;
            }

            function editRow(user){
                $('#editModal').modal("show");
                return user;
            }

            function loadAllUsers() {
                console.log('Fetching all users');
                var deferred = $q.defer();
                $http.get(urls.USER_SERVICE_API, {
                    headers: mainService.createAuthorizationTokenHeader()
                })
                    .then(
                        function (response) {
                            $localStorage.users = response.data;
                            console.log("response loading users", response.data);
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            var errMsg = "";
                            if (errResponse.status === 403) {
                                errMsg = errResponse.data.message + ". No authorization to View Summary";
                            } else {
                                errMsg = "An unexpected error occured: " + errResponse.data.message;
                            }
                            $('#userList').hide();
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
                console.log('Creating User: ',user);
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
                            console.error('Error while creating User : ' + errMsg);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
        }

    ]);
