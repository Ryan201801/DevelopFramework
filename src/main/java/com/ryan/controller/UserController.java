package com.ryan.controller;

import com.ryan.application.UserProcess;
import com.ryan.util.CommonResponse;
import com.ryan.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;

/**
 * @Description: 用户Controller，包括：member、seller、member and seller、admin
 * @author Ryan
 * @date 2018/1/30 14:49
 */
@Component
@Path("/v1/users")
@Api(value = "User management", produces = "application/json", tags = "User")
public class UserController {
    @Autowired(required=true)
    private UserProcess userProcess;

    /**
     * @Description: 普通更新用户信息
     * @author Ryan
     * @date 2018/1/29 16:15
     */
    @PUT
    @Path("/{userId}")
    @Consumes("application/json")
    @ApiOperation(value = "updateUser:普通更新用户信息（Ryan）", notes = "updateUser:普通更新用户信息（Ryan）")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = User.class),
            @ApiResponse(code = 403, message = "unauthorized", response = CommonResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = CommonResponse.class)
    })
    public CommonResponse<User> updateUser(@PathParam("userId") int userId,@NotNull User user) {
        return new CommonResponse<>(userProcess.updateUser(user));
    }
    /**
     * @Description: 删除用户
     * @author Ryan
     * @date 2018/1/30 11:10
     */
    @DELETE
    @Path("/{userid}")
    @Consumes("application/json")
    @ApiOperation(value = "delete a user.", notes = "delete a user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = Boolean.class),
            @ApiResponse(code = 403, message = "unauthorized", response = CommonResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = CommonResponse.class)
    })
    public CommonResponse<Boolean> deleteUser(@PathParam("userid") int userid) {
        return new CommonResponse<>(userProcess.deleteUser(userid));
    }
}
