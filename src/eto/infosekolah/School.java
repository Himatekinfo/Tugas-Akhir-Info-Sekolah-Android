package eto.infosekolah;

import java.io.Serializable;
import java.util.ArrayList;

public class School implements Serializable {
	public class Schools {
		ArrayList<School> items;

		public Schools() {
			this.items = new ArrayList<School>();
		}

		public void add(School item) {
			this.items.add(item);
		}
	}

	private static final long serialVersionUID = 982721872436868950L;
	public String Name = "";
	public String Id = "";
	public String Cost = "";
	public String Accreditation = "";
	public String Website = "";
	public String CategoryId = "";
	public String GoogleId = "";
	public String Latitude = "";
	public String Longitude = "";
	public String Address = "";
	public String CostDetails = "";
	public String EncodedPolyline = "";

	public String Distance = "0";
	public String InitialCost = "0";
	public String PeriodicalCost = "0";

}