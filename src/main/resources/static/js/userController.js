// (function(){
//     'use strict';
    angular
        .module('app')
        .controller('UserController', UserController);

    UserController.$inject = ['$scope','UserService'];

    function UserController($scope, UserService) {
        $scope.headingTitle = "User List";
        $scope.headingTitleModal = "";

        var vm = this;
        $scope.user = {};
        $scope.users=[];
        $scope.remove = false;
        $scope.userEdit = {};

        vm.getAllUsers = getAllUsers;
        vm.submit = submit;
        vm.getCurrentUser = getCurrentUser;
        vm.editRow = editRow;
        vm.save = save;
        vm.deleteRow = deleteRow;
        vm.showDeleteModal = showDeleteModal;
        vm.deleteSelf = deleteSelf;

        function deleteSelf() {
            console.log("delete self", getCurrentUser());
            UserService.deleteSelf();
        };

        function deleteRow() {
            UserService.deleteRow($scope.userEdit);
        };

        function showDeleteModal(userName) {
            $scope.headingTitleModal = "Delete";
            $scope.userEdit = {"userName":userName};
            UserService.showDeleteModal();
        };

        function editRow(user) {
            $scope.headingTitleModal = "Update";
            $scope.userEdit = UserService.editRow(user);
        };

        function save(){
            return UserService.save($scope.userEdit);
        }

        function getAllUsers (){
            return UserService.getAllUsers();
        }

        function getCurrentUser(){
            $scope.headingTitle = "User Profile";
            return UserService.getCurrentUser();
        }

        function submit() {
            console.log("$scope.user is",$scope.user);
            UserService.createUser($scope.user).then(function(response){
                $scope.remove = true;
                console.log($scope.user,$scope.remove);
            });

        }

    }


// });