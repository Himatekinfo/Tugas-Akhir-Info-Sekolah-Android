package eto.infosekolah;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Category implements Serializable {
	private static final long serialVersionUID = -4954197460792825261L;
	public String Id = "";
	
	@SerializedName("LookupName")
	public String Name = "";
	
	@SerializedName("LookupValue")
	public String Value = "";
	
	public class Categories {
		ArrayList<Category> items;
		    
	    public Categories()
	    {
	        items = new ArrayList<Category>();
	    }
	    
	    public void add(Category item)
	    {
	        items.add(item);
	    }
	}
}
