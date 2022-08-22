angular.module("routingApp").controller("LoginCtrl", [
    "$rootScope",
    "$scope",
    "$http",
    "APP_URL",
    "SESSION",
    "$routeParams",
    "$window",
    function ($rootScope, $scope, $http, APP_URL, SESSION, $routeParams, $window) {
        const notyf = new Notyf({
            duration: 2500,
            position: {
                x: "right",
                y: "top",
            },
        });

        $rootScope.utils = {
            img: null,
            id: null,
            role: null,
        }

        $scope.initial = function () {
            if (localStorage.getItem("token")) {
                $rootScope.currentSession = true;
                $rootScope.role = localStorage.getItem("role");
                $rootScope.utils.img = $scope.decodeToken(localStorage.getItem("token")).image;
                $rootScope.utils.id = $scope.decodeToken(localStorage.getItem("token")).id;
                $rootScope.utils.role = $scope.decodeToken(localStorage.getItem("token")).role;
            } else {
                $rootScope.currentSession = false;
                $rootScope.utils = {
                    img: null,
                    id: null,
                    role: null,
                }
            }
        }

        $scope.getToken = function () {
            $rootScope.role = localStorage.getItem("role")
            if (localStorage.getItem("token")) {
                return true;
            } else {
                return false;
            }
        }

        $rootScope.currenteSession = false
        $rootScope.role = ""

        this.beforeLogin = () => {
            return $http({
                method: "POST",
                url: `${APP_URL.url}/user/session`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                },
                data: $scope.user,
            }).then((res) => {
                this.login()
            }).catch((err) => {
                console.log(err);
            });
        };

        $scope.logout = () => {
            $rootScope.currenteSession = false;
            $window.localStorage.clear();
            $window.location.href = "#!/";
            $scope.initial()
        }

        (() => {
            "use strict";
            const forms = document.querySelectorAll(".needs-validation");
            Array.prototype.slice.call(forms).forEach((form) => {
                form.addEventListener(
                    "submit",
                    (event) => {
                        if (!form.checkValidity()) {
                            event.preventDefault();
                            event.stopPropagation();
                        } else {
                            this.beforeLogin();
                        }
                        form.classList.add("was-validated");
                    }
                )
            })
        })

        $scope.isRegister;

        $scope.user = {
            username: null,
            password: null,
        };

        $scope.tmp = {
            state: null,
        }

        $scope.register = {
            username: null,
            password: null,
            passwordConfirm: null,
            name: null,
            lastname: null,
            surname: null,
            image: null,
            city: null,
            phone: null,
            birthDate: null,
            gender: null,
            roles: null,
        }

        $scope.uploadedPic;

        $(document).ready(function () {
            $scope.isRegister = false;
            $scope.uploadedPic = false;
        })

        this.changeRegisterMode = () => {
            $scope.user = {
                username: null,
                password: null,
            };

            $scope.register = {
                username: null,
                password: null,
                passwordConfirm: null,
                name: null,
                lastname: null,
                surname: null,
                image: null,
                city: null,
                phone: null,
                birthDate: null,
                gender: null,
                roles: null,
            };

            $scope.tmp = {
                state: null,
            };
            $scope.isRegister = !$scope.isRegister;
            $scope.uploadedPic = false;
        }

        this.setRole = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/user/roles/${$scope.register.roles.id}`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                },
            }).then((res) => {
                $scope.register.roles = res.data;
            })
        }

        this.save = async () => {
            await this.uploadProfilePic();
            if ($scope.register.username != null && $scope.register.password != null && $scope.register.passwordConfirm != null && $scope.register.name != null && $scope.register.lastname != null && $scope.uploadedPic == true && $scope.register.city != null && $scope.register.phone != null && $scope.register.birthDate != null && $scope.register.gender != null && $scope.register.roles) {
                if ($scope.register.password == $scope.register.passwordConfirm) {
                    return $http({
                        method: "POST",
                        url: `${APP_URL.url}/user/register`,
                        headers: {
                            "Content-Type": "application/json",
                            Accept: "application/json",
                        },
                        data: $scope.register
                    }).then((res) => {
                        if (res.data) {
                            this.changeRegisterMode();
                            notyf.success("Cuenta creada correctamente, inicia sesión");
                        } else {
                            notyf.error("Ocurrió un error");
                        }
                    }).catch((e) => {
                        console.log(e.error);
                        notyf.error("Ocurrió un error");
                    });
                } else {
                    notyf.error("Las contraseñas deben ser iguales");
                }
            } else {
                notyf.error("Llena los campos faltantes");
            }
        }

        this.findAllStates = () => {
            if ($rootScope.currentSession) {
                $window.location.href = "#!/"
            } else {
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

        this.login = () => {
            return $http({
                method: "POST",
                url: `${APP_URL.url}/user/login`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                },
                data: $scope.user,
            }).then((res) => {
                if (res.data) {
                    $window.localStorage.setItem("token", res.data.jwtToken);
                    let name = $scope.decodeToken(res.data.jwtToken)
                    $window.localStorage.setItem("role", name.role);
                    $rootScope.currentSession = $scope.getToken();
                    notyf.success("¡Bienvenido(a)!");
                    $scope.initial();
                    if (name.role == "ROLE_RECLUTADOR") {
                        $window.location.href = "#!/mis-vacantes"
                    } else if (name.role == "ROLE_CANDIDATO") {
                        $window.location.href = "#!/vacantes"
                    }
                } else {
                    notyf.error("Usuario y/o Contraseña Incorrecto");
                }
            }).catch((err) => {
                console.log(err);
            });
        };

        $scope.decodeToken = (token) => {
            var base64Url = token.split(".")[1];
            var base64 = base64Url.replace("-", "+").replace("_", "/");
            return JSON.parse($window.atob(base64));
        }

        this.findRoles = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/user/roles`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                },
            }).then((res) => {
                $scope.listRoles = res.data;
            })
        }

        this.uploadProfilePic = async () => {
            let picture = document.getElementById("picture").files[0];
            var formData = new FormData();
            if (picture) {
                formData.append("file", picture);
                let res = await fetch(`${APP_URL.url}/user/upload/picture`, {
                    method: "POST",
                    body: formData,
                }).then((r) => {
                    $scope.uploadedPic = true;
                });
            }
        };

    }
])