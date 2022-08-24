angular.module("routingApp").controller("TableVacancyGeneralCtrl", [
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
                    this.findNotRegisteredVacancies(res.data.object.id);
                    $scope.user = res.data.object;
                }
            })
        }

        $scope.process = {
            postulant: null,
            vacant: null,
            status: 1,
        }

        this.register = (vacancy) => {
            notyf.success("Enviando solicitud...");
            $('#registerBtn').prop('disabled', true);
            $scope.process.postulant = $scope.user;
            $scope.process.vacant = vacancy;
            return $http({
                method: "POST",
                url: `${APP_URL.url}/process/save`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
                data: $scope.process,
            }).then((res) => {
                if (res.data.code == 200) {
                    notyf.success("¡Registro exitoso!")
                    $('#vacancies').DataTable().destroy();
                    this.findNotRegisteredVacancies($scope.user.id);
                    $("#modalDetalles").modal("hide");
                }
                $('#registerBtn').prop('disabled', false);
            })
        }

        this.findNotRegisteredVacancies = (id) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/vacancies/list/${id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    setTimeout(executeDataTable, 10);
                    $scope.vacancies = res.data.object;
                }
            })
        }

        this.findAllVacancies = () => {
            if (localStorage.getItem("token")) {
                this.getActualUser();
            } else {
                return $http({
                    method: "GET",
                    url: `${APP_URL.url}/vacancies/list`,
                    headers: {
                        "Content-Type": "application/json",
                        Accept: "application/json",
                        /* authorization: $scope.token, */
                    },
                }).then((res) => {
                    if (res.data.code == 200) {
                        setTimeout(executeDataTable, 10);
                        $scope.vacancies = res.data.object;
                    }
                })
            }
        }

        this.findVacancy = (id) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/vacancies/${id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    /* authorization: $scope.token, */
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $scope.vacancy = res.data.object;
                }
            })
        }

        function executeDataTable() {
            $("#vacancies").DataTable({
                language: {
                    url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json",
                },
            });
        }
    }
])