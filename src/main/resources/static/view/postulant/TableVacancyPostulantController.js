angular.module("routingApp").controller("TableVacancyPostulantCtrl", [
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

        $scope.postulant = null;
        $scope.title = "Mis Postulaciones";

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
                    $scope.postulant = res.data.object;
                    this.findPostulantVacancies($scope.postulant.id);
                } else if (res.data.code == 403) {
                    notyf.error(res.data.message);
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

        this.findPostulantVacancies = (id) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/process/postulant/${id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $('#vacancies').DataTable().destroy();
                    setTimeout(executeDataTable, 10);
                    $scope.process = res.data.object;
                }
            })
        }
        $scope.modalProcess = {};
        this.confirmVacancyDeletion = (vacancy) => {
            $scope.modalProcess = vacancy;
        }

        this.filter = (type) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/process/filter/${$scope.postulant.id}/${type}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $('#vacancies').DataTable().destroy();
                    setTimeout(executeDataTable, 10);
                    switch (type) {
                        case 1:
                            $scope.title = "Mis Postulaciones";
                            break;
                        case 2:
                            $scope.title = "Mis Postulaciones (Favorito)";
                            break;
                        case 3:
                            $scope.title = "Mis Postulaciones (Rechazado)";
                            break;
                        case 4:
                            $scope.title = "Mis Postulaciones (Aceptado)";
                            break;
                    }
                    $scope.process = res.data.object;
                } else if (res.data.code == 403) {
                    notyf.error(res.data.message);
                }
            })
        }

        this.deleteProcess = (id) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/process/delete/${id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    notyf.success("Se ha cancelado tu registro a la vacante");
                    $('#vacancies').DataTable().destroy();
                    setTimeout(executeDataTable, 10);
                    this.findPostulantVacancies($scope.postulant.id);
                    $scope.title = "Mis Postulaciones";
                    $("#confirmModal").modal("hide");
                } else if (res.data.code == 403) {
                    notyf.error(res.data.message);
                }
            })
        }

        this.changeFavorites = (process) => {
            process.favorite = !process.favorite;
            return $http({
                method: "POST",
                url: `${APP_URL.url}/process/save`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
                data: process,
            }).then((res) => {
                if (res.data.code == 200) {
                    if (process.favorite) {
                        notyf.success("¡Vacante añadida a favoritos!");
                    } else{
                        notyf.success("¡Vacante eliminada de favoritos!");
                    }
                }
            })
        }
    }
])