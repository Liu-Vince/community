package com.lwc.community.controller;


import com.lwc.community.entity.User;
import com.lwc.community.service.UserService;
import com.lwc.community.util.CommunityUtil;
import com.lwc.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 刘文长
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片!");
            return "/site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件的格式不正确!");
            return "/site/setting";
        }

        // 生成随机文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        // 确定文件存放的路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            // 存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常!", e);
        }

        // 更新当前用户的头像的路径(web访问路径)
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        }
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updatePassword(Model model, @CookieValue("ticket") String ticket, String oldPassword, String newPassword, String confirmPassword) {


        User u = hostHolder.getUser();
        String oldPasswordMd5 = CommunityUtil.md5(oldPassword + u.getSalt());

//        if (oldPassword==null){
//            model.addAttribute("oldPasswordMsg","请输入原始密码!");
//            return "/site/setting";
//        }
//        if (newPassword==null){
//            model.addAttribute("newPasswordMsg","请输入新密码!");
//            return "/site/setting";
//        }
//        if (confirmPassword==null) {
//            model.addAttribute("confirmPasswordMsg", "请确认密码!");
//            return "/site/setting";
//        }

        if (!(u.getPassword().equals(oldPasswordMd5))) {
            model.addAttribute("oldpasswordMsg", "旧密码输入错误");
            return "/site/setting";
        }


        if (StringUtils.isBlank(newPassword)) {
            model.addAttribute("newpasswordMsg", "新密码不能为空");
            return "/site/setting";
        }
//        if (newPassword.length() < 8) {
//            model.addAttribute("newpasswordMsg", "新密码不能小于八位");
//            return "/site/setting";
//        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("confirmpasswordMsg", "两次密码输入不一致");
            return "/site/setting";
        }
        if (newPassword.equals(oldPassword)) {
            model.addAttribute("newpasswordMsg", "新密码不能和原密码相同");
            return "/site/setting";
        }

        userService.updatePassword(u.getId(), CommunityUtil.md5(newPassword + u.getSalt()));
        userService.logout(ticket);
        return "redirect:/login";

    }
}
