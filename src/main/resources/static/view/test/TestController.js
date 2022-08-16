angular.module("routingApp").controller("TestCtrl", [
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

        this.testDownload = () => {
            notyf.success("Descargando...");
            return $http({
                method: "GET",
                url: `${APP_URL.url}/postulant/cv/test`,
                responseType: "arraybuffer",
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                },
            }).then((res) => {
                const fileName = "test";
                const a = document.createElement("a");
                document.body.appendChild(a);
                const file = new Blob([res.data], {type: "application/pdf"});
                a.href = window.URL.createObjectURL(file);
                a.download = fileName;
                a.click();
                notyf.error("Descarga completada");
            })
        }

        this.testEmail = () => {
            return $http({
                method: "GET",
                url: `${APP_URL.url}/process/mail/test`,
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                },
            }).then((res) => {
                
            })
        }
    }
])