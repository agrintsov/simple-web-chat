package com.sagr.simple.chat.controllers;

import com.sagr.common.IResult;
import com.sagr.common.Result;
import com.sagr.common.ResultCode;
import com.sagr.simple.chat.Configuration;
import com.sagr.simple.chat.message.common.IMessage;
import com.sagr.simple.chat.message.service.MessageService;
import com.sagr.simple.chat.wrappers.MessageWrapper;
import com.sagr.simple.chat.wrappers.UserWrapper;
import com.sagr.user.common.IUser;
import com.sagr.user.common.IUserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 25.05.14.
 */
@Controller
public class RestController extends ABasicController {
    final Logger logger = LoggerFactory.getLogger(RestController.class);

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
        logger.info("User {} sent message: {}", personName, message);
        return messageService.saveMessage(message, personName);
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    @ResponseBody
    IResult<List<MessageWrapper>> getMessages(@RequestParam(defaultValue = "")String limit) {
        String personName = getLoggedInPersonName();
        if (personName == null) {
            return new Result<List<MessageWrapper>>(ResultCode.NOT_SIGNED_IN);
        }
        int lim = Configuration.MESSAGE_LIMIT;
        if (!limit.isEmpty()) {
            lim = Integer.parseInt(limit);
        }
        return convertToMassageWrappers(messageService.getLastMessages(lim), personName);
    }

    @RequestMapping(value = "/getMessagesAfterThis", method = RequestMethod.GET)
    @ResponseBody
    IResult<List<MessageWrapper>> getMessagesAfterThis(@RequestParam(defaultValue = "")String messageId, @RequestParam(defaultValue = "")String limit) {
        String personName = getLoggedInPersonName();
        if (personName == null) {
            return new Result<List<MessageWrapper>>(ResultCode.NOT_SIGNED_IN);
        }
        if (!ObjectId.isValid(messageId)) {
            return new Result<List<MessageWrapper>>(ResultCode.FAIL);
        }
        int lim = Configuration.MESSAGE_LIMIT;
        if (!limit.isEmpty()) {
            lim = Integer.parseInt(limit);
        }
        return convertToMassageWrappers(messageService.getMessagesAfterThis(new ObjectId(messageId), lim), personName);
    }


    @RequestMapping(value = "/getOnlineUsers", method = RequestMethod.GET)
    @ResponseBody IResult<List<UserWrapper>> getOnlineUsers() {
        String personName = getLoggedInPersonName();
        if (personName == null) {
            return new Result<List<UserWrapper>>(ResultCode.NOT_SIGNED_IN);
        }
        IResult<List<IUser>> allUsers = userService.getAllUsers();
        return convertToUserWrappers(allUsers, personName);
    }

    private IResult<List<MessageWrapper>> convertToMassageWrappers(IResult<List<IMessage>> result, String currentUser) {
        if (result.hasError()) {
            return new Result<List<MessageWrapper>>(result.getResultCode());
        }
        List<IMessage> messages = result.getResultObject();
        List<MessageWrapper> messageWrappers = new ArrayList<MessageWrapper>();
        for (IMessage message : messages) {
            messageWrappers.add(new MessageWrapper(message, message.getAuthorName().equals(currentUser)));
        }
        return new Result<List<MessageWrapper>>(messageWrappers);
    }

    private IResult<List<UserWrapper>> convertToUserWrappers(IResult<List<IUser>> result, String currentUser) {
        if (result.hasError()) {
            return new Result<List<UserWrapper>>(result.getResultCode());
        }
        List<IUser> users = result.getResultObject();
        List<UserWrapper> userWrappers = new ArrayList<UserWrapper>();
        for (IUser user : users) {
            userWrappers.add(new UserWrapper(user, user.getName().equals(currentUser)));
        }
        return new Result<List<UserWrapper>>(userWrappers);
    }

}
