(function() {
    'use strict';

    angular
        .module('assignment2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('soccerplayer', {
            parent: 'entity',
            url: '/soccerplayer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.soccerplayer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/soccerplayer/soccerplayers.html',
                    controller: 'SoccerplayerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('soccerplayer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('soccerplayer-detail', {
            parent: 'entity',
            url: '/soccerplayer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.soccerplayer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/soccerplayer/soccerplayer-detail.html',
                    controller: 'SoccerplayerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('soccerplayer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Soccerplayer', function($stateParams, Soccerplayer) {
                    return Soccerplayer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'soccerplayer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('soccerplayer-detail.edit', {
            parent: 'soccerplayer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/soccerplayer/soccerplayer-dialog.html',
                    controller: 'SoccerplayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Soccerplayer', function(Soccerplayer) {
                            return Soccerplayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('soccerplayer.new', {
            parent: 'soccerplayer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/soccerplayer/soccerplayer-dialog.html',
                    controller: 'SoccerplayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                goal: null,
                                position: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('soccerplayer', null, { reload: 'soccerplayer' });
                }, function() {
                    $state.go('soccerplayer');
                });
            }]
        })
        .state('soccerplayer.edit', {
            parent: 'soccerplayer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/soccerplayer/soccerplayer-dialog.html',
                    controller: 'SoccerplayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Soccerplayer', function(Soccerplayer) {
                            return Soccerplayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('soccerplayer', null, { reload: 'soccerplayer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('soccerplayer.delete', {
            parent: 'soccerplayer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/soccerplayer/soccerplayer-delete-dialog.html',
                    controller: 'SoccerplayerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Soccerplayer', function(Soccerplayer) {
                            return Soccerplayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('soccerplayer', null, { reload: 'soccerplayer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
