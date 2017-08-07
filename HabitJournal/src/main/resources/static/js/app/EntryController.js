'use strict';

angular.module('crudApp').controller('EntryController',
    ['EntryService', '$scope',  function( EntryService, $scope) {

        var self = this;
        self.entry = {};
        self.entries=[];

        self.submit = submit;
        self.getAllEntries = getAllEntries;
        self.createEntry = createEntry;
        self.updateEntry = updateEntry;
        self.removeEntry = removeEntry;
        self.editEntry = editEntry;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.entry.id === undefined || self.entry.id === null) {
                console.log('Saving New Entry', self.entry);
                createEntry(self.entry);
            } else {
                updateEntry(self.entry, self.entry.id);
                console.log('Entry updated with id ', self.entry.id);
            }
        }

        function createEntry(entry) {
            console.log('About to create entry');
            EntryService.createEntry(entry)
                .then(
                    function (response) {
                        console.log('Entry created successfully');
                        self.successMessage = 'Entry created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.entry={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Entry');
                        self.errorMessage = 'Error while creating Entry: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateEntry(entry, id){
            console.log('About to update entry');
            EntryService.updateEntry(entry, id)
                .then(
                    function (response){
                        console.log('Entry updated successfully');
                        self.successMessage='Entry updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Entry');
                        self.errorMessage='Error while updating Entry '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeEntry(id){
            console.log('About to remove Entry with id '+id);
            EntryService.removeEntry(id)
                .then(
                    function(){
                        console.log('Entry '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing entry '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllEntries(){
            return EntryService.getAllEntries();
        }

        function editEntry(id) {
            self.successMessage='';
            self.errorMessage='';
            EntryService.getEntry(id).then(
                function (entry) {
                    self.entry = entry;
                },
                function (errResponse) {
                    console.error('Error while removing entry ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.entry={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);