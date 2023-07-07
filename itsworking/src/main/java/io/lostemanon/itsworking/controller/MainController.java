package io.lostemanon.itsworking.controller;

import io.lostemanon.itsworking.dto.CommentDto;
import io.lostemanon.itsworking.dto.PostDto;
import io.lostemanon.itsworking.dto.UserDto;
import io.lostemanon.itsworking.service.CommentService;
import io.lostemanon.itsworking.service.PostService;
import io.lostemanon.itsworking.service.UserService;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/")
public class MainController {

  private final PostService postService;
  private final UserService userService;
  private final CommentService commentService;

  @Autowired
  public MainController(PostService postService,
      UserService userService,
      CommentService commentService) {
    this.postService = postService;
    this.userService = userService;
    this.commentService = commentService;
  }

  @GetMapping
  public String index(Model model, HttpServletRequest request) {
    String postContent = "";
    Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
    if (flashMap != null) {
      postContent = (String) flashMap.get("postContent");
    }

    model.addAttribute("posts", postService.getAll());
    model.addAttribute("newPost", PostDto.builder()
        .content(postContent)
        .build());
    return "index";
  }

  @GetMapping("about")
  public String about() {
    return "about";
  }

  @GetMapping("useful")
  public String useful() {
    return "useful";
  }

  @PostMapping("createPost")
  public ModelAndView createPost(@ModelAttribute PostDto postDto,
      RedirectAttributes model) {
    if (StringUtils.isEmpty(postDto.getTitle())) {
      model.addFlashAttribute("emptyTitleError", true);
      model.addFlashAttribute("postContent", postDto.getContent());
      return new ModelAndView("redirect:/");
    }
    if (StringUtils.isEmpty(postDto.getContent())) {
      model.addFlashAttribute("postEmptyError", true);
      model.addFlashAttribute("postContent", postDto.getContent());
      return new ModelAndView("redirect:/");
    }
    postService.save(postDto);
    return new ModelAndView("redirect:/");
  }

  @PostMapping("createComment")
  public ModelAndView commentPost(RedirectAttributes model,
      @ModelAttribute CommentDto commentDto) {
    long postId = commentDto.getPostId();
    if (StringUtils.isEmpty(commentDto.getContent())) {
      model.addFlashAttribute("commentEmptyError", true);
      return new ModelAndView("redirect:/posts/" + postId);
    }
    commentService.save(postId, commentDto);
    return new ModelAndView("redirect:/posts/" + postId);
  }

  @GetMapping("posts/{id}")
  public String getPost(
      @PathVariable long id,
      Model model) {
    model.addAttribute("post", postService.getById(id));
    model.addAttribute("newComment", CommentDto.builder()
        .postId(id)
        .build());
    return "post";
  }

  @GetMapping("users/{id}")
  public String getUser(@PathVariable long id, Model model) {
    model.addAttribute("user", userService.getById(id));
    model.addAttribute("userPosts", postService.getAllByUserId(id));
    return "user";
  }

  @GetMapping("signin")
  public String signin(Model model) {
    model.addAttribute("user", new UserDto());
    return "signin";
  }

  @PostMapping("signin")
  public String signin(@ModelAttribute UserDto userDto, Model model) {
    if (StringUtils.isEmpty(userDto.getName())) {
      model.addAttribute("usernameEmptyError", true);
      model.addAttribute("user", new UserDto());
      return "signin";
    }
    if (StringUtils.isEmpty(userDto.getPassword())) {
      model.addAttribute("passwordEmptyError", true);
      model.addAttribute("user", new UserDto());
      return "signin";
    }

    return "redirect:/";
  }

  @GetMapping("signup")
  public String signup(Model model) {
    model.addAttribute("user", new UserDto());
    model.addAttribute("passwordsMatchError", false);
    return "signup";
  }

  @PostMapping("signup")
  public ModelAndView signup(
      @ModelAttribute UserDto userDto,
      Model model) {
    if (StringUtils.isEmpty(userDto.getName())) {
      model.addAttribute("usernameEmptyError", true);
      model.addAttribute("user", userDto);
      return new ModelAndView("signup");
    }
    if (userService.getAll().stream()
        .map(UserDto::getName)
        .collect(Collectors.toList())
        .contains(userDto.getName())) {
      model.addAttribute("usernameAlreadyTakenError", true);
      model.addAttribute("user", userDto);
      return new ModelAndView("signup");
    }
    if (StringUtils.isEmpty(userDto.getPassword())) {
      model.addAttribute("passwordEmptyError", true);
      model.addAttribute("user", userDto);
      return new ModelAndView("signup");
    }
    if (!userDto.getPassword().equals(userDto.getPasswordRepeat())) {
      model.addAttribute("passwordsMatchError", true);
      model.addAttribute("user", userDto);
      return new ModelAndView("signup");
    }

    userService.save(userDto);
    return new ModelAndView("redirect:/");
  }
}
