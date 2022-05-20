package com.myproject.expo.expositions.command.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefinePathForUserTest {

    @Test
    public void definePathForUser(){
       assertEquals("/views/user/user.jsp", DefinePathForUser.definePath("user"));
    }

    @Test
    public void definePathForAdmin(){
        assertEquals("/views/admin/admin.jsp", DefinePathForUser.definePath("admin"));
    }

}