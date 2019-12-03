package rqchen.fkbbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
public class UploadController {
    String pathfile;
    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }

    @RequestMapping("/insert")
    public String insert(@RequestParam("fileUpload")MultipartFile file, HttpServletRequest request){
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffixName);
        fileName = UUID.randomUUID()+suffixName;
        System.out.println(fileName);
        String path = request.getSession().getServletContext().getRealPath("") + "upload"+File.separator+fileName;
        pathfile=File.separator+"upload"+File.separator+fileName;
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
        return "redirect:/upload";
    }

    @RequestMapping("/show")
    public String show(Model model){
        model.addAttribute("filepath",pathfile);
        return "show";
    }
}
