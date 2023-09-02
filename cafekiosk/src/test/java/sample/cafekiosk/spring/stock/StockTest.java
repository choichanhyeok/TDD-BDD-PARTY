package sample.cafekiosk.spring.stock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StockTest {

    @DisplayName("현재 재고의 수량이 확인 요청받은 재고의 수량보다 적은지 확인한다.")
    @Test
    void test1(){
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;


        // when
        boolean result = stock.isQuantityLessThan(quantity);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("현재 재고에 대해 삭제 요청받은 개수 만큼 차감할 수 있다.")
    @Test
    void test2(){
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("현재 재고보다 많은 수량의 차감 시도할 경우 예외가 발생한다.")
    @Test
    void test3(){
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when // then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");

    }
}