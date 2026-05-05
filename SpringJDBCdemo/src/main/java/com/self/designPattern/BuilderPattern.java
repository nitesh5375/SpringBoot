package com.self.designPattern;

public class BuilderPattern {

    static class User{
        private String name;
        private int age;
        private String address;

        public User(UserBuilder builder ){
            this.name = builder.name;
            this.age = builder.age;
            this.address = builder.address;
        }

        public static class UserBuilder{
            private String name;
            private int age;
            private String address;

            public UserBuilder setName(String name){
                this.name = name;
                return this;
            }

            public UserBuilder setAge(int age){
                this.age = age;
                return this;
            }

            public UserBuilder setAddress(String address){
                this.address = address;
                return this;
            }

            public User build(){
                return new User(this);
            }
        }
    }
class MainBuilder{
    public static void main(String[] args) {
        BuilderPattern.User user = new BuilderPattern.User.UserBuilder().setName("Nitesh").build();
    }
}

}
