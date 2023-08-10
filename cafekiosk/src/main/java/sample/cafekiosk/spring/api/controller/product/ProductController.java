package sample.cafekiosk.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products/selling")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getSellingProducts(){
        return productService.getSellingProducts();
    }
}