package com.myproject.expo.expositions.service;

import com.myproject.expo.expositions.exception.ServiceException;

public interface ChangeEmailPassService {
    boolean changeEmail(String oldEmail,String newEmail) throws ServiceException;

    boolean changePass(String email,char[]password) throws ServiceException;
}
