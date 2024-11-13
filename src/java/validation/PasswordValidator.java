/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 84941
 */
public class PasswordValidator {
    // Hàm kiểm tra mật khẩu với regex

    public static boolean validatePassword(String password) {
        // Biểu thức chính quy kiểm tra mật khẩu
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

        // Tạo đối tượng Pattern từ biểu thức chính quy
        Pattern pattern = Pattern.compile(regex);

        // Kiểm tra mật khẩu khớp với biểu thức chính quy
        Matcher matcher = pattern.matcher(password);

        // Trả về true nếu mật khẩu hợp lệ, false nếu không hợp lệ
        return matcher.matches();
    }
}
