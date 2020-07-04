package application.boot.controller;

import application.boot.model.Role;
import application.boot.model.User;
import application.boot.service.RoleService;
import application.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "error";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "loginBS";
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return roleService.getAll();
    }

    @GetMapping(value = "/admin")
    public ModelAndView adminPage() {
        List<User> users = userService.getAllUsers();
        ModelAndView mav = new ModelAndView("admin/userDetails");
        mav.addObject("users", users);
        return mav;
    }

    @GetMapping(value = "/admin/add")
    public String addUserForm(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);
        return "admin/addUser";
    }

    @PostMapping(value = "/admin/add")
    public String addUserAction(User user) {
        try {
            userService.addUser(user);
            return "redirect:/admin";
        } catch (EntityExistsException cve) {
            return "/admin/addUserExists";
        }
    }

    @GetMapping(value = "/admin/edit/{id}")
    public ModelAndView editUserForm(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);
        ModelAndView mav = new ModelAndView("admin/editUser");
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping(value = "/admin/edit/save")
    public String editUserAction(User user) {
        try {
            userService.editUser(user);
            return "redirect:/admin";
        } catch (DataIntegrityViolationException cve) {
            return "/admin/editUserExists";
        }
    }

    @GetMapping(value = "/admin/delete/{id}")
    public ModelAndView deleteUserForm(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);
        ModelAndView mav = new ModelAndView("admin/deleteUser");
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping(value = "/admin/delete/save")
    public String deleteUserAction(Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
