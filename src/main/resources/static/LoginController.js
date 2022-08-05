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

        $scope.initial = function () {
            $rootScope.currenteSession = $scope.getToken()
            $rootScope.role = localStorage.getItem("role")
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
        }

        $(document).ready(function () {
            $scope.isRegister = false;
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
            }
            $scope.isRegister = !$scope.isRegister;
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
                    let name = this.decodeToken(res.data.jwtToken)
                    $window.localStorage.setItem("role", name.role);
                    $rootScope.currentSession = $scope.getToken();
                    notyf.success("¡Bienvenido(a)!");
                    $window.location.href = "#!/"; //cambiar dependiendo el rol, si es reclutador mandar a sus vacantes, si es postulante mandar a su perfil
                } else {
                    notyf.error("Usuario y/o Contraseña Incorrecto");
                }
            }).catch((err) => {
                console.log(err);
            });
        };

        this.decodeToken = (token) => {
            var base64Url = token.split(".")[1];
            var base64 = base64Url.replace("-", "+").replace("_", "/");
            return JSON.parse($window.atob(base64));
        }

        this.uploadProfilePic = async () => {
            let picture = document.getElementById("picture").files[0];
            var formData = new FormData();
            if (picture) {
                formData.append("file", picture);
                let res = await fetch(`${APP_URL.url}/user/upload/picture`, {
                    method: "POST",
                    body: formData,
                });
                const data = await res.json();
                console.log(data);
            }
        };

    }
])