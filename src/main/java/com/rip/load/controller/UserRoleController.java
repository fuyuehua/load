package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.Role;
import com.rip.load.pojo.UserRole;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.RoleService;
import com.rip.load.service.UserRoleService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = {"用户与角色绑定控制器"})
@RestController
@RequestMapping("api/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "新增用户与角色绑定")
    @PostMapping("/add")
    public Result<Object> add(
            @ApiParam(value = "用户与角色实体类")
            @RequestBody List<UserRole> list){
        List<UserRole> listInDB = userRoleService.selectList(new EntityWrapper<UserRole>().eq("rid", list.get(0).getRid()));
        for(UserRole userRole : list) {
            if (userRole.getUid() == null || userRole.getUid() == 0 || userRole.getRid() == null || userRole.getRid() == 0) {
                return new ResultUtil<Object>().setErrorMsg("参数不足，除了ID都要传");
            }
            userRole.setId(null);
            for(UserRole urInDB : listInDB){
                if(userRole.getUid().equals(urInDB.getUid())){
                    return new ResultUtil<Object>().setErrorMsg("数据库中已经存在此绑定");
                }
            }
        }


        boolean b = userRoleService.insertBatch(list);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("储存错误");
        }
    }

    @ApiOperation("删除用户与角色的绑定")
    @GetMapping("/delete")
    public Result<Object> delete(
            @ApiParam(value = "用户ID")
            @RequestParam int uid,
            @ApiParam(value = "角色ID")
            @RequestParam int rid
    ){
        if(uid == 0 || rid == 0){
            return new ResultUtil<Object>().setErrorMsg("不能传O");
        }
        boolean b = userRoleService.delete(new EntityWrapper<UserRole>().eq("uid", uid).eq("rid", rid));
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("将用户已经绑定的角色修改成另一个角色")
    @PostMapping("/update")
    public Result<Object> update(
            @ApiParam(value = "用户与角色实体类")
            @RequestBody UserRole userRole
    ){
        if(userRole.getId() == null || userRole.getId() == 0 || userRole.getRid() == null || userRole.getRid() == 0)
            return new ResultUtil<Object>().setErrorMsg("参数不可为空");
        Role role = roleService.selectById(userRole.getRid());
        if(role == null){
            return new ResultUtil<Object>().setErrorMsg("这个角色不存在");
        }
        userRole.setUid(null);
        boolean b = userRoleService.updateById(userRole);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }


}

