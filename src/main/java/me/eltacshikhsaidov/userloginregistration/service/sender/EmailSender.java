package me.eltacshikhsaidov.userloginregistration.service.sender;

public interface EmailSender {
    void send(String to, String email);
}