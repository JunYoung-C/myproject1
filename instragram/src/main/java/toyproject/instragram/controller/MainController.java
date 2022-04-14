package toyproject.instragram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import toyproject.instragram.SignIn;
import toyproject.instragram.SignInMember;
import toyproject.instragram.controller.dto.CommentSaveForm;
import toyproject.instragram.controller.dto.PostSaveForm;
import toyproject.instragram.controller.dto.SignInForm;
import toyproject.instragram.entity.Member;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(@SignIn SignInMember signInMember, Model model) {

        if (signInMember == null) {
            model.addAttribute("signInForm", new SignInForm());
            return "signIn";
        }

        CommentSaveForm commentSaveForm = new CommentSaveForm();
        commentSaveForm.setMemberId(signInMember.getMemberId());

        model.addAttribute("postSaveForm", new PostSaveForm());
        model.addAttribute("commentSaveForm", commentSaveForm);
        model.addAttribute("signInMember", signInMember);
        return "main";
    }
}
