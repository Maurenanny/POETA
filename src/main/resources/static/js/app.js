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
          /* controller: "LoginCtrl",
          controllerAs: "ctrlLogin",
          resolve: {
            auth: function (AuthService) {
              return AuthService.login();
            },
          }, */
        })
        .when("/test", {
          templateUrl: "/view/test.html",
        })
    }
  ])
