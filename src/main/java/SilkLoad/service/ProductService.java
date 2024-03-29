package SilkLoad.service;


import SilkLoad.dto.*;
import SilkLoad.entity.*;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.CategoryRepository;
import SilkLoad.repository.OrderRepository;
import SilkLoad.repository.ProductImageRepository;
import SilkLoad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    /**
     * 물품 이미지가 저장될 절대 경로, application properties 참조
     */
    @Value("${imgFile.dir}")
    private String imgFileDir;


    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    /**
     * imageRepository.... 서비스를 따로 분리해야 하나 생각 중
     */
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void save(ProductFormDto productFormDto, Members loginMember) throws IOException {

        String categoryName = productFormDto.getCategory();
        Category category = categoryClassification(categoryName);

        Product product = ProductFormDtoToProduct(productFormDto, loginMember);
        //카테고리 등록
        product.changeCategory(category);
        category.getProductList().add(product);//카테고리의 productList에 product값 넣기
        //product 저장
        Product savedProduct = productRepository.save(product);

        Orders order = getOrders(loginMember, savedProduct);

        orderRepository.save(order);

        //이미지등록
        List<ProductImage> productImages = filesImgSave(productFormDto.getImageFileList());
        productImages.forEach(productImage -> {
            productImage.changeProduct(product);
            productImageRepository.save(productImage);
        });
    }


    private Orders getOrders(Members loginMember, Product savedProduct) {
        Orders order = Orders.builder()
                .product(savedProduct)
                .offerPrice(-1L)
                .memberBuyer(loginMember)
                .orderDateTime(LocalDateTime.now())
                .orderType(OrderType.unRegistered)
                .build();
        return order;
    }

    private Product ProductFormDtoToProduct(ProductFormDto productFormDto, Members loginMember) {
        Product product = Product.builder()
                .name(productFormDto.getName())
                .instantPrice(productFormDto.getInstancePrice())
                .auctionPrice(productFormDto.getAuctionPrice())
                .explanation(productFormDto.getExplanation())
                .predictionImage(productFormDto.getPredictionImage())
                .createdDate(productFormDto.getCreatedDate())
                .productTime(productFormDto.getProductTime())
                .productType(ProductType.sale)
                .members(loginMember)
                .build();
        return product;
    }


    @Transactional
    Category categoryClassification(String categoryName) {
        Category category;
        String[] splitCategory = categoryName.split(",");

        Optional<Category> categoryWithProduct = categoryRepository.
                findByFirstAndSecondAndThirdContains(splitCategory[0], splitCategory[1], splitCategory[2]);

        if (!categoryWithProduct.isPresent()) { // 존재하지 않는다면
            category = Category.builder()
                    .first(splitCategory[0])
                    .second(splitCategory[1])
                    .third(splitCategory[2])
                    .build();
        } else {
            category = categoryWithProduct.get();
        }
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
    @Transactional
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
     * id로 Product(물품) 찾기
     *
     * @param id
     * @return id로 1개의 물품만 반환
     */
    @Transactional(readOnly = true)
    public ProductRecordDto findById_ProductRecordDto(long id) {
        Product product = productRepository.findById(id).get();
        ProductRecordDto productRecordDto = ProductToProductRecordDto(product);
        return productRecordDto;
    }


    /**
     * 매개변수 product를 통해 ProductRecordDto를 생성
     */
    @Transactional
    public ProductRecordDto ProductToProductRecordDto(Product product) {

        ProductRecordDto productRecordDto = ProductRecordDto.builder()
                .id(product.getId())
                .name(product.getName())
                .auctionPrice(product.getAuctionPrice())
                .instantPrice(product.getInstantPrice())
                .explanation(product.getExplanation())
                .predictionImage(product.getPredictionName())
                .productType(product.getProductType())
                .categoryRecordDto(CategoryToDto(product.getCategory()))
                .deadLine(productDeadLine(product.getCreatedDate(), product.getProductTime()))
                .productTime(product.getProductTime())
                .productImagesList(ImageListToDto(product.getProductImagesList()))
                .build();

        return productRecordDto;
    }

    /**
     * ProductImage의 List를 Dto로 만드는 메서드
     *
     * @param productImageList
     * @return
     */
    @Transactional
    List<ProductImageRecordDto> ImageListToDto(List<ProductImage> productImageList) {

        List<ProductImageRecordDto> productImageRecordDtoList = new ArrayList<ProductImageRecordDto>();

        productImageList.forEach(productImage -> {
            ProductImageRecordDto buildImage = ProductImageRecordDto.builder()
                    .storeFileName(productImage.getStoreFileName())
                    .uploadFileName(productImage.getStoreFileName())
                    .build();

            productImageRecordDtoList.add(buildImage);
        });

        return productImageRecordDtoList;
    }


    public List<ProductRecordDto> ListProductToDtoList(List<Product> productList) {
        List<ProductRecordDto> productRecordDtoList = new ArrayList<>();
        productList.forEach((product -> {
            ProductRecordDto productRecordDto = ProductRecordDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .auctionPrice(product.getAuctionPrice())
                    .instantPrice(product.getInstantPrice())
                    .explanation(product.getExplanation())
                    .productType(product.getProductType())
                    .categoryRecordDto(CategoryToDto(product.getCategory()))
                    .deadLine(productDeadLine(product.getCreatedDate(), product.getProductTime()))
                    .productTime(product.getProductTime())
                    .productImagesList(ImageListToDto(product.getProductImagesList()))
                    .build();
            productRecordDtoList.add(productRecordDto);
        }));

        return productRecordDtoList;
    }


    private CategoryRecordDto CategoryToDto(Category category) {

        return CategoryRecordDto.builder()
                .first(category.getFirst())
                .second(category.getSecond())
                .third(category.getThird())
                .build();
    }

    /**
     * @param createdProduct
     * @param productTime    productTime의 상태가 무엇인지 확인하여 등록 날짜를 통한 마감 시간을 구하는 메소드
     *                       localDateTime 형식이 아닌 String 형식의 날짜와 시간을 리턴
     * @return
     */
    public LocalDateTime productDeadLine(LocalDateTime createdProduct, ProductTime productTime) {
        if (productTime == ProductTime.ONE_DAY) {
            createdProduct = createdProduct.plusDays(1);
        } else if (productTime == ProductTime.TWO_DAY) {
            createdProduct = createdProduct.plusDays(2);
        }

        return createdProduct;
    }


    /**
     * second 카테고리 별로 물품을 가져오기
     * 아래 3종 세트
     *
     * @param second
     * @param pageable
     * @return
     */
    @Transactional
    public Page<ProductRecordDto> pagedBysecondcategoryProduct(String second, Pageable pageable) {

        Page<ProductCategoryDto> productCategoryDtos = productRepository.findsecondcategory(second, pageable);
        List<ProductRecordDto> productRecordDtoList = PageProductToDtoList(productCategoryDtos);
        //List 를 Page로 변환
        return ListToPage(pageable, productRecordDtoList);
    }

    /**
     * 카테고리 별로 물품을 가져오기
     * 아래 3종 세트
     *
     * @param first
     * @param pageable
     * @return
     */
    @Transactional
    public Page<ProductRecordDto> pagedByfirstsecondcategoryProduct(String first, String second, Pageable pageable) {

        Page<ProductCategoryDto> productCategoryDtos = productRepository.findFirstandSecondcategory(first, second, pageable);
        List<ProductRecordDto> productRecordDtoList = PageProductToDtoList(productCategoryDtos);
        //List 를 Page로 변환
        return ListToPage(pageable, productRecordDtoList);
    }
    private List<ProductRecordDto> PageProductToDtoList(Page<ProductCategoryDto> productCategoryDtos) {
        List<ProductRecordDto> productRecordDtoList = new ArrayList<>();

        //ProductRecordDto로 바꾸기
        for (ProductCategoryDto productCategoryDto : productCategoryDtos) {
            List<ProductImageRecordDto> list = CategoryProductToImageDtoList(productCategoryDto);
            categoryToProductRecordDto(productRecordDtoList, list, productCategoryDto);
        }
        return productRecordDtoList;
    }

    /**
     * 카테고리화 된 객체 ProductRecordDto로 만들기
     *
     * @param productRecordDtoList
     * @param list
     * @param productCategoryDto
     */
    private void categoryToProductRecordDto(List<ProductRecordDto> productRecordDtoList,
                                            List<ProductImageRecordDto> list,
                                            ProductCategoryDto productCategoryDto) {
        String first = productCategoryDto.getFirst();
        String second = productCategoryDto.getSecond();
        String third = productCategoryDto.getThird();
        CategoryRecordDto build = CategoryRecordDto.builder().first(first).second(second).third(third).build();

        ProductRecordDto productRecordDto = ProductRecordDto.builder()
                .id(productCategoryDto.getId())
                .name(productCategoryDto.getName())
                .auctionPrice(productCategoryDto.getAuctionPrice())
                .instantPrice(productCategoryDto.getInstantPrice())
                .explanation(productCategoryDto.getExplanation())
                .productType(productCategoryDto.getProductType())
                .categoryRecordDto(build)
                .deadLine(productDeadLine(productCategoryDto.getCreatedDate(), productCategoryDto.getProductTime()))
                .productTime(productCategoryDto.getProductTime())
                .productImagesList(list)
                .build();

        productRecordDtoList.add(productRecordDto);
    }

    /**
     * 카테고리로 잘라온 물품의 이미지 리스트 만들기
     *
     * @param productCategoryDto
     * @return
     */
    private List<ProductImageRecordDto> CategoryProductToImageDtoList(ProductCategoryDto productCategoryDto) {
        List<ProductImageRecordDto> list = new ArrayList<>();

        ProductImageRecordDto imageRecordDto = ProductImageRecordDto.builder()
                .storeFileName(productCategoryDto.getStoreFileName())
                .uploadFileName(productCategoryDto.getUploadFileName())
                .build();
        list.add(imageRecordDto);

        return list;
    }

    /**
     * List로 된 productRecordDto 객체를 Page객체로 변환
     *
     * @param pageable
     * @param productRecordDtoList
     * @return
     */
    private Page<ProductRecordDto> ListToPage(Pageable pageable, List<ProductRecordDto> productRecordDtoList) {
        Page<ProductRecordDto> productRecordDtos = new PageImpl<>(productRecordDtoList, pageable, productRecordDtoList.size());
        return productRecordDtos;
    }

    public List<HomeProductDto> findHomeProductDto(Pageable pageable) {
        List<HomeProductDto> content = orderRepository.findHomeProductDtoBySaleOrder(pageable).getContent();

        return content;
    }



    //사용법: spring @Scheduled 검색
    //1시간 마다 실행
    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void checkDeadLine() {
        List<Product> allProduct = productRepository.findByProductTimeNotAndProductType(ProductTime.NONE, ProductType.sale);
        allProduct.forEach(product -> {
            LocalDateTime deadLine = productDeadLine(product.getCreatedDate(), product.getProductTime());
            if (product.getProductType() == ProductType.sale &&
                    product.getProductTime() != ProductTime.NONE &&
                    deadLine.isBefore(LocalDateTime.now())) {
                product.setProductType(ProductType.cancel);
            }
        });
    }
}
