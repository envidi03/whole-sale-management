/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sendEmail;

/**
 *
 * @author 84941
 */
public interface IJavaMail {
    boolean send(String toEmail,String subject,String messageContent);
}
