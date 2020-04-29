/** 商品详细页（控制器）*/
app.controller('itemController', function($scope){
    /** 定义购买数量操作方法 */
    $scope.addNum = function(x){
        parseInt($scope.num);
        $scope.num += x;
        if($scope.num < 1){
            $scope.num = 1;
        }
    };

    //定义一个变量记录选择的规格
    $scope.spec={};
    //定义方法吧选择的规格 赋值给这个变量 结果{"机身内存":"128G","网络":"电信4G"}
    $scope.selectSpec=function (specName, value) {
        $scope.spec[specName]=value;
        //在用户选择规格后触发读取方法：
        searchSku();
    }

    //判断用户选择哪个规格 选择的给样式
    $scope.isSelected=function (specName,value) {
        return $scope.spec[specName]==value;
    }

    /** 加载默认的SKU */
    $scope.loadSku = function () {
        //获取第一个sku
        $scope.sku = itemList[0];
        //得到选项规格 吧这个规格赋值给选中的规格选项
        $scope.spec=JSON.parse($scope.sku.spec);
    }

    /** 根据用户选中的规格选项，查找对应的SKU商品 */
    /** 根据用户选中的规格选项，查找对应的SKU商品 */
    var searchSku = function(){
        for (var i = 0; i < itemList.length; i++){
            /** 判断规格选项是不是当前用户选中的 */
            if(itemList[i].spec == JSON.stringify($scope.spec)){
                $scope.sku = itemList[i];
                return;
            }
        }
    };

    /** 加入购物车事件绑定 */
    $scope.addToCart = function(){
        alert('skuid:' + $scope.sku.id);
    };

});
