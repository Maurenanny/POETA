angular.module("routingApp").controller("ProfileCtrl", [
    "$rootScope",
    "$scope",
    "$http",
    "APP_URL",
    "$location",
    "$routeParams",
    "$filter",
    "$window",
    function ($rootScope, $scope, $http, APP_URL, $location, $routeParams, $filter, $window) {
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
            if ($routeParams.id && localStorage.getItem("token")) {
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
            } else {
                window.location.href = "#!/"
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
            })
        }

        this.updateUser = () => {
            $scope.profile.postulant = $scope.user
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
                    $("#exampleModal").modal("hide");
                    notyf.success("Perfil Actualizado");
                } else if (res.data.code == 403) {
                    notyf.error(res.data.message);
                }
            })
        }

        this.uploadCV = async () => {
            let cv = document.getElementById("cv").files[0];
            var formData = new FormData();
            if (cv) {
                formData.append("file", cv);
                let res = await fetch(`${APP_URL.url}/user/upload/cv/${$scope.user.id}`, {
                    method: "POST",
                    body: formData,
                    authorization: $scope.token,
                }).then((r) => {
                        $scope.uploadedPic = true;
                        $("#uploadCv").modal("hide");
                        notyf.success("Curriculum subido correctamente");
                });
            } else {
                notyf.error("Debes subir tu curriculum");
            }
        };

        this.downloadCV = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/postulant/cv/generate/${$scope.user.id}`,
                responseType: "arraybuffer",
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token
                },
            }).then((res) => {
                const fileName = `${$scope.user.name}${$scope.user.lastname}${$scope.user.surname}CV`;
                const a = document.createElement("a");
                document.body.appendChild(a);
                const file = new Blob([res.data], {type: "application/pdf"});
                a.href = window.URL.createObjectURL(file);
                a.download = fileName.replace(" ", "");
                a.click();
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