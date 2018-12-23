package com.lottery.controller;

import com.github.pagehelper.PageHelper;
import com.lottery.common.ResponseModel;
import com.lottery.model.Product;
import com.lottery.model.Unit;
import com.lottery.service.OperatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/operator")
@Api("运营商模块")
public class OperatorController {
    @Autowired
    OperatorService operatorService;


    @ApiOperation(value = "获取活动列表", notes = "获取活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pagenum", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pagesize", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query")
    }
    )
    @RequiresRoles("1")
    @RequestMapping(value = "productlist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getProductList(
            @RequestParam(value = "pagenum", required = false,defaultValue = "1") Integer pagenum,
            @RequestParam(value = "pagesize", required = false,defaultValue = "10") Integer pagesize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "isvalid", required = false) Integer isvalid,
            @RequestParam(value = "id", required = false) Integer id
    ) {
        PageHelper.startPage(pagenum, pagesize);
        List<Product> productList = operatorService.getProductListbyCondition(id, name, isvalid);
        return new ResponseModel(productList);
    }

    @ApiOperation(value = "获取活动商品详情", notes = "获取活动商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query"),
    }
    )
    @RequiresRoles("1")
    @RequestMapping(value = "productdetails", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getProductDetails(@RequestParam(value = "id") Integer id){
        HashMap<String,Object> result=operatorService.getProductDetails(id);
        return new ResponseModel(result);
    }


    @ApiOperation(value = "删除活动商品(会同时删除商品对应的规格)", notes = "删除活动商品(会同时删除商品对应的规格)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query"),
    }
    )
    @RequiresRoles("1")
    @RequestMapping(value = "productremove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel removeProduct(@RequestParam(value = "id") Integer id){
        operatorService.removeProduct(id);
        return new ResponseModel(0L,"成功删除",null);

    }


    @ApiOperation(value = "增加活动商品", notes = "增加活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "icon", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "description", dataType = "String", paramType = "query"),
    }
    )
    @RequiresRoles("1")
    @RequestMapping(value = "productadd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel addProduct(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "isvalid",required = false,defaultValue = "1") Integer isvalid,
            @RequestParam(value = "icon") String icon,
            @RequestParam(value = "description",required = false) String description
            ){
        HashMap<String,Integer> result= operatorService.addProduct(name,icon,description,isvalid);
        if(result.get("count")>0)
            return new ResponseModel(0L,"新增成功",result.get("productid"));
        else
            return new ResponseModel(500L,"新增失败",null);
    }


    @ApiOperation(value = "修改活动商品", notes = "修改活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "icon", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "description", dataType = "String", paramType = "query"),
    }
    )
    @RequiresRoles("1")
    @RequestMapping(value = "productupdate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel updateProduct(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "isvalid",required = false,defaultValue = "1") Integer isvalid,
            @RequestParam(value = "icon",required = false) String icon,
            @RequestParam(value = "description",required = false) String description
    ){
        Integer count = operatorService.updateProduct(id,name,icon,description,isvalid);
        return  new ResponseModel(0L,"更新成功"+count+"条数据",null);
    }


    @ApiOperation(value = "获取规格列表", notes = "获取规格列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "productid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query"),
    }
    )
    @RequiresRoles("1")
    @RequestMapping(value = "unitlist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getUnitList(
            @RequestParam(value = "id",required = false) Integer id,
            @RequestParam(value = "productid",required = false) Integer productid,
            @RequestParam(value = "isvalid",required = false,defaultValue = "1") Integer isvalid,
            @RequestParam(value = "name",required = false) String name

    ){
        List<Unit> unitList= operatorService.getUnitList(id,productid,isvalid,name);
        return new ResponseModel(unitList);
    }


    @ApiOperation(value = "删除规格", notes = "删除规格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query"),

    })
    @RequiresRoles("1")
    @RequestMapping(value = "unitremove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel removeUnit(@RequestParam(value = "id",required = false) Integer id){
        Integer count=operatorService.removeUnit(id);
        if (count>0)
            return new ResponseModel(0L,"删除成功",null);
        else
            return new ResponseModel(500L,"删除失败",null);
    }


    @ApiOperation(value = "添加规格", notes = "添加规格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "expired", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "price", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query"),

    })
    @RequiresRoles("1")
    @RequestMapping(value = "unitadd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel addUnit(
            @RequestParam(value = "productid") Integer productid,
            @RequestParam(value = "isvalid",required = false,defaultValue = "1") Integer isvalid,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "price") Integer price,
            @RequestParam(value = "expired") Integer expired
    ){
        HashMap<String,Integer> result=operatorService.addUnit(productid,isvalid,price,name,expired);
        if(result.get("count")>0)
            return new ResponseModel(0L,"新增成功",result.get("unitid"));
        else
            return new ResponseModel(500L,"新增失败",null);


    }





}
