package sample.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.product.SellingStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static sample.cafekiosk.spring.domain.product.SellingStatus.SELLING;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown(){
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품의 상품번호에서 1증가한 값이다.")
    @Test
    void test1() {
        // given
        Product product = createProduct("001", HANDMADE, SELLING,"아메리카노", 4000);
        productRepository.saveAll(List.of(product));
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("딸기스무디")
                .price(5000)
                .productType(HANDMADE)
                .sellingStatus(SELLING)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // then
        assertThat(productResponse)
                .extracting("productNumber", "productType", "price", "name", "sellingStatus")
                .contains("002", HANDMADE, SELLING, "딸기스무디", 5000);

        List<Product> products = productRepository.findAll();

        assertThat(products)
                .hasSize(2)
                .extracting("productNumber", "price", "sellingStatus", "name", "productType")
                .containsExactlyInAnyOrder(tuple("001", 4000, SELLING, "아메리카노", HANDMADE), tuple("002", 5000, SELLING, "딸기스무디", HANDMADE));
    }

    @DisplayName("신규 상품 등록시에 기존 상품이 하나도 없는 경우라면 상품 번호는 001 이다.")
    @Test
    void test2() {
        // given
        ProductCreateRequest productCreateRequest = ProductCreateRequest.builder()
                .sellingStatus(SELLING)
                .price(5000)
                .name("딸기스무디")
                .productType(HANDMADE)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(productCreateRequest.toServiceRequest());

        // then
        assertThat(productResponse)
                .extracting("productNumber", "price", "sellingStatus", "name", "productType")
                .containsExactly("001", 5000, SELLING, "딸기스무디", HANDMADE);

        List<Product> products = productRepository.findAll();
        assertThat(products)
                .hasSize(1)
                .extracting("productNumber", "price", "sellingStatus", "name", "productType")
                .contains(tuple("001", 5000, SELLING, "딸기스무디", HANDMADE));


    }

    private Product createProduct(String productNumber, ProductType productType, SellingStatus sellingStatus, String name, int price) {
        Product product = Product.builder()
                .productNumber(productNumber)
                .productType(productType)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
        return product;
    }

}