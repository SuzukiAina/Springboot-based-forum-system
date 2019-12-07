package rqchen.fkbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rqchen.fkbbs.entity.Reply;
import rqchen.fkbbs.entity.Theme;
import rqchen.fkbbs.entity.User;
import rqchen.fkbbs.service.ReplyService;
import rqchen.fkbbs.service.ThemeService;
import rqchen.fkbbs.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class Info_controller {
    @Autowired
    ThemeService themeService;
    @Autowired
    UserService userService;
    @Autowired
    ReplyService replyService;

    @RequestMapping("/info")
    public String info(HttpServletRequest request, Model model){
        model.addAttribute("sector_now","user_info");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:main";
        }
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
        }
        model.addAttribute("login_user",user);
        return "info";
    }

    @RequestMapping("/info_update")
    public String info_update(HttpServletRequest request,@RequestParam("name")String nickname,@RequestParam("myfile") MultipartFile file){
        if(!Objects.equals(file.getOriginalFilename(), "")){
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println(suffixName);
            fileName = UUID.randomUUID()+suffixName;
            System.out.println(fileName);
            String path = request.getSession().getServletContext().getRealPath("") + "upload"+ File.separator+fileName;
            String pathfile=File.separator+"upload"+File.separator+fileName;
            File file1 = new File(path);
            if(!file1.getParentFile().exists()){
                file1.getParentFile().mkdir();
            }
            System.out.println(path);
            try {
                //将图片保存到static文件夹里
                file.transferTo(new File(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
            User user = (User)request.getSession().getAttribute("user");
            user.setUser_name(nickname);
            user.setImg_url(pathfile);
            userService.update(user);
        }else {
            User user = (User)request.getSession().getAttribute("user");
            user.setUser_name(nickname);
            userService.updateName(user);
        }
        return "redirect:info";
    }

    @RequestMapping("/mytheme")
    public String mytheme(HttpServletRequest request, Model model){
        model.addAttribute("sector_now","my_theme");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        int login_id=Integer.parseInt(user.getUser_id());
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
        }
        model.addAttribute("login_user",user);
        List<Theme> themeList=themeService.mythemeList(login_id);
        model.addAttribute("my_themes",themeList);
        return "info";
    }

    @RequestMapping("/myreply")
    public String myreply(HttpServletRequest request, Model model){
        model.addAttribute("sector_now","my_reply");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        int login_id=Integer.parseInt(user.getUser_id());
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
        }
        model.addAttribute("login_user",user);
        List<Reply> replyList=replyService.getReplyU(login_id);
        model.addAttribute("my_replies",replyList);
        return "info";
    }

    @RequestMapping("/managetheme")
    public String managetheme(HttpServletRequest request, Model model){
        model.addAttribute("sector_now","manage_theme");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        int login_id=Integer.parseInt(user.getUser_id());
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
            return "redirect:/info";
        }
        model.addAttribute("login_user",user);
        List<Theme> themeList=themeService.themeList();
        for(Theme mtheme:themeList){
            int uid=Integer.parseInt(mtheme.getUser_id());
            mtheme.setUser_name(userService.getUserName(uid));
        }
        model.addAttribute("themes",themeList);
        return "info";
    }

    @RequestMapping("/manageuser")
    public String manageuser(@RequestParam(required = false,value ="uid",defaultValue = "1") int user_id,HttpServletRequest request, Model model){
        model.addAttribute("sector_now","manage_user");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        int login_id=Integer.parseInt(user.getUser_id());
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
            return "redirect:/info";
        }
        model.addAttribute("login_user",user);
        List<User> userList=userService.userList();
        for(User users:userList){
            int id=Integer.parseInt(users.getUser_id());
            users.setMyreply(replyService.getReplyU(id));
            users.setMytheme(themeService.mythemeList(id));
        }
        model.addAttribute("users",userList);
        return "info";
    }

    @RequestMapping("/managereply")
    public String managereply(@RequestParam(required = false,value ="rid",defaultValue = "1") int user_id,HttpServletRequest request, Model model){
        model.addAttribute("sector_now","manage_reply");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        int login_id=Integer.parseInt(user.getUser_id());
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
            return "redirect:/info";
        }
        model.addAttribute("login_user",user);
        List<Reply> replyListList=replyService.replyList();
        for(Reply reply:replyListList){
            int tid=Integer.parseInt(reply.getTheme_id());
            int uid=Integer.parseInt(reply.getUser_id());
            reply.setRTheme_title(themeService.themeTitle(tid));
            reply.setUser_name(userService.getUserName(uid));
        }
        model.addAttribute("replies",replyListList);
        return "info";
    }

    @RequestMapping("/edit_user")
    public String edituser(@RequestParam(value ="uid") int user_id,HttpServletRequest request, Model model){
        model.addAttribute("sector_now","edit_user");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        int login_id=Integer.parseInt(user.getUser_id());
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
            return "redirect:/info";
        }
        model.addAttribute("login_user",user);
        User edituser=userService.getById(user_id);
        edituser.setMyreply(replyService.getReplyU(user_id));
        edituser.setMytheme(themeService.mythemeList(user_id));
        for(Reply reply:edituser.getMyreply()){
            int id=Integer.parseInt(reply.getTheme_id());
            reply.setRTheme_title(themeService.themeTitle(id));
        }
        model.addAttribute("edituser",edituser);
        model.addAttribute("edituser_theme",edituser.getMytheme());
        model.addAttribute("edituser_reply",edituser.getMyreply());
        return "info";
    }

    @RequestMapping("/edit_theme")
    public String edit_theme(HttpServletRequest request,@RequestParam("tid")int theme_id, Model model){
        model.addAttribute("sector_now","edit_theme");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        int login_id=Integer.parseInt(user.getUser_id());
        Theme edit_theme=themeService.getById(theme_id);
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
            model.addAttribute("login_user",user);
            model.addAttribute("edit_theme",edit_theme);
        }else {
            model.addAttribute("ROLE",0);
            model.addAttribute("login_user",user);
            System.out.println("帖子uid为"+themeService.getUIDbt(theme_id));
            System.out.println("登陆uid为"+login_id);
            if(Integer.parseInt(themeService.getUIDbt(theme_id))!=login_id){
                return "redirect:/info";
            }else{
                model.addAttribute("edit_theme",edit_theme);
            }
        }
        return "info";
    }

    @RequestMapping("/edit_reply")
    public String edit_reply(HttpServletRequest request,@RequestParam("rid")int reply_id, Model model){
        model.addAttribute("sector_now","edit_reply");
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        int login_id=Integer.parseInt(user.getUser_id());
        String role=user.getRole();
        if(role.equals("1")){
            model.addAttribute("ROLE",1);
        }else {
            model.addAttribute("ROLE",0);
            return "redirect:/info";
        }
        model.addAttribute("login_user",user);
        Reply edit_reply=replyService.getById(reply_id);
        model.addAttribute("edit_reply",edit_reply);
        return "info";
    }

    @RequestMapping("/edituser_update")
    public String edituser_update(HttpServletRequest request,@RequestParam("edit_uid")int id,@RequestParam("name")String nickname,@RequestParam("myfile") MultipartFile file){
        if(!Objects.equals(file.getOriginalFilename(), "")){
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println(suffixName);
            fileName = UUID.randomUUID()+suffixName;
            System.out.println(fileName);
            String path = request.getSession().getServletContext().getRealPath("") + "upload"+ File.separator+fileName;
            String pathfile=File.separator+"upload"+File.separator+fileName;
            File file1 = new File(path);
            if(!file1.getParentFile().exists()){
                file1.getParentFile().mkdir();
            }
            System.out.println(path);
            try {
                //将图片保存到static文件夹里
                file.transferTo(new File(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("修改的"+id);
            User user = userService.getById(id);
            user.setUser_name(nickname);
            user.setImg_url(pathfile);
            userService.update(user);
        }else {
            User user = userService.getById(id);
            user.setUser_name(nickname);
            userService.updateName(user);
        }
        return "redirect:manageuser";
    }
}
