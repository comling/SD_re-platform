/**
 * (required) ng-fileupload="{ 'dialog'(default) or 'drop' }" <br>
 * (required) ng-fileupload-url="{url_string_to_upload}" <br>
 * (optional) ng-fileupload-start="{callback_function_for_start}" callbackFunc($files) <br>
 * (optional) ng-fileupload-files="{model_for_files_info_with_progess}" <br>
 * (optional) ng-fileupload-completed="{callback_function_for_completed}" callbackFunc($response, $file) <br>
 * <br>
 * <code>
 * $files: [ ... $file ... ]
 * $file: {
 * 		... (File object) ...
 * 		progress: { upload progress from 0 to 1 }
 * 		completed: { false or true }
 * }
 * </code>
 * @param $http
 * @returns
 */
angular.module('common', [])
    .factory('httpInterceptors', ['$q', '$rootScope', '$window',  function ($q, $rootScope,$window) {
        return {
            'request': function (config) {
                $("#loading").show();
                return config;
            },
            'response': function (response) {
                $("#loading").hide();
                return response;
            },
            'responseError': function (response) {
                var httpStatusCode = response.status !== undefined ? response.status : 400;
                switch (httpStatusCode) {
                    case 403:
                        // history.go(0);
                        location.reload();
                        return;
                    case 500:
                    case 404:
                        console.log('/404')
                        // location.href('/404');
                        break;
                }
                $("#loading").hide();
                return $q.reject(response);
            }
        }
    }])
    .directive('ngFileupload', function ($http) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs, ngModel) {

                if (attrs.ngFileupload == "dialog" || !attrs.ngFileupload) {
                    element.bind('change', function (e) {
                        upload(e.target.files);
                    });
                } else if (attrs.ngFileupload == "drop") {
                    element.on('dragover', function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                    });
                    element.on('dragenter', function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                    });
                    element.on("drop", function (e) {
                        e.preventDefault();
                        e.stopPropagation();

                        if (!e.originalEvent.dataTransfer)
                            return false;
                        if (!e.originalEvent.dataTransfer.files.length)
                            return false;

                        upload(e.originalEvent.dataTransfer.files);
                    });
                }

                function upload(filesObj) {
                    var url = scope.$eval(attrs.ngFileuploadUrl);

                    var files = [];
                    for (var i = 0; i < filesObj.length; i++) {
                        files.push(filesObj[i]);
                        filesObj[i].filename = filesObj[i].name; // 'name' is read-only
                    }

                    if (attrs.ngFileuploadFiles) {
                        scope[attrs.ngFileuploadFiles] = files;
                    }

                    if (attrs.ngFileuploadStart) {
                        if (typeof scope[attrs.ngFileuploadStart] === "function") {
                            scope[attrs.ngFileuploadStart](files);
                        } else {
                            scope.$eval(attrs.ngFileuploadStart, {
                                $files: files
                            });
                        }
                    }


                    files.forEach(function (file, index) {
                        var formdata = new FormData();
                        formdata.append("file", file);
                        file.completed = false;

                        $http({
                            method: "POST",
                            url: url,
                            data: formdata,
                            headers: {
                                "Accept": "application/json; charset=UTF-8",
                                "Content-Type": undefined, // auto-detection
                                "X-CSRF-TOKEN": scope.global._csrf // global._csrf_header: global._csrf
                            },
                            uploadEventHandlers: {
                                progress: function (e) {
                                    file.progress = e.loaded / e.total;
                                }
                            }
                        }).then(function (response) {
                            file.completed = true;
                            if (attrs.ngFileuploadCompleted) {
                                if (typeof scope[attrs.ngFileuploadCompleted] === "function") {
                                    scope[attrs.ngFileuploadCompleted](response.data, index, file);
                                } else {
                                    scope.$eval(attrs.ngFileuploadCompleted, {
                                        $response: response.data,
                                        $index: index,
                                        $file: file
                                    });
                                }
                            }
                        });
                    });
                }
            }
        };
    })
    .directive("ngTooltip", function () {
        var tooltip = angular.element("#tooltip-template");
        return {
            restrict: 'A',
            link: function (scope, element, attrs, ngModel) {

                var tip = scope.$eval(attrs.ngTooltip);
                scope.$watch(attrs.ngTooltip, function () {
                    tip = scope.$eval(attrs.ngTooltip);
                }, true);
                element.bind("mouseover", function (e) {

                    var top = angular.element(e.target).offset().top;
                    var left = angular.element(e.target).offset().left;
                    var w = angular.element(e.target).width();
                    var h = angular.element(e.target).height();

                    tooltip.find(".tooltiptext").html(tip || "설명 없음");
//				var w2 = tooltip.width();
//				var h2 = tooltip.height();

                    tooltip.css({top: top + h, left: left + (w / 2), zIndex: 100});
                    tooltip.find(".tooltiptext")
                        .css({visibility: "visible"});
                });
                element.bind("mouseleave", function (e) {
                    tooltip.find(".tooltiptext").css({visibility: "hidden"});
                })

            }
        };
    })

