package SilkLoad.example.service;


import SilkLoad.dto.ProductFormDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.ProductImageRepository;
import SilkLoad.repository.ProductRepository;
import SilkLoad.service.MemberService;
import SilkLoad.service.ProductService;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@SpringBootTest
public class ProductAddTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductImageRepository productImageRepository;

}
