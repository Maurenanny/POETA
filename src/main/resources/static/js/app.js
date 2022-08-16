/*const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

sign_up_btn.addEventListener("click", () => {
  container.classList.add("sign-up-mode");
});

sign_in_btn.addEventListener("click", () => {
  container.classList.remove("sign-up-mode");
});

 */
angular
  .module("routingApp", ["ngRoute", "ngSanitize"])
  .config([
    "$routeProvider",
    function ($routeProvider) {
      $routeProvider
        .when("/", {
          templateUrl: "/landing.html",
          /* resolve: {
            auth: function (AuthService) {
              return AuthService.Auth();
            },
          }, */
        })
        .when("/login", {
          templateUrl: "/login.html",
          controller: "LoginCtrl",
          controllerAs: "ctrlLogin",
        })
        .when("/perfil", {
          templateUrl: "/view/profile/perfil.html"
        })
        .when("/testV", {
          templateUrl: "/view/test/test.html",
          controller: "TestCtrl",
          controllerAs: "ctrlTest",
        })
    }
  ])
  .constant('APP_URL', {
    url: "http://localhost:8080"
  })
  .value("SESSION", {
    token: localStorage.getItem("token"),
  }).run(function ($rootScope, $location) {
    $rootScope.$on(
      "$routeChangeError",
      function (event, current, previous, rejection) {
        if (rejection === "Not Authenticated") {
          $location.path("/login");
        }
        if (rejection === "Session") {
          $location.path("/"); //Cambiar a la que corresponde xdxdxd
        }
      }
    )
  })
