package com.dabast.common.util;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


public class FlatXmlWriterEx extends FlatXmlWriter {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(FlatXmlWriterEx.class);

	private int totalTableCount;
	private int processedTableCount = 0;
	private String processingTableName;
	private int currentTableTotalCount;
	private Map<String, Integer> tableRowCountMap = new HashMap<String, Integer>(0);
	
	public FlatXmlWriterEx(OutputStream outputStream, String encoding)throws UnsupportedEncodingException {
		super(outputStream, encoding);
	}
	public FlatXmlWriterEx(Writer writer) {
		super(writer);
	}
	public FlatXmlWriterEx(Writer writer, String encoding) {
		super(writer, encoding);
	}

	@Override
	public void startTable(ITableMetaData metaData) throws DataSetException {
		processedTableCount ++;
		currentTableTotalCount = tableRowCountMap.get(metaData.getTableName());
		processingTableName = metaData.getTableName();
		super.startTable(metaData);
	}
	@Override
	public void write(IDataSet dataSet) throws DataSetException {
		for(String tableName: dataSet.getTableNames()){
			tableRowCountMap.put(tableName, 0);
		}
		totalTableCount = dataSet.getTableNames().length;
		super.write(dataSet);
	}

	public int getTotalTableCount() {
		return totalTableCount;
	}
	public int getProcessedTableCount() {
		return processedTableCount;
	}
	public int getCurrentTableTotalCount() {
		return currentTableTotalCount;
	}
	public String getProcessingTableName() {
		return processingTableName;
	}
}
