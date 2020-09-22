package com.car.myapp.review.dao;

import java.util.List;

import com.car.myapp.review.dto.ReviewDto;

public interface ReviewDao {
	//�۸��
	public List<ReviewDto> getList(ReviewDto dto);
	//���� ����
	public int getCount(ReviewDto dto);
	//�� �߰�
	public void insert(ReviewDto dto);
	//������ ������
	public ReviewDto getData(int num);
	//Ű���带 Ȱ���� ������ ������
	public ReviewDto getData(ReviewDto dto);
	//��ȸ�� ���� ��Ű��
	public void addViewCount(int num);
	//�� ����
	public void delete(int num);
	//�� ����
	public void update(ReviewDto dto);
}
