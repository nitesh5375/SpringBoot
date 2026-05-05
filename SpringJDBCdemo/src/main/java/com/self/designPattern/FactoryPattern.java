package com.self.designPattern;

public class FactoryPattern {

    interface Notification{
        void send();
    }

    static class EmailNotification implements Notification{
        @Override
        public void send(){
            System.out.println("Email Notification");
        }
    }

    static class SmsNotification implements Notification{
        @Override
        public void send(){
            System.out.println("Sms Notification");
        }
    }

    static class NotificationFactory{

        public static Notification createNotification(String notificationType){
            if(notificationType.equalsIgnoreCase("Email")){
                return new EmailNotification();
            }
            else
                return new SmsNotification();
        }
    }

    static class Main{
        public static void main(String[] args){
            Notification notification = NotificationFactory.createNotification("Email");
            notification.send();
        }
    }
}
