package sample.cafekiosk.unit;

import lombok.extern.slf4j.Slf4j;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;

@Slf4j
public class CafeKioskRunner {
    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();

        //cafeKiosk.add(new Americano());
        //log.debug("아메리카노 주문");

        //cafeKiosk.add(new Latte());
        //log.debug("라떼 주문");

        //int totalPrice = cafeKiosk.calculateTotalPrice();
        //log.debug("최종 가격: {}", totalPrice);
    }

}
