/* angular.module("routingApp").controller("ProfileCtrl",[
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
        $(document).ready(function(){
            console.log("aaaaaaaaaaaaaaa");
        })
        this.findUserProfile = () => {
            notyf.success("aaaaaaa");
            if($routeParams.id){
                return $http({
                    method : "get",
                    url : `${APP_URL.url}/postulant/${$routeParams.id}`,
                    headers : {
                        "Content-Type" : "application/json",
                        Accept : "application/json",
                        Authorization : $scope.token,
                    },
                }).then((res) => {
                    console.log(res.data)
                })
            }
        }
    }
]); */
angular.module("routingApp").controller("ProfileCtrl", [
    "$rootScope",
    "$scope",
    "$http",
    "APP_URL",
    function ($rootScope, $scope, $http, APP_URL) {
        const notyf = new Notyf({
            duration: 2500,
            position: {
                x: "right",
                y: "top",
            },
        });
        $(document).ready(function(){
            console.log("aaaaaaaaaaaaaaa");
        })
        
    }
])