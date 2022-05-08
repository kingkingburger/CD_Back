package SilkLoad.service;


import SilkLoad.dto.ProductFormDto;
import SilkLoad.dto.ProductSaleDto;
import SilkLoad.entity.*;
import SilkLoad.repository.ProductImageRepository;
import SilkLoad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService  {

    /**
     * 물품 이미지가 저장될 절대 경로, application properties 참조
     */
    @Value("${imgFile.dir}")
    private String imgFileDir;

    private final ProductRepository productRepository;

    /**
     * imageRepository.... 서비스를 따로 분리해야 하나 생각 중
     */
    private final ProductImageRepository productImageRepository;



    public void save(ProductFormDto productFormDto, Members loginMember) throws IOException {

        String categoryName = productFormDto.getCategory();

        Category category = categoryClassification(categoryName);

        Product product = getProduct(productFormDto, loginMember);
        //카테고리 등록
        product.changeCategory(category);

        //product 저장
        productRepository.save(product);


        //이미지등록
        List<ProductImage> productImages = filesImgSave(productFormDto.getImageFileList());
        productImages.forEach(productImage -> {
            productImage.changeProduct(product);
            productImageRepository.save(productImage);
        });


    }

    private Product getProduct(ProductFormDto productFormDto, Members loginMember) {
        Product product = Product.builder()
                .name(productFormDto.getName())
                .instantPrice(productFormDto.getInstancePrice())
                .auctionPrice(productFormDto.getAuctionPrice())
                .explanation(productFormDto.getExplanation())
                .createdDate(productFormDto.getCreatedDate())
                .productTime( productFormDto.getProductTime())
                .productType(ProductType.sale)
                .members(loginMember)
                .build();
        return product;
    }

    private Category categoryClassification(String categoryName) {

        String[] splitCategory = categoryName.split(",");
        Category category = Category.builder()
                .first(splitCategory[0])
                .second(splitCategory[1])
                .build();
        return category;

    }

    /**
     * @param filename 서버에 저장된 파일 명
     * @return 저장된 파일의 경로 명 + 파일 명
     */
    public String getFullPath(String filename) {
        return imgFileDir + filename;
    }


    /**
     * @param imgFileList
     * @return 리스트의 productImage 객체에 upload 파일명과 store 파일 명을 저장한 후 그 list를 반환한다.
     * @throws IOException
     */
    private List<ProductImage> filesImgSave(List<MultipartFile> imgFileList) throws IOException {

        List<ProductImage> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : imgFileList) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(fileImgSave(multipartFile));
            }
        }
        return storeFileResult;
    }


    /**
     * 이미지 저장 메소드
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public ProductImage fileImgSave(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalImgName = multipartFile.getOriginalFilename();
        String storeFileName = createSaveImgName(originalImgName);
        System.out.println("storeFileName = " + getFullPath(storeFileName));
        //디렉터리에 파일 저장하는 메소드
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return ProductImage.builder()
                .uploadFileName(originalImgName)
                .storeFileName(storeFileName)
                .build();
    }

    /**
     * @param originalImgName uuid를 통해 랜덤한 파일 이름 만들기, 파일 이름 겹치는 것 방지
     * @return uuid 파일 명 + 확장자 명
     */
    private String createSaveImgName(String originalImgName) {
        String ext = extractExt(originalImgName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    /**
     * @param originalFilename 원래 파일 명
     * @return 원래 파일의 확장자 이름(img, png..)
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


    /**
     * product_image db로 부터 모든 파일 추출
     */
    public List<ProductImage> findAllProductImage() {
        return productImageRepository.findAll();
    }


    /**
     * id로 Product(물품) 찾기
     *
     * @param id
     * @return id로 1개의 물품만 반환
     */
    public Product findById_Product(long id) {
        Product product = productRepository.findById(id).get();
        return product;
    }


    /**
     * db에 있는 product 테이블에 있는 모든것
     * @return List형태로 반환
     */
    public List<ProductSaleDto> findAllProduct() {
        List<Product> allProduct = productRepository.findAll();

        return productToProductSaleDto(allProduct);
    }

    public List<ProductSaleDto> productToProductSaleDto(List<Product> allProduct) {

        List<ProductSaleDto> productSaleDtoList = new ArrayList<>();


        allProduct.forEach( product -> {

            ProductSaleDto productSaleDto = getProductSaleDto(product);

            productSaleDtoList.add(productSaleDto);

        } );
        return productSaleDtoList;
    }

    public ProductSaleDto getProductSaleDto(Product product) {
        ProductSaleDto productSaleDto = ProductSaleDto.builder().id(product.getId())
                .name(product.getName())
                .auctionPrice(product.getAuctionPrice())
                .instantPrice(product.getInstantPrice())
                .explanation(product.getExplanation())
                .productType(product.getProductType())
                .category(product.getCategory())
                .deadLine(productDeadLine( product.getCreatedDate(), product.getProductTime() ))
                .productTime(product.getProductTime())
                .productImagesList(product.getProductImagesList())
                .build();
        return productSaleDto;
    }

    private String productDeadLine(LocalDateTime createdProduct, ProductTime productTime) {

        if (productTime == ProductTime.ONE_DAY  ) {
            createdProduct = createdProduct.plusDays(1);
        }
        else if (productTime == ProductTime.TWO_DAY) {
            createdProduct = createdProduct.plusDays(2);

        }
        String parsedLocalDateTimeNow = "";
        if (createdProduct != null) {
            parsedLocalDateTimeNow = createdProduct.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        return parsedLocalDateTimeNow;


    }


    public List<Product> findAllimg() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct;
    }

}
