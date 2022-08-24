angular.module("routingApp").controller("VacancyManagementCtrl", [
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

        $scope.stage = 1;

        function executeDataTable() {
            $("#postulants").DataTable({
                language: {
                    url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json",
                },
            });
        }

        $scope.user = {};

        this.getActualUser = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/user/actual`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $scope.user = res.data.object;
                    this.getVacancy();
                }
            })
        }

        this.getPostulant = (id) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/postulant/cv/user/${id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $scope.postulantModal = res.data.object;
                    this.getPostulantHasCv();
                }
            })
        }

        this.downloadCV = (obj) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/postulant/cv/generate/${obj.postulant.id}`,
                responseType: "arraybuffer",
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token
                },
            }).then((res) => {
                const fileName = `${$scope.postulantModal.postulant.name}${$scope.postulantModal.postulant.lastname}${$scope.postulantModal.postulant.surname}CV`;
                const a = document.createElement("a");
                document.body.appendChild(a);
                const file = new Blob([res.data], {type: "application/pdf"});
                a.href = window.URL.createObjectURL(file);
                a.download = fileName.replace(" ", "");
                a.click();
                this.updateStatus(obj, 2, 1);
            })
        }

        this.updateStatus = (obj, status, stage) => {
            obj.status = status;
            return $http({
                method: "POST",
                url: `${APP_URL.url}/process/save`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
                data: obj,
            }).then((res) => {
                if (res.data.code == 200) {
                    this.getProcessByStage(stage);
                }
            })
        }

        this.getPostulantHasCv = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/postulant/cv/hasCv/${$scope.postulantModal.postulant.id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $scope.hasModal = res.data.object;
                }
            })
        }

        this.getVacancy = () => {
            if ($routeParams.id) {
                return $http({
                    method: "GET",
                    url: `${APP_URL.url}/vacancies/${$routeParams.id}`,
                    headers: {
                        "Content-Type": "application/json",
                        Accept: "application/json",
                        //authorization: $scope.token,
                    },
                }).then((res) => {
                    $scope.vacancy = res.data.object;
                    this.getProcessByStage(1);
                })
            } else {
                $window.location.href = "#!/"
            }
        }

        this.getProcessByStage = (type) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/process/management/${$scope.vacancy.id}/${type}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $('#postulants').DataTable().destroy();
                    $scope.postulants = res.data.object;
                    setTimeout(executeDataTable, 10);
                } else if (res.data.code == 403) {
                    notyf.error(res.data.message);
                }
            })
        }
    }
])