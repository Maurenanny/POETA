(function () {

    angular.module('app', []);
    angular.module('app').controller("WizardController", [wizardController]);

    function wizardController() {
        var vm = this;
        
        //Model
        vm.currentStep = 1;
        vm.steps = [
          {
            step: 1,
            name: "Paso 1",
            wizard: "step1.html"
          },
          {
            step: 2,
            name: "2 Paso",
            wizard: "step2.html"
          },   
          {
            step: 3,
            name: " 3 Paso",
            wizard: "step3.html"
          },             
        ];
        vm.user = {};
        
        //Functions
        vm.gotoStep = function(newStep) {
          vm.currentStep = newStep;
        }
        
        vm.getStepWizard = function(){
          for (var i = 0; i < vm.steps.length; i++) {
                if (vm.currentStep == vm.steps[i].step) {
                    return vm.steps[i].wizard;
                }
            }
        }
        
        vm.save = function() {
          alert(
            "Saving form... \n\n" + 
            "nombre paso 1: " + vm.user.name + "\n" + 
            "nombre paso 2: " + vm.user.name + "\n" + 
            "nombre paso 3: " + vm.user.name);
        }
    }
    
})();