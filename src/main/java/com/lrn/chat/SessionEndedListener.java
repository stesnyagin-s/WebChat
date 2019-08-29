package com.lrn.chat;

import com.lrn.chat.service.Messages;
import com.lrn.chat.service.UsersOnline;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent> {
    private final Messages msg;
    private final UsersOnline users;

    public SessionEndedListener(Messages msg, UsersOnline users) {
        this.msg = msg;
        this.users = users;
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        for (SecurityContext securityContext : event.getSecurityContexts()) {
            Authentication authentication = securityContext.getAuthentication();
            UserDetails user = (UserDetails) authentication.getPrincipal();
            users.getUsers().remove("</a> <a class=\"text-info\">" + user.getUsername() + "</a>",
                    "</a> <a class=\"text-info\">" + user.getUsername() + "</a>");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            msg.getMessages().add("<a class=\"text-secondary\">" + dtf.format(now) + "</a> <a class=\"text-info\">" + user.getUsername() + "</a> has left the chat");
        }
    }

}

