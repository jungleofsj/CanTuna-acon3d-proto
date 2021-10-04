package CanTuna.CanTunaacon3d.controller;

import CanTuna.CanTunaacon3d.Service.ItemService;
import CanTuna.CanTunaacon3d.Service.UserService;
import CanTuna.CanTunaacon3d.domain.ExRate;
import CanTuna.CanTunaacon3d.domain.Item;
import CanTuna.CanTunaacon3d.domain.User;
import org.hibernate.usertype.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Controller
public class ItemController {


    private final ItemService itemService;
    private final UserService userService;

    public ItemController(ItemService itemService,UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }


    @ResponseBody
    @GetMapping("/items/all")
    public List<Item> list(Model model) {

        List<Item> items = itemService.viewItem();

        return items;
    }

    @GetMapping("/items/new")
    public String createForm() {
        return "/createItemForm";
    }

    @PostMapping("/items/new")
    public ResponseEntity createItem(@RequestHeader("Authorization") Long userType, @RequestBody Item item) {

        if (userType != User.USER_TYPE_CREATOR) {
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
        Item resultItem = itemService.createItem(item);

        return new ResponseEntity(resultItem, HttpStatus.OK);
    }

    @GetMapping("/items/update")
    public String updateForm() {
        return "/updateItemForm";
    }

    @PutMapping("/items/{id}")
    public ResponseEntity updateItem(@RequestHeader("Authorization") Long userType, @RequestBody Item item, @PathVariable("id") long id) {
        if (userType != User.USER_TYPE_EDITOR) {
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        item.setItemApproved(true);

        Optional<Item> itemResult = itemService.updateItem(id, item);

        if (itemResult.isEmpty()) {
            return new ResponseEntity("UnExist productID", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(itemResult, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/items")
    public ResponseEntity approvedItem(@RequestHeader("Authorization") Long userType, @RequestParam("isApproved") Boolean isApproved) {

        List<Item> items = null;

        if (isApproved == true) {
            items = itemService.viewApprovedItem();
        } else {
            items = itemService.nonApprovedItem();
        }
        if (userType == User.USER_TYPE_CUSTOMER) {
            return new ResponseEntity("열람권한이 없습니다", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(items, HttpStatus.OK);
    }

    @PostMapping("/items/purchase")
    public ResponseEntity purchaseItem(@RequestHeader("Authorization")Long UserType, @RequestBody PurchaseForm form) {

        Optional<Item> item = itemService.findItemById(form.getProductId());

        if(item.isEmpty()) {
            return new ResponseEntity("없는 상품입니다.", HttpStatus.BAD_REQUEST);
        }
        Item resultItem = itemService.findItemById(form.getProductId()).stream().findAny().get();

        itemService.buyItem(resultItem, form.getCustomerId(), form.getQuantity(), form.getCurrency());

        return new ResponseEntity(resultItem.getItemId(), HttpStatus.OK);
    }
}
