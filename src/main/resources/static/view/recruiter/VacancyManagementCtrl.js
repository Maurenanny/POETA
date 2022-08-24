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

        this.getPostulant = (id, obj, status) => {
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
                    $scope.postulantModal.status = status
                    $scope.tmpProcess = obj;
                    this.getPostulantHasCv();
                }
            })
        }

        this.sendCvMail = (status) => {
            if (status == 1) {
                return $http({
                    method: "GET",
                    url: `${APP_URL.url}/process/mail/${$scope.tmpProcess.id}/4`,
                    headers: {
                        "Content-Type": "application/json",
                        Accept: "application/json",
                        authorization: $scope.token,
                    },
                }).then((res) => {

                })
            }
        }

        this.sendMail = (id, type) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/process/mail/${id}/${type}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                notyf.success("Se ha notificado al candidato")
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
                const file = new Blob([res.data], { type: "application/pdf" });
                const href = window.URL.createObjectURL(file);
                window.open(href);
                if (obj.status == 1) {
                    this.sendCvMail();
                    this.updateStatus(obj, 2, 1);
                }
            })
        }

        this.selectWinner = (postulant) => {
            notyf.success("Notificando a los candidatos idóneos sus resultados, espere un momento...");
            return $http({
                method: "GET",
                url: `${APP_URL.url}/process/selectWinner/${postulant.id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    this.getProcessByStage(4);
                    notyf.success("Se ha seleccionado al candidado, los demás se han rechazado automáticamente y la vacante se ha cerrado");
                } else if (res.data.code == 403) {
                    notyf.error(res.data.message);
                }
            })
        }

        this.updateStatus = (obj, status, stage) => {
            obj.status = status;
            obj.vacant = $scope.vacancy;
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
                    if (status == 0) {
                        this.sendMail(obj.id, 2);
                    }
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
                    $scope.stage = type;
                    setTimeout(executeDataTable, 10);
                } else if (res.data.code == 403) {
                    notyf.error(res.data.message);
                }
            })
        }
    }
])