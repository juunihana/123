package io.projects.social.controller;

import io.projects.social.dto.PostDto;
import io.projects.social.service.PostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/{id}")
  public PostDto get(@PathVariable long id) {
    return postService.getById(id);
  }

  @GetMapping
  public List<PostDto> getAll() {
    return postService.getAll();
  }
}
