(function() {
    'use strict';

    var app = angular.module('plnkrGanttMaster',
        ['gantt',
            'gantt.sortable',
            'gantt.movable',
            'gantt.drawtask',
            'gantt.tooltips',
            'gantt.bounds',
            'gantt.progress',
            'gantt.table',
            'gantt.tree',
            'gantt.groups',
            'gantt.resizeSensor',
            'gantt.overlap',
            'gantt.dependencies'
        ]);

    app.controller('Ctrl',  function ($scope, $http) {
    	 $scope.data = [
    	                // Order is optional. If not specified it will be assigned automatically
    	                {name: 'Milestones', height: '3em', sortable: false, classes: 'gantt-row-milestone', color: '#45607D', tasks: [
    	                    // Dates can be specified as string, timestamp or javascript date object. The data attribute can be used to attach a custom object
    	                    {name: 'Kickoff', color: '#93C47D', from: '2013-10-07T09:00:00', to: '2013-10-07T10:00:00', data: 'Can contain any custom data or object'},
    	                    {name: 'Concept approval', color: '#93C47D', from: new Date(2013, 9, 18, 18, 0, 0), to: new Date(2013, 9, 18, 18, 0, 0), est: new Date(2013, 9, 16, 7, 0, 0), lct: new Date(2013, 9, 19, 0, 0, 0)},
    	                    {name: 'Development finished', color: '#93C47D', from: new Date(2013, 10, 15, 18, 0, 0), to: new Date(2013, 10, 15, 18, 0, 0)},
    	                    {name: 'Shop is running', color: '#93C47D', from: new Date(2013, 10, 22, 12, 0, 0), to: new Date(2013, 10, 22, 12, 0, 0)},
    	                    {name: 'Go-live', color: '#93C47D', from: new Date(2013, 10, 29, 16, 0, 0), to: new Date(2013, 10, 29, 16, 0, 0)}
    	                ], data: 'Can contain any custom data or object'},
    	                {name: 'Status meetings', tasks: [
    	                    {name: 'Demo #1', color: '#9FC5F8', from: new Date(2013, 9, 25, 15, 0, 0), to: new Date(2013, 9, 25, 18, 30, 0)},
    	                    {name: 'Demo #2', color: '#9FC5F8', from: new Date(2013, 10, 1, 15, 0, 0), to: new Date(2013, 10, 1, 18, 0, 0)},
    	                    {name: 'Demo #3', color: '#9FC5F8', from: new Date(2013, 10, 8, 15, 0, 0), to: new Date(2013, 10, 8, 18, 0, 0)},
    	                    {name: 'Demo #4', color: '#9FC5F8', from: new Date(2013, 10, 15, 15, 0, 0), to: new Date(2013, 10, 15, 18, 0, 0)},
    	                    {name: 'Demo #5', color: '#9FC5F8', from: new Date(2013, 10, 24, 9, 0, 0), to: new Date(2013, 10, 24, 10, 0, 0)}
    	                ]},
    	                {name: 'Kickoff', movable: {allowResizing: false}, tasks: [
    	                    {name: 'Day 1', color: '#9FC5F8', from: new Date(2013, 9, 7, 9, 0, 0), to: new Date(2013, 9, 7, 17, 0, 0),
    	                        progress: {percent: 100, color: '#3C8CF8'}, movable: false},
    	                    {name: 'Day 2', color: '#9FC5F8', from: new Date(2013, 9, 8, 9, 0, 0), to: new Date(2013, 9, 8, 17, 0, 0),
    	                        progress: {percent: 100, color: '#3C8CF8'}},
    	                    {name: 'Day 3', color: '#9FC5F8', from: new Date(2013, 9, 9, 8, 30, 0), to: new Date(2013, 9, 9, 12, 0, 0),
    	                        progress: {percent: 100, color: '#3C8CF8'}}
    	                ]},
    	                {name: 'Create concept', tasks: [
    	                    {name: 'Create concept', priority: 20, content: '<i class="fa fa-cog" ng-click="scope.handleTaskIconClick(task.model)"></i> {{task.model.name}}', color: '#F1C232', from: new Date(2013, 9, 10, 8, 0, 0), to: new Date(2013, 9, 16, 18, 0, 0), est: new Date(2013, 9, 8, 8, 0, 0), lct: new Date(2013, 9, 18, 20, 0, 0),
    	                        progress: 100, note: 'note', amout: 1000, doneQty: 50}
    	                ]},
    	                {name: 'Finalize concept', tasks: [
    	                    {id: 'Finalize concept', name: 'Finalize concept', priority: 10, color: '#F1C232', from: new Date(2013, 9, 17, 8, 0, 0), to: new Date(2013, 9, 18, 18, 0, 0),
    	                        progress: 100}
    	                ]},
    	            ];
		/*var data = { json: JSON.stringify($scope.data) };
		$.post('../../CustomerFTLot/getSchedule.koala', data).done(function(result){
			if(result.success ){
				debugger;
//				$scope.data = JSON.parse(result['data']);
				$scope.data = result['data'];
			}else{
				alert("error");
			}
		});*/
		$scope.reset = function(){
			var url = '../../TestSys/getSchedule.koala',
				data = { rowVos: JSON.stringify($scope.data) },
				transFn = function(data) {
					return $.param(data);
				},
				postCfg = {
						headers: { 'Content-Type': 'application/x-www.form-urlencoded; charset=UTF-8'},
						transformRequest: transFn
				};

			$http.post(url, $scope.data )
            .success(function(result){
            	debugger;
                console.info("Saved.");
                //angular.element(document.querySelector("#test")).scope().data = JSON.parse(result['data']);
                $scope.data = result['data'];
//                $scope.data = JSON.parse(result['data']);
            })
            .error(function(){
            });
		}
    	$scope.update = function() {
/*    		var data = { json: JSON.stringify($scope.data) };
    		$.post('../../CustomerFTLot/getSchedule.koala', data).done(function(result){
				if(result.success ){
					$scope.data = JSON.parse(result['data']);
				}else{
					alert("error");
				}
			});
*/
			$http.post('../../TestSys/updateSchedule.koala', $scope.data )
            .success(function(result){
            	debugger;
                console.info("Saved.");
                //angular.element(document.querySelector("#test")).scope().data = JSON.parse(result['data']);
                $scope.data = JSON.parse(result['data']);
            })
            .error(function(){
            });
    	}
    	$scope.registerApi = function(api) {
    		
    		api.directives.on.new($scope, function(dName, dScope, dElement, dAttrs, dController) {
    			  if (dName === 'ganttTask') {
    			    dElement.bind('dblclick', function(event) {
    			        alert('task-click: ' + dScope.task.model);
    			    });
    			  }
    			});
    	}
    });
})();

