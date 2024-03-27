package team.trillion.yamuzip.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @GetMapping("/insert")
    public void registCategory() {}

    @GetMapping("/select")
    public void getCategoryList() {}

    @GetMapping("/modify")
    public void modifyCategoryList() {}

    @GetMapping("/delete")
    public void deleteCategoryList() {}
}
