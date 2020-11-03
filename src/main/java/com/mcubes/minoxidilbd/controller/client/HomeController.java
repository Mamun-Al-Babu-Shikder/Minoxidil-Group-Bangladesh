package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.configuration.security.EncryptDecrypt;
import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.ClientOrder;
import com.mcubes.minoxidilbd.entity.Product;
import com.mcubes.minoxidilbd.repository.ProductRepository;
import com.mcubes.minoxidilbd.service.ClientOrderService;
import com.mcubes.minoxidilbd.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by A.A.MAMUN on 10/7/2020.
 */
@Controller
public class HomeController {

    @Autowired
    private CommonData commonData;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = {"/","/home"})
    public String homePage(Model model, Principal principal){

        model.addAttribute("login", principal!=null);

        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        List<Product> newProducts = productService.getNewProducts();
        List<Product> bestSellingProducts = productService.getBestSellingProducts();
        List<Product> highRatedProducts = productService.getHighRatedProducts();
        List<Product> topDiscountedProducts = productService.getTopDiscountedProducts();

        model.addAttribute("newProducts", newProducts);
        model.addAttribute("bestSellingProducts", bestSellingProducts);
        model.addAttribute("highRatedProducts", highRatedProducts);
        model.addAttribute("topDiscountedProducts", topDiscountedProducts);

        return "client/pages/index";
    }


    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @ResponseBody
    @RequestMapping("/encrypt")
    public String encrypt(@RequestParam String v)
    {
        try {
            String value = encryptDecrypt.encrypt(v);
            return value;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/decrypt")
    public String decrypt(@RequestParam String v){
        System.out.println("# val : "+v);
        try{
            return encryptDecrypt.decrypt(v);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Autowired
    private ClientOrderService clientOrderService;

    @ResponseBody
    @RequestMapping("/client_order")
    public Page<ClientOrder> fetchClientOrder( @RequestParam(name = "status", defaultValue = "Pending") String status) {
        ClientOrder.OrderStatus  orderStatus = commonData.getOrderStatusMap().get(status.toLowerCase());
        System.out.println("# Order Status : " + orderStatus);
        return clientOrderService.fetchClientOrderByStatus(orderStatus);
    }



}
