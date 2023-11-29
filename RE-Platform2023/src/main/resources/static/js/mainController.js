let app = angular.module("app", [
    "common","techDna.components"])
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
    function pagination(total, offset, limit, displayPages, totalSumCapacity, sort) {
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
            sort: sort,
            totalSumCapacity: totalSumCapacity
        }
    }


    $scope.searchDto = {
        pageNum: 1,
        size: 10,
        pageSize: 10,
        keyword: '',
        searchFilter: {
            BNAME: '사업명',          // 사업명
            facilityType: '시설구분',   //'시설구분'
            energy: '에너지원',         //'에너지원'
            BYEAR: '사업연도',          //'사업연도'
            sigungu: '시/군/구'         //'시/군/구'
        }
    }

    angular.element(document).ready(function () {
        console.log("MainController.js is ready");
        $scope.global = {};

        angular.element("input[type='hidden']").each(function () {
            let el = angular.element(this);
            $scope.global[el.attr("name")] = el.val();
        });

        /* 하단 배너 슬라이딩 용 모든 페이지에 적용*/
        $('.slider-wrapper').slick({
            slidesToShow: 4,
            slidesToScroll: 1,
            autoplay: true,
            autoplaySpeed: 3000,
            arrows : false
        });

        if($scope.global.pageName =='home'){
            getMinMaxYear(), getSumCapacity(), getBusinessDataList(), getSumCount();
        } else if($scope.global.pageName =='local'){
            getMinMaxYear(), getSearchFilter(), $scope.getSearchBusinessDataList($scope.searchDto);
        }
    });



    /* 최초 필터 로드용 */
    $scope.onSearchFilter = {
        BNAME: [],          // 사업명
        BYEAR: [],          //'사업연도'
        facilityType: [],   //'시설구분'
        energy: [],         //'에너지원'
        sigungu: []         //'시/군/구'
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
    $scope.pagination = {
        page: 0,
        offset: 0,
        limit: 10
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
    /* main home - 총 설비 개소 */
    function getSumCount(){
        $("#loading").show();
        const url = '/api/getSumCount';
        doRequest(url, '', function(result){
            console.log("getSumCount : " , result),
                $scope.sumCount = result.data;
            $("#loading").hide();
        }, 'GET');
    }


    /* local - search filter load */
    function getSearchFilter(){
        $("#loading").show();
        const url = '/api/getSearchFilter';
        doRequest(url, '', function(result){
            console.log("getSearchFilter : " , result),
                $scope.onSearchFilter.BNAME = result.data.BNAME,
                $scope.onSearchFilter.BYEAR = result.data.BYEAR,
                $scope.onSearchFilter.energy = result.data.energy,
                $scope.onSearchFilter.facilityType = result.data.facilityType,
                $scope.onSearchFilter.sigungu = result.data.sigungu,
            $("#loading").hide();
        }, 'GET');
    }

    /* local - search for filter */
    $scope.getSearchBusinessDataList = function(searchDto){
        $("#loading").show();
        console.log("searchDto : ", searchDto)
        let body = {
                pageNum: searchDto.pageNum,
                size: searchDto.size,
                pageSize: searchDto.pageSize,
                keyword: searchDto.keyword,
                searchFilter: {
                    BNAME: searchDto.searchFilter.BNAME,
                    facilityType: searchDto.searchFilter.facilityType,
                    energy: searchDto.searchFilter.energy,
                    BYEAR: searchDto.searchFilter.BYEAR,
                    sigungu: searchDto.searchFilter.sigungu
            }
        };
        /* 최초 검색시 초기화 */
        if(searchDto.searchFilter.BNAME === "사업명") body.searchFilter.BNAME = "";
        if(searchDto.searchFilter.facilityType === "시설구분") body.searchFilter.facilityType = "";
        if(searchDto.searchFilter.energy === "에너지원") body.searchFilter.energy = "";
        if(searchDto.searchFilter.BYEAR === "사업연도") body.searchFilter.BYEAR = "";
        if(searchDto.searchFilter.sigungu === "시/군/구") body.searchFilter.sigungu = "";
        console.log("body : ", body)

        const url = '/api/getSearchBusinessDataList';
        $http({
            method: "POST",
            url: url,
            data: JSON.stringify(body),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
        }).then( function (result) {
            console.log("getSearchBusinessDataList : " , result);

            $scope.boardData = result.data;
            $scope.pagination = pagination(result.data.params.pagination.totalRecordCount, result.data.params.offset,
                                        result.data.params.size, result.data.params.pageSize,
                                        result.data.params.pagination.totalSumCapacity);
            $("#loading").hide();
        })
    }

    /* 페이지네이션 새로고침 역할 */
    $scope.goBoardPage = function (page) {
        console.log(page);
        page = page || 0;
        $scope.searchDto.pageNum = page.page + 1;
        $scope.searchDto.size = page.limit;
        if (page.limit !== $scope.pagination.limit) {
            $scope.pagination.limit = page.limit;
            $scope.pagination.offset = 0;
            $scope.pagination.page = 0;
        } else {
            $scope.pagination.offset = page.page * page.limit;
            $scope.pagination.page = page.page;
        }

        $scope.getSearchBusinessDataList($scope.searchDto);
    }

    $scope.keypressSearch = function(event){
        console.log(event);
        if (event.keyCode === 13)
            $scope.getSearchBusinessDataList($scope.searchDto);
    }

    $scope.excelDownload = function(searchDto){
        $("#loading").show();
        console.log("searchDto : ", searchDto)
        let body = {
            pageNum: searchDto.pageNum,
            size: searchDto.size,
            pageSize: searchDto.pageSize,
            keyword: searchDto.keyword,
            searchFilter: {
                BNAME: searchDto.searchFilter.BNAME,
                facilityType: searchDto.searchFilter.facilityType,
                energy: searchDto.searchFilter.energy,
                BYEAR: searchDto.searchFilter.BYEAR,
                sigungu: searchDto.searchFilter.sigungu
            }
        };
        /* 최초 검색시 초기화 */
        if(searchDto.searchFilter.BNAME === "사업명") body.searchFilter.BNAME = "";
        if(searchDto.searchFilter.facilityType === "시설구분") body.searchFilter.facilityType = "";
        if(searchDto.searchFilter.energy === "에너지원") body.searchFilter.energy = "";
        if(searchDto.searchFilter.BYEAR === "사업연도") body.searchFilter.BYEAR = "";
        if(searchDto.searchFilter.sigungu === "시/군/구") body.searchFilter.sigungu = "";
        console.log("body : ", body)

        const url = '/api/excelDownload';
        $http({
            method: "GET",
            url: url,
            data: JSON.stringify(body),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            cache: false,
            xhr: function () {
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 2) {
                        if (xhr.status == 200) {
                            xhr.responseType = "blob";
                        } else {
                            xhr.responseType = "text";
                        }
                    }
                };
                return xhr;
            },
        }).then( function (result) {
            console.log("excelDownload : " , result);

            var blob = new Blob([result.data], { type: "application/octetstream" });

            //Check the Browser type and download the File.
            var isIE = false || !!document.documentMode;
            if (isIE) {
                window.navigator.msSaveBlob(blob, fileName);
            } else {
                var url = window.URL || window.webkitURL;
                link = url.createObjectURL(blob);
                var a = $("<a />");
                a.attr("download", fileName);
                a.attr("href", link);
                $("body").append(a);
                a[0].click();
                $("body").remove(a);
            }

        })
    }




    /* 메뉴 준비중 알랏 */
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
                "Content-Type": "application/json; charset=utf-8"
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