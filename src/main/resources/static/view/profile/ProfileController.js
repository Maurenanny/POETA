angular.module("routingApp").controller("ProfileCtrl", [
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
            duration: 2500,
            position: {
                x: "right",
                y: "top",
            },
        });
        $scope.user = {}
        this.findUserProfile = () => {
            if ($routeParams.id) {
                return $http({
                    method: "get",
                    url: `${APP_URL.url}/user/profile/${$routeParams.id}`,
                    headers: {
                        "Content-Type": "application/json",
                        Accept: "application/json",
                        Authorization: $scope.token,
                    },
                }).then((res) => {
                    if (res.data.code == 200) {
                        $scope.profile = res.data.object;
                        $scope.user = res.data.object.postulant
                    } else if (res.data.code == 403) {
                        notyf.error(res.data.message)
                    }
                })
            }
        }

        this.findAllStates = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/state/list`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                },
            }).then((res) => {
                $scope.listStates = res.data;
                console.log($scope.listStates);
            })
        }

        this.updateUser = () => {
            $scope.profile.user = $scope.user
            return $http({
                method: "POST",
                url: `${APP_URL.url}/postulant/cv/save`, 
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
                data: $scope.profile,
            }).then((res) => {
                if (res.data.code == 200) {
                    //if ($scope.isRegister) {
                        //notyf.success("Vacante Registrada");
                    //} else {
                        notyf.success("Perfil Actualizado");
                    //}
                    //$('#vacancies').DataTable().destroy();
                    //this.clear();
                    //this.getActualUser();
                }
            })
        }

        this.findAllCities = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/city/list/${$scope.tmp.state.id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                },
            }).then((res) => {
                $scope.listCities = res.data;
            })
        }

        this.stateHandler = () => {
            $scope.tmp.state = $scope.user.city.state;
            this.findAllCities();
        }
    }
]);