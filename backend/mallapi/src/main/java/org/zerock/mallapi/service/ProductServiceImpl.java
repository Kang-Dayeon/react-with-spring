package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.domain.ProductImage;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {
        // Pageable : 페이징을 처리하는 인터페이스
        // PageRequest : 현재 페이지와 한 페이지에 보여 줄 게시물 개수 등을 설정하여 페이징 요청하는 클래스
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        List<ProductDTO> dtoList = result.get().map(arr -> {
            ProductDTO productDTO = null;

            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            productDTO = ProductDTO.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .build();

            String imageStr = productImage.getFileName();
            productDTO.setUploadFileName(List.of(imageStr));

            return productDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public Long register(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);

        log.info("---------------------------------");
        log.info(product);
        log.info(product.getImageList());

        Long pno = productRepository.save(product).getPno();

        return pno;
    }

    @Override
    public ProductDTO get(Long pno) {

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        return entityToDTO(product);
    }

    @Override
    public void modify(ProductDTO productDTO) {
        Optional<Product> result = productRepository.findById(productDTO.getPno());

        Product product = result.orElseThrow();

        product.changeDesc(productDTO.getPdesc());
        product.changeName(productDTO.getPname());
        product.changePrice(product.getPrice());
        product.changeDel(productDTO.isDelFlag());

        List<String> uploadFileNames = productDTO.getUploadFileName();

        if(uploadFileNames != null && !uploadFileNames.isEmpty()){
            uploadFileNames.forEach(uploadName -> {
                product.addImageString(uploadName);
            });
        }

        productRepository.save(product);

    }

    @Override
    public void remove(Long pno) {
        productRepository.deleteById(pno);
    }

    private ProductDTO entityToDTO(Product product){
        ProductDTO productDTO = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                .delFlag(product.isDelFlag())
                .build();

        List<ProductImage> imageList = product.getImageList();

        if(imageList == null || imageList.isEmpty()){
            return productDTO;
        }

        List<String> fileNameList = imageList.stream().map(productImage -> productImage.getFileName()).toList();

        productDTO.setUploadFileName(fileNameList);

        return productDTO;
    }

    private Product dtoToEntity(ProductDTO productDTO){
        Product product = Product.builder()
                .pno(productDTO.getPno())
                .pname(productDTO.getPname())
                .pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice())
                .build();

        List<String> uploadFileNames = productDTO.getUploadFileName();

        if(uploadFileNames == null || uploadFileNames.isEmpty()){
            return product;
        }

        uploadFileNames.forEach(fileName -> {
            product.addImageString(fileName);
        });

        return product;
    }


}
