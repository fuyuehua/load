package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.Permission;
import com.rip.load.pojo.RolePermission;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.PermissionService;
import com.rip.load.service.RolePermissionService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
@Api(tags = {"角色与权限绑定控制器"})
@RestController
@RequestMapping("api/rolePermission")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "新增角色与权限绑定")
    @PostMapping("/add")
    public Result<Object> add(
            @ApiParam(value = "角色与权限实体类")
            @RequestBody RolePermission rolePermission){

        if(rolePermission.getPid() == null || rolePermission.getPid() == 0 || rolePermission.getRid() == null || rolePermission.getRid() == 0)
            return new ResultUtil<Object>().setErrorMsg("参数不足，除了ID都要传");
        rolePermission.setId(null);
        RolePermission rolePermissionInDB = rolePermissionService.selectOne(new EntityWrapper<RolePermission>().eq("pid", rolePermission.getPid()).eq("rid", rolePermission.getRid()));
        if(rolePermissionInDB != null){
            return new ResultUtil<Object>().setErrorMsg("数据库已有此绑定");
        }
        boolean b = rolePermissionService.insert(rolePermission);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("储存错误");
        }
    }

    @ApiOperation("删除角色与权限的绑定")
    @GetMapping("/delete")
    public Result<Object> delete(
            @ApiParam(value = "角色与权限的绑定ID")
            @RequestParam int id
    ){
        boolean b = rolePermissionService.deleteById(id);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("将角色已经绑定的权限修改成另一个权限")
    @PostMapping("/update")
    public Result<Object> update(
            @ApiParam(value = "角色与权限类")
            @RequestBody RolePermission rolePermission
    ){
        if(rolePermission.getId() == null || rolePermission.getId() == 0 || rolePermission.getPid() == null || rolePermission.getPid() == 0)
            return new ResultUtil<Object>().setErrorMsg("参数不可为空");
        Permission permission =permissionService.selectById(rolePermission.getPid());
        if(permission == null){
            return new ResultUtil<Object>().setErrorMsg("这个权限不存在");
        }
        rolePermission.setRid(null);
        boolean b = rolePermissionService.updateById(rolePermission);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }
}