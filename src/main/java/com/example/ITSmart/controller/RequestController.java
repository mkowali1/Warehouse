package com.example.ITSmart.controller;

import com.example.ITSmart.entity.*;
import com.example.ITSmart.service.ItemsRepository;
import com.example.ITSmart.service.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @GetMapping({"", "/"})
    public String showProductList(Model model) {
        List<Request> requests = requestRepository.findAll();
        List<Item> itemList = itemsRepository.findAll();
        model.addAttribute("items", itemList);
        model.addAttribute("requests", requests);
        return "requests/index";
    }

    @GetMapping("/order")
    public ModelAndView showOrderPage(Model model, @RequestParam Long itemId){
        Item item = itemsRepository.findById(itemId);
        model.addAttribute("item", item);

        ModelAndView mav = new ModelAndView("requests/createRequest");
        Request request = new Request();

        mav.addObject("request", request);
        return mav;
    }


    @PostMapping("/saveRequest")
    public String saveRequest(@ModelAttribute Request request,
                              RedirectAttributes redirAttrs,
                              @RequestParam String itemIdName,
                              @RequestParam String unitOfMeasurementName,
                              @RequestParam String priceName) {
        request.setItemId(Long.valueOf(itemIdName));
        request.setUnitOfMeasurement(unitOfMeasurementName);
        request.setPrice(Double.parseDouble(priceName));
        requestRepository.save(request);
        redirAttrs.addFlashAttribute("message", "Request Created!!!");
        return "redirect:/orders/message";
    }

    @GetMapping("/accept")
    @Transactional
    public String acceptRequest(@RequestParam Long requestId, Model model){
        Request request = requestRepository.findById(requestId);
        Item item = itemsRepository.findById(request.getItemId());
        if(item.getQuantity() > request.getQuantity()){
            item.setQuantity(item.getQuantity() - request.getQuantity());
            itemsRepository.save(item);
            requestRepository.deleteById(requestId);
            return "redirect:/orders";
        } else{
            model.addAttribute("errorMessage", "Nie udało się dodać przedmiotu. Spróbuj ponownie.");
            return "redirect:/";
        }
    }

    @GetMapping("/deleteOrder")
    @Transactional
    public String deleteOrder(@RequestParam Long requestId) {
        requestRepository.deleteById(requestId);
        return "redirect:/orders";
    }

    @GetMapping("/message")
    public String showFail(Model model) {
        return "requests/message";
    }

}
