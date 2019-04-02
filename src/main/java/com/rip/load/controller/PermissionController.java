package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.Permission;
import com.rip.load.pojo.RolePermission;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.PermissionService;
import com.rip.load.service.RolePermissionService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
@Api(value = "权限控制器", tags = {"权限控制"})
@RestController
@RequestMapping("api/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @ApiOperation(value = "新增一个权限")
    @PostMapping("/add")
    public Result<Object> add(
            @ApiParam(value = "权限实体类")
            @RequestBody Permission permission){

        if(StringUtils.isEmpty(permission.getName()) || StringUtils.isEmpty(permission.getUrl()) || StringUtils.isEmpty(permission.getDesc()))
            return new ResultUtil<Object>().setErrorMsg("参数不足，除了ID都要传");
        permission.setId(null);
        boolean b = permissionService.insert(permission);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("储存错误");
        }
    }

    @ApiOperation("删除一个权限")
    @GetMapping("/delete")
    public Result<Object> delete(
            @ApiParam(value = "权限ID")
            @RequestParam int id
    ){

        boolean rid2 = rolePermissionService.delete(new EntityWrapper<RolePermission>().eq("pid", id));
        boolean b = permissionService.deleteById(id);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("修改一个权限")
    @PostMapping("/update")
    public Result<Object> update(
            @ApiParam(value = "权限实体类")
            @RequestBody Permission permission
    ){
        if(StringUtils.isEmpty(permission.getName()) || StringUtils.isEmpty(permission.getUrl()) || StringUtils.isEmpty(permission.getDesc())
                || permission.getId() == null || permission.getId() == 0)
            return new ResultUtil<Object>().setErrorMsg("参数不可为空");

        boolean b = permissionService.updateById(permission);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("查询所有权限（分页）")
    @GetMapping("/getAllInPage")
    public Result<Page<Permission>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize
    ){
        Page<Permission> page = new Page<>(currentPage, pageSize);
        Page<Permission> result = permissionService.selectPage(page);
        return new ResultUtil<Page<Permission>>().setData(result);
    }

    @ApiOperation("查询单个权限")
    @GetMapping("/get")
    public Result<Permission> get(
            @ApiParam(value = "权限ID")
            @RequestParam int id
    ){
        Permission permission = permissionService.selectById(id);
        return new ResultUtil<Permission>().setData(permission);
    }

    @ApiOperation("通过角色ID查出所有权限")
    @GetMapping("/getByRoleId")
    public Result<List<Permission>> getByRoleId(
            @ApiParam(value = "角色ID")
            @RequestParam int id
    ){
        List<Permission> userPermission = permissionService.getByRoleId(id);
        return new ResultUtil<List<Permission>>().setData(userPermission);
    }

    @ApiOperation("通过用户ID查出所有权限")
    @GetMapping("/getByUserId")
    public Result<List<Permission>> getByUserId(
            @ApiParam(value = "用户ID")
            @RequestParam int id
    ){
        List<Permission> userPermission = permissionService.getUserPermission(id);
        return new ResultUtil<List<Permission>>().setData(userPermission);
    }

}
