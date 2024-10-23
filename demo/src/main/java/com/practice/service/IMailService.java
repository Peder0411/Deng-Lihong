package com.practice.service;

import org.springframework.stereotype.Service;


@Service
public interface IMailService {


    public boolean sendMail(String email) ;
    public  String getTemporaryStorage();
}
