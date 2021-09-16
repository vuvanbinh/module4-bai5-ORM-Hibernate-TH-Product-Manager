package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.IProductService;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("product")
@PropertySource("classpath:upload_file.properties")
public class ProductController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public ModelAndView showProductList(){
        ModelAndView mav = new ModelAndView("product");
        mav.addObject("products",productService.findAll());
        return mav;
    }


    @GetMapping("create")
    public ModelAndView showCreateProductForm(){
        ModelAndView mav = new ModelAndView("create");
        mav.addObject("product",new ProductForm());
        return mav;
    }
    @PostMapping("save")
    public ModelAndView saveProduct(@ModelAttribute ProductForm productForm){
        ModelAndView mav = new ModelAndView("redirect:/product");
        //B1: Luu file vao vung nho;
        MultipartFile multipartFile = productForm.getImg();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(),new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //B2: Tạo mứoi đối tượng Product từ productForm
        Product product = new Product();
//        product.setId(productService.findMaxId());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        product.setProducer(productForm.getProducer());
        product.setImg(fileName);

        //Tiến hành save vào productList;
        productService.save(product);
        return mav;
    }

    @GetMapping("{id}/edit")
    public ModelAndView showEditProductForm(@PathVariable int id){
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("product",productService.findById(id));
        return mav;
    }
    @PostMapping("edit")
    public ModelAndView editProduct(Product product){
        ModelAndView mav = new ModelAndView("redirect:/product");
        product.setImg(productService.findById(product.getId()).getImg());
        productService.update(product.getId(),product);
        return mav;
    }

    @GetMapping("{id}/delete")
    public ModelAndView showDeleteFrom(@PathVariable int id){
        ModelAndView mav = new ModelAndView("delete");
        mav.addObject("product",productService.findById(id));
        mav.addObject("message","Are you sure?");
        return mav;
    }
    @PostMapping("delete")
    public String deleteProduct(Product product, RedirectAttributes redirect){
        productService.delete(product.getId());
        redirect.addFlashAttribute("message","Removed product successfully!");
        return "redirect:/product";
    }

    @GetMapping("{id}/detail")
    public ModelAndView detailProduct(@PathVariable int id){
        ModelAndView mav = new ModelAndView("detail");
        mav.addObject("product",productService.findById(id));
        return mav;
    }

    @GetMapping("search")
    public ModelAndView searchByProductName(String name){
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("products",productService.findByName(name));
        return mav;
    }

}
