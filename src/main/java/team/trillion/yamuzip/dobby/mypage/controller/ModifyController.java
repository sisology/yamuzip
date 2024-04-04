package team.trillion.yamuzip.dobby.mypage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.trillion.yamuzip.auth.service.AuthService;
import team.trillion.yamuzip.dobby.mypage.model.dto.WorkdayDTO;
import team.trillion.yamuzip.login.model.dto.UserDTO;
import team.trillion.yamuzip.dobby.mypage.model.dto.ModifyDTO;
import team.trillion.yamuzip.dobby.mypage.model.service.ModifyService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Slf4j
@Controller
@RequestMapping("/dobby")
@RequiredArgsConstructor
public class ModifyController {

    private final ModifyService modifyService;
    private final AuthService authenticationService;

    @Value("${profile.imageDir}")
    private String uploadDirectory;


    /* 도비 프로필 화면 이동 */
    @GetMapping("/profile")
    public String modifyUser(Model model,
                             @AuthenticationPrincipal UserDTO user){

        ModifyDTO dobby = modifyService.getDobby(user.getUserCode());
        if(dobby == null) {
            model.addAttribute("dobby", new ModifyDTO());
        }
         else {
            model.addAttribute("dobby", dobby);
            model.addAttribute("workdayList", dobby.getWorkdayList().stream().map(WorkdayDTO::getDayWeek).toList());

            System.out.println(dobby);
            System.out.println(dobby.getWorkdayList());

        }

        return "dobby/profile";
    }


    /* 회원 정보 수정 */
    @PostMapping("/profile")
    public String modifyUser(String dobNickname, String dobContent, String dobArea, String dobCareerDays,
                             @AuthenticationPrincipal UserDTO user,
                             Model model,
                             /* 프로필이미지 delete추가 */
                             @RequestParam(required = false)boolean profileDelete,
                             @RequestParam(required = false) MultipartFile profile) {

        /* 도비 정보 수정 */

        ModifyDTO modifyDobby = new ModifyDTO();
        modifyDobby.setUserCode(user.getUserCode());
        modifyDobby.setDobNickname(dobNickname);
        modifyDobby.setDobContent(dobContent);
        modifyDobby.setDobArea(dobArea);
        modifyDobby.setDobCareerDays(dobCareerDays);

        /* 도비 이미지 업로드 */

        log.info(String.valueOf(profile));
        if (profile.getSize() > 0) {
            /* 상단에 IMAGE_DIR 추가 */
            String filePath = uploadDirectory + "profile-images";
            System.out.println("filePath = " + filePath);
            String originFileName = profile.getOriginalFilename();//업로드 파일명
            String ext = originFileName.substring(originFileName.lastIndexOf("."));//업로드 파일명에서 확장자 분리
            String savedName = UUID.randomUUID() + ext;//고유한 파일명 생성 + 확장자 추가
            String finalFilePath = filePath + "/" + savedName;
            System.out.println("finalFilePath = " + finalFilePath);
            File dir = new File(filePath);
            if (!dir.exists()) dir.mkdirs();
            try {
                profile.transferTo(new File(finalFilePath));
                model.addAttribute("userImg", "/upload/profile-images/" + savedName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String saveFileName = "/upload/profile-images/" + savedName;
            modifyDobby.setDobImg(saveFileName);
            model.addAttribute("modifyDobby", modifyDobby);
        }
        if (profileDelete) {
            modifyDobby.setDobImg("removed");
        }

        modifyService.modifyDobby(modifyDobby);

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(user.getUserId()));


        return "redirect:/";
    }

    protected Authentication createNewAuthentication(String userId) {

        UserDetails newPrincipal = authenticationService.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken newAuth
                = new UsernamePasswordAuthenticationToken(newPrincipal, newPrincipal.getPassword(), newPrincipal.getAuthorities());

        return newAuth;
    }

}

