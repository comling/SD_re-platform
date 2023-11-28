let app = angular.module("app", [
    "common",])
    .controller("MainController", [
        "$scope", "$http", "$q", "$window", "$interval", "$timeout", '$filter',
        MainController
    ]);
app.config(['$qProvider', '$httpProvider', function ($qProvider, $httpProvider) {
    $qProvider.errorOnUnhandledRejections(false);
    $httpProvider.interceptors.push('httpInterceptors');
}]);


function MainController(
    $scope, $http, $q, $window, $interval, $timeout, $filter
) {
    const Toast = Swal.mixin({
        toast: true,
        position: 'center',
        showConfirmButton: false,
        timer: 1800,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
    })

    /**
     * 페이지네이션 함수
     * */
    function pagination(total, offset, limit, displayPages, sort) {
        var page = Math.floor(offset / limit),
            totalPages = Math.ceil(total / limit),
            pages = [],
            l = Math.floor(offset / limit / displayPages) * displayPages,
            c = Math.min(l + displayPages, totalPages),
            s = l;

        for (; s < c; s++) {
            pages.push(s);
        }

        return {
            total: total,
            limit: limit,
            page: page,
            offset: offset,
            pageFirst: 1,
            pagePrev: Math.max(0, pages[0] - 1),
            pageNext: Math.min(totalPages - 1, pages[pages.length - 1] + 1),
            pageLast: totalPages - 1,
            pageTotal: totalPages,
            pages: pages,
            sort: sort
        }
    }

    angular.element(document).ready(function () {
        console.log("MainController.js is ready");
        $scope.global = {};

        angular.element("input[type='hidden']").each(function () {
            let el = angular.element(this);
            $scope.global[el.attr("name")] = el.val();
        });

        /* 하단 배너 슬라이딩 용 */
        $('.slider-wrapper').slick({
            slidesToShow: 4,
            slidesToScroll: 1,
            autoplay: true,
            autoplaySpeed: 3000,
            arrows : false
        });

        getMinMaxYear();
        getSumCapacity();
        getBusinessDataList();
        getSumCount();
    });

    $scope.searchDto = {
        pageNum: 1,
        size: 10,
        pageSize: 10,
        keyword: '',
        searchType: {
            BNAME: '',          // 사업명
            facilityType: '',   //'시설구분'
            energy: '',         //'에너지원'
            BYEAR: '',          //'사업연도'
            sigungu: ''         //'시/군/구'
        }
    }


    $scope.boardData = {
        list: [],
        total: 0,
        offset: 1,
        limit: 10,
        size: 10,
        pagination: {}
    };

    $scope.yearData = {
        max : 0,
        min : 0
    }
    $scope.sumCapacity;
    $scope.sumCount;

    $scope.moveTo = function (path, newWindow, option = "") {
        newWindow ?
            $window.open($scope.global.baseurl + path, newWindow, option) :
            $window.location.href = $scope.global.baseurl + path;
    };

    $scope.copy_to_clipboard = function () {
        let copyText = document.getElementById('referralCode');
        copyText.select();
        copyText.setSelectionRange(0, 99999);
        document.execCommand("Copy");

        Toast.fire({
            icon: 'success',                         // Alert 타입 (success, warning, error, info, question)
            title: '복사 되었습니다',         // Alert 제목
            html: "사용을 원하는 곳에서 붙여넣기(ctrl + v) 해주세요",
        })
    }


    function getBusinessDataList(page, size, filter){
        $("#loading").show();
        page = page | 0;
        size = size | 10;
        // const url = '/api/getBusinessDataListSearch?sort=userID,DESC&page=' + page + '&size=' + size;
        const url = '/api/getBusinessDataList';
        doRequest(url, '', function(result){
            console.log(result),
            $scope.boardData = result.data,
                $scope.boardData.pagination = pagination(result.data.totalElements, result.data.pageable.offset, size, 10)
                    ,console.log($scope.boardData),
                $("#loading").hide();
        }, 'GET');
    }

    /* main home - 기간 */
    function getMinMaxYear(){
        $("#loading").show();
        const url = '/api/getMinMaxYear';
        doRequest(url, '', function(result){
            console.log("getMinMaxYear : " , result),
                console.log(result.data.list.MIN),
                console.log(result.data.list.MAX),
                $scope.yearData.min = result.data.list.MIN;
                $scope.yearData.max = result.data.list.MAX;
                $("#loading").hide();
        }, 'GET');
    }

    /* main home - 총 설비 용량 */
    function getSumCapacity(){
        $("#loading").show();
        const url = '/api/getSumCapacity';
        doRequest(url, '', function(result){
            console.log("getSumCapacity : " , result),
                $scope.sumCapacity = result.data;
                $("#loading").hide();
        }, 'GET');
    }

    function getSumCount(){
        $("#loading").show();
        const url = '/api/getSumCount';
        doRequest(url, '', function(result){
            console.log("getSumCount : " , result),
                $scope.sumCount = result.data;
            $("#loading").hide();
        }, 'GET');
    }

    $scope.nextMore = function(){
        Swal.fire({
            icon: 'info',
            title: '준비중',
            text: '해당 메뉴는 준비중입니다.'
        })
    }


    /**
     * 통신 함수
     * */
    function doRequest(url, body, callback, method) {
        let request = {
            method: method || "POST",
            url: url,
            headers: {
                Accept: "application/json; charset=UTF-8",
                "Content-Type": "application/json; charset=utf-8",
                "X-CSRF-TOKEN": $scope.global._csrf
            },
            timeout: $q.defer().promise
        };
        body && (request.data = angular.toJson(body)), $http(request).then(function (response) {
            callback && callback(response)
        })
    }
}



Array.prototype.find || Object.defineProperty(Array.prototype, "find", {
    value: function (e) {
        if (null == this) throw new TypeError('"this" is null or not defined');
        var t = Object(this),
            o = t.length >>> 0;
        if ("function" != typeof e) throw new TypeError("predicate must be a function");
        for (var r = arguments[1], n = 0; n < o;) {
            var a = t[n];
            if ($scope.call(r, a, n, t)) return a;
            n++
        }
    },
    configurable: true,
    writable: true
});

app.directive('toNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            ctrl.$parsers.push(function (value) {
                return parseFloat(value || '');
            });
        }
    };
});