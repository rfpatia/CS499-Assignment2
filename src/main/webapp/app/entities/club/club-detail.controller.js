(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ClubDetailController', ClubDetailController);

    ClubDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Club'];

    function ClubDetailController($scope, $rootScope, $stateParams, previousState, entity, Club) {
        var vm = this;

        vm.club = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:clubUpdate', function(event, result) {
            vm.club = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
