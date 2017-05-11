/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.importer.listener;

import org.beangle.model.transfer.Transfer;
import org.beangle.model.transfer.TransferListener;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.AbstractItemImporter;

public class ItemImporterListener implements TransferListener {

	protected AbstractItemImporter importer;
    
    //新增数量
    private int count = 0;
    
    //更新数量
    private int ucount = 0;

    public int getUcount() {
        return ucount;
    }

    public void setUcount(int ucount) {
        this.ucount = ucount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

	public void onFinish(TransferResult tr) {
	}

	public void onItemFinish(TransferResult tr) {
	}

	public void setTransfer(Transfer transfer) {
		if (transfer instanceof AbstractItemImporter) {
			this.importer = (AbstractItemImporter) transfer;
		}
	}

	public void onStart(TransferResult tr) {
	}

	public void onItemStart(TransferResult tr) {
	}
	
	protected void addCount() {
		this.count++;
	}
	protected void addUcount() {
		this.ucount++;
	}
}
