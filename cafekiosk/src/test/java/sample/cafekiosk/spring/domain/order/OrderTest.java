package sample.cafekiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static sample.cafekiosk.spring.domain.product.SellingStatus.SELLING;

class OrderTest {

    @DisplayName("주문 생성시에 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void test1(){
        // given
        List<Product> products =  List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());


        // then
        Assertions.assertThat(order.getTotalPrice()).isEqualTo(3000);

    }

    @DisplayName("주문 생성시에 주문의 상태는 INIT이다.")
    @Test
    void test2(){
        // given
        List<Product> products =  List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        Assertions.assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성시에 등록 시간을 기록한다.")
    @Test
    void test3(){
        // given
        LocalDateTime registedDateTime = LocalDateTime.now();
        List<Product> products =  List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products, registedDateTime);

        // then
        Assertions.assertThat(order.getRegistedDateTime()).isEqualTo(registedDateTime);
    }


    private Product createProduct(String productNumber, int price){
        return  Product.builder()
                .productNumber(productNumber)
                .productType(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(price)
                .build();
    }

}