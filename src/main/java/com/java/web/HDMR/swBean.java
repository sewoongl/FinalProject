package com.java.web.HDMR;

import org.apache.hadoop.io.Text;

public class swBean {
	
	String year;					
	int goals = 0;		
	int rebounds = 0;
	int assist = 0;
	int steal = 0;
	boolean goalsYn = true;
	boolean reboundsYn = true;	
	boolean assistYn = true;	
	boolean stealYn = true;	
	
	public swBean(Text value) {
		
		try {
			String[] colums = value.toString().split(",");
			this.year = colums[0];
			this.goals = Integer.parseInt(colums[10]);
			this.rebounds = Integer.parseInt(colums[25]);
			this.assist = Integer.parseInt(colums[26]);
			this.steal = Integer.parseInt(colums[29]);
			
			if (colums[10].equals("0")) {
				this.goalsYn = false;
			}else {
				this.goals = Integer.parseInt(colums[10]);
			}
			if (colums[25].equals("0")) {
				this.reboundsYn = false;
			}else {
				this.rebounds = Integer.parseInt(colums[25]);
			}
			if (colums[26].equals("0")) {
				this.assistYn = false;
			}else {
				this.assist = Integer.parseInt(colums[26]);
			}
			if (colums[29].equals("0")) {
				this.stealYn = false;
			}else {
				this.steal = Integer.parseInt(colums[29]);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getYear() {
		return year;
	}

	public int getGoals() {
		return goals;
	}

	public int getRebounds() {
		return rebounds;
	}

	public int getAssist() {
		return assist;
	}

	public int getSteal() {
		return steal;
	}

	public boolean isGoalsYn() {
		return goalsYn;
	}

	public boolean isReboundsYn() {
		return reboundsYn;
	}

	public boolean isAssistYn() {
		return assistYn;
	}

	public boolean isStealYn() {
		return stealYn;
	}

	@Override
	public String toString() {
		return "swBean [year=" + year + ", goals=" + goals + ", rebounds=" + rebounds + ", assist=" + assist
				+ ", steal=" + steal + ", goalsYn=" + goalsYn + ", reboundsYn=" + reboundsYn + ", assistYn=" + assistYn
				+ ", stealYn=" + stealYn + "]";
	}

	
	
	

}	