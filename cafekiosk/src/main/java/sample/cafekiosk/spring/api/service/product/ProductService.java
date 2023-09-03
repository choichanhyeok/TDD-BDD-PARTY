package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.product.SellingStatus;

import java.util.List;
import java.util.stream.Collectors;

import static sample.cafekiosk.spring.domain.product.ProductType.*;
import static sample.cafekiosk.spring.domain.product.SellingStatus.*;

/*
 * readOnly = true: 읽기전용
 * CRUD 에서 CUD 작업이 동작을 안한다. / 오직 조회만 가능케됨.
 * JPA에서 이점이 생기는데, 업데이트 쿼리 같은거 쓸 때 스냅샷 저장, 변경 감지를 안해도 되서 성능 향상을 얻을 수 있음
 *
 * 사실 가장 중요한 건 CQRS: COMMAND(데이터를 생성, 변경, 삭제) / READ(조회)를 분리하자
 * 보통 일반적인 서비스의 경우 커맨드보다 리드라는 행위가 훨씬 빈도수가 크다.
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    // 동시성 이슈 (1): productNumber라는 필드에 Unique걸고, 튕겼을(누가 이미 등록했을 때) 때 3회 이상 재시도. => 빈도수가 낮은 경우
    // 동시성 이슈 (2): productNumber가 증가하는 값이 아니라 uuid 값으로 사용하면 동시성 관련 없이 문제 해결 가능
    public ProductResponse createProduct(ProductCreateServiceRequest productCreateRequest){
        String nextProductNumber = createNextProductNumber();

        Product product = productCreateRequest.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    @Transactional(readOnly = true)
    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProductNumber();

        if (latestProductNumber == null) {
            return "001";
        }

        Integer latestProductNumberInt = Integer.valueOf(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }

    /**
     *
     * @return
     */
    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(forDisplay());
        List<ProductResponse> productResponses = products.stream()
                                                        .map(product -> ProductResponse.of(product))
                                                        .collect(Collectors.toList());

        return productResponses;
    }


}
