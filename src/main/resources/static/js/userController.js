// (function(){
//     'use strict';
    angular
        .module('app')
        .controller('UserController', UserController);

    UserController.$inject = ['$scope','UserService'];

    function UserController($scope, UserService) {
        $scope.headingTitle = "User List";

        var vm = this;
        $scope.user = {};
        $scope.users=[];
        $scope.remove = false;

        vm.getAllUsers = getAllUsers;
        vm.submit = submit;
        vm.getCurrentUser = getCurrentUser;

        function getAllUsers (){
            return UserService.getAllUsers();
        }

        function getCurrentUser (){
            $scope.headingTitle = "User Profile";
            return UserService.getCurrentUser();
        }

        function submit() {
            console.log("$scope.user is",$scope.user);
            UserService.createUser($scope.user).then(function(response){
                $scope.remove = true;
                localStorageExist ();
                console.log("done");
                console.log($scope.user,$scope.remove);
            });

        }

    }


// });