package sample.cafekiosk.spring.api.service.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.product.SellingStatus;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private ProductType productType;
    private SellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    public ProductCreateServiceRequest(ProductType productType, SellingStatus sellingStatus, String name, int price) {
        this.productType = productType;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder().productNumber(nextProductNumber).name(name).sellingStatus(sellingStatus).price(price).productType(productType).build();
    }
}
