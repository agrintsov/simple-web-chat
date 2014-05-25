package com.sagr.simple.chat.controllers;

import com.sagr.common.IResult;
import com.sagr.common.Result;
import com.sagr.common.ResultCode;
import com.sagr.simple.chat.message.common.IMessage;
import com.sagr.simple.chat.message.service.MessageService;
import com.sagr.user.common.IUser;
import com.sagr.user.common.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sasha on 25.05.14.
 */
@Controller
public class RestController extends ABasicController {
    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "messageService")
    private MessageService messageService;

    @RequestMapping(value = "/saveMessage", method = RequestMethod.POST)
    @ResponseBody
    IResult<Boolean> saveMessage(@RequestParam(defaultValue = "")String message) {
        String personName = getLoggedInPersonName();
        if (personName == null) {
            return new Result<Boolean>(ResultCode.NOT_SIGNED_IN);
        }
        if (!userService.userExists(personName)) {
            return new Result<Boolean>(ResultCode.USER_NOT_FOUND);
        }
        return messageService.saveMessage(message, personName);
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    @ResponseBody
    IResult<List<IMessage>> getMessages(@RequestParam(defaultValue = "100")int limit) {
        String personName = getLoggedInPersonName();
        if (personName == null) {
            return new Result<List<IMessage>>(ResultCode.NOT_SIGNED_IN);
        }
        return messageService.getLastMessages(limit);
    }


    @RequestMapping(value = "/getOnlineUsers", method = RequestMethod.GET)
    @ResponseBody IResult<List<IUser>> getOnlineUsers() {
        String personName = getLoggedInPersonName();
        if (personName == null) {
            return new Result<List<IUser>>(ResultCode.NOT_SIGNED_IN);
        }
        IResult<List<IUser>> allUsers = userService.getAllUsers();
        return allUsers;
    }

}
