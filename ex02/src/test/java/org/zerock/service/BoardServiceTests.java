package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	private BoardService service;
	
//	@Test
//	public void testRegister() {
//		BoardVO board = new BoardVO();
//		board.setTitle("���� �ۼ��ϴ� ��");
//		board.setContent("���� �ۼ��ϴ� ����");
//		board.setWriter("newbie");
//
//		service.register(board);
//		log.info("������ �Խù���, ��ȣ:" + board.getBno());
//	}

	
	@Test
	public void testUpdate() {
		BoardVO board = service.get(1L);
		if (board == null) {
			return;
		}
		
		board.setTitle("���� �����մϴ�.");
		log.info("MODIFY RESULT: " + service.modify(board));
	}
}
