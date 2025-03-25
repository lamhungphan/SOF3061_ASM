package com.fpoly.asm.controller;

import com.fpoly.asm.entity.Account;
import com.fpoly.asm.entity.Cart;
import com.fpoly.asm.service.AccountService;
import com.fpoly.asm.service.impl.CartServiceImpl;
import com.fpoly.asm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

}


