package com.sagr.simple.chat.controllers;

import com.sagr.common.IResult;
import com.sagr.simple.chat.message.common.IMessage;
import com.sagr.simple.chat.message.service.MessageService;
import com.sagr.user.common.IUser;
import com.sagr.user.common.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sasha on 21.05.14.
 */
@Controller
public class MainController extends ABasicController {

    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "messageService")
    private MessageService messageService;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, @RequestParam(defaultValue = "false")boolean login,
                               @RequestParam(defaultValue = "false")boolean alreadyRegistered) {

        model.addAttribute("login", login);
        model.addAttribute("alreadyRegistered", alreadyRegistered);

        return "welcome";

    }

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String chat(ModelMap model) {
        IResult<List<IUser>> allUsers = userService.getAllUsers();
        if (!allUsers.hasError()) {
            model.addAttribute("users", allUsers.getResultObject());
        }
        IResult<List<IMessage>> lastMessages = messageService.getLastMessages(100);
        if (!lastMessages.hasError()) {
            model.addAttribute("messages", lastMessages.getResultObject());
        }
        System.out.println(allUsers);
        return "chat";

    }

}