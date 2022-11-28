package com.tz.eduservice.controller;

import com.tz.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user/")
@CrossOrigin
public class EduLoginController {
    @ApiOperation("User Login,return token")
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @ApiOperation("Get userInfo by Token")
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","admin")
                .data("name","tiger")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
