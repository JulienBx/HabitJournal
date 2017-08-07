'use strict';

angular.module('crudApp').factory('EntryService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllEntries: loadAllEntries,
                getAllEntries: getAllEntries,
                getEntry: getEntry,
                createEntry: createEntry,
                updateEntry: updateEntry,
                removeEntry: removeEntry
            };

            return factory;

            function loadAllEntries() {
                console.log('Fetching all entries');
                var deferred = $q.defer();
                $http.get(urls.ENTRY_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all entries');
                            $localStorage.entries = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading entries');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllEntries(){
                return $localStorage.entries;
            }

            function getEntry(id) {
                console.log('Fetching Entry with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.ENTRY_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Entry with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading entry with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createEntry(entry) {
                console.log('Creating Entry');
                var deferred = $q.defer();
                $http.post(urls.ENTRY_SERVICE_API, entry)
                    .then(
                        function (response) {
                            loadAllEntries();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Entry : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateEntry(entry, id) {
                console.log('Updating Entry with id '+id);
                var deferred = $q.defer();
                $http.put(urls.ENTRY_SERVICE_API + id, entry)
                    .then(
                        function (response) {
                            loadAllEntries();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Entry with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeEntry(id) {
                console.log('Removing Entry with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.ENTRY_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllEntries();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Entry with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);