package com.chandrika.miu.retailstore;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/add")
    public ModelAndView productEntry(){
        ModelAndView mav = new ModelAndView();
        Map<String, Product> params = new HashMap<>();
        params.put("product", new Product());
        return new ModelAndView("addProduct", params);
    }

    @PostMapping("/add")
    public ModelAndView addProduct(HttpSession session,
                                   @ModelAttribute("product") @Valid Product product,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return new ModelAndView("addProduct");
        }
        Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
       if (productList == null){
           productList = new HashMap<>();
       }
       productList.put(product.getProductNumber(), product);
       session.setAttribute("productList", productList);
       return new ModelAndView("redirect:/products");
    }

    @GetMapping("")
    public ModelAndView getProducts(HttpSession session){
        Map<String, Product> productList = (Map<String, Product>)session.getAttribute("productList");
        if (productList == null) {
            productList = new HashMap<String, Product>();
            session.setAttribute("productList", productList);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("productList", productList.values());
        return new ModelAndView("products", params);
    }

    @PostMapping("/removeproduct")
    public ModelAndView removeProduct(HttpSession session,
                                   @RequestParam("productNumber") String productNumber,
                                   RedirectAttributes redirectAttributes){
        Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
        if (productList == null){
            productList = new HashMap<>();
        }
        productList.remove(productNumber);
        session.setAttribute("productList", productList);
        return new ModelAndView("redirect:/products");
    }

    @PostMapping("/addtocart")
    public ModelAndView addToCart(HttpSession session,
                                      @RequestParam("productNumber") String productNumber,
                                      RedirectAttributes redirectAttributes){
        Map<String, ShoppingCart> cartItems = (Map<String, ShoppingCart>) session.getAttribute("cartItems");
        if (cartItems == null){
            cartItems = new HashMap<>();
        }
        Map<String, Product> productList = (Map<String, Product>) session.getAttribute("productList");
        Product product = productList.get(productNumber);
        ShoppingCart cartItem;
        if (cartItems.containsKey(productNumber)){
           cartItem = cartItems.get(productNumber);
           cartItem.setProductQuantity(cartItem.getProductQuantity()+1);
        }else {
            cartItem = new ShoppingCart(1, product.getProductNumber(), product.getName(), product.getPrice());
        }
        cartItems.put(productNumber, cartItem);
        session.setAttribute("cartItems", cartItems);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/showcart")
    public ModelAndView showCart(HttpSession session){
        Map<String, ShoppingCart> cartList = (Map<String, ShoppingCart>)session.getAttribute("cartItems");
        if (cartList == null) {
            cartList = new HashMap<>();
            session.setAttribute("cartItems", cartList);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("cartList", cartList.values());
        params.put("totalPrice", cartList.values().stream().mapToDouble(ShoppingCart::getProductPrice).sum());
        return new ModelAndView("cart", params);
    }

    @PostMapping("/removeproductfromcart")
    public ModelAndView removeProductFromCart(HttpSession session,
                                      @RequestParam("productNumber") String productNumber,
                                      RedirectAttributes redirectAttributes){
        Map<String, ShoppingCart> cartList = (Map<String, ShoppingCart>) session.getAttribute("cartItems");
        if (cartList == null){
            cartList = new HashMap<>();
        }
         ShoppingCart cartItem = cartList.get(productNumber);
         if (cartItem.getProductQuantity() > 1){
             cartItem.setProductQuantity(cartItem.getProductQuantity()-1);
             cartList.put(productNumber, cartItem);
         }else {
             cartList.remove(productNumber);
         }
        session.setAttribute("cartItems", cartList);
        return new ModelAndView("redirect:/products/showcart");
    }
}
