package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
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

//	// return type void : find a view(.jsp) with the name inside @GetMapping
//	@GetMapping("/list")
//	public void list(Model model) {
//		log.info("list");
//		model.addAttribute("list", service.getList()); // this model will be finally transferred into the list.jsp
//		// you can use variables inside an object "list" in the list.jsp
//	}
	
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or modify");
		model.addAttribute("boardVO",service.get(bno));
	}
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list: " + cri);
		model.addAttribute("list",service.getList(cri));
		model.addAttribute("pageMaker", new PageDTO(cri, 123));
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

//	@GetMapping("/get")
//	public void get(@RequestParam("bno") long bno, Model model) {
//		log.info("/get");
//		model.addAttribute("board", service.get(bno));
//	}

	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify: " + board);
		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove..."+bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}

		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		return "redirect:/board/list";
	}

}
