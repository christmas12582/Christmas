/**
 * 
 */
package com.lottery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.ShareMapper;
import com.lottery.model.Share;

/**
 * @author ws_yu
 *
 */
@Service
public class ShareService {

	@Autowired
	private ShareMapper shareMapper;
	
	/**
	 * 保存分享记录
	 * @param share
	 * @return
	 */
	@Transactional
	public Integer saveShare(Share share){
		shareMapper.insert(share);
		return share.getId();
	}
}
