package com.entor.system.controller;

import com.entor.bus.entity.BusLine;
import com.entor.bus.entity.BusStation;
import com.entor.bus.service.IBusLineService;
import com.entor.bus.service.IBusStationService;
import com.entor.common.authentication.ShiroHelper;
import com.entor.common.controller.BaseController;
import com.entor.common.entity.FebsConstant;
import com.entor.common.utils.DateUtil;
import com.entor.common.utils.FebsUtil;
import com.entor.system.entity.User;
import com.entor.system.service.IUserDataPermissionService;
import com.entor.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller("systemView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IUserService userService;
    private final ShiroHelper shiroHelper;
    private final IUserDataPermissionService userDataPermissionService;
    private final IBusStationService busStationService;
    private final IBusLineService busLineService;

    @GetMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request) {
        if (FebsUtil.isAjaxRequest(request)) {
            throw new ExpiredSessionException();
        } else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName(FebsUtil.view("login"));
            return mav;
        }
    }

    @GetMapping("unauthorized")
    public String unauthorized() {
        return FebsUtil.view("error/403");
    }


    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String index(Model model) {
        AuthorizationInfo authorizationInfo = shiroHelper.getCurrentUserAuthorizationInfo();
        User user = super.getCurrentUser();
        User currentUserDetail = userService.findByName(user.getUsername());
        currentUserDetail.setPassword("It's a secret");
        model.addAttribute("user", currentUserDetail);
        model.addAttribute("permissions", authorizationInfo.getStringPermissions());
        model.addAttribute("roles", authorizationInfo.getRoles());
        return "index";
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "layout")
    public String layout() {
        return FebsUtil.view("layout");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "password/update")
    public String passwordUpdate() {
        return FebsUtil.view("system/user/passwordUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/profile")
    public String userProfile() {
        return FebsUtil.view("system/user/userProfile");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/avatar")
    public String userAvatar() {
        return FebsUtil.view("system/user/avatar");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/profile/update")
    public String profileUpdate() {
        return FebsUtil.view("system/user/profileUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user")
    @RequiresPermissions("user:view")
    public String systemUser() {
        return FebsUtil.view("system/user/user");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/add")
    @RequiresPermissions("user:add")
    public String systemUserAdd() {
        return FebsUtil.view("system/user/userAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/detail/{username}")
    @RequiresPermissions("user:view")
    public String systemUserDetail(@PathVariable String username, Model model) {
        resolveUserModel(username, model, true);
        return FebsUtil.view("system/user/userDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/update/{username}")
    @RequiresPermissions("user:update")
    public String systemUserUpdate(@PathVariable String username, Model model) {
        resolveUserModel(username, model, false);
        return FebsUtil.view("system/user/userUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/role")
    @RequiresPermissions("role:view")
    public String systemRole() {
        return FebsUtil.view("system/role/role");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/menu")
    @RequiresPermissions("menu:view")
    public String systemMenu() {
        return FebsUtil.view("system/menu/menu");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/dept")
    @RequiresPermissions("dept:view")
    public String systemDept() {
        return FebsUtil.view("system/dept/dept");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "busLine/list")
    @RequiresPermissions("busLine:view")
    public String busLine() {
        return FebsUtil.view("bus/line/line");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "busLine/map")
    @RequiresPermissions("busLine:view")
    public String busLineMap() {
        return FebsUtil.view("bus/line/lineMap");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "bus/busLine/add")
    @RequiresPermissions("busLine:add")
    public String busLineAdd() {
        return FebsUtil.view("bus/line/lineAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "bus/busLine/update/{name}")
    @RequiresPermissions("busLine:update")
    public String busLineUpdate(@PathVariable String name, Model model) {
        resolveUserModel3(name, model, false);
        return FebsUtil.view("bus/line/lineUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "busStation/list")
    @RequiresPermissions("busStation:view")
    public String busStation() {
        return FebsUtil.view("bus/station/station");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "bus/busStation/add")
    @RequiresPermissions("busStation:add")
    public String busStationAdd() {
        return FebsUtil.view("bus/station/stationAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "bus/busStation/update/{name}")
    @RequiresPermissions("busStation:update")
    public String busStationUpdate(@PathVariable String name, Model model) {
        resolveUserModel2(name, model, false);
        return FebsUtil.view("bus/station/stationUpdate");
    }

    @RequestMapping(FebsConstant.VIEW_PREFIX + "index")
    public String pageIndex() {
        return FebsUtil.view("index");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "404")
    public String error404() {
        return FebsUtil.view("error/404");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "403")
    public String error403() {
        return FebsUtil.view("error/403");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "500")
    public String error500() {
        return FebsUtil.view("error/500");
    }

    private void resolveUserModel(String username, Model model, Boolean transform) {
        User user = userService.findByName(username);
        String deptIds = userDataPermissionService.findByUserId(String.valueOf(user.getUserId()));
        user.setDeptIds(deptIds);
        model.addAttribute("user", user);
        if (transform) {
            String sex = user.getSex();
            if (User.SEX_MALE.equals(sex)) {
                user.setSex("男");
            } else if (User.SEX_FEMALE.equals(sex)) {
                user.setSex("女");
            } else {
                user.setSex("保密");
            }
        }
        if (user.getLastLoginTime() != null) {
            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        }
    }
    private void resolveUserModel2(String name, Model model, Boolean transform) {
        BusStation busStation = busStationService.findByName(name);
        model.addAttribute("busStation", busStation);
    }
    private void resolveUserModel3(String name, Model model, Boolean transform) {
        BusLine busLine = busLineService.findByName(name);
        model.addAttribute("busLine", busLine);
    }

}
