package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	// @AllArgsConstructor�� ����ؼ� ���� �ƱԸ�Ʈ �����ڸ� �������� ��, ������ �ʴ� ������ ���� @Autowired��
	// �ڵ����Եȴ�
	// @AllArgsConstructor�� �����ϸ� �� ���� ���� @Autowired�� �����Ѵ�.
	private BoardService service;

	// return type void : find a view(.jsp) with the name inside @GetMapping
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list", service.getList()); // this model will be finally transferred into the list.jsp
		// you can use variables inside an object "list" in the list.jsp
	}

	@GetMapping("/register")
	public void register() { //empty methoed is used to return a view in the @GetMapping annotation
		
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) { // Spring MVC automatically makes BoardVO variable
																		// board
		// parameters are added automatically into BoardVO board
		log.info("register: " + board);
		service.register(board);
		// Flash attributes are saved temporarily before the redirect (typically in the
		// session) to be made available to the request after the redirect and removed
		// immediately.
		rttr.addFlashAttribute("result", board.getBno());
		return "redirect:/board/list";
	}

	@GetMapping("/get")
	public void get(@RequestParam("bno") long bno, Model model) {
		log.info("/get");
		model.addAttribute("board", service.get(bno));
	}

	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify: " + board);
		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") long bno, RedirectAttributes rttr) {
		log.info("remove..."+bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		
		return "redirect:/board/list";
	}

}
