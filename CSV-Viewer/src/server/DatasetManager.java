package server;

import java.util.ArrayList;

public class DatasetManager implements IDatasetManager{
	
	private ArrayList<Dataset> datasets= new ArrayList<Dataset>(); 
	
	public int registerDataset(String datasetName, String canonicalPath) {
		if(datasetName.equals("")) {
			return -1;
		}
		//Datasey already exists
		if(searchDataset(datasetName)!=null){
			return -10;
		}
		Dataset dataset=new Dataset(datasetName);
		dataset.setData(canonicalPath);
		datasets.add(dataset);
		return 0;
	}

	public String [] retrieveDataset(String datasetName, ArrayList<String []> data) {

		Dataset theDataset = searchDataset(datasetName);
		if(theDataset!=null)
		{
			ArrayList<String[]> temp=theDataset.getData();
			//DeepCopy
			for(int i=1;i<temp.size();i++)
			{
				String[] row=temp.get(i);
				String[] deepRow=new String[row.length];
				for(int j=0;j<deepRow.length;j++) 
				{	
					deepRow[j]=row[j];
				}
				data.add(deepRow);
			}//End Of Deep Copy
			return theDataset.getHeader();
		}
		return null;
	}
	
	public int filterDataset(String originalDatasetName,String newDatasetName, String filterColumnName, String filterValue) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		String[] header = retrieveDataset(originalDatasetName, data);
		if(header==null) {
			System.out.println("Could not retrieve header ");
			return -1;
		}
		//position of attibute in the header

		int attributePos=getPositionAttribute(header, filterColumnName);
		if(attributePos==-1) {
			System.out.println("Could not find attribute ");
			return -1;
		}
		ArrayList<String[]> filteredData=new ArrayList<String[]>();
		filteredData.add(header);
		for(String[] row:data) {
			if(row[attributePos].equals(filterValue)) {
				filteredData.add(row);
			}
		}
		datasets.add(new Dataset(newDatasetName,filteredData));
		return 0;
	}
	public ArrayList<String[]> getDatasetProjection(String datasetName, ArrayList<String> attributeNames){
		//-----findDatasetIfExists(Dataset)-------
		Dataset reqDataset=searchDataset(datasetName);
		
		if(reqDataset==null) {
			System.out.println("Dataset does not exist!!!!");
			return null;
		}
		String[] header =  reqDataset.getHeader();
		//----------GetPositionOfAttributes(int[])------------
		int[] attributePos= getAttributePositionArray(header, attributeNames);
		
		if(attributePos==null){
			System.out.println("Could not find position of attribute");
			return null;
		}

		return getPairsProjection(reqDataset, attributePos);
		//make pairs
	}

	//--------------------Helper Methods-----------------------
	
	//fills arraylist with pairs(coordinates) to be projected
	private ArrayList<String[]> getPairsProjection(Dataset theDataset, int[] attributePos){
		ArrayList<String[]> data=new ArrayList<String[]>();
		data = theDataset.getData();
		ArrayList<String[]> projection=new ArrayList<String[]>();
		
		for(int i = 1; i < data.size(); i++) {
			//we dont want the first row in the header
			String[] values=new String[2];
			values[0]=data.get(i)[attributePos[0]]; values[1]=data.get(i)[attributePos[1]];
			projection.add(values);
		}
		
		return projection;
	}


	private Dataset searchDataset(String datasetName){
		for(Dataset dataset : datasets){
			if(dataset.getName().equals(datasetName))
			{
				return dataset;
			}
		}
		return null;
	}
	
	//is Called from DatasetProjection to get x and y attributes
	private int[] getAttributePositionArray(String[] header, ArrayList<String> attributeNames)
	{
		int [] attributePos= {-1,-1};
		attributePos[0] = getPositionAttribute(header, attributeNames.get(0));
		attributePos[1] = getPositionAttribute(header, attributeNames.get(1));
		
		if(attributePos[0]==-1||attributePos[1]==-1){
			System.out.println("An error occured during finding position of the attributes");
			return null;
		}
		return attributePos;
	}
	
	
	//find the position of an attribute in the header
	private int getPositionAttribute(String[] header, String searchAttribute){
		int retValue= -1;
		for(int i = 0; i < header.length; i++)
		{
			if(header[i].equals(searchAttribute)) {
				retValue = i;
				break;
			}	
		}
		return retValue;
	}

}