package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

class CafeKioskTest {
    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Beverage latte = new Latte();

        cafeKiosk.add(latte, 1);
        /**
         * 1. 자동화되지 않은 테스트라는 것은, 아래와 같이 콘솔에 직접 찍어서 직접 확인하는 테스트를 의미한다.
         *    이번 학습 기록에서는 직접 콘솔에 찍어 확인하지 않고도 테스트를 자동화하는 것을 돕는 도구인 Junit에 대해서 학습한다.
         */
        //log.debug("음료 수: {}" + cafeKiosk.getBeverages().size());
        //log.debug("최초 주문한 음료: {}" + cafeKiosk.getBeverages().get(0).getName());

        /**
         * 2. 아래처럼 Junit을 이용하면 내가 기대한 값(Expected), 실제 값(Actually)를 비교하여 자동으로 테스트 성공 여부를 체크해준다.
         */
        int expectedSize = 1;
        int actuallySize = cafeKiosk.getBeverages().size();

        String expectedName = "라떼";
        String actuallyName = cafeKiosk.getBeverages().get(0).getName();

        Assertions.assertThat(actuallySize).isEqualTo(expectedSize);
        Assertions.assertThat(actuallyName).isEqualTo(expectedName);
    }

    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Beverage latte = new Latte();

        cafeKiosk.add(latte, 2);

        Assertions.assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(latte);
        Assertions.assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(latte);
    }

    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // 예외 발생
        Assertions.assertThatThrownBy(() -> cafeKiosk.add(americano, 0)).isInstanceOf(IllegalArgumentException.class).hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }

    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Beverage latte = new Latte();

        cafeKiosk.add(latte, 1);
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(latte);
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(0);
    }

    @Test
    void clear(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Beverage latte = new Latte();
        Beverage americano = new Americano();

        cafeKiosk.add(latte, 1);
        cafeKiosk.add(americano, 1);
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(0);
    }


    @Test
    void createOrder(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 1);

        Order order = cafeKiosk.createOreder(LocalDateTime.of(2023, 8, 6, 10, 0));

        Assertions.assertThat(order.getBeverages()).hasSize(1);
        Assertions.assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderOutsideOpenTime(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 1);

        Assertions.assertThatThrownBy(() -> cafeKiosk.createOreder(LocalDateTime.of(2023, 8, 6, 9, 59)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }
}