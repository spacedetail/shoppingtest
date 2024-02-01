package com.boot.shopping.controller;

import com.boot.shopping.dto.ItemDto;
import com.boot.shopping.dto.MemberFormDto;
import com.boot.shopping.entity.Member;
import com.boot.shopping.service.MemberService;
/*import jakarta.validation.Valid;*/
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //1
    @GetMapping(value = "/new")
    private String memberForm(Model model){

        model.addAttribute("memberFormDto",new MemberFormDto());

        return "/member/memberForm";
    }

    //2
    @PostMapping(value = "/new")
    private String memberForm(@Valid MemberFormDto memberFormDto , BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "/member/memberForm";
        }

        try{
            Member member = Member.createMember(memberFormDto,passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "/member/memberForm";
        }

        return "redirect:/";
    }

    //3.
    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }
    //4.
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginError", "아이디, 비밀번호를 확인 해주세요");
        return "/member/memberLoginForm";
    }



}
