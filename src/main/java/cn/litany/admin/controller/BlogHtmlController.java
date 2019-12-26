package cn.litany.admin.controller;

import cn.litany.admin.service.BlogReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Litany
 */
@Controller
public class BlogHtmlController {

    @Qualifier("nameReader")
    @Autowired
    private BlogReader blogReader;


    @RequestMapping(value = "/blog/{region}/{blogName}",method = RequestMethod.GET)
    public String  movetoBlogHtml(RedirectAttributes attr, @PathVariable String blogName, @PathVariable String region) throws ServletException, IOException {
        attr.addAttribute("region",region);
        attr.addAttribute("blog",blogName);
        return "redirect:/blog.html";
    }



}
