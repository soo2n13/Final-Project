package com.car.myapp.review.service;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.myapp.review.dao.ReviewDao;
import com.car.myapp.review.dto.ReviewDto;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewDao reviewDao;
	
	//�� �������� ��Ÿ�� row �� ����
	final int PAGE_ROW_COUNT=10;
	//�ϴ� ���÷��� ������ ����
	final int PAGE_DISPLAY_COUNT=5;
	
	@Override
	public void getList(HttpServletRequest request) {
		//������ �������� ��ȣ
		int pageNum=1;
		//������ �������� ��ȣ�� �Ķ���ͷ� ���޵Ǵ��� �о�� ����.	
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){//������ ��ȣ�� �Ķ���ͷ� �Ѿ�´ٸ�
			//������ ��ȣ�� �����Ѵ�.
			pageNum=Integer.parseInt(strPageNum);
		}
		//������ ������ �������� ���� ResultSet row ��ȣ
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//������ ������ �������� �� ResultSet row ��ȣ
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		/*
			�˻� Ű���忡 ���õ� ó�� 
		*/
		String keyword=request.getParameter("keyword"); //�˻� Ű����
		String condition=request.getParameter("condition"); //�˻� ����
		if(keyword==null){//���޵� Ű���尡 ���ٸ� 
			keyword=""; //�� ���ڿ��� �־��ش�. 
			condition="";
		}
		//���ڵ��� Ű���带 �̸� ����� �д�. 
		String encodedK=URLEncoder.encode(keyword);
		
		//�˻� Ű����� startRowNum, endRowNum �� ���� FileDto ��ü ����
		ReviewDto dto=new ReviewDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		if(!keyword.equals("")){ //���� Ű���尡 �Ѿ�´ٸ� 
			if(condition.equals("title_content")){
				//�˻� Ű���带 FileDto ��ü�� �ʵ忡 ��´�. 
				dto.setTitle(keyword);
				dto.setContent(keyword);	
			}else if(condition.equals("title")){
				dto.setTitle(keyword);
			}else if(condition.equals("writer")){
				dto.setWriter(keyword);
			}
		}
		//���� ��� ������
		List<ReviewDto> list=reviewDao.getList(dto);
		//��ü row �� ���� 
		int totalRow=reviewDao.getCount(dto);
		
		//��ü �������� ���� ���ϱ�
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//���� ������ ��ȣ
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//�� ������ ��ȣ
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//�� ������ ��ȣ�� �߸��� ���̶�� 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //�������ش�. 
		}
		
		//EL ���� ����� ���� �̸� request �� ��Ƶα�
		request.setAttribute("list", list);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("condition", condition);
		request.setAttribute("keyword", keyword);
		request.setAttribute("encodedK", encodedK);				
	}


	@Override
	public void getDetail(HttpServletRequest request) {
		//�Ķ���ͷ� ���޵Ǵ� �۹�ȣ 
		int num=Integer.parseInt(request.getParameter("num"));
		/*
		�˻� Ű���忡 ���õ� ó�� 
		*/
		String keyword=request.getParameter("keyword"); //�˻� Ű����
		String condition=request.getParameter("condition"); //�˻� ����
		if(keyword==null){//���޵� Ű���尡 ���ٸ� 
			keyword=""; //�� ���ڿ��� �־��ش�. 
			condition="";
		}
		//���ڵ��� Ű���带 �̸� ����� �д�. 
		String encodedK=URLEncoder.encode(keyword);
		
		//�۹�ȣ�� �˻� Ű���带 ���� ReviewDto ��ü ����
		ReviewDto dto=new ReviewDto();
		dto.setNum(num);//�۹�ȣ ��� 
		
		if(!keyword.equals("")){ //���� Ű���尡 �Ѿ�´ٸ� 
			if(condition.equals("title_content")){
				//�˻� Ű���带 FileDto ��ü�� �ʵ忡 ��´�. 
				dto.setTitle(keyword);
				dto.setContent(keyword);	
			}else if(condition.equals("title")){
				dto.setTitle(keyword);
			}else if(condition.equals("writer")){
				dto.setWriter(keyword);
			}
		}
		//�ڼ��� ������ �� ���� 
		ReviewDto resultDto=reviewDao.getData(dto);
		
		//view ���������� �ʿ��� ���� HttpServletRequest �� ���
		request.setAttribute("dto", resultDto);
		request.setAttribute("condition", condition);
		request.setAttribute("keyword", keyword);
		request.setAttribute("encodedK", encodedK);
		
		//�� ��ȸ�� �ø���
		reviewDao.addViewCount(num);
		
		/* �Ʒ��� ��� ����¡ ó�� ���� ����Ͻ� ���� �Դϴ�.*/
		final int PAGE_ROW_COUNT=5;
		final int PAGE_DISPLAY_COUNT=5;
		/*
		//��ü row �� ������ �о�´�.
		//�ڼ��� ������ ���� ��ȣ�� ref_group  ��ȣ �̴�. 
		int totalRow=cafeCommentDao.getCount(num);

		//������ �������� ��ȣ(���� pageNum �� �Ѿ���� ������ ���� ������ ������)
		String strPageNum=request.getParameter("pageNum");
		//��ü �������� ���� ���ϱ�
		int totalPageCount=
						(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//�ϴ� ������ �������� ��� ����� �����ֱ�� �ϰ� 
		int pageNum=totalPageCount;
		//���� ������ ��ȣ�� �Ѿ�´ٸ�
		if(strPageNum!=null) {
			//�Ѿ�� �������� �ش��ϴ� ��� ����� �����ֵ��� �Ѵ�. 
			pageNum=Integer.parseInt(strPageNum);
		}
		//������ ������ �������� ���� ResultSet row ��ȣ
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//������ ������ �������� �� ResultSet row ��ȣ
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		
		//���� ������ ��ȣ
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//�� ������ ��ȣ
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//�� ������ ��ȣ�� �߸��� ���̶�� 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //�������ش�. 
		}
		*/
		
	}
		


	@Override
	public void saveContent(ReviewDto dto) {
		reviewDao.insert(dto);
		
	}

	@Override
	public void updateContent(ReviewDto dto) {
		reviewDao.update(dto);
		
	}

	@Override
	public void deleteContent(int num, HttpServletRequest request) {
		reviewDao.delete(num);
		
	}

}
