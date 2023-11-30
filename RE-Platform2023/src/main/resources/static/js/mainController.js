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
            getHomeMainAggrList();
            getMinMaxYear();
            getSumCapacity();
            // $scope.getSearchBusinessDataList($scope.searchDto);

        } else if($scope.global.pageName =='local'){
            getMinMaxYear();
            getSearchFilter();
            $scope.getSearchBusinessDataList($scope.searchDto);
        } else if ($scope.global.pageName === 'map') {
            loadKAKAOMap();
            getMinMaxYear();
            getSearchFilter();
            $scope.searchDto.size = 1000;
            $scope.getSearchBusinessDataList($scope.searchDto, $scope.addMarkerResponse);
        } else if($scope.global.pageName === 'business'){
            $scope.getSearchBusinessDataList($scope.searchDto);
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

    /*메인 홈화면 집계용*/
    function getHomeMainAggrList(){
        $("#loading").show();
        const url = '/api/getHomeMainAggrList';
        doRequest(url, '', function(result){
            console.log("getHomeMainAggrList : " , result),
                $scope.boardData.list = result.data.list;
                $scope.pagination.total = result.data.count;
                $scope.pagination.totalSumCapacity = result.data.sumCapacity;
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
    $scope.getSearchBusinessDataList = function(searchDto, callback){
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
            callback && callback(result);
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

    /* Excel Download */
    $scope.excelDownload = function(searchDto){
        $("#loading").show();

        /* 조회 데이터 없을 경우 return */
        if($scope.boardData.list.length === 0 ) {
            Swal.fire({
                icon: 'warning',
                title: '조회 데이터가 0건 입니다.',
                text: '엑셀 다운로드는 조회 데이터가 1건 이상일경우에만 가능합니다.'
            })
            return;
        }

        /* 전체 데이터를 내려줄 것이므로 pagenum, size 등 제거 */
        let body = {
            keyword: searchDto.keyword,
            BNAME: searchDto.searchFilter.BNAME,
            facilityType: searchDto.searchFilter.facilityType,
            energy: searchDto.searchFilter.energy,
            BYEAR: searchDto.searchFilter.BYEAR,
            sigungu: searchDto.searchFilter.sigungu
        };
        /* 최초 검색시 초기화 */
        if(searchDto.searchFilter.BNAME === "사업명") body.BNAME = "";
        if(searchDto.searchFilter.facilityType === "시설구분") body.facilityType = "";
        if(searchDto.searchFilter.energy === "에너지원") body.energy = "";
        if(searchDto.searchFilter.BYEAR === "사업연도") body.BYEAR = "";
        if(searchDto.searchFilter.sigungu === "시/군/구") body.sigungu = "";

        const url = '/api/excelDownload';

        let excelDownloadForm = document.createElement('form');
        excelDownloadForm.setAttribute("method", "POST");
        excelDownloadForm.setAttribute("action", url);

        let input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'keyword');
        input.setAttribute('value', body.keyword);
        excelDownloadForm.appendChild(input);

        input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'BNAME');
        input.setAttribute('value', body.BNAME);
        excelDownloadForm.appendChild(input);

        input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'facilityType');
        input.setAttribute('value', body.facilityType);
        excelDownloadForm.appendChild(input);

        input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'energy');
        input.setAttribute('value', body.energy);
        excelDownloadForm.appendChild(input);

        input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'BYEAR');
        input.setAttribute('value', body.BYEAR);
        excelDownloadForm.appendChild(input);

        input = document.createElement('input');
        input.setAttribute('type', 'hidden');
        input.setAttribute('name', 'sigungu');
        input.setAttribute('value', body.sigungu);
        excelDownloadForm.appendChild(input);

        console.log(excelDownloadForm);

        document.body.appendChild(excelDownloadForm);
        excelDownloadForm.submit();

        $("#loading").hide();

    }

    $scope.detailModalData;
    $scope.mdzIndex = 10;
    /* 상세 페이지 오픈 */
    $scope.openModal = function (mdId, data) {
        console.log(data);
        $scope.detailModalData = data;
        $scope.mdzIndex++;
        if (mdId === null || mdId === '') return true;

        if(mdId === 'businessData-modal'){
            getAsDataList(data.userID);
        }


        $('#' + mdId).addClass('js-is-active').css({'z-index': $scope.mdzIndex});
    }

    function getAsDataList(userID){
        $("#loading").show();
        $scope.detailModalData.asData = {};

        let url = '/api/getAsDataList?userID=' + userID*1;
        $http({
            method: "GET",
            url: url,
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
        }).then( function (result) {
            $("#loading").hide();
            $scope.detailModalData.asData = result.data;
        }).catch(e=>{
            console.log(e);
            $("#loading").hide();
            return null;
        })
    }

    $scope.closeNaraModal = function (mdId) {
        $scope.detailModalData = {};
        $('#' + mdId).removeClass('js-is-active').css({'z-index': ''});
        if ($('.js-modal.js-is-active').length === 0) {
            $scope.mdzIndex = 10;
        }
    }



    /* 메뉴 준비중 알랏 */
    $scope.nextMore = function(){
        Swal.fire({
            icon: 'info',
            title: '준비중',
            text: '해당 메뉴는 준비중입니다.'
        })
    }

    var markers = [];
    var map;
    function loadKAKAOMap() {
        const container = document.getElementById('map') //지도를 담을 영역의 DOM 레퍼런스
            , options = { //지도를 생성할 때 필요한 기본 옵션
            center: new kakao.maps.LatLng(37.4678, 127.511), //지도의 중심좌표.
            level: 7 //지도의 레벨(확대, 축소 정도)
        };
        map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
    }

    function setCenter(position) {
        // 이동할 위도 경도 위치를 생성합니다
        var moveLatLon = position;

        // 지도 중심을 이동 시킵니다
        map.setCenter(moveLatLon);
    }

    function addMarker(position, info) {
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            position: position,
            clickable: true
        });
        marker.info = info;
        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);
        kakao.maps.event.addListener(marker, 'click', function() {
            $scope.openModal('businessData-modal', marker.info);
        })
        // 생성된 마커를 배열에 추가합니다
        markers.push(marker);
    }
    function delMarker() {
        markers = [];
    }
    // 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
    function setMarkers(map) {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }
    }

    // "마커 보이기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에 표시하는 함수입니다
    function showMarkers() {
        setMarkers(map)
    }

    // "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
    function hideMarkers() {
        setMarkers(null);
    }

    $scope.addMarkerResponse = function (response) {
        hideMarkers();
        delMarker();
        let x = 0;
        let y = 0;
        response.data.list.forEach(item => {
            addMarker(new kakao.maps.LatLng(item.entY, item.entX), item)
            x += item.entX
            y += item.entY
        })
        const length = response.data.list.length
        setCenter(new kakao.maps.LatLng(y/length, x/length))
        showMarkers();
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