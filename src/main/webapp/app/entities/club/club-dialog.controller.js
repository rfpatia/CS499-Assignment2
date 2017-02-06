(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ClubDialogController', ClubDialogController);

    ClubDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Club'];

    function ClubDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Club) {
        var vm = this;

        vm.club = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.club.id !== null) {
                Club.update(vm.club, onSaveSuccess, onSaveError);
            } else {
                Club.save(vm.club, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:clubUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
