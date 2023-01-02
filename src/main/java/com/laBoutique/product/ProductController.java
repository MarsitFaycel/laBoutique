package com.laBoutique.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @GetMapping("/all")
    public String getAllProduct(Model model){
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products", products);
        return "products";
    }
    @GetMapping("/edit")
    public String greetingForm(Model model) {
        model.addAttribute("product", new Product());
        return "editProduct";
    }
    @PostMapping("/edit")
    public RedirectView  greetingSubmit(@ModelAttribute Product product, Model model) {
        model.addAttribute("product", product);
        productService.save(product);
        return new RedirectView("/product/all");
    }


}
