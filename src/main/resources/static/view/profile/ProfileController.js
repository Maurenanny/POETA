angular.module("routingApp").controller("ProfileCtrl",[
    "$rootScope",
    "$scope",
    "$http",
    "APP_URL",
    "$location",
    "$routeParams",
    "$filter",
    function ($rootScope, $scope, $http, APP_URL, $location, $routeParams, $filter) {
        $scope.token = "Bearer " + localStorage.getItem("token");
        const notyf = new Notyf({
            duration : 2500,
            position : {
                x : "right",
                y : "top",
            },
        });
        $scope.user = {}
        this.findUserProfile = () => {
            if($routeParams.id){
                return $http({
                    method : "get",
                    url : `${APP_URL.url}/user/${$routeParams.id}`,
                    headers : {
                        "Content-Type" : "application/json",
                        Accept : "application/json",
                        Authorization : $scope.token,
                    },
                }).then((res) => {
                    $scope.user = res.data;
                })
            }
        }
    }
]);