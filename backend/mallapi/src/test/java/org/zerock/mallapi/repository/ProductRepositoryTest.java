package org.zerock.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.dto.PageRequestDTO;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){
        for(int i = 0; i < 10; i++) {
            Product product = Product.builder().pname("test" + i).pdesc("test desc" + i).price(1000).build();

            product.addImageString(UUID.randomUUID() + "_" + "IMAGE1.jpg");
            product.addImageString(UUID.randomUUID() + "_" + "IMAGE2.jpg");

            productRepository.save(product);

        }
    }

    @Transactional
    @Test
    public void testRead(){
        Long pno = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
    }

    @Test
    public void testList(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.get().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        productRepository.searchList(pageRequestDTO);
    }


}
