/** 定义控制器层 */
app.controller('goodsController', function($scope, $controller, baseService){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});

    /** 添加或修改 */
    $scope.saveOrUpdate = function(){
        var url = "save";
        //获取富文本编辑器内容 赋值给goods.goodsDesc.introduction
        $scope.goods.goodsDesc.introduction = editor.html();
        /** 发送post请求 */
        baseService.sendPost("/goods/" + url, $scope.goods)
            .then(function(response){
                if (response.data){
                    alert("保存成功！");
                    /** 清空表单 */
                    $scope.goods = {};
                    /** 清空富文本编辑器 */
                    editor.html('');
                }else{
                    alert("操作失败！");
                }
            });
    };

    //图片上传的提交方法 用一个对象封装图片颜色 和 图片地址
    //图片上传的提交方法
    $scope.uploadFile = function () {
        baseService.uploadFile().then(function (value) {
            if (value.data.status==200){
                $scope.picEntity.url=value.data.url;
            } else {
                alert("上传失败")
            }
        })
    }

    //点击保存按钮 图片和图片颜色字眼 显示在一行上
   $scope.goods={goodsDesc:{itemImages : []}};
    $scope.addPic = function () {
        $scope.goods.goodsDesc.itemImages.push($scope.picEntity)

    };

   //点击删除按钮 删除一行图片数据
    $scope.deletePic = function (index) {
        $scope.goods.goodsDesc.itemImages.splice(index,1);
    }


    //分三级查询 第一级
    $scope.findItemCatByParentId = function (parentId, name) {
        baseService.sendGet("/itemcat/findItemCatByParentId?parentId=" + parentId).then(function (value) {
            $scope[name]=value.data;
        })
    }

    //监控parentId 如果发生改变 我们就自动查询二级
    $scope.$watch('goods.category1Id',function (newValue, oldValue) {
      if (newValue){
          $scope.findItemCatByParentId(newValue,"itemCatList2");
      }else {
          $scope.itemCatList2 = [];
      }
    });
    //监控parentId 如果发生改变 我们就自动查询三级
    $scope.$watch('goods.category2Id',function (newValue, oldValue) {

        if (newValue){
            $scope.findItemCatByParentId(newValue,"itemCatList3");
        }else {
            $scope.itemCatList3 = [];
        }
    })

    //goods.typeTemplateId 就是itemCat.typeId  记录第三级的parentId 就是第二级的itemcat.id
    $scope.$watch('goods.category3Id',function (newValue, oldVale) {
        if (newValue){
            for (var i =0 ;i<$scope.itemCatList3.length;i++){
                var itemCatList = $scope.itemCatList3[i];
                if (itemCatList.id == newValue){
                    $scope.goods.typeTemplateId = itemCatList.typeId;
                    break;
                }
            }
        }
    });

    /** 监控 goods.typeTemplateId 模板ID，查询该模版对应的品牌 */
    $scope.$watch('goods.typeTemplateId', function(newValue, oldValue) {
        if (!newValue){
            return;
        }
        baseService.findOne("/typeTemplate/findOne", newValue)
            .then(function(response){
                /** 获取模版中的品牌列表 */
                $scope.brandIds = JSON.parse(response.data.brandIds);

            });
    });

    /** 监控 goods.typeTemplateId 模板ID，读取模板中的扩展属性赋给商品的扩展属性 */
    $scope.$watch('goods.typeTemplateId', function(newValue, oldValue) {
        if (!newValue){
            return;
        }
        baseService.findOne("/typeTemplate/findOne", newValue)
            .then(function(response){
            // 设置扩展属性
                $scope.goods.goodsDesc.customAttributeItems =
                    JSON.parse(response.data.customAttributeItems);
                console.log($scope.goods)
            });
    });














    /** 查询条件对象 */
    $scope.searchEntity = {};
    /** 分页查询(查询条件) */
    $scope.search = function(page, rows){
        baseService.findByPage("/goods/findByPage", page,
			rows, $scope.searchEntity)
            .then(function(response){
                /** 获取分页查询结果 */
                $scope.dataList = response.data.rows;
                /** 更新分页总记录数 */
                $scope.paginationConf.totalItems = response.data.total;
            });
    };

    /** 显示修改 */
    $scope.show = function(entity){
       /** 把json对象转化成一个新的json对象 */
       $scope.entity = JSON.parse(JSON.stringify(entity));
    };

    /** 批量删除 */
    $scope.delete = function(){
        if ($scope.ids.length > 0){
            baseService.deleteById("/goods/delete", $scope.ids)
                .then(function(response){
                    if (response.data){
                        /** 重新加载数据 */
                        $scope.reload();
                    }else{
                        alert("删除失败！");
                    }
                });
        }else{
            alert("请选择要删除的记录！");
        }
    };
});