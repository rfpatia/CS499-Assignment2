(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('SoccerplayerDeleteController',SoccerplayerDeleteController);

    SoccerplayerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Soccerplayer'];

    function SoccerplayerDeleteController($uibModalInstance, entity, Soccerplayer) {
        var vm = this;

        vm.soccerplayer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Soccerplayer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
