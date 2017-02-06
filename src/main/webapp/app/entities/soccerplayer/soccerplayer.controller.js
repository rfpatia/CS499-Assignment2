(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('SoccerplayerController', SoccerplayerController);

    SoccerplayerController.$inject = ['$scope', '$state', 'Soccerplayer'];

    function SoccerplayerController ($scope, $state, Soccerplayer) {
        var vm = this;

        vm.soccerplayers = [];

        loadAll();

        function loadAll() {
            Soccerplayer.query(function(result) {
                vm.soccerplayers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
