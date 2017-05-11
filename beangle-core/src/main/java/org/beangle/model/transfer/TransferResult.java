/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换结果
 * 
 * @author beangle
 */
public class TransferResult {

	List<TransferMessage> msgs = new ArrayList<TransferMessage>();

	List<TransferMessage> errs = new ArrayList<TransferMessage>();

	Transfer transfer;

	private int total = 0;

	private int num = 0;

	// 新增数量
	private int count = 0;

	// 更新数量
	private int ucount = 0;
	/**
	 * 成功记录数
	 */
	private int success = 0;

	/**
	 * 失败记录数
	 */
	private int fail = 0;

	private String status = "";

	public void addFailure(String message, Object value) {
		errs.add(new TransferMessage(transfer.getTranferIndex(), message, value));
	}

	public void addMessage(String message, Object value) {
		msgs.add(new TransferMessage(transfer.getTranferIndex(), message, value));
	}

	public boolean hasErrors() {
		return !errs.isEmpty();
	}

	public void printResult() {
		for (final TransferMessage msg : msgs) {
			System.out.println(msg);
		}
	}

	public int errors() {
		return errs.size();
	}

	public List<TransferMessage> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<TransferMessage> msgs) {
		this.msgs = msgs;
	}

	public List<TransferMessage> getErrs() {
		return errs;
	}

	public void setErrs(List<TransferMessage> errs) {
		this.errs = errs;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getUcount() {
		return ucount;
	}

	public void setUcount(int ucount) {
		this.ucount = ucount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

}
