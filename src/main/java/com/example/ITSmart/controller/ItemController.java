package com.example.ITSmart.controller;

import com.example.ITSmart.entity.Item;
import com.example.ITSmart.service.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemsRepository itemsRepository;

    @GetMapping({"", "/"})
    public String showProductList(Model model) {
        List<Item> items = itemsRepository.findAll();
        model.addAttribute("items", items);
        return "items/index";
    }

    @GetMapping("/create")
    public ModelAndView showCreatePage(Model model){
        ModelAndView mav = new ModelAndView("items/createItem");
        Item item = new Item();
        mav.addObject("item", item);
        return mav;
    }

    @PostMapping("/saveItem")
    public String saveItem(@ModelAttribute Item item, BindingResult result) {
        itemsRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/edit")
    public ModelAndView editItem(@RequestParam Long itemId){
        ModelAndView mav = new ModelAndView("items/createItem");
        Item item = itemsRepository.findById(itemId);
        mav.addObject("item", item);
        return mav;
    }

    @GetMapping("/deleteItem")
    @Transactional
    public String deleteItem(@RequestParam Long itemId) {
        itemsRepository.deleteById(itemId);
        return "redirect:/items";
    }


}
