angular.module("routingApp").controller("TableVacancyCtrl", [
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

        $scope.recruiter = null;

        $scope.register = {
            title: null,
            description: null,
            recruiter: null,
            startDate: new Date(),
            dateEnd: null,
            status: 1,
            city: null,
            type: null,
            jobStartDate: null,
            minSalary: null,
            maxSalary: null,
            paymentPeriod: null,
            benefits: null,
        }

        $scope.tmp = {
            state: null,
        }

        $scope.isRegister = true;

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

        this.clear = () => {
            $scope.register = {
                title: null,
                description: null,
                recruiter: $scope.recruiter,
                startDate: new Date(),
                dateEnd: null,
                status: 1,
                city: null,
                type: null,
                jobStartDate: null,
                minSalary: null,
                maxSalary: null,
                paymentPeriod: null,
                benefits: null,
            }
            $scope.tmp = {
                state: null,
            }
            $scope.isRegister = true;
        }

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
                    this.findRecruiterVacancies(res.data.object.id);
                    $scope.register.recruiter = res.data.object;
                    $scope.recruiter = res.data.object;
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

        this.lockDate = (date) => {
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var year = date.getFullYear();
            if (month < 10) month = '0' + month.toString();
            if (day < 10) day = '0' + day.toString();
            return year + '-' + month + '-' + day;
        }

        this.lockJobStartDateMin = () => {
            $('#jobStart').attr('min', this.lockDate($scope.register.dateEnd));
        }

        this.lockMaxSalaryMin = () => {
            $('#maxSalary').attr('min', $scope.register.minSalary);
            $scope.register.maxSalary = $scope.register.minSalary;
        }

        this.findRecruiterVacancies = (id) => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/vacancies/recruiter/${id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $scope.vacancies = res.data.object;
                    setTimeout(executeDataTable, 10);
                    this.findAllStates();
                    this.findPeriods();
                    this.findTypes();
                    $('#endDate').attr('min', this.lockDate(new Date()));
                    $('#jobStart').attr('min', this.lockDate(new Date()));
                } else if (res.data.code == 403) {
                    notyf.error(res.data.message);
                }
            })
        }

        this.findTypes = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/type/list`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $scope.types = res.data.object;
                }
            })
        }

        this.findPeriods = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/period/list`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $scope.periods = res.data.object;
                }
            })
        }

        this.save = () => {
            return $http({
                method: "POST",
                url: `${APP_URL.url}/vacancies/save`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
                data: $scope.register,
            }).then((res) => {
                if (res.data.code == 200) {
                    if ($scope.isRegister) {
                        notyf.success("Vacante Registrada");
                    } else {
                        notyf.success("Vacante Actualizada");
                    }
                    $('#vacancies').DataTable().destroy();
                    this.clear();
                    this.getActualUser();
                }
            })
        }

        this.loadVacancy = (id) => {
            $scope.isRegister = false;
            return $http({
                method: "GET",
                url: `${APP_URL.url}/vacancies/${id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                    authorization: $scope.token,
                },
            }).then((res) => {
                if (res.data.code == 200) {
                    $scope.register = res.data.object;
                    $scope.register.dateEnd = new Date(
                        res.data.object.dateEnd.charAt(0) +
                        res.data.object.dateEnd.charAt(1) +
                        res.data.object.dateEnd.charAt(2) +
                        res.data.object.dateEnd.charAt(3),
                        res.data.object.dateEnd.charAt(5) +
                        res.data.object.dateEnd.charAt(6) - 1,
                        res.data.object.dateEnd.charAt(8) +
                        res.data.object.dateEnd.charAt(9)
                    );
                    $scope.register.jobStartDate = new Date(
                        res.data.object.jobStartDate.charAt(0) +
                        res.data.object.jobStartDate.charAt(1) +
                        res.data.object.jobStartDate.charAt(2) +
                        res.data.object.jobStartDate.charAt(3),
                        res.data.object.jobStartDate.charAt(5) +
                        res.data.object.jobStartDate.charAt(6) - 1,
                        res.data.object.jobStartDate.charAt(8) +
                        res.data.object.jobStartDate.charAt(9)
                    );
                    $scope.tmp.state = $scope.register.city.state;
                    this.findAllCities();
                }
            })
        }
    }
])