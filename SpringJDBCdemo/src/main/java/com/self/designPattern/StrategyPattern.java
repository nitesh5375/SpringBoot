package com.self.designPattern;


public class StrategyPattern {

    interface Discount{
        double applyDiscount(double price);
    }

    static class NoDiscount implements Discount{
        @Override
        public double applyDiscount(double price) {
            System.out.println("No discount applied");
            return price;
        }
    }

    static class SeasonalDiscount implements Discount{
        @Override
        public double applyDiscount(double price) {
            return price * 0.9;  //10% off
        }
    }

    static class Cart{

        private Discount discount;

        public Cart(Discount discount){
            this.discount = discount;
        }

        public void setDiscountStrategy(Discount discount){
            this.discount = discount;
        }

        public double checkout(double price){
            double finalPrice = discount.applyDiscount(price);
            return finalPrice;
        }
    }

    class Main{
        public static void main(String[] args) {
            Cart cart = new Cart( new NoDiscount());

            cart.setDiscountStrategy((Discount) new SeasonalDiscount());

            cart.checkout(500);
            

        }
    }
}
