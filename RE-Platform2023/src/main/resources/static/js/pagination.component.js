
angular.module('techDna.components', [])
.component('tdSearchPagination', {
    templateUrl: 'pagination.component.html',
    controller: searchPaginationComponent,
    bindings: {
        onPage: '&',
        context: '<',
    }
});

function searchPaginationComponent($scope) {
    'use strict';
    var self = this;

    self.limitOptions = [10, 20, 50];

    self.page = 0;
    self.limit = 10;
    self.pages = [0];
    self.offset = 0;
    self.total = 0;
    self.pageLast = 1;

    self.projectCreateEnabled = false;
    self.projectApplyEnabled = false;

    self.sort = false;
    self.sortField = null;
    self.totalSumCapacity = 0;

    self.reload = function(page) {
        if (self.sort){
            self.onPage({
                $event: {
                    page: page || self.page,
                    limit: self.limit,
                    sortField: self.sortField
                }
            });
        }else {
            self.onPage({
                $event: {
                    page: page || self.page,
                    limit: self.limit
                }
            });
        }
    };

    self.goPage = function(page) {
        //patents.offset = p * patents.limit; searchPatents()
        page = Number.isInteger(page) ? page : Math.floor((self.directPage || 0) - 1);
        self.page = Math.max(0, page);
        //directAcces 안씀
        // if (!$scope.directAccess.page.$valid) {
        //     self.directPage = self.page + 1;
        //     return;
        // }
        self.reload();
    };

    self.prevPage = function () {
        self.page -= 1;
        self.reload();
    };

    self.prevPage = function (page) {
        self.page -= page;
        if(self.page < 0)
            self.page = 0;
        self.reload();
    };

    self.nextPage = function () {
        //patents.offset = patents.pagination.pageNext * patents.limit; searchPatents()
        self.page += 1;
        self.reload();
    };

    self.nextPage = function (page) {
        //patents.offset = patents.pagination.pageNext * patents.limit; searchPatents()
        self.page += page;
        var limit = self.total / self.limit;
        if(self.page > limit)
            self.page = Math.floor(limit);
        self.reload();
    };

    self.endPage = function () {
        var limit = self.total / self.limit;
        if((self.total % self.limit) == 0)
            limit -= 1;
        self.goPage(Math.floor(limit));
    };

    self.$onInit = function() {
        self.page = self.context.page;
        self.directPage = self.page + 1;
        self.pages = self.context.pages;
        self.limit = self.context.limit;
        self.total = self.context.total;
        self.sort = self.context.sort;
        self.totalSumCapacity = self.context.totalSumCapacity;
    };

    self.$onChanges = function(bound){
        var ctx = bound.context.currentValue;
        if (!ctx) {return;}
        self.page = ctx.page;
        self.pageLast = ctx.pageLast + 1;
        self.directPage = self.page + 1;
        self.pages = ctx.pages;
        self.limit = ctx.limit;
        self.total = ctx.total;
        self.sort = ctx.sort;
        self.totalSumCapacity = ctx.totalSumCapacity;
    }
}