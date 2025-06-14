package com.rs.money.management.api.services.impl;

import com.rs.money.management.api.entities.PasswordResetOtp;
import com.rs.money.management.api.entities.User;
import com.rs.money.management.api.repository.PasswordResetOtpRepo;
import com.rs.money.management.api.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetOtpService {
    private final PasswordEncoder passwordEncoder;
    private final  PasswordResetOtpRepo passwordResetOtpRepo;
    private final UserRepo userRepo;

    public   void setPasswordResetOtp(String email){
            User user = userRepo.findByEmail(email).orElseThrow();
            PasswordResetOtp passwordResetOtp = new PasswordResetOtp();
            passwordResetOtp.setPin(passwordEncoder.encode(getRandomNumberString()));
            passwordResetOtp.setExpiryDate(calculateExpiryDate(1));
            passwordResetOtp.setUser(user);
            passwordResetOtpRepo.save(passwordResetOtp);
        }

    private Date calculateExpiryDate(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
    private  String getRandomNumberString() {
    Random rnd = new Random();
    int number = rnd.nextInt(999999);
    // this will convert any number sequence into 6 character.
    return String.format("%06d", number);
}
}
