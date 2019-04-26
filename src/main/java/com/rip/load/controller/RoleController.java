package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.Role;
import com.rip.load.pojo.RolePermission;
import com.rip.load.pojo.User;
import com.rip.load.pojo.UserRole;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.RolePermissionService;
import com.rip.load.service.RoleService;
import com.rip.load.service.UserRoleService;
import com.rip.load.service.UserService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
@Api(value = "角色控制器", tags = {"角色控制"})
@RestController
@RequestMapping("api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;


    @ApiOperation(value = "新增一个角色")
    @PostMapping("/add")
    public Result<Object> add(
            @ApiParam(value = "角色实体类")
            @RequestBody Role role) {

        if (StringUtils.isEmpty(role.getName()) || StringUtils.isEmpty(role.getDesc()))
            return new ResultUtil<Object>().setErrorMsg("参数不足，除了ID都要传");
        role.setId(null);
        boolean b = roleService.insert(role);
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("储存错误");
        }
    }

    @ApiOperation("删除一个角色")
    @GetMapping("/delete")
    public Result<Object> delete(
            @ApiParam(value = "角色ID")
            @RequestParam int id
    ) {
        boolean rid = userRoleService.delete(new EntityWrapper<UserRole>().eq("rid", id));
        boolean rid2 = rolePermissionService.delete(new EntityWrapper<RolePermission>().eq("rid", id));
        boolean b = roleService.deleteById(id);
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("修改一个角色")
    @PostMapping("/update")
    public Result<Object> update(
            @ApiParam(value = "角色实体类")
            @RequestBody Role role
    ) {
        if (StringUtils.isEmpty(role.getName()) || StringUtils.isEmpty(role.getDesc())
                || role.getId() == null || role.getId() == 0)
            return new ResultUtil<Object>().setErrorMsg("参数不可为空");

        boolean b = roleService.updateById(role);
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("查询所有角色（分页）")
    @GetMapping("/getAllInPage")
    public Result<Page<Role>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize
    ) {
        Page<Role> page = new Page<>(currentPage, pageSize);
        Page<Role> result = roleService.selectPage(page);
        return new ResultUtil<Page<Role>>().setData(result);
    }

    @ApiOperation("查询单个角色")
    @GetMapping("/get")
    public Result<Role> get(
            @ApiParam(value = "角色ID")
            @RequestParam int id
    ) {
        Role role = roleService.selectById(id);
        return new ResultUtil<Role>().setData(role);
    }

    @ApiOperation("查询用户绑定的角色")
    @GetMapping("/getRoleByUserId")
    public Result<List<Role>> getRoleByUser(
            @ApiParam(value = "用户ID")
            @RequestParam int id
    ) {
        List<Role> role = roleService.getUserRole(id);
        return new ResultUtil<List<Role>>().setData(role);
    }

    @ApiOperation("通过角色拿到绑定它的用户")
    @GetMapping("/getAllInPageByrid")
    public Result<Page<User>> getAllInPageByrid(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "角色ID")
            @RequestParam int rid){
        if(rid == 0){
            return new ResultUtil<Page<User>>().setErrorMsg("该用户不存在");
        }
        List<UserRole> list = userRoleService.selectList(new EntityWrapper<UserRole>().eq("rid", rid));
        List<Integer> list1 = new ArrayList<>();
        for(UserRole ur : list){
            list1.add(ur.getUid());
        }
        Page<User> page = new Page<>(currentPage, pageSize);
        if(list1.size() == 0){
            return new ResultUtil<Page<User>>().setData(page);
        }
        Page<User> userPage = userService.selectPage(page, new EntityWrapper<User>().in("id",list1).eq("onoff", 1));
        return new ResultUtil<Page<User>>().setData(userPage);
    }

    @ApiOperation("通过角色拿到未绑定它的用户")
    @GetMapping("/getNOInPageByrid")
    public Result<Page<User>> getNOInPageByrid(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "角色ID")
            @RequestParam int rid){
        if(rid == 0){
            return new ResultUtil<Page<User>>().setErrorMsg("该用户不存在");
        }
        List<UserRole> list = userRoleService.selectList(new EntityWrapper<UserRole>().eq("rid", rid));
        List<Integer> list1 = new ArrayList<>();
        for(UserRole ur : list){
            list1.add(ur.getUid());
        }
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.selectPage(page, new EntityWrapper<User>().notIn("id",list1).eq("onoff", 1));
        return new ResultUtil<Page<User>>().setData(userPage);
    }

    @ApiOperation("通过角色拿到未绑定它的用户然后进行模糊查询")
    @GetMapping("/getNOByridAndFuzzyQuery")
    public Result<Page<User>> getNOByridAndFuzzyQuery(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "模糊公司名称")
            @RequestParam String nickname,
            @ApiParam(value = "模糊手机号")
            @RequestParam String phone,
            @ApiParam(value = "角色ID")
            @RequestParam int rid){
        if(rid == 0){
            return new ResultUtil<Page<User>>().setErrorMsg("该角色不存在");
        }
        List<UserRole> list = userRoleService.selectList(new EntityWrapper<UserRole>().eq("rid", rid));
        List<Integer> list1 = new ArrayList<>();
        for(UserRole ur : list){
            list1.add(ur.getUid());
        }
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.selectPage(page, new EntityWrapper<User>().notIn("id",list1)
                .eq("onoff", 1)
                .like("nickname", nickname)
                .like("phone", phone));
        return new ResultUtil<Page<User>>().setData(userPage);
    }
}
