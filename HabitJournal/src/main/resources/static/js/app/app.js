var app = angular.module('crudApp',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8080/journalhabit',
    ENTRY_SERVICE_API : 'http://localhost:8080/journalhabit/calendar/entry/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'EntryController',
                controllerAs:'ctrl',
                resolve: {
                    entries: function ($q, EntryService) {
                        console.log('Load all entries');
                        var deferred = $q.defer();
                        EntryService.loadAllEntries().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

