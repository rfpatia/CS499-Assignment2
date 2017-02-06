'use strict';

describe('Controller Tests', function() {

    describe('Soccerplayer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSoccerplayer, MockCountry, MockClub;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSoccerplayer = jasmine.createSpy('MockSoccerplayer');
            MockCountry = jasmine.createSpy('MockCountry');
            MockClub = jasmine.createSpy('MockClub');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Soccerplayer': MockSoccerplayer,
                'Country': MockCountry,
                'Club': MockClub
            };
            createController = function() {
                $injector.get('$controller')("SoccerplayerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'assignment2App:soccerplayerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
