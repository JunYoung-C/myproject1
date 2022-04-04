package toyproject.instragram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toyproject.instragram.controller.dto.PostSaveForm;
import toyproject.instragram.service.PostDto;
import toyproject.instragram.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public String addPost(@Valid PostSaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "main";
        }

        postService.addPost(new PostDto(form.getMemberId(), getFilePaths(form), form.getText()));
        return "redirect:/main";
    }

    @PostMapping("/posts/{postId}")
    public void modifyPost(@PathVariable Long postId, @RequestParam String modifiedText) {
        // TODO 수정, 삭제 등의 처리도 200 처리하는거 추후에 고려하기
        postService.modifyPostText(postId, modifiedText);
    }

    private List<String> getFilePaths(PostSaveForm form) {
        return form.getFiles().stream().map(file -> file.getOriginalFilename()).collect(Collectors.toList());
    }
}
