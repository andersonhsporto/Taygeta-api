package com.api.taygeta.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class ApiErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handleError() {
    return "pages/error";
  }

}
