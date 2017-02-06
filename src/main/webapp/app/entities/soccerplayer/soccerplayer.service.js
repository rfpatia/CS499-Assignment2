(function() {
    'use strict';
    angular
        .module('assignment2App')
        .factory('Soccerplayer', Soccerplayer);

    Soccerplayer.$inject = ['$resource'];

    function Soccerplayer ($resource) {
        var resourceUrl =  'api/soccerplayers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
