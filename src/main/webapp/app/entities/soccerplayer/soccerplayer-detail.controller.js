(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('SoccerplayerDetailController', SoccerplayerDetailController);

    SoccerplayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Soccerplayer', 'Country', 'Club'];

    function SoccerplayerDetailController($scope, $rootScope, $stateParams, previousState, entity, Soccerplayer, Country, Club) {
        var vm = this;

        vm.soccerplayer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:soccerplayerUpdate', function(event, result) {
            vm.soccerplayer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
