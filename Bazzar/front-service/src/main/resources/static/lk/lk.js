angular.module('store').controller('lkController', function ($scope, $http, $localStorage, $rootScope) {
    // использовать для локального подключения
    const contextPathCore = 'http://localhost:5555/core/api/v1/';
    const contextPathNotification = 'http://localhost:5555/notification/api/v1/';
    const contextPathOrganization = 'http://localhost:5555/organization/api/v1/';
    // использовать для удаленного подключения
    // const contextPath = 'http://95.165.90.118:443/core/api/v1';
    // const contextPathAuth = 'http://95.165.90.118:443/auth/api/v1/';

    $scope.loadHistory = function () {
        $http.get(contextPathCore + 'history')
            .then(function (response) {
                $scope.historyList = response.data;
            });
    };

    $scope.saveProduct = function () {
        $http.post(contextPathCore + 'products', $scope.save_product)
            .then(function (response) {
                alert("Успех!");
                $scope.save_product.title = null;
                $scope.save_product.description = null;
                $scope.save_product.organizationTitle = null;
                $scope.save_product.price = null;
                $scope.save_product.quantity = null;
            });
    };

    $scope.createCompany = function () {
        // Получаем значения полей из формы
        const companyOwner = $localStorage.simpleUser.username;
        console.log(companyOwner);
        const companyName = document.getElementById("companyName").value;
        const companyDescription = document.getElementById("companyDescription").value;
        const companyImage = document.getElementById("companyImage").files[0];

        // Создаем объект FormData и добавляем значения полей
        const formData = new FormData();
        formData.append("owner", companyOwner)
        formData.append("name", companyName);
        formData.append("description", companyDescription);
        formData.append("companyImage", companyImage);

        // Отправляем POST запрос на сервер, передавая объект FormData
        fetch(contextPathOrganization + 'organizations', {
            method: "POST",
            body: formData
        }).then(function (response) {
            alert("Организация создана успешно, ожидайте модерации администратором.");
        }).catch(error => console.error(error));
    };

    $scope.loadNotification = function () {
        $http.get(contextPathNotification + 'notifications')
            .then(function (response) {
                $scope.notificationList = response.data;
            });
    };

    $scope.deleteNotification = function (id) {
        $http.delete(contextPathNotification + 'notifications/' + id)
            .then(function (response) {
                alert('Сообщение удалено.');
                $scope.loadNotification();
            });
    };

    $scope.loadNotification();
    $scope.loadHistory();
    $rootScope.loadProducts();
});