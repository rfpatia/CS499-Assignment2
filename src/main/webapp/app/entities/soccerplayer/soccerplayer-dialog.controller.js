(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('SoccerplayerDialogController', SoccerplayerDialogController);

    SoccerplayerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Soccerplayer', 'Country', 'Club'];

    function SoccerplayerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Soccerplayer, Country, Club) {
        var vm = this;

        vm.soccerplayer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.countries = Country.query({filter: 'soccerplayer-is-null'});
        $q.all([vm.soccerplayer.$promise, vm.countries.$promise]).then(function() {
            if (!vm.soccerplayer.country || !vm.soccerplayer.country.id) {
                return $q.reject();
            }
            return Country.get({id : vm.soccerplayer.country.id}).$promise;
        }).then(function(country) {
            vm.countries.push(country);
        });
        vm.clubs = Club.query({filter: 'soccerplayer-is-null'});
        $q.all([vm.soccerplayer.$promise, vm.clubs.$promise]).then(function() {
            if (!vm.soccerplayer.club || !vm.soccerplayer.club.id) {
                return $q.reject();
            }
            return Club.get({id : vm.soccerplayer.club.id}).$promise;
        }).then(function(club) {
            vm.clubs.push(club);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.soccerplayer.id !== null) {
                Soccerplayer.update(vm.soccerplayer, onSaveSuccess, onSaveError);
            } else {
                Soccerplayer.save(vm.soccerplayer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:soccerplayerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
