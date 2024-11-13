/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.security.SecureRandom;

/**
 *
 * @author 84941
 */
public class OTPGenerator {

    public static String generateOTP() {
        // Create a SecureRandom subject 
        SecureRandom random = new SecureRandom();

        // Create a 6-digit OTP
        int otp = 100000 + random.nextInt(900000);

        // Tranfer number into string
        return String.valueOf(otp);
    }
}
